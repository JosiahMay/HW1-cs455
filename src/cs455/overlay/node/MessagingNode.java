package cs455.overlay.node;

import cs455.overlay.wireformats.OverlayNodeSendsRegistration;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MessagingNode {

  private String ipAddress;
  private int port;
  private ServerSocket serverSocket;

  public static void main(String[] args) {

    if (args.length != 2) {
      System.err.println("Invalid number of arguments: " + args.length + " expected 2");
      System.err.println("java cs455.overlay.node.MessagingNode <registry-host> <registry-port>");
      System.exit(1);
    }

    String registryIP = args[0];
    int registryPortNumber = Integer.parseInt(args[1]);

    System.out.println("IP: " + registryIP + ":" + registryPortNumber);

    try {

      ServerSocket serverSocket = new ServerSocket(0);

      System.out.println(InetAddress.getLocalHost().getHostAddress());
      System.out.println(serverSocket.getLocalPort());

      serverSocket.close();

      Socket registrySocket = new Socket(registryIP, registryPortNumber);
      System.out.println("Getting socket port: "+ registrySocket.getPort());

      DataOutputStream dout = new DataOutputStream(registrySocket.getOutputStream());

      OverlayNodeSendsRegistration data=
          new OverlayNodeSendsRegistration(InetAddress.getLocalHost().getHostAddress(),
              serverSocket.getLocalPort());
      System.out.println(data);
      byte[] dataToSend = data.getBytes();
      int dataLength = dataToSend.length;
      dout.writeInt(dataLength);
      dout.write(dataToSend, 0, dataLength);
      dout.flush();
      registrySocket.close();
      dout.close();


    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
