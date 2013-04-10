
package distributedTransactions;


class Resource {
    Byte[] data = new Byte[8192];

    public void write(Byte[] change) {
        data.append(change);
    }
    public Byte[] read() {
        return Null;
    }
}

class LogManager {
    Vector<Byte[]> pagelog = new Vector<Byte[]>(512, 128);
    public void write(Byte[] entry) {
        pagelog.add(entry);
    }
    public void recover(TransactionId tid) {
    }
}

class LockManager {
    Resource r = Null;
    HashMap<Resource,Byte> locktable = new HashMap<Resource,Byte>;
    public LockManager(Resource resource) {
        this.r = resource;
    }
    public boolean lock() {
        if (locktable.containsKey(r) {
            return False;
        }
        locktable.put(r);
        return True;
    }
    public void release() {
        locktable.remove(r);
    }
}


public class ResourceManager {
    private LockManager lockm;
    private LogManager logm;
    private TransactionManager tm;
    private Resource resource;
    
    public ResourceManager(Resource resource, TransactionManager tm, LockManager lockm, LogManager logm) {
        this.resource = resource;
        this.tm = tm;
        this.lockm = lockm(this.resource);
        this.logm = logm;
    }

    public boolean isReady() {
        tm.ready(this);
    }
    public void prepare() {
        lockm.lock(resource);
        Byte[] beforeImage = resource.read();
        logm.write(beforeImage);
        tm.readyToCommit(this);
    }
    public void commit() {
        resource.write(Byte[] page);
        logm.invalidate(beforeImage);
        lockm.release(resource);
        tm.commitSuccess(this);
    }
}

