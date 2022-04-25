package egger.software.graphqlexamples.entity;

public class Flight {
    private final Long id;
    private final String number;
    private final String from;
    private final String to;

    public Flight(Long id, String number, String from, String to) {
        this.id = id;
        this.number = number;
        this.from = from;
        this.to = to;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Flight copy() {
        return copy(null, null, null, null);
    }

    public Flight copy(Long newId, String newNumber, String newFrom, String newTo) {
        Long id = newId != null ? newId : this.id;
        String number = newNumber != null ? newNumber : this.number;
        String from = newFrom != null ? newFrom : this.from;
        String to = newTo != null ? newTo : this.to;
        return new Flight(id, number, from, to);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (id != null ? !id.equals(flight.id) : flight.id != null) return false;
        if (number != null ? !number.equals(flight.number) : flight.number != null) return false;
        if (from != null ? !from.equals(flight.from) : flight.from != null) return false;
        return to != null ? to.equals(flight.to) : flight.to == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
