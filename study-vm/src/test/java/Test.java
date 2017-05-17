import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by chuangjiangx-chenhao on 2017/4/20.
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(256));
//
//        System.out.println(120000>>8);
//        System.out.println((120000>>8) & 0xff);
        byte[] bytes = new byte[]{0,0,0,1};
        System.out.println(bytes2int(bytes,0,4));
    }
    static int bytes2int(byte[] bytes,int start,int len){
        int end =len+start;
        int sum=0;
        for (int i = 0; i < end; i++) {
            int j = (((int)bytes[i])&0xff);
            j = j << (8*(len--));
            sum = sum + j;
        }
        return sum;
    }
}
