package site.minnan.entry.infrastructure.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import site.minnan.entry.application.provider.TemperatureProviderService;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class Scheduler {

    @Autowired
    private TemperatureProviderService temperatureProviderService;

    @Scheduled(cron = "0 0 0 * * *")
    @PostConstruct
    public void temperatureTask(){
        log.info("生成旅客体温记录开始");
        temperatureProviderService.createTemperatureRecord();
        log.info("生成旅客体温记录结束");
    }
}
