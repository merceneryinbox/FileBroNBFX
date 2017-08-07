/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileManagerTools;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author mercenery
 */
public final class PathedTreeItem extends TreeItem<Path> {

    private boolean isVisited = false;
    private boolean leafPropertyComputed = false;
    private Path path;
    private List<Path> subPaths;
    private List<PathedTreeItem> subPathItems;
    private String fullFileName;

    private String extension;
    private String nameOfFile;
    private long size;
    private String lastModFormatedString;
    private final DateFormat df = new SimpleDateFormat("yyyy. MMMM. dd в hh:mm");
    private FileTime ft;

    // string constructor
    public PathedTreeItem(String strPath) {
        super(Paths.get(strPath));
        path = Paths.get(strPath);
        fullFileName = strPath;

        nameOfFile = getNameOfFile();
        extension = getExtension();
        size = getSize();
        // sizeInFormatedString = getSizeInFormatedString();
        // lastModFormatedString = getLastModFormatedString();

        subPaths = new ArrayList<>();
        subPathItems = new ArrayList<>();

        if (path.getParent() != null) {
            autosetIcon();
        }
    }

    // Path constructor
    public PathedTreeItem(Path path) {
        super(path);
        this.path = path;

        fullFileName = path.toString();
        nameOfFile = getNameOfFile();
        extension = getExtension();
        size = getSize();
        // sizeInFormatedString = getSizeInFormatedString();
        //lastModFormatedString = getLastModFormatedString();

        subPaths = new ArrayList<>();
        subPathItems = new ArrayList<>();
        if (path.getParent() != null) {
            autosetIcon();
        }
    }

    @Override
    public boolean isLeaf() {
        return !Files.isDirectory(this.getValue()) && this.getValue() != null;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public void populateMyself() {
// населяю элемент если - это папка и если она не нуль
        if (Files.isDirectory(path) && path != null) {

            // создаю Iterable объект из элементов внутри папки (не рекурсивно)
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
                for (Path path1 : directoryStream) {

                    // сохраняю каждый найденный элемент типа - Path из Iterable списка - directoryStream в ArrayList - subPaths
                    subPaths.add(path1);
                }

                // теперь беру каждый элемент из subPaths и оборачиваю его в PathedTreeItem, а потом добавляю в ArrayList
                // subPathItems
                subPaths.forEach((Path path1) -> {
                    subPathItems.add(new PathedTreeItem(path1));
                });

                // а теперь subPathItems добавляю в родительский элемент
                this.getChildren().addAll(subPathItems);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void autosetIcon() {
        // с помощью метода filterFileByType класса FileTypesFilter выясняю какого
        //типа файл в переменной данного PathedTreeItem
        String test = new FileTypesFilter(path).filterFileByType();

        // Getting the type of selected PathedTreeItem
        switch (test) {
            case "folder":

                if (this.getVisited() == true && this.getParent() != null) {
                    try {
                        this.setGraphic(new ImageView(new Image((getClass().getResourceAsStream("icoes\\folder_opened.png")))));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (this.getVisited() == false && this.getParent() != null) {
                    try {
                        this.setGraphic(new ImageView(getClass().getResource("icoes\folder_closed.png").toExternalForm()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case "archive":
                try {
                    this.setGraphic(new ImageView(getClass().getResource("archive.png").toExternalForm()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "exe":
                try {
                    this.setGraphic(new ImageView(getClass().getResource("icoes\\exe.png").toExternalForm()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "office":
                try {
                    this.setGraphic(new ImageView(getClass().getResource("icoes\\office.png").toExternalForm()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "web":
                try {
                    this.setGraphic(new ImageView(getClass().getResource("icoes\\web.png").toExternalForm()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "picture":
                try {
                    this.setGraphic(new ImageView(getClass().getResource("icoes\\picture.png").toExternalForm()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "multimedia":
                try {
                    this.setGraphic(new ImageView(getClass().getResource("icoes\\multimedia.png").toExternalForm()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "others":
                try {
                    this.setGraphic(new ImageView(getClass().getResource("icoes\\other.png").toExternalForm()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void setIsVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setLastMod(FileTime lastMod) {
        this.lastModFormatedString = df.format(lastMod);
    }

    private boolean getVisited() {
        return isVisited;
    }

    public String getNameOfFile() {
        if (!Files.isDirectory(this.getValue()) && Files.isReadable(this.getValue())) {
            nameOfFile = fullFileName.substring(0, fullFileName.lastIndexOf("."));
        } else {
            nameOfFile = fullFileName;
        }
        return nameOfFile;
    }

    public String getExtension() {
        extension = null;
        if (!Files.isDirectory(this.getValue())) {
            extension = fullFileName.substring(fullFileName.lastIndexOf(".") + 1, fullFileName.length());
        }
        return extension;
    }

    public Long getSize() {
        try {
            size = Files.size(this.getValue()) / 1024;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return size;
    }

    public String getLastModFormatedString() {
        try {
            ft = Files.getLastModifiedTime(this.getValue());
            lastModFormatedString = df.format(ft.toMillis());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return lastModFormatedString;
    }

    // Ondemand slow method
    /*private static String giveMeStringFromLong(long size) {
        Long longSizeIncome = size;
        String resultFormatedSizeTostring = "";
        String incomLongToString = longSizeIncome.toString();
        Integer integerFromLongByString = Integer.parseInt(incomLongToString);
        String stringFromInteger = integerFromLongByString.toString();
        int start;
        int end;
        int y = stringFromInteger.length();

        if (y > 3) {
            for (int j = 0; j < y;) {

                System.out.println(y);
                start = j;
                end = j + stringFromInteger.length() / 3;
                resultFormatedSizeTostring = resultFormatedSizeTostring + stringFromInteger.substring(start, end) + "\'";
                j = j + stringFromInteger.length() / 3;

                if (j == stringFromInteger.length()) {
                    resultFormatedSizeTostring = resultFormatedSizeTostring.substring(0, resultFormatedSizeTostring.length() - 1);
                    break;
                } else if (j > stringFromInteger.length()) {
                    resultFormatedSizeTostring = resultFormatedSizeTostring + stringFromInteger.substring(j - stringFromInteger.length() / 3, stringFromInteger.length());
                    break;
                }
            }
        } else {
            resultFormatedSizeTostring = "";
        }
        return resultFormatedSizeTostring;
    }*/
}
