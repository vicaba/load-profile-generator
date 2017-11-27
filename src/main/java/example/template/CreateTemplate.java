package example.template;

import domain.value.Value;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.*;

public class CreateTemplate {

  private Configuration cfg;
  private ArrayList<List<Value>> outputs;
  private static final List<String> ACCEPTED_TYPES = Arrays.asList("html", "json", "csv");
  private String type = "";

  public CreateTemplate(String type) {
    try {
      this.cfg = new Configuration(Configuration.VERSION_2_3_26);
      this.cfg.setDirectoryForTemplateLoading(new File("templates"));
      this.cfg.setDefaultEncoding("UTF-8");
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
      //root.put("headers", outputs.get(0));

      root.put("outputs", this.outputs);

      //TODO SET A DEFAULT BEHAVIOR
      if (ACCEPTED_TYPES.contains(this.type)) {
        Template template = this.cfg.getTemplate("template_" + this.type + ".ftlh");
        // For output in console, use this one. Testing only.
        //Writer out = new OutputStreamWriter(System.out);

        // For output in file, use this one.
        Writer out = new FileWriter(new File("output/data." + type));

        template.process(root, out);
        out.flush();
        out.close();
      }
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
  }
}
