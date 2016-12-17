package com.justinmutsito.coolquotes.coolquotes.utils;

import android.content.Context;

import com.justinmutsito.coolquotes.coolquotes.R;

/**
 * Created by justin on 12/17/16.
 */
public class Quotes {
    private int mAuthorFace;
    private String[] mQuotes;


    public Quotes(Context context, int author) {
        initAuthor(context, author);
    }

    public int getAuthorFace() {
        return mAuthorFace;
    }

    public String[] getQuotes() {
        return mQuotes;
    }

    private void initAuthor(Context context, int n) {


        //Select which string array of quotes to use
        switch (n) {

            case 0: {
                mQuotes = context.getResources().getStringArray(R.array.Albert_Einstein);
                mAuthorFace = R.drawable.bg_albert;
                break;
            }
            case 1: {
                mQuotes = context.getResources().getStringArray(R.array.Abraham_Lincoln);
                mAuthorFace = R.drawable.bg_abraham;
                break;
            }


            case 2: {
                mQuotes = context.getResources().getStringArray(R.array.Benjamin_Franklin);
                mAuthorFace = R.drawable.bg_benjamin;
                break;
            }


            case 3: {
                mQuotes = context.getResources().getStringArray(R.array.Bill_Gates);
                mAuthorFace = R.drawable.bg_bill_gates;
                break;
            }

            case 4: {
                mQuotes = context.getResources().getStringArray(R.array.Bill_Cosby);
                mAuthorFace = R.drawable.bg_bill_cosby;
                break;
            }

            case 5: {
                mQuotes = context.getResources().getStringArray(R.array.Confucius);
                mAuthorFace = R.drawable.bg_confucius;
                break;
            }
            case 6: {
                mQuotes = context.getResources().getStringArray(R.array.Charles_Darwin);
                mAuthorFace = R.drawable.bg_charles_darwin;
                break;
            }

            case 7: {
                mQuotes = context.getResources().getStringArray(R.array.Charles_Dickens);
                mAuthorFace = R.drawable.bg_charles_dickens;
                break;
            }

            case 8: {
                mQuotes = context.getResources().getStringArray(R.array.Charlie_Chaplin);
                mAuthorFace = R.drawable.bg_charlie_chaplin;
                break;
            }
            case 9: {
                mQuotes = context.getResources().getStringArray(R.array.Ernest_Hemingway);
                mAuthorFace = R.drawable.bg_ernest_hemingway;
                break;
            }

            case 10: {
                mQuotes = context.getResources().getStringArray(R.array.Ernesto_Guevara);
                mAuthorFace = R.drawable.bg_ernesto;
                break;
            }

            case 11: {
                mQuotes = context.getResources().getStringArray(R.array.George_Bernard_Shaw);
                mAuthorFace = R.drawable.bg_george_bernard;
                break;
            }
            case 12: {
                mQuotes = context.getResources().getStringArray(R.array.Henry_Ford);
                mAuthorFace = R.drawable.bg_henry_ford;
                break;
            }

            case 13: {
                mQuotes = context.getResources().getStringArray(R.array.Julian_Assange);
                mAuthorFace = R.drawable.bg_julian__assange;
                break;
            }
            case 14: {
                mQuotes = context.getResources().getStringArray(R.array.Karl_Marx);
                mAuthorFace = R.drawable.bg_karl_marx;
                break;
            }

            case 15: {
                mQuotes = context.getResources().getStringArray(R.array.Mahatma_Gandhi);
                mAuthorFace = R.drawable.bg_mahatma__gandhi;
                break;
            }
            case 16: {
                mQuotes = context.getResources().getStringArray(R.array.Mother_Teresa);
                mAuthorFace = R.drawable.bg_mother_teresa;
                break;
            }

            case 17: {
                mQuotes = context.getResources().getStringArray(R.array.Mark_Twain);
                mAuthorFace = R.drawable.bg_mark_twain;
                break;
            }

            case 18: {
                mQuotes = context.getResources().getStringArray(R.array.Oscar_Wilde);
                mAuthorFace = R.drawable.bg_oscar_wilde;
                break;
            }

            case 19: {
                mQuotes = context.getResources().getStringArray(R.array.Socrates);
                mAuthorFace = R.drawable.bg_socrates;
                break;
            }
            case 20: {
                mQuotes = context.getResources().getStringArray(R.array.Steven_Jobs);
                mAuthorFace = R.drawable.bg_steve_jobs;
                break;
            }

            case 21: {
                mQuotes = context.getResources().getStringArray(R.array.William_Shakespeare);
                mAuthorFace = R.drawable.bg_william_shakespeare;
                break;
            }


            default: {
                mQuotes = context.getResources().getStringArray(R.array.Warren_Buffett);
                mAuthorFace = R.drawable.bg_warren_buffet;

            }
        }

    }
}
