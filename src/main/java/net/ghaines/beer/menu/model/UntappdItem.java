package net.ghaines.beer.menu.model;

import java.util.Date;

public record UntappdItem(Date created_at, UntappdUser user, UntappdBeer beer, UntappdBrewery brewery) {
}
