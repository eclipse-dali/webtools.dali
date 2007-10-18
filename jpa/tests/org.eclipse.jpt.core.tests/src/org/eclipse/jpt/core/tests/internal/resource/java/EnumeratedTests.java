/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.resource.java.EnumType;
import org.eclipse.jpt.core.internal.resource.java.Enumerated;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class EnumeratedTests extends JavaResourceModelTestCase {

	public EnumeratedTests(String name) {
		super(name);
	}

	private IType createTestEnumerated() throws Exception {
		this.createAnnotationAndMembers("Enumerated", "EnumType value();");
		this.createEnumAndMembers("EnumType", "ORDINAL, STRING");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENUMERATED);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@Enumerated");
			}
		});
	}
	
	private IType createTestEnumeratedWithValue() throws Exception {
		this.createAnnotationAndMembers("Enumerated", "EnumType value();");
		this.createEnumAndMembers("EnumType", "ORDINAL, STRING");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENUMERATED, JPA.ENUM_TYPE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append("@Enumerated(EnumType.ORDINAL)");
			}
		});
	}

	public void testEnumerated() throws Exception {
		IType testType = this.createTestEnumerated();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		Enumerated enumerated = (Enumerated) attributeResource.annotation(JPA.ENUMERATED);
		assertNotNull(enumerated);
	}
	
	public void testGetValue() throws Exception {
		IType testType = this.createTestEnumeratedWithValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();
		
		Enumerated enumerated = (Enumerated) attributeResource.annotation(JPA.ENUMERATED);
		assertEquals(EnumType.ORDINAL, enumerated.getValue());
	}
	
	public void testSetValue() throws Exception {
		IType testType = this.createTestEnumerated();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		JavaPersistentAttributeResource attributeResource = typeResource.fields().next();

		Enumerated enumerated = (Enumerated) attributeResource.annotation(JPA.ENUMERATED);

		enumerated.setValue(EnumType.STRING);
		
		assertSourceContains("@Enumerated(STRING)");
		
		enumerated.setValue(null);
		
		assertSourceDoesNotContain("@Enumerated");
	}

}
