package by.bsuir.skillhub.services;

import by.bsuir.skillhub.dto.*;
import by.bsuir.skillhub.entity.Answers;
import by.bsuir.skillhub.entity.Lessons;
import by.bsuir.skillhub.entity.Questions;
import by.bsuir.skillhub.entity.Users;
import by.bsuir.skillhub.repo.AnswersRepository;
import by.bsuir.skillhub.repo.LessonsRepository;
import by.bsuir.skillhub.repo.QuestionsRepository;
import by.bsuir.skillhub.repo.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionsService {

    private final QuestionsRepository questionsRepository;
    private final AnswersRepository answersRepository;
    private final UsersRepository usersRepository;
    private final LessonsRepository lessonsRepository;

    public List<QuestionDto> getQuestionsForLesson(Lessons lesson) {
        List<Questions> LessonQuestions = questionsRepository.findByLesson(lesson);
        List<QuestionDto> questions = new ArrayList<>();
        for (Questions question : LessonQuestions) {
            QuestionDto questionDto = new QuestionDto();
            questionDto.setCreatedAt(question.getCreatedAt());
            questionDto.setUpdatedAt(question.getUpdatedAt());
            questionDto.setUser(mapUserDtoFromUser(question.getUser()));
            questionDto.setBody(question.getBody());
            questionDto.setQuestionId(question.getQuestionId());

            List<AnswerDto> answers = new ArrayList<>();
            List<Answers> answersList = answersRepository.findByQuestion(question);
            for(Answers answer : answersList){
                AnswerDto answerDto = new AnswerDto();
                answerDto.setCreatedAt(answer.getCreatedAt());
                answerDto.setUpdatedAt(answer.getUpdatedAt());
                answerDto.setUser(mapUserDtoFromUser(answer.getUser()));
                answerDto.setBody(answer.getBody());
                answerDto.setAnswerId(answer.getAnswerId());
                answerDto.setQuestionId(question.getQuestionId());
                answers.add(answerDto);
            }
            questionDto.setAnswers(answers);
            questions.add(questionDto);
        }
        return questions;
    }

    public HttpStatus addAnswer(AddAnswerDto answer){
        try{
            Questions question = questionsRepository.findById(answer.getQuestionId()).orElse(null);
            Answers answers = new Answers();
            answers.setCreatedAt(answer.getCreatedAt());
            answers.setUpdatedAt(answer.getUpdatedAt());
            answers.setBody(answer.getBody());
            answers.setUser(usersRepository.findById(answer.getUserId()).orElse(null));
            answers.setQuestion(question);
            answersRepository.save(answers);
            return HttpStatus.CREATED;
        }catch (Exception e){
            return HttpStatus.BAD_REQUEST;
        }
    }

    public HttpStatus addQuestion(AddQuestionDto question){
        try{
            Questions newQuestion = new Questions();
            newQuestion.setCreatedAt(question.getCreatedAt());
            newQuestion.setUpdatedAt(question.getUpdatedAt());
            newQuestion.setBody(question.getBody());
            newQuestion.setUser(usersRepository.findById(question.getUserId()).orElse(null));
            newQuestion.setLesson(lessonsRepository.findById(question.getLessonId()).orElse(null));
            newQuestion.setTitle("");
            questionsRepository.save(newQuestion);
            return HttpStatus.CREATED;
        }catch (Exception e){
            return HttpStatus.BAD_REQUEST;
        }
    }

    private UserDto mapUserDtoFromUser (Users user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setLogin(user.getLogin());
        userDto.setDiamonds(user.getDiamonds());
        userDto.setRole(user.getRole());
        userDto.setPerson(user.getPerson());
        userDto.setAvatarStroke(user.getAvatarStroke());
        userDto.setDignity(user.getDignity());
        userDto.setNicknameColor(user.getNicknameColor());
        return userDto;
    }
}
