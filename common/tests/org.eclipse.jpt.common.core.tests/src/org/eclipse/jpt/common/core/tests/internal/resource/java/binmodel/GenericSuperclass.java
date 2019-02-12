/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.tests.internal.resource.java.binmodel;

/**
 * This entire package is to be exported as binmodel.jar (in the *.java source folder).
 * If any changes are made to these classes, please re-export.
 */
public abstract class GenericSuperclass<T1, T2> {
	
	protected T1 genericField1;
	
	protected T2 genericField2;
	
	
	
	public GenericSuperclass() {
	
	}
	
	protected T1 genericMethod1() {
		return null;
	}
	
	protected T2 genericMethod2() {
		return null;
	}
	
	public boolean equals(String arg) {
		return true;
	}
	
	public int hashCode(String arg) {
		return 0;
	}
}
