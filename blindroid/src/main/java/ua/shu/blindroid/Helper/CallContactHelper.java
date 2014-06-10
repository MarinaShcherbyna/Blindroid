package ua.shu.blindroid.Helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import ua.shu.blindroid.Entity.Contact;
import ua.shu.blindroid.Helper.SimilarityAlghoritms.JaroWinklerDistance;
import ua.shu.blindroid.Helper.SimilarityAlghoritms.LevenshteinDistance;

public class CallContactHelper {

    public static Contact getMostSimilarContact(Context context, String speechText)
    {
        String[] speechWords = speechText.split(" ", 2);
        if (speechWords.length > 1) {
            String fieldText = speechText.split(" ", 2)[1];
            ArrayList<Contact> contactList = ContactsHelper.getDeviceContactList(context);

            Contact startContact = null,jaroCurrFunc = null;
            double startSimilarity = 0;
            double jaroSimilarity = 0;

            for (Contact contact : contactList) {
                double contactSimilarity = LevenshteinDistance.similarity(fieldText, contact.name);

                if (contactSimilarity > startSimilarity) {
                    startSimilarity = contactSimilarity;
                    startContact = contact;
                }

                double jaroFuncSimilarity = JaroWinklerDistance.getSimilarity(fieldText, contact.name);
                if (jaroSimilarity < jaroFuncSimilarity) {
                    jaroCurrFunc = contact;
                    jaroSimilarity = jaroFuncSimilarity;
                }

            }

            Log.e("123", "JaroResult function - " + jaroCurrFunc.name + "  " + jaroSimilarity);
            Log.e("123", "Levensteint function - " + startContact.name + "  " + startSimilarity);

            if (jaroSimilarity > startSimilarity) return jaroCurrFunc;
            if (jaroSimilarity < startSimilarity) return startContact;

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
