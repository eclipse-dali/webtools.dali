/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
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
