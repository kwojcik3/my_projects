public class Puns {
    public static void main(String[] args) {
        new Thread(new KeyServer()).start();
        new Thread(new PunsGuessFrame()).start();
        new Thread(new PunsPaintFrame()).start();
    }
}