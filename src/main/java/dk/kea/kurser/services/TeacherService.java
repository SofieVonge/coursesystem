package dk.kea.kurser.services;

import dk.kea.kurser.models.Course;
import dk.kea.kurser.repositories.CourseRepo;
import org.springframework.stereotype.Service;

@Service
//Metoden laver et nyt kursus
public class TeacherService {

    private CourseRepo courseRepo;
    public void creatCourse (Course course){

        courseRepo.save(course);

    }

    //konstruktør
    public TeacherService(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    //metoden opdater kursus
    public void updateCourse(Course course){

        courseRepo.save(course);
    }

    //den metode sletter kursus
    public void deleteCourse(Long id){

        courseRepo.deleteById(id);
    }

}
