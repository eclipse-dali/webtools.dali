/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.context.Customizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.ReadOnly;
import org.eclipse.jpt.eclipselink.core.resource.java.CustomizerAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.ReadOnlyAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class EclipseLinkJavaMappedSuperclassTests extends EclipseLinkJavaContextModelTestCase
{

	private void createReadOnlyAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "ReadOnly", "");		
	}
	
	private void createCustomizerAnnotation() throws Exception {
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "Customizer", "Class value();");		
	}

	private ICompilationUnit createTestMappedSuperclassWithReadOnly() throws Exception {
		createReadOnlyAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS, EclipseLinkJPA.READ_ONLY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
				sb.append("@ReadOnly").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestMappedSuperclassWithConvertAndCustomizerClass() throws Exception {
		createCustomizerAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS, EclipseLinkJPA.CUSTOMIZER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
				sb.append("    @Customizer(Foo.class");
			}
		});
	}

	public EclipseLinkJavaMappedSuperclassTests(String name) {
		super(name);
	}


	public void testGetReadOnly() throws Exception {
		createTestMappedSuperclassWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkMappedSuperclass mappedSuperclass = (EclipseLinkMappedSuperclass) javaPersistentType().getMapping();
		ReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(true, readOnly.getReadOnly());
	}

	public void testSetReadOnly() throws Exception {
		createTestMappedSuperclassWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkMappedSuperclass mappedSuperclass = (EclipseLinkMappedSuperclass) javaPersistentType().getMapping();
		ReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(true, readOnly.getReadOnly());
		
		readOnly.setReadOnly(false);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		assertNull(typeResource.getAnnotation(ReadOnlyAnnotation.ANNOTATION_NAME));
		assertEquals(false, readOnly.getReadOnly());

		readOnly.setReadOnly(true);
		assertNotNull(typeResource.getAnnotation(ReadOnlyAnnotation.ANNOTATION_NAME));
		assertEquals(true, readOnly.getReadOnly());
	}
	
	public void testReadOnlyUpdatesFromResourceModelChange() throws Exception {
		createTestMappedSuperclassWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkMappedSuperclass mappedSuperclass = (EclipseLinkMappedSuperclass) javaPersistentType().getMapping();
		ReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(true, readOnly.getReadOnly());
		
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.removeAnnotation(ReadOnlyAnnotation.ANNOTATION_NAME);
		
		assertEquals(false, readOnly.getReadOnly());
		
		typeResource.addAnnotation(ReadOnlyAnnotation.ANNOTATION_NAME);
		assertEquals(true, readOnly.getReadOnly());
	}

	public void testGetCustomizerClass() throws Exception {
		createTestMappedSuperclassWithConvertAndCustomizerClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Customizer customizer = ((EclipseLinkMappedSuperclass) javaPersistentType().getMapping()).getCustomizer();
		
		assertEquals("Foo", customizer.getCustomizerClass());
	}

	public void testSetCustomizerClass() throws Exception {
		createTestMappedSuperclassWithConvertAndCustomizerClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Customizer customizer = ((EclipseLinkMappedSuperclass) javaPersistentType().getMapping()).getCustomizer();
		assertEquals("Foo", customizer.getCustomizerClass());
		
		customizer.setCustomizerClass("Bar");
		assertEquals("Bar", customizer.getCustomizerClass());
			
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		CustomizerAnnotation customizerAnnotation = (CustomizerAnnotation) typeResource.getAnnotation(CustomizerAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", customizerAnnotation.getValue());

		
		customizer.setCustomizerClass(null);
		assertEquals(null, customizer.getCustomizerClass());
		customizerAnnotation = (CustomizerAnnotation) typeResource.getAnnotation(CustomizerAnnotation.ANNOTATION_NAME);		
		assertEquals(null, customizerAnnotation);


		customizer.setCustomizerClass("Bar");
		assertEquals("Bar", customizer.getCustomizerClass());
		customizerAnnotation = (CustomizerAnnotation) typeResource.getAnnotation(CustomizerAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", customizerAnnotation.getValue());
	}
	
	public void testGetCustomizerClassUpdatesFromResourceModelChange() throws Exception {
		createTestMappedSuperclassWithConvertAndCustomizerClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkMappedSuperclass mappedSuperclass = (EclipseLinkMappedSuperclass) javaPersistentType().getMapping();
		Customizer customizer = mappedSuperclass.getCustomizer();

		assertEquals("Foo", customizer.getCustomizerClass());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		CustomizerAnnotation customizerAnnotation = (CustomizerAnnotation) typeResource.getAnnotation(CustomizerAnnotation.ANNOTATION_NAME);
		customizerAnnotation.setValue("Bar");
		assertEquals("Bar", customizer.getCustomizerClass());
		
		typeResource.removeAnnotation(CustomizerAnnotation.ANNOTATION_NAME);
		assertEquals(null, customizer.getCustomizerClass());
		
		customizerAnnotation = (CustomizerAnnotation) typeResource.addAnnotation(CustomizerAnnotation.ANNOTATION_NAME);		
		assertEquals(null, customizer.getCustomizerClass());
		
		customizerAnnotation.setValue("FooBar");
		assertEquals("FooBar", customizer.getCustomizerClass());	
	}	
}
