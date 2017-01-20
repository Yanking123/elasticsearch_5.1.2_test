package test;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkIndexByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 
 * <pre>
 * es jdbc操作基类。
 * </pre>
 * @author wangwenhui  wangwenhui@jiaxincloud.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class EsJdbcDaoSupport  {

	/**
	 * 插入es记录
	 * 
	 * @param index 数据库
	 * @param type 表
	 * @param dataMap 字段对应的数据
	 * @return  boolean
	 */
	public   void insert(String index,String type,Map<String,Object>dataMap) {
		TransportClient client =EsConnectionFactory.createEsClient();
		IndexResponse response = client.prepareIndex(index.trim(), type.trim()).setSource(dataMap).get();
		Logger.getLogger(EsJdbcDaoSupport.class).info(response);
		client.close();
	}
	
	/**
	 * update es记录
	 * 
	 * @param index 数据库
	 * @param type 表
	 * @param dataMap 字段对应的数据
	 * @return  boolean
	 */
	public   void update(String index,String type,SatisfySDREntity entity) {
		TransportClient client =EsConnectionFactory.createEsClient();
		try {
//			IndexRequest indexRequest = new IndexRequest(index,type,"AVflaevU2NLgp0KDTiLs")
//			        .source(XContentFactory.jsonBuilder()
//			            .startObject()
//			                .field("SS_APP_NAME", entity.getSsAppName())
//			                .field("SS_VISITOR_ID", entity.getSsVisitorId())
//			                .field("SS_SESSION_ID", entity.getSsSessionId())
//			                .field("SS_AGENT_ID",entity.getSsAgentId())
//			                .field("SS_ENTERPRISE_ID",entity.getSsEnterpriseId())
//			                .field("SS_SATISFY_SCORE", entity.getSsSatisfyScore())
//			                .field("SS_WORKGROUP_ID", entity.getWorkgroupId())
//			                .field("SS_CHANNEL_NO", entity.getChannelNo())
//			                .field("SS_TIME", entity.getSsTime())
//			            .endObject());
			
//			UpdateRequest updateRequest = new UpdateRequest(index, type, "AVflaevU2NLgp0KDTiLs")
//			        .doc(XContentFactory.jsonBuilder()
//			            .startObject()
////			            .field("SS_APP_NAME", entity.getSsAppName())
////		                .field("SS_VISITOR_ID", entity.getSsVisitorId())
////		                .field("SS_SESSION_ID", entity.getSsSessionId())
////		                .field("SS_AGENT_ID",entity.getSsAgentId())
////		                .field("SS_ENTERPRISE_ID",entity.getSsEnterpriseId())
////		                .field("SS_SATISFY_SCORE", entity.getSsSatisfyScore())
////		                .field("SS_WORKGROUP_ID", entity.getWorkgroupId())
////		                .field("SS_CHANNEL_NO", entity.getChannelNo())
//		                .field("VS_VISITOR_ENGINE", "")

			try {
				UpdateByQueryRequestBuilder ubqrb=UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
//				ubqrb.filter(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("ST_ENTERPRISE_ID", "nnvxytfuync0nq")))
//				.filter(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("ST_TAG_ID1", "1015")))
			
//				.script(new Script("ctx._source.ST_TAG_ID2=5")).source().get();
//				Map<String,String>dataMap=new HashMap<String,String>();
//				dataMap.put("ST_TAG_ID2", "2");
//				dataMap.put("ST_TAG_ID3", "3");
				Map<String,Object>dataMap=new HashMap<String,Object>();
				Script s=new Script(ScriptType.INLINE,Script.DEFAULT_SCRIPT_LANG , "[ctx._source.QS_ID='new1234567989ID34543543']", dataMap);
				BulkIndexByScrollResponse r=ubqrb.source(index).
						script(s)
						.filter(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("QS_ENTERPRISE_ID", "ctdiznh1b3rnbg")))
						.filter(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("QS_SESSION_ID", "fhnxzt13015-hui2")))
						.get();
				System.out.println(r.getUpdated());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}finally{
			client.close();
		}
	}
	/**
	 * 查询es记录
	 * 
	 * @param index 数据库
	 * @param type 表
	 * @param dataMap 字段对应的数据
	 * @return  boolean
	 */
	public  ResultSet query(String sql,String index) {
		  DruidDataSource dds =null;
	        Connection connection = null;
	        PreparedStatement ps = null;
	      try{
	      	int begin=0;
	    	int end=0;
	    	System.out.println("connect to index:"+index);
	    	     dds =EsConnectionFactory.getEsDataSource(index);
		         connection = dds.getConnection();
		         ps = connection.prepareStatement(sql);
		          ResultSet resultSet = ps.executeQuery();
		          return resultSet;
	      }
	      catch(Exception e) {
	    	  e.printStackTrace();
	    	  System.out.println("occur error");
	      }finally{
	            try {
					ps.close();
				    connection.close();
			        dds.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	      }
	    return null;
	}
	
	/**
	 * 查询es记录
	 * 
	 * @param index 数据库
	 * @param type 表
	 * @param dataMap 字段对应的数据
	 * @return  boolean
	 */
	public  long delete (String index,String deleteId) {
		TransportClient client =EsConnectionFactory.createEsClient();
		BulkIndexByScrollResponse response =
			    DeleteByQueryAction.INSTANCE.newRequestBuilder(client).
			        filter(QueryBuilders.matchQuery("QS_SESSION_ID", deleteId)) 
			        .source(index)                    
			        .get();                                             

			return response.getDeleted();   
	}
	
	  public void getHealth(){
		  TransportClient client =EsConnectionFactory.createEsClient();
	    	ClusterHealthResponse healths = client.admin().cluster().prepareHealth().get(); 
	    	String clusterName = healths.getClusterName();              
	    	int numberOfDataNodes = healths.getNumberOfDataNodes();     
	    	int numberOfNodes = healths.getNumberOfNodes();             
	    	for (ClusterIndexHealth health : healths.getIndices().values()) { 
	    	    String index = health.getIndex();                       
	    	    int numberOfShards = health.getNumberOfShards();        
	    	    int numberOfReplicas = health.getNumberOfReplicas();    
	    	    ClusterHealthStatus status = health.getStatus();     
	    	    System.out.println(status);
	    	  
	    	}
	    }

	
}