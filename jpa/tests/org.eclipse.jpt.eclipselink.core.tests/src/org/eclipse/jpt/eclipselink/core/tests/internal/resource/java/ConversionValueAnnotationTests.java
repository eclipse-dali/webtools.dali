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
import org.eclipse.jpt.eclipselink.core.resource.java.ObjectTypeConverterAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class ConversionValueAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public ConversionValueAnnotationTests(String name) {
		super(name);
	}

	private void createObjectTypeConverterAnnotation() throws Exception {
		createConversionValueAnnotation();
		this.createAnnotationAndMembers("ObjectTypeConverter", 
			"String name(); " +
			"Class dataType() default void.class; " +
			"Class objectType() default void.class; " +
			"ConversionValue[] conversionValues(); " +
			"String defaultObjectValue() default \"\"");		
	}
	
	private void createConversionValueAnnotation() throws Exception {
		this.createAnnotationAndMembers("ConversionValue", 
			" String dataValue(); " +
			" String objectValue(); ");		
	}
	
	private ICompilationUnit createTestObjectTypeConverter() throws Exception {
		createObjectTypeConverterAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.OBJECT_TYPE_CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ObjectTypeConverter");
			}
		});
	}	
	
	private ICompilationUnit createTestObjectTypeConverterWithConversionValues() throws Exception {
		createObjectTypeConverterAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLinkJPA.OBJECT_TYPE_CONVERTER, EclipseLinkJPA.CONVERSION_VALUE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ObjectTypeConverter(conversionValues = {@ConversionValue(dataValue=\"F\", objectValue = \"Female\"), @ConversionValue(dataValue=\"M\", objectValue = \"Male\")})");
			}
		});
	}


	public void testGetDataValue() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithConversionValues();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ObjectTypeConverterAnnotation converter = (ObjectTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.OBJECT_TYPE_CONVERTER);
		assertEquals("F", converter.conversionValueAt(0).getDataValue());
	}

	public void testSetDataValue() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithConversionValues();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ObjectTypeConverterAnnotation converter = (ObjectTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.OBJECT_TYPE_CONVERTER);
		assertEquals("F", converter.conversionValueAt(0).getDataValue());
		
		converter.conversionValueAt(0).setDataValue("FOO");
		assertEquals("FOO", converter.conversionValueAt(0).getDataValue());
		
		assertSourceContains("@ObjectTypeConverter(conversionValues = {@ConversionValue(dataValue=\"FOO\", objectValue = \"Female\"), @ConversionValue(dataValue=\"M\", objectValue = \"Male\")})", cu);
	}
	
	public void testSetDataValueNull() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverter();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ObjectTypeConverterAnnotation converter = (ObjectTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.OBJECT_TYPE_CONVERTER);
		assertEquals(0, converter.conversionValuesSize());
		
		converter.addConversionValue(0).setDataValue("FOO");
		assertSourceContains("@ObjectTypeConverter(conversionValues=@ConversionValue(dataValue=\"FOO\"))", cu);
		
		converter.conversionValueAt(0).setDataValue(null);
		assertSourceContains("@ObjectTypeConverter(conversionValues=@ConversionValue)", cu);
		assertEquals(1, converter.conversionValuesSize());
	}
	
	public void testGetObjectValue() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithConversionValues();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ObjectTypeConverterAnnotation converter = (ObjectTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.OBJECT_TYPE_CONVERTER);
		assertEquals("Female", converter.conversionValueAt(0).getObjectValue());
	}

	public void testSetObjectValue() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithConversionValues();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ObjectTypeConverterAnnotation converter = (ObjectTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.OBJECT_TYPE_CONVERTER);
		assertEquals("Female", converter.conversionValueAt(0).getObjectValue());
		
		converter.conversionValueAt(0).setObjectValue("FOO");
		assertEquals("FOO", converter.conversionValueAt(0).getObjectValue());
		
		assertSourceContains("@ObjectTypeConverter(conversionValues = {@ConversionValue(dataValue=\"F\", objectValue = \"FOO\"), @ConversionValue(dataValue=\"M\", objectValue = \"Male\")})", cu);
	}
	
	public void testSetObjectValueNull() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverter();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu);
		JavaResourcePersistentAttribute attributeResource = typeResource.fields().next();
		
		ObjectTypeConverterAnnotation converter = (ObjectTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkJPA.OBJECT_TYPE_CONVERTER);
		assertEquals(0, converter.conversionValuesSize());
		
		converter.addConversionValue(0).setObjectValue("FOO");
		assertSourceContains("@ObjectTypeConverter(conversionValues=@ConversionValue(objectValue=\"FOO\"))", cu);
		
		converter.conversionValueAt(0).setObjectValue(null);
		assertSourceContains("@ObjectTypeConverter(conversionValues=@ConversionValue)", cu);
		assertEquals(1, converter.conversionValuesSize());
	}
}
