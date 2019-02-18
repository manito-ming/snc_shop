package snc.server.ide.pojo;

public class CdOrder {
   private String orderid;    //订单编号
    private String phone;     //电话
    private String addr;      //地址
    private String oname;     //姓名
    private double money;     //商品价格
    private String orderdate; //下单日期
    private int sta;       //状态
    private String kd;        //物流单号
    private String scms;      //商品信息

    public CdOrder() {
    }

    public CdOrder(String orderid, String phone, String addr, String oname, double money, String orderdate, int sta, String kd, String scmc) {
        this.orderid = orderid;
        this.phone = phone;
        this.addr = addr;
        this.oname = oname;
        this.money = money;
        this.orderdate = orderdate;
        this.sta = sta;
        this.kd = kd;
        this.scms = scmc;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public int getSta() {
        return sta;
    }

    public void setSta(int sta) {
        this.sta = sta;
    }

    public String getKd() {
        return kd;
    }

    public void setKd(String kd) {
        this.kd = kd;
    }

    public String getScms() {
        return scms;
    }

    public void setScms(String scmc) {
        this.scms = scmc;
    }
}
