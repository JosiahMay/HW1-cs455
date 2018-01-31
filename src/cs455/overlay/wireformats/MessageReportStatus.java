package cs455.overlay.wireformats;

import java.io.IOException;

public abstract class MessageReportStatus extends Event implements Protocol{

  private final int id;
  private final String message;


  public MessageReportStatus(int messageType, int idNum, String message){
    super(messageType);
    this.id = idNum;
    this.message = message;
  }

  public MessageReportStatus(byte[] marshalledBytes) throws IOException {
    super(marshalledBytes);

    //Read Data
    id = din.readInt();
    message = readByteString();

    // Close Input streams
    teardownDataInputStream();
  }

  @Override
  public byte[] getBytes() throws IOException{
    setupDataOutputStream();

    //MessageType and Id
    dout.writeInt(messageType);
    dout.writeInt(id);
    // Message
    dout.writeInt(message.length());
    dout.write(message.getBytes());

    return getOutputByteArray();
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
