
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;


/**
 * @author Tarry
 * @create 2019/9/29 10:18
 */
public class TestparseGold {
    public static void main(String[] args) {
        String[][] course ={{"数学","语文","英语"},{"数学","语文","英语","物理","化学"},{"数学","语文","英语","物理","化学"},{"数学","语文","英语","物理","化学"},{"数学","语文","英语","物理","化学"},{"数学","语文","英语","物理","化学"},{"数学","语文","英语","物理","化学"}};
        String[] grade = {"小学","初一","初二","初三","高一","高二","高三"};
        String shouke="[[1,1],[1,2],[1,3],[2,0],[2,1],[2,2],[2,3],[2,4],[4,1],[4,2],[4,3]]";

        if(StringUtils.isBlank(shouke)){
            return;//字段为null，返回
        }
        JSONArray jsonArray = JSONArray.fromObject(shouke);
        int[][] courseXy =new int[jsonArray.size()][2];
        for (int i=0;i< jsonArray.size();i++){
            for (int j=0;j<jsonArray.getJSONArray(i).size();j++){
                courseXy[i][j]=jsonArray.getJSONArray(i).getInt(j);
            }
        }

        LinkedHashMap<String, ArrayList<String>> courseMap = new LinkedHashMap<>();

        //遍历该老师教的年级
        for (int[] arrs:courseXy){

            if (CollectionUtils.isEmpty(courseMap.get(grade[arrs[0]]))){
                ArrayList<String> list = new ArrayList<>();
                list.add(course[arrs[0]][arrs[1]]);
                courseMap.put(grade[arrs[0]],list);
            }else{
                ArrayList<String> temp = courseMap.get(grade[arrs[0]]);
                temp.add(course[arrs[0]][arrs[1]]);
                courseMap.put(grade[arrs[0]],temp);
            }
        }
        //{初一=[语文, 英语, 物理], 初二=[数学, 语文, 英语, 物理, 化学], 高一=[语文, 英语, 物理]}
        return ;
    }
}
