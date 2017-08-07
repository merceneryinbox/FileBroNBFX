
import java.nio.file.Path;

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

        /* try {
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
         */
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
    /*
   private static String giveMeStringFromLong(long size) {
		Long longSizeIncome = new Long(size);

		System.out.println(size/1024);
		String c = "";
		String f = longSizeIncome.toString();
		Integer i = Integer.parseInt(f);
		String s = i.toString();
		System.out.println(s);
		int start;
		int end;
		for (int j = 0; j < s.length();) {
			System.out.println("j = " + j);
			start = j;
			end = j + s.length() / 3;
			System.out.println("start = " + start);
			System.out.println("end = " + end);
			c = c + s.substring(start, end) + "\'";
			System.out.println("c = " + c);
			j = j + s.length() / 3;

			if (j == s.length()) {
				c = c.substring(0, c.length() - 1);
				break;
			} else if (j > s.length()) {
				c = c + s.substring(j - s.length() / 3, s.length());
				break;
			}
		}
		System.out.println("C = " + c);

		return c;
	}
     */
}
