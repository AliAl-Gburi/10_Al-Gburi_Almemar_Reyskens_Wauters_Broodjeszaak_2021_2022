package model.kortingStrategies;

public enum KortingStrategyEnum {
    GEENKORTING("GeenKorting"),KORTINGCHEAPESTSANWICH("KortingCheapestSandwich"), KORTINGTIENPERCENT("KortingTienPercent");
    private String stringvalue;

    private KortingStrategyEnum(String stringvalue) {
        this.stringvalue = stringvalue;
    }
    public String getStringvalue() {
        return this.stringvalue;
    }
}
