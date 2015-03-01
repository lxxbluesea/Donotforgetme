package com.example.donotforgetme.Utils;

import android.app.Activity;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by ZJGJK03 on 2015/2/27.
 */
public class TextToSpeechUtil extends Activity implements TextToSpeech.OnInitListener {

    int DATA_CHECK_CODE=0;
    TextToSpeech textToSpeech;
    String text="";
    static TextToSpeechUtil textToSpeechUtil;

    private TextToSpeechUtil() {
        checkTTS();
    }

    /**
     * 检测TTS
     */
    void checkTTS()
    {
        Intent check=new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(check, DATA_CHECK_CODE);
    }

    /**
     * 返回TTS检测结果
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==DATA_CHECK_CODE)
        {
            //检测TTS是否正常
            if(resultCode==TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
            {
                //检测通过后，进行TTS初始化
                textToSpeech = new TextToSpeech(this, this);
            }
            else
            {
                //不正常时重新安装
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }

    /**
     * 获取TTS实例
     * @return
     */
    public static TextToSpeechUtil getInstance() {
        if (textToSpeechUtil == null) {
            textToSpeechUtil = new TextToSpeechUtil();
        }
        return textToSpeechUtil;
    }

    /**
     * 播放语音
     * @param text
     * @throws Exception
     */
    public void SpeechChinese(String text) throws Exception {
        if (TextUtils.isEmpty(text)) {
            throw new Exception("需要转换的文本为空");
        }
        if (LangUtil.isChinese(text)) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null);
        } else {
            throw new Exception("你输入的不是中文");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Stop();
    }

    /**
     * 停止TTS
     */
    public void Stop()
    {
        if(textToSpeech!=null)
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    /**
     * TTS初始化
     * @param status
     */
    @Override
    public void onInit(int status) {
        if(status==TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this,"语音包损坏或不支持该语言",Toast.LENGTH_SHORT).show();
            } else {
                //这里可以写上一些初始化成功的代码
            }
        }
        else {
            Toast.makeText(this,"TTS初始化失败",Toast.LENGTH_SHORT).show();
        }
    }


}
