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
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.IManyToManyMapping;
import org.eclipse.jpt.core.internal.context.base.IManyToOneMapping;
import org.eclipse.jpt.core.internal.context.base.IOneToManyMapping;
import org.eclipse.jpt.core.internal.context.base.IOneToOneMapping;
import org.eclipse.jpt.core.internal.context.base.ITransientMapping;
import org.eclipse.jpt.core.internal.context.base.IVersionMapping;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.context.orm.XmlColumn;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.context.orm.XmlVersionMapping;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.orm.Version;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionMapping");
		XmlVersionMapping xmlVersionMapping = (XmlVersionMapping) xmlPersistentAttribute.getMapping();
		Version versionResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionMapping");
		XmlVersionMapping xmlVersionMapping = (XmlVersionMapping) xmlPersistentAttribute.getMapping();
		Version versionResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionMapping");
		XmlVersionMapping xmlVersionMapping = (XmlVersionMapping) xmlPersistentAttribute.getMapping();
		Version versionResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		ormResource().save(null);
		
		assertNull(xmlVersionMapping.getTemporal());
		assertNull(versionResource.getTemporal());
				
		//set temporal in the resource model, verify context model updated
		versionResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE);
		ormResource().save(null);
		assertEquals(TemporalType.DATE, xmlVersionMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE, versionResource.getTemporal());
	
		versionResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME);
		ormResource().save(null);
		assertEquals(TemporalType.TIME, xmlVersionMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME, versionResource.getTemporal());

		versionResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP);
		ormResource().save(null);
		assertEquals(TemporalType.TIMESTAMP, xmlVersionMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP, versionResource.getTemporal());

		//set temporal to null in the resource model
		versionResource.setTemporal(null);
		ormResource().save(null);
		assertNull(xmlVersionMapping.getTemporal());
		assertNull(versionResource.getTemporal());
	}
	
	public void testModifyTemporal() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "versionMapping");
		XmlVersionMapping xmlVersionMapping = (XmlVersionMapping) xmlPersistentAttribute.getMapping();
		Version versionResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		ormResource().save(null);
		
		assertNull(xmlVersionMapping.getTemporal());
		assertNull(versionResource.getTemporal());
				
		//set temporal in the context model, verify resource model updated
		xmlVersionMapping.setTemporal(TemporalType.DATE);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE, versionResource.getTemporal());
		assertEquals(TemporalType.DATE, xmlVersionMapping.getTemporal());
	
		xmlVersionMapping.setTemporal(TemporalType.TIME);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME, versionResource.getTemporal());
		assertEquals(TemporalType.TIME, xmlVersionMapping.getTemporal());

		xmlVersionMapping.setTemporal(TemporalType.TIMESTAMP);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP, versionResource.getTemporal());
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

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "foo");
		assertEquals(2, xmlPersistentType.virtualAttributesSize());
		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.specifiedAttributes().next();
		XmlVersionMapping xmlVersionMapping = (XmlVersionMapping) xmlPersistentAttribute.getMapping();
		
		assertEquals("foo", xmlVersionMapping.getName());
		assertNull(xmlVersionMapping.getTemporal());

		
		XmlColumn xmlColumn = xmlVersionMapping.getColumn();
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(2, xmlPersistentType.virtualAttributesSize());		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.virtualAttributes().next();
		
		XmlVersionMapping xmlVersionMapping = (XmlVersionMapping) xmlPersistentAttribute.getMapping();	
		assertEquals("id", xmlVersionMapping.getName());
		assertEquals(TemporalType.TIMESTAMP, xmlVersionMapping.getTemporal());
		
		XmlColumn xmlColumn = xmlVersionMapping.getColumn();
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		xmlPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(2, xmlPersistentType.virtualAttributesSize());		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.virtualAttributes().next();
		
		XmlVersionMapping xmlVersionMapping = (XmlVersionMapping) xmlPersistentAttribute.getMapping();	
		assertEquals("id", xmlVersionMapping.getName());
		assertNull(xmlVersionMapping.getTemporal());
		
		XmlColumn xmlColumn = xmlVersionMapping.getColumn();
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

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "id");
		assertEquals(1, xmlPersistentType.virtualAttributesSize());
		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.specifiedAttributes().next();
		XmlVersionMapping xmlVersionMapping = (XmlVersionMapping) xmlPersistentAttribute.getMapping();
		
		assertEquals("id", xmlVersionMapping.getName());
		assertNull(xmlVersionMapping.getTemporal());
		
		XmlColumn xmlColumn = xmlVersionMapping.getColumn();
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		IVersionMapping versionMapping = (IVersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IIdMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
		assertEquals(TemporalType.TIME, ((IIdMapping) xmlPersistentAttribute.getMapping()).getTemporal());
		assertEquals("FOO", ((IIdMapping) xmlPersistentAttribute.getMapping()).getColumn().getSpecifiedName());
	}
	
	public void testVersionMorphToBasicMapping() throws Exception {
		createTestEntityVersionMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		IVersionMapping versionMapping = (IVersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IBasicMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
		assertEquals(TemporalType.TIME, ((IBasicMapping) xmlPersistentAttribute.getMapping()).getTemporal());
		assertEquals("FOO", ((IBasicMapping) xmlPersistentAttribute.getMapping()).getColumn().getSpecifiedName());
	}
	
	public void testVersionMorphToTransientMapping() throws Exception {
		createTestEntityVersionMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		IVersionMapping versionMapping = (IVersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof ITransientMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToEmbeddedMapping() throws Exception {
		createTestEntityVersionMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		IVersionMapping versionMapping = (IVersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IEmbeddedMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityVersionMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		IVersionMapping versionMapping = (IVersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IEmbeddedIdMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToOneToOneMapping() throws Exception {
		createTestEntityVersionMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		IVersionMapping versionMapping = (IVersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IOneToOneMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToOneToManyMapping() throws Exception {
		createTestEntityVersionMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");

		IVersionMapping versionMapping = (IVersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IOneToManyMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToManyToOneMapping() throws Exception {
		createTestEntityVersionMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		IVersionMapping versionMapping = (IVersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IManyToOneMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToManyToManyMapping() throws Exception {
		createTestEntityVersionMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY, "version");
		
		IVersionMapping versionMapping = (IVersionMapping) xmlPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setTemporal(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IManyToManyMapping);
		assertEquals("version", xmlPersistentAttribute.getMapping().getName());
	}

}