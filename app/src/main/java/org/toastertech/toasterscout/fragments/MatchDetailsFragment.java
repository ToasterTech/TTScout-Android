package org.toastertech.toasterscout.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import org.toastertech.toasterscout.R;
import org.toastertech.toasterscout.data.Match;


public class MatchDetailsFragment extends Fragment {
    View inflatedView;
    EditText matchNumField, teamNumField, scoutNameField;


    public MatchDetailsFragment() {
        super();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        System.out.println("onCreateView executed");

        inflatedView = inflater.inflate(R.layout.fragment_match_details, container, false);
        matchNumField  = (EditText) inflatedView.findViewById(R.id.matchNumTextField);
        teamNumField   = (EditText) inflatedView.findViewById(R.id.teamNumTextField);
        scoutNameField = (EditText) inflatedView.findViewById(R.id.scoutNameTextField);

        return inflatedView;
    }

    public void updateFragment(Match match){
        matchNumField .setText(String.valueOf(match.getMatchNum()));
        teamNumField  .setText(String.valueOf(match.getTeamNum()));
        scoutNameField.setText(match.getScoutName());
    }

    public int getMatchNum(){
        if(matchNumField.getText().toString().length() > 0){
            return Integer.valueOf(matchNumField.getText().toString());
        }

        return 0;
    }

    public int getTeamNum(){
        if(teamNumField.getText().toString().length() > 0) {
            return Integer.valueOf(teamNumField.getText().toString());
        }

        return 0;
    }

    public String getScoutName(){
        return scoutNameField.getText().toString();
    }
}
