package io.github.nuvemazul.backend.infra;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CloudinaryImageStoreService implements ImageStorageService {

    @Override
    public String url(MultipartFile image) {
        System.out.println("Uploading image to Cloudinary: " + image.getOriginalFilename());
        return "https://cloudinary.com/fake-image-url";
    }
}
