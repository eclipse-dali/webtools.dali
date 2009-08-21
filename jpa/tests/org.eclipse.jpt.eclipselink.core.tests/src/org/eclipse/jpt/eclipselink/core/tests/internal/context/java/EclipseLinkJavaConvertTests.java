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
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConvertAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkStructConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkTypeConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkJavaConvertTests extends EclipseLinkContextModelTestCase
{

	private ICompilationUnit createTestEntityWithBasicMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic").append(CR);
			}
		});
	}

	
	private ICompilationUnit createTestEntityWithConvert() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLinkJPA.CONVERT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert(\"class-instance\")").append(CR);
			}
		});
	}
	
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
				sb.append("@Convert(\"class-instance\")").append(CR);
				sb.append("    @TypeConverter");
			}
		});
	}
	
	public EclipseLinkJavaConvertTests(String name) {
		super(name);
	}


	public void testGetConverterName() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addAnnotation(EclipseLinkConvertAnnotation.ANNOTATION_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		
		assertEquals(EclipseLinkConvert.NO_CONVERTER, eclipseLinkConvert.getConverterName());
		assertEquals(EclipseLinkConvert.NO_CONVERTER, eclipseLinkConvert.getDefaultConverterName());
		assertEquals(null, eclipseLinkConvert.getSpecifiedConverterName());
	}
	
	public void testGetConvertName2() throws Exception {
		createTestEntityWithConvert();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();

		assertEquals(EclipseLinkConvert.CLASS_INSTANCE_CONVERTER, eclipseLinkConvert.getConverterName());
	}

	public void testSetSpecifiedConverterName() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();
		basicMapping.setSpecifiedConverter(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER);
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		assertEquals(null, eclipseLinkConvert.getSpecifiedConverterName());
		
		eclipseLinkConvert.setSpecifiedConverterName("foo");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkConvertAnnotation convertAnnotation = (EclipseLinkConvertAnnotation) attributeResource.getAnnotation(EclipseLinkConvertAnnotation.ANNOTATION_NAME);
		
		assertEquals("foo", convertAnnotation.getValue());
		
		eclipseLinkConvert.setSpecifiedConverterName(null);
		convertAnnotation = (EclipseLinkConvertAnnotation) attributeResource.getAnnotation(EclipseLinkConvertAnnotation.ANNOTATION_NAME);
		assertNotNull(convertAnnotation);
		assertEquals(null, convertAnnotation.getValue());
	}
	
	public void testGetConverterNameUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(Converter.NO_CONVERTER, basicMapping.getConverter().getType());
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkConvertAnnotation convert = (EclipseLinkConvertAnnotation) attributeResource.addAnnotation(EclipseLinkConvertAnnotation.ANNOTATION_NAME);
		convert.setValue("foo");
		
		assertEquals(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER, basicMapping.getConverter().getType());
		assertEquals("foo", ((EclipseLinkConvert) basicMapping.getConverter()).getConverterName());
		
		attributeResource.removeAnnotation(EclipseLinkConvertAnnotation.ANNOTATION_NAME);
		
		assertEquals(Converter.NO_CONVERTER, basicMapping.getConverter().getType());
		assertFalse(basicMapping.isDefault());
		assertSame(basicMapping, persistentAttribute.getSpecifiedMapping());
	}
	

	public void testGetConverter() throws Exception {
		createTestEntityWithConvertAndTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		
		assertEquals(EclipseLinkConverter.TYPE_CONVERTER, eclipseLinkConvert.getConverter().getType());
	}
	
	public void testSetConverter() throws Exception {
		createTestEntityWithConvert();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		
		assertEquals(null, eclipseLinkConvert.getConverter());
		
		eclipseLinkConvert.setConverter(EclipseLinkConverter.TYPE_CONVERTER);	
		assertEquals(EclipseLinkConverter.TYPE_CONVERTER, eclipseLinkConvert.getConverter().getType());
	
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME));
		
		eclipseLinkConvert.setConverter(EclipseLinkConverter.STRUCT_CONVERTER);
		assertEquals(EclipseLinkConverter.STRUCT_CONVERTER, eclipseLinkConvert.getConverter().getType());
		assertNotNull(attributeResource.getAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME));

		eclipseLinkConvert.setConverter(EclipseLinkConverter.NO_CONVERTER);
		assertEquals(null, eclipseLinkConvert.getConverter());
		assertNull(attributeResource.getAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME));
		
		eclipseLinkConvert.setConverter(EclipseLinkConverter.STRUCT_CONVERTER);
		assertEquals(EclipseLinkConverter.STRUCT_CONVERTER, eclipseLinkConvert.getConverter().getType());
		assertNotNull(attributeResource.getAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME));

		
		basicMapping.setSpecifiedConverter(null);
		assertNull(attributeResource.getAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(EclipseLinkConvertAnnotation.ANNOTATION_NAME));
		
	}

}
