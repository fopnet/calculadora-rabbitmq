package com.witsoftware.builder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AdditionMessageBuilder extends AmqpMessageBuilder {

    @Override
    String getOperator() {
        return "+";
    }

}
