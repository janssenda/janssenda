package it.pkg.dao;

import it.pkg.model.Picture;

import java.util.List;

public interface AlbumDao {

    public Picture addPicture(Picture picture);

    public void deletePicture(int pictureId);

    public Picture getPictureById(int pictureId);

    public List<Picture> getAllPictures();


}
