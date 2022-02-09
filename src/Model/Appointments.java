package Model;

import java.time.LocalDateTime;

/**
 * This is the model class for appointments.
 */
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
    private String customer;
    private String user;
    private String contact;


    public Appointments(int apptID, String apptTitle, String apptDesc, String apptLocation, String apptType,
                        LocalDateTime start, LocalDateTime end, String customer, String user, String contact){
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptDesc = apptDesc;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.start = start;
        this.end = end;
        this.customer = customer;
        this.user = user;
        this.contact = contact;
    }

    //Constructor for total customer appointments by month and type.
    public Appointments(String apptType, int total) {
        this.apptType = apptType;
        this.apptID = total;
    }

    //Constructor for contact schedule report.
    public Appointments(int apptID, String apptTitle, String apptType, String apptDesc, LocalDateTime start, LocalDateTime end, String customer) {
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptType = apptType;
        this.apptDesc = apptDesc;
        this.start = start;
        this.end = end;
        this.customer = customer;
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
