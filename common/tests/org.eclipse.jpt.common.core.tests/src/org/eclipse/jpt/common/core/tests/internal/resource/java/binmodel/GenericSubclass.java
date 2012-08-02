package org.eclipse.jpt.common.core.tests.internal.resource.java.binmodel;

/**
 * This entire package is to be exported as binmodel.jar (in the *.java source folder).
 * If any changes are made to these classes, please re-export.
 */
public class GenericSubclass<T extends Number> extends GenericSuperclass<String, T> {
	
	
	private GenericSubclass() {
		super();
	}
	
	@Deprecated
	protected String deprecatedField;
	
	@Deprecated
	protected String deprecatedMethod() {
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		return true;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
}
