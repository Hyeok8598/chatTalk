import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    // Field
    Socket clientSocket;
    BufferedReader input;
    BufferedReader keyboard;
    PrintWriter output;
    String message;

    public Client() {
        makeClientSocket("localhost",12345);
    }

    private void makeClientSocket(String host,int port) {
        if(clientSocket == null) {
            try {
                clientSocket = new Socket(host, port);
                System.out.println("클라이언트 소켓이 포트 " + port + "에서 열렸습니다.");
            } catch(IOException e) {
                System.out.println("클라이언트 소켓 생성 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("클라이언트가 할당되어 있습니다.");
        }
    }

    private void handleUserInput() {
        try {
            while ((message = keyboard.readLine()) != null) {
                output.println(message);         // 사용자 입력을 서버로 전송
                System.out.println(input.readLine()); // 서버로부터 응답을 읽고 출력
            }
        } catch (IOException e) {
            System.out.println("입출력 중 오류 발생: " + e.getMessage());
        } finally {
            // 리소스 정리
            try {
                if (keyboard != null) keyboard.close();
                if (output != null) output.close();
                if (input != null) input.close();
            } catch (IOException e) {
                System.out.println("리소스 닫기 중 오류 발생: " + e.getMessage());
            }
        }
    }

    public void start() {
        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            keyboard = new BufferedReader(new InputStreamReader(System.in));
            output = new PrintWriter(clientSocket.getOutputStream(), true);

            handleUserInput();
        } catch (IOException e) {
            System.out.println("입출력 중 오류 발생: " + e.getMessage());
        } finally {
            try {
                if (keyboard != null) keyboard.close();
                if (output != null) output.close();
                if (input != null) input.close();
            } catch (IOException e) {
                System.out.println("리소스 닫기 중 오류 발생: " + e.getMessage());
            }
        }
    }
}
