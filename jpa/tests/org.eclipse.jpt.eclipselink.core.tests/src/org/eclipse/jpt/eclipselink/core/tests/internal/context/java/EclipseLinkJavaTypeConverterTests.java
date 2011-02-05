/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkTypeConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLinkJavaTypeConverterTests extends EclipseLinkContextModelTestCase
{
	
	private ICompilationUnit createTestEntityWithConvertAndTypeConverter() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLink.CONVERT, EclipseLink.TYPE_CONVERTER);
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
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLink.CONVERT, EclipseLink.TYPE_CONVERTER);
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
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLink.CONVERT, EclipseLink.TYPE_CONVERTER);
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
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		EclipseLinkTypeConverter converter = (EclipseLinkTypeConverter) eclipseLinkConvert.getConverter();
		
		assertEquals("foo", converter.getName());
	}

	public void testSetName() throws Exception {
		createTestEntityWithConvertAndTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		EclipseLinkTypeConverter converter = (EclipseLinkTypeConverter) eclipseLinkConvert.getConverter();
		assertEquals("foo", converter.getName());
		
		converter.setName("bar");
		assertEquals("bar", converter.getName());
			
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkTypeConverterAnnotation converterAnnotation = (EclipseLinkTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("bar", converterAnnotation.getName());

		
		converter.setName(null);
		assertEquals(null, converter.getName());
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getName());


		converter.setName("bar");
		assertEquals("bar", converter.getName());
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("bar", converterAnnotation.getName());
	}
	
	public void testGetNameUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		EclipseLinkTypeConverter converter = (EclipseLinkTypeConverter) eclipseLinkConvert.getConverter();

		assertEquals("foo", converter.getName());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkTypeConverterAnnotation converterAnnotation = (EclipseLinkTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setName("bar");
		getJpaProject().synchronizeContextModel();
		assertEquals("bar", converter.getName());
		
		attributeResource.removeAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertEquals(null, eclipseLinkConvert.getConverter());
		
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) attributeResource.addAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		getJpaProject().synchronizeContextModel();
		assertNotNull(eclipseLinkConvert.getConverter());
		
		converterAnnotation.setName("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals("FOO", eclipseLinkConvert.getConverter().getName());	
	}

	public void testGetDataType() throws Exception {
		createTestEntityWithConvertAndDataType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		EclipseLinkTypeConverter converter = (EclipseLinkTypeConverter) eclipseLinkConvert.getConverter();
		
		assertEquals("Foo", converter.getDataType());
	}

	public void testSetDataType() throws Exception {
		createTestEntityWithConvertAndDataType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		EclipseLinkTypeConverter converter = (EclipseLinkTypeConverter) eclipseLinkConvert.getConverter();
		assertEquals("Foo", converter.getDataType());
		
		converter.setDataType("Bar");
		assertEquals("Bar", converter.getDataType());
			
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkTypeConverterAnnotation converterAnnotation = (EclipseLinkTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getDataType());

		
		converter.setDataType(null);
		assertEquals(null, converter.getDataType());
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getDataType());


		converter.setDataType("Bar");
		assertEquals("Bar", converter.getDataType());
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getDataType());
	}
	
	public void testGetDataTypeUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndDataType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		EclipseLinkTypeConverter converter = (EclipseLinkTypeConverter) eclipseLinkConvert.getConverter();

		assertEquals("Foo", converter.getDataType());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkTypeConverterAnnotation converterAnnotation = (EclipseLinkTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setDataType("Bar");
		getJpaProject().synchronizeContextModel();
		assertEquals("Bar", converter.getDataType());
		
		attributeResource.removeAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertEquals(null, eclipseLinkConvert.getConverter());
		
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) attributeResource.addAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		getJpaProject().synchronizeContextModel();
		assertNotNull(eclipseLinkConvert.getConverter());
		
		converterAnnotation.setDataType("FooBar");
		getJpaProject().synchronizeContextModel();
		assertEquals("FooBar", ((EclipseLinkTypeConverter) eclipseLinkConvert.getConverter()).getDataType());	
	}
	
	public void testGetObjectType() throws Exception {
		createTestEntityWithConvertAndObjectType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		EclipseLinkTypeConverter converter = (EclipseLinkTypeConverter) eclipseLinkConvert.getConverter();
		
		assertEquals("Foo", converter.getObjectType());
	}

	public void testSetObjectType() throws Exception {
		createTestEntityWithConvertAndObjectType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		EclipseLinkTypeConverter converter = (EclipseLinkTypeConverter) eclipseLinkConvert.getConverter();
		assertEquals("Foo", converter.getObjectType());
		
		converter.setObjectType("Bar");
		assertEquals("Bar", converter.getObjectType());
			
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkTypeConverterAnnotation converterAnnotation = (EclipseLinkTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getObjectType());

		
		converter.setObjectType(null);
		assertEquals(null, converter.getObjectType());
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getObjectType());


		converter.setObjectType("Bar");
		assertEquals("Bar", converter.getObjectType());
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getObjectType());
	}
	
	public void testGetObjectTypeUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndObjectType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		EclipseLinkTypeConverter converter = (EclipseLinkTypeConverter) eclipseLinkConvert.getConverter();

		assertEquals("Foo", converter.getObjectType());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkTypeConverterAnnotation converterAnnotation = (EclipseLinkTypeConverterAnnotation) attributeResource.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setObjectType("Bar");
		getJpaProject().synchronizeContextModel();
		assertEquals("Bar", converter.getObjectType());
		
		attributeResource.removeAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertEquals(null, eclipseLinkConvert.getConverter());
		
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) attributeResource.addAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		getJpaProject().synchronizeContextModel();
		assertNotNull(eclipseLinkConvert.getConverter());
		
		converterAnnotation.setObjectType("FooBar");
		getJpaProject().synchronizeContextModel();
		assertEquals("FooBar", ((EclipseLinkTypeConverter) eclipseLinkConvert.getConverter()).getObjectType());	
	}
}
