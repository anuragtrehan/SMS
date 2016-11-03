package anurag.com.navdrawer.Json;

/**
 * Created by AnuragTrehan on 5/5/2016.
 */
public class JsonLink
{
    public JsonLink() {
    }
    String URL="http://samplemanagementsystem.esy.es/";

    String ADDORDER_URL = URL+"/tms/addorder.php";
    String ADDSTYLE_URL =    URL+"/tms/addstyle.php";
    String DATA_URL =        URL+"/tms/admindata.php";
    String PENDING_PPS_URL = URL+"/tms/pendingpps.php";
    String ADDPPS_URL =      URL+"/tms/ppstoqc.php";
    String QCPPS_URL =       URL+"/tms/qcpps.php";
    String SEARCH_URL =      URL+"/tms/search.php";
    String TECH_PPS_URL =    URL+"/tms/techpps.php";
    String DATA_URL1 =       URL+"/tms/adminuser.php";
    String VPR_URL =         URL+"/tms/vpr.php";
    String SEARCH_LIST_URL = URL+"/tms/searchlist.php";
    String SEARCH_RESULT_URL = URL+"/tms/searchresult.php";
    String SEARCH_PPS_URL = URL+"/tms/searchpps.php";
    String LOGIN_URL = URL+"/tms/login.php";

    public String getADDORDER_URL() {
        return ADDORDER_URL;
    }

    public void setADDORDER_URL(String ADDORDER_URL) {
        this.ADDORDER_URL = ADDORDER_URL;
    }

    public String getADDPPS_URL() {
        return ADDPPS_URL;
    }

    public void setADDPPS_URL(String ADDPPS_URL) {
        this.ADDPPS_URL = ADDPPS_URL;
    }

    public String getADDSTYLE_URL() {
        return ADDSTYLE_URL;
    }

    public void setADDSTYLE_URL(String ADDSTYLE_URL) {
        this.ADDSTYLE_URL = ADDSTYLE_URL;
    }

    public String getDATA_URL() {
        return DATA_URL;
    }

    public void setDATA_URL(String DATA_URL) {
        this.DATA_URL = DATA_URL;
    }

    public String getPENDING_PPS_URL() {
        return PENDING_PPS_URL;
    }

    public void setPENDING_PPS_URL(String PENDING_PPS_URL) {
        this.PENDING_PPS_URL = PENDING_PPS_URL;
    }

    public String getQCPPS_URL() {
        return QCPPS_URL;
    }

    public void setQCPPS_URL(String QCPPS_URL) {
        this.QCPPS_URL = QCPPS_URL;
    }

    public String getSEARCH_URL() {
        return SEARCH_URL;
    }

    public void setSEARCH_URL(String SEARCH_URL) {
        this.SEARCH_URL = SEARCH_URL;
    }

    public String getTECH_PPS_URL() {
        return TECH_PPS_URL;
    }

    public void setTECH_PPS_URL(String TECH_PPS_URL) {
        this.TECH_PPS_URL = TECH_PPS_URL;
    }

    public String getVPR_URL() {
        return VPR_URL;
    }

    public void setVPR_URL(String VPR_URL) {
        this.VPR_URL = VPR_URL;
    }

    public String getDATA_URL1() {
        return DATA_URL1;
    }

    public void setDATA_URL1(String DATA_URL1) {
        this.DATA_URL1 = DATA_URL1;
    }

    public String getSEARCH_LIST_URL() {
        return SEARCH_LIST_URL;
    }

    public void setSEARCH_LIST_URL(String SEARCH_LIST_URL) {
        this.SEARCH_LIST_URL = SEARCH_LIST_URL;
    }

    public String getSEARCH_RESULT_URL() {
        return SEARCH_RESULT_URL;
    }

    public void setSEARCH_RESULT_URL(String SEARCH_RESULT_URL) {
        this.SEARCH_RESULT_URL = SEARCH_RESULT_URL;
    }

    public String getLOGIN_URL() {
        return LOGIN_URL;
    }

    public void setLOGIN_URL(String LOGIN_URL) {
        this.LOGIN_URL = LOGIN_URL;
    }

    public String getSEARCH_PPS_URL() {
        return SEARCH_PPS_URL;
    }

    public void setSEARCH_PPS_URL(String SEARCH_PPS_URL) {
        this.SEARCH_PPS_URL = SEARCH_PPS_URL;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
