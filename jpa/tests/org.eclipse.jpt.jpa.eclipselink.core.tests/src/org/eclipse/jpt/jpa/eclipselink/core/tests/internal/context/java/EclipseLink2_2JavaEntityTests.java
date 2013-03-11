/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorValueAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.InheritanceAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkObjectTypeConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkReadOnlyAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkStructConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkTypeConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLink2_2ContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLink2_2JavaEntityTests extends EclipseLink2_2ContextModelTestCase
{
	protected static final String SUB_TYPE_NAME = "AnnotationTestTypeChild";
	protected static final String FULLY_QUALIFIED_SUB_TYPE_NAME = PACKAGE_NAME + "." + SUB_TYPE_NAME;


	public EclipseLink2_2JavaEntityTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}

	public void testMorphToMappedSuperclass() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		entity.getTable().setSpecifiedName("FOO");
		entity.addSpecifiedSecondaryTable(0);
		entity.addSpecifiedPrimaryKeyJoinColumn(0);
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		resourceType.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceType.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedDiscriminatorValue("asdf");
		entity.getDiscriminatorColumn().setSpecifiedName("BAR");
		entity.getGeneratorContainer().addTableGenerator();
		entity.getGeneratorContainer().addSequenceGenerator();
		entity.getIdClassReference().setSpecifiedIdClassName("myIdClass");
		entity.getQueryContainer().addNamedNativeQuery(0);
		entity.getQueryContainer().addNamedQuery(0);
		entity.getConverterContainer().addCustomConverter("customConverter", 0);
		entity.getConverterContainer().addCustomConverter("customConverter2", 1);
		entity.getConverterContainer().addObjectTypeConverter("customConverter", 0);
		entity.getConverterContainer().addObjectTypeConverter("objectTypeConverter2", 1);
		entity.getConverterContainer().addTypeConverter("customConverter", 0);
		entity.getConverterContainer().addTypeConverter("typeConverter2", 1);
		entity.getConverterContainer().addStructConverter("structConverter", 0);
		entity.getConverterContainer().addStructConverter("structConverter2", 1);
		entity.getReadOnly().setSpecifiedReadOnly(Boolean.TRUE);

		getJavaPersistentType().setMappingKey(MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY);
		assertTrue(getJavaPersistentType().getMapping() instanceof MappedSuperclass);

		assertNull(resourceType.getAnnotation(EntityAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(TableAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceType.getAnnotationsSize(SecondaryTableAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceType.getAnnotationsSize(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceType.getAnnotationsSize(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceType.getAnnotationsSize(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(InheritanceAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(DiscriminatorColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, NamedQueryAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, NamedNativeQueryAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, EclipseLinkConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(1, EclipseLinkConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(1, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, EclipseLinkStructConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(1, EclipseLinkStructConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(1, EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(EclipseLinkReadOnlyAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToEmbeddable() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		EclipseLinkJavaEntity entity = (EclipseLinkJavaEntity) getJavaPersistentType().getMapping();
		entity.getTable().setSpecifiedName("FOO");
		entity.addSpecifiedSecondaryTable(0);
		entity.addSpecifiedPrimaryKeyJoinColumn(0);
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		resourceType.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceType.addAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		entity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		entity.setSpecifiedDiscriminatorValue("asdf");
		entity.getDiscriminatorColumn().setSpecifiedName("BAR");
		entity.getGeneratorContainer().addTableGenerator();
		entity.getGeneratorContainer().addSequenceGenerator();
		entity.getIdClassReference().setSpecifiedIdClassName("myIdClass");
		entity.getQueryContainer().addNamedNativeQuery(0);
		entity.getQueryContainer().addNamedQuery(0);
		entity.getConverterContainer().addCustomConverter("customConverter", 0);
		entity.getConverterContainer().addCustomConverter("customConverter2", 1);
		entity.getConverterContainer().addObjectTypeConverter("objectTypeConverter", 0);
		entity.getConverterContainer().addObjectTypeConverter("objectTypeConverter2", 1);
		entity.getConverterContainer().addTypeConverter("ctypeConverter", 0);
		entity.getConverterContainer().addTypeConverter("typeConverter2", 1);
		entity.getConverterContainer().addStructConverter("structConverter", 0);
		entity.getConverterContainer().addStructConverter("structConverter2", 1);
		entity.getReadOnly().setSpecifiedReadOnly(Boolean.TRUE);
		getJavaPersistentType().setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		assertTrue(getJavaPersistentType().getMapping() instanceof Embeddable);

		assertNull(resourceType.getAnnotation(EntityAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(TableAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceType.getAnnotationsSize(SecondaryTableAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceType.getAnnotationsSize(PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceType.getAnnotationsSize(AttributeOverrideAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceType.getAnnotationsSize(AssociationOverrideAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(InheritanceAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(DiscriminatorValueAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(DiscriminatorColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceType.getAnnotationsSize(NamedQueryAnnotation.ANNOTATION_NAME));
		assertEquals(0, resourceType.getAnnotationsSize(NamedNativeQueryAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, EclipseLinkConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(1, EclipseLinkConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(1, EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, EclipseLinkStructConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(1, EclipseLinkStructConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(0, EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(1, EclipseLinkObjectTypeConverterAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(EclipseLinkReadOnlyAnnotation.ANNOTATION_NAME));
	}
}
