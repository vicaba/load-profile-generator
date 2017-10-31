package domain.transform.distribution;

public class DistributionsApplication {
  private String operation;

  public DistributionsApplication(String operation) {
    this.operation = operation;
  }

  public void applyDistribution() {
    switch (operation) {
      case "reset":
        break;
      default:
        break;
    }
  }
}
