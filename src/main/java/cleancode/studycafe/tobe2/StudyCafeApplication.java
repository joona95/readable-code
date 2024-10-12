package cleancode.studycafe.tobe2;

import cleancode.studycafe.tobe2.io.provider.LockerPassFileReader;
import cleancode.studycafe.tobe2.io.provider.SeatPassFileReader;
import cleancode.studycafe.tobe2.provider.LockerPassProvider;
import cleancode.studycafe.tobe2.provider.SeatPassProvider;

public class StudyCafeApplication {

    public static void main(String[] args) {
        SeatPassProvider seatPassProvider = new SeatPassFileReader();
        LockerPassProvider lockerPassProvider = new LockerPassFileReader();
        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(seatPassProvider, lockerPassProvider);
        studyCafePassMachine.run();
    }

}
