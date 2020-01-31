package loomoRaw;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import java.io.IOException;

import loomoRaw.base.BaseService;


public class MainActivity extends Activity {
    private BaseService base;
    private RawMove move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);
       // tourControl = new TourControl();
//        //DEBUG
//        try {
//            tourControl.setupTour(getApplicationContext());
//        } catch (Exception e){
//            e.printStackTrace();
//        }
        initServices();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base:
                String[] start = {"Starting", "1.0f", "1.0f"};
                move = new RawMove(start);
                move.execute();

            case R.id.stop:
                base.disconnect();
              //  tourControl.beginTour(getApplicationContext());
                break;
            default:
                break;
        }
    }

    public void initServices(){
        base = new BaseService(getApplicationContext());
        //speech = new SpeechService(getApplicationContext(), tourControl);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        this.base.disconnect();
       // this.speech.disconnect();
    }
}
