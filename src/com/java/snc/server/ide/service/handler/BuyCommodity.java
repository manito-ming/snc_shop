package snc.server.ide.service.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import snc.boot.redis.GetJedis;
import snc.server.ide.pojo.CdOrder;
import snc.server.ide.pojo.Commodity;
import snc.server.ide.pojo.User;
import snc.server.ide.service.ClassService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BuyCommodity {


    private ClassService classService;
    private Jedis jedis = GetJedis.getJedis();



    public BuyCommodity(ClassService classService) {
        this.classService = classService;
    }
    public boolean buyCommodity(Commodity commodity, String scms){
        System.out.println(scms+"================");
        String[] str = scms.split(",");
        double money = 0;

        for (String scms1:str){
            System.out.println(scms1+"=================");
            String json= jedis.hget("commodity_"+commodity.getPid(),scms1);
            JSONObject object = JSON.parseObject(json);
            double price  = Double.parseDouble(object.getString("price"));
            int num = Integer.parseInt(object.getString("num"));
            /*生成订单*/
            if (order(object,scms1)){
                System.out.println("生成订单成功");
            }
            money += price*num;
        }
        double cartmoney = Double.parseDouble( jedis.hget(commodity.getPid(), "CM"));
        System.out.println("cartmoney: "+cartmoney+"money:"+money);
        Transaction multi=jedis.multi();
        if(cartmoney-money>=0){
            String mm= String.valueOf(cartmoney-money);
            System.out.println("购买成功");
            multi.hset(commodity.getPid(),"CM", mm); //修改redis
            multi.exec();
            /*修改数据库*/
            User user = new User();
            user.setCls_pt(mm);
            user.setUuid(commodity.getPid());
            classService.Updatemoney(user);

            return true;
        }else {
            return false;
        }
    }
    //生成订单表
    public boolean order(JSONObject object,String scms){
        CdOrder cdOrder = new CdOrder();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");//生成订单id的格式
        String dd = df.format(date);

        cdOrder.setScms(scms);
        cdOrder.setMoney(Double.parseDouble(object.getString("price")));
        cdOrder.setOrderdate(dd);
        cdOrder.setSta(0);
        cdOrder.setOrderid(dd);//生成订单id
        return true;
    }
}
