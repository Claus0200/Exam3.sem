package org.example.controllers;

import io.javalin.http.Context;
import org.example.dtos.PackagingDTO;
import org.example.enums.Category;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ITripController {

    void addGuideToTrip(Context ctx);

    void populate(Context ctx);

    void sortByCategory(Context ctx);

    void getTotalPriceOfGuides(Context ctx);

    List<PackagingDTO> getPackaging(Category category);

    void getTotalPriceOfPacking(Context ctx);
}
