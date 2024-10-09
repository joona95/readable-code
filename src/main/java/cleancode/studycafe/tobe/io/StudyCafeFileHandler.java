package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;
import cleancode.studycafe.tobe.model.StudyCafePasses;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StudyCafeFileHandler {

    public static final String STUDYCAFE_PASS_LIST_CSV_FILE_URI = "src/main/resources/cleancode/studycafe/pass-list.csv";
    public static final String STUDYCAFE_LOCKER_CSV_FILE_URI = "src/main/resources/cleancode/studycafe/locker.csv";
    public static final String LINE_SPLIT_CHAR = ",";

    public StudyCafePasses readStudyCafePasses() {
        List<String> lines = readLinesFromFile(STUDYCAFE_PASS_LIST_CSV_FILE_URI);

        List<StudyCafePass> studyCafePasses = lines.stream()
                .map(this::getStudyCafePass)
                .toList();

        return StudyCafePasses.of(studyCafePasses);
    }

    public List<StudyCafeLockerPass> readLockerPasses() {
        List<String> lines = readLinesFromFile(STUDYCAFE_LOCKER_CSV_FILE_URI);

        return lines.stream()
                .map(this::getStudyCafeLockerPass)
                .toList();
    }

    private List<String> readLinesFromFile(String fileURI) {
        try {
            return Files.readAllLines(Paths.get(fileURI));
        }  catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

    private StudyCafePass getStudyCafePass(String line) {
        String[] values = line.split(LINE_SPLIT_CHAR);
        StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
        int duration = Integer.parseInt(values[1]);
        int price = Integer.parseInt(values[2]);
        double discountRate = Double.parseDouble(values[3]);

        return StudyCafePass.of(studyCafePassType, duration, price, discountRate);
    }


    private StudyCafeLockerPass getStudyCafeLockerPass(String line) {
        String[] values = line.split(LINE_SPLIT_CHAR);
        StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
        int duration = Integer.parseInt(values[1]);
        int price = Integer.parseInt(values[2]);

        return StudyCafeLockerPass.of(studyCafePassType, duration, price);
    }
}
