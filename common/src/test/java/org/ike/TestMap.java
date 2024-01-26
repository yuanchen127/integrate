package org.ike;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class TestMap {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<String, String>() {{
            put("id", "不能为空");
            put("count", "不能小于1");
        }};

        final StringBuilder msg =new StringBuilder("数据验证错误: ");
        map.forEach((k, v) -> {
            msg.append(String.format("%s:%s,", k, v));
        });
        System.out.println(msg.toString());
    }
}
