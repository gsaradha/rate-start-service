package com.ratestart.integrator.model

import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
class Category {

    String categoryName
    Long categoryId
    String categoryImage
    String icon

    List<Tip> tips = []

    class Tip {
        Long id
        String text
    }

}
