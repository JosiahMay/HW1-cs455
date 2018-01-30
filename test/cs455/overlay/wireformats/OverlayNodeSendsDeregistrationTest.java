package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class OverlayNodeSendsDeregistrationTest {

  private OverlayNodeSendsDeregistration nsd;
  private final String ipAddress = "192.0.0.1";
  private final int portNumber = 159;
  private final int idNumber = 147;

  @Before
  public void setup(){
    nsd = new OverlayNodeSendsDeregistration(ipAddress, portNumber,idNumber);
  }

  @Test
  public void getIpAddress() throws Exception {
    assertEquals(nsd.getIpAddress(), ipAddress);
  }

  @Test
  public void getPortNumber() throws Exception {
    assertEquals(nsd.getPortNumber(), portNumber);
  }

  @Test
  public void getIdNumber() throws Exception {
    assertEquals(nsd.getIdNumber(), idNumber);
  }

  @Test
  public void nullConstructorTest(){
    nsd = new OverlayNodeSendsDeregistration();
    assertEquals(nsd.getPortNumber(), -1);
  }

  @Test
  public void testBytes() throws IOException {
    byte[] bytes = nsd.getBytes();
    OverlayNodeSendsDeregistration byteBuild = new OverlayNodeSendsDeregistration(bytes);
    assertEquals(byteBuild.toString(), buildString());
  }

  private String buildString(){
    return "Event type: 4\nID Number: " + idNumber +
        "\nIP Address: " + ipAddress + "\nPort Number: " + portNumber;
  }

}