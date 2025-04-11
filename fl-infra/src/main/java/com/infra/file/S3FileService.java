package com.infra.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.infra.exception.custom.S3UploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class S3FileService implements FileService {
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public S3FileService(final AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public String toUrls(final MultipartFile file) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyyMMdd");
        String createdDate = now.format(dateTimeFormatter);
        String uuid = UUID.randomUUID().toString();
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        return createdDate + File.separator + uuid + ext;
    }

    @Async
    @Override
    public void fileUpload(final MultipartFile file, final String fileName) {
        try {
            InputStream inputStream = file.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(bucketName, fileName, inputStream, metadata);
        } catch (IOException e) {
            throw new S3UploadException(e.getMessage());
        }
    }

    @Override
    @Async
    public void deleteFile(final String fileName) {
        try {
            amazonS3.deleteObject(bucketName, fileName);
        } catch (Exception e) {
            throw new S3UploadException(e.getMessage());
        }
    }
}
