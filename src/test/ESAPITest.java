package test;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

/**
 * 
 * <pre>
 * es 客户端api测试。
 * </pre>
 * 
 * @author 王文辉 wangwenhui@jiaxincloud.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 *          </pre>
 */
public class ESAPITest extends EsJdbcDaoSupport {

	public static void main(String[] args) throws Exception {
		
		// 执行创建索引方法
//		st.createIndex();
////		 st.testDelete();
//		 for(int i=1;i<10;i++){
//			 Thread t = new Thread( new Runnable() {
//					@Override
//					public void run() {
//							 for(int j=1;j<1000;j++){
//							ESAPITest st1 = new ESAPITest();
//							long begin=System.currentTimeMillis();
//							 st1.putSDR(j);
//							long end=System.currentTimeMillis();
//							System.out.println("cost time->"+(end-begin)+"ms");
//						
//					}
//					}
//				});
//			 t.start();
//		 }
//for(int i=0;i<10;i++){
//	 Thread t = new Thread( new Runnable() {
//			@Override
//			public void run() {
//				int j=0;
//				while(true){
//					long begin=System.currentTimeMillis();
//					ESAPITest st = new ESAPITest();
//					 try {
//						st.testJDBC();
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
////					 j++;
////					 st.putSDR(j);
////						long end=System.currentTimeMillis();
////						System.out.println("cost time->"+(end-begin)+"ms");
//				     }
//				
//			}
//		});
//	 t.start();
//}
		// st.testDelete();
		// st.updateSDR();
		// st.getHealth();
		
		ESAPITest st = new ESAPITest();
		 st.deleteAllIndex();
//		st.createIndex();
	}

	@SuppressWarnings("resource")
	public void createIndex() {
//		Scanner scanner = new Scanner(System.in);
//		System.out.print("set cluster.name：");
//		String line = scanner.nextLine();
		// es集群名称
//		System.out.println("cluster.name->" + line);
//		String cluster_name = line;
		// es集群ip
//		System.out.print("set cluster.ip：");
//		String lineIp = scanner.nextLine();
//		System.out.println("cluster.ip->" + lineIp);
		System.out.println("****************clear all index first,please wait !******************");
		// 删除现有所有索引
		deleteAllIndex();
		System.out.println("****************creating index,please wait !******************");
		try {
//			// 逐个创建索引
//			create_st_seesion_tag_sdr("st_session_tag");
//			create_st_storage_files_sdr("st_storage_files");
//			create_st_cc_group_traffic_sdr("st_cc_group_traffic_sdr");
//			create_st_cc_agent_traffic_sdr("st_cc_agent_traffic_sdr");
//			create_st_agent_visitor_sdr("st_agent_visitor_sdr");
//			create_st_agent_visitor_manual_sdr("st_agent_visitor_manual_sdr");
//			create_st_agent_transfer_sdr("st_agent_transfer_sdr");
			create_st_agent_session_sdr("st_agent_session_sdr");
//			create_st_agent_satisfy_sdr("st_agent_satisfy_sdr");
//			create_st_agent_qs_detail_sdr("st_agent_qs_detail_sdr");
//			create_st_agent_order_sdr("st_agent_order_sdr");
//			create_st_agent_online_time_sdr("st_agent_online_time_sdr");
//			create_st_agent_online_sdr("st_agent_online_sdr");
//			create_cc_agent_status_history("cc_agent_status_history");
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("****************Congratulations! Indexs created successfully******************");
//		System.out.println("****************Let us see the index status!******************");
//		getHealth();
	}

	/**
	 * 创建es index
	 * 
	 * @throws Exception
	 */
	public void create_st_seesion_tag_sdr(String index) throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("settings")
				.field("number_of_shards", 5) // 分片
				.field("number_of_replicas", 1)// 副片
				.endObject().startObject("mappings").startObject(index.toUpperCase())// type
				.startObject("properties").startObject("ST_TAG_ID3").field("type", "integer").endObject() // 字段名
				.startObject("ST_TAG_ID2").field("type", "integer").endObject().startObject("ST_TAG_ID1")
				.field("type", "integer").endObject() // keyword 为不分析，text为分析字段
				.startObject("ST_TIME").field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																											// 为不分析，text为分析字段
				.startObject("ST_SESSION_ID").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("ST_AGENT_ID").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("ST_WORKGROUP_ID").field("type", "keyword").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("ST_IS_TRANSFER").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("ST_ENTERPRISE_ID").field("type", "keyword").endObject() // keyword
																						// 为不分析，text为分析字段
				.endObject().endObject().endObject().endObject();

		TransportClient client = EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index.toLowerCase())// index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println(index + "->Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
//		client.close();
	}

	/**
	 * 创建es index
	 * 
	 * @throws Exception
	 */
	public void create_st_storage_files_sdr(String index) throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("settings")
				.field("number_of_shards", 5) // 分片
				.field("number_of_replicas", 1)// 副片
				.endObject().startObject("mappings").startObject(index.toUpperCase())// type
				.startObject("properties").startObject("BUCKET").field("type", "keyword").endObject() // 字段名
				.startObject("FROM_MODULE").field("type", "integer").endObject().startObject("SIZE")
				.field("type", "long").endObject() // keyword 为不分析，text为分析字段
				.startObject("EXPIRED_TIME").field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																													// 为不分析，text为分析字段
				.startObject("CREATE_TIME").field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																												// 为不分析，text为分析字段
				.startObject("FILE_TYPE").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("ID").field("type", "keyword").endObject() // keyword
																		// 为不分析，text为分析字段
				.startObject("ENTERPRISE_ID").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("FILE_NAME").field("type", "keyword").endObject() // keyword
																				// 为不分析，text为分析字段
				.endObject().endObject().endObject().endObject();

		TransportClient client = EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index.toLowerCase())// index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println(index + "->Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
//		client.close();
	}

	/**
	 * 创建es index
	 * 
	 * @throws Exception
	 */
	public void create_st_cc_group_traffic_sdr(String index) throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("settings")
				.field("number_of_shards", 5) // 分片
				.field("number_of_replicas", 1)// 副片
				.endObject().startObject("mappings").startObject(index.toUpperCase())// type
				.startObject("properties").startObject("CI_LEVEL_4").field("type", "integer").endObject() // 字段名
				.startObject("CI_LEVEL_5").field("type", "integer").endObject().startObject("MAX_CI_QUEUE_DURATION")
				.field("type", "integer").endObject() // keyword 为不分析，text为分析字段
				.startObject("INSERT_TIMES").field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																													// 为不分析，text为分析字段
				.startObject("CST_DURATION").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("D_0_6").field("type", "integer").endObject() // keyword
																			// 为不分析，text为分析字段
				.startObject("MIN_CI_QUEUE_DURATION").field("type", "integer").endObject() // keyword
																							// 为不分析，text为分析字段
				.startObject("CI_QUEUE_DURATION").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("P_6_10").field("type", "integer").endObject() // keyword
																			// 为不分析，text为分析字段
				.startObject("MIN_CO_DURATION").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("QUEUE_ID").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("TRSF_ANSWER_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("MAX_CO_DURATION").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("P_16_X").field("type", "integer").endObject() // keyword
																			// 为不分析，text为分析字段
				.startObject("CO_LEVEL_1").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("MIN_CO_ALTERTING_DURATION").field("type", "integer").endObject() // keyword
																								// 为不分析，text为分析字段
				.startObject("ID").field("type", "keyword").endObject() // keyword
																		// 为不分析，text为分析字段
				.startObject("CO_LEVEL_4").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CO_LEVEL_5").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CI_NO_ANSWER_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("CO_LEVEL_2").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CO_LEVEL_3").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CO_NO_ANSWER_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("CI_QUEUE_GIVEUP_TIMES").field("type", "integer").endObject() // keyword
																							// 为不分析，text为分析字段
				.startObject("CO_FAIL_TIMES").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("CI_ALERTING_DURATION").field("type", "integer").endObject() // keyword
																							// 为不分析，text为分析字段
				.startObject("P_11_15").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("TRSF_DURATION").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("MIN_TRSF_DURATION").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("MAX_CI_DURATION").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("OS_DURATION").field("type", "long").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CST_ANSWER_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("CI_ALERTING_HANDUP_TIMES").field("type", "integer").endObject() // keyword
																								// 为不分析，text为分析字段
				.startObject("CI_QUEUE_TIMES").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("CO_TIMES").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("MIN_CST_DURATION").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("MAX_TRSF_DURATION").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("MAX_CI_ALERTING_DURATION").field("type", "integer").endObject() // keyword
																								// 为不分析，text为分析字段
				.startObject("CO_DURATION").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("MIN_CI_ALERTING_DURATION").field("type", "integer").endObject() // keyword
																								// 为不分析，text为分析字段
				.startObject("MAX_CO_ALTERTING_DURATION").field("type", "integer").endObject() // keyword
																								// 为不分析，text为分析字段
				.startObject("MAX_CST_DURATION").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("CI_DURATION").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("CST_TIMES").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("DAY_POINT").field("type", "keyword").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("TRSF_TIMES").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("APP_ID").field("type", "integer").endObject() // keyword
																			// 为不分析，text为分析字段
				.startObject("CI_ALERTING_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("OS_ALERTING_DURATION").field("type", "long").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("CI_REJECT_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("P_0_5").field("type", "integer").endObject() // keyword
																			// 为不分析，text为分析字段
				.startObject("CI_QUEUE_OVERFLOW").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("P_0_20").field("type", "integer").endObject() // keyword
																			// 为不分析，text为分析字段
				.startObject("CI_ANSWER_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("MIN_CI_DURATION").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("TIME_POINT").field("type", "keyword").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CO_REJECT_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("CI_TIMES").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CO_ALTERTING_DURATION").field("type", "integer").endObject() // keyword
																							// 为不分析，text为分析字段
				.startObject("CI_LEVEL_1").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CI_LEVEL_2").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CO_ANSWER_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("CI_LEVEL_3").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.endObject().endObject().endObject().endObject();

		TransportClient client = EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index.toLowerCase())// index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println(index + "->Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
//		client.close();
	}

	/**
	 * 创建es index
	 * 
	 * @throws Exception
	 */
	public void create_st_cc_agent_traffic_sdr(String index) throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("settings")
				.field("number_of_shards", 5) // 分片
				.field("number_of_replicas", 1)// 副片
				.endObject().startObject("mappings").startObject(index.toUpperCase())// type
				.startObject("properties").startObject("CI_LEVEL_4").field("type", "integer").endObject() // 字段名
				.startObject("CI_LEVEL_5").field("type", "integer").endObject().startObject("INSERT_TIMES")
				.field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("CI_ALERTING_HANDUP_TIMES").field("type", "integer").endObject() // keyword
																								// 为不分析，text为分析字段
				.startObject("CST_DURATION").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("CO_TIMES").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("P_6_10").field("type", "integer").endObject() // keyword
																			// 为不分析，text为分析字段
				.startObject("CO_DURATION").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("QUEUE_ID").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("TRSF_ANSWER_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("CI_DURATION").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("P_16_X").field("type", "integer").endObject() // keyword
																			// 为不分析，text为分析字段
				.startObject("CO_LEVEL_1").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("ID").field("type", "keyword").endObject() // keyword
																		// 为不分析，text为分析字段
				.startObject("CO_LEVEL_4").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CO_LEVEL_5").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CI_NO_ANSWER_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("CO_LEVEL_2").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CLIENT_NUMBER").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("CO_LEVEL_3").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CO_NO_ANSWER_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("CST_TIMES").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("DAY_POINT").field("type", "keyword").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("TRSF_TIMES").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("APP_ID").field("type", "integer").endObject() // keyword
																			// 为不分析，text为分析字段
				.startObject("CI_ALERTING_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("OS_ALERTING_DURATION").field("type", "long").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("CI_REJECT_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("P_0_5").field("type", "integer").endObject() // keyword
																			// 为不分析，text为分析字段
				.startObject("CO_FAIL_TIMES").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("CI_ALERTING_DURATION").field("type", "integer").endObject() // keyword
																							// 为不分析，text为分析字段
				.startObject("P_0_20").field("type", "integer").endObject() // keyword
																			// 为不分析，text为分析字段
				.startObject("CI_ANSWER_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("P_11_15").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("TRSF_DURATION").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("TIME_POINT").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CO_REJECT_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("CI_TIMES").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CO_ALTERTING_DURATION").field("type", "integer").endObject() // keyword
																							// 为不分析，text为分析字段
				.startObject("CI_LEVEL_1").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("OS_DURATION").field("type", "long").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CI_LEVEL_2").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CO_ANSWER_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("CI_LEVEL_3").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("CST_ANSWER_TIMES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.endObject().endObject().endObject().endObject();

		TransportClient client = EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index.toLowerCase())// index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println(index + "->Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
//		client.close();
	}

	/**
	 * 创建es index
	 * 
	 * @throws Exception
	 */
	public void create_st_agent_visitor_sdr(String index) throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("settings")
				.field("number_of_shards", 5) // 分片
				.field("number_of_replicas", 1)// 副片
				.endObject().startObject("mappings").startObject(index.toUpperCase())// type
				.startObject("properties").startObject("VS_VISITOR_TERMINAL").field("type", "integer").endObject() // 字段名
				.startObject("VS_APP_NAME").field("type", "keyword").endObject().startObject("VS_VISITOR_CHANNEL")
				.field("type", "integer").endObject() // keyword 为不分析，text为分析字段
				.startObject("VS_VISITOR_ID").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("VS_VISITOR_ENGINE_KEY").field("type", "keyword").endObject() // keyword
																							// 为不分析，text为分析字段
				.startObject("VS_VISITOR_PAGE_TITLE").field("type", "keyword").endObject() // keyword
																							// 为不分析，text为分析字段
				.startObject("VS_VISITOR_AREA").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("VS_CHANNEL_NO").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("VS_VISITOR_IP").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("VS_ENTERPRISE_ID").field("type", "keyword").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("VS_TIME").field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																											// 为不分析，text为分析字段
				.startObject("VS_VISITOR_PAGE_URL").field("type", "keyword").endObject() // keyword
																							// 为不分析，text为分析字段
				.startObject("VS_WORKGROUP_ID").field("type", "keyword").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("VS_VISITOR_SEQUENCE").field("type", "keyword").endObject() // keyword
																							// 为不分析，text为分析字段
				.startObject("VS_VISITOR_ENGINE").field("type", "keyword").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("VS_ID").field("type", "keyword").endObject() // keyword
																			// 为不分析，text为分析字段
				.startObject("VS_VISITOR_AREA_NAME").field("type", "keyword").endObject() // keyword
																							// 为不分析，text为分析字段
				.endObject().endObject().endObject().endObject();

		TransportClient client = EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index.toLowerCase())// index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println(index + "->Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
//		client.close();
	}

	/**
	 * 创建es index
	 * 
	 * @throws Exception
	 */
	public void create_st_agent_visitor_manual_sdr(String index) throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("settings")
				.field("number_of_shards", 5) // 分片
				.field("number_of_replicas", 1)// 副片
				.endObject().startObject("mappings").startObject(index.toUpperCase())// type
				.startObject("properties").startObject("VS_VISITOR_TERMINAL").field("type", "integer").endObject() // 字段名
				.startObject("VS_APP_NAME").field("type", "keyword").endObject().startObject("VS_QUEUE_WAIT_TIME")
				.field("type", "long").endObject() // keyword 为不分析，text为分析字段
				.startObject("VS_VISITOR_CHANNEL").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("VS_VISITOR_ID").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("VS_VISITOR_SDR_ID").field("type", "keyword").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("VS_VISITOR_AREA").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("VS_CHANNEL_NO").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("VS_REQ_STATE").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("VS_ENTERPRISE_ID").field("type", "keyword").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("VS_TIME").field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																											// 为不分析，text为分析字段
				.startObject("VS_SESSION_ID").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("VS_WORKGROUP_ID").field("type", "keyword").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("VS_STATUS").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("VS_ID").field("type", "keyword").endObject() // keyword
																			// 为不分析，text为分析字段
				.endObject().endObject().endObject().endObject();

		TransportClient client = EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index.toLowerCase())// index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println(index + "->Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
//		client.close();
	}

	/**
	 * 创建es index
	 * 
	 * @throws Exception
	 */
	public void create_st_agent_transfer_sdr(String index) throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("settings")
				.field("number_of_shards", 5) // 分片
				.field("number_of_replicas", 1)// 副片
				.endObject().startObject("mappings").startObject(index.toUpperCase())// type
				.startObject("properties").startObject("TS_FROM_AGENT_TYPE").field("type", "integer").endObject() // 字段名
				.startObject("TS_VISITOR_CHANNEL").field("type", "integer").endObject().startObject("TS_TO_AGENT_ID")
				.field("type", "keyword").endObject() // keyword 为不分析，text为分析字段
				.startObject("TS_APP_NAME").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("TS_SESSION_ID").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("TS_FROM_WORKGROUP_ID").field("type", "keyword").endObject() // keyword
																							// 为不分析，text为分析字段
				.startObject("TS_VISITOR_AREA").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("TS_TO_WORKGROUP_ID").field("type", "keyword").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("TS_CHANNEL_NO").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("TS_VISITOR_TERMINAL").field("type", "integer").endObject() // keyword
																							// 为不分析，text为分析字段
				.startObject("TS_TIME").field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																											// 为不分析，text为分析字段
				.startObject("TS_FROM_AGENT_ID").field("type", "keyword").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("TS_ENTERPRISE_ID").field("type", "keyword").endObject() // keyword
																						// 为不分析，text为分析字段
				.endObject().endObject().endObject().endObject();

		TransportClient client = EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index.toLowerCase())// index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println(index + "->Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
//		client.close();
	}

	/**
	 * 创建es index
	 * 
	 * @throws Exception
	 */
	public void create_st_agent_session_sdr(String index) throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("settings")
				.field("number_of_shards", 5) // 分片
				.field("number_of_replicas", 1)// 副片
				.endObject().startObject("mappings").startObject(index.toUpperCase())// type
				.startObject("properties").startObject("SS_VISITOR_AREA").field("type", "integer").endObject() // 字段名
				.startObject("SS_ENTERPRISE_ID").field("type", "keyword").endObject().startObject("SS_VISITOR_ID")
				.field("type", "keyword").endObject() // keyword 为不分析，text为分析字段
				.startObject("SS_VISITOR_CHANNEL").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("SS_VISITOR_MESSAGES").field("type", "integer").endObject() // keyword
																							// 为不分析，text为分析字段
				.startObject("SS_APP_NAME").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("SS_AVG_RESP").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("SS_DURATION").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("SS_CHANNEL_NO").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("SS_REQ_STATE").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("SS_END_TIME").field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																												// 为不分析，text为分析字段
				.startObject("SS_F_RESP").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("SS_WORGROUP_ID").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("SS_TIME").field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																											// 为不分析，text为分析字段
				.startObject("SS_AGENT_ID").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("SS_TIMEOUT").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("SS_SESSION_ID").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("SS_AGENT_TYPE").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("SS_START_TIME").field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																													// 为不分析，text为分析字段
				.startObject("SS_TIMESTAMP").field("type", "text").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("SS_QUEUE_WAIT_TIME").field("type", "long").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("SS_VISITOR_TERMINAL").field("type", "integer").endObject() // keyword
																							// 为不分析，text为分析字段
				.startObject("SS_MESSAGES").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("SS_VALID_SESSION").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("SS_AGENT_MESSAGES").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("SS_MAX_RESP").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.endObject().endObject().endObject().endObject();

		TransportClient client = EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index.toLowerCase())// index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println(index + "->Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
//		client.close();
	}

	/**
	 * 创建es index
	 * 
	 * @throws Exception
	 */
	public void create_st_agent_satisfy_sdr(String index) throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("settings")
				.field("number_of_shards", 5) // 分片
				.field("number_of_replicas", 1)// 副片
				.endObject().startObject("mappings").startObject(index.toUpperCase())// type
				.startObject("properties").startObject("SS_APP_NAME").field("type", "keyword").endObject() // 字段名
				.startObject("SS_WORGROUP_ID").field("type", "keyword").endObject().startObject("SS_TIME")
				.field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("SS_VISITOR_AREA").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("SS_AGENT_ID").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("SS_SESSION_ID").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("SS_CHANNEL_NO").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("SS_ENTERPRISE_ID").field("type", "keyword").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("SS_VISITOR_ID").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("SS_VISITOR_TERMINAL").field("type", "integer").endObject() // keyword
																							// 为不分析，text为分析字段
				.startObject("SS_VISITOR_CHANNEL").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("SS_SATISFY_SCORE").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.endObject().endObject().endObject().endObject();

		TransportClient client = EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index.toLowerCase())// index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println(index + "->Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
//		client.close();
	}

	/**
	 * 创建es index
	 * 
	 * @throws Exception
	 */
	public void create_st_agent_qs_detail_sdr(String index) throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("settings")
				.field("number_of_shards", 5) // 分片
				.field("number_of_replicas", 1)// 副片
				.endObject().startObject("mappings").startObject(index.toUpperCase())// type
				.startObject("properties").startObject("QS_ENTERPRISE_ID").field("type", "keyword").endObject() // 字段名
				.startObject("QS_SESSION_AGENT_ID").field("type", "keyword").endObject().startObject("QS_ID")
				.field("type", "keyword").endObject().startObject("QS_SESSION_ID").field("type", "keyword").endObject() // keyword
																														// 为不分析，text为分析字段
				.startObject("QS_TIME").field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																											// 为不分析，text为分析字段
				.startObject("QS_QS_AGENT_ID").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("QS_SCORE_TYPE_ID").field("type", "keyword").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("QS_LEVEL_ID").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("QS_SESSION_TIME").field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																													// 为不分析，text为分析字段
				.startObject("QS_SESSION_WORKGROUP_ID").field("type", "keyword").endObject() // keyword
																								// 为不分析，text为分析字段
				.startObject("QS_BIZ_TYPE").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.endObject().endObject().endObject().endObject();

		TransportClient client = EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index.toLowerCase())// index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println(index + "->Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
//		client.close();
	}

	/**
	 * 创建es index
	 * 
	 * @throws Exception
	 */
	public void create_st_agent_order_sdr(String index) throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("settings")
				.field("number_of_shards", 5) // 分片
				.field("number_of_replicas", 1)// 副片
				.endObject().startObject("mappings").startObject(index.toUpperCase())// type
				.startObject("properties").startObject("SS_CLOSED_ORDERS").field("type", "integer").endObject() // 字段名
				.startObject("SS_AGENT_ID").field("type", "keyword").endObject().startObject("SS_SOLVED_TIME")
				.field("type", "integer").endObject().startObject("SS_ENTERPRISE_ID").field("type", "keyword")
				.endObject() // keyword 为不分析，text为分析字段
				.startObject("SS_TIME").field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																											// 为不分析，text为分析字段
				.startObject("SS_RESP_TIME").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("SS_NEW_ORDERS").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("SS_SOLVED_ORDERS").field("type", "integer").endObject() // keyword
																						// 为不分析，text为分析字段
				.endObject().endObject().endObject().endObject();

		TransportClient client = EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index.toLowerCase())// index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println(index + "->Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
//		client.close();
	}

	/**
	 * 创建es index
	 * 
	 * @throws Exception
	 */
	public void create_st_agent_online_time_sdr(String index) throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("settings")
				.field("number_of_shards", 5) // 分片
				.field("number_of_replicas", 1)// 副片
				.endObject().startObject("mappings").startObject(index.toUpperCase())// type
				.startObject("properties").startObject("AOTS_ONLINE_DURATION").field("type", "long").endObject() // 字段名
				.startObject("AOTS_RESOURCE").field("type", "integer").endObject().startObject("AOTS_AGENT_ID")
				.field("type", "keyword").endObject().startObject("AOTS_LOGIN_TIME").field("type", "date")
				.field("format", "yyyyMMddHHmmss").endObject() // keyword
																// 为不分析，text为分析字段
				.startObject("AOTS_LOGOUT_TIME").field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																														// 为不分析，text为分析字段
				.startObject("AOTS_WORKGROUP_ID").field("type", "keyword").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("AOTS_ENTERPRISE_ID").field("type", "keyword").endObject() // keyword
																						// 为不分析，text为分析字段
				.endObject().endObject().endObject().endObject();

		TransportClient client = EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index.toLowerCase())// index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println(index + "->Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
//		client.close();
	}

	/**
	 * 创建es index
	 * 
	 * @throws Exception
	 */
	public void create_st_agent_online_sdr(String index) throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("settings")
				.field("number_of_shards", 5) // 分片
				.field("number_of_replicas", 1)// 副片
				.endObject().startObject("mappings").startObject(index.toUpperCase())// type
				.startObject("properties").startObject("OS_AGENT_ID").field("type", "keyword").endObject() // 字段名
				.startObject("OS_WORKGROUP_ID").field("type", "keyword").endObject() // 字段名
				.startObject("OS_ENTERPRISE_ID").field("type", "keyword").endObject() // 字段名
				.startObject("OS_ONLINE_TIME").field("type", "long").endObject().startObject("OS_TIME")
				.field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("OS_APP_NAME").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.endObject().endObject().endObject().endObject();

		TransportClient client = EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index.toLowerCase())// index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println(index + "->Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
//		client.close();
	}

	/**
	 * 创建es index
	 * 
	 * @throws Exception
	 */
	public void create_cc_agent_status_history(String index) throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("settings")
				.field("number_of_shards", 5) // 分片
				.field("number_of_replicas", 1)// 副片
				.endObject().startObject("mappings").startObject(index.toUpperCase())// type
				.startObject("properties").startObject("WORKGROUP_JID").field("type", "keyword").endObject() // 字段名
				.startObject("ID").field("type", "keyword").endObject() // 字段名
				.startObject("STATUS_ID").field("type", "keyword").endObject() // 字段名
				.startObject("AGENT_JID").field("type", "keyword").endObject().startObject("INSERT_TIME")
				.field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("APP_KEY").field("type", "keyword").endObject() // keyword
																				// 为不分析，text为分析字段
				.startObject("DURATION").field("type", "integer").endObject() // keyword
																				// 为不分析，text为分析字段
				.endObject().endObject().endObject().endObject();

		TransportClient client = EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index.toLowerCase())// index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println(index + "->Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
//		client.close();
	}

	/**
	 * 创建es index
	 * 
	 * @throws Exception
	 */
	public void createSDRIndex(String index) throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("settings")
				.field("number_of_shards", 5) // 分片
				.field("number_of_replicas", 1)// 副片
				.endObject().startObject("mappings").startObject(index.toUpperCase())// type
				.startObject("properties").startObject("ST_TAG_ID3").field("type", "integer").endObject() // 字段名
				.startObject("ST_TAG_ID2").field("type", "integer").endObject().startObject("ST_TAG_ID1")
				.field("type", "integer").endObject() // keyword 为不分析，text为分析字段
				.startObject("ST_TIME").field("type", "date").field("format", "yyyyMMddHHmmss").endObject() // keyword
																											// 为不分析，text为分析字段
				.startObject("ST_SESSION_ID").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("ST_AGENT_ID").field("type", "keyword").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("ST_WORKGROUP_ID").field("type", "keyword").endObject() // keyword
																						// 为不分析，text为分析字段
				.startObject("ST_IS_TRANSFER").field("type", "integer").endObject() // keyword
																					// 为不分析，text为分析字段
				.startObject("ST_ENTERPRISE_ID").field("type", "keyword").endObject() // keyword
																						// 为不分析，text为分析字段
				.endObject().endObject().endObject().endObject();

		TransportClient client = EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index.toLowerCase())// index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println(index + "->Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
		client.close();
	}
	
	/**
	 * 创建es index
	 * 
	 * @throws Exception
	 */
	public static void createFundIndex(String index) throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("settings")
				.field("number_of_shards", 5) // 分片
				.field("number_of_replicas", 1)// 副片
				.endObject().startObject("mappings").startObject(index.toUpperCase())// type
				.startObject("properties")
				.startObject("name").field("type", "keyword").endObject() // 字段名
				.startObject("fund_id").field("type", "keyword").endObject()
				.startObject("sumVal").field("type", "float").endObject()
				.startObject("bonus").field("type", "float").endObject()
				.startObject("oneMonthPercent").field("type", "float").endObject()
				.startObject("threeMonthPercent").field("type", "float").endObject()
				.startObject("sixMonthPercent").field("type", "float").endObject()
				.startObject("oneYearPercent").field("type", "float").endObject()
				.startObject("threeYearPercent").field("type", "float").endObject()
				.startObject("openPercent").field("type", "float").endObject()
				.startObject("pastValPercent").field("type", "float").endObject()
				.startObject("pastVal").field("type", "float").endObject() // keyword 为不分析，text为分析字段
				.startObject("currentVal").field("type", "float").endObject() // keyword 为不分析，text为分析字段
				.startObject("openDay").field("type", "date").field("format", "yyyy-MM-dd").endObject() // keyword
				.startObject("type").field("type", "keyword").endObject() // keyword
				.endObject().endObject().endObject().endObject();
		
		TransportClient client = EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index.toLowerCase())// index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println(index + "->Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
		client.close();
	}

	/**
	 * 更新index 字段
	 */
	public void updateIndex() {
		TransportClient client = EsConnectionFactory.createEsClient();
		client.admin().indices()
				.preparePutMapping("test3").setType("TEST3").setSource("{\n" + "  \"properties\": {\n"
						+ "    \"QS_SESSION_ID\": {\n" + "      \"type\": \"keyword\"\n" + "    }\n" + "  }\n" + "}")
				.get();
	}

	/**
	 * 插入doc
	 */
	public void putSDR(int i) {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("ST_WORKGROUP_ID", System.currentTimeMillis());
		json.put("ST_ENTERPRISE_ID", "ctdiznh1b3rnbg");
		json.put("ST_SESSION_ID", "fhnxzt13015-hui3-" + i);
		json.put("ST_TAG_ID3",  i);
		json.put("ST_AGENT_ID", "fhnxzt13015-hui3-" + i);
		insert("st_session_tag", "ST_SESSION_TAG", json);
		System.out.println("insert success.");
	}
	/**
	 * 插入doc
	 */
	public void putSDRFromCsdn(String index,String type,Map<String,Object>dataMap) {
		insert(index, type, dataMap);
		System.out.println("insert success.");
	}

	/**
	 * 更新doc
	 */
	public void updateSDR() {
		SatisfySDREntity s = new SatisfySDREntity();
		update("st_agent_session_sdr", "ST_AGENT_SESSION_SDR", s);
	}

	/**
	 * 通过sql 插件查询es数据
	 * 
	 * @throws Exception
	 */
	public void testJDBC() throws Exception {
		String sql = "SELECT * from st_session_tag ";
		getModeList("st_session_tag", sql, null);
//		try {
//			while (rs.next()) {
//				System.out.println(rs.getString("ST_ENTERPRISE_ID"));
//				// System.out.println( rs.getString("QS_ENTERPRISE_ID") );
//
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			String error = e.getCause().toString();
//			if (error.indexOf("IndexOutOfBoundsException") > 0) {
//				System.out.println("data is null");
//			}
//
//		}

	}

	/**
	 * 查询删除API
	 * 
	 * @throws Exception
	 */
	public void testDelete() throws Exception {
		System.out.println(delete("test3", "fhnxzt13015-hui"));
	}

	/**
	 * 删除索引
	 */
	public void testDeleteIndex() {
		TransportClient client = EsConnectionFactory.createEsClient();
		DeleteIndexResponse dResponse = client.admin().indices().prepareDelete("test2").execute().actionGet();
		System.out.println(dResponse.isAcknowledged());
	}

	public static boolean checkObjectIsArray(final Object data) {
		boolean result = false;
		if (data == null) {
			return result;
		}
		boolean isArray = data.getClass().isArray();
		if (isArray) {
			int length = Array.getLength(data);
			if (length > 0) {
				result = true;
			}
		}
		return result;
	}

	// 判断索引是否存在 传入参数为索引库名称
	public static boolean isIndexExists(String indexName) {
		boolean flag = false;
		TransportClient client = EsConnectionFactory.createEsClient();

		IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(indexName);

		IndicesExistsResponse inExistsResponse = client.admin().indices().exists(inExistsRequest).actionGet();

		if (inExistsResponse.isExists()) {
			flag = true;
		} else {
			flag = false;
		}

		return flag;
	}

	// 删除索引库

	public static boolean deleteIndex(String indexName) {
		boolean result = false;
		TransportClient client = EsConnectionFactory.createEsClient();

		DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(indexName).execute().actionGet();
		if (dResponse.isAcknowledged()) {
			result = true;
			System.out.println("delete index " + indexName + "  successfully!");
		} else {
			System.out.println("Fail to delete index " + indexName);
		}
		return result;
	}

	public static void deleteAllIndex() {
		TransportClient client = EsConnectionFactory2.transportClient;
		ClusterStateResponse response = client.admin().cluster().prepareState().execute().actionGet();
		// 获取所有索引
		String[] indexs = response.getState().getMetaData().getConcreteAllIndices();
		for (String index : indexs) {
			if(index!=null&&index.equals(".kibana")){
				continue;
			}
			// 清空所有索引。
			DeleteIndexResponse deleteIndexResponse = client.admin().indices().prepareDelete(index).execute()
					.actionGet();
			if (deleteIndexResponse.isAcknowledged()) {
				System.out.println(index + " delete");//
			}

		}
	}
}
