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
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkConversionValueAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkObjectTypeConverterAnnotation;

@SuppressWarnings("nls")
public class ObjectTypeConverterAnnotationTests extends EclipseLinkJavaResourceModelTestCase {
	
	public ObjectTypeConverterAnnotationTests(String name) {
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
	
	private ICompilationUnit createTestObjectTypeConverterWithDataType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.OBJECT_TYPE_CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ObjectTypeConverter(dataType=Foo.class)");
			}
		});
	}
	
	private ICompilationUnit createTestObjectTypeConverterWithObjectType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.OBJECT_TYPE_CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ObjectTypeConverter(objectType=Foo.class)");
			}
		});
	}
	
	private ICompilationUnit createTestObjectTypeConverterWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.OBJECT_TYPE_CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ObjectTypeConverter(name = \"bar\")");
			}
		});
	}
	
	private ICompilationUnit createTestObjectTypeConverterWithDefaultObjectValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink.OBJECT_TYPE_CONVERTER);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ObjectTypeConverter(defaultObjectValue = \"f\")");
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

	public void testObjectTypeConverterAnnotation() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverter();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		assertNotNull(resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER));
		
		resourceField.removeAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertNull(resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER));
		
		resourceField.addAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertNotNull(resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER));
	}

	public void testGetDataType() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithDataType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals("Foo", converter.getDataType());
	}

	public void testSetDataType() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithDataType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals("Foo", converter.getDataType());
		
		converter.setDataType("Bar");
		assertEquals("Bar", converter.getDataType());
		
		assertSourceContains("@ObjectTypeConverter(dataType=Bar.class)", cu);
		
		converter.setDataType("int");
		assertEquals("int", converter.getDataType());
		assertSourceContains("@ObjectTypeConverter(dataType=int.class)", cu);
	}
	
	public void testSetDataTypeNull() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithDataType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals("Foo", converter.getDataType());
		
		converter.setDataType(null);
		assertNull(converter.getDataType());
		
		assertSourceContains("@ObjectTypeConverter", cu);
		assertSourceDoesNotContain("dataType", cu);
	}

	public void testGetObjectType() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithObjectType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals("Foo", converter.getObjectType());
	}

	public void testSetObjectType() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithObjectType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals("Foo", converter.getObjectType());
		
		converter.setObjectType("Bar");
		assertEquals("Bar", converter.getObjectType());
		
		assertSourceContains("@ObjectTypeConverter(objectType=Bar.class)", cu);
		
		converter.setObjectType("int");
		assertEquals("int", converter.getObjectType());
		assertSourceContains("@ObjectTypeConverter(objectType=int.class)", cu);
	}
	
	public void testSetObjectTypeNull() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithObjectType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals("Foo", converter.getObjectType());
		
		converter.setObjectType(null);
		assertNull(converter.getObjectType());
		
		assertSourceContains("@ObjectTypeConverter", cu);
		assertSourceDoesNotContain("objectType", cu);
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals("bar", converter.getName());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals("bar", converter.getName());
		
		converter.setName("foo");
		assertEquals("foo", converter.getName());
		
		assertSourceContains("@ObjectTypeConverter(name = \"foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals("bar", converter.getName());
		
		converter.setName(null);
		assertNull(converter.getName());
		
		assertSourceContains("@ObjectTypeConverter", cu);
		assertSourceDoesNotContain("name=", cu);
	}
	

	public void testGetDefaultObjectValue() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithDefaultObjectValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals("f", converter.getDefaultObjectValue());
	}

	public void testSetDefaultObjectValue() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithDefaultObjectValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals("f", converter.getDefaultObjectValue());
		
		converter.setDefaultObjectValue("foo");
		assertEquals("foo", converter.getDefaultObjectValue());
		
		assertSourceContains("@ObjectTypeConverter(defaultObjectValue = \"foo\")", cu);
	}
	
	public void testSetDefaultObjectValueNull() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithDefaultObjectValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		assertEquals("f", converter.getDefaultObjectValue());
		
		converter.setDefaultObjectValue(null);
		assertNull(converter.getDefaultObjectValue());
		
		assertSourceContains("@ObjectTypeConverter", cu);
		assertSourceDoesNotContain("defaultObjectValue", cu);
	}
	
	public void testConversionValues() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		
		assertEquals(0, converter.getConversionValuesSize());
		assertFalse(converter.getConversionValues().iterator().hasNext());
	}
	
	public void testConversionValues2() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);

		
		converter.addConversionValue(0);
		converter.addConversionValue(1);
				
		assertEquals(2, converter.getConversionValuesSize());
		ListIterator<EclipseLinkConversionValueAnnotation> conversionValues = converter.getConversionValues().iterator();
		assertTrue(conversionValues.hasNext());
		assertNotNull(conversionValues.next());
		assertNotNull(conversionValues.next());
		assertFalse(conversionValues.hasNext());
	}
	
	public void testConversionValues3() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithConversionValues();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
				
		assertEquals(2, converter.getConversionValuesSize());
		ListIterator<EclipseLinkConversionValueAnnotation> conversionValues = converter.getConversionValues().iterator();
		EclipseLinkConversionValueAnnotation conversionValue = conversionValues.next();
		assertEquals("F", conversionValue.getDataValue());
		assertEquals("Female", conversionValue.getObjectValue());
		conversionValue = conversionValues.next();
		assertEquals("M", conversionValue.getDataValue());
		assertEquals("Male", conversionValue.getObjectValue());
		assertFalse(conversionValues.hasNext());
	}
	
	public void testAddConversionValue() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		
		converter.addConversionValue(0).setObjectValue("FOO");
		converter.addConversionValue(1);
		converter.addConversionValue(0).setDataValue("BAR");

		assertEquals("BAR", converter.conversionValueAt(0).getDataValue());
		assertNull(converter.conversionValueAt(0).getObjectValue());
		assertEquals("FOO", converter.conversionValueAt(1).getObjectValue());
		assertNull(converter.conversionValueAt(1).getDataValue());
		assertNull(converter.conversionValueAt(2).getDataValue());
		assertNull(converter.conversionValueAt(2).getObjectValue());

		assertSourceContains("@ObjectTypeConverter(name = \"bar\", conversionValues = {@ConversionValue(dataValue = \"BAR\"),@ConversionValue(objectValue = \"FOO\"), @ConversionValue})", cu);
	}
	
	public void testRemoveConversionValue() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithConversionValues();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		converter.addConversionValue(0).setObjectValue("FOO");
		
		Iterator<EclipseLinkConversionValueAnnotation> conversionValues = converter.getConversionValues().iterator();
		assertEquals("FOO", conversionValues.next().getObjectValue());
		assertEquals("Female", conversionValues.next().getObjectValue());
		assertEquals("Male", conversionValues.next().getObjectValue());
		assertFalse(conversionValues.hasNext());
		assertSourceContains("@ObjectTypeConverter(conversionValues = {@ConversionValue(objectValue = \"FOO\"), @ConversionValue(dataValue = \"F\", objectValue = \"Female\"), @ConversionValue(dataValue = \"M\", objectValue = \"Male\")})", cu);
		
		converter.removeConversionValue(1);
		conversionValues = converter.getConversionValues().iterator();
		assertEquals("FOO", conversionValues.next().getObjectValue());
		assertEquals("Male", conversionValues.next().getObjectValue());
		assertFalse(conversionValues.hasNext());
		assertSourceContains("@ObjectTypeConverter(conversionValues = {@ConversionValue(objectValue = \"FOO\"), @ConversionValue(dataValue = \"M\", objectValue = \"Male\")})", cu);

		converter.removeConversionValue(0);
		conversionValues = converter.getConversionValues().iterator();
		assertEquals("Male", conversionValues.next().getObjectValue());
		assertFalse(conversionValues.hasNext());
		assertSourceContains("@ObjectTypeConverter(conversionValues = @ConversionValue(dataValue = \"M\", objectValue = \"Male\"))", cu);

		
		converter.removeConversionValue(0);
		assertSourceDoesNotContain("@conversionValues", cu);
		assertSourceContains("@ObjectTypeConverter", cu);
	}
	
	public void testMoveConversionValue() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithConversionValues();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		converter.addConversionValue(0).setObjectValue("FOO");
		
		assertSourceContains("@ObjectTypeConverter(conversionValues = {@ConversionValue(objectValue = \"FOO\"), @ConversionValue(dataValue = \"F\", objectValue = \"Female\"), @ConversionValue(dataValue = \"M\", objectValue = \"Male\")})", cu);

		converter.moveConversionValue(2, 0);
		assertEquals("Female", converter.conversionValueAt(0).getObjectValue());
		assertEquals("F", converter.conversionValueAt(0).getDataValue());
		assertEquals("Male", converter.conversionValueAt(1).getObjectValue());
		assertEquals("M", converter.conversionValueAt(1).getDataValue());
		assertEquals("FOO", converter.conversionValueAt(2).getObjectValue());
		assertEquals(null, converter.conversionValueAt(2).getDataValue());
		assertEquals(3, converter.getConversionValuesSize());
		assertSourceContains("@ObjectTypeConverter(conversionValues = {@ConversionValue(dataValue = \"F\", objectValue = \"Female\"), @ConversionValue(dataValue = \"M\", objectValue = \"Male\"), @ConversionValue(objectValue = \"FOO\")})", cu);
	}
	
	public void testMoveConversionValue2() throws Exception {
		ICompilationUnit cu = this.createTestObjectTypeConverterWithConversionValues();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		JavaResourceField resourceField = CollectionTools.get(resourceType.getFields(), 0);
		
		EclipseLinkObjectTypeConverterAnnotation converter = (EclipseLinkObjectTypeConverterAnnotation) resourceField.getAnnotation(EclipseLink.OBJECT_TYPE_CONVERTER);
		converter.addConversionValue(0).setObjectValue("FOO");
		
		assertSourceContains("@ObjectTypeConverter(conversionValues = {@ConversionValue(objectValue = \"FOO\"), @ConversionValue(dataValue = \"F\", objectValue = \"Female\"), @ConversionValue(dataValue = \"M\", objectValue = \"Male\")})", cu);

		converter.moveConversionValue(0, 2);
		assertEquals("Male", converter.conversionValueAt(0).getObjectValue());
		assertEquals("M", converter.conversionValueAt(0).getDataValue());
		assertEquals("FOO", converter.conversionValueAt(1).getObjectValue());
		assertEquals(null, converter.conversionValueAt(1).getDataValue());
		assertEquals("Female", converter.conversionValueAt(2).getObjectValue());
		assertEquals("F", converter.conversionValueAt(2).getDataValue());
		assertEquals(3, converter.getConversionValuesSize());
		assertSourceContains("@ObjectTypeConverter(conversionValues = {@ConversionValue(dataValue = \"M\", objectValue = \"Male\"), @ConversionValue(objectValue = \"FOO\"), @ConversionValue(dataValue = \"F\", objectValue = \"Female\")})", cu);
	}
}
