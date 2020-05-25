import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class DataMonitor implements AsyncCallback.StatCallback, AsyncCallback.Children2Callback
{
    private final ZooKeeper zk;
    private List<String> lastRes = new LinkedList<>();
    private final String znode;
    private final String[] exec;
    private Process process = null;

    DataMonitor(String znode, ZooKeeper zk, String[] exec)
    {
        this.zk = zk;
        this.znode = znode;
        this.exec = exec;
    }

    public void startWatch()
    {
        zk.exists(znode, true, this, null);
        zk.getChildren(znode, true, this, null);
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat)
    {
        if (rc == KeeperException.Code.OK.intValue())
        {
            if (process == null)
            {
                System.out.println("Added z node, starting program.");
                zk.getChildren(znode, true, this, null);

                ProcessBuilder pb = new ProcessBuilder();
                pb.command(exec);

                try
                { this.process = pb.start(); }
                catch (IOException e)
                {e.printStackTrace(); }
            }
        }
        else if (rc == KeeperException.Code.NONODE.intValue())
        {
            if (this.process != null)
            {
                System.out.println("Deleted z node, stopping program.");
                this.process.destroy();
                this.process = null;
            }
        }
        else
            System.err.println("EXCEPTION DETECTED: " + rc);

        zk.exists(znode, true, this, null);
    }

    @Override
    public void processResult(int rc, String path, Object ctx, List<String> list, Stat stat)
    {
        if (!list.equals(lastRes))
        {
            lastRes = list;
            if (rc == KeeperException.Code.OK.intValue())
                System.out.println("Children change detected, now there are " + list.size() + " children.");
        }

        zk.getChildren(znode, true, this, null);
    }
}
