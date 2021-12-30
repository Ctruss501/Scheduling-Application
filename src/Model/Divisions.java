package Model;

public class Divisions {

    private int divID;
    private String divDivision;

    public Divisions(int divID, String divDivision){
        this.divID = divID;
        this.divDivision = divDivision;
    }

    /**
     *
     * @return divID
     */
    public int getDivID() {
        return divID;
    }

    /**
     *
     * @param divID
     */
    public void setDivID(int divID) {
        this.divID = divID;
    }

    /**
     *
     * @return divDivision
     */
    public String getDivDivision() {
        return divDivision;
    }

    /**
     *
     * @param divDivision
     */
    public void setDivDivision(String divDivision) {
        this.divDivision = divDivision;
    }
}
