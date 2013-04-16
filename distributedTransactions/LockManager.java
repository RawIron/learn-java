
package distributedTransactions;

import java.util.HashMap;


public class LockManager {
    Resource r = null;
    HashMap<Resource,Byte> locktable = new HashMap<Resource,Byte>();
    Queue<Caller> waitQueue = new Queue<Caller>();

    public LockManager(Resource resource) {
        this.r = resource;
    }

    public boolean lock() {
        if (locktable.containsKey(r)) {
            waitQueue.put(caller);
            return false;
        }
        locktable.put(r,null);
        return true;
    }
    public void release() {
        locktable.remove(r);
        waitQueue.get(caller);
    }
}

