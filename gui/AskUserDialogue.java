package gui;

import controller.AskUser;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class AskUserDialogue implements AskUser {
    //todo поменять чтобы было разное по параметру в зависимости от команды вью
    @Override
    public File askFile() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileDialog.setDialogType(JFileChooser.CUSTOM_DIALOG);
        FileFilter filter = new FileNameExtensionFilter(
                "Portable Network Graphics", "png");
        fileDialog.addChoosableFileFilter(filter);

        fileDialog.setSelectedFile(new File("out.png"));
        fileDialog.showSaveDialog(null);

        File f = fileDialog.getSelectedFile();
        return f;
    }

    @Override
    public Dimension askDrawingSize() {
        MainMenu.NewDrawingDialog diag = new MainMenu.NewDrawingDialog();
        Dimension size = diag.getNewSize();
        System.out.println(size);
        return size;
    }
}
