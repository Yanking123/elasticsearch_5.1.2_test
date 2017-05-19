package test;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.elasticsearch.client.transport.TransportClient;

import com.alibaba.druid.pool.ElasticSearchDruidDataSource;
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
    public static String ES_ADDRESS="172.16.54.74:19300";
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
	
private static Map<String,ElasticSearchDruidDataSource> esDSMap = new ConcurrentHashMap<String,ElasticSearchDruidDataSource>();
	
	/**
	 * 创建es数据库连接
	 * 
	 * @return
	 */
	public  static ElasticSearchDruidDataSource getEsDataSource(String index) {
		//需要做线程同步 ，多线程并发时 防止创建多个es连接池（不同步线程增加）
		synchronized(index.intern()){
			ElasticSearchDruidDataSource ds = esDSMap.get(index);
			if(ds==null){
				try{
			        Properties properties = new Properties();
			        String url = "jdbc:elasticsearch://"+ES_ADDRESS+"/"+index;
			        properties.put("url", url );
			    	ds =   (ElasticSearchDruidDataSource) ElasticSearchDruidDataSourceFactory.createDataSource(properties);
			    	ds.setAsyncCloseConnectionEnable(true);
			    	ds.setAccessToUnderlyingConnectionAllowed(true);
			    	//连接池数 默认为8个  一个连接池 大概有50个线程
//			    	ds.setMaxActive(4);
			    	ds.setRemoveAbandoned(true);
			    	//设置移除超时链接
			    	ds.setRemoveAbandonedTimeout(180);
			    	ds.setPoolPreparedStatements(true);
			    	ds.setInitialSize(1);
			        if(ds!=null){
			        	esDSMap.put(index, ds);
			        }
				}catch(Exception e){
				}
			}
			return ds;
		}
	
	}
	
	
	
}