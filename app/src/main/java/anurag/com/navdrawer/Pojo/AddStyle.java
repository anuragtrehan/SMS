package anurag.com.navdrawer.Pojo;

/**
 * Created by AnuragTrehan on 4/18/2016.
 */
public class AddStyle {

    String f_code,f_width,f_consumption;
    String l_code,l_width,l_consumption;

    public AddStyle(String string1, String string2, String string3,int check) {
        if(check==1) {
            f_code = string1;
            f_width = string2;
            f_consumption = string3;
        }
        if(check==2)
        {
            l_code=string1;
            l_width= string2;
            l_consumption =string3;
        }
    }

    public String getF_code() {
        return f_code;
    }

    public void setF_code(String f_code) {
        this.f_code = f_code;
    }

    public String getF_consumption() {
        return f_consumption;
    }

    public void setF_consumption(String f_consumption) {
        this.f_consumption = f_consumption;
    }

    public String getF_width() {
        return f_width;
    }

    public void setF_width(String f_width) {
        this.f_width = f_width;
    }

    public String getL_code() {
        return l_code;
    }

    public void setL_code(String l_code) {
        this.l_code = l_code;
    }

    public String getL_consumption() {
        return l_consumption;
    }

    public void setL_consumption(String l_consumption) {
        this.l_consumption = l_consumption;
    }

    public String getL_width() {
        return l_width;
    }

    public void setL_width(String l_width) {
        this.l_width = l_width;
    }
}
