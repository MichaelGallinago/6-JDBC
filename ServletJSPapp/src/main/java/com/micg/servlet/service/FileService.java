package com.micg.servlet.service;

import com.micg.servlet.model.FileSystemItem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileService {

    public static final String userDirectoriesPath = "C:\\Users\\micha\\fileManager\\";

    private static List<FileSystemItem> fileSystemElementsInCurrentDir = new ArrayList<>();
    private static String currentDirectoryPath = "";

    public static List<FileSystemItem> GetItemsFromDirectory(String directory) {
        currentDirectoryPath = directory;
        fileSystemElementsInCurrentDir = new ArrayList<>();

        File currentDirectory = new File(currentDirectoryPath);
        File[] itemsData = currentDirectory.listFiles();

        if (itemsData != null) {
            Arrays.stream(itemsData).forEach(FileService::GetItem);
        }
        return fileSystemElementsInCurrentDir;
    }

    private static void GetItem(File itemData) {
        fileSystemElementsInCurrentDir.add(itemData.isFile() ? GetFile(itemData) : GetDirectory(itemData));
    }

    private static FileSystemItem GetFile(File fileData) {
        String currentFileSize = "";
        String currentFileModificationDate = "";

        try {
            var attributes = Files.readAttributes(Paths.get(fileData.getPath()), BasicFileAttributes.class);
            long createDate = attributes.creationTime().toMillis();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy, hh:mm:ss a");
            currentFileModificationDate = dateFormat.format(createDate);
            currentFileSize = String.valueOf(attributes.size());
        } catch (java.io.IOException exception) {
            exception.printStackTrace();
        }

        return new com.micg.servlet.model.File(
                fileData.getName(),
                currentDirectoryPath,
                currentFileSize,
                currentFileModificationDate);
    }

    private static FileSystemItem GetDirectory(File directoryData) {
        String currentDirModificationDate = "";

        try {
            var attributes = Files.readAttributes(Paths.get(directoryData.getPath()), BasicFileAttributes.class);
            long createDate = attributes.creationTime().toMillis();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy, hh:mm:ss a");
            currentDirModificationDate = dateFormat.format(createDate);
        } catch (java.io.IOException exception) {
            exception.printStackTrace();
        }

        return new com.micg.servlet.model.Directory(
                directoryData.getName(),
                currentDirectoryPath,
                currentDirModificationDate);
    }

    public static void createDirectory(String path) throws IOException {
        File directory = new File(path);
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                throw new IOException("Не удалось создать директорию по этому пути");
            }
        }
    }
}
