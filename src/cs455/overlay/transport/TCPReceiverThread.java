package cs455.overlay.transport;

import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.EventFactory;
import cs455.overlay.wireformats.Protocol;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class TCPReceiverThread extends Thread {

  private Socket socket;
  private DataInputStream din;
  public TCPReceiverThread(Socket socket) throws IOException {
    this.socket = socket;
    din = new DataInputStream(socket.getInputStream());
  }


  public void run() {
    int dataLength;
    while (!Thread.currentThread().isInterrupted()) {
      try {
          dataLength = din.readInt();
          byte[] data = new byte[dataLength];
          din.readFully(data, 0, dataLength);
          sendEvent(data);
      } catch (SocketException se) {
        System.out.println(se.getMessage());
        break;
      } catch (IOException ioe) {
        break;
      }
    }
    //System.out.println("Out of Receiver Thread");
  }

  private void sendEvent(byte[] data) throws IOException {

    Event event = EventFactory.getEvent(data);
    if(event.getType() == Protocol.OVERLAY_NODE_SENDS_REGISTRATION ||
        event.getType() == Protocol.OVERLAY_NODE_SENDS_DEREGISTRATION){
      EventFactory.sentToNode(event, socket);
    } else {
      EventFactory.sentToNode(event);
    }
  }

}
