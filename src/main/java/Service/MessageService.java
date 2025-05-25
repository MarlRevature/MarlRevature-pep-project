package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    /**
     * no-args constructor for creating a new accountService with a new accountDAO.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
    }
    public Message insertMessage(Message message){
        return messageDAO.insertMessage(message);
    }
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }
    public Message getMessage(int message_id){
        return messageDAO.getMessage(message_id);
    }
    public Message deleteMessage(int message_id){
        return messageDAO.deleteMessage(message_id);
    }
    public Message updateMessage(int message_id,String message_text){
        return messageDAO.updateMessage(message_id,message_text);
    }
    public List<Message> getAllMessagesFromUser(int account_id){
        return messageDAO.getAllMessagesFromUser(account_id);
    }
}   
