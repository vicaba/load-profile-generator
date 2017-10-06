package output.stream.test;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.stream.*;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import domain.config.ConfigGenerator;
import domain.field.InputField;
import output.config.InputConfig;
import output.data.DataPreparation;
import output.stream.test.Greeter.Greet;
import output.stream.test.Greeter.WhoToGreet;
import scala.concurrent.duration.Duration;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public class StreamExample {
  public void example() {

    final Source<Integer, NotUsed> source = Source.repeat(1);
    final ActorSystem system = ActorSystem.create("QuickStart");
    final Materializer materializer = ActorMaterializer.create(system);
    OutputCalculations outputCalculations = new OutputCalculations();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    InputConfig inputConfig = new InputConfig("resources/input.json");
    ConfigGenerator configGen;

    if ((configGen = inputConfig.getConfigGenerator()) != null) {

      /*
      InputField inputField = configGen.getField(0);
      DataPreparation dataPreparation = new DataPreparation(configGen);
      Source<JsonArray, NotUsed> source3 = Source.single(dataPreparation.prepareData());
      final CompletionStage<Done> done2 =
          source3.runForeach(i -> System.out.println(gson.toJson(i)), materializer);
      done2.thenRun(system::terminate);
      */

      /*
      Source<JsonObject, NotUsed> source2 =
          source
              .map(i -> ((OptionsString) inputField.getOptions()))
              .map(j -> j.getAcceptedString(outputCalculations.calculateRangeInteger(0, 2, 'E')))
              .map(stringJsonSerializer::write);
      */
      /*
        final CompletionStage<Done> done =
            source2.runForeach(i -> System.out.println(gson.toJson(i)), materializer);
        done.thenRun(system::terminate);
      */
    }
  }

  public void example2() {
    try {
      final ActorSystem system = ActorSystem.create("Helloakka");
      final ActorRef printerActor = system.actorOf(Printer.props(), "printerActor");
      final ActorRef howdyGreeter =
          system.actorOf(Greeter.props("Howdy", printerActor), "hodwyGreeter");
      final ActorRef helloGreeter =
          system.actorOf(Greeter.props("Hello", printerActor), "helloGreeter");
      final ActorRef goodDayGreeter =
          system.actorOf(Greeter.props("Good Day", printerActor), "goodDayGreeter");

      howdyGreeter.tell(new WhoToGreet("Akka"), ActorRef.noSender());
      howdyGreeter.tell(new Greet(), ActorRef.noSender());

      howdyGreeter.tell(new WhoToGreet("Lightbend"), ActorRef.noSender());
      howdyGreeter.tell(new Greet(), ActorRef.noSender());

      helloGreeter.tell(new WhoToGreet("Java"), ActorRef.noSender());
      helloGreeter.tell(new Greet(), ActorRef.noSender());

      goodDayGreeter.tell(new WhoToGreet("Play"), ActorRef.noSender());
      goodDayGreeter.tell(new Greet(), ActorRef.noSender());

      System.out.println(">>> Press ENTER to exit <<<");

      System.in.read();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void example3() {
    ActorHierarchyExperiments actorHierarchy = new ActorHierarchyExperiments();
  }

  public void example4() {
    // Especifica la fuente de datos, el primer parámetro indica el tipo de datos que sacará la
    // fuente.
    final Source<Integer, NotUsed> source = Source.range(0, 100);
    // Necesario ya que necesitamos uno al menos para el Materializer, y es necesario un ActorSystem
    // para tener alguno.
    final ActorSystem system = ActorSystem.create("QuickStart");
    // Especifica una factoría de streams. Es necesario para hacer stream entre Actors.
    final Materializer materializer = ActorMaterializer.create(system);

    // runForEach hace que la fuente pase al Materializer y que comience a sacar datos,
    // aparte de sacar la info por consola.
    final CompletionStage<Done> done = source.runForeach(System.out::println, materializer);

    // Si recibimos por done que el estado del stream ha pasado a que se ha finalizado, esta línea
    // para el ActorSystem.
    done.thenRun(system::terminate);
  }

  public void example5() {
    final Source<Integer, NotUsed> source = Source.range(1, 100);
    final ActorSystem system = ActorSystem.create("QuickStart2");
    final Materializer materializer = ActorMaterializer.create(system);

    final Source<BigInteger, NotUsed> factorials =
        source.scan(BigInteger.ONE, (acc, next) -> acc.multiply(BigInteger.valueOf(next)));

    final CompletionStage<Done> result =
        factorials
            .zipWith(Source.range(0, 99), (num, idx) -> String.format("%d! = %s", idx, num))
            .throttle(1, Duration.create(1, TimeUnit.SECONDS), 1, ThrottleMode.shaping())
            .runForeach(System.out::println, materializer);
    /*
    final CompletionStage<IOResult> result =
            factorials
              .map(BigInteger::toString).runWith(lineSink("output/factorial2.txt"), materializer);
     */

    /*
    final CompletionStage<IOResult> result =
            factorials
              .map(num -> ByteString.fromString(num.toString() + "\n"))
              .runWith(FileIO.toPath(Paths.get("output/factorials.txt")),materializer);
     */

    result.thenRun(system::terminate);
  }

  public void example6(ConfigGenerator configGenerator) {
    /*
    DataPreparation dataPreparation = new DataPreparation(configGenerator);
    final ActorSystem system = ActorSystem.create("QuickStart");
    final Materializer materializer = ActorMaterializer.create(system);
    final Source<JsonElement, NotUsed> source =
        Source.repeat("not-used");
            .map(a -> dataPreparation.prepareData());


    Gson gson = new Gson();
    CompletionStage<Done> resultFuture = source
            .via(new MapStreamData<>(gson::toJson))
            .runWith(Sink.foreach(System.out::println), materializer);

    Scanner scanner = new Scanner(System.in);
    while (scanner.nextLine() == null);
    system.terminate();
    */
  }

  // TODO Siguiente lectura para la proxima vez, General Concepts.

  // Objetivos principales:
  // 1. Cada vez que se ha de enviar un nuevo dato por el Stream, tendremos que generarlo de nuevo
  // para la aleatoriedad
  //   de datos. La funcion importate aqui es prepareData(), ya que esta es la que genera el nuevo
  // dato. Mirar como
  //   poner esta funcion para que cada vez que se quiera enviar un nuevo dato esta se llama
  // reusando el mismo source.
  // 2. El dato que se enviara debe ser un tipo especifico, como por ejemplo String o BigInteger.
  //   String puede ser la mejor opcion para esto al trabajar con json y xml, pero vale la pena
  // mirar
  //   de que otras formas puedo pasar el valor para que no dependa solo de un tipo especifico como
  // JsonArray.
  // 3. Mirar si es necesario hacer algun ajuste de la velocidad en que se ha de enviar datos (en
  // caso de que haya
  //   algun estandar), o si no es necesario controlarlo, aunque akka internamente ya pueda
  // controlar el flujo
  //   para evitar OutOfMemoryException.
  // 4. Como se ha de crear varias columnas para cada dato (depediendo del input.json), mirar para
  // akka como hacer
  //   que la generacion de cada columna se haga en paralelo, para no generar una columna despues de
  // otra y asi
  //   que tarde menos a la hora de generar un dato. Tambien mirar si vale la pena hacer esto o no
  // segun la solucion
  //   encontrada.
}
