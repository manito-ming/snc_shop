package java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import snc.server.ide.dao.UserInfoDao;

import javax.annotation.Resource;

@ContextConfiguration(value = {"classpath:applicationContext.xml"})
public class Test {
    @Autowired
    private UserInfoDao userInfoDao;


    @org.junit.Test
    public void selectByPage() throws Exception {

        String users = userInfoDao.getUUID("a");
        System.out.println(users);

    }
}
