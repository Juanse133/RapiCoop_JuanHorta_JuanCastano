package co.edu.rapicoop_juanhorta_juancastano;

public class Compra {

    private Integer ID;
    private String PRODUCTS_ID;
    private String CLIENT_EMAIL;
    private String ADDRESS;
    private String STATUS;
    private String DATE;


    public Compra(Integer ID, String PRODUCTS_ID, String CLIENT_EMAIL, String ADDRESS, String STATUS, String DATE) {
        this.ID = ID;
        this.PRODUCTS_ID = PRODUCTS_ID;
        this.CLIENT_EMAIL = CLIENT_EMAIL;
        this.ADDRESS = ADDRESS;
        this.STATUS = STATUS;
        this.DATE = DATE;
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
}
