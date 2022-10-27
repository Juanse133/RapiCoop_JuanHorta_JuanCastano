package co.edu.rapicoop_juanhorta_juancastano;

public class Carrito {

    private Integer ID;
    private Integer PRODUCT_ID;
    private Integer QUANTITY;
    private Integer CLIENT_ID;

    public Carrito(Integer ID, Integer PRODUCT_ID, Integer QUANTITY, Integer CLIENT_ID) {
        this.ID = ID;
        this.PRODUCT_ID = PRODUCT_ID;
        this.QUANTITY = QUANTITY;
        this.CLIENT_ID = CLIENT_ID;
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

    public Integer getCLIENT_ID() {
        return CLIENT_ID;
    }

    public void setCLIENT_ID(Integer CLIENT_ID) {
        this.CLIENT_ID = CLIENT_ID;
    }
}
