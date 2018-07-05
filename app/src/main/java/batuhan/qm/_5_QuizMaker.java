package batuhan.qm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class _5_QuizMaker extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String count;
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    EditText editQuestion;
    EditText editAnswer1;
    EditText editAnswer2;
    EditText editAnswer3;
    EditText editAnswer4;
    EditText editCorrect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_maker);

        editQuestion = (EditText) findViewById(R.id.question);
        editAnswer1 = (EditText) findViewById(R.id.answer1);
        editAnswer2 = (EditText) findViewById(R.id.answer2);
        editAnswer3 = (EditText) findViewById(R.id.answer3);
        editAnswer4 = (EditText) findViewById(R.id.answer4);
        editCorrect = (EditText) findViewById(R.id.correctans);

        Button Submit = (Button) findViewById(R.id.button1);
        mAuth= FirebaseAuth.getInstance();

        DatabaseReference ref =database.getReference("Questions").child("count");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count= dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createQuestion(editQuestion.getText().toString(), editAnswer1.getText().toString(), editAnswer2.getText().toString(), editAnswer3.getText().toString(), editAnswer4.getText().toString(), editCorrect.getText().toString());

            }
        });


    }

    protected boolean validateForm() {
        boolean valid = true;

        String question = editQuestion.getText().toString();
        if (TextUtils.isEmpty(question)) {
            editQuestion.setError("Required.");
            valid = false;
        } else {
            editQuestion.setError(null);
        }

        String answer1 = editAnswer1.getText().toString();
        if (TextUtils.isEmpty(answer1)) {
            editAnswer1.setError("Required.");
            valid = false;
        } else {
            editAnswer1.setError(null);
        }

        String answer2 = editAnswer2.getText().toString();
        if (TextUtils.isEmpty(answer2)) {
            editAnswer2.setError("Required.");
            valid = false;
        } else {
            editAnswer2.setError(null);
        }

        String answer3 = editAnswer3.getText().toString();
        if (TextUtils.isEmpty(answer3)) {
            editAnswer3.setError("Required.");
            valid = false;
        } else {
            editAnswer3.setError(null);
        }

        String answer4 = editAnswer4.getText().toString();
        if (TextUtils.isEmpty(answer4)) {
            editAnswer4.setError("Required.");
            valid = false;
        } else {
            editAnswer4.setError(null);
        }

        String correctans = editCorrect.getText().toString();
        if (TextUtils.isEmpty(correctans)) {
            editCorrect.setError("Required.");
            valid = false;
        } else {
            if(correctans.equals(answer1) || correctans.equals(answer2) || correctans.equals(answer3) || correctans.equals(answer4)){
                editCorrect.setError(null);
            }else {
                editCorrect.setError("Answer is not specified above!");
                valid = false;
            }
        }

        return valid;
    }

    private void createQuestion(final String question, final String answer1, final String answer2, final String answer3, final String answer4, final String correctans) {

        if (!validateForm()) {
            return;
        }

        final ProgressDialog progress;
        progress = ProgressDialog.show(this, "Please wait", "Your question is adding...", true);

        database.getReference("Questions").child(count).child("answer1").setValue(answer1);
        database.getReference("Questions").child(count).child("answer2").setValue(answer2);
        database.getReference("Questions").child(count).child("answer3").setValue(answer3);
        database.getReference("Questions").child(count).child("answer4").setValue(answer4);
        database.getReference("Questions").child(count).child("correct").setValue(correctans);
        database.getReference("Questions").child(count).child("name").setValue(question);

        int a = Integer.parseInt(count);
        a++;
        database.getReference("Questions").child("count").setValue(Integer.toString(a));

        progress.dismiss();
        Toast.makeText(_5_QuizMaker.this,
                "New question is added !", Toast.LENGTH_LONG).show();
        startAct();

    }

    public void startAct(){
        startActivity(new Intent(this, _3_MenuScreen.class));
    }

}
