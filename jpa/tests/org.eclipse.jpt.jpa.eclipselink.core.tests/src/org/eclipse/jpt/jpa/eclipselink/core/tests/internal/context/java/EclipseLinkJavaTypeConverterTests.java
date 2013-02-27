/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkTypeConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLinkJavaTypeConverterTests extends EclipseLinkContextModelTestCase
{
	
	private ICompilationUnit createTestEntityWithConvertAndTypeConverter() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, EclipseLink.CONVERT, EclipseLink.TYPE_CONVERTER);
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
				return IteratorTools.iterator(JPA.ENTITY, EclipseLink.CONVERT, EclipseLink.TYPE_CONVERTER);
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
				return IteratorTools.iterator(JPA.ENTITY, EclipseLink.CONVERT, EclipseLink.TYPE_CONVERTER);
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

		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaEclipseLinkBasicMapping basicMapping = (JavaEclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkTypeConverter converter = basicMapping.getConverterContainer().getTypeConverters().iterator().next();

		assertEquals("foo", converter.getName());
	}

	public void testSetName() throws Exception {
		createTestEntityWithConvertAndTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaEclipseLinkBasicMapping basicMapping = (JavaEclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkTypeConverter converter = basicMapping.getConverterContainer().getTypeConverters().iterator().next();
		assertEquals("foo", converter.getName());
		
		converter.setName("bar");
		assertEquals("bar", converter.getName());
			
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		EclipseLinkTypeConverterAnnotation converterAnnotation = (EclipseLinkTypeConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("bar", converterAnnotation.getName());

		
		converter.setName(null);
		assertEquals(null, converter.getName());
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getName());


		converter.setName("bar");
		assertEquals("bar", converter.getName());
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("bar", converterAnnotation.getName());
	}
	
	public void testGetNameUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaEclipseLinkBasicMapping basicMapping = (JavaEclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkTypeConverter converter = basicMapping.getConverterContainer().getTypeConverters().iterator().next();

		assertEquals("foo", converter.getName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		EclipseLinkTypeConverterAnnotation converterAnnotation = (EclipseLinkTypeConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setName("bar");
		getJpaProject().synchronizeContextModel();
		assertEquals("bar", converter.getName());
		
		resourceField.removeAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertFalse(basicMapping.getConverterContainer().getTypeConverters().iterator().hasNext());
		
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) resourceField.addAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		getJpaProject().synchronizeContextModel();
		converter = basicMapping.getConverterContainer().getTypeConverters().iterator().next();
		assertNotNull(converter);
		
		converterAnnotation.setName("FOO");
		getJpaProject().synchronizeContextModel();
		converter = basicMapping.getConverterContainer().getTypeConverters().iterator().next();
		assertEquals("FOO", converter.getName());	
	}

	public void testGetDataType() throws Exception {
		createTestEntityWithConvertAndDataType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaEclipseLinkBasicMapping basicMapping = (JavaEclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkTypeConverter converter = basicMapping.getConverterContainer().getTypeConverters().iterator().next();
		
		assertEquals("Foo", converter.getDataType());
	}

	public void testSetDataType() throws Exception {
		createTestEntityWithConvertAndDataType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaEclipseLinkBasicMapping basicMapping = (JavaEclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkTypeConverter converter = basicMapping.getConverterContainer().getTypeConverters().iterator().next();
		assertEquals("Foo", converter.getDataType());
		
		converter.setDataType("Bar");
		assertEquals("Bar", converter.getDataType());
			
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		EclipseLinkTypeConverterAnnotation converterAnnotation = (EclipseLinkTypeConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getDataType());

		
		converter.setDataType(null);
		assertEquals(null, converter.getDataType());
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getDataType());


		converter.setDataType("Bar");
		assertEquals("Bar", converter.getDataType());
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getDataType());
	}
	
	public void testGetDataTypeUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndDataType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaEclipseLinkBasicMapping basicMapping = (JavaEclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkTypeConverter converter = basicMapping.getConverterContainer().getTypeConverters().iterator().next();

		assertEquals("Foo", converter.getDataType());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		EclipseLinkTypeConverterAnnotation converterAnnotation = (EclipseLinkTypeConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setDataType("Bar");
		getJpaProject().synchronizeContextModel();
		assertEquals("Bar", converter.getDataType());
		
		resourceField.removeAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertFalse(basicMapping.getConverterContainer().getTypeConverters().iterator().hasNext());
		
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) resourceField.addAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		getJpaProject().synchronizeContextModel();
		converter = basicMapping.getConverterContainer().getTypeConverters().iterator().next();
		assertNotNull(converter);
		
		converterAnnotation.setDataType("FooBar");
		getJpaProject().synchronizeContextModel();
		converter = basicMapping.getConverterContainer().getTypeConverters().iterator().next();
		assertEquals("FooBar", converter.getDataType());	
	}
	
	public void testGetObjectType() throws Exception {
		createTestEntityWithConvertAndObjectType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaEclipseLinkBasicMapping basicMapping = (JavaEclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkTypeConverter converter = basicMapping.getConverterContainer().getTypeConverters().iterator().next();
		
		assertEquals("Foo", converter.getObjectType());
	}

	public void testSetObjectType() throws Exception {
		createTestEntityWithConvertAndObjectType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaEclipseLinkBasicMapping basicMapping = (JavaEclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkTypeConverter converter = basicMapping.getConverterContainer().getTypeConverters().iterator().next();
		assertEquals("Foo", converter.getObjectType());
		
		converter.setObjectType("Bar");
		assertEquals("Bar", converter.getObjectType());
			
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		EclipseLinkTypeConverterAnnotation converterAnnotation = (EclipseLinkTypeConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getObjectType());

		
		converter.setObjectType(null);
		assertEquals(null, converter.getObjectType());
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getObjectType());


		converter.setObjectType("Bar");
		assertEquals("Bar", converter.getObjectType());
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getObjectType());
	}
	
	public void testGetObjectTypeUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndObjectType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaEclipseLinkBasicMapping basicMapping = (JavaEclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkTypeConverter converter = basicMapping.getConverterContainer().getTypeConverters().iterator().next();

		assertEquals("Foo", converter.getObjectType());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		EclipseLinkTypeConverterAnnotation converterAnnotation = (EclipseLinkTypeConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setObjectType("Bar");
		getJpaProject().synchronizeContextModel();
		assertEquals("Bar", converter.getObjectType());
		
		resourceField.removeAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertFalse(basicMapping.getConverterContainer().getTypeConverters().iterator().hasNext());
		
		converterAnnotation = (EclipseLinkTypeConverterAnnotation) resourceField.addAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME);		
		getJpaProject().synchronizeContextModel();
		converter = basicMapping.getConverterContainer().getTypeConverters().iterator().next();
		assertNotNull(converter);
		
		converterAnnotation.setObjectType("FooBar");
		getJpaProject().synchronizeContextModel();
		assertEquals("FooBar", converter.getObjectType());	
	}
}
