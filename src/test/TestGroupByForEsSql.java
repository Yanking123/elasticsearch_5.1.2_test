package test;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

/**
 * 
 * <pre>
 * es 客户端api测试。
 * 使用sql插件 -测试查询group by问题
 *  通过测试，工程内没因为最新修改后的jar包不生效，需要引用最新jar包
 *  放到插件中用只是在网页sql插件中使用
 *  所以修改的地方有两个：
 *  1：工程内覆盖原来的jar(elasticsearch-5.1.2.jar)
 *  2：es插件目录下引用修改后的jar(elasticsearch-5.1.2.jar)
 *  只需替换一个jar即可
 * </pre>
 * @author 王文辉  wangwenhui@jiaxincloud.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class TestGroupByForEsSql extends EsJdbcDaoSupport {

	public  static void main(String[] args) throws Exception {
		TestGroupByForEsSql st =new TestGroupByForEsSql();
		st.testJDBC();
	}

	/**
	 * 创建es index
	 * @throws Exception
	 */
	public  void create_st_cc_agent_traffic_sdr(String index) throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder()
				.startObject()
				.startObject("settings")
				.field("number_of_shards", 5) //分片
				.field("number_of_replicas", 1)//副片
				.endObject()
				.startObject("mappings")
				.startObject(index.toUpperCase())//type
				.startObject("properties") 
				.startObject("CI_LEVEL_4").field("type", "integer").endObject() //字段名
				.startObject("CI_LEVEL_5").field("type", "integer").endObject()
				.startObject("INSERT_TIMES").field("type", "date").field("format", "yyyyMMddHHmmss").endObject() //keyword 为不分析，text为分析字段
				.startObject("CI_ALERTING_HANDUP_TIMES").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CST_DURATION").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CO_TIMES").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("P_6_10").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CO_DURATION").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("QUEUE_ID").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("TRSF_ANSWER_TIMES").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CI_DURATION").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("P_16_X").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CO_LEVEL_1").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("ID").field("type", "keyword").endObject() //keyword 为不分析，text为分析字段
				.startObject("CO_LEVEL_4").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CO_LEVEL_5").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CI_NO_ANSWER_TIMES").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CO_LEVEL_2").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CLIENT_NUMBER").field("type", "keyword").endObject() //keyword 为不分析，text为分析字段
				.startObject("CO_LEVEL_3").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CO_NO_ANSWER_TIMES").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CST_TIMES").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("DAY_POINT").field("type", "keyword").endObject() //keyword 为不分析，text为分析字段
				.startObject("TRSF_TIMES").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("APP_ID").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CI_ALERTING_TIMES").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("OS_ALERTING_DURATION").field("type", "long").endObject() //keyword 为不分析，text为分析字段
				.startObject("CI_REJECT_TIMES").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("P_0_5").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CO_FAIL_TIMES").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CI_ALERTING_DURATION").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("P_0_20").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CI_ANSWER_TIMES").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("P_11_15").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("TRSF_DURATION").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("TIME_POINT").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CO_REJECT_TIMES").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CI_TIMES").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CO_ALTERTING_DURATION").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CI_LEVEL_1").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("OS_DURATION").field("type", "long").endObject() //keyword 为不分析，text为分析字段
				.startObject("CI_LEVEL_2").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CO_ANSWER_TIMES").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CI_LEVEL_3").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.startObject("CST_ANSWER_TIMES").field("type", "integer").endObject() //keyword 为不分析，text为分析字段
				.endObject()
				.endObject()
				.endObject()
				.endObject();
		
		TransportClient client =EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate(index.toLowerCase())//index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println(index+"->Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
		client.close();
	}
	
	
	
	/**
	 * 通过sql 插件查询es数据
	 * @throws Exception
	 */
    public  void testJDBC() throws Exception {
    	String sql="SELECT * FROM st_cc_agent_traffic_sdr group by APP_ID,QUEUE_ID,CLIENT_NUMBER";
    	getModeList("st_cc_agent_traffic_sdr", sql, null);
      
    }
  
}
