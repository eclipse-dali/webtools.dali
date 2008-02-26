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
import org.eclipse.jpt.core.context.GenerationType;
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
import org.eclipse.jpt.core.internal.context.orm.GenericOrmIdMapping;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmSequenceGenerator;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmTableGenerator;
import org.eclipse.jpt.core.internal.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class XmlIdMappingTests extends ContextModelTestCase
{
	public XmlIdMappingTests(String name) {
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
	
	private void createIdAnnotation() throws Exception{
		this.createAnnotationAndMembers("Id", "");		
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
	
	private void createGeneratedValueAnnotation() throws Exception{
		this.createAnnotationAndMembers("GeneratedValue", 
			"GenerationType strategy() default AUTO;" +
			"String generator() default \"\"; ");		
	}

	
	private void createSequenceGeneratorAnnotation() throws Exception{
		this.createAnnotationAndMembers("SequenceGenerator", 
			"String name();" +
			"String sequenceName() default \"\"; " +
			"int initialValue() default 0; " +
			"int allocationSize() default 50;");		
	}

	private void createTableGeneratorAnnotation() throws Exception{
		this.createAnnotationAndMembers("TableGenerator", 
			"String name(); " +
			"String table() default \"\"; " +
			"String catalog() default \"\"; " +
			"String schema() default \"\";" +
			"String pkColumnName() default \"\"; " +
			"String valueColumnName() default \"\"; " +
			"String pkColumnValue() default \"\"; " +
			"int initialValue() default 0; " +
			"int allocationSize() default 50; " +
			"UniqueConstraint[] uniqueConstraints() default {};");		
	}

	private IType createTestEntityIdMapping() throws Exception {
		createEntityAnnotation();
		createIdAnnotation();
		createColumnAnnotation();
		createTemporalAnnotation();
		createGeneratedValueAnnotation();
		createSequenceGeneratorAnnotation();
		createTableGeneratorAnnotation();
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertEquals("idMapping", xmlIdMapping.getName());
		assertEquals("idMapping", idResource.getName());
				
		//set name in the resource model, verify context model updated
		idResource.setName("newName");
		assertEquals("newName", xmlIdMapping.getName());
		assertEquals("newName", idResource.getName());
	
		//set name to null in the resource model
		idResource.setName(null);
		assertNull(xmlIdMapping.getName());
		assertNull(idResource.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertEquals("idMapping", xmlIdMapping.getName());
		assertEquals("idMapping", idResource.getName());
				
		//set name in the context model, verify resource model updated
		xmlIdMapping.setName("newName");
		assertEquals("newName", xmlIdMapping.getName());
		assertEquals("newName", idResource.getName());
	
		//set name to null in the context model
		xmlIdMapping.setName(null);
		assertNull(xmlIdMapping.getName());
		assertNull(idResource.getName());
	}
	
	public void testUpdateTemporal() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		ormResource().save(null);
		
		assertNull(xmlIdMapping.getTemporal());
		assertNull(idResource.getTemporal());
				
		//set temporal in the resource model, verify context model updated
		idResource.setTemporal(org.eclipse.jpt.core.resource.orm.TemporalType.DATE);
		ormResource().save(null);
		assertEquals(TemporalType.DATE, xmlIdMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.DATE, idResource.getTemporal());
	
		idResource.setTemporal(org.eclipse.jpt.core.resource.orm.TemporalType.TIME);
		ormResource().save(null);
		assertEquals(TemporalType.TIME, xmlIdMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.TIME, idResource.getTemporal());

		idResource.setTemporal(org.eclipse.jpt.core.resource.orm.TemporalType.TIMESTAMP);
		ormResource().save(null);
		assertEquals(TemporalType.TIMESTAMP, xmlIdMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.TIMESTAMP, idResource.getTemporal());

		//set temporal to null in the resource model
		idResource.setTemporal(null);
		ormResource().save(null);
		assertNull(xmlIdMapping.getTemporal());
		assertNull(idResource.getTemporal());
	}
	
	public void testModifyTemporal() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		ormResource().save(null);
		
		assertNull(xmlIdMapping.getTemporal());
		assertNull(idResource.getTemporal());
				
		//set temporal in the context model, verify resource model updated
		xmlIdMapping.setTemporal(TemporalType.DATE);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.DATE, idResource.getTemporal());
		assertEquals(TemporalType.DATE, xmlIdMapping.getTemporal());
	
		xmlIdMapping.setTemporal(TemporalType.TIME);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.TIME, idResource.getTemporal());
		assertEquals(TemporalType.TIME, xmlIdMapping.getTemporal());

		xmlIdMapping.setTemporal(TemporalType.TIMESTAMP);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.resource.orm.TemporalType.TIMESTAMP, idResource.getTemporal());
		assertEquals(TemporalType.TIMESTAMP, xmlIdMapping.getTemporal());

		//set temporal to null in the context model
		xmlIdMapping.setTemporal(null);
		ormResource().save(null);
		assertNull(idResource.getTemporal());
		assertNull(xmlIdMapping.getTemporal());
	}
	
	//TODO test defaults
	//TODO test overriding java mapping with a different mapping type in xml

	
	public void testAddSequenceGenerator() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getSequenceGenerator());
		assertNull(idResource.getSequenceGenerator());
		
		xmlIdMapping.addSequenceGenerator();
		
		assertNotNull(idResource.getSequenceGenerator());
		assertNotNull(xmlIdMapping.getSequenceGenerator());
				
		//try adding another sequence generator, should get an IllegalStateException
		try {
			xmlIdMapping.addSequenceGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveSequenceGenerator() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getSequenceGenerator());
		assertNull(idResource.getSequenceGenerator());

		xmlIdMapping.addSequenceGenerator();
		assertNotNull(idResource.getSequenceGenerator());
		assertNotNull(xmlIdMapping.getSequenceGenerator());

		xmlIdMapping.removeSequenceGenerator();
		
		assertNull(xmlIdMapping.getSequenceGenerator());
		assertNull(idResource.getSequenceGenerator());

		//try removing the sequence generator again, should get an IllegalStateException
		try {
			xmlIdMapping.removeSequenceGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}

	public void testUpdateSequenceGenerator() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getSequenceGenerator());
		assertNull(idResource.getSequenceGenerator());
		
		idResource.setSequenceGenerator(OrmFactory.eINSTANCE.createSequenceGeneratorImpl());
				
		assertNotNull(xmlIdMapping.getSequenceGenerator());
		assertNotNull(idResource.getSequenceGenerator());
				
		idResource.setSequenceGenerator(null);
		assertNull(xmlIdMapping.getSequenceGenerator());
		assertNull(idResource.getSequenceGenerator());
	}
	
	public void testAddTableGenerator() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getTableGenerator());
		assertNull(idResource.getTableGenerator());
		
		xmlIdMapping.addTableGenerator();
		
		assertNotNull(idResource.getTableGenerator());
		assertNotNull(xmlIdMapping.getTableGenerator());
				
		//try adding another table generator, should get an IllegalStateException
		try {
			xmlIdMapping.addTableGenerator();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveTableGenerator() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getTableGenerator());
		assertNull(idResource.getTableGenerator());

		xmlIdMapping.addTableGenerator();
		assertNotNull(idResource.getTableGenerator());
		assertNotNull(xmlIdMapping.getTableGenerator());

		xmlIdMapping.removeTableGenerator();
		
		assertNull(xmlIdMapping.getTableGenerator());
		assertNull(idResource.getTableGenerator());

		//try removing the table generator again, should get an IllegalStateException
		try {
			xmlIdMapping.removeTableGenerator();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testUpdateTableGenerator() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getTableGenerator());
		assertNull(idResource.getTableGenerator());
		
		idResource.setTableGenerator(OrmFactory.eINSTANCE.createTableGeneratorImpl());
				
		assertNotNull(xmlIdMapping.getTableGenerator());
		assertNotNull(idResource.getTableGenerator());
				
		idResource.setTableGenerator(null);
		assertNull(xmlIdMapping.getTableGenerator());
		assertNull(idResource.getTableGenerator());
	}

	public void testAddGeneratedValue() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getGeneratedValue());
		assertNull(idResource.getGeneratedValue());
		
		xmlIdMapping.addGeneratedValue();
		
		assertNotNull(idResource.getGeneratedValue());
		assertNotNull(xmlIdMapping.getGeneratedValue());
				
		//try adding another sequence generator, should get an IllegalStateException
		try {
			xmlIdMapping.addGeneratedValue();
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}
	
	public void testRemoveGeneratedValue() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getGeneratedValue());
		assertNull(idResource.getGeneratedValue());

		xmlIdMapping.addGeneratedValue();
		assertNotNull(idResource.getGeneratedValue());
		assertNotNull(xmlIdMapping.getGeneratedValue());

		xmlIdMapping.removeGeneratedValue();
		
		assertNull(xmlIdMapping.getGeneratedValue());
		assertNull(idResource.getGeneratedValue());

		//try removing the sequence generator again, should get an IllegalStateException
		try {
			xmlIdMapping.removeGeneratedValue();		
		} catch (IllegalStateException e) {
			return;
		}
		fail("IllegalStateException not thrown");
	}

	public void testUpdateGeneratedValue() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();
		XmlId idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
		assertNull(xmlIdMapping.getGeneratedValue());
		assertNull(idResource.getGeneratedValue());
		
		idResource.setGeneratedValue(OrmFactory.eINSTANCE.createGeneratedValueImpl());
				
		assertNotNull(xmlIdMapping.getGeneratedValue());
		assertNotNull(idResource.getGeneratedValue());
				
		idResource.setGeneratedValue(null);
		assertNull(xmlIdMapping.getGeneratedValue());
		assertNull(idResource.getGeneratedValue());
	}
	
	
	public void testIdMappingNoUnderylingJavaAttribute() throws Exception {
		createTestEntityIdMapping();

		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "foo");
		assertEquals(2, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("foo", xmlIdMapping.getName());
		assertNull(xmlIdMapping.getTemporal());
		assertNull(xmlIdMapping.getGeneratedValue());
		assertNull(xmlIdMapping.getSequenceGenerator());
		assertNull(xmlIdMapping.getTableGenerator());

		
		OrmColumn ormColumn = xmlIdMapping.getColumn();
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
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(2, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();	
		assertEquals("id", xmlIdMapping.getName());
		assertEquals(TemporalType.TIMESTAMP, xmlIdMapping.getTemporal());
		
		OrmColumn ormColumn = xmlIdMapping.getColumn();
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
		
		OrmGeneratedValue ormGeneratedValue = xmlIdMapping.getGeneratedValue();
		assertEquals("myTableGenerator", ormGeneratedValue.getSpecifiedGenerator());
		assertEquals(GenerationType.TABLE, ormGeneratedValue.getSpecifiedStrategy());
		
		GenericOrmTableGenerator xmlTableGenerator = xmlIdMapping.getTableGenerator();
		assertEquals("myTableGenerator", xmlTableGenerator.getName());
		assertEquals("myTable", xmlTableGenerator.getSpecifiedTable());
		assertEquals("myCatalog", xmlTableGenerator.getSpecifiedCatalog());
		assertEquals("mySchema", xmlTableGenerator.getSpecifiedSchema());
		assertEquals("myPkColumnName", xmlTableGenerator.getSpecifiedPkColumnName());
		assertEquals("myPkColumnValue", xmlTableGenerator.getSpecifiedPkColumnValue());
		assertEquals("myValueColumnName", xmlTableGenerator.getSpecifiedValueColumnName());
		assertEquals(Integer.valueOf(1), xmlTableGenerator.getSpecifiedInitialValue());
		assertEquals(Integer.valueOf(1), xmlTableGenerator.getSpecifiedAllocationSize());

		GenericOrmSequenceGenerator xmlSequenceGenerator = xmlIdMapping.getSequenceGenerator();
		assertEquals("mySequenceGenerator", xmlSequenceGenerator.getName());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(2, ormPersistentType.virtualAttributesSize());		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.virtualAttributes().next();
		
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();	
		assertEquals("id", xmlIdMapping.getName());
		assertNull(xmlIdMapping.getTemporal());
		assertNull(xmlIdMapping.getGeneratedValue());
		assertNull(xmlIdMapping.getSequenceGenerator());
		assertNull(xmlIdMapping.getTableGenerator());
		
		OrmColumn ormColumn = xmlIdMapping.getColumn();
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
		createTestEntityIdMapping();

		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		assertEquals(1, ormPersistentType.virtualAttributesSize());
		
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.specifiedAttributes().next();
		GenericOrmIdMapping xmlIdMapping = (GenericOrmIdMapping) ormPersistentAttribute.getMapping();
		
		assertEquals("id", xmlIdMapping.getName());
		assertNull(xmlIdMapping.getTemporal());
		assertNull(xmlIdMapping.getGeneratedValue());
		assertNull(xmlIdMapping.getSequenceGenerator());
		assertNull(xmlIdMapping.getTableGenerator());
		
		OrmColumn ormColumn = xmlIdMapping.getColumn();
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
	
	//3 things tested above
	//1. virtual mapping metadata complete=false - defaults are taken from the java annotations
	//2. virtual mapping metadata complete=true - defaults are taken from java defaults,annotations ignored
	//3. specified mapping (metadata complete=true/false - defaults are taken from java annotations

	public void testIdMorphToBasicMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setTemporal(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
		assertEquals(TemporalType.TIME, ((BasicMapping) ormPersistentAttribute.getMapping()).getTemporal());
		assertEquals("FOO", ((BasicMapping) ormPersistentAttribute.getMapping()).getColumn().getSpecifiedName());
	}
	
	public void testIdMorphToVersionMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setTemporal(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
		assertEquals(TemporalType.TIME, ((VersionMapping) ormPersistentAttribute.getMapping()).getTemporal());
		assertEquals("FOO", ((VersionMapping) ormPersistentAttribute.getMapping()).getColumn().getSpecifiedName());
	}
	
	public void testIdMorphToTransientMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setTemporal(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToEmbeddedMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setTemporal(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setTemporal(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToOneToOneMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setTemporal(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToOneMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToOneToManyMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setTemporal(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToManyToOneMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setTemporal(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testIdMorphToManyToManyMapping() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		
		IdMapping idMapping = (IdMapping) ormPersistentAttribute.getMapping();
		assertFalse(idMapping.isDefault());
		idMapping.getColumn().setSpecifiedName("FOO");
		idMapping.setTemporal(TemporalType.TIME);
		assertFalse(idMapping.isDefault());
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("id", ormPersistentAttribute.getMapping().getName());
	}	
}