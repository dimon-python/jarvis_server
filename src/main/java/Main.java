import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

public class Main {
    private static final int PORT = 12345;
    static Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());

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
}