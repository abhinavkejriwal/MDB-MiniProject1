package ak.com.example.miniproject1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> memberNames;
    Button option1;
    Button option2;
    Button option3;
    Button option4;
    Button end;
    ImageView memberImage;
    int scoreTrack;
    TextView scoreTrackView;
    TextView time;
    CountDownTimer timekeeper;
    int memNamePicID;

    /**A function that gets the member's picture,
     * by typing in the name */
    public int getMemberPicID(String Name) {
        return this.getResources().getIdentifier(Name.toLowerCase().
                replace(" ", ""), "drawable", this.getPackageName());
    }

    /** A function that updates the score. */
    public void scoreUpdate(int points) {
        scoreTrack += points;
        scoreTrackView.setText("Score: " + scoreTrack);
    }

    /** A random instance*/
    final Random r = new Random();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
                String guess1 = ((Button) v).getText().toString();
                int guess1ID = getMemberPicID(guess1);
                if (guess1ID == memNamePicID) {
                    scoreUpdate(1);
                    Toast.makeText(GameActivity.this, "Great!",
                            Toast.LENGTH_LONG).show();
                    playGame();
                } else {
                    Toast.makeText(GameActivity.this, "Nope",
                            Toast.LENGTH_LONG).show();
                    playGame();
                }
                break;
            case R.id.memImage:
                Intent contactCreator = new Intent(ContactsContract.Intents.Insert.ACTION);
                contactCreator.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                startActivity(contactCreator);
                break;
            case R.id.endgame:
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(GameActivity.this);
                builder.setTitle("End Game").setMessage("Sure?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                timekeeper.cancel();
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timekeeper.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playGame();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        scoreTrack = 0;

        memberNames = new ArrayList<>();
        String[] memberArray = {"Aayush Tyagi", "Abhinav Koppu", "Aditya Yadav", "Ajay Merchia",
                "Alice Zhao", "Amy Shen", "Anand Chandra", "Andres Medrano", "Angela Dong",
                "Anika Bagga", "Anmol Parande", "Austin Davis", "Ayush Kumar", "Brandon David",
                "Candice Ye", "Carol Wang", "Cody Hsieh", "Daniel Andrews", "Daniel Jing",
                "Eric Kong", "Ethan Wong", "Fang Shuo", "Izzie Lau", "Jaiveer Singh", "Japjot Singh",
                "Jeffery Zhang", "Joey Hejna", "Julie Deng", "Justin Kim", "Kaden Dippe", "Kanyes Thaker",
                "Kayli Jiang", "Kiana Go", "Leon Kwak", "Levi Walsh", "Louie Mcconnell", "Max Miranda",
                "Michelle Mao", "Mohit Katyal", "Mudabbir Khan", "Natasha Wong", "Nikhar Arora", "Noah Pepper",
                "Paul Shao", "Radhika Dhomse", "Sai Yandapalli", "Saman Virai", "Sarah Tang",
                "Sharie Wang", "Shiv Kushwah", "Shomil Jain", "Shreya Reddy", "Shubha Jagannatha",
                "Shubham Gupta", "Srujay Korlakunta", "Stephen Jayakar", "Suyash Gupta", "Tiger Chen", "Vaibhav Gattani", "Victor Sun",
                "Vidya Ravikumar", "Vineeth Yeevani", "Wilbur Shi", "William Lu", "Will Oakley", "Xin Yi Chen", "Young Lin"};

        for (int i = 0; i < memberArray.length; i += 1) {
            memberNames.add(memberArray[i]);
        }

        memberImage = findViewById(R.id.memImage);

        option1 = findViewById(R.id.button1);
        option2 = findViewById(R.id.button2);
        option3 = findViewById(R.id.button3);
        option4 = findViewById(R.id.button4);

        scoreTrackView = findViewById(R.id.scoredisplay);

        end = findViewById(R.id.endgame);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);
        memberImage.setOnClickListener(this);

        time = findViewById(R.id.timer);

        timekeeper = new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                time.setText("Time Left:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                time.setText("Ooh!");
                Toast.makeText(GameActivity.this, "Time Up!",
                        Toast.LENGTH_LONG).show();
                playGame();
            }
        };

        playGame();

    }


    /** Function that gives a new board */
    public void boardInitializer() {
        Collections.shuffle(memberNames);

        ArrayList<String> onlyFourNames = new ArrayList<>();

        for (int i = 0; i <= 4; i += 1) {
            String name = memberNames.get(i);
            onlyFourNames.add(name);
        }

        option1.setText(memberNames.get(0));
        option2.setText(memberNames.get(1));
        option3.setText(memberNames.get(2));
        option4.setText(memberNames.get(3));

        int memNameIndex = (int)(Math.random() * 4);
        String memNameString = onlyFourNames.get(memNameIndex);
        memNamePicID = getMemberPicID(memNameString);
        memberImage.setImageResource(memNamePicID);

    }

    /** Function that actually allows the user to play the game. */
    public void playGame() {
        boardInitializer();
        timekeeper.cancel();
        timekeeper.start();
        end.setOnClickListener(this);

    }
}
