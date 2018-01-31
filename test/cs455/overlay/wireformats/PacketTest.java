package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class PacketTest {

  private Packet p;
  private final int destination = 100;
  private final int source = 5;
  private final int payload = 123513;
  private final int[] traceRoute = {5,12,48};

  @Before
  public void setUp() throws Exception {
    p = new Packet(destination, source, payload, traceRoute);
  }

  @Test
  public void getDestination() throws Exception {
    assertEquals(p.getDestination(), destination);
  }

  @Test
  public void getSource() throws Exception {
    assertEquals(p.getSource(), source);
  }

  @Test
  public void getPayload() throws Exception {
    assertEquals(p.getPayload(), payload);
  }

  @Test
  public void getTraceRoute() throws Exception {
    assertEquals(p.getTraceRoute(), traceRoute);
  }

  @Test
  public void toStringTest(){
    assertEquals(p.toString(), messageString());
  }

  private String messageString() {
    return "Destination: " + destination
        + "\nSource: " + source
        + "\nPayload: " + payload
        + "\nTraceRoute: " + Arrays.toString(traceRoute);
  }

}