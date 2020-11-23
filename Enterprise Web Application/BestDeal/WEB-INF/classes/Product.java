import java.io.IOException;
import java.io.*;


public class Product implements Serializable{
	private String ProductType;
	private String Id;
	private String productName;
	private Double productPrice;
	private String productImage;
	private String productManufacturer;
	private String productCondition;
	private Double productDiscount;
	private Double rebateAmount;
	private Integer count;
	private Integer num_items;
	private Double total_sales;
	private String dates;


	public String getProductType() {
		return ProductType;
	}

	public void setProductType(String ProductType) {
		this.ProductType = ProductType;
	}

	public String getId() {
		return Id;
	}

	public void setId(String Id) {
		this.Id = Id;
	}

	public String getproductName() {
		return productName;
	}

	public void setproductName(String productName) {
		this.productName = productName;
	}

	public Double getproductPrice() {
		return productPrice;
	}

	public void setproductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}
	
	public String getproductImage() {
		return productImage;
	}

	public void setproductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getproductManufacturer() {
		return productManufacturer;
	}

	public void setproductManufacturer(String productManufacturer) {
		this.productManufacturer = productManufacturer;
	}

	public String getproductCondition() {
		return productCondition;
	}

	public void setproductCondition(String productCondition) {
		this.productCondition = productCondition;
	}

	public Double getproductDiscount() {
		return productDiscount;
	}

	public void setproductDiscount(Double productDiscount) {
		this.productDiscount = productDiscount;
	}

	public Double getrebateAmount() {
		return rebateAmount;
	}

	public void setrebateAmount(Double rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public int getcount() {
		return count;
	}

	public void setcount(int count) {
		this.count = count;
	}

	public int getnum_items() {
		return num_items;
	}

	public void setnum_items(int num_items) {
		this.num_items = num_items;
	}

	public Double gettotal_sales() {
		return total_sales;
	}

	public void settotal_sales(Double total_sales) {
		this.total_sales = total_sales;
	}

	public String getdates() {
		return dates;
	}

	public void setdates(String dates) {
		this.dates = dates;
	}
}
