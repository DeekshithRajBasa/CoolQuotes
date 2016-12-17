package com.justinmutsito.coolquotes.coolquotes.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.justinmutsito.coolquotes.coolquotes.R;
import com.justinmutsito.coolquotes.coolquotes.database.DBOpenHelper;
import com.justinmutsito.coolquotes.coolquotes.preferences.Preferences;
import com.justinmutsito.coolquotes.coolquotes.utils.Quotes;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class WelcomeActivity extends AppCompatActivity {

    private String[] mQuotes;
    private int mPersonNumber;
    private int mPosition;
    private String mCurrentQuote;
    private DBOpenHelper mDBOpenHelper;
    private Preferences mPreferences;

    @Bind(R.id.faceImageView)
    ImageView mFace;
    @Bind(R.id.quoteLabel)
    TextView mQuote;
    @Bind(R.id.categoriesButton)
    Button mCategories;
    @Bind(R.id.authorButton)
    Button mAuthor;
    @Bind(R.id.shareIcon)
    ImageView mIconShare;
    @Bind(R.id.favouritesLabel)
    TextView mFavorites;
    @Bind(R.id.settingsIcon)
    ImageView mIconSettings;
    @Bind(R.id.fadedImage)
    ImageView mFadedImage;
    @Bind(R.id.fadedImage2)
    ImageView mFadedImage2;
    @Bind(R.id.backgroundImage)
    ImageView mBackgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        mPreferences = new Preferences(this);

        appLaunch();
        setMyTheme();

        //Get and set UI data
        initUi();
        animateViews();

        //Open database for saving favourites quotes.
        mDBOpenHelper = new DBOpenHelper(this);


    }

    private void initUi() {
        randomQuote();
        Quotes quotes = new Quotes(this,mPersonNumber);
        mQuotes = quotes.getQuotes();
        mFace.setImageResource(quotes.getAuthorFace());
        mCurrentQuote = mQuotes[mPosition];
        mQuote.setText(mCurrentQuote);
    }

    private void animateViews() {
        //Animate views
        YoYo.with(Techniques.Bounce).duration(1000).playOn(mFadedImage);
        YoYo.with(Techniques.Hinge).duration(3000).playOn(mFadedImage2);
        YoYo.with(Techniques.BounceIn).duration(2000).playOn(mFace);
        YoYo.with(Techniques.ZoomIn).duration(2000).playOn(mQuote);
        YoYo.with(Techniques.RollIn).duration(2000).playOn(mCategories);
        YoYo.with(Techniques.RollIn).duration(2000).playOn(mAuthor);
        YoYo.with(Techniques.ZoomIn).duration(2000).playOn(mIconShare);
        YoYo.with(Techniques.Tada).duration(2000).playOn(mFavorites);
        YoYo.with(Techniques.ZoomIn).duration(2000).playOn(mIconSettings);

    }


    @OnClick(R.id.quoteLabel)
    public void getQuote() {
        //Share or add quote to favourites.

        AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);

        builder.setItems(R.array.quotesOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                if (position == 0) {
                    //Share.
                    share(mCurrentQuote);
                } else {
                    //Add to favourites.
                    boolean added = mDBOpenHelper.addFavourite(mCurrentQuote);
                    if (added) {
                        Toast.makeText(WelcomeActivity.this, R.string.added, Toast.LENGTH_SHORT).show();


                    } else {
                        //Pop error Dialog.
                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(WelcomeActivity.this);
                        sweetAlertDialog.setTitleText(getString(R.string.try_again))
                                .show();

                    }

                }
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();


    }


    @OnClick(R.id.categoriesButton)
    public void startActivityCategories() {

        startActivity(new Intent(WelcomeActivity.this, CategoriesActivity.class));

    }


    @OnClick(R.id.authorButton)
    public void startActivityPeople() {
        startActivity(new Intent(WelcomeActivity.this, AuthorsActivity.class));

    }

    @OnClick(R.id.shareIcon)
    public void shareAppDetails() {

        //Share app with friends by sending it's link from the app store.
        String url = "https://play.google.com/store/apps/details?id=com.justinmutsito.standoutqoutes";
        String message = "Check out this cool app " + url;
        share(message);

    }

    @OnClick(R.id.favouritesLabel)
    public void startActivityFavourites() {
        startActivity(new Intent(WelcomeActivity.this, FavouritesActivity.class));

    }

    @OnClick(R.id.settingsIcon)
    public void configSettings() {

        startActivity(new Intent(WelcomeActivity.this, SettingsActivity.class));
    }


    private void randomQuote() {

        //Sets randomly the person and quote position.

        int NUMBER_OF_PEOPLE = 23; //Number of people in the people string array
        int MINIMUM_NUMBER_OF_QUOTES = 10; //Minimum number of quotes provided by one person

        Random r = new Random();
        mPersonNumber = r.nextInt(NUMBER_OF_PEOPLE - 1);
        mPosition = r.nextInt(MINIMUM_NUMBER_OF_QUOTES - 1);

    }

    private void share(String text) {
        //Share quote.
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, text + ".Shared from " + getString(R.string.app_name) + " .");
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share)));
    }

    private void setMyTheme() {

        //Set theme using mTheme passed from the SettingsActivity


        String theme = mPreferences.getMyTheme();
        if (theme.equals("brown")) {
            String darkGrey = "#212121";
            mBackgroundImage.setImageResource(R.drawable.bg_brown);
            mFadedImage.setImageResource(R.color.brownFaded);
            mQuote.setTextColor(Color.parseColor(darkGrey));
            mCategories.setTextColor(Color.parseColor(darkGrey));
            mCategories.setBackground(getResources().getDrawable(R.drawable.bg_brown_circle_gradient));
            mAuthor.setTextColor(Color.parseColor(darkGrey));
            mAuthor.setBackground(getResources().getDrawable(R.drawable.bg_brown_circle_gradient));
            mIconShare.setImageResource(R.drawable.ic_share_variant_grey600_48dp);
            mFavorites.setTextColor(Color.parseColor(darkGrey));
            mIconSettings.setImageResource(R.drawable.ic_settings_grey600_48dp);

        } else {
            String white = "#ffffff";
            mBackgroundImage.setImageResource(R.drawable.bg_blue);
            mFadedImage.setImageResource(R.color.blueFaded);
            mQuote.setTextColor(Color.parseColor(white));
            mCategories.setTextColor(Color.parseColor(white));
            mCategories.setBackground(getResources().getDrawable(R.drawable.bg_blue_circle_gradient));
            mAuthor.setTextColor(Color.parseColor(white));
            mAuthor.setBackground(getResources().getDrawable(R.drawable.bg_blue_circle_gradient));
            mIconShare.setImageResource(R.drawable.ic_share_variant_white_48dp);
            mFavorites.setTextColor(Color.parseColor(white));
            mIconSettings.setImageResource(R.drawable.ic_settings_white_48dp);
        }
    }

    private void appLaunch(){
        int launchTime = mPreferences.getLauchTime();
        if(launchTime<=4){
            if(launchTime==2 || launchTime==4){
                mPreferences.setTheme("blue");
                Toast.makeText(WelcomeActivity.this, R.string.change_theme,Toast.LENGTH_LONG).show();
            }

            else {
                mPreferences.setTheme("brown");
            }


        }
        launchTime++;
        mPreferences.setLaunchTime(launchTime);
    }


    @Override
    protected void onResume() {
        super.onResume();
        initUi();
        animateViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDBOpenHelper.close();
    }
}


