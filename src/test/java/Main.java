

public class Main {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    log( "123");
                }
            });
        }
    }

    public static void log(String s) {
        System.out.println(Thread.currentThread().getName() + ": " + s);
    }

}
