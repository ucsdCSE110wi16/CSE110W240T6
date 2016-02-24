package teamjamin.ffs.ChatModel;

/**
 * Created by User on 2/24/16.
 */
public class MessageModel {

    private String message;
    private String recipient;
    private String sender;

    private int status;

        /* Setter */

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int recipientOrSenderStatus) {
        this.status = status;
    }

    public void setRecipient(String givenRecipient){
        recipient=givenRecipient;
    }

    public void setSender(String givenSender){
        sender=givenSender;
    }


    /* Getter */

    public String getMessage() {
        return message;
    }

    public String getRecipient(){
        return recipient;
    }

    public String getSender(){
        return sender;
    }

    public int getStatus() {
        return status;
    }

}
