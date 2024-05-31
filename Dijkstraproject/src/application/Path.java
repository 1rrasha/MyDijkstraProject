package application;

public class Path {
	private double distance;
	private String source;
	private String target;

	public Path() {
		// TODO Auto-generated constructor stub
	}

	public Path(double d, String source, String target) {
		super();
		this.distance = d;
		this.source = source;
		this.target = target;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return "PathTable [distance=" + distance + ", source=" + source + ", target=" + target + "]";
	}

}
