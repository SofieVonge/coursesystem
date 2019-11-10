package dk.kea.kurser.helpers;

import dk.kea.kurser.models.Course;
import org.springframework.data.jpa.domain.Specification;


public class CourseSpecifications
{
    public static Specification<Course> titleLike(String title) {
        // specification result
        Specification<Course> result = null;

        // danish title specification - title like given title
        Specification<Course> titleDanish = (Specification<Course>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("titleDanish"), "%" + title + "%");

        // english title specification - title like given title
        Specification<Course> titleEnglish = (Specification<Course>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("titleEnglish"), "%" + title + "%");

        // concat specifications
        result = titleDanish.or(titleEnglish);

        // return specification
        return result;
    }

    public static Specification<Course> ectsBetween(int min, int max) {
        Specification<Course> result = null;

        result = (Specification<Course>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.between(root.get("ects"), min, max);

        return result;
    }

    public static Specification<Course> ectsEquals(int ects) {
        Specification<Course> result = null;

        result = (Specification<Course>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ects"), ects);

        return result;
    }

    public static Specification<Course> isElective() {
        Specification<Course> result = null;

        result = (Specification<Course>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.isFalse(root.get("mandatory"));

        return result;
    }

    public static Specification<Course> isMandatory() {
        Specification<Course> result = null;

        result = (Specification<Course>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.isTrue(root.get("mandatory"));

        return result;
    }
}
