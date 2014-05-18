package ua.shu.blindroid.Helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import ua.shu.blindroid.Entity.Contact;
import ua.shu.blindroid.Helper.SimilarityAlghoritms.LevenshteinDistance;

public class CallContactHelper {

    public static Contact getMostSimilarContact(Context context, String speechText)
    {
        String[] speechWords = speechText.split(" ", 2);
        if (speechWords.length > 1) {
            String fieldText = speechText.split(" ", 2)[1];
            ArrayList<Contact> contactList = ContactsHelper.getDeviceContactList(context);
            Contact startContact = null;
            double startSimilarity = 0;

            for (Contact contact : contactList) {
                double contactSimilarity = LevenshteinDistance.similarity(fieldText, contact.name);
                Log.e("123", contact.name + " have "  + contactSimilarity + "similarity with '" + fieldText + "'");
                if (contactSimilarity > startSimilarity) {
                    startSimilarity = contactSimilarity;
                    startContact = contact;
                }
            }

            if (startSimilarity < 0.3) return null;
            return startContact;
        }

        return null;

    }

    public static void callContact(Contact contact, Context context) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + contact.phoneNumber));
            context.startActivity(callIntent);
    }
}
