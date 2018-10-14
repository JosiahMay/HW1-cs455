package cs455.overlay.node;

import cs455.overlay.routing.RoutingEntry;
import cs455.overlay.transport.TCPServerThread;
import cs455.overlay.util.InputCommands;
import cs455.overlay.util.PacketsSentInfo;
import cs455.overlay.util.StatisticsCollectorAndDisplay;
import cs455.overlay.wireformats.Event;
import cs455.overlay.wireformats.NodeReportsOverlaySetupStatus;
import cs455.overlay.wireformats.OverlayNodeReportsTaskFinished;
import cs455.overlay.wireformats.OverlayNodeReportsTrafficSummary;
import cs455.overlay.wireformats.OverlayNodeSendsData;
import cs455.overlay.wireformats.OverlayNodeSendsDeregistration;
import cs455.overlay.wireformats.Packet;
import cs455.overlay.wireformats.Protocol;
import cs455.overlay.wireformats.RegistryReportsDeregistrationStatus;
import cs455.overlay.wireformats.RegistryReportsRegistrationStatus;
import cs455.overlay.wireformats.RegistryRequestsTaskInitiate;
import cs455.overlay.wireformats.RegistrySendsNodeManifest;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MessagingNode extends Node{

  private final Object passMessageLock = new Object();
  private final String registryIpAddress;
  private final int registryPort;
  private int idNumber;
  private PacketsSentInfo packetInfo = new PacketsSentInfo();
  private int[] allNodesInNetwork;
  private ArrayList<Integer> relayIdNums;





  public MessagingNode(String ipAddress, int port){

    this.registryIpAddress = ipAddress;
    this.registryPort = port;

  }


  private void run() throws IOException {

    serverThread = new TCPServerThread(this, 0);

    serverThread.start();

    while(!commandLineParser.isInterrupted()){

    }
  }

  public static void main(String[] args) {

    if (args.length != 2) {
      System.err.println("Invalid number of arguments: " + args.length + " expected 2");
      System.err.println("java cs455.overlay.node.MessagingNode <registry-host> <registry-port>");
      System.exit(1);
    }

    String registryIP = args[0];
    int registryPortNumber = Integer.parseInt(args[1]);
    MessagingNode node = new MessagingNode(registryIP, registryPortNumber);

    node.setupCommandLineParser();


    try {

      node.run();
    }  catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void onEvent(Event event) {
    try {
      switch (event.getType()){
      case Protocol.REGISTRY_REPORTS_REGISTRATION_STATUS:
        registrationStatus(event);
        break;
      case Protocol.REGISTRY_REPORTS_DEREGISTRATION_STATUS:
        deregistrationStatus(event);
        break;
      case Protocol.REGISTRY_SENDS_NODE_MANIFEST:
          setupRoutingTables(event);
        break;
      case Protocol.REGISTRY_REQUESTS_TASK_INITIATE:
        startMessaging(event);
        break;
      case Protocol.REGISTRY_REQUESTS_TRAFFIC_SUMMARY:
        sendSummary();
        break;
      case Protocol.OVERLAY_NODE_SENDS_DATA:
        receiveData(event);
        break;
      default:
        System.err.println("Invalid type of message received by messaging node: " + event.getType());
      }
    } catch (IOException e) {
      e.printStackTrace();
      //System.exit(1);
  }

  }

  private void receiveData(Event event) {
    //System.out.println("Entered received data");
    OverlayNodeSendsData ev = (OverlayNodeSendsData) event;

    Packet packet = ev.getPacket();

    int destination = packet.getDestination();
    if(packet.getTraceRoute().length > allNodesInNetwork.length)
    {
      System.out.println("Packet going in circles");
      System.out.println(Arrays.toString(packet.getTraceRoute()));
      System.exit(1);
    }
    if(destination == idNumber){
      //packetInfo.addToPacketsReceived();
      packetInfo.addToSumReceived((long) packet.getPayload());
    } else {
      packetInfo.addPacketsRelayed();

      int[] trace = addToTraceRoute(packet.getTraceRoute());
      int sentTo = findClosestIdNode(packet.getDestination());
      Packet newPacket = new Packet(packet.getDestination(),
          packet.getSource(), packet.getPayload(), trace);

      try {
        synchronized (passMessageLock) {
          connectionsCache.sendMessage(sentTo, new OverlayNodeSendsData(newPacket).getBytes());
        }
      } catch (IOException e) {
        System.err.println("Error when sending to Node: " + sentTo);
        e.printStackTrace();
        System.err.flush();
        //System.exit(1);
      }
    }


  }

  private int[] addToTraceRoute(int[] traceRoute) {
    int[] rt = new int[traceRoute.length + 1];

    System.arraycopy(traceRoute, 0, rt, 0, traceRoute.length);
    rt[rt.length - 1] = idNumber;
    return rt;
  }

  private void sendSummary() throws IOException {
    synchronized (packetInfo) {
      serverThread.sendMessageToRegistry(
          new OverlayNodeReportsTrafficSummary(idNumber, packetInfo).getBytes());
    }
    packetInfo = new PacketsSentInfo();
  }

  private void startMessaging(Event event) throws IOException {

    RegistryRequestsTaskInitiate ev = (RegistryRequestsTaskInitiate) event;
    System.out.println("ID: " + idNumber + " sending " + ev.getPacketsToSend() + " messages");
    Random random = new Random();
    int number;
    int node;
    for (int i = 0; i < ev.getPacketsToSend(); i++) {
      number = random.nextInt();
      node = allNodesInNetwork[random.nextInt(allNodesInNetwork.length)];
      while(node == idNumber){
        node = allNodesInNetwork[random.nextInt(allNodesInNetwork.length)];
      }

      Packet packet = setupPacket(node, number);

      int sentTo = findClosestIdNode(node);
      //System.out.println("Sending: " + number + " to node " + sentTo);
      connectionsCache.sendMessage(sentTo, new OverlayNodeSendsData(packet).getBytes());


      //packetInfo.addToPacketsSent();
      packetInfo.addToSumSent((long) number);

    }

    serverThread.sendMessageToRegistry(new OverlayNodeReportsTaskFinished(
        serverThread.getServerSocketIpAddress(),
        serverThread.getSeverPortNumber(),
        idNumber).getBytes());


    System.out.println("ID: " + idNumber + " finished sending " + ev.getPacketsToSend() + " messages");

  }

  private int findClosestIdNode(int node) {
    //System.out.println(Arrays.toString(allNodesInNetwork));
    //System.out.println(relayIdNums);
    //System.out.println("Looking for best from " + idNumber + " to " + node);
    int best = -1;
    for (Integer i : relayIdNums) {

      if (i <= node) {
        //System.out.println(i + " is better fit");
        best = i;
        //System.out.println("The best number is " + best);
      }
    }

    if(best == -1){
      best = relayIdNums.get(relayIdNums.size()-1);
    }

      return best;
  }

  private Packet setupPacket(int node, int number) {

    return new Packet(node, idNumber, number, new int[0] );

  }

  private void setupRoutingTables(Event event) throws IOException {
    RegistrySendsNodeManifest ev = (RegistrySendsNodeManifest) event;
    System.out.println("Setting up overlay");
    connectionsCache.clearConnections();

    //System.out.println(ev.getRoutingTable());

    allNodesInNetwork = ev.getTablesInNetwork();

    //System.out.println(Arrays.toString(ev.getTablesInNetwork()));

    relayIdNums = new ArrayList<>();
    RoutingEntry[] table = ev.getRoutingTable().getEntries();

    for (RoutingEntry entry : table) {
      //System.out.println(entry);
      relayIdNums.add(entry.getNodeId());
      try {
        serverThread.connectToNode(entry.getIpAddress(), entry.getPortNumber(), entry.getNodeId());
      } catch (IOException e) {
        serverThread.sendMessageToRegistry(new NodeReportsOverlaySetupStatus(-1,
            "Node: " + idNumber + " " + e.getMessage()).getBytes());
        throw e;
      }

    }

    Collections.sort(relayIdNums);
    System.out.println(Arrays.toString(relayIdNums.toArray()));

    serverThread.sendMessageToRegistry(new NodeReportsOverlaySetupStatus(idNumber,
        "Connected to all nodes in routing table").getBytes());
  }


  private void registrationStatus(Event event) {
    RegistryReportsRegistrationStatus ev = (RegistryReportsRegistrationStatus) event;
    if(ev.getId() != -1){
      this.idNumber = ev.getId();
      System.out.println("This nodes ID is " + idNumber);
    } else {
      System.out.println("Error in registration message node:");
      System.out.println(ev.getMessage());
    }

  }

  private void deregistrationStatus(Event event) {
    RegistryReportsDeregistrationStatus ev = (RegistryReportsDeregistrationStatus) event;
    if(ev.getId() != -1){
      stopCommandLineParser();
      stopServerThread();
      System.exit(0);
    } else {
      System.out.println("Error in deregistration message node:");
      System.out.println(ev.getMessage());
    }
  }

  private void sendExitOverlayRequest() throws IOException {
    OverlayNodeSendsDeregistration event =
        new OverlayNodeSendsDeregistration(
            serverThread.getServerSocketIpAddress(),
            serverThread.getSeverPortNumber(),
            idNumber);


      serverThread.sendMessageToRegistry(event.getBytes());
  }

  @Override
  public void onEvent(Event event, Socket socket) {
    // Unused for Messaging nodes
  }

  @Override
  public void onCommand(InputCommands command) {
    try {
      switch (command.command){
        case EXIT_OVERLAY:
          sendExitOverlayRequest();
          break;
        case PRINT_COUNTERS_AND_DIAGNOSTICS:
          System.out.println("Node ID: " + idNumber);
          StatisticsCollectorAndDisplay.displayPacketsSentInfo(packetInfo);
          break;
        default:
          System.err.println("Invalid command received by messaging node");


      }
    } catch (IOException e) {
      System.err.println("Error in running commands:");
      e.printStackTrace();
      System.exit(1);
    }

  }



  public String getRegistryIpAddress() {
    return registryIpAddress;
  }

  public int getRegistryPort(){
    return registryPort;
  }
}
