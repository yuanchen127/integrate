package org.ike;

import cn.hutool.extra.pinyin.PinyinUtil;
import junit.framework.TestCase;

public class Pinyin extends TestCase {
    public void testMultiPinYin() {
        System.out.printf(PinyinUtil.getFirstLetter("妇科宫颈活检检查(免费)", ""));
    }
}
