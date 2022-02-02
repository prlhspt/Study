package com.prlhspt.market.service;

import com.prlhspt.market.domain.Item.Album;
import com.prlhspt.market.domain.Item.Book;
import com.prlhspt.market.domain.Item.Item;
import com.prlhspt.market.domain.Item.Movie;
import com.prlhspt.market.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@SpringBootTest
class ItemServiceTest {

    @Autowired ItemRepository itemRepository;
    @Autowired ItemService itemService;

    @Test
    @DisplayName("아이템 저장 성공")
    void saveItem() {

        Album album = (Album) itemService.saveItem(new Album("album1", 1000, 2, "artist1", "etc1"));
        Book book = (Book) itemService.saveItem(new Book("book1", 2000, 3, "author1", "isbn1"));
        Movie movie = (Movie) itemService.saveItem(new Movie("movie1", 3000, 4, "director", "actor1"));

        Album findAlbum = (Album) itemRepository.findByName("album1").orElse(null);

        Book findBook = (Book) itemRepository.findByName("book1").orElse(null);

        Movie findMovie = (Movie) itemRepository.findByName("movie1").orElse(null);

        Assertions.assertThat(findAlbum).isEqualTo(album);
        Assertions.assertThat(findBook).isEqualTo(book);
        Assertions.assertThat(findMovie).isEqualTo(movie);

    }
}