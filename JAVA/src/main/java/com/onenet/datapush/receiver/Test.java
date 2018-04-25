package com.onenet.datapush.receiver;

/**
 * Created by chengwengao on 2018/4/23.
 */
public class Test {

    public static void main(String[] args) {
        String payload = "0080138400000001AAFF";
        System.out.println(ByteUtil.getDevCommand(payload));

//        String body = "{\"msg\":{\"at\":1524391176698,\"login_type\":10,\"type\":2,\"dev_id\":52454780,\"status\":1},\"msg_signature\":\"Sg1GDi2WC02Kday0ap0Ejw==\",\"nonce\":\"F6Fb7@(&\"}";
//        Util.BodyObj obj = Util.resolveBody(body, false);
//        boolean flag = Util.checkSignature(obj, "whBx2ZwAU5LTYIKOLj1MPx56QRe3OsG");
//        System.out.println(obj);
//        System.out.println(flag);
//        System.out.println("ok");

//        String body = "{\n" +
//                "    \"msg\":{\n" +
//                "        \"at\":1524391188159,\n" +
//                "        \"type\":1,\n" +
//                "        \"ds_id\":\"3200_0_5750\",\n" +
//                "        \"value\":\"0080138100000001AAFF\",\n" +
//                "        \"dev_id\":52454780\n" +
//                "    },\n" +
//                "    \"msg_signature\":\"AmFu5EYjbooRnJtYdPxl5A==\",\n" +
//                "    \"nonce\":\"qP*gGqk4\"\n" +
//                "}\n";
//        System.out.println(HandleJson.handleObj(body));
    }

}
