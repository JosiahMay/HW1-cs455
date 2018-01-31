package cs455.overlay.wireformats;

import java.io.IOException;

public class NodeReportsOverlaySetupStatus extends MessageReportStatus implements Protocol{

  public NodeReportsOverlaySetupStatus() {
    this(-1, "Constructed from null constructor");

  }

  public NodeReportsOverlaySetupStatus(int idNum, String message){
    super(NODE_REPORTS_OVERLAY_SETUP_STATUS, idNum, message);

  }

  public NodeReportsOverlaySetupStatus(byte[] marshalledBytes) throws IOException {
    super(marshalledBytes);

  }

}
