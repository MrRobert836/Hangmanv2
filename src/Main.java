import java.io.IOException;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Files;

public class Main {
    private static String word;
    private static int errors = 0;
    private static int foundLetters = 0;

    private static final Set<Character> ENTERED_LETTERS = new LinkedHashSet<>();
    private static final StringBuilder MASK = new StringBuilder();
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Path PATH_OF_NOUNS_FILE = Path.of("src/resources/Nouns.txt");

    private static final String BEGIN_THE_GAME = "Да";
    private static final String END_THE_GAME = "Нет";
    private static final int FATAL = 6;

    public static void main(String[] args) {

        boolean begin = true;

        while(begin){
            System.out.printf("Введите %s если хотите начать новую игру. \n", BEGIN_THE_GAME);
            System.out.printf("Введите %s если хотите выйти из игры. \n", END_THE_GAME);

            while(true){

                String command = SCANNER.nextLine();

                if (command.equalsIgnoreCase(BEGIN_THE_GAME)) {

                    if(Files.exists(PATH_OF_NOUNS_FILE)){
                        chooseSecretWord();
                        playSession();
                        printEndgameInfo();
                        clearSession();
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

    private static void playSession(){

        MASK.append("*".repeat(word.length()));

        while(!isGameOver()){

            printSessionInfo();
            char letter = enterLetter();

            if(ENTERED_LETTERS.contains(letter)){
                System.out.println("Данная буква уже была введена");
                continue;
            }else {
                ENTERED_LETTERS.add(letter);
            }

            int indexOfLetter = word.indexOf(letter);

            if(indexOfLetter < 0){
                errors++;
                continue;
            }

            for (int i = 0; i < word.length(); i++) {

                char wordLetter = word.charAt(i);

                if(letter == wordLetter){
                    foundLetters++;
                    MASK.setCharAt(i, letter);
                }
            }
        }

        printSessionInfo();
    }

    private static boolean isGameOver(){
        return foundLetters == word.length() || errors == FATAL;
    }

    private static void chooseSecretWord() {

        List<String> words;

        try {
            words = Files.readAllLines(PATH_OF_NOUNS_FILE);
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        Random random = new Random();
        word = words.get(random.nextInt(words.size()));
    }

    private static void clearSession(){
        foundLetters = 0;
        errors = 0;
        ENTERED_LETTERS.clear();
        MASK.setLength(0);
    }

    private static char enterLetter(){

        String input;

        while(true){
            System.out.print("Введите символ: ");
            input = SCANNER.nextLine();

            if(isVoidOrWhitespace(input)){
                System.out.println("Введена пустая строка");
            }else if (isLetterTooLong(input)) {
                System.out.println("Введана строка. Необходимо ввести символ");
            } else if (!isRussianLetter(input.charAt(0))){
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

    private static boolean isVoidOrWhitespace (String input){
        return input.isBlank();
    }

    private static void printSessionInfo(){
        System.out.printf("Ошибки: %s\n", errors);
        System.out.print("Введённые буквы: ");
        printEnteredLetters();

        System.out.printf("\nСлово: %s\n", MASK);
        Gallows.printPicture(errors);
    }

    private static void printEnteredLetters(){
        if (!ENTERED_LETTERS.isEmpty()) {
            Iterator<Character> iterator = ENTERED_LETTERS.iterator();

            System.out.print(iterator.next());

            while (iterator.hasNext()){

                System.out.printf(", %s", iterator.next());
            }
        }
    }

    private static void printEndgameInfo(){

        if (errors == FATAL){
            System.out.println("ПОРАЖЕНИЕ!!!");
            System.out.printf("Загаданное слово: %s\n", word);
        } else{
            System.out.println("ПОБЕДА!!!");
        }
    }
}