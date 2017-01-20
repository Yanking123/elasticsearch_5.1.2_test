package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

/**
 * 
 * <pre>
 * es 客户端api测试。
 * </pre>
 * @author 王文辉  wangwenhui@jiaxincloud.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ESAPITest extends EsJdbcDaoSupport {

	public  static void main(String[] args) throws Exception {
		ESAPITest st =new ESAPITest();
//		st.updateIndex();
//		st.createSDRIndex();
//		st.testDelete();
//		st.putSDR();
//		st.testJDBC();
//		st.testDelete();
		st.updateSDR();
		st.getHealth();
//		st.testDeleteIndex();
	}

	/**
	 * 创建es index
	 * @throws Exception
	 */
	public  void createSDRIndex() throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder()
				.startObject()
				.startObject("settings")
				.field("number_of_shards", 5) //分片
				.field("number_of_replicas", 0)//副片
				.endObject()
					.startObject("mappings")
						.startObject("TEST3")//type
								.startObject("properties") 
							.startObject("QS_ENTERPRISE_ID").field("type", "keyword").endObject() //字段名
							.startObject("QS_SESSION_ID").field("type", "keyword").endObject()
							.startObject("QS_ID").field("type", "keyword").endObject() //keyword 为不分析，text为分析字段
								.endObject()
						.endObject()
					.endObject()
					.endObject();

		TransportClient client =EsConnectionFactory.createEsClient();
		CreateIndexRequestBuilder cirb = client.admin().indices().prepareCreate("test3")//index名
				.setSource(mapping);
		CreateIndexResponse response = cirb.execute().actionGet();
		if (response.isAcknowledged()) {
			System.out.println("Index created.");
		} else {
			System.err.println("Index creation failed.");
		}
		client.close();
	}

	/**
	 * 更新index 字段
	 */
	public void updateIndex(){
		TransportClient client =EsConnectionFactory.createEsClient();
		client.admin().indices().preparePutMapping("test3")   
        .setType("TEST3")                                
        .setSource("{\n" +                              
                "  \"properties\": {\n" +
                "    \"QS_SESSION_ID\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    }\n" +
                "  }\n" +
                "}")
        .get();
	}
	
	
	/**
	 * 插入doc
	 */
	public  void putSDR() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("QS_ID", "20160829152817");
		json.put("QS_ENTERPRISE_ID", "ctdiznh1b3rnbg");
		json.put("QS_SESSION_ID", "fhnxzt13015-hui3");
		insert("test3", "TEST3", json);
		System.out.println("insert success.");
	}
	
	/**
	 * 更新doc
	 */
	public  void  updateSDR() {
		SatisfySDREntity s=new SatisfySDREntity();
		update("test3", "TEST3", s);
	}
	
	/**
	 * 通过sql 插件查询es数据
	 * @throws Exception
	 */
    public  void testJDBC() throws Exception {
    	String sql="SELECT * from test3 group by QS_SESSION_ID";
    	ResultSet rs=query(sql,"test3");
  try{
	  while (rs.next()) {
    	  System.out.println( rs.getString("QS_SESSION_ID") );
//          System.out.println( rs.getString("QS_ENTERPRISE_ID") );
     
    }
  }catch(SQLException e ){
		 String error=e.getCause().toString();
		 if(error.indexOf("IndexOutOfBoundsException")>0){
				System.out.println("data is null");
		 }
  
  }
      
    }
    
    /**
     * 查询删除API
     * @throws Exception
     */
    public  void testDelete() throws Exception {
    	System.out.println(delete ("test3","fhnxzt13015-hui"));
    }
    
    /**
     *删除索引
     */
  public void testDeleteIndex(){
		TransportClient client =EsConnectionFactory.createEsClient();
	      DeleteIndexResponse dResponse = client.admin().indices().prepareDelete("test2")
              .execute().actionGet();
	      System.out.println(dResponse.isAcknowledged());
  }
}
