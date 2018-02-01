package cs455.overlay.wireformats;

public class TrafficReport {

  private final int packetSent;
  private final long sumOfPacketSent;
  private final int packetsRelayed;
  private final int packetsReceived;
  private final long sumOfPacketsReceived;

  public TrafficReport(){
    this(new int[3], new long[2]);
  }

  public TrafficReport(int[] packetInfo, long[] sumOfPackets){
    checkPacketInfo(packetInfo);
    packetSent = packetInfo[0];
    packetsRelayed = packetInfo[1];
    packetsReceived = packetInfo[2];

    setupSumOfPackets(sumOfPackets);
    sumOfPacketSent = sumOfPackets[0];
    sumOfPacketsReceived = sumOfPackets[1];
  }

  private void setupSumOfPackets(long[] sumOfPackets) {
    if(sumOfPackets.length != 2){
      throw new IllegalArgumentException("Expected 2 data points on the some of packets");
    }
  }

  private void checkPacketInfo(int[] packetInfo) {
    if(packetInfo.length != 3){
      throw new IllegalArgumentException("Expected 3 data points on packets");
    }
  }

  public int getPacketSent() {
    return packetSent;
  }

  public long getSumOfPacketSent() {
    return sumOfPacketSent;
  }

  public int getPacketsRelayed() {
    return packetsRelayed;
  }

  public int getPacketsReceived() {
    return packetsReceived;
  }

  public long getSumOfPacketsReceived() {
    return sumOfPacketsReceived;
  }
}
