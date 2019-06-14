import java.io.IOException;
import java.net.*;
import java.util.HashMap;

public class PoxyServer extends Thread {
private DatagramSocket socket;
private HashMap<String,Integer> backgroundServers;
private boolean running;
private byte[] buf = new byte[256];

    public PoxyServer() throws SocketException, UnknownHostException {
        this.socket = new DatagramSocket(1024,InetAddress.getByName("localhost"));
        this.backgroundServers = new HashMap<>();
    }

    @Override
    public void run() {
        running=true;

        while (running){
            buf=new byte[256];
            DatagramPacket packet = new
                    DatagramPacket(buf,buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String received = new String(packet.getData(),0,packet.getLength());
            if(received.contains("#B")){
                String[] arrayParams = received.split(",");
               Integer port =Integer.parseInt(arrayParams[1]);
               String URL=arrayParams[2];
               backgroundServers.put(URL,port);
            }else if(received.contains("#S")){
                String[] arrayParams = received.split(",");
                String URL=arrayParams[1];
                Integer port =backgroundServers.get(URL);
                int requestPort=packet.getPort();
                InetAddress address=packet.getAddress();
                String response ="#R,"+ port +","+URL;
                DatagramPacket newPacket=new DatagramPacket(response.getBytes(),response.getBytes().length,address,requestPort);
                try {
                    //sending to client what port
                    socket.send(newPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else {
                // everything except flags S and B it means shut down the proxy server
                //running =false;
            }


        }
        socket.close();
    }
}
