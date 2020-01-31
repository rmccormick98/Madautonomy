package loomoRaw;

import android.util.Log;

import static java.util.concurrent.TimeUnit.SECONDS;


public class RawMove {
    private static final String TAG = "RawMove";


    private String[] message;

    public RawMove(String[] message){
        this.message = message;
    }

    public void execute() {
        Log.i(TAG, "move speed: " + message[1]);
        Log.i(TAG, "move angle: " + message[2]);



        try {
            float newSpeed = 0.3f * Float.valueOf(message[1]); // was 1.0f
            float newAngle = Float.valueOf(message[2]);


            while(true) {
                newSpeed = 0.0F;
                newAngle = 0.4F;
                loomoRaw.base.BaseService.getInstance().move(newSpeed, newAngle);
                Log.i(TAG, "Movement 1");
            }
           // SECONDS.sleep(1);

          /*  newSpeed = 1.0F;
            newAngle = 0.0F;
            loomoRaw.base.BaseService.getInstance().move(newSpeed, newAngle);
            Log.i(TAG, "Movement 2");

            SECONDS.sleep(1);

            newSpeed = -0.4F;
            newAngle = -0.4F;
            loomoRaw.base.BaseService.getInstance().move(newSpeed, newAngle);
            Log.i(TAG, "Movement 3");

            SECONDS.sleep(1);
*/



        } catch (Exception e) {
            Log.e(TAG, "Got Exception during HEAD command", e);
        }
    }


}
