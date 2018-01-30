package cs455.overlay.wireformats;

import java.io.IOException;

public class RegistryReportsRegistrationStatus extends Event implements Protocol{

  private final int id;
  private final String message;

  public RegistryReportsRegistrationStatus() {
    this(-1, 0, "Constructed from null constructor");

  }

  public RegistryReportsRegistrationStatus(int idNum, int overlayNum, String message){
    super(REGISTRY_REPORTS_REGISTRATION_STATUS);
    this.id = idNum;
    this.message = setupMessage(message, overlayNum);
  }

  public RegistryReportsRegistrationStatus(byte[] marshalledBytes) throws IOException {
    super(REGISTRY_REPORTS_REGISTRATION_STATUS);
    // Setup input streams
    setupDataInputStream(marshalledBytes);

    // Check if messageType matches
    confirmMessageType(din.readInt());

    //Read Data
    id = din.readInt();
    message = readByteString();

    // Close Input streams
    teardownDataInputStream();
  }

  @Override
  public byte[] getBytes() throws IOException{
    byte[] marshalledBytes;
    setupDataOutputStream();

    //MessageType and Id
    dout.writeInt(messageType);
    dout.writeInt(id);
    // Message
    dout.writeInt(message.length());
    dout.write(message.getBytes());

    // Save data to byte array
    dout.flush();
    marshalledBytes = baOutputStream.toByteArray();

    // Close connections
    teardownDataOutputStream();

    return marshalledBytes;
  }

  private String setupMessage(String message, int overlayNum) {
    String rt;
    if(id < 0){
      rt = message;
    } else {
      rt = "Registration request successful. The number of messaging nodes currently constituting "
          + "the overlay is (" + overlayNum + ")";
    }
    return rt;
  }


  public String getMessage() {
    return message;
  }

  public int getId(){
    return id;
  }

  @Override
  public String toString(){
    return "Event type: " + getType()
        + "\nIdNum: " + getId()
        + "\nMessage: " + getMessage();
  }


}
