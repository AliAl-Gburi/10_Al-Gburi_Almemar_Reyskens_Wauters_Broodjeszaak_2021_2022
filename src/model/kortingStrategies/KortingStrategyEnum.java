package model.kortingStrategies;

public enum KortingStrategyEnum {
    GEENKORTING("Geen korting"),KORTINGCHEAPESTSANWICH("Goedkoopste broodje gratis"), KORTINGTIENPERCENT("10% korting op bestelling");
    private String stringvalue;

    private KortingStrategyEnum(String stringvalue) {
        this.stringvalue = stringvalue;
    }
    public String getStringvalue() {
        return this.stringvalue;
    }
}
