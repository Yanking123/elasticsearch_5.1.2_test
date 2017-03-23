/**
 * Copyright(c) Guangzhou JiaxinCloud Science & Technology Ltd. 
 */
package test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author 王文辉  wangwenhui@jiaxincloud.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class StreamTest {
       public static void main(String[] args) {
    	   int [] array=new int[]{3,2,6,2,6,4,7,8,12,10};
    	   ScriptEngineManager manager = new ScriptEngineManager();
    	   ScriptEngine engine = manager.getEngineByName( "SHELL" );
    	            
    	   System.out.println( engine.getClass().getName() );
    	   try {
			System.out.println( "Result:" + engine.eval( "echo '3232';" ) );
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}
