import java.util.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        String[] texts = new String[25];

        List<Thread> threads = new ArrayList<>();

        long startTs = System.currentTimeMillis(); // start time

        for (int l = 0; l < texts.length; l++) {
            String newString = generateText("aab", 30_000);
            texts[l] = newString;

            Thread thread = new Thread(() -> {
                int maxSize = 0;
                for (int i = 0; i < newString.length(); i++) {
                    for (int j = 0; j < newString.length(); j++) {
                        if (i >= j) {
                            continue;
                        }
                        boolean bFound = false;
                        for (int k = i; k < j; k++) {
                            if (newString.charAt(k) == 'b') {
                                bFound = true;
                                break;
                            }
                        }
                        if (!bFound && maxSize < j - i) {
                            maxSize = j - i;
                        }
                    }
                }
                System.out.println(newString.substring(0, 100) + " -> " + maxSize);
            });

            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join(); // зависаем, ждём когда поток объект которого лежит в thread завершится
        }

        long endTs = System.currentTimeMillis(); // end time

        System.out.println("Time: " + (endTs - startTs) + "ms");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}