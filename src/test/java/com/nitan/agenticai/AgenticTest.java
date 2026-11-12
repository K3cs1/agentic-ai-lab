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
    void ticketQuestion() {
        System.out.println(assistant.answer("What is the content of ticket TCK-1?"));
    }

    @Test
    void vacationQuestion() {
        System.out.println(assistant.answer("How many vacation days do I have, and how long can I carry them over?"));
    }
}