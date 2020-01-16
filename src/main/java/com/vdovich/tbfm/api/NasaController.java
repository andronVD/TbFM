package com.vdovich.tbfm.api;

import com.vdovich.tbfm.util.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vdovich.tbfm.service.INasaService;

import java.net.MalformedURLException;


@RestController
@RequestMapping("/nasa")
public class NasaController {

    @Autowired
    private INasaService nasaService;

    @GetMapping("/pictureOfTheDay")
    public String callNasaApi() throws MalformedURLException {
        return nasaService.sendPicture(nasaService.getPictureOfTheDay(), JsonProperty.URL);
    }

    @GetMapping("/fullpicture")
    public void savePicture() {
        nasaService.savePicture();
    }

    @GetMapping("/mars")
    public String getPictureFromMars() throws MalformedURLException {
        return nasaService.sendPicture(nasaService.getPictureFromMars(), JsonProperty.IMG_SRC);
    }
}
