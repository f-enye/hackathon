package com.elvis.storyboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by elvis on 9/20/14.
 */
public class Inventory extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory);
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            ListView results_list = (ListView) findViewById(R.id.resultsList);
            results_list.setVisibility(View.GONE);
            LinearLayout linearLayout = (LinearLayout)findViewById(R.id.inventory_layout);
            TextView encouragement = new TextView(this);
            encouragement.setText("Nothing uploaded yet, give it a shot!");
            linearLayout.addView(encouragement);
        }
        else{
            //do things here.
        }

        Button create = (Button) findViewById(R.id.create_button);
        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateRecording.class);
                startActivity(intent);
            }
        });
    }
}