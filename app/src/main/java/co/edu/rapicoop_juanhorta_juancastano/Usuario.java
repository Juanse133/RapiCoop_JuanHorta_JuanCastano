package co.edu.rapicoop_juanhorta_juancastano;

public class Usuario {

    //Atributos
    private Integer ID;
    private String FULLNAME;
    private String USERNAME;
    private String EMAIL;
    private String PASSWORD;
    private String GENDER;

    public Usuario(Integer ID, String FULLNAME, String USERNAME, String EMAIL, String PASSWORD, String GENDER) {
        this.ID = ID;
        this.FULLNAME = FULLNAME;
        this.USERNAME = USERNAME;
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
        this.GENDER = GENDER;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getFULLNAME() {
        return FULLNAME;
    }

    public void setFULLNAME(String FULLNAME) {
        this.FULLNAME = FULLNAME;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getGENDER() {
        return GENDER;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }
}
