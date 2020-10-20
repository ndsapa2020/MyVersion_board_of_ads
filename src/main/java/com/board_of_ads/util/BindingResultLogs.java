package com.board_of_ads.util;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class BindingResultLogs {

    public boolean checkUserFields (BindingResult bindingResult, Logger logger) {
            if (bindingResult.hasErrors()) {
                logAllErrors(bindingResult, logger);
                return false;
            }
            return true;
    }

    private void logAllErrors (BindingResult bindingResult, Logger logger) {
        bindingResult
                .getFieldErrors()
                .forEach(fieldError -> logger.warn(fieldError.getField() + ": " + fieldError.getDefaultMessage()));
    }

}
