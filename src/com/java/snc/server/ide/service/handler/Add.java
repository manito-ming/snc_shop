package snc.server.ide.service.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import snc.boot.redis.GetJedis;
import snc.server.ide.pojo.Commodity;

import snc.server.ide.service.UserInfoService;

import java.sql.ResultSet;
import java.util.List;

public class Add {                //点击加入购物车时的redis流程，购物车的数据存储

   private Jedis jedis = GetJedis.getJedis();

    public void add(Commodity commodity){//考虑到购买同一个商品但是商品的属性不同，那么购物车也会显示不同属性，所以redis的key不能以商品id，而是应该以所有属性合起来来的字符串为key

        String cofiled=commodity.getSid()+"_"+commodity.getCol()+"_"+commodity.getMod()+"_"+commodity.getSize();
        String coStr=jedis.hget("commodity_"+commodity.getPid(),cofiled);
        System.out.println(coStr+"--------------");
        Transaction multi=jedis.multi();                      //用返回的jedis对象开启事务
        jedis.watch("commodity_" +commodity.getPid());// 监听key
        Commodity commodity1=(Commodity) JSONObject.toJavaObject(JSON.parseObject(coStr), Commodity.class);
//        System.out.println(commodity1.getMod()+"=========");
        if(commodity1!=null){                                 //在购物车里，用户再一次的选择商品的数量和京东一样,如果不为null，说明说明我是在购物车里修改商品的数量
            commodity1.setNum(commodity.getNum());              //购买数量

            commodity1.setPrice(commodity.getPrice());

        }else{
                commodity1=commodity;

        }
        multi.hset("commodity_"+commodity1.getPid(),cofiled, JSON.toJSONString(commodity1));
       // 当取redis缓存的时候，值发生改变的，watch下面的事物事件会中断，这样的话
        List<Object> exec = multi.exec();
        // 释放监听
//        jedis.unwatch(jedis);
        // 当事务执行失败是重新执行一次代码
        if (exec == null) {
            add(commodity);
        }

    }

    public String dianjigouwuche(Commodity commodity){    //当用户点击购物车时，给前端返回所有的商品

        List<String> strings=jedis.hvals("commodity_"+commodity.getPid());
        System.out.println("========dianjigouwuche=======");
        System.out.println(strings);
//
            return String.valueOf(strings);

//        return String.valueOf(strings);

    }


    public  void del(Commodity commodity){   //我只需要拿到前端的json和加入购物车不一样，我只需要uuid ，sid，num。col，size
                                                       //不用传num，这样可以减少传输字节数
        Jedis jedis = GetJedis.getJedis();
        String cofiled=commodity.getSid()+"_"+commodity.getCol()+"_"+commodity.getMod()+"_"+commodity.getSize();
        jedis.hdel("commodity"+"_"+commodity.getPid(),cofiled);

    }
    public boolean buyCommodity(Commodity commodity){          //付款
        Transaction multi=jedis.multi();
        int money= Integer.parseInt(jedis.hget(commodity.getPid(),"money"));
        if(money-commodity.getPrice()>=0){
            int money1= (int) (money-commodity.getPrice());
            multi.hset("buy_"+commodity.getPid(),commodity.getSid(), JSON.toJSONString(commodity));
            multi.hset(commodity.getPid(),"money", String.valueOf(money));
            return true;
        }else {
            return false;
        }
    }
    public String product(String s){
        JSONObject jsonObj = JSON.parseObject(s);
        ResultSet resultSet=null;
        String pt1=jsonObj.getString("pt");
        String limit=jsonObj.getString("limit");
        int limit1= Integer.parseInt(limit);
        String type1=jsonObj.getString("type");
        String brand1=jsonObj.getString("brand");
        String text1=jsonObj.getString("text");

        return String.valueOf(resultSet);
    }

//    public static void main(String[] args) throws Exception {
//        Add a=new Add();
//        a.channelRead("1",a);
////        a.del();
//    }
}
