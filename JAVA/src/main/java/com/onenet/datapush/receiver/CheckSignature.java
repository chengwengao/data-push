package com.onenet.datapush.receiver;

/**校验数字签名
 * Created by chengwengao on 2018/4/24.
 */
public class CheckSignature {

    public static boolean checkSign(String body, String token){
        /*************明文模式  start****************/
        Util.BodyObj obj = Util.resolveBody(body, false);
        if (obj != null) {
            return Util.checkSignature(obj, token);
        }
        return false;
        /*************明文模式  end****************/
    }
}
