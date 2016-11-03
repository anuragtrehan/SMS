package anurag.com.navdrawer.Pojo;

/**
 * Created by AnuragTrehan on 5/5/2016.
 */
public class SearchPojo
{
    String date,job_order_no,style_no,vendor,chart,stage;


    public SearchPojo( String date, String job_order_no,String style_no,String stage, String vendor,String chart) {
        this.chart = chart;
        this.date = date;
        this.job_order_no = job_order_no;
        this.stage = stage;
        this.style_no = style_no;
        this.vendor = vendor;
    }

    public String getChart() {
        return chart;
    }

    public void setChart(String chart) {
        this.chart = chart;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJob_order_no() {
        return job_order_no;
    }

    public void setJob_order_no(String job_order_no) {
        this.job_order_no = job_order_no;
    }

    public String getStyle_no() {
        return style_no;
    }

    public void setStyle_no(String style_no) {
        this.style_no = style_no;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}
