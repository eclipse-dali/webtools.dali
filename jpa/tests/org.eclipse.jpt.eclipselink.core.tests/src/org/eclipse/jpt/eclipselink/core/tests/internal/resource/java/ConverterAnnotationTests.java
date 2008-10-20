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
import org.eclipse.jpt.eclipselink.core.resource.java.ConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class ConverterAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public ConverterAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestConverter() throws Exception {
		this.createAnnotationAndMembers("Converter", "String name(); Class converterClass()");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Converter");
			}
		});
	}
	
	private ICompilationUnit createTestConverterWithConverterClass() throws Exception {
		this.createAnnotationAndMembers("Converter", "String name(); Class converterClass()");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Converter(converterClass=Foo.class)");
			}
		});
	}
	
	private ICompilationUnit createTestConverterWithName() throws Exception {
		this.createAnnotationAndMembers("Converter", "String name(); Class converterClass()");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Converter(name=\"bar\")");
			}
		});
	}

	public void testConverterAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestConverter();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertNotNull(attributeResource.getSupportingAnnotation(EclipseLinkJPA.CONVERTER));
		
		attributeResource.removeSupportingAnnotation(EclipseLinkJPA.CONVERTER);
		assertNull(attributeResource.getSupportingAnnotation(EclipseLinkJPA.CONVERTER));
		
		attributeResource.addSupportingAnnotation(EclipseLinkJPA.CONVERTER);
		assertNotNull(attributeResource.getSupportingAnnotation(EclipseLinkJPA.CONVERTER));
	}

	public void testGetConverterClass() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithConverterClass();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ConverterAnnotation converter = (ConverterAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.CONVERTER);
		assertEquals("Foo", converter.getConverterClass());
	}

	public void testSetConverterClass() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithConverterClass();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ConverterAnnotation converter = (ConverterAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.CONVERTER);
		assertEquals("Foo", converter.getConverterClass());
		
		converter.setConverterClass("Bar");
		assertEquals("Bar", converter.getConverterClass());
		
		assertSourceContains("@Converter(converterClass=Bar.class)", cu);
	}
	
	public void testSetConverterClassNull() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithConverterClass();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ConverterAnnotation converter = (ConverterAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.CONVERTER);
		assertEquals("Foo", converter.getConverterClass());
		
		converter.setConverterClass(null);
		assertNull(converter.getConverterClass());
		
		assertSourceContains("@Converter", cu);
		assertSourceDoesNotContain("converterClass", cu);
	}
	
	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ConverterAnnotation converter = (ConverterAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.CONVERTER);
		assertEquals("bar", converter.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ConverterAnnotation converter = (ConverterAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.CONVERTER);
		assertEquals("bar", converter.getName());
		
		converter.setName("foo");
		assertEquals("foo", converter.getName());
		
		assertSourceContains("@Converter(name=\"foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ConverterAnnotation converter = (ConverterAnnotation) attributeResource.getSupportingAnnotation(EclipseLinkJPA.CONVERTER);
		assertEquals("bar", converter.getName());
		
		converter.setName(null);
		assertNull(converter.getName());
		
		assertSourceContains("@Converter", cu);
		assertSourceDoesNotContain("name=", cu);
	}
}
