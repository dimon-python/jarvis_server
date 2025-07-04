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

            while ((message = in.readLine()) != null) {  // Читаем, пока клиент не отключится
                broadcast(message);
            }
        } catch(IOException e){
            System.out.println("клиент отключился");

        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Ошибка при закрытии сокета: " + e.getMessage());
            }
            Main.clients.remove(this);  // Удаляем клиента из списка
        }

    }

    void sendMessage(String message){
        out.println(message);
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