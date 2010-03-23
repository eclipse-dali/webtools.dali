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
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.GenerationType;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.TemporalConverter;
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class OrmIdMappingTests extends ContextModelTestCase
{
	public OrmIdMappingTests(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}

	private ICompilationUnit createTestEntityIdMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, JPA.COLUMN, JPA.TEMPORAL, JPA.TEMPORAL_TYPE, JPA.GENERATED_VALUE, JPA.GENERATION_TYPE, JPA.TABLE_GENERATOR, JPA.SEQUENCE_GENERATOR);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
				sb.append(CR);
				sb.append("    @Column(name=\"MY_COLUMN\", unique=true, nullable=false, insertable=false, updatable=false, columnDefinition=\"COLUMN_DEFINITION\", table=\"MY_TABLE\", length=5, precision=6, scale=7)");
				sb.append(CR);
				sb.append("    @Temporal(TemporalType.TIMESTAMP)");
				sb.append(CR);
				sb.append("    @GeneratedValue(strategy=GenerationType.TABLE, generator=\"myTableGenerator\")");
				sb.append(CR);
				sb.append("    @TableGenerator(name=\"myTableGenerator\", table=\"myTable\", catalog=\"myCatalog\", schema=\"mySchema\", pkColumnName=\"myPkColumnName\", valueColumnName=\"myValueColumnName\", pkColumnValue=\"myPkColumnValue\", initialValue=1, allocationSize=1)");
				sb.append(CR);
				sb.append("    @SequenceGenerator(name=\"mySequenceGenerator\")");
			}
		});
	}
	
	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertEquals("idMapping", ormIdMapping.getName());
		assertEquals("idMapping", idResource.getName());
				
		//set name in the resource model, verify context model updated
		idResource.setName("newName");
		assertEquals("newName", ormIdMapping.getName());
		assertEquals("newName", idResource.getName());
	
		//set name to null in the resource model
		idResource.setName(null);
		assertNull(ormIdMapping.getName());
		assertNull(idResource.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertEquals("idMapping", ormIdMapping.getName());
		assertEquals("idMapping", idResource.getName());
				
		//set name in the context model, verify resource model updated
		ormIdMapping.setName("newName");
		assertEquals("newName", ormIdMapping.getName());
		assertEquals("newName", idResource.getName());
	
		//set name to null in the context model
		ormIdMapping.setName(null);
		assertNull(ormIdMapping.getName());
		assertNull(idResource.getName());
	}
	
	public void testUpdateTemporal() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertEquals(Converter.NO_CONVERTER, ormIdMapping.getConverter().getType());
		assertNull(idResource.getTemporal());
				
		//set temporal in the resource model, verify context model updated
		idResource.setTemporal(org.eclipse.jpt.core.resource.orm.TemporalType.DATE);
		assertEquals(TemporalType.DATE, ((TemporalConverter) ormIdMapping.getConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.DATE, idResource.getTemporal());
	
		idResource.setTemporal(org.eclipse.jpt.core.resource.orm.TemporalType.TIME);
		assertEquals(TemporalType.TIME, ((TemporalConverter) ormIdMapping.getConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.TIME, idResource.getTemporal());

		idResource.setTemporal(org.eclipse.jpt.core.resource.orm.TemporalType.TIMESTAMP);
		assertEquals(TemporalType.TIMESTAMP, ((TemporalConverter) ormIdMapping.getConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.TIMESTAMP, idResource.getTemporal());

		//set temporal to null in the resource model
		idResource.setTemporal(null);
		assertEquals(Converter.NO_CONVERTER, ormIdMapping.getConverter().getType());
		assertNull(idResource.getTemporal());
	}
	
	public void testModifyTemporal() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertEquals(Converter.NO_CONVERTER, ormIdMapping.getConverter().getType());
		assertNull(idResource.getTemporal());
				
		//set temporal in the context model, verify resource model updated
		ormIdMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) ormIdMapping.getConverter()).setTemporalType(TemporalType.DATE);
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.DATE, idResource.getTemporal());
		assertEquals(TemporalType.DATE, ((TemporalConverter) ormIdMapping.getConverter()).getTemporalType());
	
		((TemporalConverter) ormIdMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.TIME, idResource.getTemporal());
		assertEquals(TemporalType.TIME, ((TemporalConverter) ormIdMapping.getConverter()).getTemporalType());

		((TemporalConverter) ormIdMapping.getConverter()).setTemporalType(TemporalType.TIMESTAMP);
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.TIMESTAMP, idResource.getTemporal());
		assertEquals(TemporalType.TIMESTAMP, ((TemporalConverter) ormIdMapping.getConverter()).getTemporalType());

		//set temporal to null in the context model
		ormIdMapping.setConverter(Converter.NO_CONVERTER);
		assertNull(idResource.getTemporal());
		assertEquals(Converter.NO_CONVERTER, ormIdMapping.getConverter().getType());
	}
	
	//TODO test defaults
	//TODO test overriding java mapping with a different mapping type in xml

	
	public void testAddSequenceGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(ormIdMapping.getGeneratorContainer().getSequenceGenerator());
		assertNull(idResource.getSequenceGenerator());
		
		ormIdMapping.getGeneratorContainer().addSequenceGenerator();
		
		assertNotNull(idResource.getSequenceGenerator());
		assertNotNull(ormIdMapping.getGeneratorContainer().getSequenceGenerator());
				
		//try adding another sequence generator, should get an IllegalStateException
		try {
			ormIdMapping.getGeneratorContainer().addSequenceGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveSequenceGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(ormIdMapping.getGeneratorContainer().getSequenceGenerator());
		assertNull(idResource.getSequenceGenerator());

		ormIdMapping.getGeneratorContainer().addSequenceGenerator();
		assertNotNull(idResource.getSequenceGenerator());
		assertNotNull(ormIdMapping.getGeneratorContainer().getSequenceGenerator());

		ormIdMapping.getGeneratorContainer().removeSequenceGenerator();
		
		assertNull(ormIdMapping.getGeneratorContainer().getSequenceGenerator());
		assertNull(idResource.getSequenceGenerator());

		//try removing the sequence generator again, should get an IllegalStateException
		try {
			ormIdMapping.getGeneratorContainer().removeSequenceGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}

	public void testUpdateSequenceGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(ormIdMapping.getGeneratorContainer().getSequenceGenerator());
		assertNull(idResource.getSequenceGenerator());
		assertEquals(0, ormIdMapping.getPersistenceUnit().generatorsSize());
		
		idResource.setSequenceGenerator(OrmFactory.eINSTANCE.createXmlSequenceGenerator());
		assertNotNull(ormIdMapping.getGeneratorContainer().getSequenceGenerator());
		assertNotNull(idResource.getSequenceGenerator());
		assertEquals(1, ormIdMapping.getPersistenceUnit().generatorsSize());
		
		ormIdMapping.getGeneratorContainer().getSequenceGenerator().setName("foo");
		assertEquals(1, ormIdMapping.getPersistenceUnit().generatorsSize());
				
		idResource.setSequenceGenerator(null);
		assertNull(ormIdMapping.getGeneratorContainer().getSequenceGenerator());
		assertNull(idResource.getSequenceGenerator());
		assertEquals(0, ormIdMapping.getPersistenceUnit().generatorsSize());
	}
	
	public void testAddTableGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(ormIdMapping.getGeneratorContainer().getTableGenerator());
		assertNull(idResource.getTableGenerator());
		
		ormIdMapping.getGeneratorContainer().addTableGenerator();
		
		assertNotNull(idResource.getTableGenerator());
		assertNotNull(ormIdMapping.getGeneratorContainer().getTableGenerator());
				
		//try adding another table generator, should get an IllegalStateException
		try {
			ormIdMapping.getGeneratorContainer().addTableGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveTableGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(ormIdMapping.getGeneratorContainer().getTableGenerator());
		assertNull(idResource.getTableGenerator());

		ormIdMapping.getGeneratorContainer().addTableGenerator();
		assertNotNull(idResource.getTableGenerator());
		assertNotNull(ormIdMapping.getGeneratorContainer().getTableGenerator());

		ormIdMapping.getGeneratorContainer().removeTableGenerator();
		
		assertNull(ormIdMapping.getGeneratorContainer().getTableGenerator());
		assertNull(idResource.getTableGenerator());

		//try removing the table generator again, should get an IllegalStateException
		try {
			ormIdMapping.getGeneratorContainer().removeTableGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testUpdateTableGenerator() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(ormIdMapping.getGeneratorContainer().getTableGenerator());
		assertNull(idResource.getTableGenerator());
		assertEquals(0, ormIdMapping.getPersistenceUnit().generatorsSize());
		
		idResource.setTableGenerator(OrmFactory.eINSTANCE.createXmlTableGenerator());		
		assertNotNull(ormIdMapping.getGeneratorContainer().getTableGenerator());
		assertNotNull(idResource.getTableGenerator());
		assertEquals(1, ormIdMapping.getPersistenceUnit().generatorsSize());
		
		ormIdMapping.getGeneratorContainer().getTableGenerator().setName("foo");
		assertEquals(1, ormIdMapping.getGeneratorContainer().getPersistenceUnit().generatorsSize());

		idResource.setTableGenerator(null);
		assertNull(ormIdMapping.getGeneratorContainer().getTableGenerator());
		assertNull(idResource.getTableGenerator());
		assertEquals(0, ormIdMapping.getPersistenceUnit().generatorsSize());
	}

	public void testAddGeneratedValue() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(ormIdMapping.getGeneratedValue());
		assertNull(idResource.getGeneratedValue());
		
		ormIdMapping.addGeneratedValue();
		
		assertNotNull(idResource.getGeneratedValue());
		assertNotNull(ormIdMapping.getGeneratedValue());
				
		//try adding another sequence generator, should get an IllegalStateException
		try {
			ormIdMapping.addGeneratedValue();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveGeneratedValue() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(ormIdMapping.getGeneratedValue());
		assertNull(idResource.getGeneratedValue());

		ormIdMapping.addGeneratedValue();
		assertNotNull(idResource.getGeneratedValue());
		assertNotNull(ormIdMapping.getGeneratedValue());

		ormIdMapping.removeGeneratedValue();
		
		assertNull(ormIdMapping.getGeneratedValue());
		assertNull(idResource.getGeneratedValue());

		//try removing the sequence generator again, should get an IllegalStateException
		try {
			ormIdMapping.removeGeneratedValue();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}

	public void testUpdateGeneratedValue() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(ormIdMapping.getGeneratedValue());
		assertNull(idResource.getGeneratedValue());
		
		idResource.setGeneratedValue(OrmFactory.eINSTANCE.createXmlGeneratedValue());
				
		assertNotNull(ormIdMapping.getGeneratedValue());
		assertNotNull(idResource.getGeneratedValue());
				
		idResource.setGeneratedValue(null);
		assertNull(ormIdMapping.getGeneratedValue());
		assertNull(idResource.getGeneratedValue());
	}
	
	
	public void testIdMappingNoUnderylingJavaAttribute() throws Exception {
		createTestEntityIdMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "foo");
		assertEquals(2, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("foo", ormIdMapping.getName());
		assertEquals(Converter.NO_CONVERTER, ormIdMapping.getConverter().getType());
		assertNull(ormIdMapping.getGeneratedValue());
		assertNull(ormIdMapping.getGeneratorContainer().getSequenceGenerator());
		assertNull(ormIdMapping.getGeneratorContainer().getTableGenerator());

		
		OrmColumn ormColumn = ormIdMapping.getColumn();
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
		assertEquals(false, ormColumn.isDefaultUnique());
		assertEquals(true, ormColumn.isDefaultNullable());
		assertEquals(true, ormColumn.isDefaultInsertable());
		assertEquals(true, ormColumn.isDefaultUpdatable());
		assertEquals(null, ormColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, ormColumn.getDefaultTable());
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
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(2, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();	
		assertEquals("id", ormIdMapping.getName());
		assertEquals(Converter.TEMPORAL_CONVERTER, ormIdMapping.getConverter().getType());
		assertEquals(TemporalType.TIMESTAMP, ((TemporalConverter) ormIdMapping.getConverter()).getTemporalType());
		
		OrmColumn ormColumn = ormIdMapping.getColumn();
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
		
		OrmGeneratedValue ormGeneratedValue = ormIdMapping.getGeneratedValue();
		assertEquals("myTableGenerator", ormGeneratedValue.getSpecifiedGenerator());
		assertEquals(GenerationType.TABLE, ormGeneratedValue.getSpecifiedStrategy());
		
		OrmTableGenerator ormTableGenerator = ormIdMapping.getGeneratorContainer().getTableGenerator();
		assertEquals("myTableGenerator", ormTableGenerator.getName());
		assertEquals("myTable", ormTableGenerator.getSpecifiedTable());
		assertEquals("myCatalog", ormTableGenerator.getSpecifiedCatalog());
		assertEquals("mySchema", ormTableGenerator.getSpecifiedSchema());
		assertEquals("myPkColumnName", ormTableGenerator.getSpecifiedPkColumnName());
		assertEquals("myPkColumnValue", ormTableGenerator.getSpecifiedPkColumnValue());
		assertEquals("myValueColumnName", ormTableGenerator.getSpecifiedValueColumnName());
		assertEquals(Integer.valueOf(1), ormTableGenerator.getSpecifiedInitialValue());
		assertEquals(Integer.valueOf(1), ormTableGenerator.getSpecifiedAllocationSize());

		OrmSequenceGenerator ormSequenceGenerator = ormIdMapping.getGeneratorContainer().getSequenceGenerator();
		assertEquals("mySequenceGenerator", ormSequenceGenerator.getName());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(2, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();

		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
		
		ormPersistentAttribute.makeSpecified(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		ormPersistentAttribute= ormPersistentType.specifiedAttributes().next();
		
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();	
		assertEquals("id", ormIdMapping.getName());
		assertEquals(Converter.NO_CONVERTER, ormIdMapping.getConverter().getType());
		assertNull(ormIdMapping.getGeneratedValue());
		assertNull(ormIdMapping.getGeneratorContainer().getSequenceGenerator());
		assertNull(ormIdMapping.getGeneratorContainer().getTableGenerator());
		
		OrmColumn ormColumn = ormIdMapping.getColumn();
		assertEquals("id", ormColumn.getName());
		assertEquals(false, ormColumn.isDefaultUnique());
		assertEquals(true, ormColumn.isDefaultNullable());
		assertEquals(true, ormColumn.isDefaultInsertable());
		assertEquals(true, ormColumn.isDefaultUpdatable());
		assertNull(ormColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, ormColumn.getTable());
		assertEquals(255, ormColumn.getLength());
		assertEquals(0, ormColumn.getPrecision());
		assertEquals(0, ormColumn.getScale());
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityIdMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		assertEquals(1, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("id", ormIdMapping.getName());
		assertEquals(Converter.NO_CONVERTER, ormIdMapping.getConverter().getType());
		assertNull(ormIdMapping.getGeneratedValue());
		assertNull(ormIdMapping.getGeneratorContainer().getSequenceGenerator());
		assertNull(ormIdMapping.getGeneratorContainer().getTableGenerator());
		
		OrmColumn ormColumn = ormIdMapping.getColumn();
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
		assertEquals(false, ormColumn.isDefaultUnique());
		assertEquals(true, ormColumn.isDefaultNullable());
		assertEquals(true, ormColumn.isDefaultInsertable());
		assertEquals(true, ormColumn.isDefaultUpdatable());
		assertEquals(null, ormColumn.getColumnDefinition());
		assertEquals(TYPE_NAME, ormColumn.getDefaultTable());
		assertEquals(255, ormColumn.getDefaultLength());
		assertEquals(0, ormColumn.getDefaultPrecision());
		assertEquals(0, ormColumn.getDefaultScale());
	}
	
	//3 things tested above
	//1. virtual mapping metadata complete=false - defaults are taken from the java annotations
	//2. virtual mapping metadata complete=true - defaults are taken from java defaults,annotations ignored
	//3. specified mapping (metadata complete=true/false - defaults are taken from java annotations

	public void testIdMorphToBasicMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
		assertEquals("FOO", ((BasicMapping) ormPersistentAttribute.getMapping()).getColumn().getSpecifiedName());
	}
	
	public void testIdMorphToVersionMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
		assertEquals("FOO", ((VersionMapping) ormPersistentAttribute.getMapping()).getColumn().getSpecifiedName());
	}
	
	public void testIdMorphToTransientMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToEmbeddedMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToOneToOneMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToOneMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToOneToManyMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToManyToOneMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToManyToManyMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}	
}