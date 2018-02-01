package cs455.overlay.wireformats;


import java.io.IOException;

public class OverlayNodeReportsTrafficSummary extends Event implements Protocol{

  private final int idNum;
  private final TrafficReport report;

  public OverlayNodeReportsTrafficSummary() {
    this(-1, new TrafficReport());
  }

  public OverlayNodeReportsTrafficSummary(int idNum, TrafficReport report) {
    super(OVERLAY_NODE_REPORTS_TRAFFIC_SUMMARY);
    this.idNum = idNum;
    this.report = report;
  }

  public OverlayNodeReportsTrafficSummary(byte[] marshalledBytes) throws IOException {
    super(marshalledBytes);
    idNum = din.readInt();
    report = readTrafficReport();

    teardownDataInputStream();
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
    dout.writeInt(report.getPacketSent());
    dout.writeInt(report.getPacketsRelayed());
    dout.writeLong(report.getSumOfPacketSent());
    dout.writeLong(report.getPacketsReceived());
    dout.writeLong(report.getSumOfPacketsReceived());
  }

  private TrafficReport readTrafficReport() throws IOException {
    int packetsSent = din.readInt();
    int packetsRelayed = din.readInt();
    long sumOfSent = din.readLong();
    int packetsReceived = din.readInt();
    long sumOfReceived = din.readLong();

    int[] packetData = {packetsSent, packetsRelayed, packetsReceived};
    long[] sumData = {sumOfSent, sumOfReceived};

    return new TrafficReport(packetData, sumData);
  }

  public int getIdNum() {
    return idNum;
  }

  public TrafficReport getReport() {
    return report;
  }

}
