package domain.out.template;

import domain.value.Value;

import java.util.List;

interface TemplateSystem {
  void configureTemplateSystem(String type);

  void addNewInfoToTemplateSystem(List<Value> data);

  void generateFromTemplate();
}
