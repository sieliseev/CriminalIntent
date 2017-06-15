package com.example.stas.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;
import java.util.UUID;

/**
 * Created by Stas on 13.06.2017.
 */

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "com.example.stas.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private ImageButton mLastCrimeButton, mFirstCrimeButton;

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);

                disableButton();

                return CrimeFragment.newInstance(crime.getID());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }

            private void disableButton() {
                if (mViewPager.getCurrentItem() == mCrimes.size() - 1) {
                    mLastCrimeButton.setEnabled(false);
                } else mLastCrimeButton.setEnabled(true);
                if (mViewPager.getCurrentItem() == 0) {
                    mFirstCrimeButton.setEnabled(false);
                } else mFirstCrimeButton.setEnabled(true);
            }
        });
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getID().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        mLastCrimeButton = (ImageButton) findViewById(R.id.last_crime_button);

        mLastCrimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mCrimes.size() - 1);
            }
        });
        mFirstCrimeButton = (ImageButton) findViewById(R.id.first_crime_button);
        mFirstCrimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
    }
}
