package ua.shu.blindroid.Helper;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import ua.shu.blindroid.Entity.Contact;

public class CallContactHelper {

    public static Contact getMostSimilarContact(Context context, String speechText)
    {
        String fieldText = speechText.split(" ", 2)[1];
        ArrayList<Contact> contactList = ContactsHelper.getDeviceContactList(context);
        Contact startContact = null;
        double similarity = 0;

        for (Contact contact : contactList) {
            double contactSimilarity = LevenshteinDistance.similarity(fieldText, contact.name);
            Log.e("123", contact.name + " have "  + contactSimilarity + "similarity with '" + fieldText + "'");
            if (contactSimilarity > similarity) {
                similarity = contactSimilarity;
                startContact = contact;
            }
        }

        return startContact;
    }

}
