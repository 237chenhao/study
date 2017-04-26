import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by chuangjiangx-chenhao on 2017/4/20.
 */
public class Test {

    public static void main(String[] args) {
        String p ="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        System.out.println("ddd@163.com".matches(p));
        System.out.println(DigestUtils.md5Hex("1"));
    }
}
