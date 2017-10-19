package it.pkg.dao;

import it.pkg.model.Picture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumDaoMemImpl implements AlbumDao {

    private Map<Integer, Picture> pictureMap = new HashMap<>();
    private int pictureCounter = 0;

    @Override
    public Picture addPicture(Picture picture) {
        picture.setPictureId(pictureCounter);
        pictureCounter++;
        pictureMap.put(picture.getPictureId(), picture);
        return picture;
    }

    @Override
    public void deletePicture(int pictureId) {
        pictureMap.remove(pictureId);
    }

    @Override
    public Picture getPictureById(int pictureId) {
        return pictureMap.get(pictureId);
    }

    @Override
    public List<Picture> getAllPictures() {
        List<Picture> pictureList = new ArrayList<>(pictureMap.values());
        return pictureList;
    }

}
