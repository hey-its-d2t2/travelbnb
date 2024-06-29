package com.travelbnb.controllers;

import com.travelbnb.entity.AppUser;
import com.travelbnb.entity.Favourite;
import com.travelbnb.entity.Property;
import com.travelbnb.repository.FavouriteRepository;
import com.travelbnb.repository.PropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/favourites")
public class FavouriteController {

    final  private FavouriteRepository favouriteRepository;
    final private PropertyRepository propertyRepository;

    public FavouriteController(FavouriteRepository favouriteRepository, PropertyRepository propertyRepository) {
        this.favouriteRepository = favouriteRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/addFavourite")
    public ResponseEntity<Favourite> addFavorite(
            @AuthenticationPrincipal AppUser user,
            @RequestParam long propertyId,
            @RequestBody Favourite favorite
    )
    {
        Property property = propertyRepository.findById(propertyId).get();
        favorite.setAppUser(user);
        favorite.setProperty(property);
        Favourite savedEntity =  favouriteRepository.save(favorite);
        return new ResponseEntity<>(savedEntity, HttpStatus.CREATED);
    }
}
