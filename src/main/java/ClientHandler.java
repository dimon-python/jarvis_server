import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread{
    private Socket socket;
    private static PrintWriter out;
    private BufferedReader in;
    private static String message;
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
                if (message != null){
                    broadcast(message);
                }
            }

        } catch(IOException e){

        }
    }

    static void sendMessage(String message){
        out.println(ClientHandler.message);
    }

    public static void broadcast(String message) {
        System.out.println(message);
        synchronized (Main.clients) {
            for (ClientHandler client : Main.clients) {
                try {
                    client.sendMessage(message);
                } catch (Exception e) {
                    System.out.println("Не удалось отправить сообщение клиенту");
                }
            }
        }
    }
}
