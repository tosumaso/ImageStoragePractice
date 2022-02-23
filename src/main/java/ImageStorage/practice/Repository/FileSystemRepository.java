package ImageStorage.practice.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

//サーバー内のファイルシステムに画像を保存したり検索する自作リポジトリ
@Repository
public class FileSystemRepository {

	//画像を保存する予定のディレクトリのパス(target/classes以下の自動生成されたファイルのパス)
	String RESOURCES_DIR = FileSystemRepository.class.getResource("/")
	        .getPath();

	    public String save(byte[] content, String imageName) throws Exception {
	    	//画像のパスが一意のパスになるように文字をつなげる
	        Path newFile = Paths.get(RESOURCES_DIR + new Date().getTime() + "-" + imageName);
	        //指定したディレクトリの親の元に新しいディレクトリを作成
	        Files.createDirectories(newFile.getParent());
	        //指定したパスに画像のバイトデータを書き込み
	        Files.write(newFile, content);
	        //絶対パスで返す
	        return newFile.toAbsolutePath()
	            .toString();
	    }
	    //FileSystemResource: FileやPathを扱うクラス
	    //指定したパスを持つFileSystemResourceを返す。パスが見つからないなら例外を投げる
	    public FileSystemResource findInFileSystem(String location) {
	        try {
	            return new FileSystemResource(Paths.get(location));
	        } catch (Exception e) {
	            // Handle access or file not found problems.
	            throw new RuntimeException();
	        }
	    }
}
