package com.nopecho.policy.domain;

import com.nopecho.utils.Throw;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SpecActual {

    @OneToOne(cascade = CascadeType.ALL)
    private SpecActualDetail actualDetail;

    public static SpecActual of(SpecActualDetail detail) {
        throwIfInvalidArgs(detail);
        return new SpecActual(detail);
    }

    private static void throwIfInvalidArgs(SpecActualDetail detail) {
        Throw.ifNull(detail, "Condition Spec Actual Detail");
    }
}
