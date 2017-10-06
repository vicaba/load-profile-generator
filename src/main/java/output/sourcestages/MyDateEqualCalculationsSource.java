package output.sourcestages;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.*;
import akka.stream.javadsl.Source;
import akka.stream.stage.AbstractOutHandler;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import domain.calculations.Calculations;
import domain.calculations.MyStatefulDateEqualCalculations;

import java.time.LocalDateTime;
import java.util.concurrent.CompletionStage;

public class MyDateEqualCalculationsSource extends GraphStage<SourceShape<LocalDateTime>> {

  public final Outlet<LocalDateTime> out = Outlet.create("LocalDateTimeSource.out");
  private final SourceShape<LocalDateTime> shape = SourceShape.of(out);

  private Calculations<LocalDateTime> calculations;

  /**
   * Prevent users from uncompleted instantiation
   */
  private MyDateEqualCalculationsSource() {}

  public MyDateEqualCalculationsSource(Calculations<LocalDateTime> calculations) {
    this.calculations = calculations;
  }

  @Override
  public GraphStageLogic createLogic(Attributes inheritedAttributes) throws Exception {
    return new GraphStageLogic(shape) {

      {
        setHandler(out, new AbstractOutHandler() {
        @Override
        public void onPull() throws Exception {
          push(out, calculations.calculate());
        }
      });
      }


    };
  }

  @Override
  public SourceShape<LocalDateTime> shape() {
    return shape;
  }

  public static void main(String[] args) {
    // Especifica la fuente de datos, el primer parámetro indica el tipo de datos que sacará la
    // fuente.
    final Source<LocalDateTime, NotUsed> source = Source.fromGraph(new MyDateEqualCalculationsSource(new MyStatefulDateEqualCalculations(LocalDateTime.now(), 100)));
    // Necesario ya que necesitamos uno al menos para el Materializer, y es necesario un ActorSystem
    // para tener alguno.
    final ActorSystem system = ActorSystem.create("QuickStart");
    // Especifica una factoría de streams. Es necesario para hacer stream entre Actors.
    final Materializer materializer = ActorMaterializer.create(system);

    // runForEach hace que la fuente pase al Materializer y que comience a sacar datos,
    // aparte de sacar la info por consola.
    final CompletionStage<Done> done = source.runForeach(System.out::println, materializer);
    done.thenRun(system::terminate);
  }

}
