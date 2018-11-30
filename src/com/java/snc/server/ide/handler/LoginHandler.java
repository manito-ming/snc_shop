package snc.server.ide.handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import redis.clients.jedis.Jedis;
import snc.boot.redis.GetJedis;
import snc.boot.util.FinalTable;
import snc.boot.util.common.Router;
import snc.server.ide.pojo.User;
import snc.server.ide.pojo.UserInfo;
import snc.server.ide.service.UserInfoService;
import snc.server.ide.service.impl.UserInfoServiceImpl;

import javax.annotation.PostConstruct;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by jac on 18-11-16.
 */
@Controller
public class LoginHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = Logger.getLogger(LoginHandler.class);
    @Autowired
    private UserInfoService userInfoService;

    private static LoginHandler loginHandler;

    @PostConstruct
    public void init(){
        loginHandler = this;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fhr = (FullHttpRequest) msg;
        FullHttpResponse freq = null;
        if (fhr.uri().equals("/snc/ide/login")) {
            logger.info("/snc/ide/login sucess");
            JSONObject o = Router.getMessage(fhr);
            String aid = o.getString(FinalTable.ARM_USER_ID);
            logger.info("user id : "+aid);
            String ide_uid = FinalTable.Prefix_AID+aid;
            Jedis jedis = GetJedis.getJedis();
            String uuid;
            UserInfo userInfo = new UserInfo();
            if (jedis.exists(ide_uid) && (uuid = jedis.hget(ide_uid,FinalTable.USER_ID))!=null) {
                userInfo.setIde_id(ide_uid);
                userInfo.setUser_id(uuid);
                String dockerid = jedis.hget(aid,FinalTable.DOCKER_ID);
                if (dockerid == null) {

                } else {
                    userInfo.setDockerid(dockerid);
                }
                String debugPort = jedis.hget(aid,FinalTable.DEBUG_PORT);
                if (debugPort == null) {
                    //分配port
                } else {
                    userInfo.setPort(Integer.parseInt(debugPort));
                }
            } else if (jedis.exists(aid)) {
                try {
                    System.out.println(loginHandler.userInfoService);
                    String user_id = loginHandler.userInfoService.getUUID(aid);
                    String data = "{\"r\":\"xxxx\",\"d\":\"xxxx\"}";
                    Router.sendMessage("0",data,ctx);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            ctx.fireChannelRead(msg);
        }
    }
    private void sendMessage(FullHttpResponse response, ChannelHandlerContext ctx){

    }
}
