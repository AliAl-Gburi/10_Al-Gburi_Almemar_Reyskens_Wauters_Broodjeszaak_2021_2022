package model;

public enum BestellingEvents {
    TOEVOEGEN_BROODJE("Voeg broodje toe"), LOAD_DATABASE("Load database");

    private final String stringValue;

    private BestellingEvents(String stringValue) {
        this.stringValue = stringValue;
    }
    public String getStringValue() {
        return this.stringValue;
    }

}
