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
interface TemplateSystem {
  /**
   * Method used to configure the template engine at the beginning.
   *
   * @param type The type of file we want to output.
   */
  void configureTemplateSystem(String type);

  /**
   * Method used to add new info that will be added to the output file.
   *
   * @param data List of Values that form a complete data.
   */
  void addNewInfoToTemplateSystem(List<Value> data);

  /** Method used to output the file using the selected template. */
  void generateFromTemplate();
}
