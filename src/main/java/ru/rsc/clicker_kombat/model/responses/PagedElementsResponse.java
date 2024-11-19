package ru.rsc.clicker_kombat.model.responses;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
@AllArgsConstructor
public class PagedElementsResponse {
    List<?> elements;
    PageInfo page;

    public PagedElementsResponse(Page<?> page) {
        this(page.toList(), PageInfo.fromPage(page));
    }

    public record PageInfo(int number, int size, long totalElements, int totalPages) {
        public static PageInfo fromPage(Page<?> page) {
            return new PageInfo(page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
        }
    }
}

