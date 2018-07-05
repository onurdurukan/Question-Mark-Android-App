package batuhan.qm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String MAIL = intent.getStringExtra("mail");
        String USERNAME = intent.getStringExtra("username");

        try {
            GMailSender sender = new GMailSender("ob2.questionmark@gmail.com","begumbatuonur");
            sender.sendMail("QuestionMark Time !!",
                    USERNAME+" has challenged you !",
                    "ob2.questionmark@gmail.com",
                    MAIL);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
    }


}
