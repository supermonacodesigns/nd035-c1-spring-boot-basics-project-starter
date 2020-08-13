package com.udacity.jwdnd.course1.cloudstorage.services;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;


@Service
public class FileService {

    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    // POST
    public int addFile(MultipartFile file, Integer userId) throws IOException {
        return fileMapper.insertFile(new File(
                null,
                file.getOriginalFilename(),
                file.getContentType(),
                Long.toString(file.getSize()),
                userId,
                file.getBytes()));
    }

    // GET
    public List<File> getFilesByUsername(String username) {
        var files = fileMapper.getAllFiles(userMapper.getUser(username).getUserId());

        files.forEach(file -> file.setEncodedURL("data" + ":" + file.getContentType() + ";base64," + Base64.getEncoder().encodeToString(file.getFileData())));

        return files;
    }

    public File getFile(String filename) {
        return fileMapper.getFileByFilename(filename);
    }

    public boolean isDuplicate(String filename) {
        return fileMapper.getFileByFilename(filename) != null;
    }

    // DELETE
    public void deleteFile(Integer fileId) {
        fileMapper.delete(fileId);
    }
}

