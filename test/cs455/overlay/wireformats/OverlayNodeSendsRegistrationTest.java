package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OverlayNodeSendsRegistrationTest {

  private OverlayNodeSendsRegistration nsr;

  @Before
  public  void setup(){
    nsr = new OverlayNodeSendsRegistration("199.199.199.1", 556);
  }

  @Test
  public void buildByBytes() throws Exception {
    byte[] bytes = nsr.getBytes();
    OverlayNodeSendsRegistration buildByBytes = new OverlayNodeSendsRegistration(bytes);
    assertEquals("Event Type: " + Protocol.OVERLAY_NODE_SENDS_REGISTRATION
        + "\nIP Address: 199.199.199.1"  + "\nPort: 556", buildByBytes.toString());

  }

  @Test
  public void getIpAddress() throws Exception {
    assertEquals(nsr.getIpAddress(), "199.199.199.1");
  }

  @Test
  public void getPort() throws Exception {
    assertEquals(nsr.getPort(), 556);
  }

  @Test
  public void emptyTest(){
    nsr = new OverlayNodeSendsRegistration();
    assertEquals(nsr.getPort(), -1);
  }

}