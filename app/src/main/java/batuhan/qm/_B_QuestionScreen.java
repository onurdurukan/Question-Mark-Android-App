package batuhan.qm;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class _B_QuestionScreen extends AppCompatActivity {

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private MediaPlayer player;
    private int random;
    private String questionNo;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private TextView question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_screen);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        player = MediaPlayer.create(this, R.raw.question);
        player.start();

        button1 = (Button) findViewById(R.id.answer1);
        button2 = (Button) findViewById(R.id.answer2);
        button3 = (Button) findViewById(R.id.answer3);
        button4 = (Button) findViewById(R.id.answer4);

        question = (TextView) findViewById(R.id.question);

        Random rand=new Random();
        random=1+rand.nextInt(80);
        questionNo=Integer.toString(random);

        mDatabase.getReference("Questions").child(questionNo).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                question.setText((String)dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

       mDatabase.getReference("Questions").child(questionNo).child("answer1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                button1.setText((String)dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        mDatabase.getReference("Questions").child(questionNo).child("answer2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                button2.setText((String)dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

       mDatabase.getReference("Questions").child(questionNo).child("answer3").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                button3.setText((String)dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        mDatabase.getReference("Questions").child(questionNo).child("answer4").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                button4.setText((String)dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.getReference("Questions").child(questionNo).child("correct").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(button1.getText().equals(dataSnapshot.getValue()))
                            CorrectAnswer();
                        else
                            WrongAnswer();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}});
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.getReference("Questions").child(questionNo).child("correct").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(button2.getText().equals(dataSnapshot.getValue()))
                            CorrectAnswer();
                        else
                            WrongAnswer();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}});

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.getReference("Questions").child(questionNo).child("correct").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(button3.getText().equals(dataSnapshot.getValue()))
                            CorrectAnswer();
                        else
                            WrongAnswer();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}});

            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.getReference("Questions").child(questionNo).child("correct").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(button4.getText().equals(dataSnapshot.getValue()))
                            CorrectAnswer();
                        else
                            WrongAnswer();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}});

            }
        });

    }

    public void CorrectAnswer(){
        startActivity(new Intent(this, _C_CorrectAnswerScreen.class));
    }
    public void WrongAnswer(){
        startActivity(new Intent(this, _D_WrongAnswerScreenC.class));
    }
}
