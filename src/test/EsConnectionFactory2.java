package test;

import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
/**
 * 
 * <pre>
 * es连接基类。
 * </pre>
 * @author 王文辉  wangwenhui@jiaxincloud.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public  class EsConnectionFactory2 {
	private final static String ES_ADDRESS="127.0.0.1:9300";
	private final static String ES_CLUSTER_NAME="wwh";
	
	public static TransportClient transportClient = null;
	

	/**
	 * 创建一次client 即可
	 */
	static {
		try {
			String[] addressArray = ES_ADDRESS.split(",");
			TransportClient client = null;
			Settings settings = Settings  
		            .builder()  
		            .build();  
			if (ES_CLUSTER_NAME != null && !"".equals(ES_CLUSTER_NAME)) {
				 settings = Settings.builder().put("cluster.name", ES_CLUSTER_NAME)
						.put("client.transport.sniff", "false").build();
				client = new PreBuiltTransportClient(settings);
			} else {
			    client = new PreBuiltTransportClient(settings);
			}
			for (int i = 0; i < addressArray.length; i++) {
				client.addTransportAddress(
						new InetSocketTransportAddress(InetAddress.getByName(addressArray[i].split(":")[0]),
								Integer.parseInt(addressArray[i].split(":")[1])));
			}
			if (client != null) {
				transportClient = client;
			}
		} catch (Exception e) {
		}
	}
	
}