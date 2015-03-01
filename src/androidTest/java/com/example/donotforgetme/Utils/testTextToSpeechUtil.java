package com.example.donotforgetme.Utils;

import junit.framework.TestCase;

/**
 * Created by ZJGJK03 on 2015/2/27.
 */
public class testTextToSpeechUtil extends TestCase {
    TextToSpeechUtil textToSpeechUtil;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        textToSpeechUtil=TextToSpeechUtil.getInstance();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSpeechChinese_For_empty()
    {
        Throwable ex=null;
        String text="";
        try
        {
            textToSpeechUtil.SpeechChinese(text);
            fail();
        }catch (Exception e)
        {
            e.printStackTrace();
            ex=e;
        }
        assertEquals(Exception.class,ex.getClass());
        assertEquals(ex.getMessage(),"需要转换的文本为空");
    }
    public void testSpeechChinese_For_english()
    {
        Throwable ex=null;
        String text="hello world";
        try
        {
            textToSpeechUtil.SpeechChinese(text);
            fail();
        }catch (Exception e)
        {
            e.printStackTrace();
            ex=e;
        }
        assertEquals(Exception.class,ex.getClass());
        assertEquals(ex.getMessage(),"你输入的不是中文");
    }
    public void testSpeechChinese_For_numbers()
    {
        Throwable ex=null;
        String text="123133";
        try
        {
            textToSpeechUtil.SpeechChinese(text);
            fail();
        }catch (Exception e)
        {
            e.printStackTrace();
            ex=e;
        }
        assertEquals(Exception.class,ex.getClass());
        assertEquals(ex.getMessage(),"你输入的不是中文");
    }
    public void testSpeechChinese_For_others()
    {
        Throwable ex=null;
        String text="!@#$^^&*&^%$";
        try
        {
            textToSpeechUtil.SpeechChinese(text);
            fail();
        }catch (Exception e)
        {
            e.printStackTrace();
            ex=e;
        }
        assertEquals(Exception.class,ex.getClass());
        assertEquals(ex.getMessage(),"你输入的不是中文");
    }
    public void testSpeechChinese()
    {
        String text="hello,你们好,2个朋友！！！";
        try
        {
            textToSpeechUtil.SpeechChinese(text);
        }catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }
    }


}
