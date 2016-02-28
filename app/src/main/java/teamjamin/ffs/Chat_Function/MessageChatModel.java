package teamjamin.ffs.Chat_Function;

/**
 * Created by Jessica on 2/27/16.
 */

public class MessageChatModel {
    private String message;
    private String recipient;
    private String sender;

    private int senderStatus;

    /* setter */
    public void setMessage(String message) {
        this.message = message;
    }

    public void setRecipientOrSenderStatus(int recipientOrSenderStatus) {
        this.senderStatus = recipientOrSenderStatus;
    }

    public void setRecipient(String givenRecipient){
        recipient=givenRecipient;
    }

    public void setSender(String givenSender){
        sender=givenSender;
    }

    /* getter */
    public String getMessage() {
        return message;
    }

    public String getRecipient(){
        return recipient;
    }

    public String getSender(){
        return sender;
    }

    public int getRecipientOrSenderStatus() {
        return senderStatus;
    }

}
