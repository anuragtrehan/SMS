package anurag.com.navdrawer.Pojo;

/**
 * Created by AnuragTrehan on 4/13/2016.
 */
public class AdminUser
{
    String name,username,password,contact,email;


    public AdminUser(String contact, String email, String name, String password, String username) {

        this.contact = contact;
        this.email = email;
        this.name = name;
        this.password = password;
        this.username = username;
    }



    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
