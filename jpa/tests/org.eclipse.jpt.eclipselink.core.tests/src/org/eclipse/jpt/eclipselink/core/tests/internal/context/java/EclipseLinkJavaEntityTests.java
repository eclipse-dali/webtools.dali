/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTrackingType;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkEntity;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkReadOnly;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkChangeTrackingAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkCustomizerAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkReadOnlyAnnotation;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkJavaEntityTests extends EclipseLinkContextModelTestCase
{

	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithConvertAndCustomizerClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLink.CUSTOMIZER);
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
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLink.CHANGE_TRACKING);
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
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLink.READ_ONLY);
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

	public void testAttributeMappingKeyAllowed() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Entity entity = (Entity) getJavaPersistentType().getMapping();
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(EclipseLinkMappingKeys.BASIC_COLLECTION_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(EclipseLinkMappingKeys.BASIC_MAP_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(EclipseLinkMappingKeys.TRANSFORMATION_ATTRIBUTE_MAPPING_KEY));
		assertTrue(entity.attributeMappingKeyAllowed(EclipseLinkMappingKeys.VARIABLE_ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY));
	}


	public void testGetCustomizerClass() throws Exception {
		createTestEntityWithConvertAndCustomizerClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkCustomizer customizer = ((EclipseLinkEntity) getJavaPersistentType().getMapping()).getCustomizer();
		
		assertEquals("Foo", customizer.getSpecifiedCustomizerClass());
	}

	public void testSetCustomizerClass() throws Exception {
		createTestEntityWithConvertAndCustomizerClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkCustomizer customizer = ((EclipseLinkEntity) getJavaPersistentType().getMapping()).getCustomizer();
		assertEquals("Foo", customizer.getSpecifiedCustomizerClass());
		
		customizer.setSpecifiedCustomizerClass("Bar");
		assertEquals("Bar", customizer.getSpecifiedCustomizerClass());
			
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkCustomizerAnnotation customizerAnnotation = (EclipseLinkCustomizerAnnotation) typeResource.getAnnotation(EclipseLinkCustomizerAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", customizerAnnotation.getValue());

		
		customizer.setSpecifiedCustomizerClass(null);
		assertEquals(null, customizer.getSpecifiedCustomizerClass());
		customizerAnnotation = (EclipseLinkCustomizerAnnotation) typeResource.getAnnotation(EclipseLinkCustomizerAnnotation.ANNOTATION_NAME);		
		assertEquals(null, customizerAnnotation);


		customizer.setSpecifiedCustomizerClass("Bar");
		assertEquals("Bar", customizer.getSpecifiedCustomizerClass());
		customizerAnnotation = (EclipseLinkCustomizerAnnotation) typeResource.getAnnotation(EclipseLinkCustomizerAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", customizerAnnotation.getValue());
	}
	
	public void testGetCustomizerClassUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithConvertAndCustomizerClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkEntity entity = (EclipseLinkEntity) getJavaPersistentType().getMapping();
		EclipseLinkCustomizer customizer = entity.getCustomizer();

		assertEquals("Foo", customizer.getSpecifiedCustomizerClass());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkCustomizerAnnotation customizerAnnotation = (EclipseLinkCustomizerAnnotation) typeResource.getAnnotation(EclipseLinkCustomizerAnnotation.ANNOTATION_NAME);
		customizerAnnotation.setValue("Bar");
		getJpaProject().synchronizeContextModel();
		assertEquals("Bar", customizer.getSpecifiedCustomizerClass());
		
		typeResource.removeAnnotation(EclipseLinkCustomizerAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertEquals(null, customizer.getSpecifiedCustomizerClass());
		
		customizerAnnotation = (EclipseLinkCustomizerAnnotation) typeResource.addAnnotation(EclipseLinkCustomizerAnnotation.ANNOTATION_NAME);		
		getJpaProject().synchronizeContextModel();
		assertEquals(null, customizer.getSpecifiedCustomizerClass());
		
		customizerAnnotation.setValue("FooBar");
		getJpaProject().synchronizeContextModel();
		assertEquals("FooBar", customizer.getSpecifiedCustomizerClass());	
	}
	
	public void testGetChangeTracking() throws Exception {
		createTestEntityWithChangeTracking();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEntity entity = (EclipseLinkEntity) getJavaPersistentType().getMapping();
		EclipseLinkChangeTracking contextChangeTracking = entity.getChangeTracking();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkChangeTrackingAnnotation resourceChangeTracking = (EclipseLinkChangeTrackingAnnotation) typeResource.getAnnotation(EclipseLinkChangeTrackingAnnotation.ANNOTATION_NAME);
		
		// base annotated, test context value
		
		assertNull(resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getSpecifiedType());
		
		// change resource to ATTRIBUTE specifically, test context
		
		resourceChangeTracking.setValue(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.ATTRIBUTE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.ATTRIBUTE, resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, contextChangeTracking.getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, contextChangeTracking.getSpecifiedType());
		
		// change resource to OBJECT specifically, test context
		
		resourceChangeTracking.setValue(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.OBJECT);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.OBJECT, resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, contextChangeTracking.getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, contextChangeTracking.getSpecifiedType());
		
		// change resource to DEFERRED specifically, test context
		
		resourceChangeTracking.setValue(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.DEFERRED);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.DEFERRED, resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, contextChangeTracking.getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, contextChangeTracking.getSpecifiedType());
		
		// change resource to AUTO specifically, test context
		
		resourceChangeTracking.setValue(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.AUTO);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.AUTO, resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getSpecifiedType());
		
		// remove value from resource, test context
		
		resourceChangeTracking.setValue(null);
		getJpaProject().synchronizeContextModel();
		
		assertNull(resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getSpecifiedType());
		
		// remove annotation, text context
		
		typeResource.removeAnnotation(EclipseLinkChangeTrackingAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		assertNull(resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertNull(contextChangeTracking.getSpecifiedType());
	}
	
	public void testSetChangeTracking() throws Exception {
		createTestEntityWithChangeTracking();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEntity entity = (EclipseLinkEntity) getJavaPersistentType().getMapping();
		EclipseLinkChangeTracking contextChangeTracking = entity.getChangeTracking();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkChangeTrackingAnnotation resourceChangeTracking = (EclipseLinkChangeTrackingAnnotation) typeResource.getAnnotation(EclipseLinkChangeTrackingAnnotation.ANNOTATION_NAME);
		
		// base annotated, test resource value
		
		assertNull(resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getSpecifiedType());
		
		// change context to AUTO specifically, test resource
		
		contextChangeTracking.setSpecifiedType(EclipseLinkChangeTrackingType.AUTO);
		
		assertNull(resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getSpecifiedType());
		
		// change context to ATTRIBUTE specifically, test resource
		
		contextChangeTracking.setSpecifiedType(EclipseLinkChangeTrackingType.ATTRIBUTE);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.ATTRIBUTE, resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, contextChangeTracking.getSpecifiedType());
		
		// change context to OBJECT specifically, test resource
		
		contextChangeTracking.setSpecifiedType(EclipseLinkChangeTrackingType.OBJECT);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.OBJECT, resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, contextChangeTracking.getSpecifiedType());
		
		// change context to DEFERRED specifically, test resource
		
		contextChangeTracking.setSpecifiedType(EclipseLinkChangeTrackingType.DEFERRED);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.DEFERRED, resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, contextChangeTracking.getSpecifiedType());
		
		// change context to null, test resource
		
		contextChangeTracking.setSpecifiedType(null);
		
		assertNull(typeResource.getAnnotation(EclipseLinkChangeTrackingAnnotation.ANNOTATION_NAME));
		assertNull(contextChangeTracking.getSpecifiedType());
		
		// change context to AUTO specifically (this time from no annotation), test resource
		
		contextChangeTracking.setSpecifiedType(EclipseLinkChangeTrackingType.AUTO);
		resourceChangeTracking = (EclipseLinkChangeTrackingAnnotation) typeResource.getAnnotation(EclipseLinkChangeTrackingAnnotation.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.AUTO, resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getSpecifiedType());
	}
	
	public void testGetReadOnly() throws Exception {
		createTestEntityWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEntity mappedSuperclass = (EclipseLinkEntity) getJavaPersistentType().getMapping();
		EclipseLinkReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(true, readOnly.isReadOnly());
	}

	public void testGetSpecifiedReadOnly() throws Exception {
		createTestEntityWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEntity mappedSuperclass = (EclipseLinkEntity) getJavaPersistentType().getMapping();
		EclipseLinkReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(Boolean.TRUE, readOnly.getSpecifiedReadOnly());
	}

	//TODO test inheriting a default readonly from you superclass
	public void testGetDefaultReadOnly() throws Exception {
		createTestEntityWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEntity mappedSuperclass = (EclipseLinkEntity) getJavaPersistentType().getMapping();
		EclipseLinkReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(false, readOnly.isDefaultReadOnly());
	}

	public void testSetSpecifiedReadOnly() throws Exception {
		createTestEntityWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEntity mappedSuperclass = (EclipseLinkEntity) getJavaPersistentType().getMapping();
		EclipseLinkReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(true, readOnly.isReadOnly());
		
		readOnly.setSpecifiedReadOnly(Boolean.FALSE);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		assertNull(typeResource.getAnnotation(EclipseLinkReadOnlyAnnotation.ANNOTATION_NAME));
		assertNull(readOnly.getSpecifiedReadOnly());//Boolean.FALSE and null really mean the same thing since there are only 2 states in the java resource model

		readOnly.setSpecifiedReadOnly(Boolean.TRUE);
		assertNotNull(typeResource.getAnnotation(EclipseLinkReadOnlyAnnotation.ANNOTATION_NAME));
		assertEquals(Boolean.TRUE, readOnly.getSpecifiedReadOnly());

		readOnly.setSpecifiedReadOnly(null);
		assertNull(typeResource.getAnnotation(EclipseLinkReadOnlyAnnotation.ANNOTATION_NAME));
		assertNull(readOnly.getSpecifiedReadOnly());//Boolean.FALSE and null really mean the same thing since there are only 2 states in the java resource model
	}
	
	public void testSpecifiedReadOnlyUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEntity mappedSuperclass = (EclipseLinkEntity) getJavaPersistentType().getMapping();
		EclipseLinkReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(Boolean.TRUE, readOnly.getSpecifiedReadOnly());
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.removeAnnotation(EclipseLinkReadOnlyAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		assertNull(readOnly.getSpecifiedReadOnly());
		assertEquals(false, readOnly.isDefaultReadOnly());
		
		typeResource.addAnnotation(EclipseLinkReadOnlyAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertEquals(Boolean.TRUE, readOnly.getSpecifiedReadOnly());
	}


}
