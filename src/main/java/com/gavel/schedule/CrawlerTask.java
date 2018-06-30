package com.gavel.schedule;

import com.gavel.monitor.persistence.entity.EnvInfo;
import com.gavel.monitor.persistence.repository.EnvinfoJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 陕西环保在线监控管理平台抓取监控点数据
 */
@Component
public class CrawlerTask {

    private Logger LOG = LoggerFactory.getLogger(CrawlerTask.class);

    @Autowired
    private EnvinfoJdbcRepository repository;

    /**
     * 支持cron语法：
     * 每个参数的意义分别是： second, minute, hour, day of month, month, day of week
     *
     */
    @Scheduled(cron="0 3/5 * * * ?")
    public void cron() {
        LOG.info("cron-------------");

        try {
            EnvinfoWeb.login();

            // 废气监控
            List<EnvInfo>  yqEnvInfos = EnvinfoWeb.loadYQData();
            LOG.info("废气监控: " + yqEnvInfos.size());
            if ( !CollectionUtils.isEmpty(yqEnvInfos) ){
                for (EnvInfo bean : yqEnvInfos) {
                    EnvInfo exist = repository.findById(bean.getId());
                    if ( exist==null ){
                        repository.insert(bean);
                    } else {
                        LOG.info("重复数据： " + bean);
                    }
                }
            }

            // 污水监控
            List<EnvInfo>  wsEnvInfos =  EnvinfoWeb.loadWSData();
            LOG.info("污水监控: " + wsEnvInfos.size());
            if ( !CollectionUtils.isEmpty(wsEnvInfos) ){
                for (EnvInfo bean : wsEnvInfos) {
                    EnvInfo exist = repository.findById(bean.getId());
                    if ( exist==null ){
                        repository.insert(bean);
                    } else {
                        LOG.info("重复数据： " + bean);
                    }
                }
            }

            // 污水处理厂监控
            List<EnvInfo>  fcEnvInfos =  EnvinfoWeb.loadFCData();
            LOG.info("污水处理厂监控: " + fcEnvInfos.size());
            if ( !CollectionUtils.isEmpty(fcEnvInfos) ){
                for (EnvInfo bean : fcEnvInfos) {
                    EnvInfo exist = repository.findById(bean.getId());
                    if ( exist==null ){
                        repository.insert(bean);
                    } else {
                        LOG.info("重复数据： " + bean);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("[Task异常]" + e.getMessage());
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
