package cs455.overlay.node;

import cs455.overlay.routing.RoutingEntry;
import cs455.overlay.routing.RoutingTable;
import cs455.overlay.transport.TCPConnection;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.util.InputCommands;

import cs455.overlay.util.PacketsSentInfo;
import cs455.overlay.util.StatisticsCollectorAndDisplay;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.NodeReportsOverlaySetupStatus;
import cs455.overlay.wireformats.OverlayNodeReportsTaskFinished;
import cs455.overlay.wireformats.OverlayNodeReportsTrafficSummary;
import cs455.overlay.wireformats.OverlayNodeSendsDeregistration;
import cs455.overlay.wireformats.OverlayNodeSendsRegistration;
import cs455.overlay.wireformats.Protocol;
import cs455.overlay.wireformats.RegistryReportsDeregistrationStatus;
import cs455.overlay.wireformats.RegistryReportsRegistrationStatus;

import cs455.overlay.wireformats.RegistryRequestsTaskInitiate;
import cs455.overlay.wireformats.RegistryRequestsTrafficSummary;
import cs455.overlay.wireformats.RegistrySendsNodeManifest;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class Registry extends Node{

  private final Object overlayLock = new Object();
  private TreeMap<Integer, RoutingTable> overlay = null;

  private final Object nodesLock = new Object();
  private TreeSet<Integer> allNodeIds = null;

  private TreeSet<Integer> nodesFinished = null;
  private TreeMap<Integer, PacketsSentInfo> trafficReport;



  private final int portNumber;


  private Registry(int portNum){
    super();
    this.portNumber = portNum;

  }

  private void run(){

      serverThread = new TCPServerThread(this, portNumber);

      serverThread.start();

      while(!commandLineParser.isInterrupted()){

      }

      System.out.println("Finishing program");
      serverThread.interrupt();


  }


  public static void main(String[] args) {

    if (args.length != 1) {
      System.err.println("Invalid number of arguments: " + args.length + " expected 1");
      System.err.println("java cs455.overlay.node.Registry  <port number>");
      System.exit(1);
    }

    RandomNodeID.seedArray(MAX_NODES);
    Registry r = new Registry(Integer.parseInt(args[0]));

    r.setupCommandLineParser();
    r.run();


  }

  @Override
  public void onEvent(Event event) {
    try {
      switch (event.getType()) {
        case Protocol.NODE_REPORTS_OVERLAY_SETUP_STATUS:
          overlayStatus(event);
          break;
        case Protocol.OVERLAY_NODE_REPORTS_TASK_FINISHED:
          nodesFinished(event);
          break;
        case Protocol.OVERLAY_NODE_REPORTS_TRAFFIC_SUMMARY:
          processTrafficSummary(event);
          break;
        default:
          System.err.println("Unknown event given: " + event.getType());
          break;
      }
    } catch (IOException e) {
      System.err.println("Error in running events:");
      e.printStackTrace();
      System.exit(1);
    }

  }

  private void processTrafficSummary(Event event) {

    OverlayNodeReportsTrafficSummary ev = (OverlayNodeReportsTrafficSummary) event;

    synchronized (trafficReport){

      trafficReport.put(ev.getIdNum(), ev.getReport());

      if(trafficReport.keySet().equals(allNodeIds)){

        StatisticsCollectorAndDisplay.printTrafficReport(trafficReport);
      }

    }

  }

  private void nodesFinished(Event event) throws IOException {
    OverlayNodeReportsTaskFinished ev =  (OverlayNodeReportsTaskFinished) event;

    synchronized (nodesLock){
      nodesFinished.add(ev.getIdNum());

      if(nodesFinished.equals(allNodeIds)){
        synchronized (this){
          try {
            System.out.println("Waiting for all nodes to send traffic report");
            wait(20000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

        trafficReport = new TreeMap<>();

        for (Integer i: overlay.keySet()) {
          connectionsCache.sendMessage(i,
              new RegistryRequestsTrafficSummary().getBytes());
        }
      }
    }

  }

  private void overlayStatus(Event event) {
    NodeReportsOverlaySetupStatus ev = (NodeReportsOverlaySetupStatus)event;
    if(ev.getId() == -1){
      System.out.println("Node unable to connect to another node setup");
      System.out.println(ev.getMessage());
      return;
    }
    synchronized (nodesLock){
      nodesFinished.add(ev.getId());



      if(nodesFinished.equals(allNodeIds)){
        System.out.println("Registry now ready to initiate tasks.");
      }
    }
  }


  private synchronized void deregisterNode(Event event, Socket socket) throws IOException {
    OverlayNodeSendsDeregistration ev = (OverlayNodeSendsDeregistration) event;

    String ip = socket.getInetAddress().getHostAddress();

    if(!ip.equals(ev.getIpAddress())){
      RegistryReportsDeregistrationStatus response = new RegistryReportsDeregistrationStatus(-1,
          "Information on socket did not match packet:\nExpected: "
              + ip + ":" + socket.getPort() + " Received: "
              + ev.getIpAddress() + ":" + ev.getPortNumber());
      System.out.println(response.getMessage());
      connectionsCache.sendMessageToUnRegisteredNode(socket, response.getBytes());
      return;
    }

    TCPConnection node = connectionsCache.getRegistryConnection(ev.getIdNumber());
    if(node == null){
      RegistryReportsDeregistrationStatus response = new RegistryReportsDeregistrationStatus(-1,
          "Node ID: " + ev.getIdNumber() + " not in registered nodes");
      System.out.println(response.getMessage());
      connectionsCache.sendMessageToUnRegisteredNode(socket, response.getBytes());
      return;
    }

    if(!node.compareSocket(socket)){
      RegistryReportsDeregistrationStatus response = new RegistryReportsDeregistrationStatus(-1,
          "Node ID: " + ev.getIdNumber() + " did not match the ip:port"
              +"\nExpected: " + ip + ":" + socket.getPort() + " Received: "
          + ev.getIpAddress() + ":" + ev.getPortNumber());
      System.out.println(response.getMessage());
      connectionsCache.sendMessageToUnRegisteredNode(socket, response.getBytes());
      return;
    }

    RegistryReportsDeregistrationStatus response = new RegistryReportsDeregistrationStatus(ev.getIdNumber(),
        "Deregistering node " + ev.getIdNumber());

    connectionsCache.sendMessage(ev.getIdNumber(), response.getBytes());
    connectionsCache.deregisterNode(ev.getIdNumber());

    System.out.println(response.getMessage());


  }

  private synchronized void registerNode(Event event, Socket socket) throws IOException {
    OverlayNodeSendsRegistration ev = (OverlayNodeSendsRegistration) event;
    String ip = socket.getInetAddress().getHostAddress();

    if(!ip.equals(ev.getIpAddress())){
      RegistryReportsRegistrationStatus response = new RegistryReportsRegistrationStatus(-1,
          "Information on socket did not match packet:\nExpected: "
              + ip + ":" + socket.getPort() + " Received: "
              + ev.getIpAddress() + ":" + ev.getPort());
      System.out.println(response.getMessage());
      connectionsCache.sendMessageToUnRegisteredNode(socket, response.getBytes());
      return;
    }

    if(connectionsCache.findDuplicateNode(socket)){
      RegistryReportsRegistrationStatus response = new RegistryReportsRegistrationStatus(-1,
          "Node at" + ip + ":" + socket.getPort() + " already registered");
      System.out.println(response.getMessage());
      connectionsCache.sendMessageToUnRegisteredNode(socket, response.getBytes());
      return;
    }

    int nodeId = RandomNodeID.getIdNumber();
    RegistryReportsRegistrationStatus response =
        new RegistryReportsRegistrationStatus(nodeId,
        "Registration request successful. ID: " + nodeId + " The number of messaging nodes "
            + "currently  in overlay is (" + connectionsCache.getConnectionRegisteredSize() + ")");
    connectionsCache.registerConnection(nodeId, socket, ev.getPort());

    connectionsCache.sendMessage(nodeId, response.getBytes());

    System.out.println("Registering node " + ev.getIpAddress() + ":"
        + ev.getPort()+ " with ID: " + nodeId);

  }

  private boolean compareSocketInfo(String ip1, String ip2 ) {
    return !ip1.equals(ip2);
  }

  @Override
  public void onEvent(Event event, Socket socket) {
    try {
      switch (event.getType()){
        case Protocol.OVERLAY_NODE_SENDS_REGISTRATION:
          registerNode(event, socket);
          break;
        case  Protocol.OVERLAY_NODE_SENDS_DEREGISTRATION:
          deregisterNode(event, socket);
          break;
      }
    } catch (IOException e) {
      System.err.println("Error in running commands:");
      e.printStackTrace();
      System.exit(1);
    }

  }


  @Override
  public void onCommand(InputCommands command) {
    try {
      switch (command.command){
        case SETUP_OVERLAY:
          setupOverlay(command.numberOfItems);
          break;
        case START_MESSAGING:
          startMessaging(command.numberOfItems);
          break;
        case LIST_MESSAGING_NODES:
          listMessagingNodes();
          break;
        case LIST_ROUTING_TABLES:
          listRoutingTables();
          break;
        default:
          System.err.println("Unknown command type given");
      }
    } catch (IOException e) {
      System.err.println("Error in running commands:");
      e.printStackTrace();
      System.exit(1);
    }
  }

  private void listRoutingTables() {
    synchronized (overlay){
      StatisticsCollectorAndDisplay.listRoutingTable(overlay);
    }

  }

  private void listMessagingNodes() {
    synchronized (connectionsCache){
      StatisticsCollectorAndDisplay.listMessagingNodes(connectionsCache.getConnectionRegistered());
    }
  }

  private void startMessaging(int numberOfItems) throws IOException {

    synchronized (overlayLock) {

      if (overlay == null) {
        System.out.println("No overlay set up run setup-overlay "
            + "<number of routing table entries> first");
        return;
      }

      nodesFinished = new TreeSet<>();

      for (Integer i: allNodeIds) {
        connectionsCache.sendMessage(i, new RegistryRequestsTaskInitiate(numberOfItems).getBytes());
      }

    }
  }

  private void setupOverlay(int numberOfItems) throws IOException {
    synchronized (overlayLock) {
      nodesFinished = new TreeSet<>();
      trafficReport = new TreeMap<>();
      synchronized (connectionsCache) {
        fillOverlayRoutingTables(numberOfItems);
        fillAllNodeIds();
        sendOverlay();
      }
    }
  }

  private void sendOverlay() throws IOException {

    int[] allNodes = new int[allNodeIds.size()];
    int node = 0;
    for (Integer in: allNodeIds) {
      allNodes[node] = in;
      node++;
    }
    allNodeIds.toArray(new Integer[allNodeIds.size()]);

    for (Integer i: overlay.keySet()) {
      connectionsCache.sendMessage(i,
          new RegistrySendsNodeManifest(overlay.get(i), allNodes).getBytes());
    }
  }

  private void fillAllNodeIds() {
    TreeMap<Integer, TCPConnection> tree = connectionsCache.getConnectionRegistered();
    allNodeIds = new TreeSet<>(tree.keySet());
  }

  private void fillOverlayRoutingTables(int numberOfItems) {

    overlay = new TreeMap<>();
    TreeMap<Integer, TCPConnection> tree = connectionsCache.getConnectionRegistered();
    ArrayList<Integer> keys = new ArrayList<>(tree.keySet());
    for (int i = 0; i < keys.size(); i++) {
      RoutingEntry[] entries = new RoutingEntry[numberOfItems];
      //System.out.println("Working on ID: " + keys.get(i) );
      for (int j = 0; j < numberOfItems; j++) {
        int nextNode = (int) (i + Math.pow(2,j))%keys.size();
        TCPConnection item = tree.get(keys.get(nextNode));
        Socket socket = item.getSocket();
        RoutingEntry entry = new RoutingEntry(keys.get(nextNode),
            socket.getInetAddress().getHostAddress(), connectionsCache.getServerSocketPorts(keys.get(nextNode)));
       // System.out.println("Adding entry" + entry + "\n");
        entries[j] = entry;
      }

      RoutingTable table = new RoutingTable(entries);
      overlay.put(keys.get(i), table);

    }
  }
}
