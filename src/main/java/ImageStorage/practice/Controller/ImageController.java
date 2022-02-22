package ImageStorage.practice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import ImageStorage.practice.Entity.Image;
import ImageStorage.practice.Repository.ImageRepository;

@Controller
public class ImageController {

	@Autowired
    ImageRepository imageRepository;
	
	@GetMapping("/")
	public String getMain() {
		return "main";
	}
	

    @PostMapping("/postimage")
    @ResponseBody
    Long uploadImage(@RequestParam(name="image") MultipartFile multipartImage) throws Exception {
        Image dbImage = new Image();
        dbImage.setName(multipartImage.getName());
        dbImage.setContent(multipartImage.getBytes());

        return imageRepository.save(dbImage)
            .getId();
    }
    
    @GetMapping("/image/{imageId}")
    @ResponseBody
    Resource downloadImage(@PathVariable Long imageId) {
        byte[] image = imageRepository.findById(imageId)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
          .getContent();

        return new ByteArrayResource(image);
    }
}
