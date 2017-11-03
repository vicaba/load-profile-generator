import akka.stream.scaladsl.Broadcast
import domain.in.config.ConfigHolder
import domain.in.distribution.InputDistribution
import domain.in.field.InputField
import domain.in.field.options.Options
import domain.stream.stage.conversion.{InputDistributionConversions, InputFieldConversions}
import domain.stream.stage.flow.rules.RulesFlow
import domain.stream.stage.merge.{MergeNode, MergeNodeTest}
import domain.transform.rule.RulesCheck
import domain.value.Value

import scala.collection.JavaConverters._
import scala.languageFeature.implicitConversions


class GraphGenerator {
  //implicit def sourceValueTToSource[V](st: SourceValueT[V, _]): Source[Value[V], NotUsed] = Source.fromGraph(st)

  /**
    * Java API
    *
    * @param listFields
    * @param rulesCheck
    * @param distributions
    */
  def generate(
                listFields: java.util.ArrayList[InputField[Options]],
                rulesCheck: RulesCheck,
                distributions: java.util.ArrayList[InputDistribution]): Unit = {

    generate(listFields.asScala.toList, rulesCheck, distributions.asScala.toList)

  }

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
        .map(vg => vg.getId -> InputDistributionConversions.valueGeneratorToDistribution(vg, inputDist(vg.getId)))
        .toMap
      println(s"Elements in map4 = $mapDistributions")

      val rulesFlow = new RulesFlow(rulesCheck)

      val mergeNodeTest = new MergeNodeTest(mapSources, mapBroadcasts, mapDistributions, listConnections, rulesFlow)
      mergeNodeTest.connectAndRunGraph()
    }
  }

  /**
    * Scala API
    *
    * @param inputFields
    * @param rulesCheck
    * @param distributions
    */
  def generate(
                inputFields: List[InputField[Options]],
                rulesCheck: RulesCheck,
                distributions: List[InputDistribution]): Unit = {

    if (inputFields.nonEmpty) {

      val mapSources = inputFields
        .map(InputFieldConversions.inputFieldToValueGenerator)
        .map(vg => vg.getId -> InputFieldConversions.valueGeneratorToSource(vg))
        .toMap

      println(s"Elements in map1 = $mapSources")

      val mapBroadcasts = distributions
        .map(broad => broad.getId -> Broadcast[Value[_]](2))
        .toMap

      println(s"Elements in map2 = $mapBroadcasts")

      val mapListDist = distributions
        .map(mapDist => mapDist.getResult.getId -> mapDist)
        .toMap

      val mapDistributions = inputFields
        .map(InputDistributionConversions.inputFieldToValueGenerator)
        .map(mapDist => mapListDist(mapDist.getId).getResult.getId
          -> InputDistributionConversions.valueGeneratorToDistribution(
          mapDist, mapListDist(mapDist.getId)))
        .toMap

      //TODO Distribution nodes will have as key idSourceThatAffectsDistribution:idSourceToBeDistributed
      //TODO We will always check if id exists in Broadcast, if it doesn't we will use Source.
      //TODO First outlet of Broadcast (0) will always be the outlet that is not used for distribution
      //TODO Second (1) will only be used for Distribution purposes, and nothing more.

      val rulesFlow = new RulesFlow(rulesCheck)

      val mergeRun = new MergeNode(mapSources, mapBroadcasts, mapDistributions, rulesFlow)
      mergeRun.connectAndRunGraph()

    }

  }


  //TODO The combine method merges all sources into one.
  //TODO If for example we have 3 columns, it will generate an entire data each 3 iterations (test below generates 10 data of 3 columns).
  //TODO The plan would be to grab the 3 columns, put them together in one single List, and pass them to a Sink
  //TODO This sink will do whatever job we need it to do with the merged value.
  //TODO Now to figure out how to do it with any number of columns...
  /*  val mergedResult: Future[Done] = merged.take(30).runWith(
      Sink.foreach(i => println(i.getId + " " + i.getType + " " + i.getValue.toString))
    )*/

  /*
  Future.sequence(Seq(doneNumber, doneString, doneDate)).andThen {
    case _ => system.terminate()
  }
  */
}
