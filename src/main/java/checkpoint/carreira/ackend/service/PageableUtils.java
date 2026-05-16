package checkpoint.carreira.ackend.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

public final class PageableUtils {

    private PageableUtils() {
    }

    public static Pageable withAllowedSort(Pageable pageable, Set<String> allowedProperties, String defaultProperty) {
        List<Sort.Order> validOrders = pageable.getSort()
                .stream()
                .filter(order -> allowedProperties.contains(order.getProperty()))
                .toList();

        Sort sort = validOrders.isEmpty()
                ? Sort.by(defaultProperty).ascending()
                : Sort.by(validOrders);

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }
}
