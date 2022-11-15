package co.edu.rapicoop_juanhorta_juancastano;

import java.util.Queue;

public class Compra {

    private Integer ID;
    private String PRODUCTS_ID;
    private String CLIENT_EMAIL;
    private String ADDRESS;
    private String STATUS;
    private String DATE;
    private String QUANTITIES;
    private String DEALER;
    private Double PRICE;


    public Compra(Integer ID, String PRODUCTS_ID, String CLIENT_EMAIL, String ADDRESS, String STATUS, String DATE, String QUANTITIES, String DEALER, Double PRICE) {
        this.ID = ID;
        this.PRODUCTS_ID = PRODUCTS_ID;
        this.CLIENT_EMAIL = CLIENT_EMAIL;
        this.ADDRESS = ADDRESS;
        this.STATUS = STATUS;
        this.DATE = DATE;
        this.QUANTITIES = QUANTITIES;
        this.DEALER = DEALER;
        this.PRICE = PRICE;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getPRODUCTS_ID() {
        return PRODUCTS_ID;
    }

    public void setPRODUCTS_ID(String PRODUCTS_ID) {
        this.PRODUCTS_ID = PRODUCTS_ID;
    }

    public String getCLIENT_EMAIL() {
        return CLIENT_EMAIL;
    }

    public void setCLIENT_EMAIL(String CLIENT_EMAIL) {
        this.CLIENT_EMAIL = CLIENT_EMAIL;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getQUANTITIES() {
        return QUANTITIES;
    }

    public void setQUANTITIES(String QUANTITIES) {
        this.QUANTITIES = QUANTITIES;
    }

    public String getDEALER() {
        return DEALER;
    }

    public void setDEALER(String DEALER) {
        this.DEALER = DEALER;
    }

    public Double getPRICE() {
        return PRICE;
    }

    public void setPRICE(Double PRICE) {
        this.PRICE = PRICE;
    }

    @Override
    public String toString() {
        return CLIENT_EMAIL + " - " + STATUS + " - " + DATE;
    }
}
