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
package org.eclipse.jpt.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmColumn;
import org.eclipse.jpt.core.internal.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmVersionMapping;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.XmlVersion;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class XmlVersionMappingTests extends ContextModelTestCase
{
	public XmlVersionMappingTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		persistenceResource().save(null);
	}
	
	private void createEntityAnnotation() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createVersionAnnotation() throws Exception{
		this.createAnnotationAndMembers("Version", "");		
	}
	
	private void createColumnAnnotation() throws Exception{
		this.createAnnotationAndMembers("Column", 
			"String name() default \"\";" +
			"boolean unique() default false;" +
			"boolean nullable() default true;" +
			"boolean insertable() default true;" +
			"boolean updatable() default true;" +
			"String columnDefinition() default \"\";" +
			"String table() default \"\";" +
			"int length() default 255;" +
			"int precision() default 0;" +
			"int scale() default 0;");		
	}
	
	private void createTemporalAnnotation() throws Exception{
		this.createAnnotationAndMembers("Temporal", "TemporalType value();");		
	}

	private IType createTestEntityVersionMapping() throws Exception {
		createEntityAnnotation();
		createVersionAnnotation();
		createColumnAnnotation();
		createTemporalAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.VERSION, JPA.COLUMN, JPA.TEMPORAL, JPA.TEMPORAL_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Version");
				sb.append(CR);
				sb.append("    @Column(name=\"MY_COLUMN\", unique=true, nullable=false, insertable=false, updatable=false, columnDefinition=\"COLUMN_DEFINITION\", table=\"MY_TABLE\", length=5, precision=6, scale=7)");
				sb.append(CR);
				sb.append("    @Temporal(TemporalType.TIMESTAMP)");
			}
		});
	}
	
	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionMapping");
		GenericOrmVersionMapping xmlVersionMapping = (GenericOrmVersionMapping) xmlPersistentAttribute.getMapping();
		XmlVersion versionResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		
		assertEquals("versionMapping", xmlVersionMapping.getName());
		assertEquals("versionMapping", versionResource.getName());
				
		//set name in the resource model, verify context model updated
		versionResource.setName("newName");
		assertEquals("newName", xmlVersionMapping.getName());
		assertEquals("newName", versionResource.getName());
	
		//set name to null in the resource model
		versionResource.setName(null);
		assertNull(xmlVersionMapping.getName());
		assertNull(versionResource.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionMapping");
		GenericOrmVersionMapping xmlVersionMapping = (GenericOrmVersionMapping) xmlPersistentAttribute.getMapping();
		XmlVersion versionResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		
		assertEquals("versionMapping", xmlVersionMapping.getName());
		assertEquals("versionMapping", versionResource.getName());
				
		//set name in the context model, verify resource model updated
		xmlVersionMapping.setName("newName");
		assertEquals("newName", xmlVersionMapping.getName());
		assertEquals("newName", versionResource.getName());
	
		//set name to null in the context model
		xmlVersionMapping.setName(null);
		assertNull(xmlVersionMapping.getName());
		assertNull(versionResource.getName());
	}	
	
	public void testUpdateTemporal() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionMapping");
		GenericOrmVersionMapping xmlVersionMapping = (GenericOrmVersionMapping) xmlPersistentAttribute.getMapping();
		XmlVersion versionResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		ormResource().save(null);
		
		assertNull(xmlVersionMapping.getTemporal());
		assertNull(versionResource.getTemporal());
				
		//set temporal in the resource model, verify context model updated
		versionResource.setTemporal(org.eclipse.jpt.core.resource.orm.TemporalType.DATE);
		ormResource().save(null);
		assertEquals(TemporalType.DATE, xmlVersionMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.DATE, versionResource.getTemporal());
	
		versionResource.setTemporal(org.eclipse.jpt.core.resource.orm.TemporalType.TIME);
		ormResource().save(null);
		assertEquals(TemporalType.TIME, xmlVersionMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.TIME, versionResource.getTemporal());

		versionResource.setTemporal(org.eclipse.jpt.core.resource.orm.TemporalType.TIMESTAMP);
		ormResource().save(null);
		assertEquals(TemporalType.TIMESTAMP, xmlVersionMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.TIMESTAMP, versionResource.getTemporal());

		//set temporal to null in the resource model
		versionResource.setTemporal(null);
		ormResource().save(null);
		assertNull(xmlVersionMapping.getTemporal());
		assertNull(versionResource.getTemporal());
	}
	
	public void testModifyTemporal() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionMapping");
		GenericOrmVersionMapping xmlVersionMapping = (GenericOrmVersionMapping) xmlPersistentAttribute.getMapping();
		XmlVersion versionResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		ormResource().save(null);
		
		assertNull(xmlVersionMapping.getTemporal());
		assertNull(versionResource.getTemporal());
				
		//set temporal in the context model, verify resource model updated
		xmlVersionMapping.setTemporal(TemporalType.DATE);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.DATE, versionResource.getTemporal());
		assertEquals(TemporalType.DATE, xmlVersionMapping.getTemporal());
	
		xmlVersionMapping.setTemporal(TemporalType.TIME);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.TIME, versionResource.getTemporal());
		assertEquals(TemporalType.TIME, xmlVersionMapping.getTemporal());

		xmlVersionMapping.setTemporal(TemporalType.TIMESTAMP);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.TIMESTAMP, versionResource.getTemporal());
		assertEquals(TemporalType.TIMESTAMP, xmlVersionMapping.getTemporal());

		//set temporal to null in the context model
		xmlVersionMapping.setTemporal(null);
		ormResource().save(null);
		assertNull(versionResource.getTemporal());
		assertNull(xmlVersionMapping.getTemporal());
	}
	
	//TODO test defaults
	//TODO test overriding java mapping with a different mapping type in xml
	
	public void testVersionMappingNoUnderylingJavaAttribute() throws Exception {
		createTestEntityVersionMapping();

		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "foo");
		assertEquals(2, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		GenericOrmVersionMapping xmlVersionMapping = (GenericOrmVersionMapping) xmlPersistentAttribute.getMapping();
		
		assertEquals("foo", xmlVersionMapping.getName());
		assertNull(xmlVersionMapping.getTemporal());

		
		GenericOrmColumn xmlColumn = xmlVersionMapping.getColumn();
		assertNull(xmlColumn.getSpecifiedName());
		assertNull(xmlColumn.getSpecifiedUnique());
		assertNull(xmlColumn.getSpecifiedNullable());
		assertNull(xmlColumn.getSpecifiedInsertable());
		assertNull(xmlColumn.getSpecifiedUpdatable());
		assertNull(xmlColumn.getColumnDefinition());
		assertNull(xmlColumn.getSpecifiedTable());
		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(xmlColumn.getSpecifiedPrecision());
		assertNull(xmlColumn.getSpecifiedScale());
		
		assertEquals("foo", xmlColumn.getDefaultName());
		assertEquals(Boolean.FALSE, xmlColumn.getDefaultUnique());
		assertEquals(Boolean.TRUE, xmlColumn.getDefaultNullable());
		assertEquals(Boolean.TRUE, xmlColumn.getDefaultInsertable());
		assertEquals(Boolean.TRUE, xmlColumn.getDefaultUpdatable());
		assertEquals(null, xmlColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, xmlColumn.getDefaultTable());
		assertEquals(Integer.valueOf(255), xmlColumn.getDefaultLength());
		assertEquals(Integer.valueOf(0), xmlColumn.getDefaultPrecision());
		assertEquals(Integer.valueOf(0), xmlColumn.getDefaultScale());
	}
	
	//@Basic(fetch=FetchType.LAZY, optional=false)
	//@Column(name="MY_COLUMN", unique=true, nullable=false, insertable=false, updatable=false, 
	//    columnDefinition="COLUMN_DEFINITION", table="MY_TABLE", length=5, precision=6, scale=7)");
	//@Column(
	//@Lob
	//@Temporal(TemporalType.TIMESTAMP)
	//@Enumerated(EnumType.STRING)
	public void testVirtualMappingMetadataCompleteFalse() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(2, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		GenericOrmVersionMapping xmlVersionMapping = (GenericOrmVersionMapping) xmlPersistentAttribute.getMapping();	
		assertEquals("id", xmlVersionMapping.getName());
		assertEquals(TemporalType.TIMESTAMP, xmlVersionMapping.getTemporal());
		
		GenericOrmColumn xmlColumn = xmlVersionMapping.getColumn();
		assertEquals("MY_COLUMN", xmlColumn.getSpecifiedName());
		assertEquals(Boolean.TRUE, xmlColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedUpdatable());
		assertEquals("COLUMN_DEFINITION", xmlColumn.getColumnDefinition());
		assertEquals("MY_TABLE", xmlColumn.getSpecifiedTable());
		assertEquals(Integer.valueOf(5), xmlColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(6), xmlColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(7), xmlColumn.getSpecifiedScale());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(2, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		GenericOrmVersionMapping xmlVersionMapping = (GenericOrmVersionMapping) xmlPersistentAttribute.getMapping();	
		assertEquals("id", xmlVersionMapping.getName());
		assertNull(xmlVersionMapping.getTemporal());
		
		GenericOrmColumn xmlColumn = xmlVersionMapping.getColumn();
		assertEquals("id", xmlColumn.getSpecifiedName());
		assertEquals(Boolean.FALSE, xmlColumn.getSpecifiedUnique());
		assertEquals(Boolean.TRUE, xmlColumn.getSpecifiedNullable());
		assertEquals(Boolean.TRUE, xmlColumn.getSpecifiedInsertable());
		assertEquals(Boolean.TRUE, xmlColumn.getSpecifiedUpdatable());
		assertNull(xmlColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, xmlColumn.getSpecifiedTable());
		assertEquals(Integer.valueOf(255), xmlColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(0), xmlColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(0), xmlColumn.getSpecifiedScale());
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityVersionMapping();

		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "id");
		assertEquals(1, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		GenericOrmVersionMapping xmlVersionMapping = (GenericOrmVersionMapping) xmlPersistentAttribute.getMapping();
		
		assertEquals("id", xmlVersionMapping.getName());
		assertNull(xmlVersionMapping.getTemporal());
		
		GenericOrmColumn xmlColumn = xmlVersionMapping.getColumn();
		assertNull(xmlColumn.getSpecifiedName());
		assertNull(xmlColumn.getSpecifiedUnique());
		assertNull(xmlColumn.getSpecifiedNullable());
		assertNull(xmlColumn.getSpecifiedInsertable());
		assertNull(xmlColumn.getSpecifiedUpdatable());
		assertNull(xmlColumn.getColumnDefinition());
		assertNull(xmlColumn.getSpecifiedTable());
		assertNull(xmlColumn.getSpecifiedLength());
		assertNull(xmlColumn.getSpecifiedPrecision());
		assertNull(xmlColumn.getSpecifiedScale());
		
		assertEquals("id", xmlColumn.getDefaultName());
		assertEquals(Boolean.FALSE, xmlColumn.getDefaultUnique());
		assertEquals(Boolean.TRUE, xmlColumn.getDefaultNullable());
		assertEquals(Boolean.TRUE, xmlColumn.getDefaultInsertable());
		assertEquals(Boolean.TRUE, xmlColumn.getDefaultUpdatable());
		assertEquals(null, xmlColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, xmlColumn.getDefaultTable());
		assertEquals(Integer.valueOf(255), xmlColumn.getDefaultLength());
		assertEquals(Integer.valueOf(0), xmlColumn.getDefaultPrecision());
		assertEquals(Integer.valueOf(0), xmlColumn.getDefaultScale());
	}
	
	public void testVersionMorphToIdMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		VersionMapping versionMapping = (VersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
		assertEquals(TemporalType.TIME, ((IdMapping) xmlPersistentAttribute.getMapping()).getTemporal());
		assertEquals("FOO", ((IdMapping) xmlPersistentAttribute.getMapping()).getColumn().getSpecifiedName());
	}
	
	public void testVersionMorphToBasicMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		VersionMapping versionMapping = (VersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
		assertEquals(TemporalType.TIME, ((BasicMapping) xmlPersistentAttribute.getMapping()).getTemporal());
		assertEquals("FOO", ((BasicMapping) xmlPersistentAttribute.getMapping()).getColumn().getSpecifiedName());
	}
	
	public void testVersionMorphToTransientMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		VersionMapping versionMapping = (VersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToEmbeddedMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		VersionMapping versionMapping = (VersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		VersionMapping versionMapping = (VersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToOneToOneMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		VersionMapping versionMapping = (VersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof OneToOneMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToOneToManyMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");

		VersionMapping versionMapping = (VersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToManyToOneMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		VersionMapping versionMapping = (VersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToManyToManyMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		VersionMapping versionMapping = (VersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
	}

}