import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientMap {
    // 0. Field
    private Map<String, ClientHandler> clients;

    // 1. Construct
    public ClientMap(int port) {
        this.clients = new HashMap<>();
    }

    // 2. Method
    public void addClient(String clientId, ClientHandler handler) {
        this.clients.put(clientId, handler);
    }

    public void removeClient(String clientId) {
        this.clients.remove(clientId);
    }

    public ClientHandler getClientHandler(String clientId) {
        return clients.get(clientId);
    }

    public void broadCastMessage(String message) {
        for(ClientHandler handler : clients.values()) {
            handler.sendMessage(message);
        }
    }
}
