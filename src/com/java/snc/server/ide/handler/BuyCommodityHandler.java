package snc.server.ide.handler;


import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import snc.boot.util.FinalTable;
import snc.boot.util.common.Router;
import snc.server.ide.pojo.Commodity;
import snc.server.ide.service.ClassService;
import snc.server.ide.service.handler.Add;
import snc.server.ide.service.handler.BuyCommodity;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
@Controller
public class BuyCommodityHandler extends ChannelInboundHandlerAdapter {

    private static BuyCommodityHandler buyCommodityHandler;
    @Autowired
    private ClassService classService;

    @PostConstruct
    public void init(){
        buyCommodityHandler = this;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fhr = (FullHttpRequest) msg;
        FullHttpResponse freq = null;
        if (fhr.uri().equals("/snc/shop/commodity/buy")) {

            JSONObject o = Router.getMessage(fhr);
            Commodity commodity = new Commodity();
            String scms = o.getString(FinalTable.SCMC);
            commodity.setPid(o.getString(FinalTable.USER_ID));
            BuyCommodity add = new BuyCommodity(buyCommodityHandler.classService);
            Boolean bool = add.buyCommodity(commodity,scms);

            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String json = null;
            if (bool == true) {
                json = "{\"res\":\"true\",\"onum\":" + commodity.getPid() + ",\"date\":" + ft.format(dNow) + "}";
                Router.sendMessage("0", json, ctx);
            } else {
                json = "{\"res\":\"false\",\"onum\":\"nil\",\"date\":" + ft.format(dNow) + "}";
                Router.sendMessage("0", json, ctx);
            }

        }else {
            ctx.fireChannelRead(msg);
        }
    }
}

