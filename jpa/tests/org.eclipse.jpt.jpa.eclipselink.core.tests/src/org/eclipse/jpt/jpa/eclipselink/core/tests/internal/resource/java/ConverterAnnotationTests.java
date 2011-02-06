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
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkConverterAnnotation;

@SuppressWarnings("nls")
public class ConverterAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public ConverterAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestConverter() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Converter");
			}
		});
	}
	
	private ICompilationUnit createTestConverterWithConverterClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Converter(converterClass=Foo.class)");
			}
		});
	}
	
	private ICompilationUnit createTestConverterWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.CONVERTER);
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
		
		assertNotNull(attributeResource.getAnnotation(EclipseLink.CONVERTER));
		
		attributeResource.removeAnnotation(EclipseLink.CONVERTER);
		assertNull(attributeResource.getAnnotation(EclipseLink.CONVERTER));
		
		attributeResource.addAnnotation(EclipseLink.CONVERTER);
		assertNotNull(attributeResource.getAnnotation(EclipseLink.CONVERTER));
	}

	public void testGetConverterClass() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithConverterClass();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkConverterAnnotation converter = (EclipseLinkConverterAnnotation) attributeResource.getAnnotation(EclipseLink.CONVERTER);
		assertEquals("Foo", converter.getConverterClass());
	}

	public void testSetConverterClass() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithConverterClass();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkConverterAnnotation converter = (EclipseLinkConverterAnnotation) attributeResource.getAnnotation(EclipseLink.CONVERTER);
		assertEquals("Foo", converter.getConverterClass());
		
		converter.setConverterClass("Bar");
		assertEquals("Bar", converter.getConverterClass());
		
		assertSourceContains("@Converter(converterClass=Bar.class)", cu);
	}
	
	public void testSetConverterClassNull() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithConverterClass();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkConverterAnnotation converter = (EclipseLinkConverterAnnotation) attributeResource.getAnnotation(EclipseLink.CONVERTER);
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
		
		EclipseLinkConverterAnnotation converter = (EclipseLinkConverterAnnotation) attributeResource.getAnnotation(EclipseLink.CONVERTER);
		assertEquals("bar", converter.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkConverterAnnotation converter = (EclipseLinkConverterAnnotation) attributeResource.getAnnotation(EclipseLink.CONVERTER);
		assertEquals("bar", converter.getName());
		
		converter.setName("foo");
		assertEquals("foo", converter.getName());
		
		assertSourceContains("@Converter(name=\"foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		EclipseLinkConverterAnnotation converter = (EclipseLinkConverterAnnotation) attributeResource.getAnnotation(EclipseLink.CONVERTER);
		assertEquals("bar", converter.getName());
		
		converter.setName(null);
		assertNull(converter.getName());
		
		assertSourceContains("@Converter", cu);
		assertSourceDoesNotContain("name=", cu);
	}
}
