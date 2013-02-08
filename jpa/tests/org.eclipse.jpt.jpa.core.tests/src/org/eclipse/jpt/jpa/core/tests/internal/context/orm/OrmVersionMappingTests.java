/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlVersion;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmVersionMappingTests extends ContextModelTestCase
{
	public OrmVersionMappingTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}

	private ICompilationUnit createTestEntityVersionMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.VERSION, JPA.COLUMN, JPA.TEMPORAL, JPA.TEMPORAL_TYPE);
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		OrmVersionMapping ormVersionMapping = (OrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlVersion versionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		
		assertEquals("id", ormVersionMapping.getName());
		assertEquals("id", versionResource.getName());
				
		//set name in the resource model, verify context model updated
		versionResource.setName("newName");
		assertEquals("newName", ormVersionMapping.getName());
		assertEquals("newName", versionResource.getName());
	
		//set name to null in the resource model
		versionResource.setName(null);
		assertNull(ormVersionMapping.getName());
		assertNull(versionResource.getName());
	}
	
	public void testModifyName() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		OrmVersionMapping ormVersionMapping = (OrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlVersion versionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		
		assertEquals("id", ormVersionMapping.getName());
		assertEquals("id", versionResource.getName());
				
		//set name in the context model, verify resource model updated
		ormVersionMapping.setName("newName");
		assertEquals("newName", ormVersionMapping.getName());
		assertEquals("newName", versionResource.getName());
	
		//set name to null in the context model
		ormVersionMapping.setName(null);
		assertNull(ormVersionMapping.getName());
		assertNull(versionResource.getName());
	}	
	
	public void testUpdateTemporal() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		OrmVersionMapping ormVersionMapping = (OrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlVersion versionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		
		assertNull(ormVersionMapping.getConverter().getType());
		assertNull(versionResource.getTemporal());
				
		//set temporal in the resource model, verify context model updated
		versionResource.setTemporal(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.DATE);
		assertEquals(TemporalType.DATE, ((BaseTemporalConverter) ormVersionMapping.getConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.DATE, versionResource.getTemporal());
	
		versionResource.setTemporal(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIME);
		assertEquals(TemporalType.TIME, ((BaseTemporalConverter) ormVersionMapping.getConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIME, versionResource.getTemporal());

		versionResource.setTemporal(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIMESTAMP);
		assertEquals(TemporalType.TIMESTAMP, ((BaseTemporalConverter) ormVersionMapping.getConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIMESTAMP, versionResource.getTemporal());

		//set temporal to null in the resource model
		versionResource.setTemporal(null);
		assertNull(ormVersionMapping.getConverter().getType());
		assertNull(versionResource.getTemporal());
	}
	
	public void testModifyTemporal() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		OrmVersionMapping ormVersionMapping = (OrmVersionMapping) ormPersistentAttribute.getMapping();
		XmlVersion versionResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getVersions().get(0);
		
		assertNull(ormVersionMapping.getConverter().getType());
		assertNull(versionResource.getTemporal());
				
		//set temporal in the context model, verify resource model updated
		ormVersionMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) ormVersionMapping.getConverter()).setTemporalType(TemporalType.DATE);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.DATE, versionResource.getTemporal());
		assertEquals(TemporalType.DATE, ((BaseTemporalConverter) ormVersionMapping.getConverter()).getTemporalType());
	
		((BaseTemporalConverter) ormVersionMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIME, versionResource.getTemporal());
		assertEquals(TemporalType.TIME, ((BaseTemporalConverter) ormVersionMapping.getConverter()).getTemporalType());

		((BaseTemporalConverter) ormVersionMapping.getConverter()).setTemporalType(TemporalType.TIMESTAMP);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIMESTAMP, versionResource.getTemporal());
		assertEquals(TemporalType.TIMESTAMP, ((BaseTemporalConverter) ormVersionMapping.getConverter()).getTemporalType());

		//set temporal to null in the context model
		ormVersionMapping.setConverter(null);
		assertNull(versionResource.getTemporal());
		assertNull(ormVersionMapping.getConverter().getType());
	}
	
	//TODO test defaults
	//TODO test overriding java mapping with a different mapping type in xml
	
	public void testVersionMappingNoUnderylingJavaAttribute() throws Exception {
		createTestEntityVersionMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getDefaultAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		OrmVersionMapping ormVersionMapping = (OrmVersionMapping) ormPersistentAttribute.getMapping();
		ormVersionMapping.setName("foo");
		assertEquals("foo", ormVersionMapping.getName());
		assertNull(ormVersionMapping.getConverter().getType());

		
		OrmColumn ormColumn = ormVersionMapping.getColumn();
		assertNull(ormColumn.getSpecifiedName());
		assertNull(ormColumn.getSpecifiedUnique());
		assertNull(ormColumn.getSpecifiedNullable());
		assertNull(ormColumn.getSpecifiedInsertable());
		assertNull(ormColumn.getSpecifiedUpdatable());
		assertNull(ormColumn.getColumnDefinition());
		assertNull(ormColumn.getSpecifiedTableName());
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(ormColumn.getSpecifiedPrecision());
		assertNull(ormColumn.getSpecifiedScale());
		
		assertEquals("foo", ormColumn.getDefaultName());
		assertEquals(false, ormColumn.isDefaultUnique());
		assertEquals(true, ormColumn.isDefaultNullable());
		assertEquals(true, ormColumn.isDefaultInsertable());
		assertEquals(true, ormColumn.isDefaultUpdatable());
		assertEquals(null, ormColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, ormColumn.getDefaultTableName());
		assertEquals(255, ormColumn.getDefaultLength());
		assertEquals(0, ormColumn.getDefaultPrecision());
		assertEquals(0, ormColumn.getDefaultScale());
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
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		
		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();	
		assertEquals("id", versionMapping.getName());
		assertEquals(BaseTemporalConverter.class, versionMapping.getConverter().getType());
		assertEquals(TemporalType.TIMESTAMP, ((BaseTemporalConverter) versionMapping.getConverter()).getTemporalType());
		
		Column column = versionMapping.getColumn();
		assertEquals("MY_COLUMN", column.getSpecifiedName());
		assertEquals(Boolean.TRUE, column.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, column.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, column.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, column.getSpecifiedUpdatable());
		assertEquals("COLUMN_DEFINITION", column.getColumnDefinition());
		assertEquals("MY_TABLE", column.getSpecifiedTableName());
		assertEquals(Integer.valueOf(5), column.getSpecifiedLength());
		assertEquals(Integer.valueOf(6), column.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(7), column.getSpecifiedScale());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
		assertTrue(ormPersistentAttribute.isVirtual());
		
		ormPersistentAttribute.addToXml(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		ormPersistentAttribute= ormPersistentType.getAttributeNamed("id");

		OrmVersionMapping ormVersionMapping = (OrmVersionMapping) ormPersistentAttribute.getMapping();	
		assertEquals("id", ormVersionMapping.getName());
		assertFalse(ormPersistentAttribute.isVirtual());
		assertNull(ormVersionMapping.getConverter().getType());
		
		OrmColumn ormColumn = ormVersionMapping.getColumn();
		assertEquals("id", ormColumn.getName());
		assertEquals(false, ormColumn.isDefaultUnique());
		assertEquals(true, ormColumn.isDefaultNullable());
		assertEquals(true, ormColumn.isDefaultInsertable());
		assertEquals(true, ormColumn.isDefaultUpdatable());
		assertNull(ormColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, ormColumn.getTableName());
		assertEquals(255, ormColumn.getLength());
		assertEquals(0, ormColumn.getPrecision());
		assertEquals(0, ormColumn.getScale());
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityVersionMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getDefaultAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		OrmVersionMapping ormVersionMapping = (OrmVersionMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("id", ormVersionMapping.getName());
		assertNull(ormVersionMapping.getConverter().getType());
		
		OrmColumn ormColumn = ormVersionMapping.getColumn();
		assertNull(ormColumn.getSpecifiedName());
		assertNull(ormColumn.getSpecifiedUnique());
		assertNull(ormColumn.getSpecifiedNullable());
		assertNull(ormColumn.getSpecifiedInsertable());
		assertNull(ormColumn.getSpecifiedUpdatable());
		assertNull(ormColumn.getColumnDefinition());
		assertNull(ormColumn.getSpecifiedTableName());
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(ormColumn.getSpecifiedPrecision());
		assertNull(ormColumn.getSpecifiedScale());
		
		assertEquals("id", ormColumn.getDefaultName());
		assertEquals(false, ormColumn.isDefaultUnique());
		assertEquals(true, ormColumn.isDefaultNullable());
		assertEquals(true, ormColumn.isDefaultInsertable());
		assertEquals(true, ormColumn.isDefaultUpdatable());
		assertEquals(null, ormColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, ormColumn.getDefaultTableName());
		assertEquals(255, ormColumn.getDefaultLength());
		assertEquals(0, ormColumn.getDefaultPrecision());
		assertEquals(0, ormColumn.getDefaultScale());
	}
	
	public void testVersionMorphToIdMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		
		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) versionMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
		assertEquals("FOO", ((IdMapping) ormPersistentAttribute.getMapping()).getColumn().getSpecifiedName());
	}
	
	public void testVersionMorphToBasicMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		
		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) versionMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
		assertEquals("FOO", ((BasicMapping) ormPersistentAttribute.getMapping()).getColumn().getSpecifiedName());
	}
	
	public void testVersionMorphToTransientMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		
		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) versionMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToEmbeddedMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		
		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) versionMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		
		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) versionMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToOneToOneMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		
		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) versionMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToOneMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToOneToManyMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);

		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) versionMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToManyToOneMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		
		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) versionMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testVersionMorphToManyToManyMapping() throws Exception {
		createTestEntityVersionMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		
		VersionMapping versionMapping = (VersionMapping) ormPersistentAttribute.getMapping();
		assertFalse(versionMapping.isDefault());
		versionMapping.getColumn().setSpecifiedName("FOO");
		versionMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) versionMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(versionMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}

}