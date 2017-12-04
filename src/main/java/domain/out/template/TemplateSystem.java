package domain.out.template;

import domain.value.Value;

import java.util.List;

/**
 * Interface used to serialize the template system, allowing us to easily switch the template
 * engine.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public interface TemplateSystem {
  /**
   * Method used to configure the template engine at the beginning.
   *
   * @param nameTemplate The name of the template to be used.
   * @param outputType The type of file we want to output.
   */
  void configureTemplateSystem(String nameTemplate, String outputType);

  /**
   * Method used to add new info that will be added to the output file.
   *
   * @param data List of Values that form a complete data.
   */
  void addNewInfoToTemplateSystem(List<Value> data);

  /** Method used to output the file using the selected template. */
  void generateFromTemplate();

  String obtainTemplateString(List<Value> data);
}
