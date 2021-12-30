package Model;

public class Appointments {

    private int apptID;
    private String apptTitle;
    private String apptDesc;
    private String apptLocation;
    private String apptType;

    public Appointments(int apptID, String apptTitle, String apptDesc, String apptLocation, String apptType){
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptDesc = apptDesc;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
    }

    /**
     *
     * @return apptID
     */
    public int getApptID(){

        return apptID;
    }

    /**
     *
     * @param apptID
     */
    public void setApptID(int apptID){

        this.apptID = apptID;
    }

    /**
     *
     * @return apptTitle
     */
    public String getApptTitle() {

        return apptTitle;
    }

    /**
     *
     * @param apptTitle
     */
    public void setApptTitle(String apptTitle) {

        this.apptTitle = apptTitle;
    }

    /**
     *
     * @return apptDesc
     */
    public String getApptDesc() {

        return apptDesc;
    }

    /**
     *
     * @param apptDesc
     */
    public void setApptDesc(String apptDesc) {

        this.apptDesc = apptDesc;
    }

    /**
     *
     * @return apptLocation
     */
    public String getApptLocation() {

        return apptLocation;
    }

    /**
     *
     * @param apptLocation
     */
    public void setApptLocation(String apptLocation) {

        this.apptLocation = apptLocation;
    }

    /**
     *
     * @return apptType
     */
    public String getApptType() {

        return apptType;
    }

    /**
     *
     * @param apptType
     */
    public void setApptType(String apptType) {

        this.apptType = apptType;
    }
}
