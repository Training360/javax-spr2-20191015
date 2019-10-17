package empapp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address {

    private String city;

    public Address(String city) {
        this.city = city;
    }
}
