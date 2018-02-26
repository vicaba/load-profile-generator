package domain.out.template;

import domain.value.Value;
import freemarker.template.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to work with the Apache Freemaker Template Engine to generate output files using
 * templates.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class FreemakerTemplateSystem implements TemplateSystem {
  /** Constant that defines the Apache Freemaker Version used: {@value} */
  private static final Version CONFIGURATION_VERSION = Configuration.VERSION_2_3_26;
  /** Constant that defines the default encoding Apache Freemaker uses: {@value} */
  private static final String DEFAULT_ENCODING = "UTF-8";
  /** Constant that defines the pathname of the folder that has the templates used: {@value} */
  private static final String TEMPLATES_FOLDER = "templates";
  /** Constant that defines the name of the variable used to pass data to the domain.template: {@value} */
  private static final String TEMPLATE_VAL = "outputs";
  /** Constant that defines the pathname where the output file will be generated: {@value} */
  private static final String OUTPUT_PATHNAME = "output/data.";

  /** Configuration object used to configure Apache Freemaker. */
  private Configuration cfg;
  /** Data that will be added to the domain.template to generate the output file. */
  private ArrayList<List<Value>> outputs;
  /** Type of the file that will be generated. Initially we add the default one in case of error. */
  private String outputType;

  private String nameTemplate;

  /**
   * Method that configures Apache Freemaker so we can work with it.
   *
   * @param nameTemplate The name of the domain.template to be used.
   * @param outputType The type of file we want to output.
   */
  @Override
  public void configureTemplateSystem(String nameTemplate, String outputType) {
    try {
      this.cfg = new Configuration(CONFIGURATION_VERSION);
      this.cfg.setDirectoryForTemplateLoading(new File(TEMPLATES_FOLDER));
      this.cfg.setDefaultEncoding(DEFAULT_ENCODING);
      this.cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
      this.cfg.setLogTemplateExceptions(false);
      this.outputs = new ArrayList<>();
      this.nameTemplate = nameTemplate;
      this.outputType = outputType;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method that we use to add a new data to the list of data so that the output file can be
   * generated.
   *
   * @param data List of Values that form a complete data.
   */
  @Override
  public void addNewInfoToTemplateSystem(List<Value> data) {
    this.outputs.add(data);
  }

  /** Method used to generate the output file using the domain.template chosen and the data we've saved. */
  @Override
  public void generateFromTemplate() {
    try {
      Map<String, Object> root = new HashMap<>();
      root.put(TEMPLATE_VAL, this.outputs);

      Template template = this.cfg.getTemplate(this.nameTemplate);
      // For output in console, use this one. Testing only.
      // Writer out = new OutputStreamWriter(System.out);

      // For output in file, use this one.
      Writer out = new FileWriter(new File(OUTPUT_PATHNAME + this.outputType));

      template.process(root, out);
      out.flush();
      out.close();
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String obtainTemplateString(List<Value> data) {
    String result = "";
    try {
      Map<String, Object> root = new HashMap<>();
      root.put(TEMPLATE_VAL, data);

      Template template = this.cfg.getTemplate(this.nameTemplate);
      // For output in console, use this one. Testing only.
      // Writer out = new OutputStreamWriter(System.out);

      // For output in file, use this one.
      StringWriter out = new StringWriter();

      template.process(root, out);
      result = out.getBuffer().toString();
      out.flush();
      out.close();

    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
    return result;
  }
}
