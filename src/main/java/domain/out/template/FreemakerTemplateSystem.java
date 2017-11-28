package domain.out.template;

import domain.value.Value;
import freemarker.template.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

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
  /** Constant that defines default accepted type of file to be used in case of error: {@value} */
  private static final String DEFAULT_ACCEPTED_TYPE = "json";
  /** Constant that defines the pathname of the folder that has the templates used: {@value} */
  private static final String TEMPLATES_FOLDER = "templates";
  /** Constant that defines a list of files that are compatible with this class: {@value} */
  private static final List<String> ACCEPTED_TYPES = Arrays.asList("json", "html", "csv");
  /** Constant that defines the name of the variable used to pass data to the template: {@value} */
  private static final String TEMPLATE_VAL = "outputs";
  /** Constant hat defines part of the name used for the template file: {@value} */
  private static final String TEMPLATE_NAME = "template_";
  /** Constant that defines the extension of the template file: {@value} */
  private static final String TEMPLATE_EXTENSION = ".ftlh";
  /** Constant that defines the pathname where the output file will be generated: {@value} */
  private static final String OUTPUT_PATHNAME = "output/data.";

  /** Configuration object used to configure Apache Freemaker. */
  private Configuration cfg;
  /** Data that will be added to the template to generate the output file. */
  private ArrayList<List<Value>> outputs;
  /** Type of the file that will be generated. Initially we add the default one in case of error. */
  private String type = DEFAULT_ACCEPTED_TYPE;

  /**
   * Method that configures Apache Freemaker so we can work with it.
   *
   * @param type The type of file we want to output.
   */
  @Override
  public void configureTemplateSystem(String type) {
    try {
      this.cfg = new Configuration(CONFIGURATION_VERSION);
      this.cfg.setDirectoryForTemplateLoading(new File(TEMPLATES_FOLDER));
      this.cfg.setDefaultEncoding(DEFAULT_ENCODING);
      this.cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
      this.cfg.setLogTemplateExceptions(false);
      this.outputs = new ArrayList<>();
      this.type = type;
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

  /** Method used to generate the output file using the template chosen and the data we've saved. */
  @Override
  public void generateFromTemplate() {
    try {
      Map<String, Object> root = new HashMap<>();
      root.put(TEMPLATE_VAL, this.outputs);

      /*
       * If value doesn't exist inside the accepted types,
       * it will use the default value specified instead so program is not interrupted by this error.
       */
      // TODO Create a log informing the user that the type added doesn't exist and that it has
      // defaulted to another.
      if (!ACCEPTED_TYPES.contains(this.type)) {
        this.type = DEFAULT_ACCEPTED_TYPE;
      }
      Template template = this.cfg.getTemplate(TEMPLATE_NAME + this.type + TEMPLATE_EXTENSION);
      // For output in console, use this one. Testing only.
      // Writer out = new OutputStreamWriter(System.out);

      // For output in file, use this one.
      Writer out = new FileWriter(new File(OUTPUT_PATHNAME + type));

      template.process(root, out);
      out.flush();
      out.close();
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
  }
}
