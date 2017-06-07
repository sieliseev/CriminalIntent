package com.example.stas.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Stas on 03.06.2017.
 */

public abstract class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected TextView mTitleTextView;
    protected TextView mDateTextVies;
    protected ImageView mSolvedImageView;
    protected Crime mCrime;

    protected CrimeHolder(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

        mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
        mDateTextVies = (TextView) itemView.findViewById(R.id.crime_date);
        mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
    }

    protected void bind(Crime crime) {
        mCrime = crime;
        mTitleTextView.setText(mCrime.getTitle());
        mDateTextVies.setText(getFormat());
        mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
    }

    private CharSequence getFormat() {
        return DateFormat.format("EEEE, MMM dd, yyyy", mCrime.getDate());
    }

    @Override
    public void onClick(View v) {
        Context context = itemView.getContext();
        Intent intent = new Intent(context, CrimeActivity.class);
        context.startActivity(intent);
    }
}