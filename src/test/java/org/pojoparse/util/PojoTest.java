package test.java.org.pojoparse.util;

public class PojoTest {

	private String name;
	private String address;
	private Integer addressNumber;

	public PojoTest(String name, String address, Integer addressNumber) {
		super();
		this.name = name;
		this.address = address;
		this.addressNumber = addressNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAddressNumber() {
		return addressNumber;
	}

	public void setAddressNumber(Integer addressNumber) {
		this.addressNumber = addressNumber;
	}

}
