package org.example.controller;

import javax.swing.*;
import java.io.File;

public class FilePicker {
    public static String selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an Avatar");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png"));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }
}
