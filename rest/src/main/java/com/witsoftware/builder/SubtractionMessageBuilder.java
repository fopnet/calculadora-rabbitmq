package com.witsoftware.builder;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SubtractionMessageBuilder extends AmqpMessageBuilder {

    @Override
    String getOperator() {
        return "-";
    }

}
