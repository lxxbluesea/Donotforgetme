package com.example.donotforgetme.Utils;

import com.example.donotforgetme.Entities.Contact;
import com.example.donotforgetme.R;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ZJGJK03 on 2015/2/27.
 */
public class ContactUtil {
    static ContactUtil contactUtil;
    ContentResolver contentResolver;
    /**
     * 获取库Phon表字段*
     */
    private static final String[] PHONES_PROJECTION = new String[]{Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID};
    /**
     * 联系人显示名称*
     */
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    /**
     * 电话号码*
     */
    private static final int PHONES_NUMBER_INDEX = 1;
    /**
     * 头像ID*
     */
    private static final int PHONES_PHOTO_ID_INDEX = 2;
    /**
     * 联系人的ID*
     */
    private static final int PHONES_CONTACT_ID_INDEX = 3;

    /**
     * 手机中的联系人的Uri
     */
    static Uri PHONE_CONTACT = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    /**
     * SIM卡中联系人的Uri
     */
    static Uri SIM_CONTACT = Uri.parse("content://icc/adn");

    /**
     * 构造函数
     */
    private ContactUtil() {
        contentResolver = ApplicationUtil.getContext().getContentResolver();
    }

    public static final int CONTACTRESULTCODE=103;
    /**
     * 获得实例
     *
     * @return
     */
    public static ContactUtil getInstance() {
        if (contactUtil == null)
            contactUtil = new ContactUtil();
        return contactUtil;
    }

    /**
     * 返回所有联系人，返回信息如下：
     * 姓名
     * 号码
     * 照片ID
     * 联系人ID
     *
     * @return
     */
    public List<Contact> getAllContact_FromPhone() {
        List<Contact> contactList = new ArrayList<Contact>();
        Cursor cursor = contentResolver.query(PHONE_CONTACT, PHONES_PROJECTION, null, null, "sort_key COLLATE LOCALIZED asc");
        getContacts(cursor, contactList);
//        try {
//            if (cursor != null && cursor.moveToFirst()) {
//                do {
//                    Contact contact = new Contact();
//                    long tmp1, tmp2;
//                    contact.setDisplayName(cursor.getString(PHONES_DISPLAY_NAME_INDEX));
//                    contact.setNumber(cursor.getString(PHONES_NUMBER_INDEX));
//                    tmp1 = cursor.getLong(PHONES_PHOTO_ID_INDEX);
//                    tmp2 = cursor.getLong(PHONES_CONTACT_ID_INDEX);
//                    //为联系人设置图片
//                    contact.setPhoto(getBitmapFromID(tmp1, tmp2));
//                    contact.setContactID(tmp2);
//
//                    contactList.add(contact);
//                }
//                while (cursor.moveToNext());
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            cursor.close();
//        }

        return contactList;
    }

    /**
     * 获取联系人设置图片
     * 如果联系人图片的话，则显示
     * 否则，使用系统默认的头像
     *
     * @param phote_id
     * @param contact_id
     * @return
     */
    Bitmap getBitmapFromID(long phote_id, long contact_id) {
        Bitmap photo;
        if (phote_id > 0) {

            Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contact_id);
            InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(contentResolver, uri);
            photo = BitmapFactory.decodeStream(input);
            if (photo == null)
                photo = BitmapFactory.decodeResource(ApplicationUtil.getContext().getResources(), R.drawable.pic);

        } else {
            photo = BitmapFactory.decodeResource(ApplicationUtil.getContext().getResources(), R.drawable.pic);
        }

        return photo;
    }

    /**
     * 从SIM卡里获取联系人信息，
     * SIM卡里的联系人只有手机号码和姓名
     *
     * @return
     */
    public List<Contact> getAllContact_FromSIM() {
        List<Contact> contactList = new ArrayList<Contact>();
        Cursor cursor = contentResolver.query(SIM_CONTACT, PHONES_PROJECTION, null, null, "sort_key COLLATE LOCALIZED asc");
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String tmpnum = cursor.getString(PHONES_NUMBER_INDEX);
                    if (TextUtils.isEmpty(tmpnum))
                        continue;
                    Contact contact = new Contact();
                    contact.setDisplayName(cursor.getString(PHONES_DISPLAY_NAME_INDEX));
                    contact.setNumber(tmpnum);
                    contact.setContactID(cursor.getLong(PHONES_CONTACT_ID_INDEX));

                    contactList.add(contact);
                }
                while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return contactList;
    }

    /**
     * 从SIM卡里获取联系人信息，
     * SIM卡里的联系人只有手机号码和姓名
     *
     * @return
     */
    public List<Contact> getAllContact_FromSIM(List<Contact> contactList) {
        //List<Contact> contactList = new ArrayList<Contact>();
        Cursor cursor = contentResolver.query(SIM_CONTACT, PHONES_PROJECTION, null, null, "sort_key COLLATE LOCALIZED asc");
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String tmpnum = cursor.getString(PHONES_NUMBER_INDEX);
                    if (TextUtils.isEmpty(tmpnum))
                        continue;
                    Contact contact = new Contact();
                    contact.setDisplayName(cursor.getString(PHONES_DISPLAY_NAME_INDEX));
                    contact.setNumber(tmpnum);
                    contact.setContactID(cursor.getLong(PHONES_CONTACT_ID_INDEX));

                    contactList.add(contact);
                }
                while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return contactList;
    }

    /**
     * 通过Contact_id来获得联系人
     * @param contact_id
     * @return
     */
    public Contact getContactByID(long contact_id) {
        Contact contact;
        Cursor cursor = contentResolver.query(PHONE_CONTACT, PHONES_PROJECTION, "contact_id=?", new String[]{contact_id + ""}, null);
        contact = getContact(cursor);
        return contact;
    }

    /**
     * 通过姓名来获得联系人
     *
     * @param displayName
     * @return
     */
    public List<Contact> getContactByName(String displayName) {
        List<Contact> contactList = new ArrayList<Contact>();
        Cursor cursor = contentResolver.query(PHONE_CONTACT, PHONES_PROJECTION, "display_name=?", new String[]{displayName}, null);
        getContacts(cursor, contactList);
        return contactList;
    }

    /**
     * 通过号码来获得联系人
     *
     * @param number
     * @return
     */
    public List<Contact> getContactByNumber(String number) {
        List<Contact> contactList = new ArrayList<Contact>();
        Cursor cursor = contentResolver.query(PHONE_CONTACT, PHONES_PROJECTION, "data1=?", new String[]{number}, null);
        getContacts(cursor, contactList);
        return contactList;
    }

    /**
     * 获取单个Contact，这只是把代码剥离出来，以方便复用
     * @param cursor
     * @return
     */
    Contact getContact(Cursor cursor) {
        Contact contact = null;
        try {
            if (cursor != null && cursor.moveToFirst()) {
                long tmp1, tmp2;
                contact = new Contact();
                tmp1 = cursor.getLong(PHONES_PHOTO_ID_INDEX);
                tmp2 = cursor.getLong(PHONES_CONTACT_ID_INDEX);
                contact.setContactID(tmp2);
                contact.setDisplayName(cursor.getString(PHONES_DISPLAY_NAME_INDEX));
                contact.setNumber(cursor.getString(PHONES_NUMBER_INDEX));
                contact.setPhoto(getBitmapFromID(tmp1, tmp2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return contact;
    }

    /**
     * 获取多个Contact，这只是把代码剥离出来，以方便复用
     * @param cursor
     * @return
     */
    List<Contact> getContacts(Cursor cursor,List<Contact> contactList) {
        try {
            if (cursor != null && cursor.moveToFirst()) {
                long tmp1, tmp2;
                do {
                    Contact contact = new Contact();
                    tmp1 = cursor.getLong(PHONES_PHOTO_ID_INDEX);
                    tmp2 = cursor.getLong(PHONES_CONTACT_ID_INDEX);
                    contact.setContactID(tmp2);
                    contact.setDisplayName(cursor.getString(PHONES_DISPLAY_NAME_INDEX));
                    contact.setNumber(cursor.getString(PHONES_NUMBER_INDEX));
                    contact.setPhoto(getBitmapFromID(tmp1, tmp2));
                    contactList.add(contact);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return contactList;
    }

}
