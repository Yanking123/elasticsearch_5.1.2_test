package test;
import java.util.Properties;

import org.elasticsearch.client.transport.TransportClient;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;
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
    public static String ES_ADDRESS="127.0.0.1:9300,127.0.0.2:9300";
	static TransportClient client;
	
	static{
		  ClientHelper.getInstance().init();
	}

/**
 * 
 * @return
 */
	public static TransportClient createEsClient() {
			  try {
//					ElasticSearchConnection esc=new ElasticSearchConnection("jdbc:elasticsearch://"+ES_ADDRESS);
					client=ClientHelper.getInstance().getClient();
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
	        properties.put("url", "jdbc:elasticsearch://"+ES_ADDRESS+":"+port+"/"+index );
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