package Model;
//Model to get and set login of given user
public class Login {
    public  Integer mId;
    public String mUsername;
    public String mPassword;

    @Override
    public String toString() {
        return "Login{" +
                "mId=" + mId +
                ", mUsername='" + mUsername + '\'' +
                ", mPassword='" + mPassword + '\'' +
                '}';
    }
    public Login(Integer id){
        this.mId = id;
    }
    public Login(Integer id, String password, String username) {
        this.mId = id;
        this.mPassword = password;
        this.mUsername = username;
    }
    public Login(String password, String username) {
        this.mPassword = password;
        this.mUsername = username;
    }
    public void Login(int id, String Password){
        this.mId = id;
        this.mPassword = Password;
    }
    public Login(){

    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer mId) {
        this.mId = mId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
