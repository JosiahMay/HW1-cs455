package cs455.overlay.util;

public class PacketsSentInfo {

  private Integer packetsSent = 0;
  private Integer packetsReceived = 0;
  private Integer packetsRelayed = 0;
  private Long sumSent = 0L;
  private Long sumReceived = 0L;

  public PacketsSentInfo(){

  }
  public PacketsSentInfo( int[] packetInfo, long[] sumOfPackets){
    checkPacketInfo(packetInfo);
    packetsSent = packetInfo[0];
    packetsRelayed = packetInfo[1];
    packetsReceived = packetInfo[2];

    setupSumOfPackets(sumOfPackets);
    sumSent = sumOfPackets[0];
    sumReceived = sumOfPackets[1];
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

  public synchronized Integer getPacketsSent() {
      return packetsSent;
  }

  public synchronized Integer getPacketsReceived() {
      return packetsReceived;
  }
  public synchronized Integer getPacketsRelayed() {
      return packetsRelayed;
  }

  public synchronized void addPacketsRelayed() {
      packetsRelayed++;
  }

  public synchronized Long getSumSent() {
      return sumSent;
  }

  public synchronized void addToSumSent(Long add) {
      this.sumSent += add;
      this.packetsSent++;
  }

  public synchronized Long getSumReceived() {
      return sumReceived;
  }

  public synchronized void addToSumReceived(Long add) {
      sumReceived += add;
      packetsReceived++;

  }

  @Override
  public String toString() {
    return "PacketsSent: " + packetsSent + " packetsReceived: " + packetsReceived
        + " packetsRelayed: " + packetsRelayed + " sumSent: " + sumSent +
        " sumReceived: " + sumReceived;
  }
}
