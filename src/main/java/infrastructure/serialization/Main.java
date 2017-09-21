package infrastructure.serialization;

import infrastructure.serialization.json.OutputNumberJsonSerializer;
import output.OutputTypeNumber;

public class Main {
  public static void main(String[] args) {
    OutputNumberJsonSerializer serializer = new OutputNumberJsonSerializer();
    // System.out.println(serializer.write(new OutputTypeNumber())); // Can't create class because it is protected. Add access modifiers to each method and class!
  }
}
