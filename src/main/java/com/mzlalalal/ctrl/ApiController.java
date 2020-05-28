package com.mzlalalal.ctrl;

import cn.hutool.core.collection.CollUtil;
import com.mzlalalal.model.Element;
import com.mzlalalal.model.Ret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Mzlalalal
 */
@Slf4j
@RequestMapping("/api")
@RestController
public class ApiController {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/paramCheck")
    public Ret paramCheck() {

        return Ret.ok();
    }

    @RequestMapping("/redisQueue/{value}")
    public Ret redisQueue(@PathVariable("value") String value) {
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();

        Element element = new Element();
        element.setElement(value);

        listOperations.leftPush("test", element);

        Long size = listOperations.size("test");
        if (size >= 5) {
            redisQueue();
        }

        return Ret.ok();
    }

    public synchronized void redisQueue() {
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        Long size = listOperations.size("test");
        List<Element> objectList = CollUtil.newArrayList();
        if (size < 5) {
            return;
        }
        while (size > 0) {
            Element element = (Element) listOperations.leftPop("test");
            objectList.add(element);
            size--;
        }
        System.out.println(CollUtil.join(objectList, ","));
        return;
    }
}
