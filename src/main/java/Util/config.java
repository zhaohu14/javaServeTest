package Util;

public class config {
    /*
    * 微信小程序配置
    * */
    static public class wxConfig {
        static String AppSecret = "13fff96b422f8f0d615d50257d421a5c";
        static String AppID = "wx00e4757cce863333";
        static String access_token = "";
        static Number expires_in = 0;
        static String getAccessToken = "https://api.weixin.qq.com/cgi-bin/token";
        static String code2Session = "https://api.weixin.qq.com/sns/jscode2session";
        public static void changeAccess_token (String access_token) {
            access_token = access_token;
        }
        public static void  changeExpires_in (String expires_in) {
            expires_in = expires_in;
        }
        public static String getAccess_token () {
            return access_token;
        }
        public static Number getExpires_in () {
            return expires_in;
        }
        public static String getAccessTokenUrl () {
            return getAccessToken + "?grant_type=client_credential&appid=" + AppID + "&secret=" + AppSecret;
//            return getAccessToken + "?grant_type=client_credential&appid=" + AppSecret + "&secret=" + "11";
        }
        public static String getCode2Session (String code) {
            return code2Session + "?grant_type=authorization_code&appid=" + AppID + "&secret=" + AppSecret + "&js_code=" + code;
        }
        public static String getAppID () {
            return AppID;
        }
    }
}
