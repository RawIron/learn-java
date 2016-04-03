import java.util.Vector;


class LogManagerMonitor<T> {
    private int writeCounter = 0;
    private int invalidateCounter = 0;
    private int recoverCounter = 0;

    private LogManager<T> logM;
    public LogManagerMonitor(LogManager<T> logM) {
        this.logM = logM;
    }

    public void write(T entry) {
        logM.write(entry);
        ++writeCounter;
    }
    public void invalidate(T entry) {
        logM.invalidate(entry);
        ++invalidateCounter;
    }
    public T recover() {
        T result = logM.recover();
        ++recoverCounter;
        return result;
    }

    public boolean isPersisted() {
        return true;
    }
    public boolean throwsNoBeforeImageFound() {
        return true;
    }
}


public class LogManager<T> {
    private Vector<T> pagelog = new Vector<T>(512, 128);
    public void write(T entry) {
        pagelog.add(entry);
    }
    public void invalidate(T entry) {
        pagelog.remove(entry);
    }
    public T recover() {
        return pagelog.lastElement();
    }
}

