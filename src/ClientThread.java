import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket socket;
    private static final int FILE_SIZE = 10000;
    private String FILE_TO_RECEIVE = "test.txt";

    public ClientThread(Socket socket){
       this.socket = socket;
    }
    @Override
    public void run() {
        System.out.println("New connection now working.");
        try
        {
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
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
