package domain.graph

import akka.NotUsed
import akka.stream.scaladsl.{Broadcast, RunnableGraph, Sink, Source}
import domain.distribution.DistributionFlowFactory
import domain.distribution.generator.DistributionGeneratorFactory
import domain.in.config.InputConfigurationScala
import domain.out.template.TemplateOutputScala
import domain.rules.RulesFlow
import domain.source.{SourceValueFactory, SourceValueFactoryScala}
import domain.source.generator.SourceGeneratorFactoryScala
import domain.template.TemplateSerializerFlow
import domain.transform.rule.RulesCheckScala
import domain.value.Value

import scala.languageFeature.implicitConversions

/**
  * This class is used to generate the graph and all of its nodes, that will generate data endlessly.
  *
  * @version 1.0
  * @author Albert Trias
  * @since 27/11/2017
  */
final class GraphGeneratorScala {

  /**
    * Method that allows us to generate a graph based on all the information received from the parameters.
    *
    * @param inputConfiguration The configuration obtained from the config file.
    * @param rulesCheck         The domain.rules to apply inside the graph.
    * @param createTemplate     The domain.template system to be used in the graph to output data.
    * @return Returns a closed RunnableGraph that we can start running when we want.
    */
  def generate(inputConfiguration: InputConfigurationScala,
               rulesCheck: RulesCheckScala,
               createTemplate: TemplateOutputScala): Unit /*RunnableGraph[NotUsed]*/ = {

    /* We obtain the InputFields from the Configuration class. */
    val inputFields = inputConfiguration.getFields

    if (inputFields.nonEmpty) {

      val mapBroadcasts = inputFields
        .filter(field => inputConfiguration.isBroadcast(field.getId))
        .map(field => field.getId -> Broadcast[Value[_]](2))
        .toMap
      println(s"Elements in map1 = $mapBroadcasts")

      val sourceGeneratorFactory = SourceGeneratorFactoryScala
      val sourceValueFactory = SourceValueFactoryScala
      val mapSources = inputFields
        .filter(field => !inputConfiguration.isDistribution(field.getId))
        .map(field => sourceGeneratorFactory.createGeneratorFromInput(field))
        .map(vg => vg.getId -> sourceValueFactory.createSourceFromGenerator(vg))
        .toMap
      println(s"Elements in map2 = $mapSources")

      /*
       * In this part we will check if there is any source that is meant to apply stream.distribution.
       * If there is, we will generate a list of all the ids of the nodes that are connected to it,
       * and lastly we will map it to the id of the stream.distribution node for easy access to it later.
       */
      /*
      val listConnections = inputFields
        .filter(field => inputConfiguration.isDistribution(field.getId))
        .map(vg => vg.getId -> inputConfiguration.isDistributedBy(vg.getId).asScala.toList)
        .toMap

      println(s"Elements in map3 = $listConnections")

      val distributionGeneratorFactory = DistributionGeneratorFactory
      val distributionFlowFactory = DistributionFlowFactory
      val mapDistributions = inputFields
        .filter(field => inputConfiguration.isDistribution(field.getId))
        .map(distributionGeneratorFactory.createGeneratorFromInput)
        .map(vg => vg.getId -> distributionFlowFactory.createFlowFromGenerator(vg, listConnections(vg.getId)))
        .toMap
      println(s"Elements in map4 = $mapDistributions")
      */

      /* We create both the Flow node that apply domain.rules and the Sink in this part. */
      //val rulesFlow = new RulesFlow(rulesCheck)
      //val templateFlow = new TemplateSerializerFlow(createTemplate)
      //val sinkNode = new SinkNode(createTemplate)

      /* We sent it to a class responsible to connect everything together. */
      //val mergeNodeTest = new GraphConnector(mapSources, mapBroadcasts, mapDistributions, listConnections, rulesFlow, templateFlow)
      //mergeNodeTest.connect()
    } else {

      //TODO Sent a log message indicating that there's been a problem and a valid graph was not generated.
      Source.empty[NotUsed].to(Sink.ignore)

    }
  }

}
