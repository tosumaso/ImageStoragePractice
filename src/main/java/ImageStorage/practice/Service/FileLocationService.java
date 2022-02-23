package ImageStorage.practice.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ImageStorage.practice.Entity.Image;
import ImageStorage.practice.Repository.FileSystemRepository;
import ImageStorage.practice.Repository.ImageRepository;

@Service
public class FileLocationService {
	
	@Autowired
    FileSystemRepository fileSystemRepository;
    @Autowired
    ImageRepository imageRepository;

    public Long save(byte[] bytes, String imageName) throws Exception {
        String location = fileSystemRepository.save(bytes, imageName);

        return imageRepository.save(new Image(imageName, location))
            .getId();
    }
    
    public FileSystemResource find(Long imageId) {
        Image image = imageRepository.findById(imageId)
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        //imageRepositoryで取得した画像のパスでファイルシステム内を検索
        return fileSystemRepository.findInFileSystem(image.getLocation());
    }
    
    public StringBuffer findBase64(Long imageId) throws Exception{
    	Image image = imageRepository.findById(imageId)
  	          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    	
    	FileSystemResource resource = fileSystemRepository.findInFileSystem(image.getLocation());
    	//ファイルシステム内の画像データをパスを指定してバイトコードに変換
    	byte[] fileContent =Files.readAllBytes(Paths.get(resource.getPath()));
    	//Base64に変換し、出力するための接頭辞を追加
    	StringBuffer data = new StringBuffer();
    	String base64 = new String(Base64.encodeBase64(fileContent));
    	data.append("data:image/jpeg;base64,");
        data.append(base64);
        return data;
    }
}
