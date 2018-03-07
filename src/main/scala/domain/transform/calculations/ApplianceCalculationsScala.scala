package domain.transform.calculations

import domain.value.appliance.Appliance

final class ApplianceCalculationsScala(appliance: Appliance) extends CalculationsScala[Appliance] {

  override def calculate: Appliance = {
    this.appliance
  }
}
