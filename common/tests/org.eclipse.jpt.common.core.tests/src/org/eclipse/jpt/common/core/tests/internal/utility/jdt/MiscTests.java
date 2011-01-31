/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.tests.internal.utility.jdt;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.Signature;

@SuppressWarnings("nls")
public class MiscTests extends AnnotationTestCase {

	public MiscTests(String name) {
		super(name);
	}

	/*
	 * Signature.toString(...) returns a dot-qualified name for member types
	 */
	public void testSignature() throws Exception {
		IType hashTableType = this.getJavaProject().getJavaProject().findType("java.util.Hashtable");
		IField tableField = hashTableType.getField("table");
		String tableFieldTypeSignature = tableField.getTypeSignature();
		String tableFieldTypeName = Signature.toString(tableFieldTypeSignature);
		assertEquals(tableFieldTypeName, "java.util.Hashtable.Entry[]");
	}

	/*
	 * IType.getFullyQualifiedName() returns a dollar-qualified name for member types
	 * (but the lookup on IJavaProject takes a dot-qualified name!)
	 */
	public void testITypeName() throws Exception {
		IType hashTableEntryType = this.getJavaProject().getJavaProject().findType("java.util.Hashtable.Entry");
		assertEquals(hashTableEntryType.getFullyQualifiedName(), "java.util.Hashtable$Entry");
	}

	public void testITypeParameterizedName() throws Exception {
		IType mapType = this.getJavaProject().getJavaProject().findType("java.util.Map");
		assertEquals(mapType.getFullyQualifiedParameterizedName(), "java.util.Map<K extends java.lang.Object, V extends java.lang.Object>");
	}

}
