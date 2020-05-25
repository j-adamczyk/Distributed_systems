import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Executor implements Watcher
{
    private final String znode;
    private ZooKeeper zk;

    public Executor(String IPAndPort, String znode, String[] exec)
    {
        this.znode = znode;

        try
        {
            this.zk = new ZooKeeper(IPAndPort, 5000, this);
            DataMonitor dataMonitor = new DataMonitor(znode, zk, exec);
            dataMonitor.startWatch();
        }
        catch(IOException e)
        { e.printStackTrace(); }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
    }

    private void listChildren() {
        try
        {
            List<String> children = this.zk.getChildren(znode, false);
            System.out.println("Znode's " + znode + " children:");
            printChildren(children, znode);
        }
        catch (InterruptedException e)
        { e.printStackTrace(); }
        catch (KeeperException e)
        { System.out.println("Node does not exist!"); }
    }

    private void printChildren(List<String> children, String base) throws InterruptedException, KeeperException
    {
        for (String child : children)
        {
            String path = base + "/" + child;
            System.out.println(path);
            List<String> deeperChildren = this.zk.getChildren(path, false);
            printChildren(deeperChildren, path);
        }
    }

    public static void main(String[] args)
    {
        if (args.length < 2)
        {
            System.err.println("ARGUMENTS ERROR: should be Exectur IP:port program [arguments...]");
            System.exit(1);
        }

        String IPAndPort = args[0];
        String[] exec = new String[args.length - 1];
        String znode = "/z";
        System.arraycopy(args, 1, exec, 0, exec.length);

        Executor executor = new Executor(IPAndPort, znode, exec);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("EXECUTOR READY");
        System.out.println("Write 'tree', 'print' or 'print tree' to print current znodes tree");
        while (true)
        {
            try
            {
                String line = br.readLine();
                if (line.equals("tree") || line.equals("print") || line.equals("print tree"))
                    executor.listChildren();
                else
                    System.out.println("Unrecognized command.");
            }
            catch (IOException e)
            { e.printStackTrace(); }
        }
    }
}
