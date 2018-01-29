package cs455.overlay.wireformats;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EventTest {

  private Event e;
  private byte[] testBytes = new byte[2];


  @Before
  public void setup(){
    e = new Event(-1);
  }


  @Test
  public void setupAndTeardownDataInputStream() throws Exception {
    e.setupDataInputStream(testBytes);
    e.teardownDataInputStream();
  }

  @Test
  public void setupAndTeardownDataOutputStream() throws Exception {
    e.setupDataOutputStream();
    e.teardownDataOutputStream();
  }

  @Test(expected = NullPointerException.class)
  public void teardownDataIOutputStreamFail() throws Exception {
    e.teardownDataOutputStream();
  }

  @Test(expected = NullPointerException.class)
  public void teardownDataIinputStreamFail() throws Exception {
    e.teardownDataInputStream();
  }


  @Test
  public void getBytes() throws Exception {
    assertArrayEquals(e.getBytes(), new byte[0]);
  }

  @Test
  public void getType() throws Exception {
    assertEquals(e.getType(), -1);
  }

  @Test
  public void confirmMessageType() throws Exception {
    e.confirmMessageType(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void confirmMessageTypeFail() throws Exception {
    e.confirmMessageType(1);
  }


}