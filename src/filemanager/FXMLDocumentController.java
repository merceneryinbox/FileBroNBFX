/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import fileManagerTools.PathedTreeItem;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXMLDocumentController implements Initializable {

// common variables
    private Iterable<Path> rootDirs;
    private List<PathedTreeItem> rootTreeItemsList;
    private ArrayList<PathedTreeItem> subPathedTIListInSelectedPTI;
    private List<MenuItem> mnBtItemsList;
    private Image img;
    public ImageView rootImgView;
    private final String hdPicPath = "icoes/hd_32x32.png";
    private PathedTreeItem selectedRootItem;
    private PathedTreeItem selectedPTI;

// Window controls
    @FXML
    private MenuBar mnBar;

// File Menu
    @FXML
    private MenuItem subMnFile;
    @FXML
    private MenuItem ssMnNew;
    @FXML
    private MenuItem ssmnOpen;
    @FXML
    private MenuItem mnOpenRecent;
    @FXML
    private MenuItem ssMnSave;
    @FXML
    private MenuItem ssMnSaveAs;
    @FXML
    private MenuItem ssMnPreferences;
    @FXML
    private MenuItem ssMnQuit;

//Edit Menu
    @FXML
    private MenuItem subMnEdit;
    @FXML
    private MenuItem ssMnEditUndo;
    @FXML
    private MenuItem ssMnEditRedo;
    @FXML
    private MenuItem ssMnEditCut;
    @FXML
    private MenuItem ssMnEditCopy;
    @FXML
    private MenuItem ssMnEditPaste;
    @FXML
    private MenuItem ssMnEditDel;
    @FXML
    private MenuItem ssMnEditSelAll;
    @FXML
    private MenuItem ssMnEditUnSelAll;

// Help About Menu
    @FXML
    private MenuItem subMnHelp;
    @FXML
    private MenuItem ssMnHelp;

// Tree View component
    @FXML
    private TreeView tvLeft;

// Diskchoice button
    @FXML
    private MenuButton mnBtn;

// TableView
    @FXML
    private TableView tblVCenter;
    @FXML
    private TableColumn<PathedTreeItem, String> tblViewClmnFileName;
    @FXML
    private TableColumn<PathedTreeItem, String> tblViewClmnFileExt;
    @FXML
    private TableColumn<PathedTreeItem, Number> tblViewClmnFileSize;
    @FXML
    private TableColumn<PathedTreeItem, Number> tblViewClmnFileLastMod;

// Text Area to the right in application
    @FXML
    private TextArea txtAreaLeft;
// main method in Controller

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        img = new Image(hdPicPath);
        rootImgView = new ImageView(img);
        rootDirs = new ArrayList<>();
        rootTreeItemsList = new ArrayList<>();
        mnBtItemsList = new ArrayList<>();
        subPathedTIListInSelectedPTI = new ArrayList<>();
        rootDirs = FileSystems.getDefault().getRootDirectories();

        tvLeft.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        tblViewClmnFileName.setCellValueFactory(new PropertyValueFactory<PathedTreeItem, String>("nameOfFile"));
        tblViewClmnFileExt.setCellValueFactory(new PropertyValueFactory<PathedTreeItem, String>("extension"));
        tblViewClmnFileSize.setCellValueFactory(new PropertyValueFactory<PathedTreeItem, Number>("size"));
        tblViewClmnFileLastMod.setCellValueFactory(new PropertyValueFactory<PathedTreeItem, Number>("lastModFormatedString"));

// create list with PathedTreeItems from list of root directoryes in filesystem
        for (Path rootDir : rootDirs) {
            MenuItem tmpMMenuItem = new MenuItem(rootDir.toString());
            tmpMMenuItem.setGraphic(rootImgView);
            mnBtItemsList.add(tmpMMenuItem);
        }

// add list of MenuItems with names of found drives in menu button drop down items
        mnBtn.getItems().addAll(mnBtItemsList);

// assign Action handle to each menu item in drop button menu then click on it
        mnBtItemsList.forEach((MenuItem mnBtnSelectedItem) -> {
            mnBtnSelectedItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    tvLeft.setRoot(null);
                    selectedRootItem = new PathedTreeItem(mnBtnSelectedItem.getText());
                    tvLeft.setRoot(selectedRootItem);
                    tvLeft.setShowRoot(true);
                }
            });
        });

        // определяем поведение нашего приложения для случая если пользователь изменил(в основном
        // - выделил(мышью или клавишами)) элемент TreeView
        tvLeft.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                ExecutorService pool = Executors.newFixedThreadPool(1);
                selectedPTI = (PathedTreeItem) newValue; // приведение типа
                selectedPTI.populateMyself();
                Path tmpPath = selectedPTI.getValue();
                subPathedTIListInSelectedPTI.clear(); // обнуление верменного листа подэлементов выбранного элемента
                try {
                    pool.execute(new Runnable() {
                        @Override
                        public void run() {
                            // если выбранный элемент - папка = > берём каждый подэлемент папки и обрачиваем его в
                            // PathedTreeItem, добавляя полученное в лист подэлементов - subPathedTIListInSelectedPTI
                            if (Files.isDirectory(tmpPath)) {
                                try (DirectoryStream<Path> subLiStream = Files.newDirectoryStream(tmpPath)) {
                                    for (Path path : subLiStream) {
                                        subPathedTIListInSelectedPTI.add(new PathedTreeItem(path));
                                    }
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }

                                // если выбранный элемент не папка => добавляем его в subPathedTIListInSelectedPTI
                                // - получается лист с одним элементом
                            } else {
                                subPathedTIListInSelectedPTI.add(new PathedTreeItem(tmpPath));
                            }

                            // оборачиваем полученный subPathedTIListInSelectedPTI типа - ArrayList в тип ObservableList
                            // и добавляем этот лист в таблицу (оборачивание нужно т.к. TableView принимает только ObservableList
                            // tblVCenter.setItems(null);
                            tblVCenter.setItems(FXCollections.observableList(subPathedTIListInSelectedPTI));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("attempt to shutdown executor");
                    pool.shutdown();
                    try {
                        pool.awaitTermination(5, TimeUnit.SECONDS);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (!pool.isTerminated()) {
                        System.err.println("cancel non-finished tasks");
                    }
                    pool.shutdownNow();
                    System.out.println("shutdown finished");
                }
            }
        });
    } // не удалять !

// Start menu File commands
    @FXML

    public void makeNew() {

    }

    @FXML
    public void openFolder() {

    }

    @FXML
    public void openRecent() {

    }

    @FXML
    public void save() {

    }

    @FXML
    public void saveAs() {

    }

    @FXML
    public void preferences() {

    }

    @FXML
    public void quitApp() {

    }

    // start Edit menu commands
    @FXML
    public void undo() {

    }

    @FXML
    public void redo() {

    }

    @FXML
    public void cut() {

    }

    @FXML
    public void copy() {

    }

    @FXML
    public void paste() {

    }

    @FXML
    public void delete() {

    }

    @FXML
    public void selectAll() {

    }

    @FXML
    public void unselectAll() {

    }

// Start Help menu commands
    @FXML
    public void helpAbout() {

    }

// Start menu button Check Disks Commands
    @FXML
    public void dropDisks() {

    }
}
