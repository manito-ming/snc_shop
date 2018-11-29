package snc.server.ide.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snc.server.ide.dao.UserInfoDao;
import snc.server.ide.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;


    @Override
    public String getUUID(String aid) {
        System.out.println(aid);
        System.out.println(userInfoDao);
        return userInfoDao.getUUID(aid);
    }

    @Override
    public String getCM(String uuid) {
        return userInfoDao.getCM(uuid);
    }
}
