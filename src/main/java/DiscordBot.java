import at.mukprojects.giphy4j.Giphy;
import at.mukprojects.giphy4j.entity.search.SearchRandom;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DiscordBot extends ListenerAdapter{

    private final static int EXEPCTION_KEY = 0;
    private final static int GIPHY_KEY = 2;
    private final static int DISCORD_KEY = 1;

    private static JDA jda = null;
    private static List<TextChannel> textChannels;
    private static MessageChannel channelBotLog;

    private static Giphy giphy = null;

    private static String key;
    private static File file = new File("Keys.txt");


    public static void main(String[] args) throws
            LoginException, RateLimitedException, InterruptedException {

        try{
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(createToken(DISCORD_KEY))
                    .addEventListener(new DiscordBot())
                    .buildBlocking();
            textChannels = jda.getTextChannels();
            channelBotLog = textChannels.get(0);

            giphy = new Giphy(createToken(GIPHY_KEY));

        } catch (Exception e){
            createToken(EXEPCTION_KEY);

            jda = new JDABuilder(AccountType.BOT)
                    .setToken(createToken(DISCORD_KEY))
                    .addEventListener(new DiscordBot())
                    .buildBlocking();

            textChannels = jda.getTextChannels();
            channelBotLog = textChannels.get(0);
        }
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
        if (msg.startsWith("!meme")){
            try {
                SearchRandom searchRandom = giphy.searchRandom("cat");
                channel.sendMessage(searchRandom.getData().getImageOriginalUrl()).complete();
            } catch (Exception e){
                System.out.println(e.toString());
            }
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
                for (Message aMessageList : messageList) {
                    stringBuilder.append(aMessageList.getAuthor().getName()).append(" - " ).append(aMessageList.getContent()).append("\n");
                }
                channelBotLog.sendMessage(stringBuilder.toString()).complete();
            } catch (Exception e){
                System.out.println(e.toString());
                channel.sendMessage("Please enter the Correct Command:\n\n!log 'amount'").complete();
            }
        }
    }

    /**************************************

                    Methods

     *************************************/

    private static String createToken(int keyNumber) {
        if (keyNumber == EXEPCTION_KEY){
            if (file.delete()){
                System.out.println("Token is wrong! Can't login!");
                System.out.println("Key File deleted!");
                System.out.println("Creating new File...\n");
            }
        }
        try {
            if (file.createNewFile()) {
                System.out.println("No Key File found!\n");
                PrintWriter printWriter = new PrintWriter("Keys.txt");
                Scanner sc;
                String token;

                /* Discord Token */
                System.out.println("Enter your Discord-token: ");
                sc = new Scanner(System.in);
                token = sc.nextLine();
                printWriter.println(token);


                /*YouTube Token */
                System.out.println("Enter your Giphy-token: ");
                sc = new Scanner(System.in);
                token = sc.nextLine();
                printWriter.println(token);

                printWriter.close();
                System.out.println(file + " created\n");

            }

            BufferedReader bufferedReader = new BufferedReader(new FileReader("Keys.txt"));

            if (keyNumber == DISCORD_KEY){
                key = bufferedReader.readLine();                                                                        //Once for the first Line
            } else if (keyNumber == GIPHY_KEY){
                key = bufferedReader.readLine();                                                                        //Twice for the second Line
                key = bufferedReader.readLine();
            }
            bufferedReader.close();

        } catch (IOException e) {
            System.out.println(e.toString());
            return null;
        }
        return key;
    }
}
