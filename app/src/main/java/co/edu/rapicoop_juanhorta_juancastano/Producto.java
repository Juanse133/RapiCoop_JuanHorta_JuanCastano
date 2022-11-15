package co.edu.rapicoop_juanhorta_juancastano;

public class Producto {
    //Atributos
    private Integer ID;
    private String NAME;
    private String DESCRIPTION;
    private Double PRICE;
    private Integer QUANTITY;
    private String EMAIL;
    private String TAGS;

    public Producto(Integer ID, String NAME, String DESCRIPTION, Double PRICE, Integer QUANTITY, String EMAIL, String TAGS) {
        this.ID = ID;
        this.NAME = NAME;
        this.DESCRIPTION = DESCRIPTION;
        this.PRICE = PRICE;
        this.QUANTITY = QUANTITY;
        this.EMAIL = EMAIL;
        this.TAGS = TAGS;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public Double getPRICE() {
        return PRICE;
    }

    public void setPRICE(Double PRICE) {
        this.PRICE = PRICE;
    }

    public Integer getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(Integer QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getTAGS() {
        return TAGS;
    }

    public void setTAGS(String TAGS) {
        this.TAGS = TAGS;
    }

    @Override
    public String toString() {
        return NAME + " - $" + PRICE + " - " + QUANTITY + "un.";
    }

}
