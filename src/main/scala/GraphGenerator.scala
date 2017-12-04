import akka.NotUsed
import akka.stream.scaladsl.{Broadcast, RunnableGraph, Sink, Source}
import domain.in.config.InputConfiguration
import domain.out.template.TemplateOutput
import domain.stream.stage.conversion.{InputDistributionConversions, InputFieldConversions}
import domain.stream.stage.flow.rules.RulesFlow
import domain.stream.stage.flow.template.TemplateSerializerFlow
import domain.stream.stage.merge.MergeNode
import domain.transform.rule.RulesCheck
import domain.value.Value

import scala.collection.JavaConverters._
import scala.languageFeature.implicitConversions

/**
  * This class is used to generate the graph and all of its nodes, that will generate data endlessly.
  *
  * @version 1.0
  * @author Albert Trias
  * @since 27/11/2017
  */
class GraphGenerator {

  /**
    * Method that allows us to generate a graph based on all the information received from the parameters.
    *
    * @param inputConfiguration The configuration obtained from the config file.
    * @param rulesCheck         The rules to apply inside the graph.
    * @param createTemplate     The template system to be used in the graph to output data.
    * @return Returns a closed RunnableGraph that we can start running when we want.
    */
  def generate(inputConfiguration: InputConfiguration,
               rulesCheck: RulesCheck,
               createTemplate: TemplateOutput): RunnableGraph[NotUsed] = {

    /* We obtain the InputFields from the Configuration class. */
    val inputFields = inputConfiguration.getFields.asScala.toList

    if (inputFields.nonEmpty) {
      /*
       * In this part we will check if there is any node that connects to a distribution node.
       * If there is, we will create a broadcast of two outlets for each node,
       * and lastly we will map this broadcast to the id of the source node that will connect to it,
       * for easy access to it later.
       */
      val mapBroadcasts = inputFields
        .filter(field => inputConfiguration.isBroadcast(field.getId))
        .map(field => field.getId -> Broadcast[Value[_]](2))
        .toMap
      println(s"Elements in map1 = $mapBroadcasts")

      /*
       * In this part we will check if there is any node that is not meant to apply distribution.
       * If there is, we will create a source with a configured value generator inside it,
       * and lastly we will map it to the id of the source node for easy access to it later.
       */
      val mapSources = inputFields
        .filter(field => !inputConfiguration.isDistribution(field.getId))
        .map(InputFieldConversions.inputFieldToValueGenerator)
        .map(vg => vg.getId -> InputFieldConversions.valueGeneratorToSource(vg))
        .toMap
      println(s"Elements in map2 = $mapSources")

      /*
       * In this part we will check if there is any source that is meant to apply distribution.
       * If there is, we will generate a list of all the ids of the nodes that are connected to it,
       * and lastly we will map it to the id of the distribution node for easy access to it later.
       */
      val listConnections = inputFields
        .filter(field => inputConfiguration.isDistribution(field.getId))
        .map(vg => vg.getId -> inputConfiguration.isDistributedBy(vg.getId).asScala.toList)
        .toMap

      println(s"Elements in map3 = $listConnections")

      /*
       * In this part we will check if there is any source that is meant to apply distribution.
       * If there is, we will create distribution flows with configured value generators inside it,
       * and lastly we will map it to the id of the distribution node for easy access to it later.
       */
      val mapDistributions = inputFields
        .filter(field => inputConfiguration.isDistribution(field.getId))
        .map(InputDistributionConversions.inputFieldToValueGenerator)
        .map(vg => vg.getId -> InputDistributionConversions.valueGeneratorToDistribution(vg, listConnections(vg.getId)))
        .toMap
      println(s"Elements in map4 = $mapDistributions")

      /* We create both the Flow node that apply rules and the Sink in this part. */
      val rulesFlow = new RulesFlow(rulesCheck)
      val templateFlow = new TemplateSerializerFlow(createTemplate)
      //val sinkNode = new SinkNode(createTemplate)

      /* We sent it to a class responsible to connect everything together. */
      val mergeNodeTest = new MergeNode(mapSources, mapBroadcasts, mapDistributions, listConnections, rulesFlow, templateFlow)
      mergeNodeTest.connect()
    } else {

      //TODO Sent a log message indicating that there's been a problem and a valid graph was not generated.
      Source.empty[NotUsed].to(Sink.ignore)

    }
  }

}
