package reflect;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author 崔崔
 * @date 2020/8/13-10:35
 * 小崔hello
 */
public class LiXi20200813 {
    public static void main(String[] args) {
        String str1 = "abcc";
        String  str2  = "acbc";
        char[] chars = str1.toCharArray();
        char[] chars1 = str2.toCharArray();

        Arrays.sort(chars);
        Arrays.sort(chars1);

        String s = String.valueOf(chars);
        String s1 = String.valueOf(chars1);
        if(s.equals(s1)){
            System.out.println("是匹配的");
        }
//        return sb.toString();
//    }
//
//        for(int j =0;j<str2.length();j++){
//            System.out.println(str2.charAt(j));
//        }

       // char k=str1.charAt(i);
       // if(str2.contains("k")){
        //    System.out.println("相等");
        }


        }


