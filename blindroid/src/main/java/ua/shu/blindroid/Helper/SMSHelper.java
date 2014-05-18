package ua.shu.blindroid.Helper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import ua.shu.blindroid.Activities.MainActivity;

public class SMSHelper {

    public static void readUnreadedSms(MainActivity context)
    {
        Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/inbox"), null, "read = 0", null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            do{
                    String name = ContactsHelper.getContactName(context,cursor.getString(cursor.getColumnIndexOrThrow("address")));
                    String text = cursor.getString(cursor.getColumnIndexOrThrow("body"));

                SpeechHelper.speechText(context, "Отримано повидомлення вид " + name + "з текстом - " + text);
//                SMSHelper.markMessageAsReaded(context, cursor);

            }while(cursor.moveToNext());
        } else {
            SpeechHelper.speechText(context, "Новых повидомленнь не маэ");
        }

     }

//
//    public static void markMessageAsReaded(Context context, Cursor cursor)
//    {
//        String SmsMessageId = cursor.getString(cursor.getColumnIndex("_id"));
//        ContentValues values = new ContentValues();
//        values.put("read", true);
//        context.getContentResolver().update(Uri.parse("content://sms/inbox"), values, "_id=" + SmsMessageId, null);
//    }


}
