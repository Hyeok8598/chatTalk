import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
    클라이언트와의 기본 통신 처리 담당
 */

public class ClientHandler extends Thread {
    // 0. Field
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;
    private ClientInfo clientInfo;
    private ClientMap clientMap;

    // 1. Construct
    public ClientHandler(Socket clientSocket, ClientMap clientMap) {
        this.clientSocket = clientSocket;
        this.clientMap = clientMap;
        this.clientInfo = new ClientInfo();

        try {
            this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.output = new PrintWriter(this.clientSocket.getOutputStream(), true);


        } catch(IOException e) {

        }
    }

    // 2. Method
    @Override
    public void run() {
        //ClientIdManager clientIdManager = new ClientIdManager(this.clientSocket, this.clientMap, this, this.clientInfo);
        //clientIdManager.start();

        try {
            //clientIdManager.join(); // ID 설정이 완료될 때까지 대기

            String message = "";
            while((message = this.input.readLine()) != null) {
                System.out.println(message);
                this.clientMap.broadCastMessage(this.clientInfo.getId() + " : " + message);
            }
        } catch(IOException e) {
            System.out.println("Error in ClientHandler: " + e.getMessage());
        } finally {
            // 자원 해제
            try {
                if (input != null) input.close();
                if (output != null) output.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public void sendMessage(String message) {
        this.output.println(message);
    }

    public ClientInfo getClientInfo() {
        return this.clientInfo;
    }
}
