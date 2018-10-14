package cs455.overlay.transport;

import cs455.overlay.node.MessagingNode;
import cs455.overlay.node.Node;
import cs455.overlay.wireformats.OverlayNodeSendsRegistration;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TCPServerThread extends Thread{

  private final Node parentNode;
  private final int portNumber;
  private TCPConnectionsCache connectionsCache;
  private ServerSocket serverSocket;
  private Socket registrySocket;



  public TCPServerThread(Node parentNode, int portNumber){
    this.parentNode = parentNode;
    this.portNumber = portNumber;
    this.connectionsCache = new TCPConnectionsCache();
  }

  @Override
  public void run(){

    try {

      serverSocket = new ServerSocket(portNumber);
      parentNode.setTcpConnectionsCache(connectionsCache);

      System.out.println(getClassName() + " setting up server socket at:");
      System.out.println(InetAddress.getLocalHost().getHostAddress()
          + ":" + serverSocket.getLocalPort());

      if(parentNode instanceof MessagingNode)
        setupRegistry();


      while (!Thread.currentThread().isInterrupted()) {
        Socket clientSocket = serverSocket.accept();
        TCPConnection connection = new TCPConnection(clientSocket, serverSocket.getLocalPort());
        connection.startThreads();
        connectionsCache.addConnection(connection);
      }

      connectionsCache.interruptAll();

    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }



  private void setupRegistry() throws IOException {
    String registryIpAddress = ((MessagingNode) parentNode).getRegistryIpAddress();
    int registryPort = ((MessagingNode) parentNode).getRegistryPort();
    System.out.println(getClassName() + " connecting to registry at: "
        + registryIpAddress + ":" + registryPort);
    try {

      registrySocket = new Socket(registryIpAddress, registryPort);
      TCPConnection connection = new TCPConnection(registrySocket, serverSocket.getLocalPort());
      connection.startThreads();
      connectionsCache.setupRegistry(connection);

      OverlayNodeSendsRegistration message =
          new OverlayNodeSendsRegistration(getServerSocketIpAddress(),
              getSeverPortNumber());

      sendMessageToRegistry(message.getBytes());
    } catch (ConnectException e) {
      System.out.println("Unable to connect to " + registryIpAddress + ":" + registryPort
          + " start registry before messaging nodes");
      System.exit(1);
    }
  }

  public void connectToNode(String ipAddress, int portNumber, int IdNumber) throws SocketException {
    try {

      Socket socket = new Socket(ipAddress, portNumber);

      TCPConnection connection = new TCPConnection(socket, serverSocket.getLocalPort());
      connection.startThreads();

      connectionsCache.addConnection(IdNumber, connection);

    } catch (UnknownHostException e) {
      e.printStackTrace();
      System.exit(1);
    } catch (SocketException e){
      throw new SocketException("Unable to connect to node at " + ipAddress + ":" + portNumber);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  public int getSeverPortNumber(){
    return serverSocket.getLocalPort();
  }

  public String getServerSocketIpAddress() throws UnknownHostException {
    return InetAddress.getLocalHost().getHostAddress();
  }

  public void sendMessageToRegistry(byte[] message) {
    try {
      connectionsCache.sendMessageToRegister(message);
    } catch (IOException e) {
      System.err.println("Error sending message to Registry:");
      e.printStackTrace();
      System.exit(1);
    }
  }

  private String getClassName(){
    return parentNode.getClass().getSimpleName();
  }
}
