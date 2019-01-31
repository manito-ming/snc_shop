package snc.boot.boot;

import snc.boot.connector.IDEServerConnector;
import snc.boot.connector.ShopServerConnector;
import snc.boot.util.common.BaseString;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Created by jac on 18-11-3.
 */
public class Boot {
    private final static String CONFIG_PROPERTIES = "sncconfig.properties";
    private static Charset charset = Charset.forName("UTF-8");
    private static boolean init = false;
    private static String IDE_IP;
    private static String SHOP_IP;
    private static int IDE_PORT;
    private static int SHOP_PORT;
    private static String IDE_PACKAGE_NAME;
    private static String SHOP_PACKAGE_NAME;
    private final static String ALL_STRING = "all";
    private final static String IDE_STRING = "ide";
    private final static String SHOP_STRING = "shop";
    private static String ARGS;
    private static String WEBSOCKET_URL;
    private static String USE_SPRING;

    private static Logger logger = Logger.getLogger(Boot.class);
    private static String ES_SHELL_PATH;
    private static Boolean ES_is_start = false;

    private static boolean init(){
        if (init) {
            return true;
        }
        boolean error = false;
        try {
            Properties pps = new Properties();
            pps.load(Boot.class.getClassLoader().getResourceAsStream(CONFIG_PROPERTIES));
            ARGS = pps.getProperty("args");
            WEBSOCKET_URL = pps.getProperty("websocket.url");
            USE_SPRING = pps.getProperty("usespring");
            ES_SHELL_PATH = pps.getProperty("es.path");

            if (BaseString.isEmpty(ARGS)) {
                ARGS = ALL_STRING;
            }

            if (BaseString.isEmpty(ES_SHELL_PATH)){
                error = true;
            }

            if (ALL_STRING.equals(ARGS)) {
                IDE_IP = pps.getProperty("ide.ip");
                SHOP_IP = pps.getProperty("shop.ip");
                IDE_PACKAGE_NAME = pps.getProperty("ide.server");
                SHOP_PACKAGE_NAME = pps.getProperty("shop.server");
                String ide_port = pps.getProperty("ide.port");
                String shop_port = pps.getProperty("shop.port");

                if (BaseString.isEmpty(IDE_PACKAGE_NAME) || BaseString.isEmpty(SHOP_PACKAGE_NAME)){
                    error = true;
                    logger.info("ide snc.server name or shop servername is null!");
                }

                if (BaseString.isEmpty(IDE_IP) || BaseString.isEmpty(SHOP_IP)) {
                    error = true;
                    logger.info("ide ip or shop ip is null");
                }

                if (BaseString.isEmpty(ide_port) || BaseString.isEmpty(shop_port)) {
                    error = true;
                    logger.info("ide_port or shop_port is null!");
                } else {
                    IDE_PORT = Integer.parseInt(ide_port);
                    SHOP_PORT = Integer.parseInt(shop_port);
                }
            }

            if (IDE_STRING.equals(ARGS)) {
                IDE_IP = pps.getProperty("ide.ip");
                IDE_PACKAGE_NAME = pps.getProperty("ide.server");
                String ide_port = pps.getProperty("ide.port");
                if (BaseString.isEmpty(IDE_IP) || BaseString.isEmpty(IDE_PACKAGE_NAME) || BaseString.isEmpty(ide_port)) {
                    error = true;
                    logger.info(ARGS +" ide ip or ide snc.server name or ide port is null");
                } else {
                    IDE_PORT = Integer.parseInt(ide_port);
                }
            }

            if (SHOP_STRING.equals(ARGS)) {
                SHOP_IP = pps.getProperty("shop.ip");
                SHOP_PACKAGE_NAME = pps.getProperty("shop.server");
                String shop_port = pps.getProperty("shop.port");
                if (BaseString.isEmpty(SHOP_IP) || BaseString.isEmpty(SHOP_PACKAGE_NAME) || BaseString.isEmpty(shop_port)) {
                    error = true;
                    logger.info(ARGS +" ide ip or ide snc.server name or ide port is null");
                } else {
                    SHOP_PORT = Integer.parseInt(shop_port);
                }
            }

        } catch (IOException e) {
            logger.error(e);
        }
        if (!error) {
            init = true;
        }
        logger.info("初始化完成");
        return init;
    }

    public static void start(){
        if (init()) {
            if (!ES_is_start){
                try {
                    Runtime.getRuntime().exec(ES_SHELL_PATH);
                    ES_is_start = true;
                } catch (IOException e) {
                    logger.error("es open error");
                    return;
                }
            }

            if (USE_SPRING.equals("true")) {
                ServiceManager.init();
            }
            if (Boot.ARGS.equals(Boot.ALL_STRING)) {
                IDEServerConnector iconnector = new IDEServerConnector(Boot.IDE_IP,Boot.IDE_PORT);
                ShopServerConnector sconnector = new ShopServerConnector(Boot.SHOP_IP, Boot.SHOP_PORT);
                iconnector.run();
                sconnector.run();
            }
            if (Boot.ARGS.equals(IDE_STRING)) {
                IDEServerConnector iconnector = new IDEServerConnector(Boot.IDE_IP,Boot.IDE_PORT);
                logger.info("ide service run");
                iconnector.run();
            }
            if (Boot.ARGS.equals(SHOP_STRING)) {
                ShopServerConnector sconnector = new ShopServerConnector(Boot.SHOP_IP, Boot.SHOP_PORT);
                sconnector.run();
            }
        }
    }

    public static String getWebsocketUrl() {
        return WEBSOCKET_URL;
    }

    public static String getIdePackageName() {
        return IDE_PACKAGE_NAME;
    }

    public static String getShopPackageName() {
        return SHOP_PACKAGE_NAME;
    }

    public static Charset getCharset() {
        return charset;
    }
}
