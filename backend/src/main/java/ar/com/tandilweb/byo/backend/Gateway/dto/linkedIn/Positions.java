package ar.com.tandilweb.byo.backend.Gateway.dto.linkedIn;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Positions {
	private int _total;
	private List<PositionValue> values;
	public int get_total() {
		return _total;
	}
	public void set_total(int _total) {
		this._total = _total;
	}
	public List<PositionValue> getValues() {
		return values;
	}
	public void setValues(List<PositionValue> values) {
		this.values = values;
	}
}
