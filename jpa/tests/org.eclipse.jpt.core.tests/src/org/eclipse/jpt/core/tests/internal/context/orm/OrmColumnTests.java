/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class OrmColumnTests extends ContextModelTestCase
{
	public OrmColumnTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString());
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}
	
	public void testUpdateSpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedName());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createXmlColumn());
		basic.getColumn().setName("FOO");
		assertEquals("FOO", ormColumn.getSpecifiedName());
		assertEquals("FOO", basic.getColumn().getName());
	
		//set name to null in the resource model
		basic.getColumn().setName(null);
		assertNull(ormColumn.getSpecifiedName());
		assertNull(basic.getColumn().getName());
		
		basic.getColumn().setName("FOO");
		assertEquals("FOO", ormColumn.getSpecifiedName());
		assertEquals("FOO", basic.getColumn().getName());

		basic.setColumn(null);
		assertNull(ormColumn.getSpecifiedName());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedName());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		ormColumn.setSpecifiedName("foo");
		assertEquals("foo", ormColumn.getSpecifiedName());
		assertEquals("foo", basic.getColumn().getName());
		
		//set name to null in the context model
		ormColumn.setSpecifiedName(null);
		assertNull(ormColumn.getSpecifiedName());
		assertNull(basic.getColumn());
	}
	
	public void testUpdateColumnDefinition() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getColumnDefinition());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createXmlColumn());
		basic.getColumn().setColumnDefinition("FOO");
		assertEquals("FOO", ormColumn.getColumnDefinition());
		assertEquals("FOO", basic.getColumn().getColumnDefinition());
	
		//set name to null in the resource model
		basic.getColumn().setColumnDefinition(null);
		assertNull(ormColumn.getColumnDefinition());
		assertNull(basic.getColumn().getColumnDefinition());
		
		basic.getColumn().setColumnDefinition("FOO");
		assertEquals("FOO", ormColumn.getColumnDefinition());
		assertEquals("FOO", basic.getColumn().getColumnDefinition());

		basic.setColumn(null);
		assertNull(ormColumn.getColumnDefinition());
		assertNull(basic.getColumn());
	}
	
	public void testModifyColumnDefinition() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getColumnDefinition());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		ormColumn.setColumnDefinition("foo");
		assertEquals("foo", ormColumn.getColumnDefinition());
		assertEquals("foo", basic.getColumn().getColumnDefinition());
		
		//set name to null in the context model
		ormColumn.setColumnDefinition(null);
		assertNull(ormColumn.getColumnDefinition());
		assertNull(basic.getColumn());
	}

	public void testUpdateSpecifiedTable() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedTable());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createXmlColumn());
		basic.getColumn().setTable("FOO");
		assertEquals("FOO", ormColumn.getSpecifiedTable());
		assertEquals("FOO", basic.getColumn().getTable());
	
		//set name to null in the resource model
		basic.getColumn().setTable(null);
		assertNull(ormColumn.getSpecifiedTable());
		assertNull(basic.getColumn().getTable());
		
		basic.getColumn().setTable("FOO");
		assertEquals("FOO", ormColumn.getSpecifiedTable());
		assertEquals("FOO", basic.getColumn().getTable());

		basic.setColumn(null);
		assertNull(ormColumn.getSpecifiedTable());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedTable() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedTable());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		ormColumn.setSpecifiedTable("foo");
		assertEquals("foo", ormColumn.getSpecifiedTable());
		assertEquals("foo", basic.getColumn().getTable());
		
		//set name to null in the context model
		ormColumn.setSpecifiedTable(null);
		assertNull(ormColumn.getSpecifiedTable());
		assertNull(basic.getColumn());
	}

	public void testUpdateSpecifiedNullable() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedNullable());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createXmlColumn());
		basic.getColumn().setNullable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedNullable());
		assertEquals(Boolean.TRUE, basic.getColumn().getNullable());
	
		//set name to null in the resource model
		basic.getColumn().setNullable(null);
		assertNull(ormColumn.getSpecifiedNullable());
		assertNull(basic.getColumn().getNullable());
		
		basic.getColumn().setNullable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, basic.getColumn().getNullable());

		basic.setColumn(null);
		assertNull(ormColumn.getSpecifiedNullable());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedNullable() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedNullable());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		ormColumn.setSpecifiedNullable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, basic.getColumn().getNullable());
		
		//set name to null in the context model
		ormColumn.setSpecifiedNullable(null);
		assertNull(ormColumn.getSpecifiedNullable());
		assertNull(basic.getColumn());
	}

	public void testUpdateSpecifiedUpdatable() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedUpdatable());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createXmlColumn());
		basic.getColumn().setUpdatable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.TRUE, basic.getColumn().getUpdatable());
	
		//set name to null in the resource model
		basic.getColumn().setUpdatable(null);
		assertNull(ormColumn.getSpecifiedUpdatable());
		assertNull(basic.getColumn().getUpdatable());
		
		basic.getColumn().setUpdatable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.FALSE, basic.getColumn().getUpdatable());

		basic.setColumn(null);
		assertNull(ormColumn.getSpecifiedUpdatable());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedUpdatable() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedUpdatable());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		ormColumn.setSpecifiedUpdatable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.FALSE, basic.getColumn().getUpdatable());
		
		//set name to null in the context model
		ormColumn.setSpecifiedUpdatable(null);
		assertNull(ormColumn.getSpecifiedUpdatable());
		assertNull(basic.getColumn());
	}

	public void testUpdateSpecifiedInsertable() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedInsertable());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createXmlColumn());
		basic.getColumn().setInsertable(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedInsertable());
		assertEquals(Boolean.TRUE, basic.getColumn().getInsertable());
	
		//set name to null in the resource model
		basic.getColumn().setInsertable(null);
		assertNull(ormColumn.getSpecifiedInsertable());
		assertNull(basic.getColumn().getInsertable());
		
		basic.getColumn().setInsertable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, basic.getColumn().getInsertable());

		basic.setColumn(null);
		assertNull(ormColumn.getSpecifiedInsertable());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedInsertable() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedInsertable());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		ormColumn.setSpecifiedInsertable(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, basic.getColumn().getInsertable());
		
		//set name to null in the context model
		ormColumn.setSpecifiedInsertable(null);
		assertNull(ormColumn.getSpecifiedInsertable());
		assertNull(basic.getColumn());
	}
	
	public void testUpdateSpecifiedUnique() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedUnique());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createXmlColumn());
		basic.getColumn().setUnique(Boolean.TRUE);
		assertEquals(Boolean.TRUE, ormColumn.getSpecifiedUnique());
		assertEquals(Boolean.TRUE, basic.getColumn().getUnique());
	
		//set name to null in the resource model
		basic.getColumn().setUnique(null);
		assertNull(ormColumn.getSpecifiedUnique());
		assertNull(basic.getColumn().getUnique());
		
		basic.getColumn().setUnique(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, basic.getColumn().getUnique());

		basic.setColumn(null);
		assertNull(ormColumn.getSpecifiedUnique());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedUnique() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedUnique());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		ormColumn.setSpecifiedUnique(Boolean.FALSE);
		assertEquals(Boolean.FALSE, ormColumn.getSpecifiedUnique());
		assertEquals(Boolean.FALSE, basic.getColumn().getUnique());
		
		//set name to null in the context model
		ormColumn.setSpecifiedUnique(null);
		assertNull(ormColumn.getSpecifiedUnique());
		assertNull(basic.getColumn());
	}
	
	public void testUpdateSpecifiedLength() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createXmlColumn());
		basic.getColumn().setLength(Integer.valueOf(8));
		assertEquals(Integer.valueOf(8), ormColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(8), basic.getColumn().getLength());
	
		//set name to null in the resource model
		basic.getColumn().setLength(null);
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(basic.getColumn().getLength());
		
		basic.getColumn().setLength(Integer.valueOf(11));
		assertEquals(Integer.valueOf(11), ormColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(11), basic.getColumn().getLength());

		basic.setColumn(null);
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedLength() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		ormColumn.setSpecifiedLength(Integer.valueOf(7));
		assertEquals(Integer.valueOf(7), ormColumn.getSpecifiedLength());
		assertEquals(Integer.valueOf(7), basic.getColumn().getLength());
		
		//set name to null in the context model
		ormColumn.setSpecifiedLength(null);
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
	}
	
	public void testUpdateSpecifiedPrecision() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createXmlColumn());
		basic.getColumn().setPrecision(Integer.valueOf(8));
		assertEquals(Integer.valueOf(8), ormColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(8), basic.getColumn().getPrecision());
	
		//set name to null in the resource model
		basic.getColumn().setPrecision(null);
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(basic.getColumn().getPrecision());
		
		basic.getColumn().setPrecision(Integer.valueOf(11));
		assertEquals(Integer.valueOf(11), ormColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(11), basic.getColumn().getPrecision());

		basic.setColumn(null);
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedPrecision() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		ormColumn.setSpecifiedPrecision(Integer.valueOf(7));
		assertEquals(Integer.valueOf(7), ormColumn.getSpecifiedPrecision());
		assertEquals(Integer.valueOf(7), basic.getColumn().getPrecision());
		
		//set name to null in the context model
		ormColumn.setSpecifiedPrecision(null);
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
	}
	
	public void testUpdateSpecifiedScale() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
		
		//set name in the resource model, verify context model updated
		basic.setColumn(OrmFactory.eINSTANCE.createXmlColumn());
		basic.getColumn().setScale(Integer.valueOf(8));
		assertEquals(Integer.valueOf(8), ormColumn.getSpecifiedScale());
		assertEquals(Integer.valueOf(8), basic.getColumn().getScale());
	
		//set name to null in the resource model
		basic.getColumn().setScale(null);
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(basic.getColumn().getScale());
		
		basic.getColumn().setScale(Integer.valueOf(11));
		assertEquals(Integer.valueOf(11), ormColumn.getSpecifiedScale());
		assertEquals(Integer.valueOf(11), basic.getColumn().getScale());

		basic.setColumn(null);
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
	}
	
	public void testModifySpecifiedScale() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "basicMapping");
		OrmBasicMapping ormBasicMapping = (OrmBasicMapping) ormPersistentAttribute.getMapping();
		OrmColumn ormColumn = ormBasicMapping.getColumn();
		
		XmlEntity entityResource = getXmlEntityMappings().getEntities().get(0);
		XmlBasic basic = entityResource.getAttributes().getBasics().get(0);

		assertNull(ormColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
		
		//set name in the context model, verify resource model modified
		ormColumn.setSpecifiedScale(Integer.valueOf(7));
		assertEquals(Integer.valueOf(7), ormColumn.getSpecifiedScale());
		assertEquals(Integer.valueOf(7), basic.getColumn().getScale());
		
		//set name to null in the context model
		ormColumn.setSpecifiedScale(null);
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(basic.getColumn());
	}
	
	public void testVirtualColumnDefaults() throws Exception {
		createTestEntity();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		Iterator<OrmReadOnlyPersistentAttribute> attributes = ormPersistentType.virtualAttributes();
		attributes.next();	
		
		//virtual attribute in orm.xml, java attribute has no Column annotation
		OrmReadOnlyPersistentAttribute namePersistentAttribute = attributes.next();
		BasicMapping nameVirtualMapping = (BasicMapping) namePersistentAttribute.getMapping();		
		Column virtualColumn = nameVirtualMapping.getColumn();
		assertEquals("name", virtualColumn.getName());
		assertEquals(TYPE_NAME, virtualColumn.getTable());
		assertNull(virtualColumn.getColumnDefinition());
		assertTrue(virtualColumn.isInsertable());
		assertTrue(virtualColumn.isUpdatable());
		assertTrue(virtualColumn.isNullable());
		assertFalse(virtualColumn.isUnique());
		assertEquals(Column.DEFAULT_LENGTH, virtualColumn.getLength());
		assertEquals(Column.DEFAULT_PRECISION, virtualColumn.getPrecision());
		assertEquals(Column.DEFAULT_SCALE, virtualColumn.getScale());
	
		//set Column annotation in Java
		JavaBasicMapping javaBasicMapping = (JavaBasicMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("name").getMapping();
		JavaColumn javaColumn = javaBasicMapping.getColumn();
		javaColumn.setSpecifiedName("FOO");		
		javaColumn.setSpecifiedTable("FOO_TABLE");
		javaColumn.setColumnDefinition("COLUMN_DEFINITION");
		javaColumn.setSpecifiedInsertable(Boolean.FALSE);	
		javaColumn.setSpecifiedUpdatable(Boolean.FALSE);	
		javaColumn.setSpecifiedNullable(Boolean.FALSE);	
		javaColumn.setSpecifiedUnique(Boolean.TRUE);	
		javaColumn.setSpecifiedLength(Integer.valueOf(45));
		javaColumn.setSpecifiedPrecision(Integer.valueOf(46));
		javaColumn.setSpecifiedScale(Integer.valueOf(47));

		this.getJpaProject().synchronizeContextModel();

		nameVirtualMapping = (BasicMapping) namePersistentAttribute.getMapping();		
		virtualColumn = nameVirtualMapping.getColumn();
		assertEquals("FOO", virtualColumn.getName());
		assertEquals("FOO_TABLE", virtualColumn.getTable());
		assertEquals("COLUMN_DEFINITION", virtualColumn.getColumnDefinition());
		assertFalse(virtualColumn.isInsertable());
		assertFalse(virtualColumn.isUpdatable());
		assertFalse(virtualColumn.isNullable());
		assertTrue(virtualColumn.isUnique());
		assertEquals(45, virtualColumn.getLength());
		assertEquals(46, virtualColumn.getPrecision());
		assertEquals(47, virtualColumn.getScale());

	
		//set metadata-complete, orm.xml virtual column ignores java column annotation
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		nameVirtualMapping = (BasicMapping) namePersistentAttribute.getMapping();		
		virtualColumn = nameVirtualMapping.getColumn();
		assertEquals("name", virtualColumn.getName());
		assertEquals(TYPE_NAME, virtualColumn.getTable());
		assertNull(virtualColumn.getColumnDefinition());
		assertTrue(virtualColumn.isInsertable());
		assertTrue(virtualColumn.isUpdatable());
		assertTrue(virtualColumn.isNullable());
		assertFalse(virtualColumn.isUnique());
		assertEquals(Column.DEFAULT_LENGTH, virtualColumn.getLength());
		assertEquals(Column.DEFAULT_PRECISION, virtualColumn.getPrecision());
		assertEquals(Column.DEFAULT_SCALE, virtualColumn.getScale());
	
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.FALSE);
		nameVirtualMapping = (BasicMapping) namePersistentAttribute.getMapping();		
		virtualColumn = nameVirtualMapping.getColumn();
		assertEquals("name", virtualColumn.getName());
		assertEquals(TYPE_NAME, virtualColumn.getTable());
		assertNull(virtualColumn.getColumnDefinition());
		assertTrue(virtualColumn.isInsertable());
		assertTrue(virtualColumn.isUpdatable());
		assertTrue(virtualColumn.isNullable());
		assertFalse(virtualColumn.isUnique());
		assertEquals(Column.DEFAULT_LENGTH, virtualColumn.getLength());
		assertEquals(Column.DEFAULT_PRECISION, virtualColumn.getPrecision());
		assertEquals(Column.DEFAULT_SCALE, virtualColumn.getScale());
	
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(null);
		nameVirtualMapping = (BasicMapping) namePersistentAttribute.getMapping();		
		virtualColumn = nameVirtualMapping.getColumn();
		assertEquals("name", virtualColumn.getName());
		assertEquals(TYPE_NAME, virtualColumn.getTable());
		assertNull(virtualColumn.getColumnDefinition());
		assertTrue(virtualColumn.isInsertable());
		assertTrue(virtualColumn.isUpdatable());
		assertTrue(virtualColumn.isNullable());
		assertFalse(virtualColumn.isUnique());
		assertEquals(Column.DEFAULT_LENGTH, virtualColumn.getLength());
		assertEquals(Column.DEFAULT_PRECISION, virtualColumn.getPrecision());
		assertEquals(Column.DEFAULT_SCALE, virtualColumn.getScale());
		
		//set metadata-complete false, orm.xml virtual column gets setting from java annotation
		getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		nameVirtualMapping = (BasicMapping) namePersistentAttribute.getMapping();		
		virtualColumn = nameVirtualMapping.getColumn();
		assertEquals("FOO", virtualColumn.getName());
		assertEquals("FOO_TABLE", virtualColumn.getTable());
		assertEquals("COLUMN_DEFINITION", virtualColumn.getColumnDefinition());
		assertFalse(virtualColumn.isInsertable());
		assertFalse(virtualColumn.isUpdatable());
		assertFalse(virtualColumn.isNullable());
		assertTrue(virtualColumn.isUnique());
		assertEquals(45, virtualColumn.getLength());
		assertEquals(46, virtualColumn.getPrecision());
		assertEquals(47, virtualColumn.getScale());
	}
	
	public void testNullColumnDefaults() throws Exception {
		createTestEntity();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute namePersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "name");

		OrmBasicMapping nameVirtualMapping = (OrmBasicMapping) namePersistentAttribute.getMapping();		
		OrmColumn ormColumn = nameVirtualMapping.getColumn();
	
		//set Column annotation in Java
		JavaBasicMapping javaBasicMapping = (JavaBasicMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("name").getMapping();
		JavaColumn javaColumn = javaBasicMapping.getColumn();
		javaColumn.setSpecifiedName("FOO");		
		javaColumn.setSpecifiedTable("FOO_TABLE");
		javaColumn.setColumnDefinition("COLUMN_DEFINITION");
		javaColumn.setSpecifiedInsertable(Boolean.FALSE);	
		javaColumn.setSpecifiedUpdatable(Boolean.FALSE);	
		javaColumn.setSpecifiedNullable(Boolean.FALSE);	
		javaColumn.setSpecifiedUnique(Boolean.TRUE);	
		javaColumn.setSpecifiedLength(Integer.valueOf(45));
		javaColumn.setSpecifiedPrecision(Integer.valueOf(46));
		javaColumn.setSpecifiedScale(Integer.valueOf(47));

	
		assertEquals("name", ormColumn.getDefaultName());
		assertEquals(TYPE_NAME, ormColumn.getDefaultTable());
		assertEquals(true, ormColumn.isDefaultInsertable());
		assertEquals(true, ormColumn.isDefaultUpdatable());
		assertEquals(true, ormColumn.isDefaultNullable());
		assertEquals(false, ormColumn.isDefaultUnique());
		assertEquals(Column.DEFAULT_LENGTH, ormColumn.getDefaultLength());
		assertEquals(Column.DEFAULT_PRECISION, ormColumn.getDefaultPrecision());
		assertEquals(Column.DEFAULT_SCALE, ormColumn.getDefaultScale());
		assertNull(ormColumn.getSpecifiedName());
		assertNull(ormColumn.getSpecifiedTable());
		assertNull(ormColumn.getColumnDefinition());
		assertNull(ormColumn.getSpecifiedInsertable());
		assertNull(ormColumn.getSpecifiedUpdatable());
		assertNull(ormColumn.getSpecifiedNullable());
		assertNull(ormColumn.getSpecifiedUnique());
		assertNull(ormColumn.getSpecifiedLength());
		assertNull(ormColumn.getSpecifiedPrecision());
		assertNull(ormColumn.getSpecifiedScale());
	}

	public void testVirtualColumnTable() throws Exception {
		createTestEntity();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		//virtual attribute in orm.xml, java attribute has no Column annotation
		OrmReadOnlyPersistentAttribute nameOrmAttribute = ormPersistentType.getAttributeNamed("name");
		BasicMapping nameVirtualMapping = (BasicMapping) nameOrmAttribute.getMapping();	
		Column virtualColumn = nameVirtualMapping.getColumn();
		
		assertEquals(TYPE_NAME, virtualColumn.getTable());
	
		((OrmEntity) ormPersistentType.getMapping()).getTable().setSpecifiedName("ORM_TABLE");
		assertEquals("ORM_TABLE", virtualColumn.getTable());
		
		//set Column table element in Java
		JavaBasicMapping javaBasicMapping = (JavaBasicMapping) ormPersistentType.getJavaPersistentType().getAttributeNamed("name").getMapping();
		javaBasicMapping.getColumn().setSpecifiedTable("JAVA_TABLE");	
		nameVirtualMapping = (BasicMapping) nameOrmAttribute.getMapping();	
		virtualColumn = nameVirtualMapping.getColumn();
		assertEquals("JAVA_TABLE", virtualColumn.getTable());
		
		//make name persistent attribute not virtual
		nameOrmAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, "name");
		BasicMapping nameSpecifiedMapping = (OrmBasicMapping) nameOrmAttribute.getMapping();	
		Column specifiedColumn = nameSpecifiedMapping.getColumn();
		assertNull(specifiedColumn.getSpecifiedTable());
		assertEquals("ORM_TABLE", specifiedColumn.getDefaultTable());
		assertEquals("ORM_TABLE", specifiedColumn.getTable());
	}

//public void testUpdateDefaultNameNoJava() throws Exception {
//	createTestEntity();
//	
//	OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
//	XmlEntity xmlEntity = (XmlEntity) ormPersistentType.getMapping();
//	assertEquals("Foo", xmlEntity.getTable().getDefaultName());
//}
//
//public void testUpdateDefaultNameFromParent() throws Exception {
//	createTestEntity();
//	createTestSubType();
//	
//	OrmPersistentType parentOrmPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//	OrmPersistentType childOrmPersistentType = entityMappings().addOrmPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, PACKAGE_NAME + ".AnnotationTestTypeChild");
//	XmlEntity parentXmlEntity = (XmlEntity) parentOrmPersistentType.getMapping();
//	XmlEntity childXmlEntity = (XmlEntity) childOrmPersistentType.getMapping();
//	
//	assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
//	assertEquals(TYPE_NAME, childXmlEntity.getTable().getDefaultName());
//	
//	parentXmlEntity.getTable().setSpecifiedName("FOO");
//	assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
//	assertEquals("FOO", childXmlEntity.getTable().getDefaultName());
//
//	parentXmlEntity.setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
//	assertEquals(TYPE_NAME, parentXmlEntity.getTable().getDefaultName());
//	assertEquals("AnnotationTestTypeChild", childXmlEntity.getTable().getDefaultName());
//}

}