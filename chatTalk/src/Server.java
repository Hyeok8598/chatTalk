import java.io.*;
import java.net.*;


public class Server {
    ServerSocket serverSocket;
    Socket clientSocket;
    BufferedReader input;
    PrintWriter output;
    String message;

    // Default Constructor
    public Server() {
        // 임의 할당
        makeServerSocket(12345);
    }

    private void makeServerSocket(int port) {
        if(serverSocket == null) {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("서버 소켓이 포트 " + port + "에서 열렸습니다.");
            } catch(IOException e) {
                System.out.println("서버 소켓 생성 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("이미 서버가 가동중입니다.");
        }
    }

    // 클라이언트로부터 메시지 처리
    private void handleClientMessages() throws IOException {
        while ((message = input.readLine()) != null) {
            System.out.println("Client: " + message);
            output.println("Server: " + message);
        }
    }

    public void start() {
        try {
            clientSocket = serverSocket.accept();
            System.out.println("클라이언트와 연결이 되었습니다.");

            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);

            handleClientMessages();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        } finally {
            // 리소스 정리
            try {
                if (input != null) input.close();
                if (output != null) output.close();
                if (clientSocket != null) clientSocket.close();
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                System.out.println("리소스 닫기 중 오류 발생: " + e.getMessage());
            }
        }
    }
}
