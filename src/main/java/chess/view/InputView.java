package chess.view;

import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String SEPARATOR = " ";

    public static Command readCommand() {
        String input = SCANNER.nextLine();
        List<String> separatedInput = separateInput(input);
        return Command.create(separatedInput);
    }

    private static List<String> separateInput(String input) {
        List<String> separatedInput = List.of(input.split(SEPARATOR));
        if (separatedInput.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 입력값이 없습니다.");
        }

        return separatedInput;
    }
}
