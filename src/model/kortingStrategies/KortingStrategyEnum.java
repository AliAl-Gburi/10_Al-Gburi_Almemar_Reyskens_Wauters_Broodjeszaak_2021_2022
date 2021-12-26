package model.kortingStrategies;

public enum KortingStrategyEnum {
    KORTINGCHEAPESTSANWICH("KortingCheapestSandwich"), KORTINGTIENPERCENT("KortingCheapestSandwich");
    private String stringvalue;

    private KortingStrategyEnum(String stringvalue) {
        this.stringvalue = stringvalue;
    }
    public String getStringvalue() {
        return this.stringvalue;
    }
}
