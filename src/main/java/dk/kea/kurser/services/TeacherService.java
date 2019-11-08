package dk.kea.kurser.services;

import dk.kea.kurser.models.Course;
import dk.kea.kurser.models.User;
import dk.kea.kurser.repositories.CourseRepo;
import dk.kea.kurser.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//Metoden laver et nyt kursus
public class TeacherService {

    private CourseRepo courseRepo;
    private UserRepository userRepository;

    public void creatCourse (Course course){

        courseRepo.save(course);

    }

    //konstruktør
    public TeacherService(CourseRepo courseRepo, UserRepository userRepository) {
        this.courseRepo = courseRepo;
        this.userRepository = userRepository;
    }

    //metoden opdater kursus
    public void updateCourse(Course course){

        courseRepo.save(course);
    }

    //den metode sletter kursus
    public void deleteCourse(Long id){

        courseRepo.deleteById(id);
    }

    public Course findById (Long id)
    {
        //findById giver en optional
        Optional<Course> courseOptional=courseRepo.findById(id);
        if (!courseOptional.isPresent())
        {
            throw new RuntimeException("Course not found");
        }
        //returner course vha. .get()
        return courseOptional.get();
    }

    //opretter en lærer
    public void creatTeacher (User user){

        userRepository.save(user);
    }

    public void updateTeacher(User user){

        userRepository.save(user);
    }

    public void deleteTeacher(Long id){

        userRepository.deleteById(id);
    }

    public User findUserById (Long id)
    {
        //findById giver en optional
        Optional<User> userOptional=userRepository.findById(id);

        if (!userOptional.isPresent())
        {
            throw new RuntimeException("User not found");
        }
        //returner user vha. .get()
        return userOptional.get();
    }



}
