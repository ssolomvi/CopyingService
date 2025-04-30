package ru.mai.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.model.print.PrintablePaper;
import ru.mai.model.print.PrintablePhoto;
import ru.mai.service.PrintService;

@RestController
@RequestMapping("/print")
@Tag(name = "Printer controller", description = "Controller for processing printer requests")
public class PrintController {

    private final static Logger log = LoggerFactory.getLogger(PrintController.class);

    @Autowired
    private PrintService printService;

    @PostMapping("/photo")
    public void printPhoto(@ParameterObject PrintablePhoto request) {
        log.info("Got request for photo printing: {}", request);
        printService.print(request);
    }

    @PostMapping("/paper")
    public void printPaper(@ParameterObject PrintablePaper request) {
        log.info("Got request for paper printing: {}", request);
        printService.print(request);
    }

}
