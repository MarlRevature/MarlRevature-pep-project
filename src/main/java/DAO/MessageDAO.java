package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class MessageDAO {
    public Message insertMessage(Message message)
    {
        Connection connection = ConnectionUtil.getConnection();
        try {
            if (message.getMessage_text().length()<255 && message.getMessage_text().length()>0)
            {
                String sql = "insert into message (posted_by,message_text,time_posted_epoch) values (?,?,?)" ;
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, message.getPosted_by());
                ps.setString(2, message.getMessage_text());
                ps.setLong(3, message.getTime_posted_epoch());

                ps.executeUpdate();
                ResultSet pkeyResultSet = ps.getGeneratedKeys();
                if(pkeyResultSet.next()){
                    int generated_message_id = pkeyResultSet.getInt(1);
                    message.setMessage_id(generated_message_id);
                }
                return message;
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "select * from message" ;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),
                rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessage(int message_id)
    {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from message where message_id=?" ;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),
                rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                return message;
            }
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessage(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            Message message = getMessage(message_id);
            if(message != null)
            {
                String sql = "delete from message where message_id=?" ;
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, message_id);
                ps.executeUpdate();
                
                return message;
            }
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessage(int message_id, String message_text) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            Message message = getMessage(message_id);
            if(message != null && message_text.length()<255 && !message_text.isEmpty())
            {
                String sql = "update message set message_text=? where message_id=?" ;
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, message_text);
                ps.setInt(2, message_id);
                ps.executeUpdate();
                message.setMessage_text(message_text);
                return message;
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessagesFromUser(int account_id) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "select * from message where posted_by=?" ;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),
                rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
            return messages;
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }
}
