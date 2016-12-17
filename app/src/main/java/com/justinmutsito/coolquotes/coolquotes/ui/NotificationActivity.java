package com.justinmutsito.coolquotes.coolquotes.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.justinmutsito.coolquotes.coolquotes.database.DBOpenHelper;
import com.justinmutsito.coolquotes.coolquotes.R;
import com.justinmutsito.coolquotes.coolquotes.utils.Quotes;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends AppCompatActivity {


    private String[] mQuotes;
    private int mPersonNumber;
    private int mPosition;
    private String mCurrentQuote;
    private DBOpenHelper mDBOpenHelper;


    @Bind(R.id.faceImageView)
    ImageView mFace;
    @Bind(R.id.quoteLabel)
    TextView mQuote;
    @Bind(R.id.dayQuoteLabel)
    TextView mDayQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);


        mDBOpenHelper = new DBOpenHelper(this);
        randomQuote();
        Quotes quotes = new Quotes(this,mPersonNumber);
        mQuotes = quotes.getQuotes();
        mFace.setImageResource(quotes.getAuthorFace());

        mCurrentQuote = mQuotes[mPosition];
        mQuote.setText(mCurrentQuote);
        animateViews();


    }

    @OnClick(R.id.quoteLabel)
    public void useQuote() {
        // Share or add quote to favourites
        AlertDialog.Builder builder = new AlertDialog.Builder(NotificationActivity.this);

        builder.setItems(R.array.quotesOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                if (position == 0) {
                    //Share
                    share(mCurrentQuote);
                } else {
                    //Add to favourites
                    boolean added = mDBOpenHelper.addFavourite(mCurrentQuote);
                    if (added) {

                        Toast.makeText(NotificationActivity.this, R.string.added, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(NotificationActivity.this, R.string.not_added, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void randomQuote() {
        //Sets randomly the person and quote position.

        int NUMBER_OF_PEOPLE = 23; //Number of people in the people string array
        int MINIMUM_NUMBER_OF_QUOTES = 10; //Minimum number of quotes provided by one person

        Random r = new Random();
        mPersonNumber = r.nextInt(NUMBER_OF_PEOPLE - 1);
        mPosition = r.nextInt(MINIMUM_NUMBER_OF_QUOTES - 1);

    }



    private void animateViews() {
        //Animate views
        YoYo.with(Techniques.Tada).duration(1000).playOn(mDayQuote);
        YoYo.with(Techniques.BounceIn).duration(2000).playOn(mFace);
        YoYo.with(Techniques.BounceIn).duration(2300).playOn(mQuote);


    }

    private void share(String text) {
        //Share text
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, text + ".Shared from " + getString(R.string.app_name) + " .");
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share)));
    }

}
