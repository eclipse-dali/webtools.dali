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
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.EnumType;
import org.eclipse.jpt.core.context.EnumeratedConverter;
import org.eclipse.jpt.core.context.FetchType;
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
import org.eclipse.jpt.core.resource.java.EnumeratedAnnotation;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.LobAnnotation;
import org.eclipse.jpt.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class JavaBasicMappingTests extends ContextModelTestCase
{

	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}

	private ICompilationUnit createTestEntityWithBasicMapping() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic").append(CR);
			}
		});
	}
	private ICompilationUnit createTestEntityWithBasicMappingFetchOptionalSpecified() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC, JPA.FETCH_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic(fetch=FetchType.EAGER, optional=false)").append(CR);
			}
		});
	}

	private ICompilationUnit createTestEntityWithLob() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.LOB);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Lob").append(CR);
			}
		});
	}

	private ICompilationUnit createTestEntityWithEnumerated() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ENUMERATED, JPA.ENUM_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Enumerated(EnumType.STRING)").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithTemporal() throws Exception {	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.TEMPORAL, JPA.TEMPORAL_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Temporal(TemporalType.TIMESTAMP)").append(CR);
			}
		});
	}
		
	public JavaBasicMappingTests(String name) {
		super(name);
	}
	
	public void testDefaultBasicGetDefaultFetch() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		assertEquals(FetchType.EAGER, basicMapping.getDefaultFetch());
	}
	
	public void testSpecifiedBasicGetDefaultFetch() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();
		assertEquals(FetchType.EAGER, basicMapping.getDefaultFetch());
	}
	
	public void testGetFetch() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(FetchType.EAGER, basicMapping.getFetch());
		
		basicMapping.setSpecifiedFetch(FetchType.LAZY);		
		assertEquals(FetchType.LAZY, basicMapping.getFetch());
	}
	
	public void testGetSpecifiedFetch() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(basicMapping.getSpecifiedFetch());
		
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		BasicAnnotation basic = (BasicAnnotation) attributeResource.getMappingAnnotation(JPA.BASIC);
		basic.setFetch(org.eclipse.jpt.core.resource.java.FetchType.LAZY);
		
		assertEquals(FetchType.LAZY, basicMapping.getSpecifiedFetch());
	}
	
	public void testGetSpecifiedFetch2() throws Exception {
		createTestEntityWithBasicMappingFetchOptionalSpecified();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(FetchType.EAGER, basicMapping.getSpecifiedFetch());
	}

	public void testSetSpecifiedFetch() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();
		assertNull(basicMapping.getSpecifiedFetch());
		
		basicMapping.setSpecifiedFetch(FetchType.LAZY);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		BasicAnnotation basic = (BasicAnnotation) attributeResource.getMappingAnnotation(JPA.BASIC);
		
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.LAZY, basic.getFetch());
		
		basicMapping.setSpecifiedFetch(null);
		assertNotNull(attributeResource.getMappingAnnotation(JPA.BASIC));
	}
	
	public void testSetSpecifiedFetch2() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		createOrmXmlFile();
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		assertNull(basicMapping.getSpecifiedFetch());
		assertTrue(basicMapping.isDefault());
		
		basicMapping.setSpecifiedFetch(FetchType.LAZY);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableProperties().next();
		BasicAnnotation basic = (BasicAnnotation) attributeResource.getMappingAnnotation(JPA.BASIC);
		
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.LAZY, basic.getFetch());
		
		basicMapping = (BasicMapping) persistentAttribute.getMapping();
		assertEquals(FetchType.LAZY, basicMapping.getSpecifiedFetch());
		assertFalse(basicMapping.isDefault());

		basicMapping.setSpecifiedFetch(null);
		assertNotNull(attributeResource.getMappingAnnotation(JPA.BASIC));
		
		basicMapping = (BasicMapping) persistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
	}
	
	protected void createOrmXmlFile() throws Exception {
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		persistenceResource().save(null);
	}

	public void testSetBasicRemovedFromResourceModel() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		attributeResource.addSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(LobAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		assertFalse(basicMapping.isDefault());
		
		attributeResource.setMappingAnnotation(null);
		
		assertNotSame(basicMapping, persistentAttribute.getMapping());
		
		basicMapping = (BasicMapping) persistentAttribute.getMapping();
		assertTrue(basicMapping.isDefault());
		assertEquals("FOO", basicMapping.getColumn().getSpecifiedName());
		
		
		assertNotNull(attributeResource.getSupportingAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getSupportingAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToDefaultBasic() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setSpecifiedConverter(Converter.ENUMERATED_CONVERTER);
		((EnumeratedConverter) basicMapping.getSpecifiedConverter()).setSpecifiedEnumType(EnumType.STRING);
		attributeResource.addSupportingAnnotation(LobAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((BasicMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(EnumType.STRING, ((EnumeratedConverter) ((BasicMapping) persistentAttribute.getMapping()).getSpecifiedConverter()).getEnumType());
		
		assertNull(((BasicMapping) persistentAttribute.getMapping()).getSpecifiedFetch());
		assertNull(((BasicMapping) persistentAttribute.getMapping()).getSpecifiedOptional());
		assertNull(attributeResource.getMappingAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getSupportingAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getSupportingAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToId() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setSpecifiedConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) basicMapping.getSpecifiedConverter()).setTemporalType(TemporalType.TIME);
		attributeResource.addSupportingAnnotation(LobAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((IdMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((TemporalConverter) ((IdMapping) persistentAttribute.getMapping()).getSpecifiedConverter()).getTemporalType());
		
		assertNull(attributeResource.getMappingAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(IdAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getSupportingAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToVersion() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setSpecifiedConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) basicMapping.getSpecifiedConverter()).setTemporalType(TemporalType.TIME);
		attributeResource.addSupportingAnnotation(LobAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((VersionMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((TemporalConverter) ((VersionMapping) persistentAttribute.getMapping()).getSpecifiedConverter()).getTemporalType());
		
		assertNull(attributeResource.getMappingAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(VersionAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getSupportingAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToEmbedded() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		attributeResource.addSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(LobAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		
		assertNull(attributeResource.getMappingAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToEmbeddedId() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		attributeResource.addSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(LobAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		
		assertNull(attributeResource.getMappingAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
	}

	public void testBasicMorphToTransient() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		attributeResource.addSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(LobAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		
		assertNull(attributeResource.getMappingAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getMappingAnnotation(TransientAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToOneToOne() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		attributeResource.addSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(LobAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToOneMapping);
		
//TODO		assertEquals(FetchType.EAGER, ((IOneToOneMapping) persistentAttribute.getMapping()).getSpecifiedFetch());
//		assertEquals(Boolean.FALSE, ((IOneToOneMapping) persistentAttribute.getMapping()).getSpecifiedOptional());
		assertNotNull(attributeResource.getMappingAnnotation(OneToOneAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getMappingAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
	}

	public void testBasicMorphToOneToMany() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		attributeResource.addSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(LobAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		
//TODO		assertEquals(FetchType.EAGER, ((IOneToManyMapping) persistentAttribute.getMapping()).getSpecifiedFetch());
//		assertNotNull(attributeResource.mappingAnnotation(OneToMany.ANNOTATION_NAME));
		assertNull(attributeResource.getMappingAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
	}
	public void testBasicMorphToManyToOne() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		attributeResource.addSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(LobAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		
//TODO		assertEquals(FetchType.EAGER, ((IManyToOneMapping) persistentAttribute.getMapping()).getSpecifiedFetch());
//		assertEquals(Boolean.FALSE, ((IManyToOneMapping) persistentAttribute.getMapping()).getSpecifiedOptional());
		assertNotNull(attributeResource.getMappingAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getMappingAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToManyToMany() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		attributeResource.addSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(LobAnnotation.ANNOTATION_NAME);
		attributeResource.addSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		
//TODO		assertEquals(FetchType.EAGER, ((IManyToManyMapping) persistentAttribute.getMapping()).getSpecifiedFetch());
//		assertNotNull(attributeResource.mappingAnnotation(ManyToMany.ANNOTATION_NAME));
		assertNull(attributeResource.getMappingAnnotation(BasicAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(ColumnAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(LobAnnotation.ANNOTATION_NAME));
		assertNull(attributeResource.getSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
	}

	public void testDefaultBasicGetDefaultOptional() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		assertEquals(true, basicMapping.isDefaultOptional());
	}
	
	public void testSpecifiedBasicGetDefaultOptional() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();
		assertEquals(true, basicMapping.isDefaultOptional());
	}
	
	public void testGetOptional() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(true, basicMapping.isOptional());
		
		basicMapping.setSpecifiedOptional(Boolean.TRUE);
		assertEquals(true, basicMapping.isOptional());
	}
	
	public void testGetSpecifiedOptional() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(basicMapping.getSpecifiedOptional());
		
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		BasicAnnotation basic = (BasicAnnotation) attributeResource.getMappingAnnotation(JPA.BASIC);
		basic.setOptional(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, basicMapping.getSpecifiedOptional());
	}
	
	public void testGetSpecifiedOptional2() throws Exception {
		createTestEntityWithBasicMappingFetchOptionalSpecified();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(Boolean.FALSE, basicMapping.getSpecifiedOptional());
	}

	public void testSetSpecifiedOptional() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();
		assertNull(basicMapping.getSpecifiedOptional());
		
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		BasicAnnotation basic = (BasicAnnotation) attributeResource.getMappingAnnotation(JPA.BASIC);
		
		assertEquals(Boolean.FALSE, basic.getOptional());
		
		basicMapping.setSpecifiedOptional(null);
		assertNotNull(attributeResource.getMappingAnnotation(JPA.BASIC));
	}
	
	public void testSetSpecifiedOptional2() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		assertNull(basicMapping.getSpecifiedOptional());
		assertTrue(basicMapping.isDefault());
		
		basicMapping.setSpecifiedOptional(Boolean.TRUE);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		BasicAnnotation basic = (BasicAnnotation) attributeResource.getMappingAnnotation(JPA.BASIC);
		
		assertEquals(Boolean.TRUE, basic.getOptional());
		
		basicMapping = (BasicMapping) persistentAttribute.getMapping();
		assertEquals(Boolean.TRUE, basicMapping.getSpecifiedOptional());
		assertFalse(basicMapping.isDefault());

		basicMapping.setSpecifiedOptional(null);
		assertNotNull(attributeResource.getMappingAnnotation(JPA.BASIC));
		
		basicMapping = (BasicMapping) persistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
	}

	
	public void testGetSpecifiedOptionalUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(basicMapping.getSpecifiedOptional());
		
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		BasicAnnotation basic = (BasicAnnotation) attributeResource.getMappingAnnotation(JPA.BASIC);
		basic.setOptional(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, basicMapping.getSpecifiedOptional());
		
		basic.setOptional(null);
		assertNull(basicMapping.getSpecifiedOptional());
		assertFalse(basicMapping.isDefault());
		assertSame(basicMapping, persistentAttribute.getSpecifiedMapping());
		
		basic.setOptional(Boolean.FALSE);
		attributeResource.setMappingAnnotation(null);
		
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(true, ((BasicMapping) persistentAttribute.getMapping()).isOptional());
	}
	
	
	public void testIsLob() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();

		assertFalse(basicMapping.getConverter().getType() == Converter.LOB_CONVERTER);
	}
	
	public void testIsLob2() throws Exception {
		createTestEntityWithLob();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();

		assertTrue(basicMapping.getConverter().getType() == Converter.LOB_CONVERTER);
	}
	
	public void testSetLob() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();

		basicMapping.setSpecifiedConverter(Converter.LOB_CONVERTER);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getSupportingAnnotation(LobAnnotation.ANNOTATION_NAME));
		
		basicMapping.setSpecifiedConverter(null);
		assertNull(attributeResource.getSupportingAnnotation(LobAnnotation.ANNOTATION_NAME));
	}
	
	public void testIsLobUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();

		assertFalse(basicMapping.getConverter().getType() == Converter.LOB_CONVERTER);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addSupportingAnnotation(LobAnnotation.ANNOTATION_NAME);
		
		assertTrue(basicMapping.getConverter().getType() == Converter.LOB_CONVERTER);
	
		attributeResource.removeSupportingAnnotation(LobAnnotation.ANNOTATION_NAME);
		
		assertFalse(basicMapping.getConverter().getType() == Converter.LOB_CONVERTER);
	}
	
	public void testDefaultBasicGetDefaultConverter() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		assertTrue(basicMapping.getConverter().getType() == Converter.NO_CONVERTER);
	}
	
	public void testSpecifiedBasicGetDefaultConverter() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();
		assertTrue(basicMapping.getConverter().getType() == Converter.NO_CONVERTER);
	}
	
	public void testGetEnumerated() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();

		assertTrue(basicMapping.getConverter().getType() == Converter.NO_CONVERTER);
	
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EnumeratedAnnotation enumeratedAnnotation = (EnumeratedAnnotation) attributeResource.addSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		assertEquals(EnumType.ORDINAL, ((EnumeratedConverter) basicMapping.getConverter()).getDefaultEnumType());
		
		enumeratedAnnotation.setValue(org.eclipse.jpt.core.resource.java.EnumType.STRING);		
		assertEquals(EnumType.STRING, ((EnumeratedConverter) basicMapping.getConverter()).getSpecifiedEnumType());
	}
	
	public void testGetSpecifiedEnumerated() throws Exception {
		createTestEntityWithEnumerated();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();

		assertEquals(EnumType.STRING, ((EnumeratedConverter) basicMapping.getConverter()).getSpecifiedEnumType());
	}

	public void testSetSpecifiedEnumerated() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();
		assertTrue(basicMapping.getConverter().getType() == Converter.NO_CONVERTER);
		
		basicMapping.setSpecifiedConverter(Converter.ENUMERATED_CONVERTER);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EnumeratedAnnotation enumerated = (EnumeratedAnnotation) attributeResource.getSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		
		assertNotNull(enumerated);
		assertEquals(null, enumerated.getValue());
		
		((EnumeratedConverter) basicMapping.getConverter()).setSpecifiedEnumType(EnumType.STRING);
		assertEquals(org.eclipse.jpt.core.resource.java.EnumType.STRING, enumerated.getValue());
		
		((EnumeratedConverter) basicMapping.getConverter()).setSpecifiedEnumType(null);
		assertNotNull(attributeResource.getSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
		assertNull(enumerated.getValue());
		
		basicMapping.setSpecifiedConverter(Converter.NO_CONVERTER);
		assertNull(attributeResource.getSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetSpecifiedEnumeratedUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();

		assertTrue(basicMapping.getConverter().getType() == Converter.NO_CONVERTER);
		
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EnumeratedAnnotation enumerated = (EnumeratedAnnotation) attributeResource.addSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME);
		enumerated.setValue(org.eclipse.jpt.core.resource.java.EnumType.STRING);
		
		assertEquals(EnumType.STRING, ((EnumeratedConverter) basicMapping.getConverter()).getSpecifiedEnumType());
		
		enumerated.setValue(null);
		assertNotNull(attributeResource.getSupportingAnnotation(EnumeratedAnnotation.ANNOTATION_NAME));
		assertNull(((EnumeratedConverter) basicMapping.getConverter()).getSpecifiedEnumType());
		assertFalse(basicMapping.isDefault());
		assertSame(basicMapping, persistentAttribute.getSpecifiedMapping());
	}
	
	public void testGetTemporal() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(Converter.TEMPORAL_CONVERTER, basicMapping.getConverter().getType());
	}
	
	public void testGetTemporal2() throws Exception {
		createTestEntityWithTemporal();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();

		assertEquals(Converter.TEMPORAL_CONVERTER, basicMapping.getConverter().getType());
		assertEquals(TemporalType.TIMESTAMP, ((TemporalConverter) basicMapping.getConverter()).getTemporalType());
	}

	public void testSetTemporal() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();
		assertEquals(Converter.NO_CONVERTER, basicMapping.getConverter().getType());
		
		basicMapping.setSpecifiedConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) basicMapping.getSpecifiedConverter()).setTemporalType(TemporalType.TIME);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TemporalAnnotation temporal = (TemporalAnnotation) attributeResource.getSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.core.resource.java.TemporalType.TIME, temporal.getValue());
		
		basicMapping.setSpecifiedConverter(null);
		assertNull(attributeResource.getSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetTemporalUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(Converter.NO_CONVERTER, basicMapping.getConverter().getType());
		
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TemporalAnnotation temporal = (TemporalAnnotation) attributeResource.addSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		temporal.setValue(org.eclipse.jpt.core.resource.java.TemporalType.DATE);
		
		assertEquals(Converter.TEMPORAL_CONVERTER, basicMapping.getConverter().getType());
		assertEquals(TemporalType.DATE, ((TemporalConverter) basicMapping.getConverter()).getTemporalType());
		
		attributeResource.removeSupportingAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		
		assertEquals(Converter.NO_CONVERTER, basicMapping.getConverter().getType());
		assertFalse(basicMapping.isDefault());
		assertSame(basicMapping, persistentAttribute.getSpecifiedMapping());
	}
	
	public void testGetColumn() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedName());
		assertEquals("id", basicMapping.getColumn().getName());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ColumnAnnotation column = (ColumnAnnotation) attributeResource.addSupportingAnnotation(JPA.COLUMN);
		column.setName("foo");
		
		assertEquals("foo", basicMapping.getColumn().getSpecifiedName());
		assertEquals("foo", basicMapping.getColumn().getName());
		assertEquals("id", basicMapping.getColumn().getDefaultName());
	}
}
