package snc.server.ide.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.springframework.stereotype.Controller;
import snc.boot.util.FinalTable;
import snc.boot.util.common.Router;
import snc.server.ide.test.Elasticsearch.Elasticsearch5;
import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CommodityFirstHandler extends ChannelInboundHandlerAdapter{
    private static CommodityFirstHandler commodityFirstHandle;
    @PostConstruct
    public void init(){
        commodityFirstHandle = this;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fhr = (FullHttpRequest) msg;
        FullHttpResponse freq = null;
        if (fhr.uri().equals("/snc/shop/commodity/first")) {
            System.out.println(fhr.uri());
            JSONObject o = Router.getMessage(fhr);
            Elasticsearch5 elasticsearch5=new Elasticsearch5();
            List list=elasticsearch5.CommodityFirst(o.getString(FinalTable.COMMODITY_PAGE));
            JSONArray jsonArray=JSONArray.parseArray(JSON.toJSONString(list));
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String json = null;
            System.out.println(jsonArray);
            json = "{\"res\":\"true\",\"list\":" + jsonArray + ",,\"date\":" + ft.format(dNow) + "}";
            Router.sendMessage("0", json, ctx);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
