package domain.in.field;

public class DistributionInfo {

    private String distributionMethod;
    private double offset;
    private double totalData;

    public DistributionInfo(String distributionMethod, double offset, double totalData) {
        this.distributionMethod = distributionMethod;
        this.offset = offset;
        this.totalData = totalData;
    }

    public DistributionInfo() {
        this.distributionMethod = "";
        this.offset = -1;
        this.totalData = -1;
    }

    public String getDistributionMethod() {
        return this.distributionMethod;
    }

    public double getOffset() {
        return this.offset;
    }

    public double getTotalData() {
        return totalData;
    }
}
