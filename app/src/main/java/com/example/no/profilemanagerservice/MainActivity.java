package com.example.no.profilemanagerservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button startButton;
    Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startButton= (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);

        final Intent smartService = new Intent(this,SmartService.class);


        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            Button button = (Button)v;
            if(button==startButton)
            {
                startService(new Intent(this, SmartService.class));
            }
            else if (button==stopButton)
            {
                stopService(new Intent(this, SmartService.class));
            }
        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}
