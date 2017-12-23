package org.toastertech.toasterscout.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import org.toastertech.toasterscout.R;
import org.toastertech.toasterscout.data.FileManager;
import org.toastertech.toasterscout.data.Match;
import org.toastertech.toasterscout.fragments.MatchDetailsFragment;


public class ScoutingActivity extends AppCompatActivity {
    int currentMatchIndex;

    MatchDetailsFragment matchDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting);

        try {
            FileManager.readFile();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        if(FileManager.currentMatches.size() > 0){
            currentMatchIndex = FileManager.currentMatches.size()-1;
        } else {
            FileManager.currentMatches.add(new Match());
            currentMatchIndex = 0;
        }

        setupXMLVariables();

    }


    private void setupXMLVariables(){
        matchDetailsFragment = (MatchDetailsFragment)getSupportFragmentManager().findFragmentById(R.id.matchDetailsFragment);
        matchDetailsFragment.updateFragment(FileManager.currentMatches.get(currentMatchIndex));
    }



}
