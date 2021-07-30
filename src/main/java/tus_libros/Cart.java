package tus_libros;

/*
 * Developed by 10Pines SRL
 * License:
 * This work is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/3.0/
 * or send a letter to Creative Commons, 444 Castro Street, Suite 900, Mountain View,
 * California, 94041, USA.
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.DoubleStream;

public class Cart {

	public static final String QUANTITY_MUST_BE_STRICTLY_POSITIVE = "Quantity must be strictly positive";
	public static final String PRODUCT_MUST_BE_SOLD_BY_PUBLISHER = "Product must be sold by publisher";

	private final Map<String, Double>  catalog;

	private List<Object> products = new ArrayList<Object>();

	public Cart(Map<String, Double> aCatalog) {
		catalog = aCatalog;
	}

	public boolean isEmpty() {
		return products.isEmpty();
	}

	public boolean contains(String aProduct) {
		return products.contains(aProduct);
	}

	public void addWithQuantity(String aProduct, int aQuantity) {
		assertValidQuantity(aQuantity);
		assertValidProduct(aProduct);

		for(int i = 1; i <= aQuantity; i++) {
			products.add(aProduct);
		}
		
	}

	private void assertValidProduct(String aProduct) {
		if(!catalog.containsKey(aProduct)) {
			throw new RuntimeException(PRODUCT_MUST_BE_SOLD_BY_PUBLISHER);
		}
	}

	private void assertValidQuantity(int aQuantity) {
		if(aQuantity <= 0) {
			throw new RuntimeException(QUANTITY_MUST_BE_STRICTLY_POSITIVE);
		}
	}

	public void add(String aProduct) {
		addWithQuantity(aProduct, 1);
		
	}

	public Long count(String aProduct) {
		return products.stream().filter(product -> product.equals(aProduct) ).count();
	}

	public Integer productCount() {
		return products.size();
	}

	public Double total() {
		return productPrices().sum();
	}

	private DoubleStream productPrices() {
		return products.stream().mapToDouble(product -> catalog.get(product));
	}

	public double totalFor(String aProduct) {
		return count(aProduct) * catalog.get(aProduct);
	}
}