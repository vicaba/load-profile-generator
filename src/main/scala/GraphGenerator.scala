import akka.stream.scaladsl.Broadcast
import domain.in.config.ConfigHolder
import domain.stream.stage.conversion.{InputDistributionConversions, InputFieldConversions}
import domain.stream.stage.flow.rules.RulesFlow
import domain.stream.stage.merge.{MergeNode}
import domain.transform.rule.RulesCheck
import domain.value.Value

import scala.collection.JavaConverters._
import scala.languageFeature.implicitConversions


class GraphGenerator {

  def generate(configHolder: ConfigHolder,
               rulesCheck: RulesCheck): Unit = {

    val inputFields = configHolder.getFields.asScala.toList
    val inputDist = configHolder.getDistributions.asScala.map(dist => dist.getResult.getId -> dist).toMap
    if (inputFields.nonEmpty) {

      val mapBroadcasts = inputFields
        .filter(field => configHolder.isBroadcast(field.getId))
        .map(field => field.getId -> Broadcast[Value[_]](2))
        .toMap
      println(s"Elements in map1 = $mapBroadcasts")

      val mapSources = inputFields
        .filter(field => !configHolder.isDistribution(field.getId))
        .map(InputFieldConversions.inputFieldToValueGenerator)
        .map(vg => vg.getId -> InputFieldConversions.valueGeneratorToSource(vg))
        .toMap
      println(s"Elements in map2 = $mapSources")

      val listConnections = inputFields
        .filter(field => configHolder.isDistribution(field.getId))
        .map(vg => vg.getId -> configHolder.isDistributedBy(vg.getId).asScala.toList)
        .toMap

      println(s"Elements in map3 = $listConnections")

      val mapDistributions = inputFields
        .filter(field => configHolder.isDistribution(field.getId))
        .map(InputDistributionConversions.inputFieldToValueGenerator)
        .map(vg => vg.getId -> InputDistributionConversions.valueGeneratorToDistribution(vg, listConnections(vg.getId)))
        .toMap
      println(s"Elements in map4 = $mapDistributions")

      val rulesFlow = new RulesFlow(rulesCheck)

      val mergeNodeTest = new MergeNode(mapSources, mapBroadcasts, mapDistributions, listConnections, rulesFlow)
      mergeNodeTest.connectAndRunGraph()
    }
  }

}
