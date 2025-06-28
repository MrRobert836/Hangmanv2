import java.io.IOException;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Files;

public class Main {
    private static String gameWord;
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String BEGIN_THE_GAME = "Да";
    private static final String END_THE_GAME = "Нет";
    private static final int FATAL = 6;
    private static final int DEFAULT_ARRAY_VALUE = 0;

    public static void main(String[] args) throws IOException {

        boolean gameBegin = true;

        while(gameBegin){
            System.out.println("Введите 'Да' если хотите начать новую игру. " +
                    "\nВведите 'Нет' если хотите выйти из игры");

            while(true){

                String yesOrNo = SCANNER.nextLine();

                if (yesOrNo.equals(BEGIN_THE_GAME)) {

                    newGameWord();
                    gameSession();
                    break;

                }else if(yesOrNo.equals(END_THE_GAME)){

                    gameBegin = false;
                    break;

                }else
                    System.out.println("Некорректный ввод.");
                System.out.printf("Необходимо ввести '%s' или '%s'.\n", BEGIN_THE_GAME, END_THE_GAME);
            }
        }
    }

    public static void gameSession(){
        int errors = 0;
        int foundLetters = 0;
        HashSet<String> gameMistakes = new HashSet<>();
        int[] indexOfGameWordLetter = new int[gameWord.length()];

        while(foundLetters < gameWord.length()){

            System.out.println("Ошибки: " + errors);
            System.out.print("Слово: ");
            printCorrectLetters(indexOfGameWordLetter, gameWord);
            Gallows(errors);
            String letter = enterGameLetter();

            int indexOfLetter = gameWord.indexOf(letter);

            if(indexOfLetter < 0){
                if(gameMistakes.contains(letter))
                    continue;

                errors++;
                gameMistakes.add(letter);

                if (errors == FATAL)
                    break;

                continue;
            }

            for (int i = 0; i < gameWord.length(); i++) {

                String gameWordLetter = Character.toString(gameWord.charAt(i));

                if(letter.equalsIgnoreCase(gameWordLetter)){
                    foundLetters++;
                    //В массиве хранятся номера букв секретного слова с 1, а не с 0
                    indexOfGameWordLetter[i] = i + 1;
                }
            }
        }

        if (errors == FATAL)
            System.out.println("ПОРАЖЕНИЕ!!!\n" + "Загаданное слово: " + gameWord);
        else
            System.out.println("ПОБЕДА!!!");
    }

    public static void newGameWord() throws IOException {
        Path path = Path.of("src/resources/Nouns.txt");
        List<String> gameWords = Files.readAllLines(path);

        Random random = new Random();
        gameWord = gameWords.get(random.nextInt(gameWords.size()));
    }

    public static void printCorrectLetters (int [] indexOfGameWordLetter, String gameWord){
        for (int i = 0; i < gameWord.length(); i++){
            if(indexOfGameWordLetter[i] == DEFAULT_ARRAY_VALUE)
                System.out.print("_");
            else
                System.out.print(gameWord.charAt(i));
            System.out.print(".");
        }
        System.out.println();
    }

    public static String enterGameLetter(){

        String input;

        while(true){
            System.out.print("Введите символ: ");
            input = SCANNER.nextLine();

            if(checkLetterTooLong(input))
                System.out.println("Введана строка. Необходимо ввести символ");
            else if (checkLetterNotInAlphabet(input))
                System.out.println("Введён некорректный символ. Символ должен быть буквой русского алфавита");
            else
                break;
        }

        return input;
    }

    public static boolean checkLetterNotInAlphabet(String input){

        String[] alphabet = new String[]{"а","б","в","г","д","е","ё","ж","з","и","й","к","л",
                "м","н","о","п","р","с","т","у","ф","х","ц","ч","ш","щ","ъ","ы","ь","э","ю","я"};

        int IndexOfLetter = Arrays.binarySearch(alphabet, input.toLowerCase());

        return IndexOfLetter < 0;
    }

    public static boolean checkLetterTooLong(String input){

        return input.length() > 1;
    }

    public static void Gallows(int errors){
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