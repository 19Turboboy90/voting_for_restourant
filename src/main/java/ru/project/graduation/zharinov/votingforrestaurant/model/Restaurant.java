package ru.project.graduation.zharinov.votingforrestaurant.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.project.graduation.zharinov.votingforrestaurant.util.validation.NoHtml;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = {"website", "name"}, name = "restaurant_unique_website")})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Restaurant extends NamedEntity {

    @NotBlank
    @Size(min = 8, max = 80)
    @NoHtml
    @Column(name = "website", nullable = false)
    private String website;

    @NotBlank
    @Size(min = 8, max = 80)
    @NoHtml
    @Column(name = "phone")
    private String phone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("dishDate DESC")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Schema(hidden = true)
    private List<Dish> menu;

    public Restaurant(Integer id, String name, String website, String phone) {
        super(id, name);
        this.website = website;
        this.phone = phone;
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.id, restaurant.name, restaurant.website, restaurant.phone);
    }
}
