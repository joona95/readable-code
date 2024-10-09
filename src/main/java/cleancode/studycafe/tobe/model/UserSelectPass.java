package cleancode.studycafe.tobe.model;

public class UserSelectPass {

    private final StudyCafePass studyCafePass;
    private final StudyCafeLockerPass studyCafeLockerPass;

    private UserSelectPass(StudyCafePass studyCafePass, StudyCafeLockerPass studyCafeLockerPass) {
        this.studyCafePass = studyCafePass;
        this.studyCafeLockerPass = studyCafeLockerPass;
    }

    public static UserSelectPass of(StudyCafePass studyCafePass, StudyCafeLockerPass studyCafeLockerPass) {
        return new UserSelectPass(studyCafePass, studyCafeLockerPass);
    }

    public StudyCafePass getStudyCafePass() {
        return studyCafePass;
    }

    public StudyCafeLockerPass getStudyCafeLockerPass() {
        return studyCafeLockerPass;
    }

    public int getDiscountPrice() {
        return studyCafePass.getDiscountPrice();
    }

    public int getTotalPrice() {
        return studyCafePass.getPrice() - getDiscountPrice() + getLockerPassPrice();
    }

    private int getLockerPassPrice() {
        return studyCafeLockerPass != null ? studyCafeLockerPass.getPrice() : 0;
    }

    public StudyCafePassType getPassType() {
        return studyCafePass.getPassType();
    }

    public boolean hasLockerPass() {
        return studyCafeLockerPass != null;
    }
}
