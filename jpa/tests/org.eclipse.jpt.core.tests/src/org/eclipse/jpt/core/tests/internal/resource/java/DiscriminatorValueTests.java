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
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorValue;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class DiscriminatorValueTests extends JavaResourceModelTestCase {

	public DiscriminatorValueTests(String name) {
		super(name);
	}

	private IType createTestDiscriminatorValue() throws Exception {
		this.createAnnotationAndMembers("DiscriminatorValue", "String value() default \"\";");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.DISCRIMINATOR_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@DiscriminatorValue");
			}
		});
	}
	
	private IType createTestDiscriminatorValueWithValue() throws Exception {
		this.createAnnotationAndMembers("DiscriminatorValue", "String value() default \"\";");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.DISCRIMINATOR_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@DiscriminatorValue(value=\"discriminator\")");
			}
		});
	}

	public void testDiscriminatorValue() throws Exception {
		IType testType = this.createTestDiscriminatorValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
	
		DiscriminatorValue discriminatorValue = (DiscriminatorValue) typeResource.annotation(JPA.DISCRIMINATOR_VALUE);
		assertNotNull(discriminatorValue);
	}
	
	public void testGetValue() throws Exception {
		IType testType = this.createTestDiscriminatorValueWithValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		DiscriminatorValue discriminatorValue = (DiscriminatorValue) typeResource.annotation(JPA.DISCRIMINATOR_VALUE);
		assertEquals("discriminator", discriminatorValue.getValue());
	}
	
	public void testSetValue() throws Exception {
		IType testType = this.createTestDiscriminatorValue();
		JavaPersistentTypeResource typeResource = buildJavaTypeResource(testType); 
		
		DiscriminatorValue discriminatorValue = (DiscriminatorValue) typeResource.annotation(JPA.DISCRIMINATOR_VALUE);

		discriminatorValue.setValue("foo");
		
		assertSourceContains("@DiscriminatorValue(\"foo\")");
		
		discriminatorValue.setValue(null);
		
		assertSourceDoesNotContain("@DiscriminatorValue");
	}

}
