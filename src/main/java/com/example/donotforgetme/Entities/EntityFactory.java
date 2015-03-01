package com.example.donotforgetme.Entities;

/**
 * Created by ZJGJK03 on 2015/2/27.
 */
public class EntityFactory  implements FactoryInterface {
    @Override
    public SMSInfo getSms() {
        return new SMSInfo();
    }

    @Override
    public Contact getContact() {
        return new Contact();
    }
}
