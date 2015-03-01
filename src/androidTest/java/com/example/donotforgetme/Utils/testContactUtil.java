package com.example.donotforgetme.Utils;

import android.util.Log;

import com.example.donotforgetme.Entities.Contact;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by ZJGJK03 on 2015/2/27.
 */
public class testContactUtil extends TestCase {
    ContactUtil contactUtil=null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        contactUtil=ContactUtil.getInstance();
    }

    public void testGetAllContacts_FromPhone()
    {
        if(contactUtil==null)
            fail();
        List<Contact> contactList=contactUtil.getAllContact_FromPhone();
        for (Contact contact:contactList) {
            Log.d("Contact:", contact.toString());
        }
    }

    public void testGetAllContacts_FromSIM()
    {
        if(contactUtil==null)
            fail();
        List<Contact> contactList=contactUtil.getAllContact_FromSIM();
        for (Contact contact:contactList) {
            Log.d("Contact:", contact.toString());
        }
    }

    public void testGetContactByID() {
        if (contactUtil == null)
            fail();
        Contact contact = contactUtil.getContactByID(374);
        if (contact != null)
            Log.d("Contact:", contact.toString());
    }
    public void testGetContactByID_ForEmpty()
    {
        if(contactUtil==null)
            fail();
        Contact contact=contactUtil.getContactByID(-1);
        assertEquals(null,contact);
    }

    public void testGetContactByName() {
        if (contactUtil == null)
            fail();
        List<Contact> contacts = contactUtil.getContactByName("Liuxuexian");
        //assertEquals(2,contacts.size());
        for (Contact contact : contacts)
            Log.d("Contact:", contact.toString());
    }
    public void testGetContactByName_ForEmpty() {
        if (contactUtil == null)
            fail();
        List<Contact> contacts = contactUtil.getContactByName("");
        //assertEquals(0,contacts.size());
    }
    public void testGetContactByName_ForEmpty1() {
        if (contactUtil == null)
            fail();
        List<Contact> contacts = contactUtil.getContactByName("123");
        //assertEquals(0,contacts.size());
    }
    public void testGetContactByNumber() {
        if (contactUtil == null)
            fail();
        List<Contact> contacts = contactUtil.getContactByNumber("15306205750");
        //assertEquals(2,contacts.size());
        for (Contact contact : contacts)
            Log.d("Contact:", contact.toString());
    }
    public void testGetContactByNumber_ForEmpty() {
        if (contactUtil == null)
            fail();
        List<Contact> contacts = contactUtil.getContactByNumber("");
        //assertEquals(0,contacts.size());
    }
    public void testGetContactByNumber_ForEmpty1() {
        if (contactUtil == null)
            fail();
        List<Contact> contacts = contactUtil.getContactByNumber("147");
        //assertEquals(0,contacts.size());
    }
}
