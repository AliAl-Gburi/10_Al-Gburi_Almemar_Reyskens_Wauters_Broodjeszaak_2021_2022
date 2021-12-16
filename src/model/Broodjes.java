

package model;

public class Broodjes implements Comparable<Broodjes> {
    /**
     * @authors Ali Al-Gburi
     * **/

    private String broodjesnaam;
    private double verkoopprijs;
    private int voorraad;
    private int verkocht;

    public Broodjes(String broodjesnaam, double verkoopprijs, int voorraad, int verkocht) {
        setBroodjesnaam(broodjesnaam);
        setVerkoopprijs(verkoopprijs);
        setVoorraad(voorraad);
    }

    public void setBroodjesnaam(String broodjesnaam) {
        if(broodjesnaam == null || broodjesnaam.trim().isEmpty()) throw new DomainException("Broodjesnaam mag niet leeg zijn");
        this.broodjesnaam = broodjesnaam;
    }

    public void setVerkoopprijs(double verkoopprijs) {
        if(verkoopprijs < 0) throw new DomainException("Verkooprijs mag niet negatief zijn");
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

    public String getBroodjesnaam() {
        return this.broodjesnaam;
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
        Broodjes b = (Broodjes) o;
        return this.getBroodjesnaam().equals(b.getBroodjesnaam());
    }

    public String toString(){
        return getBroodjesnaam() + "," + getVerkoopprijs() + "," + getVoorraad() + "," + getVerkocht();
    }

    @Override
    public int compareTo(Broodjes o) {
        return this.getBroodjesnaam().compareTo(o.getBroodjesnaam());
    }
}
