package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class RegistryReportsDeregistrationStatusTest {

  private RegistryReportsDeregistrationStatus rrd;
  private int idNum = 189;

  @Before
  public void setup(){
    rrd = new RegistryReportsDeregistrationStatus(idNum,  messageString());
  }

  @Test
  public void getMessage() throws Exception {
    assertEquals(rrd.getMessage(), messageString());
  }

  @Test
  public void getId() throws Exception {
    assertEquals(rrd.getId(), idNum);
  }

  @Test
  public void testNullId(){
    rrd =  new RegistryReportsDeregistrationStatus();
    assertEquals(rrd.getId(), -1);
  }

  @Test
  public void testNullMessage(){
    rrd =  new RegistryReportsDeregistrationStatus();
    assertEquals(rrd.getMessage(), "Constructed from null constructor");
  }

  @Test
  public void testBytes() throws IOException {
    byte[] bytes = rrd.getBytes();
    RegistryReportsRegistrationStatus byteBuild = new RegistryReportsRegistrationStatus(bytes);
    assertEquals(byteBuild.getMessage(), messageString());
  }

  @Test
  public void toStringTest() {
    assertEquals(rrd.toString(),
        "Event type: 5\nIdNum: " + idNum + "\nMessage: " + messageString());
  }

  private String messageString(){
    return "Deregistration request successful. Node " + idNum
        + "removed the the registry";
  }

}