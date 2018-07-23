package com.onenet.datapush.receiver;

/**byte数据处理，工具类
 * Created by chengwengao on 2018/4/22.
 */
public class ByteUtil {
    public static void main(String[] args) {
        //0080138100000001AAFF，0080138d00000001AAFF,2580138d00000000aaff
//        byte[] payload = new byte[]{(byte)0x25,(byte)0x80,(byte)0x13,(byte)0x8d,(byte)0x00,
//                (byte)0x00,(byte)0x00,(byte)0x00,(byte)0xAA,(byte)0xFF};
        String payload = "0080138000000001AAFF";
        getDevCommand(payload);
    }

    public static String getDevCommand(String payload){
        byte[] bb = hexStringToBytes(payload);
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

    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
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
