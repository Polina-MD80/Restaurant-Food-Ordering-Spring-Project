package softuni.restaurant.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import softuni.restaurant.model.entity.PictureEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true)
class PictureRepositoryTest {
    private PictureEntity testPic1;
    private PictureEntity testPic2;
    @Autowired
    PictureRepository pictureRepository;

    @BeforeEach
    void setUp() {
        testPic1 = new PictureEntity().setPublicId("somePublicId1").setUrl("somePublicUrl1");
        testPic2 = new PictureEntity().setPublicId("somePublicId2").setUrl("somePublicUrl2");

        pictureRepository.saveAll(List.of(testPic1, testPic2));
    }

    @Test
    public void findAllTest() {
        List<PictureEntity> actual = pictureRepository.findAll();
        assertTrue(actual.size() > 0);
        assertTrue(actual.stream().anyMatch(pictureEntity -> pictureEntity.getId().equals(testPic1.getId())));
        assertTrue(actual.stream().anyMatch(pictureEntity -> pictureEntity.getId().equals(testPic2.getId())));
    }
@Test
    public void deletePicture(){
    Long id = testPic1.getId();
    pictureRepository.delete(testPic1);
    assertTrue(pictureRepository.findById(id).isEmpty());
    assertFalse(pictureRepository.findById(testPic2.getId()).isEmpty());
}

}