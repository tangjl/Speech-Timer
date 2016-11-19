package jordantang.speechtimer;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.EditText;
import android.os.CountDownTimer;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.ToggleButton;

//keep screen on
//history of times
//input validation
//layout stuff

public class MainActivity extends AppCompatActivity {

    private ToggleButton toggle;
    private Button clear;
    private Button reset;
    private Button submit;
    private TextView viewTime;
    private EditText inputMinutes;
    private EditText inputSeconds;
    private Timer timer;
    private long millis = 0;
    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewTime = (TextView) findViewById(R.id.viewTime);
        viewTime.setText("00:00:000");

        timer = new Timer (millis, 1);

        toggle = (ToggleButton) findViewById(R.id.toggle);
        clear = (Button) findViewById(R.id.clear);
        reset = (Button) findViewById(R.id.reset);
        submit = (Button) findViewById(R.id.submitTime);

        toggle.setEnabled(false);
        clear.setEnabled(false);
        reset.setEnabled(false);

        toggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(!(viewTime.getText().toString()).equals("DONE!")) {
                    if (isRunning) {
                        timer.cancel();
                        String[] time = (viewTime.getText().toString()).split(":");
                        int minutes = Integer.parseInt(time[0]);
                        int seconds = Integer.parseInt(time[1]);
                        int milliseconds = Integer.parseInt(time[2]);
                        millis = (minutes * 60 * 1000) + (seconds * 1000) + milliseconds;
                        timer = new Timer(millis, 1);
                        submit.setEnabled(true);
                        inputMinutes.setEnabled(true);
                        inputSeconds.setEnabled(true);
                    } else {
                        timer.start();
                        inputSeconds.setEnabled(false);
                        inputMinutes.setEnabled(false);
                        submit.setEnabled(false);
                    }
                    isRunning = !isRunning;
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timer.cancel();
                millis = 0;
                timer = new Timer (millis, 1);
                viewTime.setText("00:00:000");
                toggle.setChecked(false);
                isRunning = false;
                toggle.setEnabled(true);
                submit.setEnabled(true);
                inputMinutes.setEnabled(true);
                inputSeconds.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timer.cancel();

                int minutes = Integer.parseInt(inputMinutes.getText().toString());
                int seconds = Integer.parseInt(inputSeconds.getText().toString());
                millis = (minutes*60*1000) + (seconds*1000);
                timer = new Timer (millis, 1);

                int seconds2 = (int) (millis/1000);
                int minutes2 = seconds2/60;
                seconds2 = seconds2%60;
                int millis2 = (int) (millis%1000);
                viewTime.setText(String.format("%02d", minutes2) + ":" + String.format("%02d", seconds2)
                        + ":" + String.format("%03d", millis2));
                toggle.setChecked(false);
                isRunning = false;
                toggle.setEnabled(true);
                submit.setEnabled(true);
                inputMinutes.setEnabled(true);
                inputSeconds.setEnabled(true);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timer.cancel();
                inputMinutes = (EditText) findViewById(R.id.minutes);
                inputSeconds = (EditText) findViewById(R.id.seconds);
                inputMinutes.clearFocus();
                inputSeconds.clearFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(inputMinutes.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(inputSeconds.getWindowToken(), 0);

                int minutes = Integer.parseInt(inputMinutes.getText().toString());
                int seconds = Integer.parseInt(inputSeconds.getText().toString());
                millis = (minutes*60*1000) + (seconds*1000);
                timer = new Timer (millis, 1);

                int seconds2 = (int) (millis/1000);
                int minutes2 = seconds2/60;
                seconds2 = seconds2%60;
                int millis2 = (int) (millis%1000);
                viewTime.setText(String.format("%02d", minutes2) + ":" + String.format("%02d", seconds2)
                        + ":" + String.format("%03d", millis2));
                toggle.setEnabled(true);
                clear.setEnabled(true);
                reset.setEnabled(true);

            }
        });
    }

    public class Timer extends CountDownTimer {

        public Timer(long millis, long timeGap) {
            super(millis, timeGap);
        }

        @Override
        public void onTick(long l) {
            int seconds = (int) (l/1000);
            int minutes = seconds/60;
            seconds = seconds%60;
            int milliseconds = (int) (l%1000);
            viewTime.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds)
                    + ":" + String.format("%03d", milliseconds));
        }

        @Override
        public void onFinish() {
            viewTime.setText("DONE!");
            toggle.setEnabled(false);
        }
    }


}