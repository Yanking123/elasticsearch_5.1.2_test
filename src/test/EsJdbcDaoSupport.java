package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkIndexByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;

import com.alibaba.druid.pool.ElasticSearchDruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidPooledConnection;
import com.alibaba.druid.pool.ElasticSearchDruidPooledPreparedStatement;

/**
 * 
 * <pre>
 * es jdbc操作基类。
 * </pre>
 * 
 * @author wangwenhui 946374340@qq.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:  2017-01-20   修改内容:
 *          </pre>
 */
public class EsJdbcDaoSupport {

	/**
	 * 插入es记录
	 * 
	 * @param index
	 *            数据库
	 * @param type
	 *            表
	 * @param dataMap
	 *            字段对应的数据
	 * @return boolean
	 */
	public void insert(String index, String type, Map<String, Object> dataMap) {
		TransportClient client = EsConnectionFactory2.transportClient;
		IndexResponse response = client.prepareIndex(index.trim(), type.trim()).setSource(dataMap).get();
	}

	/**
	 * update es记录
	 * 
	 * @param index
	 *            数据库
	 * @param type
	 *            表
	 * @param dataMap
	 *            字段对应的数据
	 * @return boolean
	 */
	public void update(String index, String type, SatisfySDREntity entity) {
		TransportClient client = EsConnectionFactory.createEsClient();
		//主键
try{
	IndexRequest indexRequest = new IndexRequest(index, type.toUpperCase(), "789872343").source(XContentFactory
			.jsonBuilder().startObject()
			.field("SS_SESSION_ID", "test12345677234")
			.endObject());
	UpdateRequest updateRequest = new UpdateRequest(index, type, "789872343").doc(
			XContentFactory.jsonBuilder().startObject()
			.field("SS_ENTERPRISE_ID", "updateEntierpriserid")
			.endObject())
			.upsert(indexRequest);
	UpdateResponse response = client.update(updateRequest).get();
	System.out.println(response.getId());
}catch(Exception e){
	e.printStackTrace();
}
		

//			try {
//				UpdateByQueryRequestBuilder ubqrb = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
//				// ubqrb.filter(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("ST_ENTERPRISE_ID",
//				// "nnvxytfuync0nq")))
//				// .filter(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("ST_TAG_ID1",
//				// "1015")))
//
//				// .script(new
//				// Script("ctx._source.ST_TAG_ID2=5")).source().get();
//				// Map<String,String>dataMap=new HashMap<String,String>();
//				// dataMap.put("ST_TAG_ID2", "2");
//				// dataMap.put("ST_TAG_ID3", "3");
//				Map<String, Object> dataMap = new HashMap<String, Object>();
//				Script s = new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG,
//						"[ctx._source.QS_ID='new1234567989ID34543543']", dataMap);
//				BulkIndexByScrollResponse r = ubqrb.source(index).script(s)
//						.filter(QueryBuilders.boolQuery()
//								.must(QueryBuilders.termQuery("QS_ENTERPRISE_ID", "ctdiznh1b3rnbg")))
//						.filter(QueryBuilders.boolQuery()
//								.must(QueryBuilders.termQuery("QS_SESSION_ID", "fhnxzt13015-hui2")))
//						.get();
//				System.out.println(r.getUpdated());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} finally {
//			client.close();
//		}
	}

	
	/**
	 * 查询列表
	 * 
	 * @param <T>
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws SQLException
	 */
	public <T> List<T> getModeList(String index, String sql, Object[] args) {
		ElasticSearchDruidDataSource dds = null;
		ElasticSearchDruidPooledConnection  connection = null;
		ElasticSearchDruidPooledPreparedStatement ps = null;
		ResultSet resultSet = null;
		List<T> result = new ArrayList<T>();
		try {
			dds = EsConnectionFactory.getEsDataSource(index);
			connection =(ElasticSearchDruidPooledConnection) dds.getConnection();
			ps =  (ElasticSearchDruidPooledPreparedStatement) connection.prepareStatement(sql);
			resultSet =  ps.executeQuery();
			if (resultSet != null ) {
				while (resultSet.next()) {
					System.out.println(resultSet.getString(0) );
				}
			}
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
			closeConn(resultSet, ps, connection);
		}
		return result;

	}

	/**
	 * 根据id删除es记录
	 * 
	 * @param index
	 * @param deleteId
	 * @return
	 */
	public long delete(String index, String deleteId) {
		TransportClient client = EsConnectionFactory.createEsClient();
		BulkIndexByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
				.filter(QueryBuilders.matchQuery("QS_SESSION_ID", deleteId)).source(index).get();

		return response.getDeleted();
	}

	/**
	 * 获取es状态
	 */
	public void getHealth() {
		TransportClient client = EsConnectionFactory.createEsClient();
		ClusterHealthResponse healths = client.admin().cluster().prepareHealth().get();
		String clusterName = healths.getClusterName();
		System.out.println("clusterName->" + clusterName);
		int numberOfDataNodes = healths.getNumberOfDataNodes();
		System.out.println("numberOfDataNodes->" + numberOfDataNodes);
		int numberOfNodes = healths.getNumberOfNodes();
		System.out.println("numberOfNodes->" + numberOfNodes);
		for (ClusterIndexHealth health : healths.getIndices().values()) {
			String index = health.getIndex();
//			System.out.println("index->" + index);
			int numberOfShards = health.getNumberOfShards();
//			System.out.println("index->" + index + ",numberOfShards->" + numberOfShards);
			int numberOfReplicas = health.getNumberOfReplicas();
//			System.out.println("index->" + index + ",numberOfReplicas->" + numberOfReplicas);
			ClusterHealthStatus status = health.getStatus();
			System.out.println("index->" + index + ",status->" + status);
		}
	}
	
	/**
	 * 关闭连接
	 * @param ps
	 * @param connection
	 */
	private void closeConn(ResultSet resultSet,PreparedStatement ps,ElasticSearchDruidPooledConnection connection){
		try {
			if(resultSet!=null){
				resultSet.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(ps!=null){
				ps.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(connection!=null){
				connection.close();
			}
		} catch (Exception e) {
		e.printStackTrace();
		}
	
	}

}