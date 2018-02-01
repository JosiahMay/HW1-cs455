package cs455.overlay.wireformats;

import java.io.IOException;

public class OverlayNodeReportsTaskFinished extends Event implements Protocol{

  private final int idNum;

  public OverlayNodeReportsTaskFinished() {
    this(-1, new TrafficReport());
  }

  public OverlayNodeReportsTaskFinished(int idNum, TrafficReport report) {
    super(OVERLAY_NODE_REPORTS_TRAFFIC_SUMMARY);
    this.idNum = idNum;

  }

  public OverlayNodeReportsTaskFinished(byte[] marshalledBytes) throws IOException {
    super(marshalledBytes);
    idNum = din.readInt();

    teardownDataInputStream();
  }

  @Override
  public byte[] getBytes() throws IOException{
    setupDataOutputStream();

    dout.writeInt(idNum);

    return getOutputByteArray();
  }

  public int getIdNum() {
    return idNum;
  }

}
