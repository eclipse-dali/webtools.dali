/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.ConversionValue;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.ObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.ConversionValueAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.ObjectTypeConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkJavaObjectTypeConverterTests extends EclipseLinkContextModelTestCase
{
	
	private ICompilationUnit createTestEntityWithConvertAndObjectTypeConverter() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLinkJPA.CONVERT, EclipseLinkJPA.OBJECT_TYPE_CONVERTER, EclipseLinkJPA.CONVERSION_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert(\"foo\")").append(CR);
				sb.append("    @ObjectTypeConverter(name=\"foo\", defaultObjectValue=\"bar\")");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithConvertAndDataType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLinkJPA.CONVERT, EclipseLinkJPA.OBJECT_TYPE_CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert(\"foo\")").append(CR);
				sb.append("    @ObjectTypeConverter(dataType=Foo.class)");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithConvertAndObjectType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLinkJPA.CONVERT, EclipseLinkJPA.OBJECT_TYPE_CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert(\"foo\")").append(CR);
				sb.append("    @ObjectTypeConverter(objectType=Foo.class)");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithConvertAndObjectTypeConverterConversionValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLinkJPA.CONVERT, EclipseLinkJPA.OBJECT_TYPE_CONVERTER, EclipseLinkJPA.CONVERSION_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert(\"foo\")").append(CR);
				sb.append("    @ObjectTypeConverter(name=\"foo\", defaultObjectValue=\"bar\", conversionValues = @ConversionValue(dataValue=\"f\", objectValue=\"female\"))");
			}
		});
	}

	public EclipseLinkJavaObjectTypeConverterTests(String name) {
		super(name);
	}


	public void testGetName() throws Exception {
		createTestEntityWithConvertAndObjectTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();
		
		assertEquals("foo", converter.getName());
	}

	public void testSetName() throws Exception {
		createTestEntityWithConvertAndObjectTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();
		assertEquals("foo", converter.getName());
		
		converter.setName("bar");
		assertEquals("bar", converter.getName());
			
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ObjectTypeConverterAnnotation converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("bar", converterAnnotation.getName());

		
		converter.setName(null);
		assertEquals(null, converter.getName());
		converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getName());


		converter.setName("bar");
		assertEquals("bar", converter.getName());
		converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("bar", converterAnnotation.getName());
	}
	
	public void testGetNameUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndObjectTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();

		assertEquals("foo", converter.getName());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ObjectTypeConverterAnnotation converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setName("bar");
		assertEquals("bar", converter.getName());
		
		attributeResource.removeSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);
		assertEquals(null, eclipseLinkConvert.getConverter());
		
		converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.addSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);		
		assertNotNull(eclipseLinkConvert.getConverter());
		
		converterAnnotation.setName("FOO");
		assertEquals("FOO", eclipseLinkConvert.getConverter().getName());	
	}

	public void testGetDataType() throws Exception {
		createTestEntityWithConvertAndDataType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();
		
		assertEquals("Foo", converter.getDataType());
	}

	public void testSetDataType() throws Exception {
		createTestEntityWithConvertAndDataType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();
		assertEquals("Foo", converter.getDataType());
		
		converter.setDataType("Bar");
		assertEquals("Bar", converter.getDataType());
			
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ObjectTypeConverterAnnotation converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getDataType());

		
		converter.setDataType(null);
		assertEquals(null, converter.getDataType());
		converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getDataType());


		converter.setDataType("Bar");
		assertEquals("Bar", converter.getDataType());
		converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getDataType());
	}
	
	public void testGetDataTypeUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndDataType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();

		assertEquals("Foo", converter.getDataType());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ObjectTypeConverterAnnotation converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setDataType("Bar");
		assertEquals("Bar", converter.getDataType());
		
		attributeResource.removeSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);
		assertEquals(null, eclipseLinkConvert.getConverter());
		
		converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.addSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);		
		assertNotNull(eclipseLinkConvert.getConverter());
		
		converterAnnotation.setDataType("FooBar");
		assertEquals("FooBar", ((ObjectTypeConverter) eclipseLinkConvert.getConverter()).getDataType());	
	}
	
	public void testGetObjectType() throws Exception {
		createTestEntityWithConvertAndObjectType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();
		
		assertEquals("Foo", converter.getObjectType());
	}

	public void testSetObjectType() throws Exception {
		createTestEntityWithConvertAndObjectType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();
		assertEquals("Foo", converter.getObjectType());
		
		converter.setObjectType("Bar");
		assertEquals("Bar", converter.getObjectType());
			
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ObjectTypeConverterAnnotation converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getObjectType());

		
		converter.setObjectType(null);
		assertEquals(null, converter.getObjectType());
		converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getObjectType());


		converter.setObjectType("Bar");
		assertEquals("Bar", converter.getObjectType());
		converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getObjectType());
	}
	
	public void testGetObjectTypeUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndObjectType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();

		assertEquals("Foo", converter.getObjectType());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ObjectTypeConverterAnnotation converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setObjectType("Bar");
		assertEquals("Bar", converter.getObjectType());
		
		attributeResource.removeSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);
		assertEquals(null, eclipseLinkConvert.getConverter());
		
		converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.addSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);		
		assertNotNull(eclipseLinkConvert.getConverter());
		
		converterAnnotation.setObjectType("FooBar");
		assertEquals("FooBar", ((ObjectTypeConverter) eclipseLinkConvert.getConverter()).getObjectType());	
	}
	
	
	public void testAddConversionValue() throws Exception {
		createTestEntityWithConvertAndObjectTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ObjectTypeConverterAnnotation converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);
		
		ConversionValue conversionValue = converter.addConversionValue(0);
		conversionValue.setDataValue("F");
		conversionValue.setObjectValue("female");
		
		ListIterator<ConversionValueAnnotation> resourceConversionValues = converterAnnotation.conversionValues();
		ConversionValueAnnotation resourceConversionValue = resourceConversionValues.next();
		assertEquals("F", resourceConversionValue.getDataValue());
		assertEquals("female", resourceConversionValue.getObjectValue());
		
		ConversionValue conversionValue2 = converter.addConversionValue(0);
		conversionValue2.setDataValue("M");
		conversionValue2.setObjectValue("male");
		
		resourceConversionValues = converterAnnotation.conversionValues();
		resourceConversionValue = resourceConversionValues.next();
		assertEquals("M", resourceConversionValue.getDataValue());
		assertEquals("male", resourceConversionValue.getObjectValue());
		resourceConversionValue = resourceConversionValues.next();
		assertEquals("F", resourceConversionValue.getDataValue());
		assertEquals("female", resourceConversionValue.getObjectValue());
		
		ConversionValue conversionValue3 = converter.addConversionValue(1);
		conversionValue3.setDataValue("O");
		conversionValue3.setObjectValue("male");
		
		resourceConversionValues = converterAnnotation.conversionValues();
		resourceConversionValue = resourceConversionValues.next();
		assertEquals("M", resourceConversionValue.getDataValue());
		assertEquals("male", resourceConversionValue.getObjectValue());
		resourceConversionValue = resourceConversionValues.next();
		assertEquals("O", resourceConversionValue.getDataValue());
		assertEquals("male", resourceConversionValue.getObjectValue());
		resourceConversionValue = resourceConversionValues.next();
		assertEquals("F", resourceConversionValue.getDataValue());
		assertEquals("female", resourceConversionValue.getObjectValue());
		
		ListIterator<ConversionValue> conversionValues = converter.conversionValues();
		assertEquals(conversionValue2, conversionValues.next());
		assertEquals(conversionValue3, conversionValues.next());
		assertEquals(conversionValue, conversionValues.next());
		
		conversionValues = converter.conversionValues();
		assertEquals("M", conversionValues.next().getDataValue());
		assertEquals("O", conversionValues.next().getDataValue());
		assertEquals("F", conversionValues.next().getDataValue());
	}
	
	public void testRemoveConversionValue() throws Exception {
		createTestEntityWithConvertAndObjectTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ObjectTypeConverterAnnotation converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);
		
		converter.addConversionValue(0).setDataValue("F");
		converter.addConversionValue(1).setDataValue("M");
		converter.addConversionValue(2).setDataValue("O");
		
		ListIterator<ConversionValueAnnotation> resourceConversionValues = converterAnnotation.conversionValues();
		assertEquals(3, CollectionTools.size(resourceConversionValues));
		
		converter.removeConversionValue(0);
		resourceConversionValues = converterAnnotation.conversionValues();
		assertEquals(2, CollectionTools.size(resourceConversionValues));
		resourceConversionValues = converterAnnotation.conversionValues();
		assertEquals("M", resourceConversionValues.next().getDataValue());
		assertEquals("O", resourceConversionValues.next().getDataValue());

		converter.removeConversionValue(0);
		resourceConversionValues = converterAnnotation.conversionValues();
		assertEquals(1, CollectionTools.size(resourceConversionValues));
		resourceConversionValues = converterAnnotation.conversionValues();
		assertEquals("O", resourceConversionValues.next().getDataValue());
		
		converter.removeConversionValue(0);
		resourceConversionValues = converterAnnotation.conversionValues();
		assertEquals(0, CollectionTools.size(resourceConversionValues));
	}

	public void testMoveConversionValue() throws Exception {
		createTestEntityWithConvertAndObjectTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ObjectTypeConverterAnnotation converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);

		converter.addConversionValue(0).setDataValue("F");
		converter.addConversionValue(1).setDataValue("M");
		converter.addConversionValue(2).setDataValue("O");
		
		ListIterator<ConversionValueAnnotation> resourceConversionValues = converterAnnotation.conversionValues();
		assertEquals(3, CollectionTools.size(resourceConversionValues));
		
		converter.moveConversionValue(2,0);
		resourceConversionValues = converterAnnotation.conversionValues();
		assertEquals(3, CollectionTools.size(resourceConversionValues));
		resourceConversionValues = converterAnnotation.conversionValues();
		assertEquals("M", resourceConversionValues.next().getDataValue());
		assertEquals("O", resourceConversionValues.next().getDataValue());
		assertEquals("F", resourceConversionValues.next().getDataValue());
		
		converter.moveConversionValue(0,1);
		resourceConversionValues = converterAnnotation.conversionValues();
		assertEquals(3, CollectionTools.size(resourceConversionValues));
		resourceConversionValues = converterAnnotation.conversionValues();
		assertEquals("O", resourceConversionValues.next().getDataValue());
		assertEquals("M", resourceConversionValues.next().getDataValue());
		assertEquals("F", resourceConversionValues.next().getDataValue());
	}
	
	public void testUpdateConversionValues() throws Exception {
		createTestEntityWithConvertAndObjectTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ObjectTypeConverterAnnotation converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);
		
		converterAnnotation.addConversionValue(0).setDataValue("F");
		converterAnnotation.addConversionValue(1).setDataValue("M");
		converterAnnotation.addConversionValue(2).setDataValue("O");

		ListIterator<ConversionValue> conversionValues = converter.conversionValues();
		assertEquals("F", conversionValues.next().getDataValue());
		assertEquals("M", conversionValues.next().getDataValue());
		assertEquals("O", conversionValues.next().getDataValue());
		assertFalse(conversionValues.hasNext());
		
		converterAnnotation.moveConversionValue(2, 0);
		conversionValues = converter.conversionValues();
		assertEquals("M", conversionValues.next().getDataValue());
		assertEquals("O", conversionValues.next().getDataValue());
		assertEquals("F", conversionValues.next().getDataValue());
		assertFalse(conversionValues.hasNext());
		
		converterAnnotation.moveConversionValue(0, 1);
		conversionValues = converter.conversionValues();
		assertEquals("O", conversionValues.next().getDataValue());
		assertEquals("M", conversionValues.next().getDataValue());
		assertEquals("F", conversionValues.next().getDataValue());
		assertFalse(conversionValues.hasNext());
		
		converterAnnotation.removeConversionValue(1);
		conversionValues = converter.conversionValues();
		assertEquals("O", conversionValues.next().getDataValue());
		assertEquals("F", conversionValues.next().getDataValue());
		assertFalse(conversionValues.hasNext());
		
		converterAnnotation.removeConversionValue(1);
		conversionValues = converter.conversionValues();
		assertEquals("O", conversionValues.next().getDataValue());
		assertFalse(conversionValues.hasNext());
		
		converterAnnotation.removeConversionValue(0);
		conversionValues = converter.conversionValues();
		assertFalse(conversionValues.hasNext());
	}
	
	public void testConversionValuesSize() throws Exception {
		createTestEntityWithConvertAndObjectTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);


		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ObjectTypeConverterAnnotation converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);
	
		assertEquals(0, converter.conversionValuesSize());
		
		converterAnnotation.addConversionValue(0).setDataValue("F");
		converterAnnotation.addConversionValue(1).setDataValue("M");
		converterAnnotation.addConversionValue(2).setDataValue("O");
		
		assertEquals(3, converter.conversionValuesSize());
	}


	public void testGetDefaultObjectValue() throws Exception {
		createTestEntityWithConvertAndObjectTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();
		
		assertEquals("bar", converter.getDefaultObjectValue());
	}

	public void testSetDefaultObjectValue() throws Exception {
		createTestEntityWithConvertAndObjectTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();
		assertEquals("bar", converter.getDefaultObjectValue());
		
		converter.setDefaultObjectValue("baz");
		assertEquals("baz", converter.getDefaultObjectValue());
			
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ObjectTypeConverterAnnotation converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("baz", converterAnnotation.getDefaultObjectValue());

		
		converter.setDefaultObjectValue(null);
		assertEquals(null, converter.getDefaultObjectValue());
		converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getDefaultObjectValue());


		converter.setDefaultObjectValue("bar");
		assertEquals("bar", converter.getDefaultObjectValue());
		converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("bar", converterAnnotation.getDefaultObjectValue());
	}
	
	public void testGetDefaultObjectValueUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndObjectTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();

		assertEquals("bar", converter.getDefaultObjectValue());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ObjectTypeConverterAnnotation converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.getSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setDefaultObjectValue("baz");
		assertEquals("baz", converter.getDefaultObjectValue());
		
		attributeResource.removeSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);
		assertEquals(null, eclipseLinkConvert.getConverter());
		
		converterAnnotation = (ObjectTypeConverterAnnotation) attributeResource.addSupportingAnnotation(ObjectTypeConverterAnnotation.ANNOTATION_NAME);		
		assertNotNull(eclipseLinkConvert.getConverter());
		
		converterAnnotation.setDefaultObjectValue("FOO");
		assertEquals("FOO", ((ObjectTypeConverter) eclipseLinkConvert.getConverter()).getDefaultObjectValue());	
	}
	
	public void testInitializeConversionValues() throws Exception {
		createTestEntityWithConvertAndObjectTypeConverterConversionValue();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		ObjectTypeConverter converter = (ObjectTypeConverter) eclipseLinkConvert.getConverter();

		assertEquals(1, converter.conversionValuesSize());
		assertEquals("f", converter.conversionValues().next().getDataValue());
		assertEquals("female", converter.conversionValues().next().getObjectValue());
	}
}
