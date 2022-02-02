package com.prlhspt.market.web.dto;

import com.prlhspt.market.domain.Item.Album;
import com.prlhspt.market.domain.Item.Book;
import com.prlhspt.market.domain.Item.Item;
import com.prlhspt.market.domain.auth.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumRequestDto {

    @NotNull
    private String name;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private int price;

    @NotNull
    @Max(9999)
    private int stockQuantity;

    @NotNull
    private String artist;

    private String etc;

    public Album toAlbum(AlbumRequestDto albumRequestDto) {
        return Album.builder()
                .name(albumRequestDto.getName())
                .price(albumRequestDto.getPrice())
                .stockQuantity(albumRequestDto.getStockQuantity())
                .artist(albumRequestDto.getArtist())
                .etc(albumRequestDto.getEtc())
                .build();
    }

}
