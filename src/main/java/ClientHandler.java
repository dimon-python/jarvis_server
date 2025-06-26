import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String message;
    //private Boolean running = true;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    public void run(){
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            Main.setClients(this);

            while (true) {
                message = in.readLine();
                System.out.println(message);
            }

        } catch(IOException e){

        }
    }
}
