package Model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

public class Appointments {

    private int apptID;
    private String apptTitle;
    private String apptDesc;
    private String apptLocation;
    private String apptType;
    private LocalDateTime start;
    private LocalDateTime end;
    private int custID;
    private int userID;
    private int contactID;


    public Appointments(int apptID, String apptTitle, String apptDesc, String apptLocation, String apptType,
                        LocalDateTime start, LocalDateTime end, int custID, int userID, int contactID){
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptDesc = apptDesc;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.start = start;
        this.end = end;
        this.custID = custID;
        this. userID = userID;
        this.contactID = contactID;
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

    /**
     *
     * @return start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     *
     * @param start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     *
     * @return end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     *
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     *
     * @return custID
     */
    public int getCustID() {
        return custID;
    }

    /**
     *
     * @param custID
     */
    public void setCustID(int custID) {
        this.custID = custID;
    }

    /**
     *
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     *
     * @return contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     *
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}
