package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class NodeReportsOverlaySetupStatusTest extends MessageReportTestSetups {

  private NodeReportsOverlaySetupStatus nros;

  @Before
  public void setup(){
    nros = new NodeReportsOverlaySetupStatus(idNum,  messageString());
  }

  @Test
  public void getMessage() throws Exception {
    assertEquals(nros.getMessage(), messageString());
  }

  @Test
  public void getId() throws Exception {
    assertEquals(nros.getId(), idNum);
  }

  @Test
  public void testNullId(){
    nros =  new NodeReportsOverlaySetupStatus();
    assertEquals(nros.getId(), -1);
  }

  @Test
  public void testNullMessage(){
    nros =  new NodeReportsOverlaySetupStatus();
    assertEquals(nros.getMessage(), nullMessage);
  }

  @Test
  public void testBytes() throws IOException {
    byte[] bytes = nros.getBytes();
    NodeReportsOverlaySetupStatus byteBuild = new NodeReportsOverlaySetupStatus(bytes);
    assertEquals(byteBuild.getMessage(), messageString());
  }

  @Test
  public void toStringTest() {
    assertEquals(nros.toString(), toStringBuilder());
  }

  private String messageString(){
    return "Node " + idNum + " successfully connected to nodes 12, 25, 85";
  }

  private String toStringBuilder(){
    messageType = Protocol.NODE_REPORTS_OVERLAY_SETUP_STATUS;
    return "Event type: " + messageType + "\nIdNum: " + idNum + "\nMessage: " + messageString();
  }
}