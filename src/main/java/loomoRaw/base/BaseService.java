package loomoRaw.base;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.segway.robot.algo.Pose2D;
import com.segway.robot.algo.PoseVLS;
import com.segway.robot.algo.VLSPoseListener;
import com.segway.robot.algo.minicontroller.CheckPoint;
import com.segway.robot.algo.minicontroller.CheckPointStateListener;
import com.segway.robot.algo.minicontroller.ObstacleStateChangedListener;
import com.segway.robot.sdk.base.bind.ServiceBinder;
import com.segway.robot.sdk.locomotion.sbv.Base;
import com.segway.robot.sdk.locomotion.sbv.StartVLSListener;

public class BaseService {

    private static final String TAG = "BaseService";
    private Base base = null;
    private Context context;
    private Handler timehandler;
    private Runnable lastStop = null;
    public static BaseService instance;

    public static BaseService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("LoomoBaseService instance not initialized yet");
        }
        return instance;
    }

    public BaseService(Context context) {
        timehandler = new Handler();
        this.context = context;
        initBase();
        this.instance = this;
    }

    private void initBase(){
        base = Base.getInstance();
        base.bindService(context, new ServiceBinder.BindStateListener() {
            @Override
            public void onBind() {
                Log.d(TAG, "Base bind successful");
                base.setControlMode(Base.CONTROL_MODE_RAW);
                // turn off self balance
                base.setCartMode(false);
            }

            @Override
            public void onUnbind(String reason) {
                Log.d(TAG, "Base bind failed");
            }
        });
    }

    public void move(float linearVelocity, float angularVelocity) {
        setRawControlMode();
        base.setLinearVelocity(linearVelocity);
        base.setAngularVelocity(angularVelocity);

        if (lastStop != null) {
            timehandler.removeCallbacks(lastStop);
            Log.d(TAG, "removed callback to stop");
        }

        lastStop = new Runnable() {
            @Override
            public void run() {
                base.setLinearVelocity(0);
                base.setAngularVelocity(0);
            }
        };


        timehandler.postDelayed(lastStop, 1000);
        Log.d(TAG, "added callback to stop");
    }

    private void setRawControlMode() {
        if (base.isVLSStarted()) {
            Log.d(TAG, "Stopping VLS");
            base.stopVLS();
        }

        if (base.getControlMode() != Base.CONTROL_MODE_RAW) {
            Log.d(TAG, "Setting control mode to: RAW");
            base.setControlMode(Base.CONTROL_MODE_RAW);
        }
    }

    public void disconnect() {
        this.base.unbindService();
    }

}
