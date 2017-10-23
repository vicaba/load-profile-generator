import domain.in.field.InputField
import domain.in.field.options.Options
import domain.stream.stage.conversion.{ConvertListFieldToListGenerator, ConvertListGeneratorToListSource}
import domain.stream.stage.flow.RulesFlow
import domain.stream.stage.merge.MergeNode
import domain.transform.rule.RulesCheck

import scala.collection.JavaConverters._
import scala.languageFeature.implicitConversions


class GeneratorGraph {
  //implicit def sourceValueTToSource[V](st: SourceValueT[V, _]): Source[Value[V], NotUsed] = Source.fromGraph(st)

  def startDataGeneration(listFields: java.util.ArrayList[InputField[Options]],
                          rulesCheck: RulesCheck): Unit = {

    val scalaFields = listFields.asScala.toList

    if (scalaFields.nonEmpty) {

      val values = new ConvertListGeneratorToListSource(
        new ConvertListFieldToListGenerator(
          scalaFields
        ).convert()
      ).convert()

      val rulesFlow = new RulesFlow(rulesCheck)

      val mergeRun = new MergeNode(values, rulesFlow)
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
