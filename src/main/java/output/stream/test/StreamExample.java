package output.stream.test;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Source;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import domain.config.ConfigGenerator;
import domain.field.InputField;
import output.StringJsonSerializer;
import output.config.InputConfig;
import output.data.JsonDataPreparation;
import output.stream.test.Greeter.Greet;
import output.stream.test.Greeter.WhoToGreet;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class StreamExample {
  public void example() {

    final Source<Integer, NotUsed> source = Source.repeat(1);
    final ActorSystem system = ActorSystem.create("QuickStart");
    final Materializer materializer = ActorMaterializer.create(system);
    OutputCalculations outputCalculations = new OutputCalculations();
    StringJsonSerializer stringJsonSerializer = new StringJsonSerializer();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    InputConfig inputConfig = new InputConfig("resources/input.json");
    ConfigGenerator configGen;

    if ((configGen = inputConfig.getConfigGenerator()) != null) {
      InputField inputField = configGen.getField(0);
      JsonDataPreparation jsonDataPreparation = new JsonDataPreparation(configGen, 1);
      Source<JsonArray, NotUsed> source3 = Source.single(jsonDataPreparation.prepareData());
      final CompletionStage<Done> done2 =
          source3.runForeach(i -> System.out.println(gson.toJson(i)), materializer);
      done2.thenRun(system::terminate);

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
}
