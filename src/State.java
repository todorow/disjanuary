import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class State {
    public static void main(String[] args) throws IOException {

        PoxyServer poxyServer =new PoxyServer();
        poxyServer.start();
        BackgroundServer proekt1 =new BackgroundServer("www.proekt1.mk",1060);
        proekt1.start();
        BackgroundServer google =new BackgroundServer("www.google.com",1063);
        google.start();
        BackgroundServer finki =new BackgroundServer("www.finki.com",1094);
        finki.start();
        Client client=new Client();
        client.sendRequestTo("www.google.com");
        client.sendRequestTo("www.finki.com");
        client.sendRequestTo("www.proekt1.mk");

    }
}
