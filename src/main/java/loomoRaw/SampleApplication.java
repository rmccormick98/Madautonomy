package loomoRaw;

import android.app.Application;
import android.content.Context;

public class SampleApplication extends Application {

    static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
