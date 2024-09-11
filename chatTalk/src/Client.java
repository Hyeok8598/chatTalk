import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    // 0. Field
    private Socket clientSocket;
    private BufferedReader input;
    private BufferedReader keyboard;
    private PrintWriter output;
    private ClientInfo clientInfo;

    // 1. Construct
    public Client() {
        makeClientSocket("localhost",12345);
    }

    // 2. Method
    private void makeClientSocket(String host, int port) {
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
            String message = "";
            while ((message = keyboard.readLine()) != null) {
                this.output.println(message);              // 사용자 입력을 서버로 전송
                System.out.println(this.input.readLine()); // 서버로부터 응답을 읽고 출력
            }
        } catch (IOException e) {
            System.out.println("입출력 중 오류 발생: " + e.getMessage());
        } finally {
            try {
                if (this.keyboard != null) this.keyboard.close();
                if (this.output != null) this.output.close();
                if (this.input != null) this.input.close();
            } catch (IOException e) {
                System.out.println("리소스 닫기 중 오류 발생: " + e.getMessage());
            }
        }
    }

    public void start() {
        try {
            // ID 지정
            this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.keyboard = new BufferedReader(new InputStreamReader(System.in));
            this.output = new PrintWriter(this.clientSocket.getOutputStream(), true);

            //System.out.println(this.input.readLine());

            //clientInfo = new ClientInfo();
            //clientInfo.setId(this.keyboard.readLine());
            //this.output.println(clientInfo.getId());
            //System.out.println("UserId : " + clientInfo.getId());

            handleUserInput();
        } catch (IOException e) {
            System.out.println("입출력 중 오류 발생: " + e.getMessage());
        } finally {
            try {
                if (this.keyboard != null) this.keyboard.close();
                if (this.output != null) this.output.close();
                if (this.input != null) this.input.close();
            } catch (IOException e) {
                System.out.println("리소스 닫기 중 오류 발생: " + e.getMessage());
            }
        }
    }
}