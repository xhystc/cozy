import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.xhystc.cozy.Cozy;
import com.xhystc.cozy.core.Session;
import com.xhystc.cozy.core.Query;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @author xiehongyang
 * @date 2018/12/15 4:33 PM
 */
public class Test
{
    static public void main(String[] args) throws Exception {

        DataSource dataSource = null;
        Properties properties = new Properties();
        try {
            properties.load(Test.class.getClassLoader().getResourceAsStream("druid.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        properties.setProperty("username","nsc");
        properties.setProperty("password","nsc");
        properties.setProperty("url","jdbc:postgresql://localhost:5432/nsc");
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean is = true;
        Cozy.register("pg",dataSource);
        Session session = Cozy.createSession("pg");

        session.transaction();
       /* Query query = session.creqteQuery();
        Thread.sleep(1000);
        List<JSONObject> res = query.sql("select * from db_table where dbname like {dbname} and safelogid >= {id}").var("id",4216).var("dbname","%tam%").select();
        for(JSONObject json : res){
            System.out.println(json);
        }*/
        Query query = session.creqteQuery();

        List<String> res = query.sql("select s.id as s_id,s.dbname as s_dbname,s.tbname as s_tbname,s.overflow as s_overflow,d.json as d_json,d.tablename as d_tablename from  safelog_statistic s,db_table d where s.dbname = d.dbname limit 3").select();
        for(String json : res){
            System.out.println(json);
        }
       /* Query query = session.creqteQuery();
        query.sql("insert into safelog_statistic(dbname,tbname,statistic) values('xiix','sfs',535)");
        for(int i=0;i<100000;i++){
            query.update();
        }*/
     //  Query query = session.creqteQuery();


    //   System.out.println("update:"+query.sql("update db_table set tablename = {name} where safelogid = {id}").var("id",4200).var("name","eeee").update());

     //  System.out.println("update:"+query.sql("update db_table set tablename = {name} where safelogid = {id}").var("id",4210).var("name","hahaha").update());

        session.commit();
    }
}
