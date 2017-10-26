import akka.NotUsed
import akka.stream.scaladsl.Source
import domain.in.distribution.InputDistribution
import domain.in.field.InputField
import domain.in.field.options.Options
import domain.stream.stage.conversion.InputFieldConversions
import domain.stream.stage.flow.RulesFlow
import domain.stream.stage.merge.MergeNode
import domain.transform.rule.RulesCheck
import domain.value.Value

import scala.collection.JavaConverters._
import scala.languageFeature.implicitConversions


class GraphGenerator {
  //implicit def sourceValueTToSource[V](st: SourceValueT[V, _]): Source[Value[V], NotUsed] = Source.fromGraph(st)

  /**
   * Java API
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

  /**
   * Scala API
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

      val rulesFlow = new RulesFlow(rulesCheck)

      val mergeRun = new MergeNode(mapSources.values.toList, rulesFlow)
      //mergeRun.connectAndRunGraph()

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
