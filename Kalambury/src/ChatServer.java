import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatServer implements Runnable{
    public String saveMe = "oczekuje";
    public void run() {
        try(
            var socket = new ServerSocket(1200)
        ){
            int i = 1;
            System.out.println("Serwer czeka na połączenie");

            while(true){
                Socket incoming = socket.accept();
                System.out.println("wywołanie nr: " + i);
                var r = new ThreadChatHandler(incoming);
                 saveMe = r.run();
                System.out.println("chatsaveme: "+saveMe);
                i++;
            }
        } catch (IOException e) {
        }
    }
}
class ThreadChatHandler {
    private Socket incoming;
    public String storedChat;

    public ThreadChatHandler(Socket incoming){
        this.incoming = incoming;
    }

    public String run()
    {
        try(var fromClient = new Scanner(incoming.getInputStream(), StandardCharsets.UTF_8)){
            String line;
            while (fromClient.hasNextLine())
            {
                line = fromClient.nextLine();
                System.out.println("   przyszło: " + line);
                if(line==null)
                  line="0";
                   storedChat = line;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return storedChat;
    }
}
