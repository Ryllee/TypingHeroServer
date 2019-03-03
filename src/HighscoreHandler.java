import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;


public class HighscoreHandler {

    private File highscoreFile;
    private UsernameHandler usernamehandler;
    ArrayList<String> saveFileNames;

    public HighscoreHandler(UsernameHandler usernamehandler){
        this.usernamehandler = usernamehandler;
        try {
            highscoreFile = new File("Highscore.txt");
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    private ArrayList<HighscoreData> extractHighscoreData(){
        ArrayList<HighscoreData> resultList = new ArrayList<>();
        saveFileNames = usernamehandler.readUsernames();
        for( String name : saveFileNames){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(name));
                String points  = reader.readLine();
                String totalpoints = reader.readLine();
                resultList.add(new HighscoreData(name,Float.valueOf(points),Float.valueOf(totalpoints)));
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return resultList;
    }

    private ArrayList<HighscoreData> sortHighscoreData(ArrayList<HighscoreData> highscoreList){
        highscoreList.sort(Comparator.comparing(HighscoreData::getTotalPoints));
        return highscoreList;
    }

    private void writeHighscoreFile(ArrayList<HighscoreData> highscoreList){
        try{
            FileWriter writer = new FileWriter(highscoreFile);
            for(int index = 0;index < 5; index++){
                writer.write(index+1 +": " + highscoreList.get(index).username + " " + highscoreList.get(index).points + " " + highscoreList.get(index).totalPoints + "\n");
            }
            writer.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void createHighscore(){
        ArrayList<HighscoreData> highscoreList = sortHighscoreData(extractHighscoreData());
        writeHighscoreFile(highscoreList);
    }





}
