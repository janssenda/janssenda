package com.dm.controller;

import com.dm.dao.ImageDao;
import com.dm.model.Picture;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Controller
@RequestMapping({"/"})
public class ImageController {

    ImageDao dao = new ImageDao();

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String welcomeMap() {
        return "index";
    }


    @RequestMapping(value="/addPicture", method=RequestMethod.POST)
    public String addPicture(HttpServletRequest request,
                             Model model,
                             @RequestParam("displayTitle") String displayTitle,
                             @RequestParam("picture") MultipartFile pictureFile) {

        displayTitle = request.getParameter("displayTitle");
        // only save the pictureFile if the user actually uploaded something
        if (!pictureFile.isEmpty()) {
            try {
                // we want to put the uploaded image into the
                // <pictureFolder> folder of our application. getRealPath
                // returns the full path to the directory under Tomcat
                // where we can save files.
                String pictureFolder ="/images/";
                String savePath = request
                        .getSession()
                        .getServletContext()
                        .getRealPath("/") + pictureFolder;
                File dir = new File(savePath);
                // if <pictureFolder> directory is not there,
                // go ahead and create it
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // get the filename of the uploaded file - we'll use the
                // same name on the server.
                String filename = pictureFile.getOriginalFilename();
                // transfer the contents of the uploaded pictureFile to
                // the server
                pictureFile.transferTo(new File(savePath + filename));

                // we successfully saved the pictureFile, now save a
                // Picture to the DAO
                Picture picture = new Picture();
                picture.setFilename(pictureFolder + filename);
                picture.setTitle(displayTitle);

                dao.addPicture(picture);

                // redirect to home page to redisplay the entire album
                return "index";
            } catch (Exception e) {
                // if we encounter an exception, add the error message
                // to the model and return back to the pictureFile upload
                // form page
                model.addAttribute("errorMsg", "File upload failed: " +
                        e.getMessage());
                return "index";
            }
        } else {
            // if the user didn't upload anything, add the error
            // message to the model and return back to the pictureFile
            // upload form page
            model.addAttribute("errorMsg",
                    "Please specify a non-empty file.");
            return "index";
        }

    }

}




