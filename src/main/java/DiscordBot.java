import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.Collections;
import java.util.List;

public class DiscordBot extends ListenerAdapter{

    private static JDA jda = null;
    private static List<TextChannel> textChannels;
    private static MessageChannel channelBotLog;

    public static void main(String[] args) throws
            LoginException, RateLimitedException, InterruptedException {
        jda = new JDABuilder(AccountType.BOT)
                .setToken("MzU2Njg0NTk4MTMxODE4NDk2.DJe8AQ.xa8Am4JbSu5X2lEjztkLHMKWoPU")
                .addEventListener(new DiscordBot())
                .buildBlocking();

        textChannels = jda.getTextChannels();
        channelBotLog = textChannels.get(0);
    }


    @Override
    public void onReady(ReadyEvent event) {
        super.onReady(event);
        System.out.println("API is ready");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        String msg = message.getContent().toLowerCase();

        if (msg.equals("!ping")){
            channel.sendMessage("Pong!").complete();
        }
        if(msg.equals("!name")){
            channel.sendMessage(author.getName()).complete();
        }
        if (msg.equals("hey")){
            channel.sendMessage("That's pretty good").complete();
        }
        if (msg.equals("privatechat")){
            PrivateChannel privateChannel = author.openPrivateChannel().complete();
            privateChannel.sendMessage("Private Message").complete();
        }
        if(msg.equals("!pokeempi")){
            channel.sendMessage("@empi#6165 poke").complete();
        }
        if (msg.startsWith("!log")){
            try {
                StringBuilder stringBuilder = new StringBuilder();
                String number = msg.substring(msg.lastIndexOf(" "), msg.length());
                int amount = Integer.parseInt(number.replace(" ", "")) + 1;

                MessageHistory messageHistory = channel.getHistory();
                List<Message> messageList = messageHistory.retrievePast(amount).complete();
                Collections.reverse(messageList);
                messageList.remove(messageList.size()-1);
                stringBuilder.append("```\n");
                for (Message aMessageList : messageList) {
                    stringBuilder.append(aMessageList.getAuthor().getName()).append(" - " ).append(aMessageList.getContent()).append("\n");
                }
                stringBuilder.append("```");
                channelBotLog.sendMessage(stringBuilder.toString()).complete();
            } catch (Exception e){
                channel.sendMessage("```Please enter the Correct Command:\n\n!log 'amount'```").complete();
            }
        }
    }
}
