package snc.server.ide.handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import snc.boot.util.FinalTable;
import snc.boot.util.common.Router;
import snc.server.ide.pojo.Commodity;
import snc.server.ide.service.handler.Add;


import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeletesCartHandler extends ChannelInboundHandlerAdapter {


    private static DeletesCartHandler deletesCartHandler;
    @PostConstruct
    public void init(){
        deletesCartHandler = this;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fhr = (FullHttpRequest) msg;
        FullHttpResponse freq = null;
        if (fhr.uri().equals("/snc/shop/Commodity/del")) {
            JSONObject o = Router.getMessage(fhr);
            Commodity commodity = new Commodity();
            commodity.setPid(o.getString(FinalTable.USER_ID));
            commodity.setPrice(Integer.parseInt(o.getString(FinalTable.COMMODITY_PRICE)));
            commodity.setMod(o.getString(FinalTable.COMMODITY_MOD));
            commodity.setCol(o.getString(FinalTable.COMMODITY_COL));
            commodity.setNum(Integer.parseInt(o.getString(FinalTable.COMMODITY_NUM)));
            commodity.setSid(o.getString(FinalTable.COMMOFITY_SID));
            commodity.setSize(o.getString(FinalTable.COMMODITY_SIZE));
            Add add = new Add();
            add.del(commodity);

            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String json = null;

            json = "{\"res\":\"true\",\"date\":" + ft.format(dNow) + "}";
            Router.sendMessage("0", json, ctx);

        }else {
            ctx.fireChannelRead(msg);
        }
    }
}
