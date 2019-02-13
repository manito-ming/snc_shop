package snc.server.ide.pojo;

public class Commodity {

    private String pid;              //用户唯一id

    public String getC_stock() {
        return c_stock;
    }

    private String sid;               //礼物id
    private int num;               //购买数量
    private double price;
    private String col;            //商品颜色
    private String mod;            //型号
    private String size;              //大小
//    private String commodity;         //属性
    private String c_brand;
    private String c_image;
    private String c_type;
    private String c_details;
    private String c_stock;

    public String getC_brand() {
        return c_brand;
    }

    public String getC_image() {
        return c_image;
    }

    public String getC_type() {
        return c_type;
    }

    public String getC_details() {
        return c_details;
    }

    public void setC_drtails(String c_drtails) {
        this.c_details = c_drtails;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String gid) {
        this.sid = gid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setC_brand(String brand) {//联想
        this.c_brand=brand;
    }

    public void setC_stock(String s) {
        this.c_stock=s;
    }

    public void setC_details(String details) {
        this.c_details=details;
    }

    public void setC_image(String image) {
        this.c_image=image;
    }

    public void setC_type(String type) {
        this.c_type=type;
    }
//    public String getCommodity() {
//        return commodity;
//    }
//
//    public void setCommodity(String commodity) {
//        this.commodity = commodity;
//    }
}
