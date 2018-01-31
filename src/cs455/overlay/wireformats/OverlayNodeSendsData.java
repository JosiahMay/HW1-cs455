package cs455.overlay.wireformats;

import java.io.IOException;

public class OverlayNodeSendsData extends Event{

  public OverlayNodeSendsData(int messageType) {
    super(messageType);
  }

  public OverlayNodeSendsData(byte[] marshalledBytes) throws IOException {
    super(marshalledBytes);
  }
}
