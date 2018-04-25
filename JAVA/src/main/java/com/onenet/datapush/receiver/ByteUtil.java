package com.onenet.datapush.receiver;

/**byte数据处理，工具类
 * Created by chengwengao on 2018/4/22.
 */
public class ByteUtil {
    public static void main(String[] args) {
        //0080138100000001AAFF
        byte[] payload = new byte[]{(byte)0x00,(byte)0x80,(byte)0x13,(byte)0x84,(byte)0x00,
                (byte)0x00,(byte)0x00,(byte)0x01,(byte)0xAA,(byte)0xFF};
        getDevCommand(payload);
    }

    public static String getDevCommand(String payload){
        byte[] bb = Base16Encoder.decode(payload);
        return getDevCommand(bb);
    }

    public static String getDevCommand(byte[] payload){

        byte bt = payload[3];

        //百思威协议取第3位的低4位
        StringBuffer sb = new StringBuffer();
        sb.append(getBit(bt, 3)).append(getBit(bt, 2)).append(getBit(bt, 1)).append(getBit(bt, 0));

        System.out.println(byteArrayToHex(payload));
        System.out.println(sb.toString());

        return sb.toString();


    }

    public static int getBit(int n, int k) {
        return (n >> k) & 1;
    }

    public static String byteToBit(byte b) {
        return ""
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

}
