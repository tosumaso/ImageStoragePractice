package ImageStorage.practice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ImageStorage.practice.Entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long>{

}
