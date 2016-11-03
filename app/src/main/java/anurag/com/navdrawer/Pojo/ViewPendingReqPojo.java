package anurag.com.navdrawer.Pojo;

/**
 * Created by AnuragTrehan on 5/1/2016.
 */
public class ViewPendingReqPojo
{
    String date, joborder,styleno,size,qualitychecker;
    int stage;

    public ViewPendingReqPojo() {
    }

    public ViewPendingReqPojo(String date, String joborder,String styleno, int stage,String size ,String qualitychecker) {
        this.date = date;
        this.joborder = joborder;
        this.qualitychecker = qualitychecker;
        this.size = size;
        this.stage = stage;
        this.styleno = styleno;
    }

    public String getJoborder() {
        return joborder;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setJoborder(String joborder) {
        this.joborder = joborder;
    }

    public String getQualitychecker() {
        return qualitychecker;
    }

    public void setQualitychecker(String qualitychecker) {
        this.qualitychecker = qualitychecker;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getStyleno() {
        return styleno;
    }

    public void setStyleno(String styleno) {
        this.styleno = styleno;
    }
}
