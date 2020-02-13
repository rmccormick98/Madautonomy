package loomoRaw;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.app.IntentService;
import java.io.IOException;

import loomoRaw.connectivity.*;
import loomoRaw.base.BaseService;


public class MainActivity extends Activity {
    private BaseService base;
    private RawMove move;
    private InstructionService inst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Inside main");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        initServices();

        Intent loomoInstructions = new Intent(this, InstructionService.class);
        startService(loomoInstructions);

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
        inst = new InstructionService();
        //speech = new SpeechService(getApplicationContext(), tourControl);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        this.base.disconnect();
       // this.speech.disconnect();
    }
}
