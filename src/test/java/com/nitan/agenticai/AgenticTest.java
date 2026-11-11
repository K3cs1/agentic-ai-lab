package com.nitan.agenticai;

import com.nitan.agenticai.assistant.Assistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AgenticTest {

    @Autowired
    private Assistant assistant;

    @Test
    void test() {
        System.err.println(assistant.answer("What are the last 3 commits in this git repository?"));
    }
}