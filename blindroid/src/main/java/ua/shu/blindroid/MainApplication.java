package ua.shu.blindroid;

import android.app.Application;

import ua.shu.blindroid.Activities.MainActivity;

public class MainApplication extends Application {

    private MainActivity mainActivity;

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
