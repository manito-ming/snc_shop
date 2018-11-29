package snc.server.ide.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import snc.boot.util.FinalTable;
import snc.boot.util.common.Router;
import snc.server.ide.pojo.Commodity;
import snc.server.ide.test.Add;


import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class CheckCartHandler extends ChannelInboundHandlerAdapter {

//    @Autowired
//    private CommodityCartService commodityCartService;

    private static CheckCartHandler checkCartHandler;
    @PostConstruct
    public void init(){
        checkCartHandler = this;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fhr = (FullHttpRequest) msg;
        FullHttpResponse freq = null;
        if (fhr.uri().equals("/snc/shop/Commodity/check")) {
            JSONObject o = Router.getMessage(fhr);
            Commodity commodity = new Commodity();
            commodity.setPid(o.getString(FinalTable.USER_ID));
//            commodity.setPrice(Integer.parseInt(o.getString(FinalTable.COMMODITY_PRICE)));
//            commodity.setMod(o.getString(FinalTable.COMMODITY_MOD));
//            commodity.setCol(o.getString(FinalTable.COMMODITY_COL));
//            commodity.setNum(Integer.parseInt(o.getString(FinalTable.COMMODITY_NUM)));
//            commodity.setSid(o.getString(FinalTable.COMMOFITY_SID));
//            commodity.setSize(o.getString(FinalTable.COMMODITY_SIZE));
            Add add = new Add();
            String check = add.dianjigouwuche(commodity);

            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String json = null;

            json = "{\"res\":\"true\",\"list\":" + check + ",\"date\":" + ft.format(dNow) + "}";
            Router.sendMessage("0", json, ctx);



        }else {
            ctx.fireChannelRead(msg);
        }
    }
}