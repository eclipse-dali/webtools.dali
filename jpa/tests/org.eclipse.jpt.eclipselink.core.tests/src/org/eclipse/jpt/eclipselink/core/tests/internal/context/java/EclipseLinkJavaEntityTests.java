/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.eclipselink.core.context.ChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.ChangeTrackingType;
import org.eclipse.jpt.eclipselink.core.context.Customizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkEntity;
import org.eclipse.jpt.eclipselink.core.context.ReadOnly;
import org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.CustomizerAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.ReadOnlyAnnotation;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkJavaEntityTests extends EclipseLinkContextModelTestCase
{
	
	private ICompilationUnit createTestEntityWithConvertAndCustomizerClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLinkJPA.CUSTOMIZER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("    @Customizer(Foo.class");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithChangeTracking() throws Exception {		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLinkJPA.CHANGE_TRACKING);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("    @ChangeTracking").append(CR);
			}
		});
	}

	private ICompilationUnit createTestEntityWithReadOnly() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLinkJPA.READ_ONLY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@ReadOnly").append(CR);
			}
		});
	}

	public EclipseLinkJavaEntityTests(String name) {
		super(name);
	}


	public void testGetCustomizerClass() throws Exception {
		createTestEntityWithConvertAndCustomizerClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Customizer customizer = ((EclipseLinkEntity) getJavaPersistentType().getMapping()).getCustomizer();
		
		assertEquals("Foo", customizer.getSpecifiedCustomizerClass());
	}

	public void testSetCustomizerClass() throws Exception {
		createTestEntityWithConvertAndCustomizerClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Customizer customizer = ((EclipseLinkEntity) getJavaPersistentType().getMapping()).getCustomizer();
		assertEquals("Foo", customizer.getSpecifiedCustomizerClass());
		
		customizer.setSpecifiedCustomizerClass("Bar");
		assertEquals("Bar", customizer.getSpecifiedCustomizerClass());
			
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		CustomizerAnnotation customizerAnnotation = (CustomizerAnnotation) typeResource.getSupportingAnnotation(CustomizerAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", customizerAnnotation.getValue());

		
		customizer.setSpecifiedCustomizerClass(null);
		assertEquals(null, customizer.getSpecifiedCustomizerClass());
		customizerAnnotation = (CustomizerAnnotation) typeResource.getSupportingAnnotation(CustomizerAnnotation.ANNOTATION_NAME);		
		assertEquals(null, customizerAnnotation);


		customizer.setSpecifiedCustomizerClass("Bar");
		assertEquals("Bar", customizer.getSpecifiedCustomizerClass());
		customizerAnnotation = (CustomizerAnnotation) typeResource.getSupportingAnnotation(CustomizerAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", customizerAnnotation.getValue());
	}
	
	public void testGetCustomizerClassUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndCustomizerClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkEntity entity = (EclipseLinkEntity) getJavaPersistentType().getMapping();
		Customizer customizer = entity.getCustomizer();

		assertEquals("Foo", customizer.getSpecifiedCustomizerClass());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		CustomizerAnnotation customizerAnnotation = (CustomizerAnnotation) typeResource.getSupportingAnnotation(CustomizerAnnotation.ANNOTATION_NAME);
		customizerAnnotation.setValue("Bar");
		assertEquals("Bar", customizer.getSpecifiedCustomizerClass());
		
		typeResource.removeSupportingAnnotation(CustomizerAnnotation.ANNOTATION_NAME);
		assertEquals(null, customizer.getSpecifiedCustomizerClass());
		
		customizerAnnotation = (CustomizerAnnotation) typeResource.addSupportingAnnotation(CustomizerAnnotation.ANNOTATION_NAME);		
		assertEquals(null, customizer.getSpecifiedCustomizerClass());
		
		customizerAnnotation.setValue("FooBar");
		assertEquals("FooBar", customizer.getSpecifiedCustomizerClass());	
	}
	
	public void testGetChangeTracking() throws Exception {
		createTestEntityWithChangeTracking();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEntity entity = (EclipseLinkEntity) getJavaPersistentType().getMapping();
		ChangeTracking contextChangeTracking = entity.getChangeTracking();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		ChangeTrackingAnnotation resourceChangeTracking = (ChangeTrackingAnnotation) typeResource.getSupportingAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME);
		
		// base annotated, test context value
		
		assertNull(resourceChangeTracking.getValue());
		assertEquals(ChangeTrackingType.AUTO, contextChangeTracking.getType());
		assertEquals(ChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertEquals(ChangeTrackingType.AUTO, contextChangeTracking.getSpecifiedType());
		
		// change resource to ATTRIBUTE specifically, test context
		
		resourceChangeTracking.setValue(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.ATTRIBUTE);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.ATTRIBUTE, resourceChangeTracking.getValue());
		assertEquals(ChangeTrackingType.ATTRIBUTE, contextChangeTracking.getType());
		assertEquals(ChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertEquals(ChangeTrackingType.ATTRIBUTE, contextChangeTracking.getSpecifiedType());
		
		// change resource to OBJECT specifically, test context
		
		resourceChangeTracking.setValue(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.OBJECT);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.OBJECT, resourceChangeTracking.getValue());
		assertEquals(ChangeTrackingType.OBJECT, contextChangeTracking.getType());
		assertEquals(ChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertEquals(ChangeTrackingType.OBJECT, contextChangeTracking.getSpecifiedType());
		
		// change resource to DEFERRED specifically, test context
		
		resourceChangeTracking.setValue(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.DEFERRED);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.DEFERRED, resourceChangeTracking.getValue());
		assertEquals(ChangeTrackingType.DEFERRED, contextChangeTracking.getType());
		assertEquals(ChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertEquals(ChangeTrackingType.DEFERRED, contextChangeTracking.getSpecifiedType());
		
		// change resource to AUTO specifically, test context
		
		resourceChangeTracking.setValue(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.AUTO);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.AUTO, resourceChangeTracking.getValue());
		assertEquals(ChangeTrackingType.AUTO, contextChangeTracking.getType());
		assertEquals(ChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertEquals(ChangeTrackingType.AUTO, contextChangeTracking.getSpecifiedType());
		
		// remove value from resource, test context
		
		resourceChangeTracking.setValue(null);
		
		assertNull(resourceChangeTracking.getValue());
		assertEquals(ChangeTrackingType.AUTO, contextChangeTracking.getType());
		assertEquals(ChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertEquals(ChangeTrackingType.AUTO, contextChangeTracking.getSpecifiedType());
		
		// remove annotation, text context
		
		typeResource.removeSupportingAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME);
		
		assertNull(resourceChangeTracking.getValue());
		assertEquals(ChangeTrackingType.AUTO, contextChangeTracking.getType());
		assertEquals(ChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertNull(contextChangeTracking.getSpecifiedType());
	}
	
	public void testSetChangeTracking() throws Exception {
		createTestEntityWithChangeTracking();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEntity entity = (EclipseLinkEntity) getJavaPersistentType().getMapping();
		ChangeTracking contextChangeTracking = entity.getChangeTracking();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		ChangeTrackingAnnotation resourceChangeTracking = (ChangeTrackingAnnotation) typeResource.getSupportingAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME);
		
		// base annotated, test resource value
		
		assertNull(resourceChangeTracking.getValue());
		assertEquals(ChangeTrackingType.AUTO, contextChangeTracking.getSpecifiedType());
		
		// change context to AUTO specifically, test resource
		
		contextChangeTracking.setSpecifiedType(ChangeTrackingType.AUTO);
		
		assertNull(resourceChangeTracking.getValue());
		assertEquals(ChangeTrackingType.AUTO, contextChangeTracking.getSpecifiedType());
		
		// change context to ATTRIBUTE specifically, test resource
		
		contextChangeTracking.setSpecifiedType(ChangeTrackingType.ATTRIBUTE);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.ATTRIBUTE, resourceChangeTracking.getValue());
		assertEquals(ChangeTrackingType.ATTRIBUTE, contextChangeTracking.getSpecifiedType());
		
		// change context to OBJECT specifically, test resource
		
		contextChangeTracking.setSpecifiedType(ChangeTrackingType.OBJECT);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.OBJECT, resourceChangeTracking.getValue());
		assertEquals(ChangeTrackingType.OBJECT, contextChangeTracking.getSpecifiedType());
		
		// change context to DEFERRED specifically, test resource
		
		contextChangeTracking.setSpecifiedType(ChangeTrackingType.DEFERRED);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.DEFERRED, resourceChangeTracking.getValue());
		assertEquals(ChangeTrackingType.DEFERRED, contextChangeTracking.getSpecifiedType());
		
		// change context to null, test resource
		
		contextChangeTracking.setSpecifiedType(null);
		
		assertNull(typeResource.getSupportingAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME));
		assertNull(contextChangeTracking.getSpecifiedType());
		
		// change context to AUTO specifically (this time from no annotation), test resource
		
		contextChangeTracking.setSpecifiedType(ChangeTrackingType.AUTO);
		resourceChangeTracking = (ChangeTrackingAnnotation) typeResource.getSupportingAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.AUTO, resourceChangeTracking.getValue());
		assertEquals(ChangeTrackingType.AUTO, contextChangeTracking.getSpecifiedType());
	}
	
	public void testGetReadOnly() throws Exception {
		createTestEntityWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEntity mappedSuperclass = (EclipseLinkEntity) getJavaPersistentType().getMapping();
		ReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(true, readOnly.isReadOnly());
	}

	public void testGetSpecifiedReadOnly() throws Exception {
		createTestEntityWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEntity mappedSuperclass = (EclipseLinkEntity) getJavaPersistentType().getMapping();
		ReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(Boolean.TRUE, readOnly.getSpecifiedReadOnly());
	}

	//TODO test inheriting a default readonly from you superclass
	public void testGetDefaultReadOnly() throws Exception {
		createTestEntityWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEntity mappedSuperclass = (EclipseLinkEntity) getJavaPersistentType().getMapping();
		ReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(false, readOnly.isDefaultReadOnly());
	}

	public void testSetSpecifiedReadOnly() throws Exception {
		createTestEntityWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEntity mappedSuperclass = (EclipseLinkEntity) getJavaPersistentType().getMapping();
		ReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(true, readOnly.isReadOnly());
		
		readOnly.setSpecifiedReadOnly(Boolean.FALSE);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		assertNull(typeResource.getSupportingAnnotation(ReadOnlyAnnotation.ANNOTATION_NAME));
		assertEquals(null, readOnly.getSpecifiedReadOnly());//Boolean.FALSE and null really mean the same thing since there are only 2 states in the java resource model

		readOnly.setSpecifiedReadOnly(Boolean.TRUE);
		assertNotNull(typeResource.getSupportingAnnotation(ReadOnlyAnnotation.ANNOTATION_NAME));
		assertEquals(Boolean.TRUE, readOnly.getSpecifiedReadOnly());

		readOnly.setSpecifiedReadOnly(null);
		assertNull(typeResource.getSupportingAnnotation(ReadOnlyAnnotation.ANNOTATION_NAME));
		assertEquals(null, readOnly.getSpecifiedReadOnly());//Boolean.FALSE and null really mean the same thing since there are only 2 states in the java resource model
	}
	
	public void testSpecifiedReadOnlyUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEntity mappedSuperclass = (EclipseLinkEntity) getJavaPersistentType().getMapping();
		ReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(Boolean.TRUE, readOnly.getSpecifiedReadOnly());
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.removeSupportingAnnotation(ReadOnlyAnnotation.ANNOTATION_NAME);
		
		assertEquals(null, readOnly.getSpecifiedReadOnly());
		assertEquals(false, readOnly.isDefaultReadOnly());
		
		typeResource.addSupportingAnnotation(ReadOnlyAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.TRUE, readOnly.getSpecifiedReadOnly());
	}


}
