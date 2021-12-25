package model;

public enum BestellingEvents {
    TOEVOEGEN_BROODJE("Voeg broodje toe");

    private final String stringValue;

    private BestellingEvents(String stringValue) {
        this.stringValue = stringValue;
    }
    public String getStringValue() {
        return this.stringValue;
    }

}
