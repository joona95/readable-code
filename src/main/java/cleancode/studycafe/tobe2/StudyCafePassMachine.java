package cleancode.studycafe.tobe2;

import cleancode.studycafe.tobe2.io.StudyIOHandler;
import cleancode.studycafe.tobe2.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe2.model.StudyCafePassType;
import cleancode.studycafe.tobe2.exception.AppException;
import cleancode.studycafe.tobe2.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe2.model.StudyCafePass;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyIOHandler ioHandler = new StudyIOHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();

            StudyCafePass selectedPass = selectPass();
            Optional<StudyCafeLockerPass> optionalLockerPass = selectLockerPass(selectedPass);

            optionalLockerPass.ifPresentOrElse(
                    lockerPass -> ioHandler.showPassOrderSummary(selectedPass, lockerPass),
                    () -> ioHandler.showPassOrderSummary(selectedPass)
            );
        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePass selectPass() {
        StudyCafePassType passType = ioHandler.askPassTypeSelecting();
        List<StudyCafePass> passCandidates = findPassCandidatesBy(passType);

        return ioHandler.askPassSelecting(passCandidates);
    }

    private List<StudyCafePass> findPassCandidatesBy(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> allPasses = studyCafeFileHandler.readStudyCafePasses();

        return allPasses.stream()
                .filter(studyCafePass -> studyCafePass.isSamePassType(studyCafePassType))
                .toList();
    }

    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafePass selectedPass) {
        if (selectedPass.cannotUseLocker()) {
            return Optional.empty();
        }

        StudyCafeLockerPass lockerPassCandidate = findLockerPassCandidate(selectedPass);

        if (lockerPassCandidate != null) {
            boolean isLockerSelected = ioHandler.askLockerPass(lockerPassCandidate);
            if (isLockerSelected) {
                return Optional.of(lockerPassCandidate);
            }
        }

        return Optional.empty();
    }

    private StudyCafeLockerPass findLockerPassCandidate(StudyCafePass pass) {
        List<StudyCafeLockerPass> allLockerPasses = studyCafeFileHandler.readLockerPasses();

        return allLockerPasses.stream()
            .filter(pass::isSameDurationType)
            .findFirst()
            .orElse(null);
    }

}
