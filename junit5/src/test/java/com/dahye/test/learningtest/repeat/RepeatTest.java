package com.dahye.test.learningtest.repeat;

import com.dahye.test.domain.Study;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("테스트 반복하기")
public class RepeatTest {

    @DisplayName("10번 반복")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println("test " + repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("변수 다르게 테스트 반복")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @ValueSource(strings = {"날씨가", "너무", "좋아요"})
    void parameterizedTest(String message) {
        System.out.println(message);
    }

    @DisplayName("@EmptySource 알아보기 : empty 문자열 테스트 추가")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @ValueSource(strings = {"날씨가", "너무", "좋아요"})
    @EmptySource
    void emptySourceTest(String message) {
        System.out.println(message);
    }

    @DisplayName("@NullSource 알아보기 : null 문자열 테스트 추가")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @ValueSource(strings = {"날씨가", "너무", "좋아요"})
    @NullSource
    void nullSourceTest(String message) {
        System.out.println(message);
    }

    @DisplayName("@NullAndEmptySource 알아보기 : null, empty 문자열 테스트 추가")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @ValueSource(strings = {"날씨가", "너무", "좋아요"})
    @NullAndEmptySource
    void nullAndEmptySourceTest(String message) {
        System.out.println(message);
    }

    @DisplayName("converter 설정 테스트")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @ValueSource(ints = {10, 20, 30})
    void converterTest(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimitCount());
    }

    private static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "can only convert to study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @DisplayName("인자 두개 넘기기")
    @ParameterizedTest(name = "{index} {displayName} message = {0}, {1}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void converterTest(Integer limit, String name) {
        System.out.println(new Study(limit, name).toString());
    }

    @DisplayName("인자 두개 넘기기 : aggregator 적용")
    @ParameterizedTest(name = "{index} {displayName} message = {0}, {1}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void aggregatorTest(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study.toString());
    }

    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
    }

}
