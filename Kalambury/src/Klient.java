import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;
public class Klient {
    private String message;

    public void setkey(String msg) {
        message = msg;
        try (
                var socket = new Socket("localhost", 1234);
                var toServer = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true
                )
        ) {
            toServer.println(message);
        } catch (IOException e) {
        }
    }

    public String getkey() {
        try (var socket = new Socket("localhost", 1234);
             var fromServer = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
             var toServer = new PrintWriter(
                     new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true
             );) {
            toServer.println("1");
            boolean done = false;
            while (!done && fromServer.hasNextLine()) {
                message = fromServer.nextLine();
                done = true;
            }
        } catch (Exception e) {
        }
        return message;
    }

    private int rand = 0;
    private String[] keys = new String[]{"krowa", "pies", "zupa", "wakacje"};
    private String key;

    public String losskey() {
        rand = new Random().nextInt(4);
        key = keys[rand];
        this.setkey(key);
        return key;
    }

    public void setTrace(int x, int y) {
        try (
                var socket = new Socket("localhost", 1000);
                var toServer = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true
                )
        ) {
            toServer.println(x);
            toServer.println(y);
        } catch (IOException e) {
        }
    }

    public void setChat(String mssg) {
        try (
                var socket = new Socket("localhost", 1200);
                var toServer = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)
        ) {
            toServer.println(mssg);
        } catch (IOException e) {
        }
    }

}

