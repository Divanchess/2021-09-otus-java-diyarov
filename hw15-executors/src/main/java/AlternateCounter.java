import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlternateCounter {
    private static final Logger LOG = LoggerFactory.getLogger(AlternateCounter.class);

    private int threadIdLast = 2;

    public AlternateCounter() {}

    public synchronized void count(int threadIdCurr) {
        int counter = 1;
        boolean forward = true;

        while(!Thread.currentThread().isInterrupted() && counter != 0) {
            try {
                while (threadIdLast == threadIdCurr) {
                    this.wait();
                }

                LOG.info(String.valueOf(counter));
                threadIdLast = threadIdCurr;
                sleep();
                if (counter == 10) {
                    forward = false;
                }
                if (forward) {
                    counter++;
                } else {
                    counter--;
                }

                notifyAll();

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public int getThreadIdLast() {
        return threadIdLast;
    }
}
