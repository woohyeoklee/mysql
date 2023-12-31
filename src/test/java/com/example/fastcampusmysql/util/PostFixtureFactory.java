package com.example.fastcampusmysql.util;

import com.example.fastcampusmysql.domain.post.entity.Post;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.time.LocalDate;

import static org.jeasy.random.FieldPredicates.*;

public class PostFixtureFactory {

    public static EasyRandom get(Long memberId, LocalDate firstDate, LocalDate lastDate) {
        var idPrecicate = named("id")
                .and(ofType(Long.class))
                .and(inClass(Post.class));

        var memberIdPrecicate = named("memberId")
                .and(ofType(Long.class))
                .and(inClass(Post.class));

        var param = new EasyRandomParameters()
                .excludeField(idPrecicate)
                .dateRange(firstDate, lastDate)
                .randomize(memberIdPrecicate, () -> memberId);
        return new EasyRandom(param);

    }
}
