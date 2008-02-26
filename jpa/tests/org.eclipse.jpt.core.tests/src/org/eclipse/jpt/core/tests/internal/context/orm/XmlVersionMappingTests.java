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
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
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
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionMapping");
		GenericOrmVersionMapping xmlVersionMapping = (GenericOrmVersionMapping) ormPersistentAttribute.getMapping();
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
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionMapping");
		GenericOrmVersionMapping xmlVersionMapping = (GenericOrmVersionMapping) ormPersistentAttribute.getMapping();
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
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionMapping");
		GenericOrmVersionMapping xmlVersionMapping = (GenericOrmVersionMapping) ormPersistentAttribute.getMapping();
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
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionMapping");
		GenericOrmVersionMapping xmlVersionMapping = (GenericOrmVersionMapping) ormPersistentAttribute.getMapping();
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
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		GenericOrmVersionMapping xmlVersionMapping = (GenericOrmVersionMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("foo", xmlVersionMapping.getName());
		assertNull(xmlVersionMapping.getTemporal());

		
		OrmColumn ormColumn = xmlVersionMapping.getColumn();
		assertNull(ormColumn.getSpecifiedName());
		assertNull(ormColumn.getSpecifiedUnique());
		assertNull(ormColumn.getSpecifiedNullable());
		assertNull(ormColumn.getSpecifiedInsertable());
		assertNull(ormColumn.getSpecifiedUpdatable());
		assertNull(ormColumn.getColumnDefinition());
		assertNull(ormColumn.getSpecifiedTable());
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(ormColumn.getSpecifiedPrecision());
		assertNull(ormColumn.getSpecifiedScale());
		
		assertEquals("foo", ormColumn.getDefaultName());
		assertEquals(Boolean.FALSE, ormColumn.getDefaultUnique());
		assertEquals(Boolean.TRUE, ormColumn.getDefaultNullable());
		assertEquals(Boolean.TRUE, ormColumn.getDefaultInsertable());
		assertEquals(Boolean.TRUE, ormColumn.getDefaultUpdatable());
		assertEquals(null, ormColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, ormColumn.getDefaultTable());
		assertEquals(Integer.valueOf(255), ormColumn.getDefaultLength());
		assertEquals(Integer.valueOf(0), ormColumn.getDefaultPrecision());
		assertEquals(Integer.valueOf(0), ormColumn.getDefaultScale());
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
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		GenericOrmVersionMapping xmlVersionMapping = (GenericOrmVersionMapping) ormPersistentAttribute.getMapping();	
		assertEquals("id", xmlVersionMapping.getName());
		assertEquals(TemporalType.TIMESTAMP, xmlVersionMapping.getTemporal());
		
		OrmColumn ormColumn = xmlVersionMapping.getColumn();
		assertEquals("MY_COLUMN", ormColumn.getSpecifiedName());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedUpdatable());
		assertEquals("COLUMN_DEFINITION", ormColumn.getColumnDefinition());
		assertEquals("MY_TABLE", ormColumn.getSpecifiedTable());
		assertEquals(Integer.valueOf(5), ormColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(6), ormColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(7), ormColumn.getSpecifiedScale());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(2, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		GenericOrmVersionMapping xmlVersionMapping = (GenericOrmVersionMapping) ormPersistentAttribute.getMapping();	
		assertEquals("id", xmlVersionMapping.getName());
		assertNull(xmlVersionMapping.getTemporal());
		
		OrmColumn ormColumn = xmlVersionMapping.getColumn();
		assertEquals("id", ormColumn.getSpecifiedName());
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedUnique());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedNullable());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedInsertable());
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedUpdatable());
		assertNull(ormColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, ormColumn.getSpecifiedTable());
		assertEquals(Integer.valueOf(255), ormColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(0), ormColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(0), ormColumn.getSpecifiedScale());
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityVersionMapping();

		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "id");
		assertEquals(1, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		GenericOrmVersionMapping xmlVersionMapping = (GenericOrmVersionMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("id", xmlVersionMapping.getName());
		assertNull(xmlVersionMapping.getTemporal());
		
		OrmColumn ormColumn = xmlVersionMapping.getColumn();
		assertNull(ormColumn.getSpecifiedName());
		assertNull(ormColumn.getSpecifiedUnique());
		assertNull(ormColumn.getSpecifiedNullable());
		assertNull(ormColumn.getSpecifiedInsertable());
		assertNull(ormColumn.getSpecifiedUpdatable());
		assertNull(ormColumn.getColumnDefinition());
		assertNull(ormColumn.getSpecifiedTable());
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(ormColumn.getSpecifiedPrecision());
		assertNull(ormColumn.getSpecifiedScale());
		
		assertEquals("id", ormColumn.getDefaultName());
		assertEquals(Boolean.FALSE, ormColumn.getDefaultUnique());
		assertEquals(Boolean.TRUE, ormColumn.getDefaultNullable());
		assertEquals(Boolean.TRUE, ormColumn.getDefaultInsertable());
		assertEquals(Boolean.TRUE, ormColumn.getDefaultUpdatable());
		assertEquals(null, ormColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, ormColumn.getDefaultTable());
		assertEquals(Integer.valueOf(255), ormColumn.getDefaultLength());
		assertEquals(Integer.valueOf(0), ormColumn.getDefaultPrecision());
		assertEquals(Integer.valueOf(0), ormColumn.getDefaultScale());
	}
	
	public void testVersionMorphToIdMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("version", ormPersistentAttribute.getMapping().getName());
		assertEquals(TemporalType.TIME, ((IdMapping) ormPersistentAttribute.getMapping()).getTemporal());
		assertEquals("FOO", ((IdMapping) ormPersistentAttribute.getMapping()).getColumn().getSpecifiedName());
	}
	
	public void testVersionMorphToBasicMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("version", ormPersistentAttribute.getMapping().getName());
		assertEquals(TemporalType.TIME, ((BasicMapping) ormPersistentAttribute.getMapping()).getTemporal());
		assertEquals("FOO", ((BasicMapping) ormPersistentAttribute.getMapping()).getColumn().getSpecifiedName());
	}
	
	public void testVersionMorphToTransientMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("version", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToEmbeddedMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("version", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("version", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToOneToOneMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToOneMapping);
		assertEquals("version", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToOneToManyMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");

		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("version", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToManyToOneMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("version", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToManyToManyMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("version", ormPersistentAttribute.getMapping().getName());
	}

}