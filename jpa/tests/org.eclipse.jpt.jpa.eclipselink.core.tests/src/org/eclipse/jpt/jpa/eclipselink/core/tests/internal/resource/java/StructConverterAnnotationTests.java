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
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		assertNotNull(resourceField.getAnnotation(EclipseLink.STRUCT_CONVERTER));
		
		resourceField.removeAnnotation(EclipseLink.STRUCT_CONVERTER);
		assertNull(resourceField.getAnnotation(EclipseLink.STRUCT_CONVERTER));
		
		resourceField.addAnnotation(EclipseLink.STRUCT_CONVERTER);
		assertNotNull(resourceField.getAnnotation(EclipseLink.STRUCT_CONVERTER));
	}

	public void testGetConverter() throws Exception {
		ICompilationUnit cu = this.createTestStructConverterWithConverter();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkStructConverterAnnotation converter = (EclipseLinkStructConverterAnnotation) resourceField.getAnnotation(EclipseLink.STRUCT_CONVERTER);
		assertEquals("Foo", converter.getConverter());
	}

	public void testSetConverter() throws Exception {
		ICompilationUnit cu = this.createTestStructConverterWithConverter();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkStructConverterAnnotation converter = (EclipseLinkStructConverterAnnotation) resourceField.getAnnotation(EclipseLink.STRUCT_CONVERTER);
		assertEquals("Foo", converter.getConverter());
		
		converter.setConverter("Bar");
		assertEquals("Bar", converter.getConverter());
		
		assertSourceContains("@StructConverter(converter=\"Bar\")", cu);
	}
	
	public void testSetConverterNull() throws Exception {
		ICompilationUnit cu = this.createTestStructConverterWithConverter();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkStructConverterAnnotation converter = (EclipseLinkStructConverterAnnotation) resourceField.getAnnotation(EclipseLink.STRUCT_CONVERTER);
		assertEquals("Foo", converter.getConverter());
		
		converter.setConverter(null);
		assertNull(converter.getConverter());
		
		assertSourceContains("@StructConverter", cu);
		assertSourceDoesNotContain("converter", cu);
	}
	
	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestStructConverterWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkStructConverterAnnotation converter = (EclipseLinkStructConverterAnnotation) resourceField.getAnnotation(EclipseLink.STRUCT_CONVERTER);
		assertEquals("bar", converter.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestStructConverterWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkStructConverterAnnotation converter = (EclipseLinkStructConverterAnnotation) resourceField.getAnnotation(EclipseLink.STRUCT_CONVERTER);
		assertEquals("bar", converter.getName());
		
		converter.setName("foo");
		assertEquals("foo", converter.getName());
		
		assertSourceContains("@StructConverter(name=\"foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestStructConverterWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkStructConverterAnnotation converter = (EclipseLinkStructConverterAnnotation) resourceField.getAnnotation(EclipseLink.STRUCT_CONVERTER);
		assertEquals("bar", converter.getName());
		
		converter.setName(null);
		assertNull(converter.getName());
		
		assertSourceContains("@StructConverter", cu);
		assertSourceDoesNotContain("name=", cu);
	}
}
