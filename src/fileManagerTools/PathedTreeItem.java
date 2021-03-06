/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileManagerTools;

import java.io.IOException;
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

    private void autosetIcon() {
        // с помощью метода filterFileByType класса FileTypesFilter выясняю какого
        //типа файл в переменной данного PathedTreeItem
        String test = new FileTypesFilter(path).filterFileByType();

        // Getting the type of selected PathedTreeItem
        switch (test) {
            case "folder":

                if (this.getVisited() == true && this.getParent() != null) {
                    try {
                        this.setGraphic(new ImageView(new Image((getClass().getResourceAsStream("icoes.folder_opened.png")))));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (this.getVisited() == false && this.getParent() != null) {
                    try {
                        this.setGraphic(new ImageView(getClass().getResource("icoes.folder_closed.png").toExternalForm()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case "archive":
                try {
                    this.setGraphic(new ImageView(getClass().getResource("icoes.archive.png").toExternalForm()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "exe":
                try {
                    this.setGraphic(new ImageView(getClass().getResource("icoes.exe.png").toExternalForm()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "office":
                try {
                    this.setGraphic(new ImageView(getClass().getResource("icoes.office.png").toExternalForm()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "web":
                try {
                    this.setGraphic(new ImageView(getClass().getResource("icoes.web.png").toExternalForm()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "picture":
                try {
                    this.setGraphic(new ImageView(getClass().getResource("icoes.picture.png").toExternalForm()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "multimedia":
                try {
                    this.setGraphic(new ImageView(getClass().getResource("icoes.multimedia.png").toExternalForm()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "others":
                try {
                    this.setGraphic(new ImageView(getClass().getResource("icoes.other.png").toExternalForm()));
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

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return df.format(ft.toMillis());
    }
}
