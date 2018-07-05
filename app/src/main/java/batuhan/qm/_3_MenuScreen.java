package batuhan.qm;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class _3_MenuScreen extends AppCompatActivity {

    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        player = MediaPlayer.create(this, R.raw.back_music);
        player.start();

        Button buttonSingle = (Button) findViewById(R.id.singleplayer);
        Button buttonMulti = (Button) findViewById(R.id.multiplayer);
        Button buttonQuizMaker = (Button) findViewById(R.id.quizmaker);
        Button buttonSettings = (Button) findViewById(R.id.settings);
        Button buttonProfile = (Button) findViewById(R.id.profile);

        buttonSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                getCategories();
            }
        });

        buttonMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
               startMulti();
            }
        });

        buttonQuizMaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                quizMaker();
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                settings();
            }
        });

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                goProfile();
            }
        });


    }

    public void getCategories(){
        startActivity(new Intent(this,_A_LevelScreen.class));
    }
    public void startMulti(){
        startActivity(new Intent(this,_4_MultiplayerOptions.class));
    }
    public void goProfile(){
        startActivity(new Intent(this,_6_ProfileScreen.class));
    }
    public void quizMaker(){
        startActivity(new Intent(this,_5_QuizMaker.class));
    }
    public void settings(){
        startActivity(new Intent(this,_7_Settings.class));
    }

}