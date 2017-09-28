package output.stream.test;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Source;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import domain.config.ConfigGenerator;
import domain.field.InputField;
import domain.options.OptionsString;
import output.StringJsonSerializer;
import output.config.InputConfig;
import output.stream.test.OutputCalculations;

import java.util.concurrent.CompletionStage;

public class StreamExample {
    public void example() {
        final Source<Integer, NotUsed> source = Source.repeat(1);
        final ActorSystem system = ActorSystem.create("QuickStart");
        final Materializer materializer = ActorMaterializer.create(system);
        OutputCalculations outputCalculations = new OutputCalculations();
        StringJsonSerializer stringJsonSerializer = new StringJsonSerializer();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        InputConfig inputConfig = new InputConfig();
        ConfigGenerator configGen;

        if ((configGen = inputConfig.getConfigGenerator()) != null) {
            InputField inputField = configGen.getField(0);
            Source<JsonObject, NotUsed> source2 =
                    source
                            .map(i -> ((OptionsString) inputField.getOptions()))
                            .map(j -> j.getAcceptedString(outputCalculations.calculateRangeInteger(0, 2, 'E')))
                            .map(stringJsonSerializer::write);
            final CompletionStage<Done> done =
                    source2.runForeach(i -> System.out.println(gson.toJson(i)), materializer);
            done.thenRun(system::terminate);
        }
    }
}
