import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
    클라이언트의 ID 할당 전용 스레드
 */
public class ClientIdManager extends Thread {
    // 0. Field
    private ClientInfo clientInfo;
    private Socket clientSocket;
    private ClientMap clientMap;
    private ClientHandler clientHandler;

    // 1. Construct
    public ClientIdManager(Socket clientSocket, ClientMap clientMap, ClientHandler clientHandler, ClientInfo clientInfo) {
        this.clientSocket = clientSocket;
        this.clientMap = clientMap;
        this.clientHandler = clientHandler;
        this.clientInfo = clientInfo;
    }

    // 2. Method
    @Override
    public void run() {
        try {
            System.out.println("ID 할당 스레드 실행");
            BufferedReader input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(this.clientSocket.getOutputStream());

            // 클라이언트에게 ID 요청
            output.println("Please type the ID you want to use.");

            System.out.println("1111");

            // ID 설정
            String clientID = input.readLine();
            System.out.println("Client ID set to: " + clientID);
            clientInfo.setId(clientID);

            clientMap.addClient(this.clientInfo.getId(), this.clientHandler);

            input.close();
            output.close();
        } catch (IOException e) {

        }
    }
}
