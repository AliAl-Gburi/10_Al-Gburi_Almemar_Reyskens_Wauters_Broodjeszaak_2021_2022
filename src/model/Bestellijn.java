package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bestellijn {
    private String naamBroodje;
    private String belegsoorten;
    private double prijs;

    public Bestellijn(String naamBroodje) {
        this.naamBroodje = naamBroodje;
    }

    public String getNaamBroodje() {
        return naamBroodje;
    }

    public String getBelegsoorten() {
        return this.belegsoorten;
    }

    public void voegBelegToe(String beleg) {
        if(belegsoorten == null || belegsoorten.trim().isEmpty()) {
            belegsoorten = beleg;
        } else {
            belegsoorten += ", " + beleg;
        }
    }

    public List<String> getBelegSoortenList() {
        List<String> belegsoorten = new ArrayList<>(Arrays.asList(getBelegsoorten().split(",")));
        return belegsoorten;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if(!(o instanceof Bestellijn)) return false;
        if(this.belegsoorten == null || this.belegsoorten.trim().isEmpty()) return false;

        Bestellijn b = (Bestellijn) o;
        return this.naamBroodje.equals(b.naamBroodje) && this.belegsoorten.equals(b.belegsoorten);
    }

    @Override
    public String toString() {
        return this.naamBroodje + this.belegsoorten;
    }
}
