package com.example.tuanspk.game01;

import android.content.Intent;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textViewChu;
    TextView textViewMau;
    TextView textViewTime;
    TextView textViewRight;
    TextView textViewScore;

    Button buttonYes;
    Button buttonNo;

    int max = 5;
    String sBangMau[] = new String[max];
    int iMaMau[] = new int[max];

    Random random = new Random();

    int i;
    int j;
    int k;

    public static int iRight;
    public static int iScore;
    int iHightScore[] = new int[2];

    int iTime = 60;

    CountDownTimer countDownTimer;
    CountDownTimer countDownTimer1;

    int iChange;
    int iLienTiep;
    boolean bRight;

    int iBonus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setStartGame();
        setResourceGame();
        setGame();
        setButton();
        setCountDownTimer(60000);
    }

    @Override
    protected void onPause() {
        super.onPause();

        countDownTimer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();

        countDownTimer.start();
    }

    private void setStartGame() {
        iScore = 0;
        iRight = 0;
        iHightScore[0] = 0;
        iHightScore[1] = 0;

        iLienTiep = 0;
        bRight = false;
    }

    public void init() {
        textViewChu = (TextView) findViewById(R.id.textview_chu);
        textViewMau = (TextView) findViewById(R.id.textview_mau);
        textViewTime = (TextView) findViewById(R.id.textview_time);
        textViewRight = (TextView) findViewById(R.id.textview_right);
        textViewScore = (TextView) findViewById(R.id.textview_score);

        buttonYes = (Button) findViewById(R.id.button_yes);
        buttonNo = (Button) findViewById(R.id.button_no);
    }

    private void setButton() {
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                countDownTimer1.cancel();

                if (i == j) {
                    iRight++;
                    if (iLienTiep > 0)
                        iScore += 100 + random.nextInt(iLienTiep * 10) + iBonus;
                    else iScore += 100 + iBonus;
                    iLienTiep++;
                    iChange = 0;
                } else {
                    iLienTiep = 0;
                    iChange++;
                }

                if (iChange > 1) {
                    int l = 0;
                    while (l < 100) {
                        if (random.nextInt(2) == 1)
                            while (i != j)
                                setGame();
                        l++;
                    }
                    if (l == 10) setGame();
                } else setGame();

                textViewRight.setText(String.valueOf(iRight));
                textViewScore.setText(String.valueOf(iScore));
            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                countDownTimer1.cancel();

                if (i != j) {
                    iRight++;
                    if (iLienTiep > 0)
                        iScore += 100 + random.nextInt(iLienTiep * 10) + iBonus;
                    else iScore += 100 + iBonus;
                    iLienTiep++;
                    iChange++;
                } else {
                    iLienTiep = 0;
                    iChange = 0;
                }

                if (iChange > 1) {
                    int l = 0;
                    while (l < 100) {
                        if (random.nextInt(2) == 1)
                            while (i != j)
                                setGame();
                        l++;
                    }
                    if (l == 10) setGame();
                } else setGame();

                textViewRight.setText(String.valueOf(iRight));
                textViewScore.setText(String.valueOf(iScore));
            }
        });
    }

    private void setResourceGame() {
        sBangMau[0] = "Red";
        sBangMau[1] = "Yellow";
        sBangMau[2] = "Green";
        sBangMau[3] = "Blue";
        sBangMau[4] = "Black";

        Resources resources = getResources();
        iMaMau[0] = resources.getColor(R.color.colorRed);
        iMaMau[1] = resources.getColor(R.color.colorYellow);
        iMaMau[2] = resources.getColor(R.color.colorGreen);
        iMaMau[3] = resources.getColor(R.color.colorBlue);
        iMaMau[4] = resources.getColor(R.color.colorBlack);
    }

    private void setGame() {
        i = random.nextInt(max);
        textViewChu.setText(sBangMau[i]);

        j = random.nextInt(max);
        textViewMau.setTextColor(iMaMau[j]);

        k = random.nextInt(max);
        textViewMau.setText(sBangMau[k]);

        setCounDownTimer1();
        countDownTimer1.start();
    }

    private void setCountDownTimer(int iTimer) {
        countDownTimer = new CountDownTimer(iTimer, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                iTime--;
                textViewTime.setText(String.valueOf(iTime));
            }

            @Override
            public void onFinish() {
                iTime--;
                textViewTime.setText(String.valueOf(iTime));

                countDownTimer.cancel();

                onRead();
                if (iRight < iHightScore[0]) iRight = iHightScore[0];
                if (iScore < iHightScore[1]) iScore = iHightScore[1];
                onWrite();

                Intent intent = new Intent(MainActivity.this, EndGameActivity.class);
                startActivity(intent);
            }
        };
    }

    private void setCounDownTimer1() {
        iBonus = 50;
        countDownTimer1 = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                iBonus -= 10;
            }

            @Override
            public void onFinish() {
                iBonus = 0;
            }
        };
    }

    public void onRead() {
        try {
//            InputStream inputStream = getAssets().open("score.txt");
//            byte[] buffer = new byte[10];
//
//            int len = 0;
//            while ((len = inputStream.read(buffer)) > 0) {
//                String sInput = new String(buffer);
//                return Integer.parseInt(sInput);
//            }
//            inputStream.close();

//            InputStream inputStream = getAssets().open("drawable/score.txt");
//            int size = inputStream.available();
//            byte[] buffer = new byte[size];
//            inputStream.read(buffer);
//            String s = new String(buffer);
//            inputStream.close();

            FileInputStream inputStream = openFileInput("rank.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String s = "";

            int l = 0;
            while ((s = reader.readLine()) != null) {
                iHightScore[l] = Integer.parseInt(s.toString());
                l++;
            }
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onWrite() {
        try {
            FileOutputStream outputStream = openFileOutput("rank.txt", 0);

            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write(String.valueOf(iRight) + "\n" + String.valueOf(iScore));
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}