/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.tests.internal.projects.JavaProjectTestHarness.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.CacheableReference2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.SharedCacheMode2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.CacheableAnnotation2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTrackingType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkReadOnly;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ChangeTrackingAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.CustomizerAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ReadOnlyAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLink2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLink2_0JavaMappedSuperclassTests extends EclipseLink2_0ContextModelTestCase
{
	protected static final String SUB_TYPE_NAME = "AnnotationTestTypeChild";
	protected static final String FULLY_QUALIFIED_SUB_TYPE_NAME = PACKAGE_NAME + "." + SUB_TYPE_NAME;

	private ICompilationUnit createTestMappedSuperclass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
			}
		});
	}

	private void createTestSubType() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.MAPPED_SUPERCLASS);
					sb.append(";");
					sb.append(CR);
				sb.append("@MappedSuperclass");
				sb.append(CR);
				sb.append("public class ").append("AnnotationTestTypeChild").append(" ");
				sb.append("extends " + TYPE_NAME + " ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
	}

	private ICompilationUnit createTestMappedSuperclassWithReadOnly() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS, EclipseLink.READ_ONLY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
				sb.append("@ReadOnly").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestMappedSuperclassWithConvertAndCustomizerClass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS, EclipseLink.CUSTOMIZER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
				sb.append("    @Customizer(Foo.class");
			}
		});
	}
	
	private ICompilationUnit createTestMappedSuperclassWithChangeTracking() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS, EclipseLink.CHANGE_TRACKING);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
				sb.append("    @ChangeTracking").append(CR);
			}
		});
	}

	public EclipseLink2_0JavaMappedSuperclassTests(String name) {
		super(name);
	}


	public void testGetReadOnly() throws Exception {
		createTestMappedSuperclassWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkMappedSuperclass mappedSuperclass = (EclipseLinkMappedSuperclass) getJavaPersistentType().getMapping();
		EclipseLinkReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(true, readOnly.isReadOnly());
	}

	public void testGetSpecifiedReadOnly() throws Exception {
		createTestMappedSuperclassWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkMappedSuperclass mappedSuperclass = (EclipseLinkMappedSuperclass) getJavaPersistentType().getMapping();
		EclipseLinkReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(Boolean.TRUE, readOnly.getSpecifiedReadOnly());
	}

	//TODO test inheriting a default readonly from you superclass
	public void testGetDefaultReadOnly() throws Exception {
		createTestMappedSuperclassWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkMappedSuperclass mappedSuperclass = (EclipseLinkMappedSuperclass) getJavaPersistentType().getMapping();
		EclipseLinkReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(false, readOnly.isDefaultReadOnly());
	}

	public void testSetSpecifiedReadOnly() throws Exception {
		createTestMappedSuperclassWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkMappedSuperclass mappedSuperclass = (EclipseLinkMappedSuperclass) getJavaPersistentType().getMapping();
		EclipseLinkReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(true, readOnly.isReadOnly());
		
		readOnly.setSpecifiedReadOnly(Boolean.FALSE);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		assertNull(resourceType.getAnnotation(ReadOnlyAnnotation.ANNOTATION_NAME));
		assertEquals(null, readOnly.getSpecifiedReadOnly());//Boolean.FALSE and null really mean the same thing since there are only 2 states in the java resource model

		readOnly.setSpecifiedReadOnly(Boolean.TRUE);
		assertNotNull(resourceType.getAnnotation(ReadOnlyAnnotation.ANNOTATION_NAME));
		assertEquals(Boolean.TRUE, readOnly.getSpecifiedReadOnly());

		readOnly.setSpecifiedReadOnly(null);
		assertNull(resourceType.getAnnotation(ReadOnlyAnnotation.ANNOTATION_NAME));
		assertEquals(null, readOnly.getSpecifiedReadOnly());//Boolean.FALSE and null really mean the same thing since there are only 2 states in the java resource model
	}
	
	public void testSpecifiedReadOnlyUpdatesFromResourceModelChange() throws Exception {
		createTestMappedSuperclassWithReadOnly();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkMappedSuperclass mappedSuperclass = (EclipseLinkMappedSuperclass) getJavaPersistentType().getMapping();
		EclipseLinkReadOnly readOnly = mappedSuperclass.getReadOnly();
		assertEquals(Boolean.TRUE, readOnly.getSpecifiedReadOnly());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		resourceType.removeAnnotation(ReadOnlyAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(null, readOnly.getSpecifiedReadOnly());
		assertEquals(false, readOnly.isDefaultReadOnly());
		
		resourceType.addAnnotation(ReadOnlyAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertEquals(Boolean.TRUE, readOnly.getSpecifiedReadOnly());
	}

	public void testGetCustomizerClass() throws Exception {
		createTestMappedSuperclassWithConvertAndCustomizerClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkCustomizer customizer = ((EclipseLinkMappedSuperclass) getJavaPersistentType().getMapping()).getCustomizer();
		
		assertEquals("Foo", customizer.getSpecifiedCustomizerClass());
	}

	public void testSetCustomizerClass() throws Exception {
		createTestMappedSuperclassWithConvertAndCustomizerClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkCustomizer customizer = ((EclipseLinkMappedSuperclass) getJavaPersistentType().getMapping()).getCustomizer();
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
		createTestMappedSuperclassWithConvertAndCustomizerClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		EclipseLinkMappedSuperclass mappedSuperclass = (EclipseLinkMappedSuperclass) getJavaPersistentType().getMapping();
		EclipseLinkCustomizer customizer = mappedSuperclass.getCustomizer();

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
		createTestMappedSuperclassWithChangeTracking();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkMappedSuperclass mappedSuperclass = (EclipseLinkMappedSuperclass) getJavaPersistentType().getMapping();
		EclipseLinkChangeTracking contextChangeTracking = mappedSuperclass.getChangeTracking();
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
		createTestMappedSuperclassWithChangeTracking();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		EclipseLinkMappedSuperclass mappedSuperclass = (EclipseLinkMappedSuperclass) getJavaPersistentType().getMapping();
		EclipseLinkChangeTracking contextChangeTracking = mappedSuperclass.getChangeTracking();
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
	
	
	public void testSetSpecifiedCacheable() throws Exception {
		ICompilationUnit cu = createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 cacheable = ((CacheableReference2_0) getJavaPersistentType().getMapping()).getCacheable();
		CacheableAnnotation2_0 cacheableAnnotation = (CacheableAnnotation2_0) getJavaPersistentType().getJavaResourceType().getAnnotation(JPA2_0.CACHEABLE);
		assertEquals(null, cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation);
		
		cacheable.setSpecifiedCacheable(Boolean.FALSE);
		cacheableAnnotation = (CacheableAnnotation2_0) getJavaPersistentType().getJavaResourceType().getAnnotation(JPA2_0.CACHEABLE);		
		assertEquals(Boolean.FALSE, cacheable.getSpecifiedCacheable());
		assertEquals(Boolean.FALSE, cacheableAnnotation.getValue());
		assertSourceContains("@Cacheable(false)", cu);
		
		cacheable.setSpecifiedCacheable(Boolean.TRUE);
		cacheableAnnotation = (CacheableAnnotation2_0) getJavaPersistentType().getJavaResourceType().getAnnotation(JPA2_0.CACHEABLE);		
		assertEquals(Boolean.TRUE, cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation.getValue());
		assertSourceContains("@Cacheable", cu);
		
		cacheable.setSpecifiedCacheable(null);
		cacheableAnnotation = (CacheableAnnotation2_0) getJavaPersistentType().getJavaResourceType().getAnnotation(JPA2_0.CACHEABLE);		
		assertEquals(null, cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation);
		assertSourceDoesNotContain("@Cacheable", cu);
	}
	
	public void testGetSpecifiedCacheable() throws Exception {
		ICompilationUnit cu = createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 cacheable = ((CacheableReference2_0) getJavaPersistentType().getMapping()).getCacheable();
		CacheableAnnotation2_0 cacheableAnnotation = (CacheableAnnotation2_0) getJavaPersistentType().getJavaResourceType().getAnnotation(JPA2_0.CACHEABLE);
		assertEquals(null, cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation);
		
		getJavaPersistentType().getJavaResourceType().addAnnotation(JPA2_0.CACHEABLE);
		getJpaProject().synchronizeContextModel();
		cacheableAnnotation = (CacheableAnnotation2_0) getJavaPersistentType().getJavaResourceType().getAnnotation(JPA2_0.CACHEABLE);
		assertEquals(Boolean.TRUE, cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation.getValue());
		assertSourceContains("@Cacheable", cu);

		cacheableAnnotation.setValue(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();
		assertEquals(Boolean.FALSE, cacheable.getSpecifiedCacheable());
		assertEquals(Boolean.FALSE, cacheableAnnotation.getValue());
		assertSourceContains("@Cacheable(false)", cu);
		
		cacheableAnnotation.setValue(Boolean.TRUE);
		getJpaProject().synchronizeContextModel();
		assertEquals(Boolean.TRUE, cacheable.getSpecifiedCacheable());
		assertEquals(Boolean.TRUE, cacheableAnnotation.getValue());
		assertSourceContains("@Cacheable(true)", cu);
		
		cacheableAnnotation.setValue(null);
		getJpaProject().synchronizeContextModel();
		assertEquals(Boolean.TRUE, cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation.getValue());
		assertSourceContains("@Cacheable", cu);

		getJavaPersistentType().getJavaResourceType().removeAnnotation(JPA2_0.CACHEABLE);
		getJpaProject().synchronizeContextModel();
		cacheableAnnotation = (CacheableAnnotation2_0) getJavaPersistentType().getJavaResourceType().getAnnotation(JPA2_0.CACHEABLE);		
		assertEquals(null,  cacheable.getSpecifiedCacheable());
		assertEquals(null, cacheableAnnotation);
		assertSourceDoesNotContain("@Cacheable", cu);
	}
	
	public void testIsDefaultCacheable() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 cacheable = ((CacheableReference2_0) getJavaPersistentType().getMapping()).getCacheable();
		PersistenceUnit2_0 persistenceUnit = getPersistenceUnit();
		assertEquals(SharedCacheMode2_0.DISABLE_SELECTIVE, persistenceUnit.getSharedCacheMode());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		persistenceUnit.setSpecifiedSharedCacheMode(SharedCacheMode2_0.ALL);
		assertEquals(true, cacheable.isDefaultCacheable());
		
		persistenceUnit.setSpecifiedSharedCacheMode(SharedCacheMode2_0.NONE);
		assertEquals(false, cacheable.isDefaultCacheable());

		persistenceUnit.setSpecifiedSharedCacheMode(SharedCacheMode2_0.ENABLE_SELECTIVE);
		assertEquals(false, cacheable.isDefaultCacheable());

		persistenceUnit.setSpecifiedSharedCacheMode(SharedCacheMode2_0.DISABLE_SELECTIVE);
		assertEquals(true, cacheable.isDefaultCacheable());
		
		persistenceUnit.setSpecifiedSharedCacheMode(SharedCacheMode2_0.UNSPECIFIED);
		assertEquals(true, cacheable.isDefaultCacheable());
	}
	
	public void testInheritedIsDefaultCacheable() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(FULLY_QUALIFIED_SUB_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Cacheable2_0 subCacheable = ((CacheableReference2_0) getJavaPersistentType().getMapping()).getCacheable();
		Cacheable2_0 cacheable = ((CacheableReference2_0) getJavaPersistentType().getSuperPersistentType().getMapping()).getCacheable();
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());

		PersistenceUnit2_0 persistenceUnit2_0 = getPersistenceUnit();
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode2_0.NONE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());

		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(null);
		cacheable.setSpecifiedCacheable(Boolean.FALSE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode2_0.DISABLE_SELECTIVE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
				
		cacheable.setSpecifiedCacheable(Boolean.FALSE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(true, cacheable.isDefaultCacheable());
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode2_0.ENABLE_SELECTIVE);
		assertEquals(false, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		cacheable.setSpecifiedCacheable(Boolean.TRUE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
		
		
		persistenceUnit2_0.setSpecifiedSharedCacheMode(SharedCacheMode2_0.NONE);
		assertEquals(true, subCacheable.isDefaultCacheable());
		assertEquals(false, cacheable.isDefaultCacheable());
	}
}
