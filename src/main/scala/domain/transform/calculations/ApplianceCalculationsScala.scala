package domain.transform.calculations

import domain.value.appliance.Appliance

final class ApplianceCalculationsScala(appliance: Appliance) extends CalculationsScala[Appliance] {

  override def calculate: Appliance = {
    //Here we start generating different time values for appliances
    //Values will be a random value of time based on the given configuration.
    this.appliance
  }
}
