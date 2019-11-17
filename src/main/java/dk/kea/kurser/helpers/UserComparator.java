package dk.kea.kurser.helpers;

import dk.kea.kurser.models.User;

import java.util.Comparator;

public class UserComparator {

    static public Comparator<User> nameComparator = new Comparator<User>() {
        @Override
        public int compare(User u1, User u2) {
            return u1.getFirstName().compareTo(u2.getFirstName());
        }
    };

    static public Comparator<User> nameComparator() {
        return new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getFirstName().compareTo(u2.getFirstName());
            }
        };
    }
}