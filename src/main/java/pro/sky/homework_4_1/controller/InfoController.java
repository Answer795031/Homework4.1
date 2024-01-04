package pro.sky.homework_4_1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/info")
public class InfoController {

    private final Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Value("${server.port}")
    private String port;

    @GetMapping(value = "/port")
    public String getPort() {
        return port;
    }

    @GetMapping("/sum")
    public void getSum() {
        long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a +1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long finish = System.currentTimeMillis();

        logger.info("result: {}, time: {}", sum, finish - start);
        // фактическое время получилось макс - 30 мс, мин - 10 мс
    }

    @GetMapping("/sum-parallel")
    public void getSumParallel() {
        long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a +1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long finish = System.currentTimeMillis();

        logger.info("parallel result: {}, time: {}", sum, finish - start);
        // фактическое время получилось макс - 31 мс, мин - 9 мс
        // сомнительно, но окэй
    }
}
