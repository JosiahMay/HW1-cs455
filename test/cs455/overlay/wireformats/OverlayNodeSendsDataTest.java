package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OverlayNodeSendsDataTest implements TestVariables{

  private OverlayNodeSendsData onsd;
  private final int messageType = Protocol.OVERLAY_NODE_SENDS_DATA;

  @Before
  public void setUp() throws Exception {
    onsd = new OverlayNodeSendsData(setupPacket());
  }

  @Test
  public void nullConstructor(){
    onsd = new OverlayNodeSendsData();
    assertEquals(onsd.getPacket().getSource(), -1);
  }

  @Test
  public void getBytes() throws Exception {
    byte[] bytes = onsd.getBytes();
    OverlayNodeSendsData byteBuilt = new OverlayNodeSendsData(bytes);
    assertEquals(byteBuilt.getPacket().getPayload(), packetPayload);
  }

  @Test
  public void toStringTest(){
    assertEquals(onsd.toString(), buildString());
  }

  private String buildString() {
    return "Event Type: " + messageType
        + "\nPacket:\n" + setupPacket().toString();
  }

  private Packet setupPacket() {
    return new Packet(packetDestination, packetSource, packetPayload, packetTraceRoute);
  }


}