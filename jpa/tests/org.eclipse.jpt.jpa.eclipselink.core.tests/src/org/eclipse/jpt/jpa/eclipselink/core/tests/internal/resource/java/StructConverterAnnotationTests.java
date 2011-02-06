/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkStructConverterAnnotation;

@SuppressWarnings("nls")
public class StructConverterAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public StructConverterAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestStructConverter() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.STRUCT_CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@StructConverter");
			}
		});
	}
	
	private ICompilationUnit createTestStructConverterWithConverter() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.STRUCT_CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@StructConverter(converter=\"Foo\")");
			}
		});
	}
	
	private ICompilationUnit createTestStructConverterWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.STRUCT_CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@StructConverter(name=\"bar\")");
			}
		});
	}

	public void testStructConverterAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestStructConverter();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertNotNull(attributeResource.getAnnotation(EclipseLink.STRUCT_CONVERTER));
		
		attributeResource.removeAnnotation(EclipseLink.STRUCT_CONVERTER);
		assertNull(attributeResource.getAnnotation(EclipseLink.STRUCT_CONVERTER));
		
		attributeResource.addAnnotation(EclipseLink.STRUCT_CONVERTER);
		assertNotNull(attributeResource.getAnnotation(EclipseLink.STRUCT_CONVERTER));
	}

	public void testGetConverter() throws Exception {
		ICompilationUnit cu = this.createTestStructConverterWithConverter();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkStructConverterAnnotation converter = (EclipseLinkStructConverterAnnotation) attributeResource.getAnnotation(EclipseLink.STRUCT_CONVERTER);
		assertEquals("Foo", converter.getConverter());
	}

	public void testSetConverter() throws Exception {
		ICompilationUnit cu = this.createTestStructConverterWithConverter();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkStructConverterAnnotation converter = (EclipseLinkStructConverterAnnotation) attributeResource.getAnnotation(EclipseLink.STRUCT_CONVERTER);
		assertEquals("Foo", converter.getConverter());
		
		converter.setConverter("Bar");
		assertEquals("Bar", converter.getConverter());
		
		assertSourceContains("@StructConverter(converter=\"Bar\")", cu);
	}
	
	public void testSetConverterNull() throws Exception {
		ICompilationUnit cu = this.createTestStructConverterWithConverter();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkStructConverterAnnotation converter = (EclipseLinkStructConverterAnnotation) attributeResource.getAnnotation(EclipseLink.STRUCT_CONVERTER);
		assertEquals("Foo", converter.getConverter());
		
		converter.setConverter(null);
		assertNull(converter.getConverter());
		
		assertSourceContains("@StructConverter", cu);
		assertSourceDoesNotContain("converter", cu);
	}
	
	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestStructConverterWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkStructConverterAnnotation converter = (EclipseLinkStructConverterAnnotation) attributeResource.getAnnotation(EclipseLink.STRUCT_CONVERTER);
		assertEquals("bar", converter.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestStructConverterWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkStructConverterAnnotation converter = (EclipseLinkStructConverterAnnotation) attributeResource.getAnnotation(EclipseLink.STRUCT_CONVERTER);
		assertEquals("bar", converter.getName());
		
		converter.setName("foo");
		assertEquals("foo", converter.getName());
		
		assertSourceContains("@StructConverter(name=\"foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestStructConverterWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkStructConverterAnnotation converter = (EclipseLinkStructConverterAnnotation) attributeResource.getAnnotation(EclipseLink.STRUCT_CONVERTER);
		assertEquals("bar", converter.getName());
		
		converter.setName(null);
		assertNull(converter.getName());
		
		assertSourceContains("@StructConverter", cu);
		assertSourceDoesNotContain("name=", cu);
	}
}
