import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import javax.security.auth.login.LoginException;

public class DiscordBot extends ListenerAdapter{

    protected static JDA jda = null;

    public static void main(String[] args) throws
            LoginException, RateLimitedException, InterruptedException{
        jda = new JDABuilder(AccountType.BOT)
                .setToken("MzQ3NjE0ODczMTc5NjUyMDk2.DHcalA.TSByOZIWPyT__nONi_Dwdnc5PNg")
                .addEventListener(new DiscordBot())
                .buildBlocking();
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
        String msg = message.getContent();

        if (msg.equals("!Ping") || msg.equals("!ping")){
            channel.sendMessage("Pong!").queue();
        }
    }
}
