package cs455.overlay.wireformats;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Event {

  final int messageType;
  private ByteArrayInputStream baInputStream;
  protected DataInputStream din;
  protected ByteArrayOutputStream baOutputStream;
  protected DataOutputStream dout;


  public Event(int messageType){
    this.messageType = messageType;
  }

  public byte[] getBytes() throws IOException{
    return new byte[0];
  }

  public int getType(){
   return messageType;
  }

  protected void confirmMessageType(int sentMessageType){
    if(messageType != (byte) sentMessageType){
      throw new IllegalArgumentException("Mismatched messageTypes, expected:" + messageType
          + "received: " + sentMessageType);
    }
  }

  protected void setupDataInputStream(byte[] marshalledBytes) {
    baInputStream = new ByteArrayInputStream(marshalledBytes);
    din = new DataInputStream(new BufferedInputStream(baInputStream));
  }

  protected void teardownDataInputStream() throws IOException {
    try {
      baInputStream.close();
      din.close();
    } catch (IOException e) {
      System.err.println("Error in closing data input streams\n" + e.getMessage());
      throw e;
    } catch (NullPointerException e){
      System.err.println("Error in closing data input streams before setup\n" + e.getMessage());
      throw e;
    }
  }

  protected void setupDataOutputStream(){
    baOutputStream = new ByteArrayOutputStream();
    dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
  }

  protected void teardownDataOutputStream() throws IOException {
    try {
      baOutputStream.close();
      dout.close();
    } catch (IOException e) {
      System.err.println("Error in closing data output streams\n" + e.getMessage());
      throw e;
    }
    catch (NullPointerException e){
      System.err.println("Error in closing data output streams before setup\n" + e.getMessage());
      throw e;
    }
  }

}
