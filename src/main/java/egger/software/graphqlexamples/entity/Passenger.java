package egger.software.graphqlexamples.entity;

public class Passenger {
    private final Long id;
    private final String name;
    private final Long flightId;

    public Passenger(Long id, String name, Long flightId) {
        this.id = id;
        this.name = name;
        this.flightId = flightId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getFlightId() {
        return flightId;
    }

    public Passenger copy() {
        return copy(null, null, null);
    }

    public Passenger copy(Long newId, String newName, Long newFlightId) {
        Long id = newId != null ? newId : this.id;
        String name = newName != null ? newName : this.name;
        Long flightId = newFlightId != null ? newFlightId : this.flightId;
        return new Passenger(id, name, flightId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Passenger passenger = (Passenger) o;

        if (id != null ? !id.equals(passenger.id) : passenger.id != null) return false;
        if (name != null ? !name.equals(passenger.name) : passenger.name != null) return false;
        return flightId != null ? flightId.equals(passenger.flightId) : passenger.flightId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (flightId != null ? flightId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", flightId=" + flightId +
                '}';
    }
}
