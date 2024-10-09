package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

public interface StudyCafeLockerPassDisplayProvidable {

    boolean supports(StudyCafePassType passType);

    String provide(StudyCafeLockerPass studyCafeLockerPass);
}
