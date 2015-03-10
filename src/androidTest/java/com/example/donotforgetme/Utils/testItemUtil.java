package com.example.donotforgetme.Utils;

import junit.framework.TestCase;

/**
 * Created by ZJGJK03 on 2015/3/10.
 */
public class testItemUtil extends TestCase {
    ItemUtil itemUtil;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        if(itemUtil==null)
            itemUtil=ItemUtil.getInstance();
    }
}
