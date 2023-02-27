package net.ghaines.beer.menu.Untappd;

import java.util.Date;

public record UntappdItem(Date created_at, UntappdUser user, UntappdBeer beer, UntappdBrewery brewery) {
}
