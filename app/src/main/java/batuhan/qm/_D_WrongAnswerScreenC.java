package batuhan.qm;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class _D_WrongAnswerScreenC extends _C_CorrectAnswerScreen {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth currentFirebaseUser;
    private String high_score;
    private int intHigh_score;
    private String ID;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_answer_screen);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        player = MediaPlayer.create(this, R.raw.error);
        player.start();

        final TextView highScore=(TextView) findViewById(R.id.highscore);
        final TextView tempScore=(TextView) findViewById(R.id.tempscore);


        for(i = 0; i <10; i++){
            final String ID=Integer.toString(i);

            database.getReference("Users").child("user_"+ID).child("uid").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(uid.equals( (String) dataSnapshot.getValue())){
                        i =10;
                        database.getReference("Users").child("user_"+ ID).child("high_score").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                highScore.setText((String)dataSnapshot.getValue());
                                tempScore.setText(temp_Score);
                                high_score=((String) dataSnapshot.getValue());
                                intHigh_score=Integer.parseInt(high_score);

                                database.getReference("Users").child("user_"+ID).child("temp_score").setValue("5");
                                if(intscore>intHigh_score) {
                                    database.getReference("Users").child("user_" + ID).child("high_score").setValue(Integer.toString(intscore));
                                    Toast.makeText(_D_WrongAnswerScreenC.this,
                                            "NEW HIGH SCORE !!", Toast.LENGTH_LONG).show();
                                }

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        });

                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }

        Button playAgain = (Button) findViewById(R.id.playAgain);
        Button goMenu = (Button) findViewById(R.id.goMenu);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
                ActivityCompat.finishAffinity(_D_WrongAnswerScreenC.this);
            }
        });

        goMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMenu();
                ActivityCompat.finishAffinity(_D_WrongAnswerScreenC.this);
            }
        });

    }

    public void newGame(){
        startActivity(new Intent(this, _B_QuestionScreen.class));
    }
    public void goMenu(){
        startActivity(new Intent(this, _3_MenuScreen.class));
    }

}
