package batuhan.qm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class _A_LevelScreen extends AppCompatActivity {
    Button Button1, Button2, Button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_screen);

        Button1 = (Button) findViewById(R.id.EasyButton);
        Button2 = (Button) findViewById(R.id.MediumButton);
        Button3 = (Button) findViewById(R.id.HardButton);


        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartIntent();
                ActivityCompat.finishAffinity(_A_LevelScreen.this);
            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartIntent();
                ActivityCompat.finishAffinity(_A_LevelScreen.this);
                 }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartIntent();
                ActivityCompat.finishAffinity(_A_LevelScreen.this);
            }
        });

    }

    public void StartIntent(){
        startActivity(new Intent(this, _B_QuestionScreen.class));
    }
}
