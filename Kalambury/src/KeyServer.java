import java.io.*;
import java.lang.constant.Constable;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class KeyServer implements Runnable{
    public void run() {
        try(
            var socket = new ServerSocket(1234)
        ){
            int i = 1;
            System.out.println("Serwer czeka na połączenie");
            String saveMe = "oczekuje";
            while(true){
                Socket incoming = socket.accept();
                System.out.println("wywołanie nr: " + i);
                var r = new ThreadEchoHandler(incoming);
                 saveMe = r.run(saveMe);
                i++;
            }
        } catch (IOException e) {
        }

    }

}

class ThreadEchoHandler {
    private Socket incoming;
    public String storedKey;

    public ThreadEchoHandler(Socket incoming){
        this.incoming = incoming;
    }

    public String run(String sv)
    {
        try(
                var fromClient = new Scanner(incoming.getInputStream(), StandardCharsets.UTF_8);
                var toClient   = new PrintWriter(
            	    new OutputStreamWriter(incoming.getOutputStream(), StandardCharsets.UTF_8), true
				)
        ){
            String line;
            while (fromClient.hasNextLine())
            {
                line = fromClient.nextLine();
                System.out.println("   przyszło: " + line);
                if(line==null)
                  line="0";
                if (line.equals("1"))
                  toClient.println(sv);
                if (!line.equals("1")&&!line.equals("2"))
                   storedKey = line;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return storedKey;
    }
}
