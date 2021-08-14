package reflect;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 崔崔
 * @date 2020/8/13-10:35
 * 小崔hello
 */
public class LiXi {
    public static void main(String[] args) {
        String a ="abc";
        char c = a.charAt(0);
        System.out.println(c);
        char data[] = {'a', 'b', 'c'};
        System.out.println(data+"data");

        Map<String, String> map = new HashMap<String, String>();
        map.put("error", "1");
        map.put("msg", "系统错误");
        String jsonString = JSONObject.toJSONString(map);
        System.out.println(jsonString);
        String json = "[{\"age\":18,\"name\":\"张三\"},{\"age\":17,\"name\":\"李四\"}]";
        //    List<Student> list = JSONObject.parseArray(json, Student.class);
        //   System.out.println(list);

        int n = 5;         // 5 个元素
        String[] name = new String[n];
        for (int i = 0; i < n; i++) {
            name[i] = String.valueOf(i);
        }
        List<String> list = Arrays.asList(name);
        System.out.println();
        for (String li : list) {
            String str = li;
            System.out.print(str + " ");


        }

    }
}
