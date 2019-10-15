package User;

public abstract class BankSystemUser {
    private String userName;
    private String password;
    private String ID;

    public BankSystemUser(String ID, String userName, String password) {
        this.ID = ID;
        this.userName = userName;
        this.password = password;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
