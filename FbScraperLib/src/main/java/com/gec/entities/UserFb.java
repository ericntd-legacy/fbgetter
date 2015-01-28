package com.gec.entities;

import com.restfb.Facebook;
import com.restfb.types.User;

import java.util.List;

/**
 * Created by eric on 27/1/15.
 */
public class UserFb extends User {
    @Facebook("age_range")
    private AgeRange ageRange;

    public AgeRange getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(AgeRange ageRange) {
        this.ageRange = ageRange;
    }

    public static class AgeRange {
        @Facebook
        private Integer min;
        @Facebook
        private Integer max;

        public Integer getMin() {

            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }
    }
}
