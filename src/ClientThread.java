import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket socket;
    private static final int FILE_SIZE = 10000;
    private HighscoreHandler highscorehandler;

    public ClientThread(Socket socket, HighscoreHandler highscorehandler){
       this.socket = socket;
       this.highscorehandler = highscorehandler;
    }
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
            }
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public void sendSaveFile() {
        try {
            DataInputStream receiveFileName = new DataInputStream(socket.getInputStream());
            String FILE_TO_SEND = (String) receiveFileName.readUTF();
            File savefile = new File(FILE_TO_SEND + ".txt");
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
        } catch (Exception e) {

        }
    }

    public void receiveSaveFile() {
        try
        {

            DataInputStream receiveFileName = new DataInputStream(socket.getInputStream());
            String FILE_TO_RECEIVE = (String) receiveFileName.readUTF();

            byte[] fileSize = new byte[FILE_SIZE];
            InputStream input = socket.getInputStream();
            FileOutputStream fileOut = new FileOutputStream(FILE_TO_RECEIVE);
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
            highscorehandler.createHighscore();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
