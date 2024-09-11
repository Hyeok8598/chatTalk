import java.io.*;
import java.net.*;


public class Server {
    private ServerSocket serverSocket;
    private ClientMap clientMap;

    // Default Constructor
    public Server() {
        // 임의 할당
        makeServerSocket(12345);
    }

    private void makeServerSocket(int port) {
        if(serverSocket == null) {
            try {
                serverSocket = new ServerSocket(port);
                clientMap = new ClientMap(port);

                System.out.println("서버 소켓이 포트 " + port + "에서 열렸습니다.");
            } catch(IOException e) {
                System.out.println("서버 소켓 생성 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("이미 서버가 가동중입니다.");
        }
    }

    public void start() {
        while(true) {
            try {
                Socket clientSocket = serverSocket.accept();

                ClientHandler clientHandler = new ClientHandler(clientSocket, this.clientMap);
                clientMap.addClient("111", clientHandler);
                clientHandler.start();
            } catch(IOException e) {

            }
        }
    }
}
