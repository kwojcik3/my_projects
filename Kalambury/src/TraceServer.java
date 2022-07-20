import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Stack;

public class TraceServer implements Runnable{
    public Stack<Integer> traceX=new Stack<Integer>();
    public Stack<Integer> traceY=new Stack<Integer>();


    public void run() {
        try(
            var socket = new ServerSocket(1000)
        ){
            int i = 1;
            System.out.println("Serwer czeka na połączenie");
            while(true){
                Socket incoming = socket.accept();
                System.out.println("wywołanie nr: " + i);
                var r = new ThreadTraceHandler(incoming);
                r.run();
                traceX.push(r.tracex);
                traceY.push(r.tracey);
                i++;
            }
        } catch (IOException e) {
        }
    }
}

 class ThreadTraceHandler {
     private final Socket incoming;
     public int tracex =112;
     public int tracey =222;
     public ThreadTraceHandler(Socket incoming){
        this.incoming = incoming;
    }
    public void run()
    {
        try(var fromClient = new Scanner(incoming.getInputStream(), StandardCharsets.UTF_8))
        {
            while (fromClient.hasNextInt())
            {
                        tracex = fromClient.nextInt();
                        tracey = fromClient.nextInt();
                        System.out.println("   przyszło X: " + tracex);
                        System.out.println("   przyszlo Y: " + tracey);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
