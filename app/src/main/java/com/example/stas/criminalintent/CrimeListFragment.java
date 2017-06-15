package com.example.stas.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Stas on 27.05.2017.
 */

public class CrimeListFragment extends Fragment {

    private int mLastClickHolderPosition = RecyclerView.NO_POSITION;

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            //Log.d("Position", "position update = "+ mLastClickHolderPosition);
           mAdapter.notifyDataSetChanged();
            //mAdapter.notifyItemChanged(mLastClickHolderPosition);
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Crime mCrime;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        private CrimeHolder(LayoutInflater inflater, ViewGroup parent, int layout) {
            super(inflater.inflate(layout, parent, false));

            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
        }

        protected void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(getFormat());
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        private CharSequence getFormat() {
            return DateFormat.format("EEEE, MMM dd, yyyy", mCrime.getDate());
        }

        @Override
        public void onClick(View v) {
            mLastClickHolderPosition = getAdapterPosition();
           // Log.d("Position", "position = "+ mLastClickHolderPosition);
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getID());
            startActivity(intent);
        }
    }

    // add the second viewHolder with the button police
    private class SeriousCrimeHolder extends CrimeHolder {

        private Button mPoliceButton;

        private SeriousCrimeHolder(LayoutInflater inflater, ViewGroup parent, int layout) {
            super(inflater, parent, layout);

            mPoliceButton = (Button) itemView.findViewById(R.id.button_police);
        }

        @Override
        protected void bind(Crime crime) {
            super.bind(crime);

            mPoliceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "You called the police!", Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }
/*
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }*/
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private final int VIEW_TYPE_SIMPLE = R.layout.list_item_crime;
        private final int VIEW_TYPE_SERIOUS = R.layout.list_item_crime_police;

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public int getItemViewType(int position) {
            if (mCrimes.get(position).isRequiresPolice())
                return VIEW_TYPE_SERIOUS;
            else
                return VIEW_TYPE_SIMPLE;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            switch (viewType) {
                case VIEW_TYPE_SIMPLE:
                    return new CrimeHolder(layoutInflater, parent, viewType);
                case VIEW_TYPE_SERIOUS:
                    return new SeriousCrimeHolder(layoutInflater, parent, viewType);
                default:
                    return new CrimeHolder(layoutInflater, parent, viewType);
            }

        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
