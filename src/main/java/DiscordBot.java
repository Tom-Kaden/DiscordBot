import net.dv8tion.jda.client.events.call.GenericCallEvent;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.ChannelManager;

import javax.security.auth.login.LoginException;
import java.util.List;

public class DiscordBot extends ListenerAdapter{

    protected static JDA jda = null;

    public static void main(String[] args) throws
            LoginException, RateLimitedException, InterruptedException {
        jda = new JDABuilder(AccountType.BOT)
                .setToken("MzQ3NjE0ODczMTc5NjUyMDk2.DHcalA.TSByOZIWPyT__nONi_Dwdnc5PNg")
                .addEventListener(new DiscordBot())
                .buildBlocking();

        List<TextChannel> textChannels = jda.getTextChannels();
        System.out.println(textChannels);
    }


    @Override
    public void onReady(ReadyEvent event) {
        super.onReady(event);
        System.out.print("API is ready");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        JDA jda = event.getJDA();
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
        if (msg.equals("privaterchat")){
            PrivateChannel privateChannel = author.openPrivateChannel().complete();
            privateChannel.sendMessage("Private Message").complete();
        }
        if (msg.equals("discord bots zu schreiben macht spaß")){
            channel.sendMessage("Ich mag es auch zu leben!").complete();
        }
        if (msg.equals("i need love")){
            channel.sendMessage("Ich denke immer an dich :*").complete();
        }
    }
}
