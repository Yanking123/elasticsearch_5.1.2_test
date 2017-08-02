package test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * Created by tgg on 16-3-17.
 */
public class ClientHelper {

    private Settings setting;

    private Map<String, TransportClient> clientMap = new ConcurrentHashMap<String, TransportClient>();

    private Map<String, Integer> ips = new HashMap<String, Integer>(); // hostname port

    private String clusterName = "wwh";

    private ClientHelper() {
        init();
        //TO-DO 添加你需要的client到helper
    }

    public static final ClientHelper getInstance() {
        return ClientHolder.INSTANCE;
    }

    private static class ClientHolder {
        private static final ClientHelper INSTANCE = new ClientHelper();
    }

    /**
     * 初始化默认的client
     */
    public void init() {
        ips.put("127.0.0.1", 9300);
        setting = Settings
                .builder()
                .put("client.transport.sniff",false)
                .put("cluster.name",clusterName).build();
        addClient(setting, getAllAddress(ips));
    }

    /**
     * 获得所有的地址端口
     *
     * @return
     */
    public List<InetSocketTransportAddress> getAllAddress(Map<String, Integer> ips) {
        List<InetSocketTransportAddress> addressList = new ArrayList<InetSocketTransportAddress>();
        for (String ip : ips.keySet()) {
            try {
				addressList.add(new InetSocketTransportAddress(InetAddress.getByName(ip), ips.get(ip)));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return addressList;
    }

    public TransportClient getClient() {
        return getClient(clusterName);
    }

    public TransportClient getClient(String clusterName) {
        return clientMap.get(clusterName);
    }

    public void addClient(Settings setting, List<InetSocketTransportAddress> transportAddress) {
    	@SuppressWarnings("resource")
		TransportClient client = new PreBuiltTransportClient(setting)
                .addTransportAddresses(transportAddress
                        .toArray(new InetSocketTransportAddress[transportAddress.size()]));
        clientMap.put(setting.get("cluster.name"), client);
    }
}