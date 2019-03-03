import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


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

    public ArrayList<HighscoreData> extractHighscoreData(){
        ArrayList<HighscoreData> resultlist = new ArrayList<>();
        saveFileNames = usernamehandler.readUsernames();
        for( String name : saveFileNames){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(name));
                String points = reader.readLine();
                String totalpoints = reader.readLine();
                resultlist.add(new HighscoreData(name,points,totalpoints));
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return resultlist;
    }



}
