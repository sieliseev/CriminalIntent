package com.example.stas.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Stas on 27.05.2017.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
