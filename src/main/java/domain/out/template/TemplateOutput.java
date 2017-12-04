package domain.out.template;

import domain.value.Value;

import java.util.List;

/**
 * Class that is used to use the current selected template engine to generate an output file with
 * all the data.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class TemplateOutput {
  /** Class that contains functions to work with the current template engine that we selected. */
  private TemplateSystem templateSystem;

  /** Constructor. */
  public TemplateOutput() {
    templateSystem = new FreemakerTemplateSystem();
  }

  /**
   * Method that allows us to call the same method from the template system class to configure the
   * template engine.
   *
   * @param nameTemplate The name of the template to be used.
   * @param type The type of file we want of output.
   */
  public void configureTemplateSystem(String nameTemplate, String type) {
    this.templateSystem.configureTemplateSystem(nameTemplate, type);
  }

  /**
   * Method that allows us to call the same method from the template system class to add new info to
   * the template.
   *
   * @param data List of Values that form a completed data.
   */
  public void addNewInfoToTemplateSystem(List<Value> data) {
    this.templateSystem.addNewInfoToTemplateSystem(data);
  }

  /**
   * Method that allows us to call the same method from the template system class to generate the
   * output file.
   */
  public void generateFromTemplate() {
    this.templateSystem.generateFromTemplate();
  }

  public String obtainTemplateString(List<Value> data) {
    return this.templateSystem.obtainTemplateString(data);
  }
}
