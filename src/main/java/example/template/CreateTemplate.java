package example.template;

import domain.value.Value;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateTemplate {

  private Configuration cfg;
  private ArrayList<List<Value>> outputs;

  public CreateTemplate() {
    try {
      cfg = new Configuration(Configuration.VERSION_2_3_26);
      cfg.setDirectoryForTemplateLoading(new File("templates"));
      cfg.setDefaultEncoding("UTF-8");
      cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
      cfg.setLogTemplateExceptions(false);
      outputs = new ArrayList<>();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void addNewInfo(List<Value> data) {
    outputs.add(data);
  }

  public void createObjectTemplate() {
    try {
      Map<String, Object> root = new HashMap<>();
      //root.put("headers", outputs.get(0));

      root.put("outputs", outputs);

      Template template = cfg.getTemplate("template_json.ftl");

      // For output in console, use this one. Testing only.
      //Writer out = new OutputStreamWriter(System.out);

      // For output in file, use this one.
      Writer out = new FileWriter(new File("output/data.json"));

      template.process(root, out);
      out.flush();
      out.close();
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
  }
}
