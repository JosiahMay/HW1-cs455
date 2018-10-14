package cs455.overlay.transport;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPSender {

    private Socket socket;
    private DataOutputStream dout;


    public TCPSender(Socket socket) throws IOException {
      this.socket = socket;
      dout = new DataOutputStream(socket.getOutputStream());
    }
    public synchronized void sendData(byte[] dataToSend) throws IOException {
      //System.err.println("Sending data to " + socket.getPort());
      if(socket.isOutputShutdown()){
        System.out.println("Socket at " + socket.getLocalSocketAddress().toString() + " closed");
      }
      int dataLength = dataToSend.length;
      dout.writeInt(dataLength);
      dout.write(dataToSend, 0, dataLength);
      dout.flush();
    }


}
