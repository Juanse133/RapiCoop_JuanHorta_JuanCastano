package co.edu.rapicoop_juanhorta_juancastano;

public class Carrito {

    private Integer ID;
    private Integer PRODUCT_ID;
    private Integer QUANTITY;
    private String CLIENT_EMAIL;

    public Carrito(Integer ID, Integer PRODUCT_ID, Integer QUANTITY, String CLIENT_EMAIL) {
        this.ID = ID;
        this.PRODUCT_ID = PRODUCT_ID;
        this.QUANTITY = QUANTITY;
        this.CLIENT_EMAIL = CLIENT_EMAIL;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getPRODUCT_ID() {
        return PRODUCT_ID;
    }

    public void setPRODUCT_ID(Integer PRODUCT_ID) {
        this.PRODUCT_ID = PRODUCT_ID;
    }

    public Integer getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(Integer QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getCLIENT_EMAIL() {
        return CLIENT_EMAIL;
    }

    public void setCLIENT_EMAIL(String CLIENT_EMAIL) {
        this.CLIENT_EMAIL = CLIENT_EMAIL;
    }
}
