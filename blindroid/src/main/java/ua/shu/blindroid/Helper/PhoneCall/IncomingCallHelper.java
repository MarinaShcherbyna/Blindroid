package ua.shu.blindroid.Helper.PhoneCall;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import ua.shu.blindroid.Activities.MainActivity;
import ua.shu.blindroid.Helper.ContactsHelper;
import ua.shu.blindroid.Helper.SpeechHelper;

/**
 * Created by shu on 19.05.14.
 */
public class IncomingCallHelper {

    public static void callRecieved(MainActivity context, Context recieverContext)
    {

        try {
            // TELEPHONY MANAGER class object to register one listner
            TelephonyManager tmgr = (TelephonyManager) recieverContext
                    .getSystemService(Context.TELEPHONY_SERVICE);

            //Create Listner
            MyPhoneStateListener PhoneListener = new MyPhoneStateListener();
            PhoneListener.context = context;

            // Register listener for LISTEN_CALL_STATE
            tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        } catch (Exception e) {
            Log.e("Phone Receive Error", " " + e);
        }
    }

    private static class MyPhoneStateListener extends PhoneStateListener {
        public MainActivity context;

        public void onCallStateChanged(int state, String incomingNumber) {

            Log.d("MyPhoneListener",state+"   incoming no:"+incomingNumber);

            if (state == 1) {

//                String msg = "New Phone Call Event. Incomming Number : "+incomingNumber;
//                int duration = Toast.LENGTH_LONG;
//                Toast toast = Toast.makeText(context, msg, duration);
//                toast.show();

                String contactName = ContactsHelper.getContactName(context, incomingNumber);
                SpeechHelper.speechText(context, "Тэлеэфонуэ - " + contactName);

            }
        }
    }

}
