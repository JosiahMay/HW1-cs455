package cs455.overlay.util;

import cs455.overlay.routing.RoutingTable;
import cs455.overlay.transport.TCPConnection;
import java.net.Socket;
import java.util.TreeMap;

public class StatisticsCollectorAndDisplay {

  public static void listMessagingNodes(TreeMap<Integer, TCPConnection> tree) {
      System.out.println("The current nodes registered:");
      System.out.println("|Node Id|\t\t\tHost Name\t\t\t\t\t\t\t|Port Number|\tIP Address");
      for (Integer i : tree.keySet()) {
        System.out.println(getTcpConnectionInfo(tree, i));
      }
      System.out.println();

  }

  private static String getTcpConnectionInfo(TreeMap<Integer, TCPConnection> tree, Integer i) {
    Socket leaf = tree.get(i).getSocket();
    return "|"+ i+ "\t|\t\t" + leaf.getInetAddress().getCanonicalHostName()
        + "\t\t\t\t|" + leaf.getPort() + "\t\t|\t" + leaf.getInetAddress().getHostAddress();
  }

  public static void displayPacketsSentInfo(PacketsSentInfo info){
    System.out.println("Packets Sent\t|\tPackets Received \t|\tPackets Relayed"
        + "\t|\tSum Values Sent\t|\tSum Values Received");
    System.out.println(printPacketInfo(info));
  }

  private static String printPacketInfo(PacketsSentInfo info) {
      return info.getPacketsSent() + "\t|\t" + info.getPacketsReceived() + "\t|\t"
          + info.getPacketsRelayed() + "\t|\t" + info.getSumSent() + "\t|\t" + info.getSumReceived();
  }

  public static void listRoutingTable(TreeMap<Integer, RoutingTable> overlay) {

    for (Integer i: overlay.keySet()) {

      System.out.println("---Node ID: " + i + "---");
      System.out.println(overlay.get(i));
      System.out.println("\n\n");

    }

  }

  public static void printTrafficReport(TreeMap<Integer, PacketsSentInfo> trafficReport) {

    int packetsSent = 0;
    int packetsReceived = 0;
    int packetsRelayed = 0;
    long sumSent = 0;
    long sumReceived = 0;

    System.out.println("\t\t\t|Packets Sent\t|\tPackets Received \t|\tPackets Relayed"
        + "\t|\tSum Values Sent\t|\tSum Values Received");

    for (Integer i :trafficReport.keySet()) {
      PacketsSentInfo nodeInfo = trafficReport.get(i);
      packetsReceived += nodeInfo.getPacketsReceived();
      packetsSent += nodeInfo.getPacketsSent();
      packetsRelayed += nodeInfo.getPacketsRelayed();
      sumSent += nodeInfo.getSumSent();
      sumReceived += nodeInfo.getSumReceived();

      System.out.print("Node: " + i + "\t|");
      System.out.println(printPacketInfo(nodeInfo));

    }

    System.out.println("Totals\t\t|"+ packetsSent + "\t|\t" + packetsReceived+ "\t|\t"
        + packetsRelayed + "\t|\t" + sumSent+ "\t|\t" + sumReceived);

  }
}
