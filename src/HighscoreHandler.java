import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Haanterar allt angående highscore
 *
 * @author Eric Rylander
 * @version 2019-03-12
 */
public class HighscoreHandler {

    private File highscoreFile;
    private UsernameHandler usernamehandler;
    ArrayList<String> saveFileNames;

    /**
     * Skapar en HighScoreHandler
     * @param usernamehandler vilken usernamehandler som den ska känna till
     */
    public HighscoreHandler(UsernameHandler usernamehandler){
        this.usernamehandler = usernamehandler;
        try {
            highscoreFile = new File("Highscore.txt");
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Hämtar HighscoreData från alla sparfiler
     * @return en lista av HighscoreData
     */
    public ArrayList<HighscoreData> extractHighscoreData(){
        ArrayList<HighscoreData> resultList = new ArrayList<>();
        saveFileNames = usernamehandler.readUsernames();
        for( String name : saveFileNames){
            try {
                String url = System.getProperty("user.dir");
                File file = new File(url+"/saveFiles/"+ name);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String points  = reader.readLine();
                String totalpoints = reader.readLine();
                resultList.add(new HighscoreData(name,Float.valueOf(points),Float.valueOf(totalpoints)));
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return resultList;
    }

    /**
     * Sorterar en lista av HighscoreData efter totalPoints
     * @param highscoreList vilken lista som ska sorteras
     * @return en lista som har sorterats
     */
    public ArrayList<HighscoreData> sortHighscoreData(ArrayList<HighscoreData> highscoreList){
        highscoreList.sort(Comparator.comparing(HighscoreData::getTotalPoints).reversed());
        return highscoreList;
    }

    /**
     * Skriver Highscoret(Top 5) i en fil på servern
     * @param highscoreList vilken lista som ska skrivas i filen
     */
    private void writeHighscoreFile(ArrayList<HighscoreData> highscoreList){
        try{
            FileWriter writer = new FileWriter(highscoreFile);
            int maxindex;
            if(highscoreList.size()<5){
                maxindex = highscoreList.size();
            }else{
                maxindex = 5;
            }
            for(int index = 0;index < maxindex ; index++){

                writer.write(index+1 +": " + highscoreList.get(index).username + " " + highscoreList.get(index).points + " " + highscoreList.get(index).totalPoints + "\n");
            }
            writer.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * Skapar/uppdaterar highscore
     * @param username spelarens username(namnet på spelarens sparfil)
     */
    public void createHighscore(String username){
        usernamehandler.writeUsernane(username);
        ArrayList<HighscoreData> highscoreList = sortHighscoreData(extractHighscoreData());
        writeHighscoreFile(highscoreList);
    }





}
