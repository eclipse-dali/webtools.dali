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
import org.eclipse.jpt.core.resource.java.Inheritance;
import org.eclipse.jpt.core.resource.java.InheritanceType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class InheritanceTests extends JavaResourceModelTestCase {

	public InheritanceTests(String name) {
		super(name);
	}

	private IType createTestInheritance() throws Exception {
		this.createAnnotationAndMembers("Inheritance", "InheritanceType strategy() default SINGLE_TABLE;");
		this.createEnumAndMembers("InheritanceType", "SINGLE_TABLE, JOINED, TABLE_PER_CLASS");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.INHERITANCE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Inheritance");
			}
		});
	}
	
	private IType createTestInheritanceWithStrategy() throws Exception {
		this.createAnnotationAndMembers("Inheritance", "InheritanceType strategy() default SINGLE_TABLE;");
		this.createEnumAndMembers("InheritanceType", "SINGLE_TABLE, JOINED, TABLE_PER_CLASS");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.INHERITANCE, JPA.INHERITANCE_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Inheritance(strategy=InheritanceType.JOINED)");
			}
		});
	}

	public void testInheritance() throws Exception {
		IType testType = this.createTestInheritance();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		Inheritance inheritance = (Inheritance) typeResource.getAnnotation(JPA.INHERITANCE);
		assertNotNull(inheritance);
	}
	
	public void testGetStrategy() throws Exception {
		IType testType = this.createTestInheritanceWithStrategy();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 
		
		Inheritance inheritance = (Inheritance) typeResource.getAnnotation(JPA.INHERITANCE);
		assertEquals(InheritanceType.JOINED, inheritance.getStrategy());
	}
	
	public void testSetStrategy() throws Exception {
		IType testType = this.createTestInheritance();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(testType); 

		Inheritance inheritance = (Inheritance) typeResource.getAnnotation(JPA.INHERITANCE);
		inheritance.setStrategy(InheritanceType.TABLE_PER_CLASS);
		
		assertSourceContains("@Inheritance(strategy=TABLE_PER_CLASS)");
		
		inheritance.setStrategy(null);
		
		assertSourceDoesNotContain("@Inheritance");
	}

}
