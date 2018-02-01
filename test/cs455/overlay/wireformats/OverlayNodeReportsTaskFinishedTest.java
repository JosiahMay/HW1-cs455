package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OverlayNodeReportsTaskFinishedTest implements TestVariables {

  private OverlayNodeReportsTaskFinished onrtf;

  @Before
  public void setUp() throws Exception {
    onrtf = new OverlayNodeReportsTaskFinished(ipAddress, portNumber, idNum);
  }

  @Test
  public void nullConstructor() throws Exception {
    onrtf =  new OverlayNodeReportsTaskFinished();
    assertEquals(onrtf.getPortNum(), -1);
  }

  @Test
  public void getBytes() throws Exception {
    byte[] bytes = onrtf.getBytes();
    OverlayNodeReportsTaskFinished byteBuild = new OverlayNodeReportsTaskFinished(bytes);
    assertEquals(onrtf.getIpAddress(), ipAddress);
  }

  @Test
  public void getIdNum() throws Exception {
    assertEquals(onrtf.getIdNum(), idNum);
  }

  @Test
  public void getPortNum() throws Exception {
    assertEquals(onrtf.getPortNum(), portNumber);
  }

  @Test
  public void getIpAddress() throws Exception {
    assertEquals(onrtf.getIpAddress(), ipAddress);
  }


}