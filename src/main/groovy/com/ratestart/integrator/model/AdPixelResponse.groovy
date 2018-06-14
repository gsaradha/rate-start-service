package com.ratestart.integrator.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
@EqualsAndHashCode
class AdPixelResponse {

    List<AdPixel> data

    Pagination paging

    Boolean success

    public class Pagination {

        Cursors cursors;

        public class Cursors {

            String before;
            String after;

        }
    }
 }
