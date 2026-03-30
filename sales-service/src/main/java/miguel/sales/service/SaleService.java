package miguel.sales.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import miguel.sales.dto.SaleRequest;
import miguel.sales.model.Invoice;
import miguel.sales.model.Sale;
import miguel.sales.model.SaleItem;
import miguel.sales.repository.InvoiceRepository;
import miguel.sales.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final InvoiceRepository invoiceRepository;

    @Transactional
    public Sale createSale(SaleRequest request) {
        Sale sale = new Sale();
        sale.setCustomerId(request.getCustomerId());
        sale.setSaleDate(LocalDateTime.now());

        List<SaleItem> items = request.getItems().stream().map(itemRequest -> {
            SaleItem item = SaleItem.builder()
                    .productId(itemRequest.getProductId())
                    .productName(itemRequest.getProductName())
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(itemRequest.getPrice())
                    .subTotal(itemRequest.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())))
                    .sale(sale)
                    .build();
            return item;
        }).collect(Collectors.toList());

        sale.setItems(items);
        BigDecimal total = items.stream()
                .map(SaleItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        sale.setTotalAmount(total);

        Sale savedSale = saleRepository.save(sale);

        // Generate Invoice Record
        Invoice invoice = Invoice.builder()
                .invoiceNumber(UUID.randomUUID().toString())
                .issuedAt(LocalDateTime.now())
                .sale(savedSale)
                .build();
        invoiceRepository.save(invoice);

        return savedSale;
    }

    public Sale getSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }
}
