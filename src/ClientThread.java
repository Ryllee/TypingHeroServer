import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThread extends Thread {

    private Socket socket;
    private static final int FILE_SIZE = 10000;
    private HighscoreHandler highscorehandler;
    private UsernameHandler usernamehandler;

    /**
     * Skapar en ny ClientThread
     * @param socket vilken socket den ska kommunicera på
     * @param highscorehandler vilken highscorehandler den ska känna till
     * @param usernamehandler vilken usernamehandler den ska känna till
     */
    public ClientThread(Socket socket, HighscoreHandler highscorehandler,UsernameHandler usernamehandler){
       this.socket = socket;
       this.highscorehandler = highscorehandler;
       this.usernamehandler = usernamehandler;
    }

    /**
     * Vad ClientThread ska göra
     */
    @Override
    public void run() {
        System.out.println("New connection now working.");
        try {
            DataInputStream commandInput = new DataInputStream(socket.getInputStream());
            String command = commandInput.readUTF();
            if (command.equals("SAVE")) {
                receiveSaveFile();
            } else if (command.equals("LOAD")) {
                sendSaveFile();
            } else if (command.equals("HIGHSCORE")){
                sendHighscore();
            }
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    /**
     * Skickar en sparfil från server till en client
     */
    public void sendSaveFile() {
        boolean fileExist = false;
        try {
            DataInputStream receiveFileName = new DataInputStream(socket.getInputStream());
            String FILE_TO_SEND = (String) receiveFileName.readUTF();
            ArrayList<String> usernames = usernamehandler.readUsernames();
            DataOutputStream write = new DataOutputStream(socket.getOutputStream());
            for(String us : usernames){
                if(us.equals(FILE_TO_SEND+".txt")){
                    write.writeUTF("TRUE");
                    write.flush();
                    fileExist = true;
                    break;
                }
                fileExist = false;
            }
            if(fileExist == true) {

                String url = System.getProperty("user.dir");
                File savefile = new File(url + "/saveFiles/" + FILE_TO_SEND + ".txt");
                byte[] fileSize = new byte[(int) savefile.length()];
                FileInputStream fileIn = new FileInputStream(savefile);
                BufferedInputStream fileBIn = new BufferedInputStream(fileIn);
                fileBIn.read(fileSize, 0, fileSize.length);
                OutputStream output = socket.getOutputStream();
                System.out.println("Sending file(" + fileSize.length + " bytes)");
                output.write(fileSize, 0, fileSize.length);
                output.flush();
                fileBIn.close();
                fileIn.close();
                System.out.println("File" + FILE_TO_SEND + ".txt sent");
            }
            else{
                write.writeUTF("FALSE");
                write.flush();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Tar emot en sparfil från client och sparar den på server
     */
    public void receiveSaveFile() {
        try
        {

            DataInputStream receiveFileName = new DataInputStream(socket.getInputStream());
            String FILE_TO_RECEIVE = (String) receiveFileName.readUTF();

            String url = System.getProperty("user.dir");
            File file = new File(url+"/saveFiles/"+ FILE_TO_RECEIVE);
            byte[] fileSize = new byte[FILE_SIZE];
            InputStream input = socket.getInputStream();
            FileOutputStream fileOut = new FileOutputStream(file);
            BufferedOutputStream fileBOut = new BufferedOutputStream(fileOut);
            int bytesread = input.read(fileSize, 0, fileSize.length);
            fileBOut.write(fileSize,0,bytesread);
            fileBOut.flush();
            System.out.println("File " + FILE_TO_RECEIVE + " downloaded(" + bytesread + " bytes read)");
            fileBOut.close();
            fileOut.close();
            //SEND RESPONSE TO CLIENT
            DataOutputStream response = new DataOutputStream(socket.getOutputStream());
            response.writeUTF("Server has downloaded file");
            response.flush();
            highscorehandler.createHighscore(FILE_TO_RECEIVE);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    /**
     * Skickar ett Highscore till en client
     */
    public void sendHighscore(){
        ArrayList<HighscoreData> highscoreList = highscorehandler.sortHighscoreData(highscorehandler.extractHighscoreData());
        try{
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            int maxindex;
            if(highscoreList.size()<5){
                maxindex = highscoreList.size();
            }else{
                maxindex = 5;
            }
            output.writeUTF(Integer.toString(maxindex));
            for(int i = 0; i <maxindex; i++){
                output.writeUTF(highscoreList.get(i).username);
                output.writeUTF(Float.toString(highscoreList.get(i).totalPoints));
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
