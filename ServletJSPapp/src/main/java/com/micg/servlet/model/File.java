package com.micg.servlet.model;

public record File(String name, String path, String size, String date) implements FileSystemItem {

    @Override
    public String size() {
        return size + " B";
    }

    @Override
    public String path() {
        return path + "/" + name;
    }
}
