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

  public Event(byte[] marshalledBytes) throws IOException {
    setupDataInputStream(marshalledBytes);
    messageType = din.readInt();
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

  protected String readByteString() throws IOException {
    int stringLength = din.readInt();
    byte[] stringInBytes = new byte[stringLength];
    din.readFully(stringInBytes);
    return new String(stringInBytes);
  }

  protected void writeByteString(String message) throws IOException {
    dout.writeInt(message.length()); // size of IP address
    byte[] ipInBytes = message.getBytes();
    dout.write(ipInBytes);
  }

}
