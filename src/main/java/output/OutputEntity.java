package output;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="dataset")
public class OutputEntity {
    private List<OutputData> outputData;

    OutputEntity() {
        outputData = new ArrayList<>();
    }

    @XmlElementRef
    public List<OutputData> getOutputData() {
        return this.outputData;
    }

    public void setOutputData(List<OutputData> outputData) {
        this.outputData = outputData;
    }

    void setOutputData(OutputData outputData) {
        this.outputData.add(outputData);
    }
}
