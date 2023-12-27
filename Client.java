import java.io.*;
import java.net.*;

public class Client {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    public void connectToServer() throws IOException {
        socket = new Socket("127.0.0.1", 8080);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String serverResponse = in.readLine();
                        if (serverResponse == null) break;
                        System.out.println(serverResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void disconnect() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.connectToServer();

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        while ((userInput = stdIn.readLine()) != null) {
            client.sendMessage(userInput);
        }

        client.disconnect();
    }
}