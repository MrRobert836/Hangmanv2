public class Gallows {

    private static final String[][] PICTURES = {
            {
                    "-----   ",
                    "|       ",
                    "|       ",
                    "|       ",
                    "|       ",
                    "------- "
            },
            {
                    "-----   ",
                    "|   |   ",
                    "|   O   ",
                    "|       ",
                    "|       ",
                    "------- "
            },
            {
                    "-----   ",
                    "|   |   ",
                    "|   O   ",
                    "|   |   ",
                    "|       ",
                    "------- "
            },
            {
                    "-----   ",
                    "|   |   ",
                    "|   O   ",
                    "|  /|   ",
                    "|       ",
                    "------- "
            },
            {
                    "-----   ",
                    "|   |   ",
                    "|   O   ",
                    "|  /|\\  ",
                    "|       ",
                    "------- "
            },
            {
                    "-----   ",
                    "|   |   ",
                    "|   O   ",
                    "|  /|\\  ",
                    "|  /    ",
                    "------- "
            },
            {
                    "-----   ",
                    "|   |   ",
                    "|   O   ",
                    "|  /|\\  ",
                    "|  / \\  ",
                    "------- "
            }
    };

    public static void printPicture(int numPicture){
        String[] picture = PICTURES[numPicture];

        for(String line: picture){
            System.out.println(line);
        }
    }
}
