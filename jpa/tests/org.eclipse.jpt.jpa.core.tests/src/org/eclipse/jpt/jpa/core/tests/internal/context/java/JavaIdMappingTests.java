/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
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
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
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
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class JavaIdMappingTests extends ContextModelTestCase
{

	private ICompilationUnit createTestEntityWithIdMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID);
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

	private ICompilationUnit createTestEntityWithTemporal() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, JPA.TEMPORAL, JPA.TEMPORAL_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("@Temporal(TemporalType.TIMESTAMP)").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithIdMappingGeneratedValue() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, JPA.GENERATED_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("@GeneratedValue").append(CR);
			}
		});
	}
	
	public JavaIdMappingTests(String name) {
		super(name);
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((BasicMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((BaseTemporalConverter) ((BasicMapping) persistentAttribute.getMapping()).getConverter()).getTemporalType());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
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
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((BasicMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((BaseTemporalConverter) ((BasicMapping) persistentAttribute.getMapping()).getConverter()).getTemporalType());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
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
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((VersionMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((BaseTemporalConverter) ((VersionMapping) persistentAttribute.getMapping()).getConverter()).getTemporalType());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(VersionAnnotation.ANNOTATION_NAME));
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
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
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
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(TransientAnnotation.ANNOTATION_NAME));
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
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
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
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToOneMapping);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
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
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
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
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
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
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNull(resourceField.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceField.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetTemporal() throws Exception {
		createTestEntityWithTemporal();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();

		assertEquals(TemporalType.TIMESTAMP, ((BaseTemporalConverter) idMapping.getConverter()).getTemporalType());
	}

	public void testSetTemporal() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		assertNull(idMapping.getConverter().getType());
		
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TemporalAnnotation temporal = (TemporalAnnotation) resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.jpa.core.resource.java.TemporalType.TIME, temporal.getValue());
		
		idMapping.setConverter(null);
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetTemporalUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();

		assertNull(idMapping.getConverter().getType());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TemporalAnnotation temporal = (TemporalAnnotation) resourceField.addAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		temporal.setValue(org.eclipse.jpt.jpa.core.resource.java.TemporalType.DATE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(TemporalType.DATE, ((BaseTemporalConverter) idMapping.getConverter()).getTemporalType());
		
		resourceField.removeAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		assertNull(idMapping.getConverter().getType());
		assertFalse(idMapping.isDefault());
		assertSame(idMapping, persistentAttribute.getMapping());
	}
	
	public void testGetColumn() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		
		assertNull(idMapping.getColumn().getSpecifiedName());
		assertEquals("id", idMapping.getColumn().getName());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ColumnAnnotation column = (ColumnAnnotation) resourceField.addAnnotation(JPA.COLUMN);
		column.setName("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("foo", idMapping.getColumn().getSpecifiedName());
		assertEquals("foo", idMapping.getColumn().getName());
		assertEquals("id", idMapping.getColumn().getDefaultName());
	}

	public void testGetSequenceGenerator() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		assertNull(idMapping.getGeneratorContainer().getSequenceGenerator());
		assertEquals(0, idMapping.getPersistenceUnit().getGeneratorsSize());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(JPA.SEQUENCE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		assertNotNull(idMapping.getGeneratorContainer().getSequenceGenerator());
		assertEquals(2, resourceField.getAnnotationsSize());
		assertEquals(1, idMapping.getPersistenceUnit().getGeneratorsSize());
		
		idMapping.getGeneratorContainer().getSequenceGenerator().setName("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals(1, idMapping.getPersistenceUnit().getGeneratorsSize());
	}
	
	public void testAddSequenceGenerator() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		
		assertNull(idMapping.getGeneratorContainer().getSequenceGenerator());
		
		idMapping.getGeneratorContainer().addSequenceGenerator();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		assertNotNull(resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR));
		assertNotNull(idMapping.getGeneratorContainer().getSequenceGenerator());
		
		//try adding another sequence generator, should get an IllegalStateException
		try {
				idMapping.getGeneratorContainer().addSequenceGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveSequenceGenerator() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(JPA.SEQUENCE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		
		
		idMapping.getGeneratorContainer().removeSequenceGenerator();
		
		assertNull(idMapping.getGeneratorContainer().getSequenceGenerator());
		assertNull(resourceField.getAnnotation(JPA.SEQUENCE_GENERATOR));

		//try removing the sequence generator again, should get an IllegalStateException
		try {
			idMapping.getGeneratorContainer().removeSequenceGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testGetTableGenerator() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		assertNull(idMapping.getGeneratorContainer().getTableGenerator());
		assertEquals(0, idMapping.getPersistenceUnit().getGeneratorsSize());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(JPA.TABLE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		assertNotNull(idMapping.getGeneratorContainer().getTableGenerator());		
		assertEquals(2, resourceField.getAnnotationsSize());
		assertEquals(1, idMapping.getPersistenceUnit().getGeneratorsSize());
		
		idMapping.getGeneratorContainer().getTableGenerator().setName("foo");
		assertEquals(1, idMapping.getPersistenceUnit().getGeneratorsSize());
	}
	
	public void testAddTableGenerator() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator());
		
		idMapping.getGeneratorContainer().addTableGenerator();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		assertNotNull(resourceField.getAnnotation(JPA.TABLE_GENERATOR));
		assertNotNull(idMapping.getGeneratorContainer().getTableGenerator());
		
		//try adding another table generator, should get an IllegalStateException
		try {
			idMapping.getGeneratorContainer().addTableGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveTableGenerator() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(JPA.TABLE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		
		
		idMapping.getGeneratorContainer().removeTableGenerator();
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator());
		assertNull(resourceField.getAnnotation(JPA.TABLE_GENERATOR));
		
		//try removing the table generator again, should get an IllegalStateException
		try {
			idMapping.getGeneratorContainer().removeTableGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testGetGeneratedValue() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		
		assertNull(idMapping.getGeneratedValue());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(JPA.GENERATED_VALUE);
		getJpaProject().synchronizeContextModel();
		
		assertNotNull(idMapping.getGeneratedValue());		
		assertEquals(2, resourceField.getAnnotationsSize());
	}
	
	public void testGetGeneratedValue2() throws Exception {
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		createTestEntityWithIdMappingGeneratedValue();
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();

		assertNotNull(idMapping.getGeneratedValue());
		assertEquals(2, resourceField.getAnnotationsSize());
	}
	
	public void testAddGeneratedValue() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		
		assertNull(idMapping.getGeneratedValue());
		
		idMapping.addGeneratedValue();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
	
		assertNotNull(resourceField.getAnnotation(JPA.GENERATED_VALUE));
		assertNotNull(idMapping.getGeneratedValue());
		
		//try adding another generated value, should get an IllegalStateException
		try {
			idMapping.addGeneratedValue();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveGeneratedValue() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(JPA.GENERATED_VALUE);
		getJpaProject().synchronizeContextModel();
		
		idMapping.removeGeneratedValue();
		
		assertNull(idMapping.getGeneratedValue());
		assertNull(resourceField.getAnnotation(JPA.GENERATED_VALUE));
		
		//try removing the generatedValue again, should get an IllegalStateException
		try {
			idMapping.removeGeneratedValue();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
}
