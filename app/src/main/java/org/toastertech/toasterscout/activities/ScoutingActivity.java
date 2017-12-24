package org.toastertech.toasterscout.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.toastertech.toasterscout.R;
import org.toastertech.toasterscout.data.FileManager;
import org.toastertech.toasterscout.data.Match;
import org.toastertech.toasterscout.fragments.MatchDetailsFragment;


public class ScoutingActivity extends AppCompatActivity {
    int currentMatchIndex;

    MatchDetailsFragment matchDetailsFragment;
    Button previousMatchButton, nextMatchButton;

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

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println(FileManager.currentDirectory.getPath());
        try {
            FileManager.writeFile();

        } catch (Exception e){
            Toast.makeText(this, "Error Writing File", Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
    }

    private void setupXMLVariables(){
        matchDetailsFragment = (MatchDetailsFragment)getSupportFragmentManager().findFragmentById(R.id.matchDetailsFragment);
        previousMatchButton  = (Button)findViewById(R.id.previousMatch);
        nextMatchButton      = (Button)findViewById(R.id.nextMatchButton);

        previousMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToPreviousMatch();
            }
        });
        nextMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNextMatch();
            }
        });

        reconfigureWidgets();
    }

    private void reconfigureWidgets(){
        matchDetailsFragment.updateFragment(FileManager.currentMatches.get(currentMatchIndex));
    }

    private void saveMatch(){
        Match currentMatch = FileManager.currentMatches.get(currentMatchIndex);

        currentMatch.setMatchNum(matchDetailsFragment.getMatchNum());
        currentMatch.setTeamNum(matchDetailsFragment.getTeamNum());
        currentMatch.setScoutName(matchDetailsFragment.getScoutName());
    }

    private void moveToPreviousMatch(){
        saveMatch();

        if(currentMatchIndex > 0){ //If this isn't the first match, then we can keep going backwards.
            currentMatchIndex -= 1;
        } else {
            Toast.makeText(this, "This is your first match. So, you can't move back.", Toast.LENGTH_SHORT).show();
        }

        reconfigureWidgets();
    }

    private void moveToNextMatch(){
        saveMatch(); //We are going to save the details of our current match

        // If this is the last match we have so far, we just need to create a new blank match
        if(currentMatchIndex == FileManager.currentMatches.size()-1){
            Match newMatch = new Match();
            FileManager.currentMatches.add(newMatch);

            Toast.makeText(this, "Creating New Match", Toast.LENGTH_LONG);
        }

        currentMatchIndex += 1;

        reconfigureWidgets();
    }


}
