package ru.project.graduation.zharinov.votingforrestaurant.util.validation;

import lombok.experimental.UtilityClass;
import ru.project.graduation.zharinov.votingforrestaurant.HasId;
import ru.project.graduation.zharinov.votingforrestaurant.error.IllegalRequestDataException;

import java.time.LocalTime;

import static ru.project.graduation.zharinov.votingforrestaurant.util.DateTimeUtil.VOTE_TIME;

@UtilityClass
public class ValidationUtil {

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void checkModification(int count, int id) {
        if (count == 0) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
    }

    public static <T> T checkExisted(T obj, int id) {
        if (obj == null) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
        return obj;
    }

    public static void checkTime(LocalTime localTime) {
        if (!localTime.isBefore(VOTE_TIME)) {
            throw new IllegalRequestDataException("It's too late to change the vote today. PLease try tomorrow");
        }
    }
}