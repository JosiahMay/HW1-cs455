package cs455.overlay.transport;

import java.io.IOException;
import java.net.Socket;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * THIS CLASS NEEDS TO BE COMPLETELY REMADE!! IT IS NOT THREAD SAFE AND I KNOW ITS LOGIC IT THE
 * REASON OVERLAY 3 DEADLOCK
 */


public class TCPConnectionsCache {

  private final ArrayList<TCPConnection> connectionsNotRegistered = new ArrayList<>();
  private final TreeMap<Integer, TCPConnection> connectionRegistered = new TreeMap<>();
  private final TreeMap<Integer, Integer> serverSocketPorts = new TreeMap<>();
  private TCPConnection registryConnection = null;

  public void setupRegistry(TCPConnection registry) {
      if (registryConnection != null)
        throw new IllegalArgumentException("The registry connection already setup");

      this.registryConnection = registry;
  }

  public synchronized void sendMessageToRegister(byte[] message) throws IOException {
      registryConnection.sendMessage(message);
  }

  public synchronized void addConnection(TCPConnection connection){
      connectionsNotRegistered.add(connection);
  }

  public void sendMessage(int idNum, byte[] message) throws IOException {

      try {
          connectionRegistered.get(idNum).sendMessage(message);
      } catch (NullPointerException e){
        System.err.println("Unable to find id: " + idNum + " in registered nodes.");
        throw e;
      } catch (ServerException e){
        System.err.println("Error when sending to Node: " + idNum);
        System.err.println(e.getMessage());
        System.err.flush();
      }
  }

  public synchronized boolean findDuplicateNode(Socket socket){
      for (TCPConnection connection : connectionRegistered.values()) {
        if(connection.compareSocket(socket)){
          return true;
        }
      }
      return false;
  }

  public synchronized void addConnection(int idNumber, TCPConnection connection){
      if(connectionRegistered.containsKey(idNumber))
      {
        throw new IllegalArgumentException("ID Number: " + idNumber + " already registered");
      }

      connectionRegistered.put(idNumber, connection);
  }

  public synchronized void registerConnection(int idNumber, Socket socket, int port){
    TCPConnection connectionToAdd = null;
      for (int i = 0; i < connectionsNotRegistered.size(); i++) {
        if(connectionsNotRegistered.get(i).compareSocket(socket)){
          connectionToAdd = connectionsNotRegistered.remove(i);
          break;
        }
      }

      if(connectionToAdd == null){
        throw new IllegalArgumentException("Requested connection "
            + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() +
            " not in the in the connectionsNotRegistered arraylist");
      }
      if(!findDuplicateNode(socket)) {
        addConnectionToMap(idNumber, connectionToAdd);
        serverSocketPorts.put(idNumber, port);
      }
}

  private synchronized void addConnectionToMap(int idNumber, TCPConnection connectionToAdd) {
      connectionRegistered.put(idNumber, connectionToAdd);
  }

  public synchronized void interruptAll(){
    try {
      registryConnection.interruptReceiverThread();
    } catch (NullPointerException e){
      // ignore
    }
    clearConnections();
  }

  public synchronized void clearConnections() {
    connectionsNotRegistered.clear();
    connectionRegistered.clear();
    serverSocketPorts.clear();
  }

  public int getConnectionRegisteredSize(){
      return connectionRegistered.size();
  }

  public synchronized void sendMessageToUnRegisteredNode(Socket socket, byte[] message) throws IOException {
      for (TCPConnection connection: connectionsNotRegistered) {
        if(connection.compareSocket(socket)){
          connection.sendMessage(message);
          break;
        }
      }
  }

  public synchronized TCPConnection getRegistryConnection(int idNum) {
      return connectionRegistered.get(idNum);
  }

  public synchronized boolean deregisterNode(int idNum){
    return connectionRegistered.remove(idNum) != null;
  }

  public synchronized int getServerSocketPorts(int id) {
    //System.out.println(serverSocketPorts.values());
    return serverSocketPorts.get(id);
  }

  public synchronized TreeMap<Integer, TCPConnection> getConnectionRegistered() {
    return connectionRegistered;
  }

}
