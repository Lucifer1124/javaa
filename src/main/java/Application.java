public class Application {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello World!");

        while (true) {
            Thread.sleep(3600000); // Sleep for 1 hour increments
        }
    }
}