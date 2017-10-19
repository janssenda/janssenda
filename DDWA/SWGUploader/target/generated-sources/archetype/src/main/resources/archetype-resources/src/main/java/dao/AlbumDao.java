#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao;

import ${package}.model.Picture;

import java.util.List;

public interface AlbumDao {

    public Picture addPicture(Picture picture);

    public void deletePicture(int pictureId);

    public Picture getPictureById(int pictureId);

    public List<Picture> getAllPictures();


}
