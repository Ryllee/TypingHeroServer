import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    /**
     * Gör det som ska ske vid program start
     * @param args
     */
    public static void main(String[] args){
        ServerSocket serversocket;
        Socket socket;
        UsernameHandler usernamehandler = new UsernameHandler();
        HighscoreHandler highscorehandler = new HighscoreHandler(usernamehandler);
        try {
           serversocket = new ServerSocket(9999);
           System.out.println("Server now running, waiting for connections.");
           System.out.println("To quit use CTRL C.");
            while(true){
                socket = serversocket.accept();
                System.out.println("New connection found.");
                ClientThread clientthread = new ClientThread(socket,highscorehandler);
                clientthread.run();
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
