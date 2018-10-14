package cs455.overlay.transport;

import java.io.IOException;
import java.net.Socket;

public class TCPConnection {

  private final TCPSender tcpSender;
  private final TCPReceiverThread receiverThread;
  private final Socket socket;

  private final String ipAddress;
  private final int portNumber;
  private final int serverSocketPort;

  public TCPConnection(Socket socket, int serverSocketPort) throws IOException {
    this.socket = socket;
    this.ipAddress = socket.getInetAddress().getHostAddress();
    this.portNumber = socket.getPort();
    this.serverSocketPort = serverSocketPort;

    this.tcpSender = new TCPSender(socket);
    this.receiverThread = new TCPReceiverThread(socket);

  }

  public void startThreads(){
    receiverThread.start();
  }

  public void sendMessage(byte[] messageToSend) throws IOException {
      tcpSender.sendData(messageToSend);
  }


  public boolean compareSocket(Socket socket1){
    return socket == socket1;
  }

  @Override
  public String toString(){
    return ipAddress + ":" + portNumber;
  }

  public void interruptReceiverThread(){
    receiverThread.interrupt();
  }

  public Socket getSocket() {
    return socket;
  }

  public int getSeverSocketPort() {
    return serverSocketPort;
  }
}
