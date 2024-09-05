import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter : ");
        String code = scanner.nextLine();



        if(code.equals("admin")) {
            Server server = new Server();

            server.start();
        }
        else if(code.equals("client")) {
            Client client = new Client();

            client.start();
        }
    }
}