package nextstep.subway.domain;

import nextstep.subway.domain.exception.SubwayException;

import javax.persistence.*;

@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "up_station_id")
    private Station upStation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "down_station_id")
    private Station downStation;

    private int distance;

    protected Section() {
    }

    public Section(Station upStation, Station downStation, int distance) {
        this(null, upStation, downStation, distance);
    }

    public Section(Line line, Station upStation, Station downStation, int distance) {
        if (upStation == downStation) {
            throw new SubwayException("상행역과 하행역은 같을 수 없습니다.");
        }

        if (distance <= 0) {
            throw new SubwayException("역과 역 사이의 길이는 0 이상이어야 합니다.");
        }

        this.line = line;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public Section up(Section newSection) {
        return new Section(line,
                upStation,
                newSection.upStation,
                distance - newSection.distance);
    }

    public Section down(Section newSection) {
        return new Section(line,
                newSection.downStation,
                downStation,
                distance - newSection.distance);
    }

    public Long getId() {
        return id;
    }

    public Line getLine() {
        return line;
    }

    public Station getUpStation() {
        return upStation;
    }

    public Station getDownStation() {
        return downStation;
    }

    public int getDistance() {
        return distance;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public boolean isDownStationId(long stationId) {
        return downStation.getId() == stationId;
    }

    public boolean isSameStations(Section other) {
        return upStation == other.upStation && downStation == other.downStation;
    }

    public boolean isSameUpStation(Section other) {
        return upStation == other.upStation;
    }

    public boolean isSameDownStation(Section other) {
        return downStation == other.downStation;
    }
}
