package batuhan.qm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class _4_MultiplayerOptions extends AppCompatActivity {

    private Button[] NAMES;
    private String[] UIDs;

    private String uid;

    private FirebaseAuth currentFirebaseUser=FirebaseAuth.getInstance();
    public String userID = currentFirebaseUser.getCurrentUser().getUid();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_options);

        NAMES = new Button[5];
        UIDs = new String[5];

        Button NAME1 = (Button) findViewById(R.id.fname1);
        Button NAME2 = (Button) findViewById(R.id.fname2);
        Button NAME3 = (Button) findViewById(R.id.fname3);
        Button NAME4 = (Button) findViewById(R.id.fname4);
        Button NAME5 = (Button) findViewById(R.id.fname5);

        NAMES[0] = NAME1;
        NAMES[1] = NAME2;
        NAMES[2] = NAME3;
        NAMES[3] = NAME4;
        NAMES[4] = NAME5;


        for (int i = 0; i < 5; i++) {
            String IDString = Integer.toString(i);

            setText(i, IDString);
            setUID(i, IDString);
        }

        NAME1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid=UIDs[0];
                start(uid);
                goLevels();
            }
        });

        NAME2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid=UIDs[1];
                start(uid);
                goLevels();
            }
        });

        NAME3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid=UIDs[2];
                start(uid);
                goLevels();
            }
        });

        NAME4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid=UIDs[3];
                start(uid);
                goLevels();
            }
        });

        NAME5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid=UIDs[4];
                start(uid);
                goLevels();
            }
        });

    }

    public void setText(final int index, String ID) {
        DatabaseReference ref = database.getReference("Users").child("user_" + ID).child("name");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                NAMES[index].setText((String) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public void setUID(final int index,String ID){
        DatabaseReference ref = database.getReference("Users").child("user_" + ID).child("uid");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UIDs[index]=((String) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public String findUserMail(final String uid){
        final String[] email = new String[1];
        for(final int[] i = {0}; i[0] <100; i[0]++){
            String ID=Integer.toString(i[0]);
            final String finalID = ID;

            database.getReference("Users").child("user_"+ID).child("uid").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (uid.equals((String) dataSnapshot.getValue())) {
                        database.getReference("Users").child("user_" + finalID).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                email[0] = ((String) dataSnapshot.getValue());
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

        return email[0];
    }

    public String findUserName(final String uid){
        final String[] name = new String[1];
        final String[] surname = new String[1];

        for(final long[] i = {0}; i[0] <100; i[0]++){
            String ID=Long.toString(i[0]);
            final String finalID = ID;

            database.getReference("Users").child("user_"+ID).child("uid").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (uid.equals((String) dataSnapshot.getValue())) {
                        database.getReference("Users").child("user_" + finalID).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                name[0] = ((String) dataSnapshot.getValue());

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                        database.getReference("Users").child("user_" + finalID).child("surname").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                surname[0] = ((String) dataSnapshot.getValue());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        String nameSurname=name[0]+" "+surname[0];

        return nameSurname;
    }

    public void goLevels(){
        startActivity(new Intent(this,_A_LevelScreen.class));
        ActivityCompat.finishAffinity(_4_MultiplayerOptions.this);
    }

    public void start(String m){

        String mail=findUserMail(m);
        String USER_name_surname=findUserName(userID);

        try {
            GMailSender sender = new GMailSender("ob2.questionmark@gmail.com","begumbatuonur");
            sender.sendMail("QuestionMark Time !!",
                    USER_name_surname+" has challenged you !",
                    "ob2.questionmark@gmail.com",
                    mail);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }

    }

}