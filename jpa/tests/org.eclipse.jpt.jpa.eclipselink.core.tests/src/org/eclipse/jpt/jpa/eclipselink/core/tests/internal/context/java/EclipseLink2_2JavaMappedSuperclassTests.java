/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.jpa.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkObjectTypeConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkReadOnlyAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkStructConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkTypeConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLink2_2ContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLink2_2JavaMappedSuperclassTests extends EclipseLink2_2ContextModelTestCase
{
	protected static final String SUB_TYPE_NAME = "AnnotationTestTypeChild";
	protected static final String FULLY_QUALIFIED_SUB_TYPE_NAME = PACKAGE_NAME + "." + SUB_TYPE_NAME;

	private ICompilationUnit createTestMappedSuperclass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
			}
		});
	}


	public EclipseLink2_2JavaMappedSuperclassTests(String name) {
		super(name);
	}


	public void testMorphToEntity() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		EclipseLinkMappedSuperclass mappedSuperclass = (EclipseLinkMappedSuperclass) getJavaPersistentType().getMapping();
		mappedSuperclass.getIdClassReference().setSpecifiedIdClassName("myIdClass");
		mappedSuperclass.getConverterContainer().addCustomConverter(0);
		mappedSuperclass.getConverterContainer().addCustomConverter(1);
		mappedSuperclass.getConverterContainer().addObjectTypeConverter(0);
		mappedSuperclass.getConverterContainer().addObjectTypeConverter(1);
		mappedSuperclass.getConverterContainer().addTypeConverter(0);
		mappedSuperclass.getConverterContainer().addTypeConverter(1);
		mappedSuperclass.getConverterContainer().addStructConverter(0);
		mappedSuperclass.getConverterContainer().addStructConverter(1);
		mappedSuperclass.getGeneratorContainer().addTableGenerator();
		mappedSuperclass.getGeneratorContainer().addSequenceGenerator();
		mappedSuperclass.getReadOnly().setSpecifiedReadOnly(Boolean.TRUE);

		getJavaPersistentType().setMappingKey(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		assertTrue(getJavaPersistentType().getMapping() instanceof Entity);

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		assertNull(resourceType.getAnnotation(MappedSuperclassAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, EclipseLinkConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(1, EclipseLinkConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(1, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, EclipseLinkStructConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(1, EclipseLinkStructConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(1, EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(EclipseLinkReadOnlyAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToEmbeddable() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		EclipseLinkMappedSuperclass mappedSuperclass = (EclipseLinkMappedSuperclass) getJavaPersistentType().getMapping();
		mappedSuperclass.getIdClassReference().setSpecifiedIdClassName("myIdClass");
		mappedSuperclass.getConverterContainer().addCustomConverter(0);
		mappedSuperclass.getConverterContainer().addCustomConverter(1);
		mappedSuperclass.getConverterContainer().addObjectTypeConverter(0);
		mappedSuperclass.getConverterContainer().addObjectTypeConverter(1);
		mappedSuperclass.getConverterContainer().addTypeConverter(0);
		mappedSuperclass.getConverterContainer().addTypeConverter(1);
		mappedSuperclass.getConverterContainer().addStructConverter(0);
		mappedSuperclass.getConverterContainer().addStructConverter(1);
		mappedSuperclass.getGeneratorContainer().addTableGenerator();
		mappedSuperclass.getGeneratorContainer().addSequenceGenerator();
		mappedSuperclass.getReadOnly().setSpecifiedReadOnly(Boolean.TRUE);

		getJavaPersistentType().setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		assertTrue(getJavaPersistentType().getMapping() instanceof Embeddable);

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		assertNull(resourceType.getAnnotation(MappedSuperclassAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, EclipseLinkConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(1, EclipseLinkConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(1, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, EclipseLinkStructConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(1, EclipseLinkStructConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(1, EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(EclipseLinkReadOnlyAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToNull() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		EclipseLinkMappedSuperclass mappedSuperclass = (EclipseLinkMappedSuperclass) getJavaPersistentType().getMapping();
		mappedSuperclass.getIdClassReference().setSpecifiedIdClassName("myIdClass");
		mappedSuperclass.getConverterContainer().addCustomConverter(0);
		mappedSuperclass.getConverterContainer().addCustomConverter(1);
		mappedSuperclass.getConverterContainer().addObjectTypeConverter(0);
		mappedSuperclass.getConverterContainer().addObjectTypeConverter(1);
		mappedSuperclass.getConverterContainer().addTypeConverter(0);
		mappedSuperclass.getConverterContainer().addTypeConverter(1);
		mappedSuperclass.getConverterContainer().addStructConverter(0);
		mappedSuperclass.getConverterContainer().addStructConverter(1);

		getJavaPersistentType().setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
		assertTrue(getJavaPersistentType().getMapping() instanceof JavaNullTypeMapping);

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		assertNull(resourceType.getAnnotation(MappedSuperclassAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceType.getAnnotationsSize(EclipseLinkConverterAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceType.getAnnotationsSize(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceType.getAnnotationsSize(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceType.getAnnotationsSize(EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME));
	}
}
