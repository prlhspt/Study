package com.prlhspt.market.web.api;

import com.prlhspt.market.config.dto.MemberSearchCondition;
import com.prlhspt.market.domain.Order;
import com.prlhspt.market.domain.OrderItem;
import com.prlhspt.market.repository.OrderItemRepository;
import com.prlhspt.market.service.OrderService;
import com.prlhspt.market.web.dto.OrderRequestDto;
import com.prlhspt.market.web.dto.OrderResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemRepository orderItemRepository;

    @GetMapping
    public Result itemList(Principal principal) {

        MemberSearchCondition condition = MemberSearchCondition.builder()
                .username(principal.getName()).build();

        List<OrderResponseDto> result = orderService.findOrderItem(condition).stream()
                .map(oi -> new OrderResponseDto(oi))
                .collect(Collectors.toList());

        Long count = orderService.countAll(condition);

        return new Result(count, result);

    }

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
