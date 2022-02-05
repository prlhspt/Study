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

    @NotNull(message = "이름은 공백으로 전송할 수 없습니다")
    private String name;

    @NotNull(message = "가격은 공백으로 전송할 수 없습니다")
    @Range(min = 1000, max = 1000000, message = "가격은 1000 이상 1000000 이하로 전송해야 합니다")
    private int price;

    @NotNull
    @Max(value = 9999, message = "재고는 9999 이하로 전송해야 합니다")
    private int stockQuantity;

    @NotNull(message = "작가는 공백으로 전송할 수 없습니다")
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
