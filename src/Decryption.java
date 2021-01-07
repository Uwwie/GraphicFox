import net.lingala.zip4j.core.*;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;


public class Decryption {

    public void create() throws IOException {

        File f = new File("temp");
        if (f.exists() && f.isDirectory()) {
            deleteDirectoryWalkTree(Paths.get("temp"));
        }
        Files.createDirectory(Paths.get("temp"));

        unblock("Images");
        unblock("Music");


        }

        public static void unblock (String s){
            try {
                ZipFile zipFile = new ZipFile("resources/"+s+".zip");
                zipFile.setPassword(s+"Password");
                zipFile.extractAll("temp/"+s);
            } catch (ZipException e) { }
        }


    void deleteDirectoryWalkTree(Path path) throws IOException {
        FileVisitor visitor = new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc != null) {
                    throw exc;
                }
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        };
        Files.walkFileTree(path, visitor);
    }


}
