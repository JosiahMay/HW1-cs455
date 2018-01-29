package cs455.overlay.node;

import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.EventFactory;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Registry implements Node{

  public static void main(String[] args) {

    if (args.length != 1) {
      System.err.println("Invalid number of arguments: " + args.length + " expected 1");
      System.err.println("java cs455.overlay.node.Registry  <port number>");
      System.exit(1);
    }
    ServerSocket serverSocket;
    int portNumber = Integer.parseInt(args[0]);
    try {
      System.out.println(portNumber);
      System.out.println(InetAddress.getLocalHost());
      serverSocket = new ServerSocket(portNumber);

      System.out.println("Opened server socket on " + serverSocket.getLocalPort());
      //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
      //String fromUser = stdIn.readLine();
      //System.out.println(fromUser);
      Socket clientSocket = serverSocket.accept();
      DataInputStream din = new DataInputStream( clientSocket.getInputStream());
      int dataLength;
      while(clientSocket != null)
      {
        try {
          dataLength = din.readInt();
          byte[] data = new byte[dataLength];
          din.readFully(data, 0, dataLength );
          Event event = EventFactory.getEvent(data);
          System.out.println(event);
          din.close();
        } catch (SocketException se) {
          System.out.println(se.getMessage());
          break;
        } catch (IOException ioe) {
          System.out.println(ioe.getMessage()) ;
          break;
        }
      }
      System.out.println("Opened socket on: " + clientSocket.getLocalSocketAddress() + clientSocket.getPort());
      serverSocket.close();




      System.out.println("Closed server socket on " + serverSocket.getLocalPort());
    } catch (IOException e) {
      e.printStackTrace();
    }



  }
}