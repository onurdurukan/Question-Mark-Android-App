package batuhan.qm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class _2_SignUpScreen extends _1_LoginScreen {

    private EditText NAME;
    private EditText SURNAME;
    private EditText AGE;
    private EditText LOCATION;
    private EditText EMAIL;
    private EditText PASSWORD;
    private String ID="";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String TAG = "TAG";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        NAME = (EditText) findViewById(R.id._name);
        SURNAME = (EditText) findViewById(R.id._surname);
        AGE = (EditText) findViewById(R.id._age);
        LOCATION = (EditText) findViewById(R.id._location);
        EMAIL = (EditText) findViewById(R.id._email);
        PASSWORD = (EditText) findViewById(R.id._password);

        Button SIGNUP = (Button) findViewById(R.id.signUpButton);
        SIGNUP.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        DatabaseReference ref = database.getReference("Users").child("user_no");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ID = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected boolean validateForm() {
        boolean valid = true;

        String email = EMAIL.getText().toString();
        if (TextUtils.isEmpty(email)) {
            EMAIL.setError("Required.");
            valid = false;
        } else {
            EMAIL.setError(null);
        }

        String password = PASSWORD.getText().toString();
        if (TextUtils.isEmpty(password)) {
            PASSWORD.setError("Required.");
            valid = false;
        } else {
            PASSWORD.setError(null);
        }

        String name = NAME.getText().toString();
        if (TextUtils.isEmpty(name)) {
            NAME.setError("Required.");
            valid = false;
        } else {
            NAME.setError(null);
        }

        String surname = SURNAME.getText().toString();
        if (TextUtils.isEmpty(surname)) {
            SURNAME.setError("Required.");
            valid = false;
        } else {
            SURNAME.setError(null);
        }

        String location = LOCATION.getText().toString();
        if (TextUtils.isEmpty(location)) {
            LOCATION.setError("Required.");
            valid = false;
        } else {
            LOCATION.setError(null);
        }

        String age = AGE.getText().toString();
        if (TextUtils.isEmpty(age)) {
            AGE.setError("Required.");
            valid = false;
        } else {
            AGE.setError(null);
        }

        return valid;
    }


    private void createAccount(final String email, String password, final String name, final String surname, final String age, final String location) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        final String e_mail=email;

        final ProgressDialog progress;
        progress = ProgressDialog.show(this, "Please wait", "Your Account is being created...", true);


        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            String uid = task.getResult().getUser().getUid();
                            database.getReference("Users").child("user_" + ID).child("uid").setValue(uid);
                            database.getReference("Users").child("user_" + ID).child("name").setValue(name);
                            database.getReference("Users").child("user_" + ID).child("surname").setValue(surname);
                            database.getReference("Users").child("user_" + ID).child("age").setValue(age);
                            database.getReference("Users").child("user_" + ID).child("location").setValue(location);
                            database.getReference("Users").child("user_" + ID).child("email").setValue(e_mail);
                            database.getReference("Users").child("user_" + ID).child("temp_score").setValue("5");
                            database.getReference("Users").child("user_" + ID).child("high_score").setValue("0");

                            int a = Integer.parseInt(ID);
                            a++;
                            database.getReference("Users").child("user_no").setValue(Integer.toString(a));
                            sendEmailVerification();
                            startAct();

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(_2_SignUpScreen.this,
                                    "Something is wrong.", Toast.LENGTH_LONG).show();
                        }

                        progress.dismiss();
                    }
                });
        // [END create_user_with_email]
    }

    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(_2_SignUpScreen.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(_2_SignUpScreen.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signUpButton) {
            createAccount(EMAIL.getText().toString(), PASSWORD.getText().toString(), NAME.getText().toString(), SURNAME.getText().toString(), AGE.getText().toString(), LOCATION.getText().toString());
        }
    }

    public void startAct(){
        startActivity(new Intent(this, _1_LoginScreen.class));
    }
}

