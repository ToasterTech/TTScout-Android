package org.toastertech.toasterscout.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import org.toastertech.toasterscout.R;
import org.toastertech.toasterscout.data.FileManager;

import java.io.File;

public class HomeScreen extends AppCompatActivity {
    Button startScoutingButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        setupXMLVariables();
        setupDirectory();
    }

    /**
     * This will pretty much create our directory. It launches the FIle Chooser.
     */
    private void setupDirectory(){
        Activity activity = this;
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(activity, permissions, 1);

        } else {
            FileManager.setupDirectory();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            FileManager.setupDirectory();
        } else {
            finishAffinity();
        }
    }

    private void setupXMLVariables(){
        startScoutingButton = (Button)findViewById(R.id.startScoutingButton);

        startScoutingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupCompetitionName();
            }
        });
    }

    private void setupCompetitionName(){
        final Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select a Competition: ");

        final String[] competitions = {"Gainesville", "Dalton", "Albany", "Columbus", "Duluth", "State"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, competitions);

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    FileManager.currentCompetition = competitions[i];
                    FileManager.currentFile = new File(FileManager.currentDirectory, FileManager.currentCompetition + ".csv");
                    FileManager.readFile();

                    Intent intent = new Intent(context, ScoutingActivity.class);
                    startActivity(intent);
                } catch (Exception e){
                    AlertDialog.Builder errorAlert = new AlertDialog.Builder(context);
                    errorAlert.setTitle("Error");
                    errorAlert.setMessage(e.getMessage());
                    errorAlert.show();
                }
            }
        });

        builder.show();
    }
}
