/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConvertAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class ConvertAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public ConvertAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestConvert() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.CONVERT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert");
			}
		});
	}
	
	private ICompilationUnit createTestConverterWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.CONVERT);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert(value=\"myConverter\")");
			}
		});
	}

	public void testConvertAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestConvert();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertNotNull(attributeResource.getSupportingAnnotation(EclipseLinkJPA.CONVERT));
		
		attributeResource.removeSupportingAnnotation(EclipseLinkJPA.CONVERT);
		assertNull(attributeResource.getSupportingAnnotation(EclipseLinkJPA.CONVERT));
		
		attributeResource.addSupportingAnnotation(EclipseLinkJPA.CONVERT);
		assertNotNull(attributeResource.getSupportingAnnotation(EclipseLinkJPA.CONVERT));
	}

	public void testGetValue() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkConvertAnnotation convert = (EclipseLinkConvertAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.CONVERT);
		assertEquals("myConverter", convert.getValue());
	}

	public void testSetValue() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkConvertAnnotation convert = (EclipseLinkConvertAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.CONVERT);
		assertEquals("myConverter", convert.getValue());
		
		convert.setValue("Bar");
		assertEquals("Bar", convert.getValue());
		
		assertSourceContains("@Convert(value=\"Bar\")", cu);
	}
	
	public void testSetValueNull() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkConvertAnnotation convert = (EclipseLinkConvertAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.CONVERT);
		assertEquals("myConverter", convert.getValue());
		
		convert.setValue(null);
		assertNull(convert.getValue());
		
		assertSourceContains("@Convert", cu);
		assertSourceDoesNotContain("value", cu);
	}

}
