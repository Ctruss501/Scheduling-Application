package Model;

/**
 *
 */

public class User {

    private int userID;
    private String username;
    private String password;

    public User(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }
    public User(String username){
        this.username = username;
    }

    /**
     *
     * @return userID
     */
    public int getUserID(){

        return userID;
    }

    /**
     *
     * @param userID
     */
    public void setUserID(int userID){

        this.userID = userID;
    }

    /**
     *
     * @return username
     */
    public String getUsername(){

        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username){

        this.username = username;
    }

    /**
     *
     * @return password
     */
    public String getPassword(){

        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password){

        this.password = password;
    }
}
