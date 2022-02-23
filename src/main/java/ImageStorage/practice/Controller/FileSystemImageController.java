package ImageStorage.practice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ImageStorage.practice.Service.FileLocationService;

@Controller
@RequestMapping("/file-system")
public class FileSystemImageController {

	@Autowired
    FileLocationService fileLocationService;

	//MultipartFile: ファイルを受け取る
    @PostMapping("/image")
    @ResponseBody
    Long uploadImage(@RequestParam MultipartFile image) throws Exception {
        return fileLocationService.save(image.getBytes(), image.getOriginalFilename());
    }

    //@RequestMapping(produces): レスポンスで返すリソースを宣言できる
    @GetMapping(value = "/fileimage/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    FileSystemResource downloadImage(@PathVariable Long imageId) throws Exception {
        return fileLocationService.find(imageId);
    }
    
    @GetMapping("/showimage/{imageId}")
    String showImage(@PathVariable Long imageId, Model model) throws Exception {
    	StringBuffer base64 = fileLocationService.findBase64(imageId);
    	model.addAttribute("base64image",base64);
    	return "main";
    }
}
