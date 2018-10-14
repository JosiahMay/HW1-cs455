package cs455.overlay.wireformats;

import cs455.overlay.util.PacketsSentInfo;

import java.io.IOException;
import java.util.Arrays;

public class OverlayNodeReportsTrafficSummary extends Event implements Protocol{

  private final int idNum;
  private final PacketsSentInfo report;

  public OverlayNodeReportsTrafficSummary() {
    this(-1, new PacketsSentInfo());
  }

  public OverlayNodeReportsTrafficSummary(int idNum, PacketsSentInfo report) {
    super(OVERLAY_NODE_REPORTS_TRAFFIC_SUMMARY);
    this.idNum = idNum;
    this.report = report;
  }

  public OverlayNodeReportsTrafficSummary(byte[] marshalledBytes) throws IOException {
    super(marshalledBytes);
    idNum = din.readInt();
    report = readTrafficReport();

    teardownDataInputStream();
    //System.out.println(report);
  }

  @Override
  public byte[] getBytes() throws IOException{
    setupDataOutputStream();

    dout.writeInt(messageType);
    dout.writeInt(idNum);
    writeTrafficReport();

    return getOutputByteArray();
  }

  private void writeTrafficReport() throws IOException {
    dout.writeInt(report.getPacketsSent());
    dout.writeInt(report.getPacketsRelayed());
    dout.writeLong(report.getSumSent());
    dout.writeInt(report.getPacketsReceived());
    dout.writeLong(report.getSumReceived());
  }

  private PacketsSentInfo readTrafficReport() throws IOException {
    int packetsSent = din.readInt();
    int packetsRelayed = din.readInt();
    long sumOfSent = din.readLong();
    int packetsReceived = din.readInt();
    long sumOfReceived = din.readLong();

    int[] packetData = {packetsSent, packetsRelayed, packetsReceived};
    long[] sumData = {sumOfSent, sumOfReceived};
    //System.out.println("In Traffic " + Arrays.toString(sumData));
    return new PacketsSentInfo(packetData, sumData);
  }

  public int getIdNum() {
    return idNum;
  }

  public PacketsSentInfo getReport() { return report; }

}
