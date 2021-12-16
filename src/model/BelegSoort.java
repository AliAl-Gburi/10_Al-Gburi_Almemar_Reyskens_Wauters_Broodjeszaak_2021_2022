package model;

public class BelegSoort implements Comparable<BelegSoort>{
    private String belegnaam;
    private double verkoopprijs;
    private int voorraad;
    private int verkocht;

    public BelegSoort(String belegnaam, double verkoopprijs, int voorraad, int verkocht) {
        setBelegnaam(belegnaam);
        setVerkoopprijs(verkoopprijs);
        setVoorraad(voorraad);
        setVerkocht(verkocht);
    }

    public void setBelegnaam(String belegnaam) {
        if(belegnaam == null || belegnaam.trim().isEmpty()) throw new DomainException("Beleg mag niet null zijn");
        this.belegnaam = belegnaam;
    }

    public void setVerkoopprijs(double verkoopprijs) {
        if (verkoopprijs < 0) throw new DomainException("Verkoopprijs mag niet negatijf zijn");
        this.verkoopprijs = verkoopprijs;
    }

    public void setVoorraad(int voorraad) {
        if(voorraad < 0) throw new DomainException("Voorraad mag niet negatief zijn");
        this.voorraad = voorraad;
    }

    public void setVerkocht(int verkocht) {
        if(verkocht < 0) throw new DomainException("Verkocht mag niet negatief zijn");
        this.verkocht = verkocht;
    }

    public String getBelegnaam() {
        return this.belegnaam;
    }

    public double getVerkoopprijs() {
        return this.verkoopprijs;
    }

    public int getVoorraad() {
        return this.voorraad;
    }

    public int getVerkocht() {
        return this.verkocht;
    }

    public boolean equals(Object o) {
        BelegSoort b = (BelegSoort) o;
        return b.getBelegnaam().equals(this.getBelegnaam());
    }

    public String toString() {
        return getBelegnaam() + "," + getVerkoopprijs() + "," + getVoorraad() + "," + getVerkocht();
    }

    @Override
    public int compareTo(BelegSoort o) {
        return this.getBelegnaam().compareTo(o.getBelegnaam());
    }
}
