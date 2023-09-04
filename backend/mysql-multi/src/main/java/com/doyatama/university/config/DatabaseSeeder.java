package com.doyatama.university.config;

import com.doyatama.university.model.Answer;
import com.doyatama.university.model.DetailRps;
import com.doyatama.university.model.Question;
import com.doyatama.university.model.Rps;
import com.doyatama.university.repository.DetailRPSRepository;
import com.doyatama.university.repository.QuestionRepository;
import com.doyatama.university.repository.RPSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

@Component
public class DatabaseSeeder {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private RPSRepository rpsRepository;
    @Autowired
    private DetailRPSRepository detailRPSRepository;

    public void seedRpsTable() {
        // Contoh pengisian data dummy
        Rps rps = new Rps();
        rps.setId((long) 1);
        rpsRepository.save(rps);
    }
    public void seedRpsDetailTable() {
        // Contoh pengisian data dummy
        DetailRps detailRps = new DetailRps();
        detailRps.setId((long) 1);
        detailRps.setRps(new Rps((long) 1));
        detailRPSRepository.save(detailRps);
    }
    public void seedQuestionsTable() {
        // Contoh pengisian data dummy
        for (int i = 0; i <= 5000000; i++) {
            Faker faker = new Faker();
            String[] typeQuestion = {"VIDEO", "AUDIO", "IMAGE", "NORMAL"};
            String[] typeAnswer = {"MULTIPLE_CHOICE",
                    "BOOLEAN",
                    "COMPLETION",
                    "MATCHING",
                    "ESSAY"};
            Question question = new Question();
            question.setId((long) i);
            question.setName(faker.lorem().sentence());
            question.setDescription(faker.lorem().sentence());
            question.setAnswerType(typeAnswer[faker.random().nextInt(typeAnswer.length)]);
            question.setQuestionType(typeQuestion[faker.random().nextInt(typeQuestion.length)]);
            question.setFilePath("folder/name.jpg");
            question.setRps_detail(new DetailRps((long)1));
            questionRepository.save(question);
        }
    }
    @EventListener
    public void seed(ContextRefreshedEvent event) {
//        seedRpsTable();
//        seedRpsDetailTable();
//        seedQuestionsTable();
    }
}
