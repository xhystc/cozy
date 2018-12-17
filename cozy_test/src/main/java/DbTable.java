import com.alibaba.fastjson.JSONObject;
import com.xhystc.cozy.converter.FastJSONConverter;
import com.xhystc.cozy.core.Entity;
import com.xhystc.cozy.core.Field;

/**
 * @author xiehongyang
 * @date 2018/12/16 4:50 PM
 */
@Entity(prefix = "d")
public class DbTable
{
    @Field(name = "json",converter = FastJSONConverter.class)
    private JSONObject json;

    @Field(name = "tablename")
    private String tbname;

    public String toString(){
        return "table:"+tbname+" json:"+json.toJSONString();
    }
}
