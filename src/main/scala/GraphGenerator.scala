import akka.NotUsed
import akka.stream.scaladsl.{Broadcast, RunnableGraph, Sink, Source}
import domain.in.config.InputConfiguration
import domain.stream.stage.conversion.{InputDistributionConversions, InputFieldConversions}
import domain.stream.stage.flow.rules.RulesFlow
import domain.stream.stage.merge.MergeNode
import domain.stream.stage.sink.SinkNode
import domain.transform.rule.RulesCheck
import domain.value.Value
import domain.out.template.CreateTemplate

import scala.collection.JavaConverters._
import scala.languageFeature.implicitConversions


class GraphGenerator {

  def generate(inputConfiguration: InputConfiguration,
               rulesCheck: RulesCheck,
               createTemplate: CreateTemplate): RunnableGraph[NotUsed] = {

    val inputFields = inputConfiguration.getFields.asScala.toList
    //val inputDist = inputConfiguration.getDistributions.asScala.map(dist => dist.getResult.getId -> dist).toMap
    if (inputFields.nonEmpty) {

      val mapBroadcasts = inputFields
        .filter(field => inputConfiguration.isBroadcast(field.getId))
        .map(field => field.getId -> Broadcast[Value[_]](2))
        .toMap
      println(s"Elements in map1 = $mapBroadcasts")

      val mapSources = inputFields
        .filter(field => !inputConfiguration.isDistribution(field.getId))
        .map(InputFieldConversions.inputFieldToValueGenerator)
        .map(vg => vg.getId -> InputFieldConversions.valueGeneratorToSource(vg))
        .toMap
      println(s"Elements in map2 = $mapSources")

      val listConnections = inputFields
        .filter(field => inputConfiguration.isDistribution(field.getId))
        .map(vg => vg.getId -> inputConfiguration.isDistributedBy(vg.getId).asScala.toList)
        .toMap

      println(s"Elements in map3 = $listConnections")

      val mapDistributions = inputFields
        .filter(field => inputConfiguration.isDistribution(field.getId))
        .map(InputDistributionConversions.inputFieldToValueGenerator)
        .map(vg => vg.getId -> InputDistributionConversions.valueGeneratorToDistribution(vg, listConnections(vg.getId)))
        .toMap
      println(s"Elements in map4 = $mapDistributions")

      val rulesFlow = new RulesFlow(rulesCheck)
      val sinkNode = new SinkNode(createTemplate)

      val mergeNodeTest = new MergeNode(mapSources, mapBroadcasts, mapDistributions, listConnections, rulesFlow, sinkNode)

      mergeNodeTest.connect()
    } else {

      Source.empty[NotUsed].to(Sink.ignore)

    }
  }

}
