package com.onenet.datapush.receiver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**json格式数据处理
 * Created by chengwengao on 2018/4/22.
 */
public class HandleJson {
    public static void main(String[] args) {
//        String obj = "{\"msg\":{\"at\":1466133706841,\"type\":1,\"ds_id\":\"datastream_id\",\"value\":42,\"dev_id\":2016617},\"msg_signature\":\"message signature\",\"nonce\":\"abcdefgh\"}";
        String obj = "{\"msg\":[{\"at\":\"1466133706841\",\"type\":1,\"ds_id\":\"datastream_id\",\"value\":42,\"dev_id\":2016617},{\"at\":\"1466133706000\",\"type\":1,\"ds_id\":\"datastream_id\",\"value\":33,\"dev_id\":2017618}],\"msg_signature\":\"message signature\",\"nonce\":\"abcdefgh\"}";
        handleObj(obj);

    }

    //解析json
    public static String handleObj(String obj){

        JSONObject object = JSONObject.parseObject(obj);
        List<DeviceInfo> deviceInfoList = new ArrayList<>();
        if(object.get("msg") instanceof JSONArray){ //批量点上传
            JSONArray msgs = (JSONArray)object.get("msg");
            for (int i=0;i<msgs.size();i++){
                DeviceInfo deviceInfo = jsonPrint(msgs.getJSONObject(i));
                deviceInfoList.add(deviceInfo);
            }
        }else if(object.get("msg") instanceof JSONObject){  //单条数据上传
            DeviceInfo deviceInfo = jsonPrint((JSONObject)object.get("msg"));
            deviceInfoList.add(deviceInfo);
        }

        return JSON.toJSONString(deviceInfoList);

    }

    public static DeviceInfo jsonPrint(JSONObject json){
        int type = Integer.valueOf(json.get("type").toString());
        if (1 == type){  //数据点消息
            Long dev_id = Long.valueOf(json.get("dev_id").toString());
            String ds_id = json.get("ds_id").toString();
            Date time = millSeconds2Date(Long.valueOf(json.get("at").toString()));
            String value = json.get("value").toString();
            DeviceInfo deviceInfo = new DeviceInfo(type, dev_id, ds_id, time, value);

            return deviceInfo;
        }else if(2 == type){ //上下线
            Long dev_id = Long.valueOf(json.get("dev_id").toString());
            int status = Integer.valueOf(json.get("status").toString());
            int login_type = Integer.valueOf(json.get("login_type").toString());
            Date time = millSeconds2Date(Long.valueOf(json.get("at").toString()));
            DeviceInfo deviceInfo = new DeviceInfo(type, dev_id, time, status, login_type);
            return deviceInfo;
        }else{
            return null;
        }
    }

    //毫秒转日期
    public static Date millSeconds2Date(Long millSeconds){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millSeconds);
        Date date = c.getTime();
        System.out.println(sdf.format(date));
        return date;
    }

    //批量msg
    public static void createJsonBatch(){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("msg_signature","message signature");
        jsonObject.put("nonce","abcdefgh");

        JSONArray jsonArray = new JSONArray();

        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("type",1);
        jsonObject1.put("dev_id",2016617);
        jsonObject1.put("ds_id","datastream_id");
        jsonObject1.put("at","1466133706841");
        jsonObject1.put("value",42);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("type",1);
        jsonObject2.put("dev_id",2017618);
        jsonObject2.put("ds_id","datastream_id");
        jsonObject2.put("at","1466133706000");
        jsonObject2.put("value",33);

        List list = new ArrayList();
        list.add(jsonObject1);
        list.add(jsonObject2);

        jsonObject.put("msg",list);

        jsonArray.add(jsonObject);

        System.out.println(jsonArray.toJSONString());
    }

    //单条msg
    public static void createJsonSingle(){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("msg_signature","message signature");
        jsonObject.put("nonce","abcdefgh");

        JSONArray jsonArray = new JSONArray();

        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("type",1);
        jsonObject1.put("dev_id",2016617);
        jsonObject1.put("ds_id","datastream_id");
        jsonObject1.put("at","1466133706841");
        jsonObject1.put("value",42);

        jsonObject.put("msg",jsonObject1);

        jsonArray.add(jsonObject);

        System.out.println(jsonArray.toJSONString());
    }

//    public static void test(){
//        String body = "{\"msg\":{\"at\":1466133706841,\"type\":1,\"ds_id\":\"datastream_id\",\"value\":42,\"dev_id\":2016617},\"msg_signature\":\"message signature\",\"nonce\":\"abcdefgh\"}";
//        Util.BodyObj obj = Util.resolveBody(body, false);
//        System.out.println("data receive:  body Object --- " +obj);
//        if (obj != null){
//                System.out.println("data receive: content" + obj.toString());
//        }else {
//            System.out.println("data receive: body empty error");
//        }
//    }
}

class DeviceInfo{
    private int type;
    private Long dev_id;
    private String ds_id;
    private Date time;
    private String value;
    private int status;
    private int login_type;

    public DeviceInfo(int type, Long dev_id, Date time, int status, int login_type) {
        this.type = type;
        this.dev_id = dev_id;
        this.time = time;
        this.status = status;
        this.login_type = login_type;
    }

    public DeviceInfo(int type, Long dev_id, String ds_id, Date time, String value) {
        this.type = type;
        this.dev_id = dev_id;
        this.ds_id = ds_id;
        this.time = time;
        this.value = value;
    }

    public String getDs_id() {
        return ds_id;
    }

    public void setDs_id(String ds_id) {
        this.ds_id = ds_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getDev_id() {
        return dev_id;
    }

    public void setDev_id(Long dev_id) {
        this.dev_id = dev_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLogin_type() {
        return login_type;
    }

    public void setLogin_type(int login_type) {
        this.login_type = login_type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
