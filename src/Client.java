import java.io.IOException;
import java.net.*;

public class Client {
private DatagramSocket socket;
private InetAddress address;
private byte [] buf= new byte[256];

    public Client() throws SocketException, UnknownHostException {
        socket=new DatagramSocket();
        address=InetAddress.getByName("localhost");
    }
    public void sendRequestTo(String URl) throws IOException {

        String request="#S,"+URl;
        DatagramPacket packet = new DatagramPacket(request.getBytes(),request.getBytes().length,address,1024);
        socket.send(packet);
        packet=new DatagramPacket(buf,buf.length);
        socket.receive(packet);
        String responseProxy=new String(buf,0,buf.length);
        String [] arrayParams=responseProxy.split(",");
        int port= Integer.parseInt(arrayParams[1]);
        String URL=arrayParams[2];
        packet =new DatagramPacket(responseProxy.getBytes(),responseProxy.getBytes().length,address,port);
        socket.send(packet);
        packet=new DatagramPacket(buf,buf.length);
        socket.receive(packet);
        String responseBacgroundServer=new String(packet.getData(),0,packet.getLength());
        System.out.println(responseBacgroundServer);
        //socket.close();




    }
}
