package snc.server.ide.pojo;

public class Commodity {

    private String pid;              //用户唯一id
    private String sid;               //礼物id
    private int num;               //购买数量
    private int price;             //价格
    private String col;            //商品颜色
    private String mod;            //型号
    private String size;              //大小
    private String commodity;         //属性
    private String c_brand;
    private String c_stock;
    private String c_sold;
    private String c_image;
    private String c_details;
    private String c_type;

    public String getC_brand() {
        return c_brand;
    }

    public void setC_brand(String c_brand) {
        this.c_brand = c_brand;
    }

    public String getC_stock() {
        return c_stock;
    }

    public void setC_stock(String c_stock) {
        this.c_stock = c_stock;
    }

    public String getC_sold() {
        return c_sold;
    }

    public void setC_sold(String c_sold) {
        this.c_sold = c_sold;
    }

    public String getC_image() {
        return c_image;
    }

    public void setC_image(String c_image) {
        this.c_image = c_image;
    }

    public String getC_details() {
        return c_details;
    }

    public void setC_details(String c_details) {
        this.c_details = c_details;
    }

    public String getC_type() {
        return c_type;
    }

    public void setC_type(String c_type) {
        this.c_type = c_type;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pidd) {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }
}
