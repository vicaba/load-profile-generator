package domain.transform.distribution;

import domain.in.distribution.InputDistribution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DistributionsCheck {
  private List<InputDistribution> inputDistribution;
  private Map<String, Integer> listCounters = new HashMap<>();

  public DistributionsCheck(List<InputDistribution> inputDistribution) {
    this.inputDistribution = inputDistribution;
    for(InputDistribution input: inputDistribution) {
      listCounters.put(input.getId(),0);
    }
  }

  public void increaseAllCounters() {
    for (Map.Entry<String, Integer> entry : listCounters.entrySet()) {
      System.out.println("++Counter of ID "+entry.getKey()+" is currently "+this.listCounters.get(entry.getKey()));
      listCounters.put(entry.getKey(), entry.getValue()+1);
      System.out.println("++Counter of ID "+entry.getKey()+" is reset to "+this.listCounters.get(entry.getKey()));
    }
  }

  public void increaseCounter(String id) {
    System.out.println("++Counter of ID "+id+" is currently "+this.listCounters.get(id));
    this.listCounters.put(id, this.listCounters.get(id)+1);
    System.out.println("++Counter of ID "+id+" is now "+this.listCounters.get(id));
  }

  public void resetCounter() {
    int count = 0;
    for (Map.Entry<String, Integer> entry : listCounters.entrySet()) {
      System.out.println("++Counter of ID "+entry.getKey()+" is currently "+this.listCounters.get(entry.getKey()));
      listCounters.put(entry.getKey(), entry.getValue()-inputDistribution.get(count).getComparator());
      System.out.println("++Counter of ID "+entry.getKey()+" is reset to "+this.listCounters.get(entry.getKey()));
      count++;
    }
  }

  public boolean checkDistribution() {
    int totalOkay = 0;
    for (InputDistribution input: inputDistribution) {
      switch (input.getCondition()) {
        case "count":
          if (listCounters.get(input.getId()) >= input.getComparator()) {
            System.out.println("++Counter of ID "+input.getId()+" is currently "+this.listCounters.get(input.getId())+ " and reaches value "+input.getComparator());
            totalOkay++;
          }
          break;

        default:
          break;
      }
    }
    System.out.println("There's "+totalOkay+ " totalOkay and size of listCounters is "+listCounters.size());
    return totalOkay == listCounters.size();
  }
}
