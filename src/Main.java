import java.net.ServerSocket;
import java.net.Socket;

public class Main {


    public static void main(String[] args){
        ServerSocket serversocket;
        Socket socket;
        try {
           serversocket = new ServerSocket(9999);
           System.out.println("Server now running, waiting for connections.");
           System.out.println("To quit use CTRL C.");
            while(true){
                socket = serversocket.accept();
                System.out.println("New connection found.");
                ClientThread clientthread = new ClientThread(socket);
                clientthread.run();
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
