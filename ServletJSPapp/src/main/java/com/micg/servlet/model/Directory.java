package com.micg.servlet.model;

public record Directory(String name, String path, String date) implements FileSystemItem {

    @Override
    public String name() {
        return name + "/";
    }

    @Override
    public String path() {
        return path + "/" + name;
    }

    @Override
    public String size() {
        return "";
    }
}
