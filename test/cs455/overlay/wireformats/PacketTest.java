package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class PacketTest implements TestVariables{

  private Packet p;

  @Before
  public void setUp() throws Exception {
    p = new Packet(packetDestination, packetSource, packetPayload, packetTraceRoute);
  }

  @Test
  public void getDestination() throws Exception {
    assertEquals(p.getDestination(), packetDestination);
  }

  @Test
  public void getSource() throws Exception {
    assertEquals(p.getSource(), packetSource);
  }

  @Test
  public void getPayload() throws Exception {
    assertEquals(p.getPayload(), packetPayload);
  }

  @Test
  public void getTraceRoute() throws Exception {
    assertEquals(p.getTraceRoute(), packetTraceRoute);
  }

  @Test
  public void toStringTest(){
    assertEquals(p.toString(), messageString());
  }

  private String messageString() {
    return "Destination: " + packetDestination
        + "\nSource: " + packetSource
        + "\nPayload: " + packetPayload
        + "\nTraceRoute: " + Arrays.toString(packetTraceRoute);
  }

}