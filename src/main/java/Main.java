import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

public class Main {
    private static final int PORT = 12345;
    private static Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());

    public static void setClients(ClientHandler client){
        clients.add(client);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Сервер запущен. Ожидание подключений...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        }
    }

    private static void broadcast(String message) {
        System.out.println(message);
        synchronized (clients) {
            for (ClientHandler client : clients) {
                try {
                    client.sendMessage(message);
                } catch (Exception e) {
                    System.out.println("Не удалось отправить сообщение клиенту");
                }
            }
        }
    }
}
