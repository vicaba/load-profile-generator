package output;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "data")
public class OutputData {
    private List<OutputType> outputTypeList;

    OutputData() {
        outputTypeList = new ArrayList<>();
    }

    @XmlElementRef
    public List<OutputType> getOutputTypeList() {
        return this.outputTypeList;
    }

    public void setOutputTypeList(List<OutputType> outputTypeList) {
        this.outputTypeList = outputTypeList;
    }

    void setOutputType(OutputType outputType) {
        this.outputTypeList.add(outputType);
    }
}
