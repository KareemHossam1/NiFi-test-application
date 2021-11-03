package nifi.testing.application;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
public class GUI {
    int fileName = 0;
    String[] formats = {".txt", ".json"};
    RadioButton singleFile, multipleFiles;
    ToggleGroup radioButtons;
    Label title,formatLbl1, dataLbl1, pathLbl1, formatLbl2, dataLbl2, pathLbl2, timeLbl;
    ComboBox formatCombo1,formatCombo2;
    TextArea data1, data2;
    TextField path1,path2,time;
    Button generateBtn, stopBtn;
    Alert invalidInput;
    Thread thread;
    HBox formatH1, formatH2, dataH1, dataH2, pathH1, pathH2, timeH,btnH;
    VBox mainVBox;
    public GUI(Stage primaryStage){
        singleFile = new RadioButton("Single file");
        multipleFiles = new RadioButton("Multiple files");
        radioButtons = new ToggleGroup();
        title = new Label("\t\t      Apache NiFi test application");
        formatLbl1 = new Label("\tFormat");
        formatLbl2 = new Label("\tFormat");
        dataLbl1 = new Label("\tData\t   ");
        dataLbl2 = new Label("\tData\t   ");
        pathLbl1 = new Label("\tPath\t   ");
        pathLbl2 = new Label("\tPath\t   ");
        timeLbl = new Label("\tTime\t   ");
        formatCombo1 = new ComboBox();
        formatCombo2 = new ComboBox();
        data1 = new TextArea();
        data2 = new TextArea();
        time = new TextField("0");
        generateBtn = new Button("Generate!");
        stopBtn = new Button("Stop");
        path1 = new TextField();
        path2 = new TextField();
        btnH = new HBox(20);
        formatH1 = new HBox(10);
        formatH2 = new HBox(10);
        dataH1 = new HBox(10);
        dataH2 = new HBox(10);
        pathH1 = new HBox(10);
        pathH2 = new HBox(10);
        timeH = new HBox(10);
        mainVBox = new VBox(15);

        btnH.setPadding(new Insets(0,0,0,280));
        title.setFont(new Font("Arial", 30));
        singleFile.setToggleGroup(radioButtons);
        multipleFiles.setToggleGroup(radioButtons);
        singleFile.setSelected(true);
        formatCombo1.getItems().addAll("txt","json");
        formatCombo1.setPromptText("txt");
        formatCombo2.getItems().addAll("txt","json","Randomly");
        formatCombo1.getSelectionModel().select(0);
        formatCombo2.getSelectionModel().select(0);
        stopBtn.setDisable(true);
        this.enableSingle();
        invalidInput = new Alert (Alert.AlertType.ERROR);
        invalidInput.setTitle("Invalid inputs");                         
        invalidInput.setHeaderText("");
        invalidInput.setContentText("\tYou entered invalid path or time.\n\tTry again!");
        
        
        radioButtons.selectedToggleProperty().addListener(changeEvent -> {  
           if(singleFile.isSelected()){
               this.enableSingle();
           }
           else{
                this.enableMultiple();
           }
        });

        generateBtn.setOnAction((event) -> {
            if(singleFile.isSelected()){
                createSingleFile(formatCombo1, path1, data1);
                generateBtn.setDisable(false);
                stopBtn.setDisable(true);
            }
            else{
                if(Integer.valueOf(time.getText())<= 0){
                    generateBtn.setDisable(false);
                    stopBtn.setDisable(true);
                    invalidInput.show();
                    generateBtn.setDisable(false);
                    stopBtn.setDisable(true);
                }
                else{
                    // New thread
                    thread = new Thread(){
                        public void run(){
                            while(true){
                                try{
                                    Thread.sleep(Integer.valueOf(time.getText()));
                                    createSingleFile(formatCombo2, path2, data2);
                                    
                                }
                                catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                      };
                    thread.start();
                    generateBtn.setDisable(true);
                    stopBtn.setDisable(false);
                }
            }
        });
                
        stopBtn.setOnAction((event)->{
            thread.stop();
            generateBtn.setDisable(false);
            stopBtn.setDisable(true);
        });

        formatH1.getChildren().addAll(formatLbl1,formatCombo1);
        formatH2.getChildren().addAll(formatLbl2,formatCombo2);
        dataH1.getChildren().addAll(dataLbl1,data1);
        dataH2.getChildren().addAll(dataLbl2,data2);
        pathH1.getChildren().addAll(pathLbl1,path1);
        pathH2.getChildren().addAll(pathLbl2,path2);
        timeH.getChildren().addAll(timeLbl,time);
        btnH.getChildren().addAll(generateBtn,stopBtn);
        mainVBox.getChildren().addAll(title,singleFile,formatH1,dataH1,
                pathH1,multipleFiles,formatH2,dataH2,pathH2,timeH,btnH);
        mainVBox.setPadding(new Insets(20,0,0,15));
        Scene scene = new Scene(mainVBox, 750, 900);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Nifi testing APP");
        primaryStage.setScene(scene);
        primaryStage.show();
    
}
    private void enableSingle(){
        formatCombo2.setDisable(true);
        data2.setDisable(true);
        time.setDisable(true);
        path2.setDisable(true);
        formatLbl2.setDisable(true);
        dataLbl2.setDisable(true);
        pathLbl2.setDisable(true);
        timeLbl.setDisable(true);
        formatCombo1.setDisable(false);
        data1.setDisable(false);
        path1.setDisable(false);
        formatLbl1.setDisable(false);
        dataLbl1.setDisable(false);
        pathLbl1.setDisable(false);
    }
    private void enableMultiple(){
        formatCombo1.setDisable(true);
        data1.setDisable(true);
        path1.setDisable(true);
        formatLbl1.setDisable(true);
        dataLbl1.setDisable(true);
        pathLbl1.setDisable(true);
        formatCombo2.setDisable(false);
        data2.setDisable(false);
        time.setDisable(false);
        path2.setDisable(false);
        formatLbl2.setDisable(false);
        dataLbl2.setDisable(false);
        pathLbl2.setDisable(false);
        timeLbl.setDisable(false);
    }
    // Generate file names using numbers
    private String generateFileName(){
        fileName++;
        return String.valueOf(fileName);
    }
    private File createSingleFile(ComboBox format,  TextField pathText, TextArea data){
        
        if(format.getValue()!="Randomly"){
            File file = new File(pathText.getText() + "\\" +
                                generateFileName() + "." + format.getValue()); // Path + file name + file format
            try{
                Path Path = Paths.get(file.getPath());
                if(!file.exists()){
                    file.createNewFile();
                    byte[] strToBytes = data.getText().getBytes();
                    Files.write(Path, strToBytes);
                }
                else{
                    // Use recursion to rename files if they exist with the same name
                    file.renameTo(createSingleFile(format, pathText, data)); 
                }
            }
            catch(Exception e){
                this.invalidInput();
            }
            return file;
        }
        else{
            Random random = new Random();
            int index = random.nextInt(formats.length); // Choose random format
            File file = new File(pathText.getText() + "\\" +
                            generateFileName() + "." + formats[index]);
            try{
                Path Path = Paths.get(file.getPath());
                if(!file.exists()){
                    file.createNewFile();
                    byte[] strToBytes = data.getText().getBytes();
                    Files.write(Path, strToBytes);
                }
                else{
                    file.renameTo(createSingleFile(format, pathText, data));
                }
            }
            catch(Exception e){
                this.invalidInput();
            }
            return file;
        }
    }
    private void invalidInput(){
        invalidInput.show();
        thread.stop();
        generateBtn.setDisable(false);
        stopBtn.setDisable(true);
    }
}