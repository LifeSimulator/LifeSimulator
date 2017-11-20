package subversion_team.cs.brandeis.edu.lifesimulator;

/**
 * Created by zhengyangzhou on 11/14/17.
 */

public class Contact {
    String email,pass;

    public Contact(){

    }

    public Contact(String email, String pass){
        this.email = email;
        this.pass = pass;
    }


    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public void setPass(String pass){
        this.pass = pass;
    }

    public String getPass(){
        return this.pass;
    }
}
