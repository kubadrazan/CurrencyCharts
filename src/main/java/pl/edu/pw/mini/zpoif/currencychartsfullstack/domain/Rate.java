package pl.edu.pw.mini.zpoif.currencychartsfullstack.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate {

    @JsonProperty("effectiveDate")
    private Date date;

    @JsonProperty("mid")
    private BigDecimal price;

    public Rate(Date date, BigDecimal price) {
        this.date = date;
        this.price = price;
    }

    public Rate() {
    }

    public static RateBuilder builder() {
        return new RateBuilder();
    }

    public Date getDate() {
        return this.date;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    @JsonProperty("effectiveDate")
    public void setDate(Date date) {
        this.date = date;
    }

    @JsonProperty("mid")
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Rate)) return false;
        final Rate other = (Rate) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$date = this.getDate();
        final Object other$date = other.getDate();
        if (this$date == null ? other$date != null : !this$date.equals(other$date)) return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Rate;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $date = this.getDate();
        result = result * PRIME + ($date == null ? 43 : $date.hashCode());
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        return result;
    }

    public String toString() {
        return "Rate(date=" + this.getDate() + ", price=" + this.getPrice() + ")";
    }

    public static class RateBuilder {
        private Date date;
        private BigDecimal price;

        RateBuilder() {
        }

        @JsonProperty("effectiveDate")
        public RateBuilder date(Date date) {
            this.date = date;
            return this;
        }

        @JsonProperty("mid")
        public RateBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Rate build() {
            return new Rate(this.date, this.price);
        }

        public String toString() {
            return "Rate.RateBuilder(date=" + this.date + ", price=" + this.price + ")";
        }
    }
}
