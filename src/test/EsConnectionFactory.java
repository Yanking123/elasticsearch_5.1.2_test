package test;
import java.net.InetAddress;
import java.util.Properties;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;
import com.alibaba.druid.util.StringUtils;
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
public  class EsConnectionFactory {
	private final static int port=9300;
    public static String cluster_name="";
    public static String cluster_ip="127.0.0.1";
	static TransportClient client;

/**
 * 
 * @return
 */
	@SuppressWarnings("resource")
	public static TransportClient createEsClient() {
		  try {
              //设置集群名称
              Settings settings = Settings.builder().build();
              //创建client
              if(!StringUtils.isEmpty(cluster_name)){
            	  settings = Settings.builder().put("cluster.name", cluster_name)
  						.put("client.transport.sniff", "false").build();
              }
               client = new PreBuiltTransportClient(settings)
                      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(cluster_ip), port));
          } catch (Exception e) {
              e.printStackTrace();
          }
          return client;
      }
	
	/**
	 * 创建es数据库连接
	 * 
	 * @return
	 */
	public static DruidDataSource getEsDataSource(String index) {
		try{
	        Properties properties = new Properties();
	        properties.put("url", "jdbc:elasticsearch://"+cluster_ip+":"+port+"/"+index );
	        DruidDataSource dds = (DruidDataSource) ElasticSearchDruidDataSourceFactory.createDataSource(properties);
	        if(dds!=null){
	        	return dds;
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}