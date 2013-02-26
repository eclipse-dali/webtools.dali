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
import org.eclipse.jpt.jpa.core.context.ModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLinkJavaConverterTests extends EclipseLinkContextModelTestCase
{

	
	private ICompilationUnit createTestEntityWithConvertAndConverter() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, EclipseLink.CONVERT, EclipseLink.CONVERTER);
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
				return IteratorTools.iterator(JPA.ENTITY, EclipseLink.CONVERT, EclipseLink.CONVERTER);
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
		
		ModifiablePersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaEclipseLinkBasicMapping basicMapping = (JavaEclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkCustomConverter converter = basicMapping.getConverterContainer().getCustomConverters().iterator().next();
		
		assertEquals("foo", converter.getName());
	}

	public void testSetName() throws Exception {
		createTestEntityWithConvertAndConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ModifiablePersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaEclipseLinkBasicMapping basicMapping = (JavaEclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkCustomConverter converter = basicMapping.getConverterContainer().getCustomConverters().iterator().next();
		assertEquals("foo", converter.getName());
		
		converter.setName("bar");
		assertEquals("bar", converter.getName());
			
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		EclipseLinkConverterAnnotation converterAnnotation = (EclipseLinkConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("bar", converterAnnotation.getName());

		
		converter.setName(null);
		assertEquals(null, converter.getName());
		converterAnnotation = (EclipseLinkConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getName());


		converter.setName("bar");
		assertEquals("bar", converter.getName());
		converterAnnotation = (EclipseLinkConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("bar", converterAnnotation.getName());
	}
	
	public void testGetNameUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		ModifiablePersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaEclipseLinkBasicMapping basicMapping = (JavaEclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkCustomConverter converter = basicMapping.getConverterContainer().getCustomConverters().iterator().next();

		assertEquals("foo", converter.getName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		EclipseLinkConverterAnnotation converterAnnotation = (EclipseLinkConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setName("bar");
		getJpaProject().synchronizeContextModel();
		assertEquals("bar", converter.getName());
		
		resourceField.removeAnnotation(0, EclipseLinkConverterAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertFalse(basicMapping.getConverterContainer().getCustomConverters().iterator().hasNext());
		
		converterAnnotation = (EclipseLinkConverterAnnotation) resourceField.addAnnotation(0, EclipseLinkConverterAnnotation.ANNOTATION_NAME);		
		getJpaProject().synchronizeContextModel();
		converter = basicMapping.getConverterContainer().getCustomConverters().iterator().next();
		assertNotNull(converter);
		
		converterAnnotation.setName("FOO");
		getJpaProject().synchronizeContextModel();
		converter = basicMapping.getConverterContainer().getCustomConverters().iterator().next();
		assertEquals("FOO", converter.getName());	
	}
	

	public void testGetConverterClass() throws Exception {
		createTestEntityWithConvertAndConverterClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ModifiablePersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaEclipseLinkBasicMapping basicMapping = (JavaEclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkCustomConverter converter = basicMapping.getConverterContainer().getCustomConverters().iterator().next();
		
		assertEquals("Foo", converter.getConverterClass());
	}

	public void testSetConverterClass() throws Exception {
		createTestEntityWithConvertAndConverterClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ModifiablePersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaEclipseLinkBasicMapping basicMapping = (JavaEclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkCustomConverter converter = basicMapping.getConverterContainer().getCustomConverters().iterator().next();
		assertEquals("Foo", converter.getConverterClass());
		
		converter.setConverterClass("Bar");
		assertEquals("Bar", converter.getConverterClass());
			
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		EclipseLinkConverterAnnotation converterAnnotation = (EclipseLinkConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getConverterClass());

		
		converter.setConverterClass(null);
		assertEquals(null, converter.getConverterClass());
		converterAnnotation = (EclipseLinkConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getConverterClass());


		converter.setConverterClass("Bar");
		assertEquals("Bar", converter.getConverterClass());
		converterAnnotation = (EclipseLinkConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getConverterClass());
	}
	
	public void testGetConverterClassUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndConverterClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		ModifiablePersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		JavaEclipseLinkBasicMapping basicMapping = (JavaEclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkCustomConverter converter = basicMapping.getConverterContainer().getCustomConverters().iterator().next();

		assertEquals("Foo", converter.getConverterClass());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		EclipseLinkConverterAnnotation converterAnnotation = (EclipseLinkConverterAnnotation) resourceField.getAnnotation(0, EclipseLinkConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setConverterClass("Bar");
		getJpaProject().synchronizeContextModel();
		assertEquals("Bar", converter.getConverterClass());
		
		resourceField.removeAnnotation(0, EclipseLinkConverterAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertFalse(basicMapping.getConverterContainer().getCustomConverters().iterator().hasNext());
		
		converterAnnotation = (EclipseLinkConverterAnnotation) resourceField.addAnnotation(0, EclipseLinkConverterAnnotation.ANNOTATION_NAME);		
		getJpaProject().synchronizeContextModel();
		converter = basicMapping.getConverterContainer().getCustomConverters().iterator().next();
		assertNotNull(converter);
		
		converterAnnotation.setConverterClass("FooBar");
		getJpaProject().synchronizeContextModel();
		converter = basicMapping.getConverterContainer().getCustomConverters().iterator().next();
		assertEquals("FooBar", converter.getConverterClass());	
	}
}
