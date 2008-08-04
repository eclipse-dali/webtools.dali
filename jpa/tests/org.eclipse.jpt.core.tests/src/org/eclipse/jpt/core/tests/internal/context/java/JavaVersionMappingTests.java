/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.ManyToManyAnnotation;
import org.eclipse.jpt.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaVersionMappingTests extends ContextModelTestCase
{
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createVersionAnnotation() throws Exception{
		this.createAnnotationAndMembers("Version", "");		
	}
	
	private void createTemporalAnnotation() throws Exception{
		this.createAnnotationAndMembers("Temporal", "TemporalType value();");		
	}

	private ICompilationUnit createTestEntityWithVersionMapping() throws Exception {
		createEntityAnnotation();
		createVersionAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.VERSION);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Version").append(CR);
			}
		});
	}

	private ICompilationUnit createTestEntityWithTemporal() throws Exception {
		createEntityAnnotation();
		createTemporalAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.VERSION, JPA.TEMPORAL, JPA.TEMPORAL_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Version").append(CR);
				sb.append("@Temporal(TemporalType.TIMESTAMP)").append(CR);
			}
		});
	}
		
	public JavaVersionMappingTests(String name) {
		super(name);
	}
		
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		VersionMapping versionMapping = (VersionMapping) persistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((BasicMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((BasicMapping) persistentAttribute.getMapping()).getTemporal());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.getMappingAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		VersionMapping versionMapping = (VersionMapping) persistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((BasicMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((BasicMapping) persistentAttribute.getMapping()).getTemporal());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.getMappingAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		VersionMapping versionMapping = (VersionMapping) persistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((IdMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((IdMapping) persistentAttribute.getMapping()).getTemporal());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.getMappingAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		VersionMapping versionMapping = (VersionMapping) persistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.getMappingAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		VersionMapping versionMapping = (VersionMapping) persistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());

		persistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.getMappingAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(TransientAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		VersionMapping versionMapping = (VersionMapping) persistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());

		persistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.getMappingAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToOneMapping() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		VersionMapping versionMapping = (VersionMapping) persistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());

		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToOneMapping);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.getMappingAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		VersionMapping versionMapping = (VersionMapping) persistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());

		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.getMappingAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		VersionMapping versionMapping = (VersionMapping) persistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());

		persistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.getMappingAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		VersionMapping versionMapping = (VersionMapping) persistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());

		persistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.getMappingAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetTemporal() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		VersionMapping versionMapping = (VersionMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(versionMapping.getTemporal());
	}
	
	public void testGetTemporal2() throws Exception {
		createTestEntityWithTemporal();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		VersionMapping versionMapping = (VersionMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(TemporalType.TIMESTAMP, versionMapping.getTemporal());
	}

	public void testSetTemporal() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		VersionMapping versionMapping = (VersionMapping) persistentAttribute.getSpecifiedMapping();
		assertNull(versionMapping.getTemporal());
		
		versionMapping.setTemporal(TemporalType.TIME);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TemporalAnnotation temporal = (TemporalAnnotation) attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.core.resource.java.TemporalType.TIME, temporal.getValue());
		
		versionMapping.setTemporal(null);
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetTemporalUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		VersionMapping versionMapping = (VersionMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(versionMapping.getTemporal());
		
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TemporalAnnotation temporal = (TemporalAnnotation) attributeResource.addAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		temporal.setValue(org.eclipse.jpt.core.resource.java.TemporalType.DATE);
		
		assertEquals(TemporalType.DATE, versionMapping.getTemporal());
		
		attributeResource.removeAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		
		assertNull(versionMapping.getTemporal());
		assertFalse(versionMapping.isDefault());
		assertSame(versionMapping, persistentAttribute.getSpecifiedMapping());
	}
	
	public void testGetColumn() throws Exception {
		createTestEntityWithVersionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		VersionMapping versionMapping = (VersionMapping) persistentAttribute.getSpecifiedMapping();
		
		assertNull(versionMapping.getColumn().getSpecifiedName());
		assertEquals("id", versionMapping.getColumn().getName());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.addAnnotation(JPA.COLUMN);
		column.setName("foo");
		
		assertEquals("foo", versionMapping.getColumn().getSpecifiedName());
		assertEquals("foo", versionMapping.getColumn().getName());
		assertEquals("id", versionMapping.getColumn().getDefaultName());
	}
	
}
