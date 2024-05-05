package application.exception.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {

    private String message;
    private String field;

    public ErrorMessage(String message) {
        this.message = message;
    }
}
