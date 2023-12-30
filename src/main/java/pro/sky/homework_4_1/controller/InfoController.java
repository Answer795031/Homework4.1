package pro.sky.homework_4_1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/info")
public class InfoController {

    @Value("${server.port}")
    private String port;

    @GetMapping(value = "/port")
    public String getPort() {
        return port;
    }
}
