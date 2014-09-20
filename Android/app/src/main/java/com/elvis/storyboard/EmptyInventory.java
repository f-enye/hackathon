package com.elvis.storyboard;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by elvis on 9/20/14.
 */
public class EmptyInventory extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory);
        Bundle extras = getIntent().getExtras();
    }
}