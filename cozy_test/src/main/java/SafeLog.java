import com.xhystc.cozy.core.Entity;
import com.xhystc.cozy.core.Field;

/**
 * @author xiehongyang
 * @date 2018/12/16 4:23 PM
 */
@Entity(prefix = "s")
public class SafeLog
{
    @Field(name = "id")
    private long id;
    @Field(name = "dbname")
    private String dbname;

    @Field(name = "tbname")
    private String tbname;

    @Field(name = "overflow")
    private Boolean overflow;

    @Field(entity = true)
    private DbTable dbTable;

    public String toString(){
        return "id:"+id+" "+dbname+"_"+tbname+" overflow:"+overflow+" db_table:"+dbTable;
    }
}
