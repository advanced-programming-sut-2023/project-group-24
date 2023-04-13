package view.enums;

public enum RecoveryQuestion {
    ONE("What is my father's name?"),
    TWO("What was my first pet's name?"),
    THREE("What is my mother's last name?");


    private final String recoveryQuestion;

    RecoveryQuestion(String recoveryQuestion) {
        this.recoveryQuestion = recoveryQuestion;
    }

    public static String getRecoveryQuestionByNumber(int number) {
        switch (number) {
            case 1:
                return ONE.recoveryQuestion;
            case 2:
                return TWO.recoveryQuestion;
            case 3:
                return THREE.recoveryQuestion;
            default:
                return null;
        }
    }
}

