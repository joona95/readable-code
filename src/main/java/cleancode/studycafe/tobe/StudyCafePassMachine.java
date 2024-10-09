package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafeLockerPasses;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;
import cleancode.studycafe.tobe.model.StudyCafePasses;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();
            List<StudyCafePass> studyCafePasses = getStudyCafePassesBy(studyCafePassType);
            outputHandler.showPassListForSelection(studyCafePasses);
            StudyCafePass selectedPass = inputHandler.getSelectPass(studyCafePasses);

            if (studyCafePassType == StudyCafePassType.FIXED) {

                StudyCafeLockerPasses lockerPasses = studyCafeFileHandler.readLockerPasses();
                Optional<StudyCafeLockerPass> lockerPass = lockerPasses.findBy(selectedPass);

                boolean lockerSelection = false;
                if (lockerPass.isPresent()) {
                    outputHandler.askLockerPass(lockerPass.get());
                    lockerSelection = inputHandler.getLockerSelection();
                }

                if (lockerSelection) {
                    outputHandler.showPassOrderSummary(selectedPass, lockerPass.get());
                    return;
                }
            }

            outputHandler.showPassOrderSummary(selectedPass, null);

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private List<StudyCafePass> getStudyCafePassesBy(StudyCafePassType studyCafePassType) {
        StudyCafePasses studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
        return studyCafePasses.findAllBy(studyCafePassType);
    }

}
