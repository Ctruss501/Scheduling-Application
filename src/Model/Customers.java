package Model;

public class Customers {

    private int custID;
    private String custName;
    private String custAddress;
    private String custPostalCode;
    private String custPhoneNum;
    private int divID;

    public Customers(int custID, String custName, String custAddress, String custPostalCode, String custPhoneNum, int divID){
        this.custID = custID;
        this.custName = custName;
        this.custAddress = custAddress;
        this.custPostalCode = custPostalCode;
        this.custPhoneNum = custPhoneNum;
        this.divID = divID;
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
     * @return custName
     */
    public String getCustName() {
        return custName;
    }

    /**
     *
     * @param custName
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     *
     * @return custAddress
     */
    public String getCustAddress() {
        return custAddress;
    }

    /**
     *
     * @param custAddress
     */
    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    /**
     *
     * @return custPostalCode
     */
    public String getCustPostalCode() {
        return custPostalCode;
    }

    /**
     *
     * @param custPostalCode
     */
    public void setCustPostalCode(String custPostalCode) {
        this.custPostalCode = custPostalCode;
    }

    /**
     *
     * @return custPhoneNum
     */
    public String getCustPhoneNum() {
        return custPhoneNum;
    }

    /**
     *
     * @param custPhoneNum
     */
    public void setCustPhoneNum(String custPhoneNum) {
        this.custPhoneNum = custPhoneNum;
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

    @Override
    public String toString(){
        return (custName);
    }
}
