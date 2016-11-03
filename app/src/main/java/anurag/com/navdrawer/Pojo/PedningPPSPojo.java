package anurag.com.navdrawer.Pojo;

/**
 * Created by AnuragTrehan on 5/2/2016.
 */
public class PedningPPSPojo
{
    String date,job_order_no,style_no,size,chart;
    int stage;
    public PedningPPSPojo( String date, String job_order_no, String style_no, int stage, String size,  String chart) {
        this.chart = chart;
        this.date = date;
        this.job_order_no = job_order_no;
        this.size = size;
        this.stage = stage;
        this.style_no = style_no;
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

    public String getStyle_no() {
        return style_no;
    }

    public void setStyle_no(String style_no) {
        this.style_no = style_no;
    }
}
