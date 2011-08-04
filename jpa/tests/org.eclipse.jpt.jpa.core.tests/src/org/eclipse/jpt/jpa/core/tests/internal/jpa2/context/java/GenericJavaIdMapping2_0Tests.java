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
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
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
import org.eclipse.jpt.jpa.core.context.TemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.Access2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ElementCollection2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.IdAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.ManyToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericJavaIdMapping2_0Tests extends Generic2_0ContextModelTestCase
{

	private ICompilationUnit createTestEntityWithIdMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
			}
		});
	}
	
	public GenericJavaIdMapping2_0Tests(String name) {
		super(name);
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(TemporalConverter.class);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((BasicMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((TemporalConverter) ((BasicMapping) persistentAttribute.getMapping()).getConverter()).getTemporalType());
		
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(TemporalConverter.class);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((BasicMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((TemporalConverter) ((BasicMapping) persistentAttribute.getMapping()).getConverter()).getTemporalType());
		
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(TemporalConverter.class);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((VersionMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((TemporalConverter) ((VersionMapping) persistentAttribute.getMapping()).getConverter()).getTemporalType());
		
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(TemporalConverter.class);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(TemporalConverter.class);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TransientAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(TemporalConverter.class);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToOneToOneMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(TemporalConverter.class);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToOneMapping);
		
		assertNotNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));//OneToOne with Id annotation is valid in JPA 2.0
		assertNotNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(TemporalConverter.class);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(TemporalConverter.class);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		
		assertNotNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));//ManyToOne with Id annotation is valid in JPA 2.0
		assertNotNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(TemporalConverter.class);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}
	

	public void testMorphToElementCollectionMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(TemporalConverter.class);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		resourceField.addAnnotation(Access2_0Annotation.ANNOTATION_NAME);
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ElementCollectionMapping2_0);
		
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(Access2_0Annotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}
}
