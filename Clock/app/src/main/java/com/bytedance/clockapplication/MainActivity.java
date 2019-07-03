package com.bytedance.clockapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bytedance.clockapplication.widget.Clock;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private View mRootView;
    private static final String TAG = "MainActivity";
    private Clock mClockView;
    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case 1:
                    mClockView.invalidate();
                    Log.d(TAG, "handleMessage() called with: msg = [" + msg + "]");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootView = findViewById(R.id.root);
        mClockView = findViewById(R.id.clock);

        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClockView.setShowAnalog(!mClockView.isShowAnalog());
            }
        });

        final Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
                    Log.d(TAG, "run() called");
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    Log.e(TAG, "run: ", e);
                }
                }
            }
        });
        thread.start();

    }

    @Override
    protected void onStart(){
        super.onStart();
    }
}
