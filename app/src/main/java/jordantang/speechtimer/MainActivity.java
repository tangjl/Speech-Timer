package jordantang.speechtimer;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button start;
    private Button pause;
    private Button reset;
    private Button submit;
    private TextView timeValue;
    private EditText input;
    private long startTime = 0L;

    private Handler handler = new Handler();

    long time = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeValue = (TextView) findViewById(R.id.viewTime);

        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(updateTimer, 0);
        }
        });

        pause = (Button) findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timeSwapBuff += time;
                handler.removeCallbacks(updateTimer);
            }
        });

        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timeValue.setText("00:00:000");
                time = 0L;
                timeSwapBuff = 0L;
                handler.removeCallbacks(updateTimer);
            }
        });

        submit = (Button) findViewById(R.id.submitTime);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                input = (EditText) findViewById(R.id.editText);
                timeValue.setText(input.getText().toString());
            }
        });
    }

    private Runnable updateTimer = new Runnable() {

            public void run () {
            time = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + time;

            int seconds = (int) (updatedTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timeValue.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds) + ":" + String.format("%03d", milliseconds));
            handler.postDelayed(this, 0);
        }
    };
}
