
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mercenery
 */
public class TestTroubles {

    private static Path path;
    private static String fullFileName;

    public static void main(String[] args) throws InterruptedException {
        try {
            Path p = Paths.get("D:\\123.fxml");
            FileTime ft = Files.getLastModifiedTime(p);
            DateFormat df = new SimpleDateFormat("dd.MMMM.yyyy года в hh:mm");
            String s = df.format(ft.toMillis());
//            BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);
//            FileTime date = attr.lastModifiedTime();
            System.out.println(s);

        } catch (IOException ex) {
            Logger.getLogger(TestTroubles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
        path = Paths.get("D:\\");
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
            for (Path path1 : directoryStream) {
                System.out.println(path1.toString());
            }
        } catch (IOException ex) {
            Logger.getLogger(TestTroubles.class.getName()).log(Level.SEVERE, null, ex);
        }
     */

 /*
        Path p = Paths.get("D:\\");
        System.out.println(Test(p));
     */
 /*
        try {
            imageView = new ImageView(new Image(s));
        } catch (Exception e) {
        }
     */

 /* simple displays ImageView the image as is
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
     */
 /*PathedTreeItem selectedRootItem = new PathedTreeItem(FileSystems.getDefault().getPath("d:\\!"));
        System.out.println(selectedRootItem.getValue().toString());
        selectedRootItem = new FulFillIcoByType(selectedRootItem).filInTheIconInPathedTreeItem();
     */
 /*
        FileSystem fs = FileSystems.getDefault();
        Path path = Paths.get("d:\\Кодировки в консоли.JPG");
        // path = fs.getPath("d:\\Кодировки в консоли.JPG");
        String test = new FileTypesFilter(path).filterFileByType();
        System.out.println(test);
     */
 /**/
    public static String Test(Path givenPath) {

        path = givenPath;
        return fullFileName = path.getFileName().toString().toLowerCase();

    }

}
