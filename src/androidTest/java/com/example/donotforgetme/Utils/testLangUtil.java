package com.example.donotforgetme.Utils;

import junit.framework.TestCase;

/**
 * Created by ZJGJK03 on 2015/2/27.
 */
public class testLangUtil extends TestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    public void testIsChinese_For_Empty() {
        String text = "";
        boolean flag = LangUtil.isChinese(text);
        assertEquals(false, flag);
    }
    public void testIsChinese_For_English() {
        String text = "aaaaa";
        boolean flag = LangUtil.isChinese(text);
        assertEquals(false, flag);
    }
    public void testIsChinese_For_English1() {
        String text = "ａａａａａ";
        boolean flag = LangUtil.isChinese(text);
        assertEquals(false, flag);
    }
    public void testIsChinese_For_Number() {
        String text = "123456";
        boolean flag = LangUtil.isChinese(text);
        assertEquals(false, flag);
    }
    public void testIsChinese_For_Number1() {
        String text = "１２３４５６";
        boolean flag = LangUtil.isChinese(text);
        assertEquals(false, flag);
    }
    public void testIsChinese_For_Others() {
        String text = "!@#$%^&";
        boolean flag = LangUtil.isChinese(text);
        assertEquals(false, flag);
    }
    public void testIsChinese_For_Others1() {
        String text = "！＠＃＄％＾";
        boolean flag = LangUtil.isChinese(text);
        assertEquals(false, flag);
    }
    public void testIsChinese() {
        String text = "你好呀，朋友！";
        boolean flag = LangUtil.isChinese(text);
        assertEquals(true, flag);
    }
    public void testIsChinese1() {
        String text = "你好呀，朋友!";
        boolean flag = LangUtil.isChinese(text);
        assertEquals(true, flag);
    }
    public void testIsChinese2() {
        String text = "你好呀，2个朋友!";
        boolean flag = LangUtil.isChinese(text);
        assertEquals(true, flag);
    }
    public void testIsChinese3() {
        String text = "2你好呀，朋友!";
        boolean flag = LangUtil.isChinese(text);
        assertEquals(true, flag);
    }
    public void testIsChinese4() {
        String text = "@你好呀，朋友!";
        boolean flag = LangUtil.isChinese(text);
        assertEquals(true, flag);
    }
    public void testIsChinese5() {
        String text = "hello 刘学贤!";
        boolean flag = LangUtil.isChinese(text);
        assertEquals(true, flag);
    }
}
