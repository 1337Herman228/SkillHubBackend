package by.bsuir.skillhub.services;

import by.bsuir.skillhub.dto.*;
import by.bsuir.skillhub.entity.*;
import by.bsuir.skillhub.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewsService {

    private final ReviewsRepository reviewsRepository;
    private final UsersRepository usersRepository;
    private final CoursesRepository coursesRepository;

    public CourseRatingInfoDto getCourseRatingInfo(Courses course) {
        List<Reviews> courseReviews = reviewsRepository.findByCourse(course);
        float rating = countAverageCourseRating(courseReviews);

        CourseRatingInfoDto courseRatingInfoDto = new CourseRatingInfoDto();
        courseRatingInfoDto.setRating(rating);
        courseRatingInfoDto.setCourseId(course.getCourseId());
        int allReviewsCount = reviewsRepository.findByCourse(course).size();
        int star5ReviewsCount = reviewsRepository.findByRatingAndCourse(5, course).size();
        int star4ReviewsCount = reviewsRepository.findByRatingAndCourse(4, course).size();
        int star3ReviewsCount = reviewsRepository.findByRatingAndCourse(3, course).size();
        int star2ReviewsCount = reviewsRepository.findByRatingAndCourse(2, course).size();
        int star1ReviewsCount = reviewsRepository.findByRatingAndCourse(1, course).size();
        float star5Percentage = 100 * (float) star5ReviewsCount / allReviewsCount;
        float star4Percentage = 100 * (float) star4ReviewsCount / allReviewsCount;
        float star3Percentage = 100 * (float) star3ReviewsCount / allReviewsCount;
        float star2Percentage = 100 * (float) star2ReviewsCount / allReviewsCount;
        float star1Percentage = 100 * (float) star1ReviewsCount / allReviewsCount;
        courseRatingInfoDto.setStar1Percentage(star1Percentage);
        courseRatingInfoDto.setStar2Percentage(star2Percentage);
        courseRatingInfoDto.setStar3Percentage(star3Percentage);
        courseRatingInfoDto.setStar4Percentage(star4Percentage);
        courseRatingInfoDto.setStar5Percentage(star5Percentage);
        return courseRatingInfoDto;
    }

    public List<ReviewDto> getCourseReviews (Courses course){
        List<Reviews> reviews = reviewsRepository.findByCourse(course);
        List<ReviewDto> reviewDtos = new ArrayList<>();
        for (Reviews review : reviews) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewId(review.getReviewId());
            reviewDto.setText(review.getReviewText());
            reviewDto.setCreatedAt(review.getCreatedAt());
            reviewDto.setCourseId(review.getCourse().getCourseId());
            reviewDto.setRating(review.getRating());

            Users user = review.getUser();
            UserDto userDto = new UserDto();
            userDto.setUserId(user.getUserId());
            userDto.setLogin(user.getLogin());
            userDto.setDiamonds(user.getDiamonds());
            userDto.setRole(user.getRole());
            userDto.setPerson(user.getPerson());

            reviewDto.setUser(userDto);
            reviewDtos.add(reviewDto);
        }
        return reviewDtos;
    }

    public float countAverageCourseRating(List<Reviews> courseReviews) {
        Integer sumRating = 0;
        for (Reviews review : courseReviews) {
            sumRating += review.getRating();
        }

        //Вычисляем средний рейтинг
        float rating = (float) sumRating / courseReviews.size();
        if (Float.isNaN(rating)) rating = 0f;

        return rating;
    }

    public ReviewDto getReviewByCourseAndUser(Courses course, Users user) {
        try{
            Reviews reviews = reviewsRepository.findByCourseAndUser(course,user).get();
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewId(reviews.getReviewId());
            reviewDto.setText(reviews.getReviewText());
            reviewDto.setCreatedAt(reviews.getCreatedAt());
            reviewDto.setCourseId(course.getCourseId());
            reviewDto.setRating(reviews.getRating());
            UserDto userDto = new UserDto();
            userDto.setUserId(user.getUserId());
            userDto.setLogin(user.getLogin());
            userDto.setDiamonds(user.getDiamonds());
            userDto.setRole(user.getRole());
            userDto.setPerson(user.getPerson());
            reviewDto.setUser(userDto);
            return reviewDto;
        }catch (Exception e){
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewId(null);
            reviewDto.setText(null);
            reviewDto.setCreatedAt(null);
            reviewDto.setCourseId(null);
            reviewDto.setRating(0);
            reviewDto.setUser(null);
            return reviewDto;
        }

    }

    public HttpStatus saveReview(AddReviewDto reviewDto) {
        try{
            Optional<Users> user = usersRepository.findById(reviewDto.getUserId());
            Optional<Courses> course = coursesRepository.findById(reviewDto.getCourseId());
            Optional<Reviews> reviews = reviewsRepository.findByCourseAndUser(course.get(),user.get());

            //Отзыв уже был создан и нужно его изменить
            if(reviews.isPresent()){
                Reviews review = reviews.get();
                review.setReviewText(reviewDto.getText());
                review.setRating(reviewDto.getRating());
                reviewsRepository.save(review);
                return HttpStatus.OK;
            }
            //Создаем новый отзыв
            else{
                Reviews review = new Reviews();
                review.setReviewText(reviewDto.getText());
                review.setCreatedAt(reviewDto.getCreatedAt());
                review.setRating(reviewDto.getRating());
                review.setCourse(course.get());
                review.setUser(user.get());
                reviewsRepository.save(review);
                return HttpStatus.OK;
            }

        }catch (Exception e){
            return HttpStatus.BAD_REQUEST;
        }
    }
}
