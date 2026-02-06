import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Main {
    private static final Random random = new Random();
  
    public static void main(String[] args) throws InterruptedException {

        var files = selectFilesToDelete();

        for (var file : files) {
            System.out.printf("%s\r\n", file.getAbsolutePath());
        }

        System.out.println();

        var option = JOptionPane.showConfirmDialog(
			null,
			"Are you sure you want to permanently delete these files?",
			"Please confirm",
			JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION) {
            System.out.println("[#] Deleting...");

            try {
                for (var file : files) {
                    overwriteAndDeleteFile(file);
                }
            }
            catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        System.out.println();
        System.out.println("[!] Work has been complete!");

    }

    public static File[] selectFilesToDelete() {
        var frame = new Frame();

        var fileDialog = new FileDialog(frame);
        fileDialog.setTitle("Select files that you would like to delete...");
        fileDialog.setMultipleMode(true);
        fileDialog.setVisible(true);

        var files = fileDialog.getFiles();

        fileDialog.dispose();
        frame.dispose();

        return files;
    }

    public static void overwriteAndDeleteFile(File file) throws IOException, InterruptedException {
        long fileSize = file.length();
        var stream = new FileOutputStream(file);

        for (long i = 0; i < fileSize; i++) {
            stream.write(random.nextInt(255));
        }

        stream.close();
        System.out.printf("[!] Overwritten '%s'.\r\n", file.getName());
        Thread.sleep(1000);

        try {
            file.delete();
            System.out.printf("[!] Deleted file '%s'.\r\n", file.getName());
        }
        catch (SecurityException securityException) {
            System.out.printf("[x] could not delete '%s'\r\n", file.getName());
        }
    }
}
