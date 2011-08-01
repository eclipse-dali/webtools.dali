/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
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
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		assertNotNull(resourceField.getAnnotation(EclipseLink.CONVERTER));
		
		resourceField.removeAnnotation(EclipseLink.CONVERTER);
		assertNull(resourceField.getAnnotation(EclipseLink.CONVERTER));
		
		resourceField.addAnnotation(EclipseLink.CONVERTER);
		assertNotNull(resourceField.getAnnotation(EclipseLink.CONVERTER));
	}

	public void testGetConverterClass() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithConverterClass();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkConverterAnnotation converter = (EclipseLinkConverterAnnotation) resourceField.getAnnotation(EclipseLink.CONVERTER);
		assertEquals("Foo", converter.getConverterClass());
	}

	public void testSetConverterClass() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithConverterClass();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkConverterAnnotation converter = (EclipseLinkConverterAnnotation) resourceField.getAnnotation(EclipseLink.CONVERTER);
		assertEquals("Foo", converter.getConverterClass());
		
		converter.setConverterClass("Bar");
		assertEquals("Bar", converter.getConverterClass());
		
		assertSourceContains("@Converter(converterClass=Bar.class)", cu);
	}
	
	public void testSetConverterClassNull() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithConverterClass();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkConverterAnnotation converter = (EclipseLinkConverterAnnotation) resourceField.getAnnotation(EclipseLink.CONVERTER);
		assertEquals("Foo", converter.getConverterClass());
		
		converter.setConverterClass(null);
		assertNull(converter.getConverterClass());
		
		assertSourceContains("@Converter", cu);
		assertSourceDoesNotContain("converterClass", cu);
	}
	
	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkConverterAnnotation converter = (EclipseLinkConverterAnnotation) resourceField.getAnnotation(EclipseLink.CONVERTER);
		assertEquals("bar", converter.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkConverterAnnotation converter = (EclipseLinkConverterAnnotation) resourceField.getAnnotation(EclipseLink.CONVERTER);
		assertEquals("bar", converter.getName());
		
		converter.setName("foo");
		assertEquals("foo", converter.getName());
		
		assertSourceContains("@Converter(name=\"foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestConverterWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkConverterAnnotation converter = (EclipseLinkConverterAnnotation) resourceField.getAnnotation(EclipseLink.CONVERTER);
		assertEquals("bar", converter.getName());
		
		converter.setName(null);
		assertNull(converter.getName());
		
		assertSourceContains("@Converter", cu);
		assertSourceDoesNotContain("name=", cu);
	}
}
