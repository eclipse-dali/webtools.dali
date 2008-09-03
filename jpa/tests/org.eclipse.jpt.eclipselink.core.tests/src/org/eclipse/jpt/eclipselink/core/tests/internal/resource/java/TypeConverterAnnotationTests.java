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
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.TypeConverterAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class TypeConverterAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public TypeConverterAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestTypeConverter() throws Exception {
		this.createAnnotationAndMembers("TypeConverter", "String name(); Class dataType() default void.class; Class objectType() default void.class ");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.TYPE_CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@TypeConverter");
			}
		});
	}
	
	private ICompilationUnit createTestTypeConverterWithDataType() throws Exception {
		this.createAnnotationAndMembers("TypeConverter", "String name(); Class dataType() default void.class; Class objectType() default void.class ");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.TYPE_CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@TypeConverter(dataType=Foo.class)");
			}
		});
	}
	
	private ICompilationUnit createTestTypeConverterWithObjectType() throws Exception {
		this.createAnnotationAndMembers("TypeConverter", "String name(); Class dataType() default void.class; Class objectType() default void.class ");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.TYPE_CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@TypeConverter(objectType=Foo.class)");
			}
		});
	}
	
	private ICompilationUnit createTestTypeConverterWithName() throws Exception {
		this.createAnnotationAndMembers("TypeConverter", "String name(); Class dataType() default void.class; Class objectType() default void.class ");
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.TYPE_CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@TypeConverter(name=\"bar\")");
			}
		});
	}

	public void testTypeConverterAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestTypeConverter();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		assertNotNull(attributeResource.getAnnotation(EclipseLinkJPA.TYPE_CONVERTER));
		
		attributeResource.removeAnnotation(EclipseLinkJPA.TYPE_CONVERTER);
		assertNull(attributeResource.getAnnotation(EclipseLinkJPA.TYPE_CONVERTER));
		
		attributeResource.addAnnotation(EclipseLinkJPA.TYPE_CONVERTER);
		assertNotNull(attributeResource.getAnnotation(EclipseLinkJPA.TYPE_CONVERTER));
	}

	public void testGetTypeDataType() throws Exception {
		ICompilationUnit cu = this.createTestTypeConverterWithDataType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TypeConverterAnnotation converter = (TypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.TYPE_CONVERTER);
		assertEquals("Foo", converter.getDataType());
	}

	public void testSetDataType() throws Exception {
		ICompilationUnit cu = this.createTestTypeConverterWithDataType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TypeConverterAnnotation converter = (TypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.TYPE_CONVERTER);
		assertEquals("Foo", converter.getDataType());
		
		converter.setDataType("Bar");
		assertEquals("Bar", converter.getDataType());
		
		assertSourceContains("@TypeConverter(dataType=Bar.class)", cu);
	}
	
	public void testSetDataTypeNull() throws Exception {
		ICompilationUnit cu = this.createTestTypeConverterWithDataType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TypeConverterAnnotation converter = (TypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.TYPE_CONVERTER);
		assertEquals("Foo", converter.getDataType());
		
		converter.setDataType(null);
		assertNull(converter.getDataType());
		
		assertSourceContains("@TypeConverter", cu);
		assertSourceDoesNotContain("dataType", cu);
	}

	public void testGetTypeObjectType() throws Exception {
		ICompilationUnit cu = this.createTestTypeConverterWithObjectType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TypeConverterAnnotation converter = (TypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.TYPE_CONVERTER);
		assertEquals("Foo", converter.getObjectType());
	}

	public void testSetObjectType() throws Exception {
		ICompilationUnit cu = this.createTestTypeConverterWithObjectType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TypeConverterAnnotation converter = (TypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.TYPE_CONVERTER);
		assertEquals("Foo", converter.getObjectType());
		
		converter.setObjectType("Bar");
		assertEquals("Bar", converter.getObjectType());
		
		assertSourceContains("@TypeConverter(objectType=Bar.class)", cu);
	}
	
	public void testSetObjectTypeNull() throws Exception {
		ICompilationUnit cu = this.createTestTypeConverterWithObjectType();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TypeConverterAnnotation converter = (TypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.TYPE_CONVERTER);
		assertEquals("Foo", converter.getObjectType());
		
		converter.setObjectType(null);
		assertNull(converter.getObjectType());
		
		assertSourceContains("@TypeConverter", cu);
		assertSourceDoesNotContain("objectType", cu);
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestTypeConverterWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TypeConverterAnnotation converter = (TypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.TYPE_CONVERTER);
		assertEquals("bar", converter.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestTypeConverterWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TypeConverterAnnotation converter = (TypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.TYPE_CONVERTER);
		assertEquals("bar", converter.getName());
		
		converter.setName("foo");
		assertEquals("foo", converter.getName());
		
		assertSourceContains("@TypeConverter(name=\"foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestTypeConverterWithName();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		TypeConverterAnnotation converter = (TypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.TYPE_CONVERTER);
		assertEquals("bar", converter.getName());
		
		converter.setName(null);
		assertNull(converter.getName());
		
		assertSourceContains("@TypeConverter", cu);
		assertSourceDoesNotContain("name=", cu);
	}
}
