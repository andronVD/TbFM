package com.vdovich.tbfm.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vdovich.tbfm.service.INasaService;


@RestController
@RequestMapping("/nasa")
public class NasaController {

    @Autowired
    private INasaService nasaService;

    @GetMapping("/pictureOfTheDay")
    public String callNasaApi() {
        return nasaService.getPictureOfTheDay();
    }

    @GetMapping("/fullpicture")
    public void savePicture() {
        nasaService.savePicture();
    }

    @GetMapping("/mars")
    public String getPictureFromMars() {
        return nasaService.getPictureFromMars();
    }
}
