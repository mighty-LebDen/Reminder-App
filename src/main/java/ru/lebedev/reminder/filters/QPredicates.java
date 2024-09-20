package ru.lebedev.reminder.filters;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QPredicates {

    private final List<Predicate> predicates = new ArrayList<>();

    public static QPredicates builder() {
        return new QPredicates();
    }

    public <T> QPredicates add(T condition2Check, Function<T, Predicate> function) {
        var condition = Optional.ofNullable(condition2Check);
        condition.ifPresent(cond -> predicates.add(function.apply(cond)));
        return this;
    }

    public Predicate build() {
        return Optional.ofNullable(ExpressionUtils.allOf(predicates)).orElse(Expressions.asBoolean(
                true).isTrue());
    }
}
