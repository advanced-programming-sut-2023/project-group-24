import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1717);
        
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("New connection form: " + socket.getInetAddress() + ":" + socket.getPort());
//            new NodeController(socket, database).start();
        }
    }
}
