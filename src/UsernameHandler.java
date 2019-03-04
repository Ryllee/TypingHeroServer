import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class UsernameHandler {

    private File users;

    public UsernameHandler(){
        try {
            String url = System.getProperty("user.dir");
            users = new File(url+"/saveFiles/FileNames.txt");
        }catch (Exception e) {
            System.out.println(e);
        }
    }

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