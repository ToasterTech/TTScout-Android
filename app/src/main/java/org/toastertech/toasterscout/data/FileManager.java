package org.toastertech.toasterscout.data;

import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * This will essentially be a static class that we will call read/write functions from.
 */
public class FileManager {

    private static String scoutName = "";

    /**
     * This is pretty much a list of all the matches we are working with at the moment.
     */
    public static ArrayList<Match> currentMatches = new ArrayList<>();

    /**
     * This represents the save directory for out files and our current save file.
     */
    public static File currentDirectory,
                       currentFile;

    /**
     * This is just the name of the competition we are scouting for.
     */
    public static String currentCompetition;

    static String[] spreadsheetHeaders = {"Match Number", "Team Number", "Scout Name"};
    static String[] parameterNames = {"matchNum", "teamNum", "scoutName"};
    static CellProcessor[] processors = {new ParseInt(), new ParseInt(), new ConvertNullTo("")};

    /**
     * Constructor is private so we can't make instances of this. Instead, we will cass our functions
     * with FileManager.method() vs. creating multiple instances to juggle.
     */
    private FileManager(){

    }


    /**
     * Writes all of our matches to the file for our competition.
     *
     * @throws Exception
     */
    public static void writeFile() throws Exception{
        currentFile = new File(currentDirectory, currentCompetition + ".csv");

        ICsvBeanWriter writer = new CsvBeanWriter(new FileWriter(currentFile, false), CsvPreference.STANDARD_PREFERENCE);

        writer.writeHeader(spreadsheetHeaders);


        for(Match match : currentMatches){
            writer.write(match, parameterNames, processors);
        }

        writer.close();
    }

    /**
     * Reads our file for matches so we can access them in the application.
     * @throws Exception
     */
    public static void readFile() throws Exception{
        currentFile = new File(currentDirectory, currentCompetition + ".csv");

        if(!currentFile.exists()){

            return;
        }
        ICsvBeanReader reader = new CsvBeanReader(new FileReader(currentFile), CsvPreference.STANDARD_PREFERENCE);
        reader.getHeader(true);

        Match currentMatch;

        while((currentMatch = reader.read(Match.class, parameterNames, processors)) != null){

            currentMatches.add(currentMatch);


        }

        reader.close();
    }

    public static void printSavedMatches(){
        for(Match match : currentMatches){
            System.out.println(match.toString());
        }
    }
}
