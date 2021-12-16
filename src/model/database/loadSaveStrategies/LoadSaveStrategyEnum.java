package model.database.loadSaveStrategies;

public enum LoadSaveStrategyEnum {
    BROODJESEXCELLOADSAVESTRATEGY("BroodjesExcelLoadSaveStrategy"), BROODJESTEKSTLOADSAVESTRATEGY("BroodjesTekstLoadSaveStrategy"), BELEGEXCELLOADSAVESTRATEGY("BelegExcelLoadSaveStrategy"), BELEGTEKSTLOADSAVESTRATEGY("BelegTekstLoadSaveStrategy");
    private String stringvalue;

    private LoadSaveStrategyEnum(String stringvalue) {
        this.stringvalue = stringvalue;
    }
    public String getStringvalue() {
        return this.stringvalue;
    }
}
