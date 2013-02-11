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
import org.eclipse.jpt.jpa.core.context.GeneratedValue;
import org.eclipse.jpt.jpa.core.context.GenerationType;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.ManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.OneToManyMapping;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.SequenceGenerator;
import org.eclipse.jpt.jpa.core.context.TableGenerator;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlId;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

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
		mappingFileRef.setFileName(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}

	private ICompilationUnit createTestEntityIdMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, JPA.COLUMN, JPA.TEMPORAL, JPA.TEMPORAL_TYPE, JPA.GENERATED_VALUE, JPA.GENERATION_TYPE, JPA.TABLE_GENERATOR, JPA.SEQUENCE_GENERATOR);
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertEquals("id", ormIdMapping.getName());
		assertEquals("id", idResource.getName());
				
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertEquals("id", ormIdMapping.getName());
		assertEquals("id", idResource.getName());
				
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(ormIdMapping.getConverter().getType());
		assertNull(idResource.getTemporal());
				
		//set temporal in the resource model, verify context model updated
		idResource.setTemporal(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.DATE);
		assertEquals(TemporalType.DATE, ((BaseTemporalConverter) ormIdMapping.getConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.DATE, idResource.getTemporal());
	
		idResource.setTemporal(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIME);
		assertEquals(TemporalType.TIME, ((BaseTemporalConverter) ormIdMapping.getConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIME, idResource.getTemporal());

		idResource.setTemporal(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIMESTAMP);
		assertEquals(TemporalType.TIMESTAMP, ((BaseTemporalConverter) ormIdMapping.getConverter()).getTemporalType());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIMESTAMP, idResource.getTemporal());

		//set temporal to null in the resource model
		idResource.setTemporal(null);
		assertNull(ormIdMapping.getConverter().getType());
		assertNull(idResource.getTemporal());
	}
	
	public void testModifyTemporal() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(ormIdMapping.getConverter().getType());
		assertNull(idResource.getTemporal());
				
		//set temporal in the context model, verify resource model updated
		ormIdMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) ormIdMapping.getConverter()).setTemporalType(TemporalType.DATE);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.DATE, idResource.getTemporal());
		assertEquals(TemporalType.DATE, ((BaseTemporalConverter) ormIdMapping.getConverter()).getTemporalType());
	
		((BaseTemporalConverter) ormIdMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIME, idResource.getTemporal());
		assertEquals(TemporalType.TIME, ((BaseTemporalConverter) ormIdMapping.getConverter()).getTemporalType());

		((BaseTemporalConverter) ormIdMapping.getConverter()).setTemporalType(TemporalType.TIMESTAMP);
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIMESTAMP, idResource.getTemporal());
		assertEquals(TemporalType.TIMESTAMP, ((BaseTemporalConverter) ormIdMapping.getConverter()).getTemporalType());

		//set temporal to null in the context model
		ormIdMapping.setConverter(null);
		assertNull(idResource.getTemporal());
		assertNull(ormIdMapping.getConverter().getType());
	}
	
	//TODO test defaults
	//TODO test overriding java mapping with a different mapping type in xml

	
	public void testAddSequenceGenerator() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(ormIdMapping.getGeneratorContainer().getSequenceGenerator());
		assertNull(idResource.getSequenceGenerator());
		assertEquals(0, ormIdMapping.getPersistenceUnit().getGeneratorsSize());
		
		idResource.setSequenceGenerator(OrmFactory.eINSTANCE.createXmlSequenceGenerator());
		assertNotNull(ormIdMapping.getGeneratorContainer().getSequenceGenerator());
		assertNotNull(idResource.getSequenceGenerator());
		assertEquals(1, ormIdMapping.getPersistenceUnit().getGeneratorsSize());
		
		ormIdMapping.getGeneratorContainer().getSequenceGenerator().setName("foo");
		assertEquals(1, ormIdMapping.getPersistenceUnit().getGeneratorsSize());
				
		idResource.setSequenceGenerator(null);
		assertNull(ormIdMapping.getGeneratorContainer().getSequenceGenerator());
		assertNull(idResource.getSequenceGenerator());
		assertEquals(0, ormIdMapping.getPersistenceUnit().getGeneratorsSize());
	}
	
	public void testAddTableGenerator() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(ormIdMapping.getGeneratorContainer().getTableGenerator());
		assertNull(idResource.getTableGenerator());
		assertEquals(0, ormIdMapping.getPersistenceUnit().getGeneratorsSize());
		
		idResource.setTableGenerator(OrmFactory.eINSTANCE.createXmlTableGenerator());		
		assertNotNull(ormIdMapping.getGeneratorContainer().getTableGenerator());
		assertNotNull(idResource.getTableGenerator());
		assertEquals(1, ormIdMapping.getPersistenceUnit().getGeneratorsSize());
		
		ormIdMapping.getGeneratorContainer().getTableGenerator().setName("foo");
		assertEquals(1, ormIdMapping.getGeneratorContainer().getPersistenceUnit().getGeneratorsSize());

		idResource.setTableGenerator(null);
		assertNull(ormIdMapping.getGeneratorContainer().getTableGenerator());
		assertNull(idResource.getTableGenerator());
		assertEquals(0, ormIdMapping.getPersistenceUnit().getGeneratorsSize());
	}

	public void testAddGeneratedValue() throws Exception {
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
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
		createTestType();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
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
		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getDefaultAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		ormIdMapping.setName("foo");
		
		assertEquals("foo", ormIdMapping.getName());
		assertNull(ormIdMapping.getConverter().getType());
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
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getDefaultAttributes().iterator().next();
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();	
		assertEquals("id", idMapping.getName());
		assertEquals(BaseTemporalConverter.class, idMapping.getConverter().getType());
		assertEquals(TemporalType.TIMESTAMP, ((BaseTemporalConverter) idMapping.getConverter()).getTemporalType());
		
		Column column = idMapping.getColumn();
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
		
		GeneratedValue generatedValue = idMapping.getGeneratedValue();
		assertEquals("myTableGenerator", generatedValue.getSpecifiedGenerator());
		assertEquals(GenerationType.TABLE, generatedValue.getSpecifiedStrategy());

// >>> I'm going to argue there is no such thing as a "virtual" generator,
// since a Java generator is *not* overridden when its ID mapping (or entity)
// is overridden in the orm.xml file - both the orm.xml ID mapping and the
// Java mapping can define an "active" generator as long as they have different
// names  ~bjv
		TableGenerator tableGenerator = idMapping.getGeneratorContainer().getTableGenerator();
		assertNull(tableGenerator);
//		assertEquals("myTableGenerator", tableGenerator.getName());
//		assertEquals("myTable", tableGenerator.getSpecifiedTable());
//		assertEquals("myCatalog", tableGenerator.getSpecifiedCatalog());
//		assertEquals("mySchema", tableGenerator.getSpecifiedSchema());
//		assertEquals("myPkColumnName", tableGenerator.getSpecifiedPkColumnName());
//		assertEquals("myPkColumnValue", tableGenerator.getSpecifiedPkColumnValue());
//		assertEquals("myValueColumnName", tableGenerator.getSpecifiedValueColumnName());
//		assertEquals(Integer.valueOf(1), tableGenerator.getSpecifiedInitialValue());
//		assertEquals(Integer.valueOf(1), tableGenerator.getSpecifiedAllocationSize());

		SequenceGenerator sequenceGenerator = idMapping.getGeneratorContainer().getSequenceGenerator();
		assertNull(sequenceGenerator);
//		assertEquals("mySequenceGenerator", sequenceGenerator.getName());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(2, ormPersistentType.getDefaultAttributesSize());		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");

		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, ormPersistentAttribute.getMappingKey());
		
		ormPersistentAttribute.addToXml(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();	
		assertFalse(ormPersistentAttribute.isVirtual());
		assertEquals("id", ormIdMapping.getName());
		assertNull(ormIdMapping.getConverter().getType());
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
		assertEquals(TYPE_NAME, ormColumn.getTableName());
		assertEquals(255, ormColumn.getLength());
		assertEquals(0, ormColumn.getPrecision());
		assertEquals(0, ormColumn.getScale());
	}
	
	public void testSpecifiedMapping() throws Exception {
		createTestEntityIdMapping();

		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getDefaultAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getSpecifiedAttributes().iterator().next();
		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("id", ormIdMapping.getName());
		assertNull(ormIdMapping.getConverter().getType());
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
	
	//3 things tested above
	//1. virtual mapping metadata complete=false - defaults are taken from the java annotations
	//2. virtual mapping metadata complete=true - defaults are taken from java defaults,annotations ignored
	//3. specified mapping (metadata complete=true/false - defaults are taken from java annotations

	public void testIdMorphToBasicMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
		assertEquals("FOO", ((BasicMapping) ormPersistentAttribute.getMapping()).getColumn().getSpecifiedName());
	}
	
	public void testIdMorphToVersionMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
	
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
		assertEquals("FOO", ((VersionMapping) ormPersistentAttribute.getMapping()).getColumn().getSpecifiedName());
	}
	
	public void testIdMorphToTransientMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToEmbeddedMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToOneToOneMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToOneMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToOneToManyMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToManyToOneMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToManyToManyMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addAttributeToXml(ormPersistentType.getAttributeNamed("id"), MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.getSpecifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.getSpecifiedAttributes().iterator().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}	
}