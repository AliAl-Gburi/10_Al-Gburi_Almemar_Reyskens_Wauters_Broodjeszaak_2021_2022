package model;

import java.util.ArrayList;
import java.util.List;

public class Bestellijn {
    private String naamBroodje;
    private String belegsoorten;

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
}
