package snc.server.ide.dao;

/**
 * Created by jac on 18-11-17.
 */
public interface UserInfoDao {
    public String getUUID(String aid);
    public String getCM(String uuid);

}
