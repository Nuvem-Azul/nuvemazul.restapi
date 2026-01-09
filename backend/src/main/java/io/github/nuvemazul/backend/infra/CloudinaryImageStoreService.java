package io.github.nuvemazul.backend.infra;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Component
public class CloudinaryImageStoreService implements ImageStorageService {

    private Cloudinary cloudinary;

    public CloudinaryImageStoreService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String url(MultipartFile image) throws Exception {

        if (image.isEmpty()) {
            return "Imagem não enviada";
        }

        byte[] imageBytes = image.getBytes();
        Map<?, ?> UploadResult = cloudinary.uploader().upload(imageBytes, ObjectUtils.emptyMap());
        String url = (String) UploadResult.get("url");

        return url;
    }
}
