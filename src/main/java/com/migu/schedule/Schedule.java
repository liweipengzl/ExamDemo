package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.TaskInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*类名和方法不能修改
 */
public class Schedule {
    //服务节点
   private Map<Integer,Integer> map1 = new HashMap<Integer, Integer>();
    //任务节点
    private Map<Integer,Integer> map2 = new HashMap<Integer,Integer>();


    public int init() {
        
        map1.clear();
        map2.clear();
        return ReturnCodeKeys.E001;
    }

//2.2服务节点注册
//    注册成功，返回E003:服务节点注册成功。
//    如果服务节点编号小于等于0, 返回E004:服务节点编号非法。
//    如果服务节点编号已注册, 返回E005:服务节点已注册。
    public int registerNode(int nodeId)
    {
        if (nodeId <= 0)
        {
            return ReturnCodeKeys.E004;
        }
        else if (map1.get(nodeId) != null)
        {
            return ReturnCodeKeys.E005;
        }
        map1.put(nodeId, nodeId);
        return ReturnCodeKeys.E003;
    }
    
    
//1、从系统中删除服务节点；
    public int unregisterNode(int nodeId) {
        
        //如果服务节点编号小于等于0, 返回E004:服务节点编号非法。
        if(nodeId<=0){
            return ReturnCodeKeys.E004;
        }
        //如果服务节点编号未被注册, 返回E007:服务节点不存在。
        if(!map1.containsKey(nodeId)){
            return ReturnCodeKeys.E007;
        }
        //注销成功，返回E006:服务节点注销成功。
        map1.remove(nodeId);
        
        return ReturnCodeKeys.E006;
        
    }


    public int addTask(int taskId, int consumption) {
        //如果任务编号小于等于0, 返回E009:任务编号非法。
        if(taskId<=0){
            return ReturnCodeKeys.E009;
        }
        //如果相同任务编号任务已经被添加, 返回E010:任务已添加。
        if(map2.containsKey(taskId)){
            return ReturnCodeKeys.E010;
        }
        //添加成功，返回E008任务添加成功。
        map2.put(taskId, consumption);
        return ReturnCodeKeys.E008;
    }


    public int deleteTask(int taskId) {
        
        //删除成功，返回E011:任务删除成功。
       // 如果任务编号小于等于0, 返回E009:任务编号非法。
       // 如果指定编号的任务未被添加, 返回E012:任务不存在。
    if(taskId<=0){
        return ReturnCodeKeys.E009;
    }
    if(!map2.containsKey(taskId)){
        return ReturnCodeKeys.E012;
    }
    map2.remove(taskId);
    return ReturnCodeKeys.E011;
    }

//    如果调度阈值取值错误，返回E002调度阈值非法。
//    如果获得最佳迁移方案, 进行了任务的迁移,返回E013: 任务调度成功;
//    如果所有迁移方案中，总会有任意两台服务器的总消耗率差值大于阈值。则认为没有合适的迁移方案,返回 E014:无合适迁移方案;
    public int scheduleTask(int threshold)
    {
        
        if(threshold<0){
            return ReturnCodeKeys.E002;
        }
        List list = new ArrayList();
        for (int key : map2.keySet())
        {
            
            list.add(map2.get(key));
        }
        int max = getMaxDouble(list);
        int min = getMinDouble(list);
        if ((max - min) > threshold)
        {
            return ReturnCodeKeys.E014;
        }else{
            return ReturnCodeKeys.E013;
        }
        
    }

    //获取集合中最大数
    public static int getMaxDouble(List list){

        int max = Collections.max(list);
       // double min = Collections.min(list);
         return max;
    }
  //获取集合中最小数
    public static int getMinDouble(List list){

        //double max = Collections.max(list);
        int min = Collections.min(list);
         return min;
    }

    
//    未做此题返回 E000方法未实现。
//    如果查询结果参数tasks为null，返回E016:参数列表非法
//     如果查询成功, 返回E015: 查询任务状态成功;查询结果从参数Tasks返回。
    public int queryTaskStatus(List<TaskInfo> tasks) {
        if(tasks==null){
            return ReturnCodeKeys.E016;
        }else{
            return ReturnCodeKeys.E015;
        }
//        for(TaskInfo x: tasks){
//            x.getNodeId()
//        }
        
        
        
        
    }

}
