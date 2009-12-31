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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkJavaConverterTests extends EclipseLinkContextModelTestCase
{

	
	private ICompilationUnit createTestEntityWithConvertAndConverter() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLink.CONVERT, EclipseLink.CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert(\"foo\")").append(CR);
				sb.append("    @Converter(name=\"foo\"");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithConvertAndConverterClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLink.CONVERT, EclipseLink.CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert(\"foo\")").append(CR);
				sb.append("    @Converter(converterClass=Foo.class");
			}
		});
	}
	
	public EclipseLinkJavaConverterTests(String name) {
		super(name);
	}


	public void testGetName() throws Exception {
		createTestEntityWithConvertAndConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getSpecifiedConverter();
		EclipseLinkCustomConverter converter = (EclipseLinkCustomConverter) eclipseLinkConvert.getConverter();
		
		assertEquals("foo", converter.getName());
	}

	public void testSetName() throws Exception {
		createTestEntityWithConvertAndConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getSpecifiedConverter();
		EclipseLinkCustomConverter converter = (EclipseLinkCustomConverter) eclipseLinkConvert.getConverter();
		assertEquals("foo", converter.getName());
		
		converter.setName("bar");
		assertEquals("bar", converter.getName());
			
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkConverterAnnotation converterAnnotation = (EclipseLinkConverterAnnotation) attributeResource.getAnnotation(EclipseLinkConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("bar", converterAnnotation.getName());

		
		converter.setName(null);
		assertEquals(null, converter.getName());
		converterAnnotation = (EclipseLinkConverterAnnotation) attributeResource.getAnnotation(EclipseLinkConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getName());


		converter.setName("bar");
		assertEquals("bar", converter.getName());
		converterAnnotation = (EclipseLinkConverterAnnotation) attributeResource.getAnnotation(EclipseLinkConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("bar", converterAnnotation.getName());
	}
	
	public void testGetNameUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getSpecifiedConverter();
		EclipseLinkCustomConverter converter = (EclipseLinkCustomConverter) eclipseLinkConvert.getConverter();

		assertEquals("foo", converter.getName());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkConverterAnnotation converterAnnotation = (EclipseLinkConverterAnnotation) attributeResource.getAnnotation(EclipseLinkConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setName("bar");
		assertEquals("bar", converter.getName());
		
		attributeResource.removeAnnotation(EclipseLinkConverterAnnotation.ANNOTATION_NAME);
		assertEquals(null, eclipseLinkConvert.getConverter());
		
		converterAnnotation = (EclipseLinkConverterAnnotation) attributeResource.addAnnotation(EclipseLinkConverterAnnotation.ANNOTATION_NAME);		
		assertNotNull(eclipseLinkConvert.getConverter());
		
		converterAnnotation.setName("FOO");
		assertEquals("FOO", eclipseLinkConvert.getConverter().getName());	
	}
	

	public void testGetConverterClass() throws Exception {
		createTestEntityWithConvertAndConverterClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getSpecifiedConverter();
		EclipseLinkCustomConverter converter = (EclipseLinkCustomConverter) eclipseLinkConvert.getConverter();
		
		assertEquals("Foo", converter.getConverterClass());
	}

	public void testSetConverterClass() throws Exception {
		createTestEntityWithConvertAndConverterClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getSpecifiedConverter();
		EclipseLinkCustomConverter converter = (EclipseLinkCustomConverter) eclipseLinkConvert.getConverter();
		assertEquals("Foo", converter.getConverterClass());
		
		converter.setConverterClass("Bar");
		assertEquals("Bar", converter.getConverterClass());
			
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkConverterAnnotation converterAnnotation = (EclipseLinkConverterAnnotation) attributeResource.getAnnotation(EclipseLinkConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getConverterClass());

		
		converter.setConverterClass(null);
		assertEquals(null, converter.getConverterClass());
		converterAnnotation = (EclipseLinkConverterAnnotation) attributeResource.getAnnotation(EclipseLinkConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getConverterClass());


		converter.setConverterClass("Bar");
		assertEquals("Bar", converter.getConverterClass());
		converterAnnotation = (EclipseLinkConverterAnnotation) attributeResource.getAnnotation(EclipseLinkConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getConverterClass());
	}
	
	public void testGetConverterClassUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndConverterClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getSpecifiedConverter();
		EclipseLinkCustomConverter converter = (EclipseLinkCustomConverter) eclipseLinkConvert.getConverter();

		assertEquals("Foo", converter.getConverterClass());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkConverterAnnotation converterAnnotation = (EclipseLinkConverterAnnotation) attributeResource.getAnnotation(EclipseLinkConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setConverterClass("Bar");
		assertEquals("Bar", converter.getConverterClass());
		
		attributeResource.removeAnnotation(EclipseLinkConverterAnnotation.ANNOTATION_NAME);
		assertEquals(null, eclipseLinkConvert.getConverter());
		
		converterAnnotation = (EclipseLinkConverterAnnotation) attributeResource.addAnnotation(EclipseLinkConverterAnnotation.ANNOTATION_NAME);		
		assertNotNull(eclipseLinkConvert.getConverter());
		
		converterAnnotation.setConverterClass("FooBar");
		assertEquals("FooBar", ((EclipseLinkCustomConverter) eclipseLinkConvert.getConverter()).getConverterClass());	
	}
}
