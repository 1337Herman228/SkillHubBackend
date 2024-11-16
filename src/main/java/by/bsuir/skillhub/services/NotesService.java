package by.bsuir.skillhub.services;

import by.bsuir.skillhub.dto.NoteDto;
import by.bsuir.skillhub.entity.Lessons;
import by.bsuir.skillhub.entity.UserNotes;
import by.bsuir.skillhub.entity.Users;
import by.bsuir.skillhub.repo.LessonsRepository;
import by.bsuir.skillhub.repo.UserNotesRepository;
import by.bsuir.skillhub.repo.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotesService {

    private final UserNotesRepository userNotesRepository;
    private final UsersRepository usersRepository;
    private final LessonsRepository lessonsRepository;

    public NoteDto getNoteByLessonAndUser(Lessons lesson, Users user) {
        try{
            UserNotes userNote = userNotesRepository.findByLessonAndUser(lesson,user).get();
            NoteDto noteDto = new NoteDto();
            noteDto.setCreatedAt(userNote.getCreatedAt());
            noteDto.setLessonId(userNote.getLesson().getLessonId());
            noteDto.setText(userNote.getNoteText());
            noteDto.setNoteId(userNote.getNoteId());
            noteDto.setUserId(userNote.getUser().getUserId());
            return noteDto;
        }catch (Exception e){
            NoteDto noteDtoNullable = new NoteDto();
            noteDtoNullable.setCreatedAt(null);
            noteDtoNullable.setLessonId(null);
            noteDtoNullable.setText(null);
            noteDtoNullable.setNoteId(null);
            noteDtoNullable.setUserId(null);
            return noteDtoNullable;
        }

    }

    public HttpStatus saveNote(NoteDto noteDto) {
        try{
            Optional<Users> user = usersRepository.findById(noteDto.getUserId());
            Optional<Lessons> lesson = lessonsRepository.findById(noteDto.getLessonId());
            Optional<UserNotes> userNote = userNotesRepository.findByLessonAndUser(lesson.get(),user.get());

            //Заметка уже была создана и нужно ее изменить
            if(userNote.isPresent()){
                UserNotes userNotes = userNote.get();
                userNotes.setNoteText(noteDto.getText());
                userNotesRepository.save(userNotes);
                return HttpStatus.OK;
            }
            //Создаем новую заметку
            else{
                UserNotes userNotes = new UserNotes();
                userNotes.setNoteText(noteDto.getText());
                userNotes.setUser(user.get());
                userNotes.setCreatedAt(noteDto.getCreatedAt());
                userNotes.setLesson(lesson.get());
                userNotesRepository.save(userNotes);
                return HttpStatus.OK;
            }

        }catch (Exception e){
            return HttpStatus.BAD_REQUEST;
        }
    }
}
