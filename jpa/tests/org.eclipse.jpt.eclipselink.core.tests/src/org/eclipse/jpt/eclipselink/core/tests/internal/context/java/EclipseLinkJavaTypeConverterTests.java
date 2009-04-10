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
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.TypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.TypeConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkJavaTypeConverterTests extends EclipseLinkContextModelTestCase
{
	
	private ICompilationUnit createTestEntityWithConvertAndTypeConverter() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLinkJPA.CONVERT, EclipseLinkJPA.TYPE_CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert(\"foo\")").append(CR);
				sb.append("    @TypeConverter(name=\"foo\"");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithConvertAndDataType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLinkJPA.CONVERT, EclipseLinkJPA.TYPE_CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert(\"foo\")").append(CR);
				sb.append("    @TypeConverter(dataType=Foo.class");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithConvertAndObjectType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLinkJPA.CONVERT, EclipseLinkJPA.TYPE_CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert(\"foo\")").append(CR);
				sb.append("    @TypeConverter(objectType=Foo.class");
			}
		});
	}
	
	public EclipseLinkJavaTypeConverterTests(String name) {
		super(name);
	}


	public void testGetName() throws Exception {
		createTestEntityWithConvertAndTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		TypeConverter converter = (TypeConverter) eclipseLinkConvert.getConverter();
		
		assertEquals("foo", converter.getName());
	}

	public void testSetName() throws Exception {
		createTestEntityWithConvertAndTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		TypeConverter converter = (TypeConverter) eclipseLinkConvert.getConverter();
		assertEquals("foo", converter.getName());
		
		converter.setName("bar");
		assertEquals("bar", converter.getName());
			
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TypeConverterAnnotation converterAnnotation = (TypeConverterAnnotation) attributeResource.getSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("bar", converterAnnotation.getName());

		
		converter.setName(null);
		assertEquals(null, converter.getName());
		converterAnnotation = (TypeConverterAnnotation) attributeResource.getSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getName());


		converter.setName("bar");
		assertEquals("bar", converter.getName());
		converterAnnotation = (TypeConverterAnnotation) attributeResource.getSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("bar", converterAnnotation.getName());
	}
	
	public void testGetNameUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		TypeConverter converter = (TypeConverter) eclipseLinkConvert.getConverter();

		assertEquals("foo", converter.getName());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TypeConverterAnnotation converterAnnotation = (TypeConverterAnnotation) attributeResource.getSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setName("bar");
		assertEquals("bar", converter.getName());
		
		attributeResource.removeSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);
		assertEquals(null, eclipseLinkConvert.getConverter());
		
		converterAnnotation = (TypeConverterAnnotation) attributeResource.addSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);		
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
		TypeConverter converter = (TypeConverter) eclipseLinkConvert.getConverter();
		
		assertEquals("Foo", converter.getDataType());
	}

	public void testSetDataType() throws Exception {
		createTestEntityWithConvertAndDataType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		TypeConverter converter = (TypeConverter) eclipseLinkConvert.getConverter();
		assertEquals("Foo", converter.getDataType());
		
		converter.setDataType("Bar");
		assertEquals("Bar", converter.getDataType());
			
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TypeConverterAnnotation converterAnnotation = (TypeConverterAnnotation) attributeResource.getSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getDataType());

		
		converter.setDataType(null);
		assertEquals(null, converter.getDataType());
		converterAnnotation = (TypeConverterAnnotation) attributeResource.getSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getDataType());


		converter.setDataType("Bar");
		assertEquals("Bar", converter.getDataType());
		converterAnnotation = (TypeConverterAnnotation) attributeResource.getSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getDataType());
	}
	
	public void testGetDataTypeUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndDataType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		TypeConverter converter = (TypeConverter) eclipseLinkConvert.getConverter();

		assertEquals("Foo", converter.getDataType());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TypeConverterAnnotation converterAnnotation = (TypeConverterAnnotation) attributeResource.getSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setDataType("Bar");
		assertEquals("Bar", converter.getDataType());
		
		attributeResource.removeSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);
		assertEquals(null, eclipseLinkConvert.getConverter());
		
		converterAnnotation = (TypeConverterAnnotation) attributeResource.addSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);		
		assertNotNull(eclipseLinkConvert.getConverter());
		
		converterAnnotation.setDataType("FooBar");
		assertEquals("FooBar", ((TypeConverter) eclipseLinkConvert.getConverter()).getDataType());	
	}
	
	public void testGetObjectType() throws Exception {
		createTestEntityWithConvertAndObjectType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		TypeConverter converter = (TypeConverter) eclipseLinkConvert.getConverter();
		
		assertEquals("Foo", converter.getObjectType());
	}

	public void testSetObjectType() throws Exception {
		createTestEntityWithConvertAndObjectType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		TypeConverter converter = (TypeConverter) eclipseLinkConvert.getConverter();
		assertEquals("Foo", converter.getObjectType());
		
		converter.setObjectType("Bar");
		assertEquals("Bar", converter.getObjectType());
			
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TypeConverterAnnotation converterAnnotation = (TypeConverterAnnotation) attributeResource.getSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getObjectType());

		
		converter.setObjectType(null);
		assertEquals(null, converter.getObjectType());
		converterAnnotation = (TypeConverterAnnotation) attributeResource.getSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getObjectType());


		converter.setObjectType("Bar");
		assertEquals("Bar", converter.getObjectType());
		converterAnnotation = (TypeConverterAnnotation) attributeResource.getSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getObjectType());
	}
	
	public void testGetObjectTypeUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndObjectType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		Convert eclipseLinkConvert = (Convert) basicMapping.getConverter();
		TypeConverter converter = (TypeConverter) eclipseLinkConvert.getConverter();

		assertEquals("Foo", converter.getObjectType());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TypeConverterAnnotation converterAnnotation = (TypeConverterAnnotation) attributeResource.getSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setObjectType("Bar");
		assertEquals("Bar", converter.getObjectType());
		
		attributeResource.removeSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);
		assertEquals(null, eclipseLinkConvert.getConverter());
		
		converterAnnotation = (TypeConverterAnnotation) attributeResource.addSupportingAnnotation(TypeConverterAnnotation.ANNOTATION_NAME);		
		assertNotNull(eclipseLinkConvert.getConverter());
		
		converterAnnotation.setObjectType("FooBar");
		assertEquals("FooBar", ((TypeConverter) eclipseLinkConvert.getConverter()).getObjectType());	
	}
}
