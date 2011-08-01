/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorValueAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

@SuppressWarnings("nls")
public class DiscriminatorValueTests extends JpaJavaResourceModelTestCase {

	public DiscriminatorValueTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestDiscriminatorValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.DISCRIMINATOR_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@DiscriminatorValue");
			}
		});
	}
	
	private ICompilationUnit createTestDiscriminatorValueWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.DISCRIMINATOR_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@DiscriminatorValue(value = \"discriminator\")");
			}
		});
	}

	public void testDiscriminatorValue() throws Exception {
		ICompilationUnit cu = this.createTestDiscriminatorValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
	
		DiscriminatorValueAnnotation discriminatorValue = (DiscriminatorValueAnnotation) resourceType.getAnnotation(JPA.DISCRIMINATOR_VALUE);
		assertNotNull(discriminatorValue);
	}
	
	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestDiscriminatorValueWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		DiscriminatorValueAnnotation discriminatorValue = (DiscriminatorValueAnnotation) resourceType.getAnnotation(JPA.DISCRIMINATOR_VALUE);
		assertEquals("discriminator", discriminatorValue.getValue());
	}
	
	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestDiscriminatorValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		
		DiscriminatorValueAnnotation discriminatorValue = (DiscriminatorValueAnnotation) resourceType.getAnnotation(JPA.DISCRIMINATOR_VALUE);

		discriminatorValue.setValue("foo");
		
		assertSourceContains("@DiscriminatorValue(\"foo\")", cu);
		
		discriminatorValue.setValue(null);
		
		assertSourceDoesNotContain("@DiscriminatorValue(", cu);
	}

}
