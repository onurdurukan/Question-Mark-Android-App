package batuhan.qm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static batuhan.qm.R.layout.activity_login_screen;

public class _1_LoginScreen extends AppCompatActivity implements View.OnClickListener {
    
    private static String TAG="TAG";
    private GoogleApiClient client;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText EMAIL;
    private  EditText PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
         //       WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(activity_login_screen);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        EMAIL   = (EditText)findViewById(R.id._email);
        PASSWORD   = (EditText)findViewById(R.id._password);
       
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.signUp).setOnClickListener(this);
        findViewById(R.id.forgetPassword).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        mAuth.addAuthStateListener(mAuthListener);
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }
    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        client.disconnect();
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        final ProgressDialog progress;
        progress = ProgressDialog.show(this, "Please wait", "Signing in...", true);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                            startMenu();

                        } else{
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(_1_LoginScreen.this,
                                    "Invalid email or password", Toast.LENGTH_LONG).show();
                        }

                        progress.dismiss();
                    }
                });
        // [END sign_in_with_email]
    }

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



        return valid;
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("_1_LoginScreen Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signUp) {
            startActivity(new Intent(this, _2_SignUpScreen.class));
        } else if (i == R.id.login) {
            signIn(EMAIL.getText().toString(), PASSWORD.getText().toString());
        } else if (i == R.id.forgetPassword) {
            String sEMAIL = EMAIL.getText().toString();
            if(!sEMAIL.matches("")){
                mAuth.sendPasswordResetEmail(EMAIL.getText().toString());
                String text="Your password was resetted!";
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                // Creates textview for centre title
                TextView myMsg = new TextView(this);
                myMsg.setText(text);
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                myMsg.setTextSize(20);
                myMsg.setTextColor(Color.BLACK);
                //set custom title
                builder.setCustomTitle(myMsg);
                builder.setMessage("Check your email for the new password.");
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.show();
                //Create custom message
                TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
                assert messageText != null;
                messageText.setGravity(Gravity.CENTER);
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Please enter your email above.");
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.show();
                //Create custom message
                TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
                assert messageText != null;
                messageText.setGravity(Gravity.CENTER);

            }

        }

    }



    public void startMenu(){
        Intent intent = new Intent(this, _3_MenuScreen.class);
        startActivity(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityCompat.finishAffinity(_1_LoginScreen.this);
        Toast.makeText(_1_LoginScreen.this,
                "Welcome again !", Toast.LENGTH_LONG).show();
    }

}