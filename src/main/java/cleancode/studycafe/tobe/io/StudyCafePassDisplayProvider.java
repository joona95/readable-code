package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.Arrays;

public enum StudyCafePassDisplayProvider implements StudyCafePassDisplayProvidable {

    HOURLY(StudyCafePassType.HOURLY) {
        @Override
        public String provide(StudyCafePass studyCafePass) {
            return String.format("%s시간권 - %d원", studyCafePass.getDuration(), studyCafePass.getPrice());
        }
    },
    WEEKLY(StudyCafePassType.WEEKLY) {
        @Override
        public String provide(StudyCafePass studyCafePass) {
            return String.format("%s주권 - %d원", studyCafePass.getDuration(), studyCafePass.getPrice());
        }
    },
    FIXED(StudyCafePassType.FIXED) {
        @Override
        public String provide(StudyCafePass studyCafePass) {
            return String.format("%s주권 - %d원", studyCafePass.getDuration(), studyCafePass.getPrice());
        }
    };

    private final StudyCafePassType passType;

    StudyCafePassDisplayProvider(StudyCafePassType passType) {
        this.passType = passType;
    }

    @Override
    public boolean supports(StudyCafePassType passType) {
        return this.passType == passType;
    }

    public static String findStudyCafePassDisplayFrom(StudyCafePass studyCafePass) {
        return Arrays.stream(values())
                .filter(provider -> provider.supports(studyCafePass.getPassType()))
                .findFirst()
                .map(provider -> provider.provide(studyCafePass))
                .orElseThrow(() -> new IllegalArgumentException("확인할 수 없는 이용권입니다."));
    }
}
