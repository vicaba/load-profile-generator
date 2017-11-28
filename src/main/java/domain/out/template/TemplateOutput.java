package domain.out.template;

import domain.value.Value;

import java.util.List;

public class TemplateOutput {
  private TemplateSystem templateSystem;

  public TemplateOutput() {
    templateSystem = new FreemakerTemplateSystem();
  }

  public void configureTemplateSystem(String type) {
    this.templateSystem.configureTemplateSystem(type);
  }

  public void addNewInfoToTemplateSystem(List<Value> data) {
      this.templateSystem.addNewInfoToTemplateSystem(data);
  }

  public void generateFromTemplate() {
      this.templateSystem.generateFromTemplate();
  }
}
