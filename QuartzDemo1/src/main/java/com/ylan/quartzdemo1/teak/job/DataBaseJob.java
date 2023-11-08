package com.ylan.quartzdemo1.teak.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ylan.quartzdemo1.entity.User;
import com.ylan.quartzdemo1.mapper.UserMapper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author by pepsi-wyl
 * @date 2023-11-06 14:47
 */

@Component
public class DataBaseJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(DataBaseJob.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    // 注入Mapper
    @Resource  // @Autowired
    private UserMapper userMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("[DataBaseJob StartTime] " + dateFormat.format(new Date()));

        // 通过工具类获取Mapper对象
//        UserMapper userMapper = SpringContextJobUtil.getBean("userMapper", UserMapper.class);

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        List<User> users = userMapper.selectList(wrapper);
        users.forEach((u)->{
            log.info("[User] " + u.toString());
        });

        log.info("[DataBaseJob EndTime] " + dateFormat.format(new Date()));
    }
}