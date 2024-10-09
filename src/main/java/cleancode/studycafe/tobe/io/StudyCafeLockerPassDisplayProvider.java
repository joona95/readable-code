package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.Arrays;

public enum StudyCafeLockerPassDisplayProvider implements StudyCafeLockerPassDisplayProvidable {

    HOURLY(StudyCafePassType.HOURLY) {
        @Override
        public String provide(StudyCafeLockerPass studyCafeLockerPass) {
            return String.format("%s시간권 - %d원", studyCafeLockerPass.getDuration(), studyCafeLockerPass.getPrice());
        }
    },
    WEEKLY(StudyCafePassType.WEEKLY) {
        @Override
        public String provide(StudyCafeLockerPass studyCafeLockerPass) {
            return String.format("%s주권 - %d원", studyCafeLockerPass.getDuration(), studyCafeLockerPass.getPrice());
        }
    },
    FIXED(StudyCafePassType.FIXED) {
        @Override
        public String provide(StudyCafeLockerPass studyCafeLockerPass) {
            return String.format("%s주권 - %d원", studyCafeLockerPass.getDuration(), studyCafeLockerPass.getPrice());
        }
    };

    private final StudyCafePassType passType;

    StudyCafeLockerPassDisplayProvider(StudyCafePassType passType) {
        this.passType = passType;
    }

    @Override
    public boolean supports(StudyCafePassType passType) {
        return this.passType == passType;
    }

    public static String findStudyCafeLockerPassDisplayFrom(StudyCafeLockerPass lockerPass) {
        return Arrays.stream(values())
                .filter(provider -> provider.supports(lockerPass.getPassType()))
                .findFirst()
                .map(provider -> provider.provide(lockerPass))
                .orElseThrow(() -> new IllegalArgumentException("확인할 수 없는 이용권입니다."));
    }
}
