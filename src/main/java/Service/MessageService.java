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
}   
