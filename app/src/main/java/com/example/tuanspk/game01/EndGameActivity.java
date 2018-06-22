package com.example.tuanspk.game01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndGameActivity extends Activity {

    TextView textViewRight;
    TextView textViewScore;

    Button buttonPlayAgain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_game_layout);

        textViewRight = (TextView) findViewById(R.id.textview_right);
        textViewRight.setText(String.valueOf(MainActivity.iRight));

        textViewScore = (TextView) findViewById(R.id.textview_score);
        textViewScore.setText(String.valueOf(MainActivity.iScore));

        buttonPlayAgain = (Button) findViewById(R.id.button_play_again);
        buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
