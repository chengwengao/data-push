package com.onenet.datapush.receiver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**数据处理，伪代码，仅做参考！！！
 * todo:mirth获取ip和port传参,equip_type先默认烟感
 * Created by chengwengao on 2018/4/22.
 */
public class DataHandle {

    public void dataHandle(String obj, String ipAddress, String port){
        String equip_type = "1";
        String area_id = "107"; //todo:OneNET平台同步的设备默认安装区域为107
        String install_origin = "3"; //todo:OneNET平台同步的设备默认安装来源为3
//        String deviceInfoList = HandleJson.handleObj(obj);
        List<DeviceInfo> deviceInfoList = new ArrayList<>();
        for (DeviceInfo deviceInfo:deviceInfoList){
            int type = deviceInfo.getType();
            Date time = deviceInfo.getTime();
            //通过请求OneNET平台获取设备编号
            Long devCode = HttpClientHandle.getDevCodeByJson(deviceInfo.getDev_id());
            if (2 == type){
                int status = deviceInfo.getStatus();
                //上下线
                if (1 == status){
                    //上线
                    //向设备表添加数据
                    String sql = "insert into equip_info(area_id, equip_code, create_date, install_origin, update_date, del_flag, status) " +
                            "values('"+area_id+"','"+devCode+"','"+time+"','"+install_origin+"','"+time+"','1','0')";
                    Conn.update(sql);
                }else if(2 == status){
                    //下线
                    String sql = "update equip_info set status = '3', update_date='"+time+"' where equip_code = '"+devCode+"'";
                    Conn.update(sql);
                }else{
                    new RuntimeException("未知设备上下线类型!");
                }
            }else if (1 == type){
                //数据点消息
                byte[] payload = deviceInfo.getValue().getBytes();
                //根据payload查看百思威协议解析数据点信息
                String commType = ByteUtil.getDevCommand(payload);
                String amp_alarm_info_sql = "insert into amp_alarm_info_sql(alarm_type, status, alarm_id, equip_type, " +
                        "remind_times," + "equip_id, alarm_time, alarm_reason_type, create_time, update_time, del_flag) values";
                String sensor_data_sql = "insert into sensor_data(ipAddress, port, rawContent, device_Code, equip_type, " +
                        "alarmContent, upload_time" + ", alarm_id) values";
                String alarm_id_uuid = UUID.randomUUID().toString();

                if ("0001".equals(commType)){
                    //设备开机
                    sensor_data_sql += "('"+ipAddress+"','"+port+"','"+deviceInfo.getValue()+"','"+devCode+"','"+equip_type+
                            "','设备开机','"+deviceInfo.getTime()+"',"+null+")";
                    Conn.update(sensor_data_sql);
                }else if("0111".equals(commType)){
                    //烟雾报警,报警类型默认为"真实火灾报警"
                    amp_alarm_info_sql += "(10001,2,'"+alarm_id_uuid+"','"+equip_type+"',1,'"+devCode+"','"+deviceInfo.getTime()
                            +"','3','"+deviceInfo.getTime()+"','"+deviceInfo.getTime()+"','0')";
                    sensor_data_sql += "('"+ipAddress+"','"+port+"','"+deviceInfo.getValue()+"','"+devCode+"','"+equip_type+
                            "','烟雾报警','"+deviceInfo.getTime()+"','"+alarm_id_uuid+"')";
                    Conn.update(amp_alarm_info_sql);
                    Conn.update(sensor_data_sql);
                }else if("0110".equals(commType)){
                    //烟雾消警
                    sensor_data_sql += "('"+ipAddress+"','"+port+"','"+deviceInfo.getValue()+"','"+devCode+"','"+equip_type+
                            "','烟雾消警','"+deviceInfo.getTime()+"',"+null+")";
                    Conn.update(sensor_data_sql);
                }else if("0010".equals(commType)){
                    //设备心跳
                    sensor_data_sql += "('"+ipAddress+"','"+port+"','"+deviceInfo.getValue()+"','"+devCode+"','"+equip_type+
                            "','设备心跳','"+deviceInfo.getTime()+"',"+null+")";
                    Conn.update(sensor_data_sql);
                }else if("0101".equals(commType)){
                    //低电压报警,报警类型默认为"日常生活报警"
                    amp_alarm_info_sql += "(10003,2,'"+alarm_id_uuid+"','"+equip_type+"',1,'"+devCode+"','"+deviceInfo.getTime()
                            +"','1','"+deviceInfo.getTime()+"','"+deviceInfo.getTime()+"','0')";
                    sensor_data_sql += "('"+ipAddress+"','"+port+"','"+deviceInfo.getValue()+"','"+devCode+"','"+equip_type+
                            "','低电压报警','"+deviceInfo.getTime()+"','"+alarm_id_uuid+"')";
                    Conn.update(amp_alarm_info_sql);
                    Conn.update(sensor_data_sql);
                }else if("1100".equals(commType)){
                    //底座低电压报警，报警类型默认为"日常生活报警"
                    amp_alarm_info_sql += "(10003,2,'"+alarm_id_uuid+"','"+equip_type+"',1,'"+devCode+"','"+deviceInfo.getTime()
                            +"','1','"+deviceInfo.getTime()+"','"+deviceInfo.getTime()+"','0')";
                    sensor_data_sql += "('"+ipAddress+"','"+port+"','"+deviceInfo.getValue()+"','"+devCode+"','"+equip_type+
                            "','底座低电压报警','"+deviceInfo.getTime()+"','"+alarm_id_uuid+"')";
                    Conn.update(amp_alarm_info_sql);
                    Conn.update(sensor_data_sql);
                }else if("0100".equals(commType)){
                    //测试报警,报警类型默认为"演示测试报警"
                    amp_alarm_info_sql += "(10003,2,'"+alarm_id_uuid+"','"+equip_type+"',1,'"+devCode+"','"+deviceInfo.getTime()
                            +"','2','"+deviceInfo.getTime()+"','"+deviceInfo.getTime()+"','0')";
                    sensor_data_sql += "('"+ipAddress+"','"+port+"','"+deviceInfo.getValue()+"','"+devCode+"','"+equip_type+
                            "','测试报警','"+deviceInfo.getTime()+"','"+alarm_id_uuid+"')";
                    Conn.update(amp_alarm_info_sql);
                    Conn.update(sensor_data_sql);
                }else if ("1101".equals(commType)){
                    //探头失联
                    amp_alarm_info_sql += "(10003,2,'"+alarm_id_uuid+"','"+equip_type+"',1,'"+devCode+"','"+deviceInfo.getTime()
                            +"','1','"+deviceInfo.getTime()+"','"+deviceInfo.getTime()+"','0')";
                    sensor_data_sql += "('"+ipAddress+"','"+port+"','"+deviceInfo.getValue()+"','"+devCode+"','"+equip_type+
                            "','探头失联','"+deviceInfo.getTime()+"','"+alarm_id_uuid+"')";
                    Conn.update(amp_alarm_info_sql);
                    Conn.update(sensor_data_sql);
                }else{
                    System.out.println("暂不处理！");
                }

            }else{
                new RuntimeException("未知消息类型!");
            }
        }
    }
}
