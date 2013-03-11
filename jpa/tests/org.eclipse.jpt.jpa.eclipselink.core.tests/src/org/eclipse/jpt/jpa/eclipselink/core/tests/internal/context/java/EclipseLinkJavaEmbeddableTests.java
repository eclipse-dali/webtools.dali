/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTrackingType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkEmbeddable;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.CustomizerAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLinkJavaEmbeddableTests extends EclipseLinkContextModelTestCase
{
	
	private ICompilationUnit createTestEmbeddableWithConvertAndCustomizerClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.EMBEDDABLE, EclipseLink.CUSTOMIZER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable").append(CR);
				sb.append("    @Customizer(Foo.class");
			}
		});
	}
	
	private ICompilationUnit createTestEmbeddableWithChangeTracking() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.EMBEDDABLE, EclipseLink.CHANGE_TRACKING);
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
		
		EclipseLinkCustomizer customizer = ((EclipseLinkEmbeddable) getJavaPersistentType().getMapping()).getCustomizer();
		
		assertEquals("Foo", customizer.getSpecifiedCustomizerClass());
	}

	public void testSetCustomizerClass() throws Exception {
		createTestEmbeddableWithConvertAndCustomizerClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkCustomizer customizer = ((EclipseLinkEmbeddable) getJavaPersistentType().getMapping()).getCustomizer();
		assertEquals("Foo", customizer.getSpecifiedCustomizerClass());
		
		customizer.setSpecifiedCustomizerClass("Bar");
		assertEquals("Bar", customizer.getSpecifiedCustomizerClass());
			
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		CustomizerAnnotation customizerAnnotation = (CustomizerAnnotation) resourceType.getAnnotation(CustomizerAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", customizerAnnotation.getValue());

		
		customizer.setSpecifiedCustomizerClass(null);
		assertEquals(null, customizer.getSpecifiedCustomizerClass());
		customizerAnnotation = (CustomizerAnnotation) resourceType.getAnnotation(CustomizerAnnotation.ANNOTATION_NAME);		
		assertEquals(null, customizerAnnotation);


		customizer.setSpecifiedCustomizerClass("Bar");
		assertEquals("Bar", customizer.getSpecifiedCustomizerClass());
		customizerAnnotation = (CustomizerAnnotation) resourceType.getAnnotation(CustomizerAnnotation.ANNOTATION_NAME);		
		assertEquals("Bar", customizerAnnotation.getValue());
	}
	
	public void testGetCustomizerClassUpdatesFromResourceModelChange() throws Exception {
		createTestEmbeddableWithConvertAndCustomizerClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkEmbeddable embeddable = (EclipseLinkEmbeddable) getJavaPersistentType().getMapping();
		EclipseLinkCustomizer customizer = embeddable.getCustomizer();

		assertEquals("Foo", customizer.getSpecifiedCustomizerClass());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		CustomizerAnnotation customizerAnnotation = (CustomizerAnnotation) resourceType.getAnnotation(CustomizerAnnotation.ANNOTATION_NAME);
		customizerAnnotation.setValue("Bar");
		getJpaProject().synchronizeContextModel();
		assertEquals("Bar", customizer.getSpecifiedCustomizerClass());
		
		resourceType.removeAnnotation(CustomizerAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertEquals(null, customizer.getSpecifiedCustomizerClass());
		
		customizerAnnotation = (CustomizerAnnotation) resourceType.addAnnotation(CustomizerAnnotation.ANNOTATION_NAME);		
		getJpaProject().synchronizeContextModel();
		assertEquals(null, customizer.getSpecifiedCustomizerClass());
		
		customizerAnnotation.setValue("FooBar");
		getJpaProject().synchronizeContextModel();
		assertEquals("FooBar", customizer.getSpecifiedCustomizerClass());	
	}
	
	public void testGetChangeTracking() throws Exception {
		createTestEmbeddableWithChangeTracking();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEmbeddable embeddable = (EclipseLinkEmbeddable) getJavaPersistentType().getMapping();
		EclipseLinkChangeTracking contextChangeTracking = embeddable.getChangeTracking();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		ChangeTrackingAnnotation resourceChangeTracking = (ChangeTrackingAnnotation) resourceType.getAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME);
		
		// base annotated, test context value
		assertNull(resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getSpecifiedType());
		
		// change resource to ATTRIBUTE specifically, test context
		resourceChangeTracking.setValue(org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingType.ATTRIBUTE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingType.ATTRIBUTE, resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, contextChangeTracking.getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, contextChangeTracking.getSpecifiedType());
		
		// change resource to OBJECT specifically, test context
		resourceChangeTracking.setValue(org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingType.OBJECT);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingType.OBJECT, resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, contextChangeTracking.getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, contextChangeTracking.getSpecifiedType());
		
		// change resource to DEFERRED specifically, test context
		resourceChangeTracking.setValue(org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingType.DEFERRED);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingType.DEFERRED, resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, contextChangeTracking.getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, contextChangeTracking.getSpecifiedType());
		
		// change resource to AUTO specifically, test context
		resourceChangeTracking.setValue(org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingType.AUTO);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingType.AUTO, resourceChangeTracking.getValue());
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
		resourceType.removeAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		assertNull(resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getType());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getDefaultType());
		assertNull(contextChangeTracking.getSpecifiedType());
	}
	
	public void testSetChangeTracking() throws Exception {
		createTestEmbeddableWithChangeTracking();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkEmbeddable embeddable = (EclipseLinkEmbeddable) getJavaPersistentType().getMapping();
		EclipseLinkChangeTracking contextChangeTracking = embeddable.getChangeTracking();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		ChangeTrackingAnnotation resourceChangeTracking = (ChangeTrackingAnnotation) resourceType.getAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME);
		
		// base annotated, test resource value
		
		assertNull(resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getSpecifiedType());
		
		// change context to AUTO specifically, test resource
		
		contextChangeTracking.setSpecifiedType(EclipseLinkChangeTrackingType.AUTO);
		
		assertNull(resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getSpecifiedType());
		
		// change context to ATTRIBUTE specifically, test resource
		
		contextChangeTracking.setSpecifiedType(EclipseLinkChangeTrackingType.ATTRIBUTE);
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingType.ATTRIBUTE, resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.ATTRIBUTE, contextChangeTracking.getSpecifiedType());
		
		// change context to OBJECT specifically, test resource
		
		contextChangeTracking.setSpecifiedType(EclipseLinkChangeTrackingType.OBJECT);
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingType.OBJECT, resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.OBJECT, contextChangeTracking.getSpecifiedType());
		
		// change context to DEFERRED specifically, test resource
		
		contextChangeTracking.setSpecifiedType(EclipseLinkChangeTrackingType.DEFERRED);
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingType.DEFERRED, resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.DEFERRED, contextChangeTracking.getSpecifiedType());
		
		// change context to null, test resource
		
		contextChangeTracking.setSpecifiedType(null);
		
		assertNull(resourceType.getAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME));
		assertNull(contextChangeTracking.getSpecifiedType());
		
		// change context to AUTO specifically (this time from no annotation), test resource
		
		contextChangeTracking.setSpecifiedType(EclipseLinkChangeTrackingType.AUTO);
		resourceChangeTracking = (ChangeTrackingAnnotation) resourceType.getAnnotation(ChangeTrackingAnnotation.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingType.AUTO, resourceChangeTracking.getValue());
		assertEquals(EclipseLinkChangeTrackingType.AUTO, contextChangeTracking.getSpecifiedType());
	}
}