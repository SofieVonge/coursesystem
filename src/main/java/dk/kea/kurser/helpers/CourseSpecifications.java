package dk.kea.kurser.helpers;

import dk.kea.kurser.models.Course;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CourseSpecifications
{
    public static Specification<Course> titleLike(String title) {
        Specification<Course> result = null;

        Specification<Course> titleDanish = (Specification<Course>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("titleDanish"), title);

        Specification titleEnglish = (Specification<Course>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("titleEnglish"), title);

        result = titleDanish;
        result = result.or(titleEnglish);

        return result;
    }
}
