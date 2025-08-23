import java.io.IOException;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Files;

public class Main {
    private static String word;
    private static int errors;
    private static int foundLetters;
    private static Set<Character> enteredLetters;
    private static StringBuilder mask;

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Path PATH_OF_NOUNS_FILE = Path.of("src/resources/Nouns.txt");

    private static final String BEGIN_THE_GAME = "Да";
    private static final String END_THE_GAME = "Нет";
    private static final int FATAL = 6;

    public static void main(String[] args) throws IOException {

        boolean begin = true;

        while(begin){
            System.out.printf("Введите %s если хотите начать новую игру. \n", BEGIN_THE_GAME);
            System.out.printf("Введите %s если хотите выйти из игры. \n", END_THE_GAME);

            while(true){

                String command = SCANNER.nextLine();

                if (command.equalsIgnoreCase(BEGIN_THE_GAME)) {

                    if(Files.exists(PATH_OF_NOUNS_FILE)){
                        chooseSecretWord();
                        gameSession();
                        printEndgameInfo();
                    }else{
                        System.out.println("Файл со словами не найден. Игра будет завершена");
                        begin = false;
                    }

                    break;

                }else if(command.equalsIgnoreCase(END_THE_GAME)){
                    begin = false;
                    break;
                }else{
                    System.out.println("Некорректный ввод.");
                    System.out.printf("Необходимо ввести '%s' или '%s'.\n", BEGIN_THE_GAME, END_THE_GAME);
                }
            }
        }
    }

    private static void gameSession(){

        initializeVariables();

        while(!isGameOver()){

            printSessionInfo();
            char letter = enterLetter();

            if(enteredLetters.contains(letter)){
                System.out.println("Данная буква уже была введена");
                continue;
            }else {
                enteredLetters.add(letter);
            }

            int indexOfLetter = word.indexOf(letter);

            if(indexOfLetter < 0){
                errors++;

                if (errors == FATAL){
                    break;
                }
                continue;
            }

            for (int i = 0; i < word.length(); i++) {

                char wordLetter = word.charAt(i);

                if(letter == wordLetter){
                    foundLetters++;
                    mask.setCharAt(i, letter);
                }
            }
        }

        printSessionInfo();
    }

    private static boolean isGameOver(){
        return foundLetters == word.length() || errors == FATAL;
    }

    private static void chooseSecretWord() throws IOException {

        List<String> words = Files.readAllLines(PATH_OF_NOUNS_FILE);

        Random random = new Random();
        word = words.get(random.nextInt(words.size()));
    }

    private static void initializeVariables(){
        foundLetters = 0;
        errors = 0;
        enteredLetters = new HashSet<>();
        mask = new StringBuilder("*");
        mask.append("*".repeat(word.length() - 1));
    }

    private static char enterLetter(){

        String input;

        while(true){
            System.out.print("Введите символ: ");
            input = SCANNER.nextLine();

            if(isLetterTooLong(input)){
                System.out.println("Введана строка. Необходимо ввести символ");
            }else if (!isRussianLetter(input.charAt(0))){
                System.out.println("Введён некорректный символ. Символ должен быть буквой русского алфавита");
            }else{
                break;
            }
        }

        return input.toLowerCase().charAt(0);
    }

    private static boolean isRussianLetter(char symbol){

        symbol = Character.toLowerCase(symbol);
        return (symbol >= 'а' && symbol <= 'я') || symbol == 'ё';
    }

    private static boolean isLetterTooLong(String input){
        return input.length() > 1;
    }

    private static void printSessionInfo(){
        System.out.printf("Ошибки: %s\n", errors);
        System.out.print("Введённые буквы: ");

        for (Character enteredLetter: enteredLetters){
            System.out.print(enteredLetter + ", ");
        }

        System.out.printf("\nСлово: %s\n", mask);
        printGallows();
    }

    private static void printEndgameInfo(){

        if (errors == FATAL){
            System.out.println("ПОРАЖЕНИЕ!!!");
            System.out.printf("Загаданное слово: %s\n", word);
        } else{
            System.out.println("ПОБЕДА!!!");
        }
    }

    private static void printGallows(){
        final String NO_ERRORS = """
                              ----------
                              |/     |
                              |
                              |
                              |
                              |
                              |
                              |
                              |
                            __|________
                            |         |\
                    """;

        final String AD_HEAD = """
                              ----------
                              |/     |
                              |     ( )
                              |
                              |
                              |
                              |
                              |
                              |
                            __|________
                            |         |\
                    """;

        final String AD_BODY = """
                              ----------
                              |/     |
                              |     ( )
                              |     _|_
                              |      |\s
                              |      |
                              |      \s
                              |      \s
                              |
                            __|________
                            |         |\
                    """;

        final String AD_FIRST_ARM = """
                              ----------
                              |/     |
                              |     ( )
                              |     _|_
                              |    / |\s
                              |      |
                              |      \s
                              |      \s
                              |
                            __|________
                            |         |\
                    """;

        final String AD_LAST_ARM = """
                              ----------
                              |/     |
                              |     ( )
                              |     _|_
                              |    / | \\\\
                              |      |
                              |      \s
                              |      \s
                              |
                            __|________
                            |         |\
                    """;

        final String AD_LEG = """
                              ----------
                              |/     |
                              |     ( )
                              |     _|_
                              |    / | \\\\
                              |      |
                              |     /\s
                              |    / \s
                              |
                            __|________
                            |         |\
                    """;

        final String GAME_OVER = """
                              ----------
                              |/     |
                              |     ( )
                              |     _|_
                              |    / | \\\\
                              |      |
                              |     /\s
                              |    /   \\\\
                              |
                            __|________
                            |         |\
                    """;

        switch(errors){
            case 0: System.out.println(NO_ERRORS);
                break;
            case 1: System.out.println(AD_HEAD);
                break;
            case 2: System.out.println(AD_BODY);
                break;
            case 3: System.out.println(AD_FIRST_ARM);
                break;
            case 4: System.out.println(AD_LAST_ARM);
                break;
            case 5: System.out.println(AD_LEG);
                break;
            case 6: System.out.println(GAME_OVER);
        }
    }
}