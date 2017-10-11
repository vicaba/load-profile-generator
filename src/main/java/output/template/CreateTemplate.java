package output.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class CreateTemplate {
  public class Product {
    private String url;
    private String name;

    Product() {}

    public String getUrl() {
      return this.url;
    }

    public String getname() {
      return this.name;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  private Configuration cfg;

  public CreateTemplate() {
    try {
      cfg = new Configuration(Configuration.VERSION_2_3_26);
      cfg.setDirectoryForTemplateLoading(new File("templates"));
      cfg.setDefaultEncoding("UTF-8");
      cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
      cfg.setLogTemplateExceptions(false);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void createObjectTemplate() {
    try {
      Map<String, Object> root = new HashMap<>();
      root.put("user", "Big Joe");

      Product latestProduct = new Product();
      latestProduct.setUrl("templates/test.html");
      latestProduct.setName("HELLO");
      root.put("latestProduct", latestProduct);

      Template template = cfg.getTemplate("test.ftlh");

      Writer out = new OutputStreamWriter(System.out);
      template.process(root, out);
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
  }
}
