import java.io.IOException;
import java.net.*;

public class BackgroundServer extends Thread {
    private final InetAddress proxyAddress;
    private DatagramSocket socket;
    private final int proxyPort;
    private final String URL;
    private byte[] buf = new byte[256];
    private boolean running;
    private int port;

    public BackgroundServer(String URL,int port) throws UnknownHostException, SocketException {
        this.proxyAddress = InetAddress.getByName("localhost");
        this.proxyPort = 1024;
        this.URL = URL;
        this.socket= new DatagramSocket(port);
        this.port=port;
    }

    @Override
    public void run() {
        running=true;
        String initial="#B,"+port+","+URL;

        DatagramPacket initialPacked=new DatagramPacket(initial.getBytes(),initial.getBytes().length,proxyAddress,proxyPort);
        try {
            socket.send(initialPacked);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(running){
            String message="this is message from "+URL;
            DatagramPacket packet=new DatagramPacket(buf,buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String received=new String(packet.getData(),0,packet.getLength());
           /* if(received.contains("#END")){
                running=false;
            }*/
            DatagramPacket newPacket= new DatagramPacket(message.getBytes(),message.getBytes().length,packet.getAddress(),packet.getPort());
            try {
                socket.send(newPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //socket.close();

    }
}
