package batuhan.qm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class _6_ProfileScreen extends AppCompatActivity {
    ImageButton button;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth currentFirebaseUser = FirebaseAuth.getInstance();
    private String numberOfUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        DatabaseReference userNo = database.getReference("Users").child("user_no");
        userNo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numberOfUsers = ((String) dataSnapshot.getValue());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        final TextView TextName = (TextView) findViewById(R.id.NameView);
        final TextView TextSurname = (TextView) findViewById(R.id.SurnameView);
        final TextView TextLocation = (TextView) findViewById(R.id.LocationView);
        final TextView TextAge = (TextView) findViewById(R.id.AgeView);
        final TextView TextHighScore = (TextView) findViewById(R.id.HighScoreView);


        final String uid = currentFirebaseUser.getCurrentUser().getUid();

        ProgressDialog progress;
        progress = ProgressDialog.show(_6_ProfileScreen.this,"Please wait !", "Getting ready..", true);

        for(int i=0;i<100;i++){
            String ID=Integer.toString(i);
            final String finalID = ID;
            database.getReference("Users").child("user_"+ID).child("uid").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(uid.equals( dataSnapshot.getValue())){
                        database.getReference("Users").child("user_"+ finalID).child("name").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                TextName.setText((String) dataSnapshot.getValue());
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        });

                        database.getReference("Users").child("user_"+ finalID).child("surname").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                TextSurname.setText((String) dataSnapshot.getValue());
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        });

                        database.getReference("Users").child("user_"+finalID).child("location").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                TextLocation.setText((String) dataSnapshot.getValue());
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        });

                        database.getReference("Users").child("user_"+finalID).child("high_score").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                TextHighScore.setText((String) dataSnapshot.getValue());
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        });

                        database.getReference("Users").child("user_"+finalID).child("age").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                TextAge.setText((String) dataSnapshot.getValue());
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

        progress.dismiss();


    }



}
