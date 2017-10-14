package domain.in.field.options;

import java.util.ArrayList;

public class OptionsString extends Options {
  private ArrayList<String> acceptedStrings;

  public OptionsString() {
    acceptedStrings = new ArrayList<>();
  }

  public int getSizeAccepted() {
    return this.acceptedStrings.size();
  }

  public ArrayList<String> getAcceptedStrings() {
    return this.acceptedStrings;
  }

  public String getAcceptedString(int iWhich) {
    return this.acceptedStrings.get(iWhich);
  }
}
