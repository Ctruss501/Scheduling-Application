package Model;

public class Divisions {

    private int divID;
    private String divDivision;
    private int countryID;

    public Divisions(int divID, String divDivision, int countryID){
        this.divID = divID;
        this.divDivision = divDivision;
        this.countryID = countryID;
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

    /**
     *
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     *
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    @Override
    public String toString(){
        return (divDivision);
    }
}
