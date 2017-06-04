package com.example.stas.criminalintent;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Stas on 03.06.2017.
 */

public abstract class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected TextView mTitleTextView;
    protected TextView mDateTextVies;
    protected Crime mCrime;

    protected CrimeHolder(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

        mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
        mDateTextVies = (TextView) itemView.findViewById(R.id.crime_date);
    }

    protected void bind(Crime crime) {
        mCrime = crime;
        mTitleTextView.setText(mCrime.getTitle());
        mDateTextVies.setText(mCrime.getDate().toString());
    }
}