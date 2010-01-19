/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TemporalConverter;
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.jpt.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.ManyToManyAnnotation;
import org.eclipse.jpt.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class JavaIdMappingTests extends ContextModelTestCase
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

	private ICompilationUnit createTestEntityWithTemporal() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, JPA.TEMPORAL, JPA.TEMPORAL_TYPE);
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
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, JPA.GENERATED_VALUE);
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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((BasicMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((TemporalConverter) ((BasicMapping) persistentAttribute.getMapping()).getConverter()).getTemporalType());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((BasicMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((TemporalConverter) ((BasicMapping) persistentAttribute.getMapping()).getConverter()).getTemporalType());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((VersionMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((TemporalConverter) ((VersionMapping) persistentAttribute.getMapping()).getConverter()).getTemporalType());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(TransientAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToOneToOneMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToOneMapping);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		idMapping.addGeneratedValue();
		idMapping.getGeneratorContainer().addTableGenerator();
		idMapping.getGeneratorContainer().addSequenceGenerator();
		assertFalse(idMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TableGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(SequenceGeneratorAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(GeneratedValueAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetTemporal() throws Exception {
		createTestEntityWithTemporal();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(TemporalType.TIMESTAMP, ((TemporalConverter) idMapping.getConverter()).getTemporalType());
	}

	public void testSetTemporal() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();
		assertEquals(Converter.NO_CONVERTER, idMapping.getConverter().getType());
		
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TemporalAnnotation temporal = (TemporalAnnotation) attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.core.resource.java.TemporalType.TIME, temporal.getValue());
		
		idMapping.setConverter(Converter.NO_CONVERTER);
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetTemporalUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(Converter.NO_CONVERTER, idMapping.getConverter().getType());
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TemporalAnnotation temporal = (TemporalAnnotation) attributeResource.addAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		temporal.setValue(org.eclipse.jpt.core.resource.java.TemporalType.DATE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(TemporalType.DATE, ((TemporalConverter) idMapping.getConverter()).getTemporalType());
		
		attributeResource.removeAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(Converter.NO_CONVERTER, idMapping.getConverter().getType());
		assertFalse(idMapping.isDefault());
		assertSame(idMapping, persistentAttribute.getSpecifiedMapping());
	}
	
	public void testGetColumn() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();
		
		assertNull(idMapping.getColumn().getSpecifiedName());
		assertEquals("id", idMapping.getColumn().getName());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.addAnnotation(JPA.COLUMN);
		column.setName("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("foo", idMapping.getColumn().getSpecifiedName());
		assertEquals("foo", idMapping.getColumn().getName());
		assertEquals("id", idMapping.getColumn().getDefaultName());
	}

	public void testGetSequenceGenerator() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();
		assertNull(idMapping.getGeneratorContainer().getSequenceGenerator());
		assertEquals(0, idMapping.getPersistenceUnit().generatorsSize());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		getJpaProject().synchronizeContextModel();
		attributeResource.addAnnotation(JPA.SEQUENCE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		assertNotNull(idMapping.getGeneratorContainer().getSequenceGenerator());
		assertEquals(2, attributeResource.annotationsSize());
		assertEquals(1, idMapping.getPersistenceUnit().generatorsSize());
		
		idMapping.getGeneratorContainer().getSequenceGenerator().setName("foo");
		getJpaProject().synchronizeContextModel();
		assertEquals(1, idMapping.getPersistenceUnit().generatorsSize());
	}
	
	public void testAddSequenceGenerator() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();
		
		assertNull(idMapping.getGeneratorContainer().getSequenceGenerator());
		
		idMapping.getGeneratorContainer().addSequenceGenerator();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
	
		assertNotNull(attributeResource.getAnnotation(JPA.SEQUENCE_GENERATOR));
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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addAnnotation(JPA.SEQUENCE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		
		
		idMapping.getGeneratorContainer().removeSequenceGenerator();
		
		assertNull(idMapping.getGeneratorContainer().getSequenceGenerator());
		assertNull(attributeResource.getAnnotation(JPA.SEQUENCE_GENERATOR));

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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();
		assertNull(idMapping.getGeneratorContainer().getTableGenerator());
		assertEquals(0, idMapping.getPersistenceUnit().generatorsSize());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addAnnotation(JPA.TABLE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		assertNotNull(idMapping.getGeneratorContainer().getTableGenerator());		
		assertEquals(2, attributeResource.annotationsSize());
		assertEquals(1, idMapping.getPersistenceUnit().generatorsSize());
		
		idMapping.getGeneratorContainer().getTableGenerator().setName("foo");
		assertEquals(1, idMapping.getPersistenceUnit().generatorsSize());
	}
	
	public void testAddTableGenerator() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator());
		
		idMapping.getGeneratorContainer().addTableGenerator();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
	
		assertNotNull(attributeResource.getAnnotation(JPA.TABLE_GENERATOR));
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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addAnnotation(JPA.TABLE_GENERATOR);
		getJpaProject().synchronizeContextModel();
		
		
		idMapping.getGeneratorContainer().removeTableGenerator();
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator());
		assertNull(attributeResource.getAnnotation(JPA.TABLE_GENERATOR));
		
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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();
		
		assertNull(idMapping.getGeneratedValue());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addAnnotation(JPA.GENERATED_VALUE);
		getJpaProject().synchronizeContextModel();
		
		assertNotNull(idMapping.getGeneratedValue());		
		assertEquals(2, attributeResource.annotationsSize());
	}
	
	public void testGetGeneratedValue2() throws Exception {
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		createTestEntityWithIdMappingGeneratedValue();
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();

		assertNotNull(idMapping.getGeneratedValue());
		assertEquals(2, attributeResource.annotationsSize());
	}
	
	public void testAddGeneratedValue() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();
		
		assertNull(idMapping.getGeneratedValue());
		
		idMapping.addGeneratedValue();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
	
		assertNotNull(attributeResource.getAnnotation(JPA.GENERATED_VALUE));
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
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addAnnotation(JPA.GENERATED_VALUE);
		getJpaProject().synchronizeContextModel();
		
		idMapping.removeGeneratedValue();
		
		assertNull(idMapping.getGeneratedValue());
		assertNull(attributeResource.getAnnotation(JPA.GENERATED_VALUE));
		
		//try removing the generatedValue again, should get an IllegalStateException
		try {
			idMapping.removeGeneratedValue();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
}
