package domain.out.template;

import domain.value.Value;
import freemarker.template.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class CreateTemplate {
  private static final Version CONFIGURATION_VERSION = Configuration.VERSION_2_3_26;
  private static final String TEMPLATES_FOLDER = "templates";
  private static final String DEFAULT_ENCODING = "UTF-8";
  private static final List<String> ACCEPTED_TYPES = Arrays.asList("json", "html", "csv");
  private static final String DEFAULT_ACCEPTED_TYPE = "json";

  private Configuration cfg;
  private ArrayList<List<Value>> outputs;
  private String type = DEFAULT_ACCEPTED_TYPE;

  public CreateTemplate(String type) {
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

  public void addNewInfo(List<Value> data) {
    this.outputs.add(data);
  }

  public void createObjectTemplate() {
    try {
      Map<String, Object> root = new HashMap<>();
      // root.put("headers", outputs.get(0));

      root.put("outputs", this.outputs);

      /*
       * If value doesn't exist inside the accepted types,
       * it will use the default value specified instead so program is not interrupted by this error.
       */
      //TODO Create a log informing the user that the type added doesn't exist and that it has defaulted to another.
      if (!ACCEPTED_TYPES.contains(this.type)) {
        this.type = DEFAULT_ACCEPTED_TYPE;
      }
      Template template = this.cfg.getTemplate("template_" + this.type + ".ftlh");
      // For output in console, use this one. Testing only.
      // Writer out = new OutputStreamWriter(System.out);

      // For output in file, use this one.
      Writer out = new FileWriter(new File("output/data." + type));

      template.process(root, out);
      out.flush();
      out.close();
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
  }
}
