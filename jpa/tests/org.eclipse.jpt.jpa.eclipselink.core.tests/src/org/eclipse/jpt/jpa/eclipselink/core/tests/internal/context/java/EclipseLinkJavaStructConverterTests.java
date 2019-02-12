/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaBasicMapping;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.StructConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLinkJavaStructConverterTests extends EclipseLinkContextModelTestCase
{
	
	private ICompilationUnit createTestEntityWithConvertAndStructConverter() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, EclipseLink.CONVERT, EclipseLink.STRUCT_CONVERTER);
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
				return IteratorTools.iterator(JPA.ENTITY, EclipseLink.CONVERT, EclipseLink.STRUCT_CONVERTER);
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
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkJavaBasicMapping basicMapping = (EclipseLinkJavaBasicMapping) persistentAttribute.getMapping();
		EclipseLinkStructConverter converter = basicMapping.getConverterContainer().getStructConverters().iterator().next();
		
		assertEquals("foo", converter.getName());
	}

	public void testSetName() throws Exception {
		createTestEntityWithConvertAndStructConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkJavaBasicMapping basicMapping = (EclipseLinkJavaBasicMapping) persistentAttribute.getMapping();
		EclipseLinkStructConverter converter = basicMapping.getConverterContainer().getStructConverters().iterator().next();
		assertEquals("foo", converter.getName());
		
		converter.setName("bar");
		assertEquals("bar", converter.getName());
			
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		StructConverterAnnotation converterAnnotation = (StructConverterAnnotation) resourceField.getAnnotation(0, StructConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("bar", converterAnnotation.getName());

		
		converter.setName(null);
		assertEquals(null, converter.getName());
		converterAnnotation = (StructConverterAnnotation) resourceField.getAnnotation(0, StructConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getName());


		converter.setName("bar");
		assertEquals("bar", converter.getName());
		converterAnnotation = (StructConverterAnnotation) resourceField.getAnnotation(0, StructConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("bar", converterAnnotation.getName());
	}
	
	public void testGetNameUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndStructConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkJavaBasicMapping basicMapping = (EclipseLinkJavaBasicMapping) persistentAttribute.getMapping();
		EclipseLinkStructConverter converter = basicMapping.getConverterContainer().getStructConverters().iterator().next();

		assertEquals("foo", converter.getName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		StructConverterAnnotation converterAnnotation = (StructConverterAnnotation) resourceField.getAnnotation(0, StructConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setName("bar");
		getJpaProject().synchronizeContextModel();
		assertEquals("bar", converter.getName());
		
		resourceField.removeAnnotation(0, StructConverterAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertFalse(basicMapping.getConverterContainer().getStructConverters().iterator().hasNext());
		
		converterAnnotation = (StructConverterAnnotation) resourceField.addAnnotation(0, StructConverterAnnotation.ANNOTATION_NAME);		
		getJpaProject().synchronizeContextModel();
		converter = basicMapping.getConverterContainer().getStructConverters().iterator().next();
		assertNotNull(converter);
		
		converterAnnotation.setName("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals("FOO", converter.getName());	
	}
	

	public void testGetConverterClass() throws Exception {
		createTestEntityWithConvertAndStructConverterClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkJavaBasicMapping basicMapping = (EclipseLinkJavaBasicMapping) persistentAttribute.getMapping();
		EclipseLinkStructConverter converter = basicMapping.getConverterContainer().getStructConverters().iterator().next();
		
		assertEquals("Foo", converter.getConverterClass());
	}

	public void testSetConverterClass() throws Exception {
		createTestEntityWithConvertAndStructConverterClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkJavaBasicMapping basicMapping = (EclipseLinkJavaBasicMapping) persistentAttribute.getMapping();
		EclipseLinkStructConverter converter = basicMapping.getConverterContainer().getStructConverters().iterator().next();
		assertEquals("Foo", converter.getConverterClass());
		
		converter.setConverterClass("Bar");
		assertEquals("Bar", converter.getConverterClass());
			
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		StructConverterAnnotation converterAnnotation = (StructConverterAnnotation) resourceField.getAnnotation(0, StructConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getConverter());

		
		converter.setConverterClass(null);
		assertEquals(null, converter.getConverterClass());
		converterAnnotation = (StructConverterAnnotation) resourceField.getAnnotation(0, StructConverterAnnotation.ANNOTATION_NAME);		
		assertEquals(null, converterAnnotation.getConverter());


		converter.setConverterClass("Bar");
		assertEquals("Bar", converter.getConverterClass());
		converterAnnotation = (StructConverterAnnotation) resourceField.getAnnotation(0, StructConverterAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", converterAnnotation.getConverter());
	}
	
	public void testGetConverterClassUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndStructConverterClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkJavaBasicMapping basicMapping = (EclipseLinkJavaBasicMapping) persistentAttribute.getMapping();
		EclipseLinkStructConverter converter = basicMapping.getConverterContainer().getStructConverters().iterator().next();

		assertEquals("Foo", converter.getConverterClass());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		StructConverterAnnotation converterAnnotation = (StructConverterAnnotation) resourceField.getAnnotation(0, StructConverterAnnotation.ANNOTATION_NAME);
		converterAnnotation.setConverter("Bar");
		getJpaProject().synchronizeContextModel();
		assertEquals("Bar", converter.getConverterClass());
		
		resourceField.removeAnnotation(0, StructConverterAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertFalse(basicMapping.getConverterContainer().getStructConverters().iterator().hasNext());
		
		converterAnnotation = (StructConverterAnnotation) resourceField.addAnnotation(0, StructConverterAnnotation.ANNOTATION_NAME);		
		getJpaProject().synchronizeContextModel();
		converter = basicMapping.getConverterContainer().getStructConverters().iterator().next();
		assertNotNull(converter);
		
		converterAnnotation.setConverter("FooBar");
		getJpaProject().synchronizeContextModel();
		assertEquals("FooBar", converter.getConverterClass());	
	}
}
