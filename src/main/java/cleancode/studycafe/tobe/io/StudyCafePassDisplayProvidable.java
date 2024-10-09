package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

public interface StudyCafePassDisplayProvidable {

    boolean supports(StudyCafePassType passType);

    String provide(StudyCafePass studyCafePass);

}
