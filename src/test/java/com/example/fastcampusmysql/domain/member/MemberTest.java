package com.example.fastcampusmysql.domain.member;

import com.example.fastcampusmysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class MemberTest {


    @DisplayName("Member 닉네임 변경 테스트")
    @Test
    public void testNickname() {

        LongStream.range(0, 10)
                .mapToObj(MemberFixtureFactory::createMember)
                .forEach(member -> {
                    System.out.println(member.getNickname());
                });
    }
}

