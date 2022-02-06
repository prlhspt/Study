package com.prlhspt.market.web.api;

import com.prlhspt.market.service.OrderService;
import com.prlhspt.market.web.dto.OrderRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<Long> save(@Valid @RequestBody OrderRequestDto orderRequestDto, BindingResult bindingResult) throws Throwable {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            throw new BindException(bindingResult);
        }

        Long orderId = orderService.order(orderRequestDto.getMemberId(), orderRequestDto.getItemId(), orderRequestDto.getCount());

        return ResponseEntity.ok(orderId);

    }

    @Data
    @AllArgsConstructor
    static class Result<U, T> {
        private U count;
        private T data;
    }

}
