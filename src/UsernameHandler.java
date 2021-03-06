import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Hanterar Usernames/filenames på server
 *
 * @author Eric Rylander
 * @version 2019-03-12
 */
public class UsernameHandler {

    private File users;

    /**
     * Skapar en UsernameHandler
     */
    public UsernameHandler(){
        try {
            users = new File("FileNames.txt");
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Skriver ett username i filen om det inte redan finns
     * @param username vilket username som ska skrivas
     * @return om den kunde skriva usernamet eller ej
     */
    public boolean writeUsernane(String username){
        ArrayList<String> usernameList = readUsernames();
        for(int i = 0; i < usernameList.size(); i++){
            if(username.equals(usernameList.get(i))){
                return false;
            }
        }
        try{
            FileWriter writer = new FileWriter(users,true);
            writer.write(username+ "\n");
            writer.close();
            return true;
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    /**
     * Läser in alla username från filen
     * @return en lista av alla username
     */
    public ArrayList<String> readUsernames(){
        String line = null;
        ArrayList<String> result = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(users));
            while((line=reader.readLine()) != null){
                result.add(line);
            }
            return result;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

}