package anurag.com.navdrawer.Pojo;

/**
 * Created by AnuragTrehan on 4/12/2016.
 */
public class AdminPojo {
    String brand,season,designer_name,fabric_code,fabric_detail,ln_code;
    int checkid;



    public AdminPojo(String value, int checkid) {
        if(checkid==1)
         brand=value ;
        if(checkid==2)
         season=value;
        if(checkid==5)
          ln_code=value;
    }

    public AdminPojo(String value, String value1,int checkid) {
        if(checkid==3)
        {
            brand=value;
            designer_name=value1;
        }
        if(checkid==4)
        {
            fabric_code=value;
            fabric_detail=value1;
        }

    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getCheckid() {
        return checkid;
    }

    public void setCheckid(int checkid) {
        this.checkid = checkid;
    }

    public String getDesigner_name() {
        return designer_name;
    }

    public void setDesigner_name(String designer_name) {
        this.designer_name = designer_name;
    }

    public String getFabric_code() {
        return fabric_code;
    }

    public void setFabric_code(String fabric_code) {
        this.fabric_code = fabric_code;
    }

    public String getFabric_detail() {
        return fabric_detail;
    }

    public void setFabric_detail(String fabric_detail) {
        this.fabric_detail = fabric_detail;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getLn_code() {
        return ln_code;
    }

    public void setLn_code(String ln_code) {
        this.ln_code = ln_code;
    }
}
