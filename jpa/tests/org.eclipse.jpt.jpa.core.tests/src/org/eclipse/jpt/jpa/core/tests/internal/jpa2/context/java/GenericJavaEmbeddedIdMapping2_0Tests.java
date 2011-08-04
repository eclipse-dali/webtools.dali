/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaNullAttributeMapping;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.Access2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ElementCollection2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.ManyToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericJavaEmbeddedIdMapping2_0Tests extends Generic2_0ContextModelTestCase
{

	public static final String EMBEDDABLE_TYPE_NAME = "MyEmbeddable";
	public static final String FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME = PACKAGE_NAME + "." + EMBEDDABLE_TYPE_NAME;

	private ICompilationUnit createTestEntityWithEmbeddedIdMapping() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.EMBEDDED_ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@EmbeddedId").append(CR);
				sb.append(CR);
				sb.append("    private " + EMBEDDABLE_TYPE_NAME +" myEmbeddedId;").append(CR);
				sb.append(CR);
			}
		});
	}

	private void createEmbeddableType() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("import ");
				sb.append(JPA.EMBEDDABLE);
				sb.append(";");
				sb.append(CR);
				sb.append("@Embeddable");
				sb.append(CR);
				sb.append("public class ").append(EMBEDDABLE_TYPE_NAME).append(" {");
				sb.append(CR);
				sb.append("    private String city;").append(CR);
				sb.append(CR);
				sb.append("    private String state;").append(CR);
				sb.append(CR);
				sb.append("    ");
				sb.append("}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, EMBEDDABLE_TYPE_NAME + ".java", sourceWriter);
	}
	

	public GenericJavaEmbeddedIdMapping2_0Tests(String name) {
		super(name);
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof BasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		assertNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(((JavaEmbeddedMapping) persistentAttribute.getMapping()).getAttributeOverrideContainer().getOverrides().iterator().hasNext());
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		assertNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testDefaultEmbeddedIdMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof GenericJavaNullAttributeMapping);
		assertTrue(persistentAttribute.getMapping().isDefault());
		
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_EMBEDDABLE_TYPE_NAME);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertTrue(persistentAttribute.getMapping().isDefault());
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof VersionMapping);
	
		assertNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		
		assertNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TransientAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IdMapping);
		
		assertNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		
		assertNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToOneToOneMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToOneMapping);
		
		assertNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		
		assertNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		
		assertNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		
		assertNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToElementCollectionMapping() throws Exception {
		createTestEntityWithEmbeddedIdMapping();
		createEmbeddableType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EmbeddedIdMapping embeddedIdMapping = (EmbeddedIdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		resourceField.addAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		assertFalse(embeddedIdMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ElementCollectionMapping2_0);
		
		assertNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(0, AttributeOverrideAnnotation.ANNOTATION_NAME));
	}
}
