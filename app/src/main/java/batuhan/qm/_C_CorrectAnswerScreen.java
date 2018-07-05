package batuhan.qm;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class _C_CorrectAnswerScreen extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public String temp_Score;
    public int intscore;
    public FirebaseAuth currentFirebaseUser = FirebaseAuth.getInstance();
    public  final String uid = currentFirebaseUser.getCurrentUser().getUid();
    public MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct_answer_screen);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        player = MediaPlayer.create(this, R.raw.applause);
        player.start();

        final TextView score=(TextView) findViewById(R.id.temp_score);
        for(final int[] i = {0}; i[0] <100; i[0]++){
            String ID=Integer.toString(i[0]);
            final String finalID = ID;
            database.getReference("Users").child("user_"+ID).child("uid").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(uid.equals( (String) dataSnapshot.getValue())){
                        database.getReference("Users").child("user_"+ finalID).child("temp_score").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                temp_Score=((String) dataSnapshot.getValue());
                                intscore = Integer.parseInt(temp_Score);
                                intscore=intscore+10;
                                temp_Score=Integer.toString(intscore);

                                score.setText(temp_Score);
                                database.getReference("Users").child("user_"+finalID).child("temp_score").setValue(temp_Score);

                                i[0] =100;
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

        Button cont_question = (Button) findViewById(R.id.cont);

        cont_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartIntent();
                ActivityCompat.finishAffinity(_C_CorrectAnswerScreen.this);
            }
        });

    }

    public void StartIntent(){
        startActivity(new Intent(this, _B_QuestionScreen.class));
    }
}
