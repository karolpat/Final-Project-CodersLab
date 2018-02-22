package pl.coderslab.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pl.coderslab.entity.Image;
import pl.coderslab.repo.ImageRepo;

@Service
public class ImageService {

	private static String UPLOADED_FOLDER = "/home/karolpat/eclipse-workspace/demo2/src/main/resources/static/storage/";
	
	@Autowired
	private ImageRepo imageRepo;
	
	public Image addNewImage(MultipartFile file) throws IOException {
		
		byte[] bytes = file.getBytes();
		Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename().replaceAll(" ", ""));
		Files.write(path, bytes);

		Image image = new Image();
		
		image.setPath("../storage/" + file.getOriginalFilename().replaceAll(" ", ""));
		imageRepo.save(image);
		
		return image;
	}
	
	public Image defaultUserImage() {
		
		Image image = new Image();
		image.setPath("../storage/default.jpg");
		imageRepo.save(image);
		return image;
	}
}
