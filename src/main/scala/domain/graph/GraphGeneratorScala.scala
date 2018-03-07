package domain.graph

import akka.NotUsed
import akka.stream.scaladsl.{Broadcast, RunnableGraph, Sink, Source}
import domain.distribution.DistributionFlowFactoryScala
import domain.distribution.generator.DistributionGeneratorFactoryScala
import domain.in.config.InputConfigurationScala
import domain.out.template.TemplateOutputScala
import domain.rules.RulesFlowScala
import domain.source.SourceValueFactoryScala
import domain.source.generator.SourceGeneratorFactoryScala
import domain.template.TemplateSerializerFlowScala
import domain.transform.rule.RulesCheckScala
import domain.value.ValueScala

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
               createTemplate: TemplateOutputScala): RunnableGraph[NotUsed] = {

    /* We obtain the InputFields from the Configuration class. */
    val inputFields = inputConfiguration.fields

    if (inputFields.nonEmpty) {

      val mapBroadcasts = inputFields
        .filter(field => inputConfiguration.isBroadcast(field.info.id))
        .map(field => field.info.id -> Broadcast[ValueScala[_]](2))
        .toMap
      println(s"Elements in map1 = $mapBroadcasts")

      val sourceGeneratorFactory = SourceGeneratorFactoryScala
      val sourceValueFactory = SourceValueFactoryScala
      val mapSources = inputFields
        .filter(field => !inputConfiguration.isDistribution(field.info.id))
        .map(field => sourceGeneratorFactory.createGeneratorFromInput(field))
        .map(vg => vg.getId -> sourceValueFactory.createSourceFromGenerator(vg))
        .toMap
      println(s"Elements in map2 = $mapSources")


      val listConnections = inputFields
        .filter(field => inputConfiguration.isDistribution(field.info.id))
        .map(vg => vg.info.id -> inputConfiguration.isDistributedBy(vg.info.id))
        .toMap

      println(s"Elements in map3 = $listConnections")


      val distributionGeneratorFactory = DistributionGeneratorFactoryScala
      val distributionFlowFactory = DistributionFlowFactoryScala
      val mapDistributions = inputFields
        .filter(field => inputConfiguration.isDistribution(field.info.id))
        .map(distributionGeneratorFactory.createGeneratorFromInput)
        .map(vg => vg.getId -> distributionFlowFactory.createFlowFromGenerator(vg, listConnections(vg.getId)))
        .toMap
      println(s"Elements in map4 = $mapDistributions")


      /* We create both the Flow node that apply domain.rules and the Sink in this part. */
      val rulesFlow = new RulesFlowScala(rulesCheck)
      println(s"RulesFlow = $rulesFlow")
      val templateFlow = new TemplateSerializerFlowScala(createTemplate)
      //val sinkNode = new SinkNode(createTemplate)

      /* We sent it to a class responsible to connect everything together. */
      val mergeNodeTest = new GraphConnectorScala(mapSources, mapBroadcasts, mapDistributions, listConnections, rulesFlow, templateFlow)
      mergeNodeTest.connect()
    } else {

      //TODO Sent a log message indicating that there's been a problem and a valid graph was not generated.
      Source.empty[NotUsed].to(Sink.ignore)

    }
  }

}
