public class ExecutorsDemo {

    public static void main(String[] args) throws InterruptedException {
        AlternateCounter counter = new AlternateCounter();
        Thread t1 = new Thread(() -> counter.count(1));
        Thread t2 = new Thread(() -> counter.count(counter.getThreadIdLast()));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
