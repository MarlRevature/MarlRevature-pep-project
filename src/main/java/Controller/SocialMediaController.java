package Controller;

import static org.mockito.ArgumentMatchers.nullable;

import java.util.List;

import org.h2.util.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountRegistrationHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountRegistrationHandler(Context ctx)  throws JsonProcessingException  {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account registeredAccount = accountService.registerAccount(account);
        if (registeredAccount!=null){
            ctx.json(mapper.writeValueAsString(registeredAccount));
        } else {
            ctx.status(400);
        }
    }

    private void postLoginHandler(Context ctx)  throws JsonProcessingException 
    {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedAccount = accountService.login(account);
        if (loggedAccount!=null){
            ctx.json(mapper.writeValueAsString(loggedAccount));
        } else {
            ctx.status(401);
        }
    }
    
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message insertedMessage = messageService.insertMessage(message);
        if (insertedMessage!=null){
            ctx.json(mapper.writeValueAsString(insertedMessage));
        } else{
            ctx.status(400);
        }
    }
    
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        int index = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessage(index);
        if (message!=null){
            ctx.json(mapper.writeValueAsString(message));
        } 
    }

    private void deleteMessageHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        int index = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessage(index);
        if (message!=null){
            ctx.json(mapper.writeValueAsString(message));
        }
    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        int index = Integer.parseInt(ctx.pathParam("message_id"));
        int msg_length =  ctx.body().length();
        String message_text =  ctx.body().substring(18, msg_length-3);
        Message updated_message = messageService.updateMessage(index,message_text);
        if (updated_message!=null){
            ctx.json(mapper.writeValueAsString(updated_message));
        } else{
            ctx.status(400);
        }
    }

    private void getAllMessagesFromUser(Context ctx)
    {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesFromUser(account_id);
        ctx.json(messages);
        
    }
}