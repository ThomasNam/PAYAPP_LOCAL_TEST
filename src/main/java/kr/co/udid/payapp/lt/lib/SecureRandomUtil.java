package kr.co.udid.payapp.lt.lib;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by sehong on 2016-05-18.
 */
public class SecureRandomUtil
{

    public static String getKey(int size) {

        String result = "";
        SecureRandom secureRandom = null;

        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        while (true) {
            //난수 발생(아스키 코드 0~z까지)
            int num = 48 + (int) (secureRandom.nextDouble() * 75);

            //숫자 || 대문자 || 소문자 일경우만
            if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                result = result + (char) num;
            }
            //설정한 크기까지
            if (result.length() == size) {
                break;
            }
        }

        return result;
    }
}
