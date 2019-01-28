import com.alibaba.fastjson.JSON;
import snc.server.ide.pojo.UserInfo;

import javax.annotation.Resource;

public class Test {
    @org.junit.Test
    public void selectByPage() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setDockerid("123");
        userInfo.setIde_id("234");
        userInfo.setPort("1111");
        userInfo.setUser_id("44444");
        String i = JSON.toJSONString(userInfo);
        System.out.println(i);

    }
}
