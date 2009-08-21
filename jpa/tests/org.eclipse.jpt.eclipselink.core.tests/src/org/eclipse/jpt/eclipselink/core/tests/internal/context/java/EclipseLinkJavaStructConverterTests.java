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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkStructConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkJavaStructConverterTests extends EclipseLinkContextModelTestCase
{
	
	private ICompilationUnit createTestEntityWithConvertAndStructConverter() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLinkJPA.CONVERT, EclipseLinkJPA.STRUCT_CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert(\"foo\")").append(CR);
				sb.append("    @StructConverter(name=\"foo\"");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithConvertAndStructConverterClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLinkJPA.CONVERT, EclipseLinkJPA.STRUCT_CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert(\"foo\")").append(CR);
				sb.append("    @StructConverter(converter=\"Foo\"");
			}
		});
	}
	
	public EclipseLinkJavaStructConverterTests(String name) {
		super(name);
	}


	public void testGetName() throws Exception {
		createTestEntityWithConvertAndStructConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		EclipseLinkStructConverter converter = (EclipseLinkStructConverter) eclipseLinkConvert.getConverter();
		
		assertEquals("foo", converter.getName());
	}

	public void testSetName() throws Exception {
		createTestEntityWithConvertAndStructConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		EclipseLinkStructConverter converter = (EclipseLinkStructConverter) eclipseLinkConvert.getConverter();
		assertEquals("foo", converter.getName());
		
		converter.setName("bar");
		assertEquals("bar", converter.getName());
			
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkStructConverterAnnotation converterAnnotation = (EclipseLinkStructConverterAnnotation) attributeResource.getAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("bar", converterAnnotation.getName());

		
		converter.setName(null);
		assertEquals(null, converter.getName());
		converterAnnotation = (EclipseLinkStructConverterAnnotation) attributeResource.getAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getName());


		converter.setName("bar");
		assertEquals("bar", converter.getName());
		converterAnnotation = (EclipseLinkStructConverterAnnotation) attributeResource.getAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("bar", converterAnnotation.getName());
	}
	
	public void testGetNameUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndStructConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		EclipseLinkStructConverter converter = (EclipseLinkStructConverter) eclipseLinkConvert.getConverter();

		assertEquals("foo", converter.getName());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkStructConverterAnnotation converterAnnotation = (EclipseLinkStructConverterAnnotation) attributeResource.getAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setName("bar");
		assertEquals("bar", converter.getName());
		
		attributeResource.removeAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME);
		assertEquals(null, eclipseLinkConvert.getConverter());
		
		converterAnnotation = (EclipseLinkStructConverterAnnotation) attributeResource.addAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME);		
		assertNotNull(eclipseLinkConvert.getConverter());
		
		converterAnnotation.setName("FOO");
		assertEquals("FOO", eclipseLinkConvert.getConverter().getName());	
	}
	

	public void testGetConverterClass() throws Exception {
		createTestEntityWithConvertAndStructConverterClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		EclipseLinkStructConverter converter = (EclipseLinkStructConverter) eclipseLinkConvert.getConverter();
		
		assertEquals("Foo", converter.getConverterClass());
	}

	public void testSetConverterClass() throws Exception {
		createTestEntityWithConvertAndStructConverterClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		EclipseLinkStructConverter converter = (EclipseLinkStructConverter) eclipseLinkConvert.getConverter();
		assertEquals("Foo", converter.getConverterClass());
		
		converter.setConverterClass("Bar");
		assertEquals("Bar", converter.getConverterClass());
			
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkStructConverterAnnotation converterAnnotation = (EclipseLinkStructConverterAnnotation) attributeResource.getAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getConverter());

		
		converter.setConverterClass(null);
		assertEquals(null, converter.getConverterClass());
		converterAnnotation = (EclipseLinkStructConverterAnnotation) attributeResource.getAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getConverter());


		converter.setConverterClass("Bar");
		assertEquals("Bar", converter.getConverterClass());
		converterAnnotation = (EclipseLinkStructConverterAnnotation) attributeResource.getAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getConverter());
	}
	
	public void testGetConverterClassUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndStructConverterClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		EclipseLinkStructConverter converter = (EclipseLinkStructConverter) eclipseLinkConvert.getConverter();

		assertEquals("Foo", converter.getConverterClass());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkStructConverterAnnotation converterAnnotation = (EclipseLinkStructConverterAnnotation) attributeResource.getAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setConverter("Bar");
		assertEquals("Bar", converter.getConverterClass());
		
		attributeResource.removeAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME);
		assertEquals(null, eclipseLinkConvert.getConverter());
		
		converterAnnotation = (EclipseLinkStructConverterAnnotation) attributeResource.addAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME);		
		assertNotNull(eclipseLinkConvert.getConverter());
		
		converterAnnotation.setConverter("FooBar");
		assertEquals("FooBar", ((EclipseLinkStructConverter) eclipseLinkConvert.getConverter()).getConverterClass());	
	}
}
