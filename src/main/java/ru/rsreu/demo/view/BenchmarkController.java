package ru.rsreu.demo.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.rsreu.demo.benchmark.Benchmark;
import ru.rsreu.demo.benchmark.TestCase;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BenchmarkController {
    private final Benchmark benchmark;

    @GetMapping("/benchmark")
    public String runBenchmark(Model model) {
        List<TestCase> result = benchmark.start();
        model.addAttribute("testCases", result);
        return "benchmark";
    }
}
