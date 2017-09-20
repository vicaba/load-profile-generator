package output;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "column")
public class OutputType  {
    String data;
    String type;

    OutputType() {

    }

    @XmlAttribute(name="value")
    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @XmlAttribute(name="type")
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
