package com.ylan.quartzdemo3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ylan.quartzdemo3.model.entity.TaskEntity;
import com.ylan.quartzdemo3.model.vo.TaskRequestVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author by pepsi-wyl
 * @date 2023-11-08 13:58
 * @description 任务Mapper
 */

@Repository("userMapper")
public interface TaskMapper extends BaseMapper<TaskEntity> {

    // 查询所有任务
    List<TaskEntity> selectAll();

    // 按照主键-任务ID查询任务
    TaskEntity selectByPrimaryKey(Integer id);

    // 根据任务名称查询任务
    TaskEntity selectByJobName(String jobName);

    // 多条件查询
    List<TaskEntity> selectTaskInfos(TaskRequestVO taskRequestVO);

    // 按照主键-任务ID删除任务
    int deleteByPrimaryKey(Integer id);

    // 插入任务 忽略NULL值
    int insertSelective(TaskEntity task);

    // 修改任务 忽略NULL值
    int updateByPrimaryKeySelective(TaskEntity task);

    // 插入任务 不忽略NULL值
    @Deprecated
    int insert(TaskEntity task);

    // 修改任务 不i忽略NULL值
    @Deprecated
    int updateByPrimaryKey(TaskEntity task);
}