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
import org.eclipse.jpt.eclipselink.core.context.ChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.ChangeTrackingType;
import org.eclipse.jpt.eclipselink.core.context.Customizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkEmbeddable;
import org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.CustomizerAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkJavaEmbeddableTests extends EclipseLinkJavaContextModelTestCase
{

	private void createCustomizerAnnotation() throws Exception {
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "Customizer", "Class value();");		
	}

	private void createChangeTrackingAnnotation() throws Exception{
		createChangeTrackingTypeEnum();
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "ChangeTracking", "ChangeTrackingType value() default ChangeTrackingType.AUTO");		
	}
	
	private void createChangeTrackingTypeEnum() throws Exception {
		this.createEnumAndMembers(ECLIPSELINK_ANNOTATIONS_PACKAGE_NAME, "ChangeTrackingType", "ATTRIBUTE, OBJECT, DEFERRED, AUTO;");	
	}
	
	private ICompilationUnit createTestEmbeddableWithConvertAndCustomizerClass() throws Exception {
		createCustomizerAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDABLE, EclipseLinkJPA.CUSTOMIZER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable").append(CR);
				sb.append("    @Customizer(Foo.class");
			}
		});
	}
	
	private ICompilationUnit createTestEmbeddableWithChangeTracking() throws Exception {
		createChangeTrackingAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.EMBEDDABLE, EclipseLinkJPA.CHANGE_TRACKING);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable").append(CR);
				sb.append("    @ChangeTracking").append(CR);
			}
		});
	}

	public EclipseLinkJavaEmbeddableTests(String name) {
		super(name);
	}


	public void testGetCustomizerClass() throws Exception {
		createTestEmbeddableWithConvertAndCustomizerClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Customizer customizer = ((EclipseLinkEmbeddable) javaPersistentType().getMapping()).getCustomizer();
		
		assertEquals("Foo", customizer.getSpecifiedCustomizerClass());
	}

	public void testSetCustomizerClass() throws Exception {
		createTestEmbeddableWithConvertAndCustomizerClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Customizer customizer = ((EclipseLinkEmbeddable) javaPersistentType().getMapping()).getCustomizer();
		assertEquals("Foo", customizer.getSpecifiedCustomizerClass());
		
		customizer.setSpecifiedCustomizerClass("Bar");
		assertEquals("Bar", customizer.getSpecifiedCustomizerClass());
			
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
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
		createTestEmbeddableWithConvertAndCustomizerClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkEmbeddable embeddable = (EclipseLinkEmbeddable) javaPersistentType().getMapping();
		Customizer customizer = embeddable.getCustomizer();

		assertEquals("Foo", customizer.getSpecifiedCustomizerClass());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
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
	
	public void testHasChangeTracking() throws Exception {
		createTestEmbeddableWithChangeTracking();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEmbeddable embeddable = (EclipseLinkEmbeddable) javaPersistentType().getMapping();
		ChangeTracking changeTracking = embeddable.getChangeTracking();
		assertEquals(true, changeTracking.hasChangeTracking());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.removeSupportingAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME);
		
		assertEquals(false, changeTracking.hasChangeTracking());
		
		typeResource.addSupportingAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME);
		assertEquals(true, changeTracking.hasChangeTracking());
	}
	
	public void testSetChangeTracking() throws Exception {
		createTestEmbeddableWithChangeTracking();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEmbeddable embeddable = (EclipseLinkEmbeddable) javaPersistentType().getMapping();
		ChangeTracking changeTracking = embeddable.getChangeTracking();
		assertEquals(true, changeTracking.hasChangeTracking());
		
		changeTracking.setChangeTracking(false);
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		assertNull(typeResource.getSupportingAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME));
		assertFalse(changeTracking.hasChangeTracking());
		
		changeTracking.setChangeTracking(true);
		assertNotNull(typeResource.getSupportingAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME));
		assertTrue(changeTracking.hasChangeTracking());
	}
	
	public void testGetSpecifiedChangeTracking() throws Exception {
		createTestEmbeddableWithChangeTracking();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEmbeddable embeddable = (EclipseLinkEmbeddable) javaPersistentType().getMapping();
		ChangeTracking changeTracking = embeddable.getChangeTracking();
		assertEquals(null, changeTracking.getSpecifiedChangeTrackingType());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		ChangeTrackingAnnotation changeTrackingAnnotation = (ChangeTrackingAnnotation) typeResource.getSupportingAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME);
		changeTrackingAnnotation.setValue(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.OBJECT);
		
		assertEquals(ChangeTrackingType.OBJECT, changeTracking.getSpecifiedChangeTrackingType());

		changeTrackingAnnotation.setValue(null);
		assertEquals(null, changeTracking.getSpecifiedChangeTrackingType());

		changeTrackingAnnotation.setValue(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.DEFERRED);
		assertEquals(ChangeTrackingType.DEFERRED, changeTracking.getSpecifiedChangeTrackingType());
		
		typeResource.removeSupportingAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME);
		assertEquals(null, changeTracking.getSpecifiedChangeTrackingType());
	}
	
	public void testSetSpecifiedChangeTracking() throws Exception {
		createTestEmbeddableWithChangeTracking();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEmbeddable embeddable = (EclipseLinkEmbeddable) javaPersistentType().getMapping();
		ChangeTracking changeTracking = embeddable.getChangeTracking();
		assertEquals(null, changeTracking.getSpecifiedChangeTrackingType());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		ChangeTrackingAnnotation changeTrackingAnnotation = (ChangeTrackingAnnotation) typeResource.getSupportingAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME);
		assertEquals(null, changeTrackingAnnotation.getValue());
		
		changeTracking.setSpecifiedChangeTrackingType(ChangeTrackingType.OBJECT);	
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.OBJECT, changeTrackingAnnotation.getValue());

		changeTracking.setSpecifiedChangeTrackingType(null);
		assertEquals(null, changeTrackingAnnotation.getValue());
		
		changeTracking.setSpecifiedChangeTrackingType(ChangeTrackingType.ATTRIBUTE);	
		assertEquals(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.ATTRIBUTE, changeTrackingAnnotation.getValue());
		
		changeTracking.setChangeTracking(false);
		assertNull(typeResource.getSupportingAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetDefaultChangeTracking() throws Exception {
		createTestEmbeddableWithChangeTracking();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEmbeddable embeddable = (EclipseLinkEmbeddable) javaPersistentType().getMapping();
		ChangeTracking changeTracking = embeddable.getChangeTracking();
		assertEquals(ChangeTracking.DEFAULT_CHANGE_TRACKING_TYPE, changeTracking.getDefaultChangeTrackingType());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.removeSupportingAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME);
		assertEquals(ChangeTracking.DEFAULT_CHANGE_TRACKING_TYPE, changeTracking.getDefaultChangeTrackingType());
		
		changeTracking.setSpecifiedChangeTrackingType(ChangeTrackingType.ATTRIBUTE);	
		assertEquals(ChangeTracking.DEFAULT_CHANGE_TRACKING_TYPE, changeTracking.getDefaultChangeTrackingType());
	}
	
	public void testGetChangeTracking() throws Exception {
		createTestEmbeddableWithChangeTracking();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEmbeddable embeddable = (EclipseLinkEmbeddable) javaPersistentType().getMapping();
		ChangeTracking changeTracking = embeddable.getChangeTracking();
		assertEquals(ChangeTracking.DEFAULT_CHANGE_TRACKING_TYPE, changeTracking.getChangeTrackingType());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		typeResource.removeSupportingAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME);
		assertEquals(ChangeTracking.DEFAULT_CHANGE_TRACKING_TYPE, changeTracking.getChangeTrackingType());
		
		changeTracking.setSpecifiedChangeTrackingType(ChangeTrackingType.DEFERRED);	
		assertEquals(ChangeTrackingType.DEFERRED, changeTracking.getChangeTrackingType());
	}
}
