package com.prlhspt.market.web.api;

import com.prlhspt.market.domain.Item.Album;
import com.prlhspt.market.domain.Item.Item;
import com.prlhspt.market.service.ItemService;
import com.prlhspt.market.web.dto.AlbumRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public Result itemList() throws Throwable {
        List<Item> items = itemService.findItems();
        Long count = itemService.countItem();
        return new Result(count, items);
    }

    @PostMapping("/album")
    public ResponseEntity<String> createItem(@Valid @RequestBody AlbumRequestDto albumRequestDto, BindingResult bindingResult) throws BindException {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            throw new BindException(bindingResult);
        }

        Item item = itemService.saveItem(albumRequestDto.toAlbum(albumRequestDto));

        return ResponseEntity.ok(item.getName());

    }



    @Data
    @AllArgsConstructor
    static class Result<T, U> {
        private U count;
        private T data;
    }

}
