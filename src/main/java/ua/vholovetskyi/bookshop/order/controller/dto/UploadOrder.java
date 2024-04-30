package ua.vholovetskyi.bookshop.order.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-22
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadOrder {
    private int importedRecord;
    private int nonImportedRecord;

    public void incrementCounter(boolean isImportedRecord) {
        if (isImportedRecord) {
            importedRecord++;
            return;
        }
        nonImportedRecord++;
    }
}
