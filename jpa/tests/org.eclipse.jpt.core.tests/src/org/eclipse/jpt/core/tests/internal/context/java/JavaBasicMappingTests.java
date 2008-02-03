/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.base.EnumType;
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.IManyToManyMapping;
import org.eclipse.jpt.core.internal.context.base.IManyToOneMapping;
import org.eclipse.jpt.core.internal.context.base.IOneToManyMapping;
import org.eclipse.jpt.core.internal.context.base.IOneToOneMapping;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.ITransientMapping;
import org.eclipse.jpt.core.internal.context.base.IVersionMapping;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.resource.java.Basic;
import org.eclipse.jpt.core.internal.resource.java.Column;
import org.eclipse.jpt.core.internal.resource.java.Embedded;
import org.eclipse.jpt.core.internal.resource.java.EmbeddedId;
import org.eclipse.jpt.core.internal.resource.java.Enumerated;
import org.eclipse.jpt.core.internal.resource.java.Id;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.Lob;
import org.eclipse.jpt.core.internal.resource.java.ManyToOne;
import org.eclipse.jpt.core.internal.resource.java.OneToOne;
import org.eclipse.jpt.core.internal.resource.java.Temporal;
import org.eclipse.jpt.core.internal.resource.java.Transient;
import org.eclipse.jpt.core.internal.resource.java.Version;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaBasicMappingTests extends ContextModelTestCase
{
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createBasicAnnotation() throws Exception{
		this.createAnnotationAndMembers("Basic", "FetchType fetch() default EAGER; boolean optional() default true;");		
	}
	
	private void createLobAnnotation() throws Exception{
		this.createAnnotationAndMembers("Lob", "");		
	}
	
	private void createEnumeratedAnnotation() throws Exception{
		this.createAnnotationAndMembers("Enumerated", "EnumType value() default ORDINAL;");		
	}
	
	private void createTemporalAnnotation() throws Exception{
		this.createAnnotationAndMembers("Temporal", "TemporalType value();");		
	}

	private IType createTestEntity() throws Exception {
		createEntityAnnotation();

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

	private IType createTestEntityWithBasicMapping() throws Exception {
		createEntityAnnotation();
		createBasicAnnotation();
	
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
	private IType createTestEntityWithBasicMappingFetchOptionalSpecified() throws Exception {
		createEntityAnnotation();
		createBasicAnnotation();
	
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

	private IType createTestEntityWithLob() throws Exception {
		createEntityAnnotation();
		createLobAnnotation();
	
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

	private IType createTestEntityWithEnumerated() throws Exception {
		createEntityAnnotation();
		createEnumeratedAnnotation();
	
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
	
	private IType createTestEntityWithTemporal() throws Exception {
		createEntityAnnotation();
		createTemporalAnnotation();
	
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
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertEquals(FetchType.EAGER, basicMapping.getDefaultFetch());
	}
	
	public void testSpecifiedBasicGetDefaultFetch() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();
		assertEquals(FetchType.EAGER, basicMapping.getDefaultFetch());
	}
	
	public void testGetFetch() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(FetchType.EAGER, basicMapping.getFetch());
		
		basicMapping.setSpecifiedFetch(FetchType.LAZY);		
		assertEquals(FetchType.LAZY, basicMapping.getFetch());
	}
	
	public void testGetSpecifiedFetch() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(basicMapping.getSpecifiedFetch());
		
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		basic.setFetch(org.eclipse.jpt.core.internal.resource.java.FetchType.LAZY);
		
		assertEquals(FetchType.LAZY, basicMapping.getSpecifiedFetch());
	}
	
	public void testGetSpecifiedFetch2() throws Exception {
		createTestEntityWithBasicMappingFetchOptionalSpecified();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(FetchType.EAGER, basicMapping.getSpecifiedFetch());
	}

	public void testSetSpecifiedFetch() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();
		assertNull(basicMapping.getSpecifiedFetch());
		
		basicMapping.setSpecifiedFetch(FetchType.LAZY);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		
		assertEquals(org.eclipse.jpt.core.internal.resource.java.FetchType.LAZY, basic.getFetch());
		
		basicMapping.setSpecifiedFetch(null);
		assertNotNull(attributeResource.mappingAnnotation(JPA.BASIC));
	}
	
	public void testSetSpecifiedFetch2() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		createOrmXmlFile();
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(AccessType.PROPERTY);
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertNull(basicMapping.getSpecifiedFetch());
		assertTrue(basicMapping.isDefault());
		
		basicMapping.setSpecifiedFetch(FetchType.LAZY);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.properties().next();
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		
		assertEquals(org.eclipse.jpt.core.internal.resource.java.FetchType.LAZY, basic.getFetch());
		
		basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertEquals(FetchType.LAZY, basicMapping.getSpecifiedFetch());
		assertFalse(basicMapping.isDefault());

		basicMapping.setSpecifiedFetch(null);
		assertNotNull(attributeResource.mappingAnnotation(JPA.BASIC));
		
		basicMapping = (IBasicMapping) persistentAttribute.getMapping();
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
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setTemporal(TemporalType.TIME);
		basicMapping.setLob(true);
		basicMapping.setSpecifiedEnumerated(EnumType.ORDINAL);
		assertFalse(basicMapping.isDefault());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		attributeResource.setMappingAnnotation(null);
		
		assertNotSame(basicMapping, persistentAttribute.getMapping());
		
		basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertTrue(basicMapping.isDefault());
		assertEquals("FOO", basicMapping.getColumn().getSpecifiedName());
		
		
		assertNotNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(Lob.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(Enumerated.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToDefaultBasic() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setLob(true);
		basicMapping.setTemporal(TemporalType.TIME);
		basicMapping.setSpecifiedEnumerated(EnumType.ORDINAL);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((IBasicMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((IBasicMapping) persistentAttribute.getMapping()).getTemporal());
		assertTrue(((IBasicMapping) persistentAttribute.getMapping()).isLob());
		assertEquals(EnumType.ORDINAL, ((IBasicMapping) persistentAttribute.getMapping()).getEnumerated());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(((IBasicMapping) persistentAttribute.getMapping()).getSpecifiedFetch());
		assertNull(((IBasicMapping) persistentAttribute.getMapping()).getSpecifiedOptional());
		assertNull(attributeResource.mappingAnnotation(Basic.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(Lob.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(Enumerated.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToId() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setLob(true);
		basicMapping.setTemporal(TemporalType.TIME);
		basicMapping.setSpecifiedEnumerated(EnumType.ORDINAL);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((IIdMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((IIdMapping) persistentAttribute.getMapping()).getTemporal());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Basic.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Id.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Lob.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Enumerated.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToVersion() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setTemporal(TemporalType.TIME);
		basicMapping.setLob(true);
		basicMapping.setSpecifiedEnumerated(EnumType.ORDINAL);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals("FOO", ((IVersionMapping) persistentAttribute.getMapping()).getColumn().getSpecifiedName());
		assertEquals(TemporalType.TIME, ((IVersionMapping) persistentAttribute.getMapping()).getTemporal());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Basic.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Version.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNotNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Lob.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Enumerated.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToEmbedded() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setTemporal(TemporalType.TIME);
		basicMapping.setLob(true);
		basicMapping.setSpecifiedEnumerated(EnumType.ORDINAL);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IEmbeddedMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Basic.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Embedded.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Lob.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Enumerated.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToEmbeddedId() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setTemporal(TemporalType.TIME);
		basicMapping.setLob(true);
		basicMapping.setSpecifiedEnumerated(EnumType.ORDINAL);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IEmbeddedIdMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Basic.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(EmbeddedId.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Lob.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Enumerated.ANNOTATION_NAME));
	}

	public void testBasicMorphToTransient() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setTemporal(TemporalType.TIME);
		basicMapping.setLob(true);
		basicMapping.setSpecifiedEnumerated(EnumType.ORDINAL);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ITransientMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(Basic.ANNOTATION_NAME));
		assertNotNull(attributeResource.mappingAnnotation(Transient.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Lob.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Enumerated.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToOneToOne() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setTemporal(TemporalType.TIME);
		basicMapping.setLob(true);
		basicMapping.setSpecifiedEnumerated(EnumType.ORDINAL);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IOneToOneMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
//TODO		assertEquals(FetchType.EAGER, ((IOneToOneMapping) persistentAttribute.getMapping()).getSpecifiedFetch());
//		assertEquals(Boolean.FALSE, ((IOneToOneMapping) persistentAttribute.getMapping()).getSpecifiedOptional());
		assertNotNull(attributeResource.mappingAnnotation(OneToOne.ANNOTATION_NAME));
		assertNull(attributeResource.mappingAnnotation(Basic.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Lob.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Enumerated.ANNOTATION_NAME));
	}

	public void testBasicMorphToOneToMany() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setTemporal(TemporalType.TIME);
		basicMapping.setLob(true);
		basicMapping.setSpecifiedEnumerated(EnumType.ORDINAL);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IOneToManyMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
//TODO		assertEquals(FetchType.EAGER, ((IOneToManyMapping) persistentAttribute.getMapping()).getSpecifiedFetch());
//		assertNotNull(attributeResource.mappingAnnotation(OneToMany.ANNOTATION_NAME));
		assertNull(attributeResource.mappingAnnotation(Basic.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Lob.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Enumerated.ANNOTATION_NAME));
	}
	public void testBasicMorphToManyToOne() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setTemporal(TemporalType.TIME);
		basicMapping.setLob(true);
		basicMapping.setSpecifiedEnumerated(EnumType.ORDINAL);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IManyToOneMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
//TODO		assertEquals(FetchType.EAGER, ((IManyToOneMapping) persistentAttribute.getMapping()).getSpecifiedFetch());
//		assertEquals(Boolean.FALSE, ((IManyToOneMapping) persistentAttribute.getMapping()).getSpecifiedOptional());
		assertNotNull(attributeResource.mappingAnnotation(ManyToOne.ANNOTATION_NAME));
		assertNull(attributeResource.mappingAnnotation(Basic.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Lob.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Enumerated.ANNOTATION_NAME));
	}
	
	public void testBasicMorphToManyToMany() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
		basicMapping.getColumn().setSpecifiedName("FOO");
		basicMapping.setTemporal(TemporalType.TIME);
		basicMapping.setLob(true);
		basicMapping.setSpecifiedEnumerated(EnumType.ORDINAL);
		basicMapping.setSpecifiedFetch(FetchType.EAGER);
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		assertFalse(basicMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IManyToManyMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
//TODO		assertEquals(FetchType.EAGER, ((IManyToManyMapping) persistentAttribute.getMapping()).getSpecifiedFetch());
//		assertNotNull(attributeResource.mappingAnnotation(ManyToMany.ANNOTATION_NAME));
		assertNull(attributeResource.mappingAnnotation(Basic.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Column.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Lob.ANNOTATION_NAME));
		assertNull(attributeResource.annotation(Enumerated.ANNOTATION_NAME));
	}

	public void testDefaultBasicGetDefaultOptional() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertEquals(Boolean.TRUE, basicMapping.getDefaultOptional());
	}
	
	public void testSpecifiedBasicGetDefaultOptional() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();
		assertEquals(Boolean.TRUE, basicMapping.getDefaultOptional());
	}
	
	public void testGetOptional() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(Boolean.TRUE, basicMapping.getOptional());
		
		basicMapping.setSpecifiedOptional(basicMapping.getOptional());
		assertEquals(Boolean.TRUE, basicMapping.getOptional());
	}
	
	public void testGetSpecifiedOptional() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(basicMapping.getSpecifiedOptional());
		
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		basic.setOptional(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, basicMapping.getSpecifiedOptional());
	}
	
	public void testGetSpecifiedOptional2() throws Exception {
		createTestEntityWithBasicMappingFetchOptionalSpecified();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(Boolean.FALSE, basicMapping.getSpecifiedOptional());
	}

	public void testSetSpecifiedOptional() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();
		assertNull(basicMapping.getSpecifiedOptional());
		
		basicMapping.setSpecifiedOptional(Boolean.FALSE);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		
		assertEquals(Boolean.FALSE, basic.getOptional());
		
		basicMapping.setSpecifiedOptional(null);
		assertNotNull(attributeResource.mappingAnnotation(JPA.BASIC));
	}
	
	public void testSetSpecifiedOptional2() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertNull(basicMapping.getSpecifiedOptional());
		assertTrue(basicMapping.isDefault());
		
		basicMapping.setSpecifiedOptional(Boolean.TRUE);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		
		assertEquals(Boolean.TRUE, basic.getOptional());
		
		basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertEquals(Boolean.TRUE, basicMapping.getSpecifiedOptional());
		assertFalse(basicMapping.isDefault());

		basicMapping.setSpecifiedOptional(null);
		assertNotNull(attributeResource.mappingAnnotation(JPA.BASIC));
		
		basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertFalse(basicMapping.isDefault());
	}

	
	public void testGetSpecifiedOptionalUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(basicMapping.getSpecifiedOptional());
		
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Basic basic = (Basic) attributeResource.mappingAnnotation(JPA.BASIC);
		basic.setOptional(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, basicMapping.getSpecifiedOptional());
		
		basic.setOptional(null);
		assertNull(basicMapping.getSpecifiedOptional());
		assertFalse(basicMapping.isDefault());
		assertSame(basicMapping, persistentAttribute.getSpecifiedMapping());
		
		basic.setOptional(Boolean.FALSE);
		attributeResource.setMappingAnnotation(null);
		
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertEquals(Boolean.TRUE, ((IBasicMapping) persistentAttribute.getMapping()).getOptional());
	}
	
	
	public void testIsLob() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertFalse(basicMapping.isLob());
	}
	
	public void testIsLob2() throws Exception {
		createTestEntityWithLob();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();

		assertTrue(basicMapping.isLob());
	}
	
	public void testSetLob() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		basicMapping.setLob(true);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNotNull(attributeResource.annotation(Lob.ANNOTATION_NAME));
		
		basicMapping.setLob(false);
		assertNull(attributeResource.annotation(Lob.ANNOTATION_NAME));
	}
	
	public void testIsLobUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertFalse(basicMapping.isLob());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		attributeResource.addAnnotation(Lob.ANNOTATION_NAME);
		
		assertTrue(basicMapping.isLob());
	
		attributeResource.removeAnnotation(Lob.ANNOTATION_NAME);
		
		assertFalse(basicMapping.isLob());
	}
	
	public void testDefaultBasicGetDefaultEnumerated() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();
		assertEquals(EnumType.ORDINAL, basicMapping.getDefaultEnumerated());
	}
	
	public void testSpecifiedBasicGetDefaultEnumerated() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();
		assertEquals(EnumType.ORDINAL, basicMapping.getDefaultEnumerated());
	}
	
	public void testGetEnumerated() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(EnumType.ORDINAL, basicMapping.getEnumerated());
		
		basicMapping.setSpecifiedEnumerated(EnumType.STRING);
		assertEquals(EnumType.STRING, basicMapping.getEnumerated());
	}
	
	public void testGetSpecifiedEnumerated() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(basicMapping.getSpecifiedEnumerated());
	}
	
	public void testGetSpecifiedEnumerated2() throws Exception {
		createTestEntityWithEnumerated();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();

		assertEquals(EnumType.STRING, basicMapping.getSpecifiedEnumerated());
	}

	public void testSetSpecifiedEnumerated() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();
		assertNull(basicMapping.getSpecifiedEnumerated());
		
		basicMapping.setSpecifiedEnumerated(EnumType.STRING);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Enumerated enumerated = (Enumerated) attributeResource.annotation(Enumerated.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.core.internal.resource.java.EnumType.STRING, enumerated.getValue());
		
		basicMapping.setSpecifiedEnumerated(null);
		assertNull(attributeResource.annotation(Enumerated.ANNOTATION_NAME));
	}
	
	public void testGetSpecifieEnumeratedUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(basicMapping.getSpecifiedEnumerated());
		
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Enumerated enumerated = (Enumerated) attributeResource.addAnnotation(Enumerated.ANNOTATION_NAME);
		enumerated.setValue(org.eclipse.jpt.core.internal.resource.java.EnumType.STRING);
		
		assertEquals(EnumType.STRING, basicMapping.getSpecifiedEnumerated());
		
		enumerated.setValue(null);
		assertNull(attributeResource.annotation(Enumerated.ANNOTATION_NAME));
		assertNull(basicMapping.getSpecifiedEnumerated());
		assertFalse(basicMapping.isDefault());
		assertSame(basicMapping, persistentAttribute.getSpecifiedMapping());
	}
	
	
	public void testGetTemporal() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(basicMapping.getTemporal());
	}
	
	public void testGetTemporal2() throws Exception {
		createTestEntityWithTemporal();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getMapping();

		assertEquals(TemporalType.TIMESTAMP, basicMapping.getTemporal());
	}

	public void testSetTemporal() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();
		assertNull(basicMapping.getTemporal());
		
		basicMapping.setTemporal(TemporalType.TIME);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Temporal temporal = (Temporal) attributeResource.annotation(Temporal.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.core.internal.resource.java.TemporalType.TIME, temporal.getValue());
		
		basicMapping.setTemporal(null);
		assertNull(attributeResource.annotation(Temporal.ANNOTATION_NAME));
	}
	
	public void testGetTemporalUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();

		assertNull(basicMapping.getTemporal());
		
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Temporal temporal = (Temporal) attributeResource.addAnnotation(Temporal.ANNOTATION_NAME);
		temporal.setValue(org.eclipse.jpt.core.internal.resource.java.TemporalType.DATE);
		
		assertEquals(TemporalType.DATE, basicMapping.getTemporal());
		
		attributeResource.removeAnnotation(Temporal.ANNOTATION_NAME);
		
		assertNull(basicMapping.getTemporal());
		assertFalse(basicMapping.isDefault());
		assertSame(basicMapping, persistentAttribute.getSpecifiedMapping());
	}
	
	public void testGetColumn() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IPersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IBasicMapping basicMapping = (IBasicMapping) persistentAttribute.getSpecifiedMapping();
		
		assertNull(basicMapping.getColumn().getSpecifiedName());
		assertEquals("id", basicMapping.getColumn().getName());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		Column column = (Column) attributeResource.addAnnotation(JPA.COLUMN);
		column.setName("foo");
		
		assertEquals("foo", basicMapping.getColumn().getSpecifiedName());
		assertEquals("foo", basicMapping.getColumn().getName());
		assertEquals("id", basicMapping.getColumn().getDefaultName());
	}
}
