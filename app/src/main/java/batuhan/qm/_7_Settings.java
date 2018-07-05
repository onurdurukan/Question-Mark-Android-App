package batuhan.qm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class _7_Settings extends AppCompatActivity {

    private EditText OldPassword;
    private EditText NewPassword;
    private EditText NewPassword2;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        OldPassword = (EditText) findViewById(R.id._oldpass);
        NewPassword = (EditText) findViewById(R.id._newpass);
        NewPassword2 = (EditText) findViewById(R.id._newpass2);

        Button CHANGE = (Button) findViewById(R.id.changepass);
        CHANGE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(NewPassword.getText().toString());
            }
        });
    }

    protected boolean validateForm() {
        boolean valid = true;

        String oldpass = OldPassword.getText().toString();
        if (TextUtils.isEmpty(oldpass)) {
            OldPassword.setError("Required.");
            valid = false;
        } else {
            OldPassword.setError(null);
        }

        String newpass = NewPassword.getText().toString();
        if (TextUtils.isEmpty(newpass)) {
            NewPassword.setError("Required.");
            valid = false;
        } else {
            NewPassword.setError(null);
        }

        String newpass2 = NewPassword2.getText().toString();
        if (TextUtils.isEmpty(newpass2)) {
            NewPassword2.setError("Required.");
            valid = false;
        } else {
            if(newpass2.equals(newpass)){
                NewPassword2.setError(null);
            }else{
                NewPassword2.setError("Passwords are not matching !");
                valid = false;
            }
        }
        return valid;
    }

    protected void update(String newPassword) {
        if (!validateForm()) {
            return;
        }
        user.updatePassword(newPassword);{

        }
        Toast.makeText(_7_Settings.this,
                "Your password has been changed !", Toast.LENGTH_LONG).show();
        startAct();
    }

    public void startAct(){
        startActivity(new Intent(this, _3_MenuScreen.class));
    }

}
