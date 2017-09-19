package config;

import java.util.ArrayList;

public class OptionsString extends Options {
    private boolean ignoreCaps;
    private ArrayList<String> acceptedStrings;

    public OptionsString() {
        acceptedStrings = new ArrayList<>();
    }

    public int getSizeAccepted() {
        return this.acceptedStrings.size();
    }

    public boolean isIgnoreCaps() {
        return this.ignoreCaps;
    }

    public String getAcceptedString(int iWhich) {
        return this.acceptedStrings.get(iWhich);
    }

    public void setIgnoreCaps(boolean ignoreCaps) {
        this.ignoreCaps = ignoreCaps;
    }
}
