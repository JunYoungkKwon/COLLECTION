package com.eight.collection.ui.writing;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.eight.collection.R;


public class ChangeButtonColor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton radioButton = (RadioButton) findViewById(R.id.writefirst_color_top_text_button);
                switch (view.getId()) {
                    case R.id.writefirst_color_top_selector_red:
                        radioButton.setBackgroundColor(Color.parseColor("#d60f0f"));
                        break;

                }
            }
        };
        Button buttonRed = (Button) findViewById(R.id.writefirst_color_top_selector_red);
        buttonRed.setOnClickListener(onClickListener);
    }

}
