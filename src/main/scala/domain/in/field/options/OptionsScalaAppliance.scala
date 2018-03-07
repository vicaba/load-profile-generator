package domain.in.field.options

import domain.value.appliance.Appliance

final class OptionsScalaAppliance(appliance: Appliance) extends OptionsScala {
  def getAppliance: Appliance = this.appliance
}
