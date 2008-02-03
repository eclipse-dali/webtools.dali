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
import org.eclipse.jpt.core.internal.context.base.GenerationType;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.context.orm.XmlColumn;
import org.eclipse.jpt.core.internal.context.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.internal.context.orm.XmlIdMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.context.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.internal.context.orm.XmlTableGenerator;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.orm.Id;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		ormResource().save(null);
		
		assertNull(xmlIdMapping.getTemporal());
		assertNull(idResource.getTemporal());
				
		//set temporal in the resource model, verify context model updated
		idResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE);
		ormResource().save(null);
		assertEquals(TemporalType.DATE, xmlIdMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE, idResource.getTemporal());
	
		idResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME);
		ormResource().save(null);
		assertEquals(TemporalType.TIME, xmlIdMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME, idResource.getTemporal());

		idResource.setTemporal(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP);
		ormResource().save(null);
		assertEquals(TemporalType.TIMESTAMP, xmlIdMapping.getTemporal());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP, idResource.getTemporal());

		//set temporal to null in the resource model
		idResource.setTemporal(null);
		ormResource().save(null);
		assertNull(xmlIdMapping.getTemporal());
		assertNull(idResource.getTemporal());
	}
	
	public void testModifyTemporal() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		ormResource().save(null);
		
		assertNull(xmlIdMapping.getTemporal());
		assertNull(idResource.getTemporal());
				
		//set temporal in the context model, verify resource model updated
		xmlIdMapping.setTemporal(TemporalType.DATE);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.DATE, idResource.getTemporal());
		assertEquals(TemporalType.DATE, xmlIdMapping.getTemporal());
	
		xmlIdMapping.setTemporal(TemporalType.TIME);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIME, idResource.getTemporal());
		assertEquals(TemporalType.TIME, xmlIdMapping.getTemporal());

		xmlIdMapping.setTemporal(TemporalType.TIMESTAMP);
		ormResource().save(null);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.TemporalType.TIMESTAMP, idResource.getTemporal());
		assertEquals(TemporalType.TIMESTAMP, xmlIdMapping.getTemporal());

		//set temporal to null in the context model
		xmlIdMapping.setTemporal(null);
		ormResource().save(null);
		assertNull(idResource.getTemporal());
		assertNull(xmlIdMapping.getTemporal());
	}
	
	//TODO test morphing to other mapping types
	//TODO test defaults

	
	public void testAddSequenceGenerator() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "idMapping");
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		Id idResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getIds().get(0);
		
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

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "foo");
		assertEquals(2, xmlPersistentType.virtualAttributesSize());
		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.specifiedAttributes().next();
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		
		assertEquals("foo", xmlIdMapping.getName());
		assertNull(xmlIdMapping.getTemporal());
		assertNull(xmlIdMapping.getGeneratedValue());
		assertNull(xmlIdMapping.getSequenceGenerator());
		assertNull(xmlIdMapping.getTableGenerator());

		
		XmlColumn xmlColumn = xmlIdMapping.getColumn();
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
		createTestEntityIdMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(2, xmlPersistentType.virtualAttributesSize());		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.virtualAttributes().next();
		
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();	
		assertEquals("id", xmlIdMapping.getName());
		assertEquals(TemporalType.TIMESTAMP, xmlIdMapping.getTemporal());
		
		XmlColumn xmlColumn = xmlIdMapping.getColumn();
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
		
		XmlGeneratedValue xmlGeneratedValue = xmlIdMapping.getGeneratedValue();
		assertEquals("myTableGenerator", xmlGeneratedValue.getSpecifiedGenerator());
		assertEquals(GenerationType.TABLE, xmlGeneratedValue.getSpecifiedStrategy());
		
		XmlTableGenerator xmlTableGenerator = xmlIdMapping.getTableGenerator();
		assertEquals("myTableGenerator", xmlTableGenerator.getName());
		assertEquals("myTable", xmlTableGenerator.getSpecifiedTable());
		assertEquals("myCatalog", xmlTableGenerator.getSpecifiedCatalog());
		assertEquals("mySchema", xmlTableGenerator.getSpecifiedSchema());
		assertEquals("myPkColumnName", xmlTableGenerator.getSpecifiedPkColumnName());
		assertEquals("myPkColumnValue", xmlTableGenerator.getSpecifiedPkColumnValue());
		assertEquals("myValueColumnName", xmlTableGenerator.getSpecifiedValueColumnName());
		assertEquals(Integer.valueOf(1), xmlTableGenerator.getSpecifiedInitialValue());
		assertEquals(Integer.valueOf(1), xmlTableGenerator.getSpecifiedAllocationSize());

		XmlSequenceGenerator xmlSequenceGenerator = xmlIdMapping.getSequenceGenerator();
		assertEquals("mySequenceGenerator", xmlSequenceGenerator.getName());
	}
	
	public void testVirtualMappingMetadataCompleteTrue() throws Exception {
		createTestEntityIdMapping();
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		xmlPersistentType.getMapping().setSpecifiedMetadataComplete(true);
		assertEquals(2, xmlPersistentType.virtualAttributesSize());		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.virtualAttributes().next();
		
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();	
		assertEquals("id", xmlIdMapping.getName());
		assertNull(xmlIdMapping.getTemporal());
		assertNull(xmlIdMapping.getGeneratedValue());
		assertNull(xmlIdMapping.getSequenceGenerator());
		assertNull(xmlIdMapping.getTableGenerator());
		
		XmlColumn xmlColumn = xmlIdMapping.getColumn();
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
		createTestEntityIdMapping();

		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY, "id");
		assertEquals(1, xmlPersistentType.virtualAttributesSize());
		
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.specifiedAttributes().next();
		XmlIdMapping xmlIdMapping = (XmlIdMapping) xmlPersistentAttribute.getMapping();
		
		assertEquals("id", xmlIdMapping.getName());
		assertNull(xmlIdMapping.getTemporal());
		assertNull(xmlIdMapping.getGeneratedValue());
		assertNull(xmlIdMapping.getSequenceGenerator());
		assertNull(xmlIdMapping.getTableGenerator());
		
		XmlColumn xmlColumn = xmlIdMapping.getColumn();
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
	
	//3 things tested above
	//1. virtual mapping metadata complete=false - defaults are taken from the java annotations
	//2. virtual mapping metadata complete=true - defaults are taken from java defaults,annotations ignored
	//3. specified mapping (metadata complete=true/false - defaults are taken from java annotations

	
}