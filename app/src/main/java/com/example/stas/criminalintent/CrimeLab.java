package com.example.stas.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Stas on 27.05.2017.
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private Map<UUID, Crime> mCrimesMap;   //improving performance

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimesMap = new LinkedHashMap<>();
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            crime.setRequiresPolice(i % 3 == 0);
            mCrimesMap.put(crime.getID(), crime);
        }
    }

    public List<Crime> getCrimes() {
        return new ArrayList<>(mCrimesMap.values());
    }

    public Crime getCrime(UUID id) {
        return mCrimesMap.get(id);
    }

}
