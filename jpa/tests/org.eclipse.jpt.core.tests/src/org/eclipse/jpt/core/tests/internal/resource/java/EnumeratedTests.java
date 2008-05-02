/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.java.EnumType;
import org.eclipse.jpt.core.resource.java.EnumeratedAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
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
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
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
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Enumerated(EnumType.ORDINAL)");
			}
		});
	}

	public void testEnumerated() throws Exception {
		IType testType = this.createTestEnumerated();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EnumeratedAnnotation enumerated = (EnumeratedAnnotation) attributeResource.getAnnotation(JPA.ENUMERATED);
		assertNotNull(enumerated);
	}
	
	public void testGetValue() throws Exception {
		IType testType = this.createTestEnumeratedWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EnumeratedAnnotation enumerated = (EnumeratedAnnotation) attributeResource.getAnnotation(JPA.ENUMERATED);
		assertEquals(EnumType.ORDINAL, enumerated.getValue());
	}
	
	public void testSetValue() throws Exception {
		IType testType = this.createTestEnumerated();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();

		EnumeratedAnnotation enumerated = (EnumeratedAnnotation) attributeResource.getAnnotation(JPA.ENUMERATED);

		enumerated.setValue(EnumType.STRING);
		
		assertSourceContains("@Enumerated(STRING)");
		
		enumerated.setValue(null);
		
		assertSourceDoesNotContain("@Enumerated");
	}

}
