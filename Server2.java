import java.io.*;
import java.net.*;

class Server2 {
    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    public Server2() {
        try {
            server = new ServerSocket(7777);
            System.out.println("server is ready to accept connection");
            System.out.println("Waiting...");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        } catch (Exception e) {
            // e.printStackTrace();
            // System.out.println("Conncetion Close");
        }
    }

    public void startReading() {
        // thread-read karke deta rahega
        Runnable r1 = () -> {

            System.out.println("reader started...");
            try {
                while (!socket.isClosed())

                {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated the chat");
                        socket.close();
                        break;

                    }
                    System.out.println("Client :" + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // System.out.println("Connection Close");
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        Runnable r2 = () -> {
            System.out.println("writer started...");
            try {

                while (!socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }

                }
                System.out.println("Conncetion Close");
            } catch (Exception e) {
                e.printStackTrace();
                // System.out.println("Conncetion Close");
            }
        };
        new Thread(r2).start();

    }

    public static void main(String[] args) {
        System.out.println("this is server.. going to start server");
        new Server2();
    }
}