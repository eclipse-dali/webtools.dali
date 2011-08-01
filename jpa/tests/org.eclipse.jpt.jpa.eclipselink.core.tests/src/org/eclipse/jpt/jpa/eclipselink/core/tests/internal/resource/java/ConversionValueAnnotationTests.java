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
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkObjectTypeConverterAnnotation;

@SuppressWarnings("nls")
public class ConversionValueAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public ConversionValueAnnotationTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestObjectTypeConverter() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.OBJECT_TYPE_CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ObjectTypeConverter");
			}
		});
	}	
	
	private ICompilationUnit createTestObjectTypeConverterWithConversionValues() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.OBJECT_TYPE_CONVERTER, EclipseLink.CONVERSION_VALUE);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ObjectTypeConverter(conversionValues = {@ConversionValue(dataValue = \"F\", objectValue = \"Female\"), @ConversionValue(dataValue = \"M\", objectValue = \"Male\")})");
			}
		});
	}


	public void testGetDataValue() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithConversionValues();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals("F", converter.conversionValueAt(0).getDataValue());
	}

	public void testSetDataValue() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithConversionValues();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals("F", converter.conversionValueAt(0).getDataValue());
		
		converter.conversionValueAt(0).setDataValue("FOO");
		assertEquals("FOO", converter.conversionValueAt(0).getDataValue());
		
		assertSourceContains("@ObjectTypeConverter(conversionValues = {@ConversionValue(dataValue = \"FOO\", objectValue = \"Female\"), @ConversionValue(dataValue = \"M\", objectValue = \"Male\")})", cu);
	}
	
	public void testSetDataValueNull() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverter();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals(0, converter.getConversionValuesSize());
		
		converter.addConversionValue(0).setDataValue("FOO");
		assertSourceContains("@ObjectTypeConverter(conversionValues = @ConversionValue(dataValue = \"FOO\"))", cu);
		
		converter.conversionValueAt(0).setDataValue(null);
		assertSourceContains("@ObjectTypeConverter(conversionValues = @ConversionValue)", cu);
		assertEquals(1, converter.getConversionValuesSize());
	}
	
	public void testGetObjectValue() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithConversionValues();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals("Female", converter.conversionValueAt(0).getObjectValue());
	}

	public void testSetObjectValue() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithConversionValues();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals("Female", converter.conversionValueAt(0).getObjectValue());
		
		converter.conversionValueAt(0).setObjectValue("FOO");
		assertEquals("FOO", converter.conversionValueAt(0).getObjectValue());
		
		assertSourceContains("@ObjectTypeConverter(conversionValues = {@ConversionValue(dataValue = \"F\", objectValue = \"FOO\"), @ConversionValue(dataValue = \"M\", objectValue = \"Male\")})", cu);
	}
	
	public void testSetObjectValueNull() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverter();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals(0, converter.getConversionValuesSize());
		
		converter.addConversionValue(0).setObjectValue("FOO");
		assertSourceContains("@ObjectTypeConverter(conversionValues = @ConversionValue(objectValue = \"FOO\"))", cu);
		
		converter.conversionValueAt(0).setObjectValue(null);
		assertSourceContains("@ObjectTypeConverter(conversionValues = @ConversionValue)", cu);
		assertEquals(1, converter.getConversionValuesSize());
	}
}
