/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.TableGenerator;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class JavaTableGeneratorTests extends ContextModelTestCase
{
	private static final String TABLE_GENERATOR_NAME = "MY_TABLE_GENERATOR";

	private ICompilationUnit createTestEntityWithTableGenerator() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.TABLE_GENERATOR, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("@TableGenerator(name=\"" + TABLE_GENERATOR_NAME + "\")");
			}
		});
	}
		
	public JavaTableGeneratorTests(String name) {
		super(name);
	}

	public void testGetName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(TABLE_GENERATOR_NAME, idMapping.getGeneratorContainer().getTableGenerator().getName());

		//change resource model tableGenerator name, verify the context model is updated
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.setName("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("foo", idMapping.getGeneratorContainer().getTableGenerator().getName());
	}

	public void testSetName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(TABLE_GENERATOR_NAME, idMapping.getGeneratorContainer().getTableGenerator().getName());

		idMapping.getGeneratorContainer().getTableGenerator().setName("foo");
		
		assertEquals("foo", idMapping.getGeneratorContainer().getTableGenerator().getName());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);
		
		assertEquals("foo", tableGenerator.getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(TABLE_GENERATOR_NAME, idMapping.getGeneratorContainer().getTableGenerator().getName());

		idMapping.getGeneratorContainer().getTableGenerator().setName(null);
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);
		
		assertNull(tableGenerator);
	}
	
	public void testGetCatalog() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getCatalog());
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setCatalog("myCatalog");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("myCatalog", idMapping.getGeneratorContainer().getTableGenerator().getCatalog());
		assertEquals("myCatalog", idMapping.getGeneratorContainer().getTableGenerator().getSpecifiedCatalog());
	}
	
	public void testGetDefaultCatalog() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();

		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getDefaultCatalog());
		
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedCatalog("myCatalog");
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getDefaultCatalog());
		assertEquals("myCatalog", idMapping.getGeneratorContainer().getTableGenerator().getSpecifiedCatalog());
	}
	
	public void testSetSpecifiedCatalog() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedCatalog("myCatalog");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("myCatalog", tableGenerator.getCatalog());
		
		idMapping.getGeneratorContainer().getTableGenerator().setName(null);
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedCatalog(null);
		assertNull(attributeResource.getAnnotation(JPA.TABLE_GENERATOR));
	}

	public void testGetSchema() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getSchema());
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setSchema("mySchema");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("mySchema", idMapping.getGeneratorContainer().getTableGenerator().getSchema());
		assertEquals("mySchema", idMapping.getGeneratorContainer().getTableGenerator().getSpecifiedSchema());
	}
	
	public void testGetDefaultSchema() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();

		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getDefaultSchema());
		
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedSchema("mySchema");
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getDefaultSchema());
		assertEquals("mySchema", idMapping.getGeneratorContainer().getTableGenerator().getSpecifiedSchema());
	}
	
	public void testUpdateDefaultSchemaFromPersistenceUnitDefaults() throws Exception {
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString());
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);

		createTestEntityWithTableGenerator();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		IdMapping idMapping = (IdMapping)  ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getDefaultSchema());
		
		getEntityMappings().getPersistenceUnitDefaults().setSpecifiedSchema("FOO");
		assertEquals("FOO", idMapping.getGeneratorContainer().getTableGenerator().getDefaultSchema());
		
		getEntityMappings().setSpecifiedSchema("BAR");
		assertEquals("BAR", idMapping.getGeneratorContainer().getTableGenerator().getDefaultSchema());
		
		ormEntity.getTable().setSpecifiedSchema("XML_SCHEMA");
		assertEquals("BAR", idMapping.getGeneratorContainer().getTableGenerator().getDefaultSchema());

		getEntityMappings().removePersistentType(0);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		//default schema taken from persistence-unit-defaults not entity-mappings since the entity is not in an orm.xml file
		assertEquals("FOO", ((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getGeneratorContainer().getTableGenerator().getDefaultSchema());
	}

	public void testSetSpecifiedSchema() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedSchema("mySchema");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("mySchema", tableGenerator.getSchema());
		
		idMapping.getGeneratorContainer().getTableGenerator().setName(null);
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedSchema(null);
		assertNull(attributeResource.getAnnotation(JPA.TABLE_GENERATOR));
	}
	
	public void testGetPkColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getPkColumnName());
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setPkColumnName("myPkColumnName");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("myPkColumnName", idMapping.getGeneratorContainer().getTableGenerator().getPkColumnName());
		assertEquals("myPkColumnName", idMapping.getGeneratorContainer().getTableGenerator().getSpecifiedPkColumnName());
	}
	
	public void testGetDefaultPkColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();

		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getDefaultPkColumnName());
		
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedPkColumnName("myPkColumnName");
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getDefaultPkColumnName());
		assertEquals("myPkColumnName", idMapping.getGeneratorContainer().getTableGenerator().getSpecifiedPkColumnName());
	}
	
	public void testSetSpecifiedPkColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedPkColumnName("myPkColumnName");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("myPkColumnName", tableGenerator.getPkColumnName());
		
		idMapping.getGeneratorContainer().getTableGenerator().setName(null);
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedPkColumnName(null);
		assertNull(attributeResource.getAnnotation(JPA.TABLE_GENERATOR));
	}	
	
	public void testGetValueColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getValueColumnName());
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setValueColumnName("myValueColumnName");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("myValueColumnName", idMapping.getGeneratorContainer().getTableGenerator().getValueColumnName());
		assertEquals("myValueColumnName", idMapping.getGeneratorContainer().getTableGenerator().getSpecifiedValueColumnName());
	}
	
	public void testGetDefaultValueColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();

		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getDefaultValueColumnName());
		
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedValueColumnName("myValueColumnName");
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getDefaultValueColumnName());
		assertEquals("myValueColumnName", idMapping.getGeneratorContainer().getTableGenerator().getSpecifiedValueColumnName());
	}
	
	public void testSetSpecifiedValueColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedValueColumnName("myValueColumnName");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("myValueColumnName", tableGenerator.getValueColumnName());
		
		idMapping.getGeneratorContainer().getTableGenerator().setName(null);
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedValueColumnName(null);
		assertNull(attributeResource.getAnnotation(JPA.TABLE_GENERATOR));
	}
	
	public void testGetPkColumnValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getPkColumnValue());
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setPkColumnValue("myPkColumnValue");
		getJpaProject().synchronizeContextModel();
		
		assertEquals("myPkColumnValue", idMapping.getGeneratorContainer().getTableGenerator().getPkColumnValue());
		assertEquals("myPkColumnValue", idMapping.getGeneratorContainer().getTableGenerator().getSpecifiedPkColumnValue());
	}
	
	public void testGetDefaultPkColumnValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();

		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getDefaultPkColumnValue());
		
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedPkColumnValue("myPkColumnValue");
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getDefaultPkColumnValue());
		assertEquals("myPkColumnValue", idMapping.getGeneratorContainer().getTableGenerator().getSpecifiedPkColumnValue());
	}
	
	public void testSetSpecifiedPkColumnValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedPkColumnValue("myPkColumnValue");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("myPkColumnValue", tableGenerator.getPkColumnValue());
		
		idMapping.getGeneratorContainer().getTableGenerator().setName(null);
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedPkColumnValue(null);
		assertNull(attributeResource.getAnnotation(JPA.TABLE_GENERATOR));
	}
	
	public void testGetInitialValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertEquals(TableGenerator.DEFAULT_INITIAL_VALUE, idMapping.getGeneratorContainer().getTableGenerator().getInitialValue());
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setInitialValue(Integer.valueOf(82));
		getJpaProject().synchronizeContextModel();
		
		assertEquals(82, idMapping.getGeneratorContainer().getTableGenerator().getInitialValue());
		assertEquals(Integer.valueOf(82), idMapping.getGeneratorContainer().getTableGenerator().getSpecifiedInitialValue());
	}
	
	public void testGetDefaultInitialValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();

		assertEquals(TableGenerator.DEFAULT_INITIAL_VALUE, idMapping.getGeneratorContainer().getTableGenerator().getDefaultInitialValue());
		
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedInitialValue(Integer.valueOf(82));
		
		assertEquals(TableGenerator.DEFAULT_INITIAL_VALUE, idMapping.getGeneratorContainer().getTableGenerator().getDefaultInitialValue());
		assertEquals(Integer.valueOf(82), idMapping.getGeneratorContainer().getTableGenerator().getSpecifiedInitialValue());
	}
	
	public void testSetSpecifiedInitialValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedInitialValue(Integer.valueOf(20));
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals(Integer.valueOf(20), tableGenerator.getInitialValue());
		
		idMapping.getGeneratorContainer().getTableGenerator().setName(null);
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedInitialValue(null);
		assertNull(attributeResource.getAnnotation(JPA.TABLE_GENERATOR));
	}
	
	public void testGetAllocationSize() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertEquals(Generator.DEFAULT_ALLOCATION_SIZE, idMapping.getGeneratorContainer().getTableGenerator().getAllocationSize());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setAllocationSize(Integer.valueOf(20));
		getJpaProject().synchronizeContextModel();
		
		assertEquals(20, idMapping.getGeneratorContainer().getTableGenerator().getAllocationSize());
		assertEquals(Integer.valueOf(20), idMapping.getGeneratorContainer().getTableGenerator().getSpecifiedAllocationSize());
	}
	
	public void testGetDefaultAllocationSize() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();

		assertEquals(Generator.DEFAULT_ALLOCATION_SIZE, idMapping.getGeneratorContainer().getTableGenerator().getDefaultAllocationSize());
		
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedAllocationSize(Integer.valueOf(20));
		
		assertEquals(Generator.DEFAULT_ALLOCATION_SIZE, idMapping.getGeneratorContainer().getTableGenerator().getDefaultAllocationSize());
		assertEquals(Integer.valueOf(20), idMapping.getGeneratorContainer().getTableGenerator().getSpecifiedAllocationSize());
	}
	
	public void testSetSpecifiedAllocationSize() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedAllocationSize(Integer.valueOf(25));
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals(Integer.valueOf(25), tableGenerator.getAllocationSize());
		
		idMapping.getGeneratorContainer().getTableGenerator().setName(null);
		idMapping.getGeneratorContainer().getTableGenerator().setSpecifiedAllocationSize(null);
		assertNull(attributeResource.getAnnotation(JPA.TABLE_GENERATOR));
	}
	

	public void testUniqueConstraints() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		TableGenerator tableGenerator = ((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getGeneratorContainer().getTableGenerator();

		ListIterator<JavaUniqueConstraint> uniqueConstraints = tableGenerator.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGeneratorAnnotation = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		tableGeneratorAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		tableGeneratorAnnotation.addUniqueConstraint(0).addColumnName(0, "bar");
		getJpaProject().synchronizeContextModel();
		
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("bar", uniqueConstraints.next().columnNames().next());
		assertEquals("foo", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsSize() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		TableGenerator tableGenerator = ((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getGeneratorContainer().getTableGenerator();

		assertEquals(0,  tableGenerator.uniqueConstraintsSize());

		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGeneratorAnnotation = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		tableGeneratorAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		tableGeneratorAnnotation.addUniqueConstraint(1).addColumnName(0, "bar");
		
		getJpaProject().synchronizeContextModel();
		assertEquals(2,  tableGenerator.uniqueConstraintsSize());
	}

	public void testAddUniqueConstraint() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		TableGenerator tableGenerator = ((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getGeneratorContainer().getTableGenerator();
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "FOO");
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "BAR");
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGeneratorAnnotation = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		ListIterator<UniqueConstraintAnnotation> uniqueConstraints = tableGeneratorAnnotation.uniqueConstraints();
		
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testAddUniqueConstraint2() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		TableGenerator tableGenerator = ((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getGeneratorContainer().getTableGenerator();
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "FOO");
		tableGenerator.addUniqueConstraint(1).addColumnName(0, "BAR");
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGeneratorAnnotation = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		ListIterator<UniqueConstraintAnnotation> uniqueConstraints = tableGeneratorAnnotation.uniqueConstraints();
		
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		TableGenerator tableGenerator = ((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getGeneratorContainer().getTableGenerator();
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "FOO");
		tableGenerator.addUniqueConstraint(1).addColumnName(0, "BAR");
		tableGenerator.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGeneratorAnnotation = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals(3, tableGeneratorAnnotation.uniqueConstraintsSize());

		tableGenerator.removeUniqueConstraint(1);
		
		ListIterator<UniqueConstraintAnnotation> uniqueConstraintAnnotations = tableGeneratorAnnotation.uniqueConstraints();
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNames().next());		
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNames().next());
		assertFalse(uniqueConstraintAnnotations.hasNext());
		
		Iterator<UniqueConstraint> uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());		
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		
		tableGenerator.removeUniqueConstraint(1);
		uniqueConstraintAnnotations = tableGeneratorAnnotation.uniqueConstraints();
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNames().next());		
		assertFalse(uniqueConstraintAnnotations.hasNext());

		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());		
		assertFalse(uniqueConstraints.hasNext());

		
		tableGenerator.removeUniqueConstraint(0);
		uniqueConstraintAnnotations = tableGeneratorAnnotation.uniqueConstraints();
		assertFalse(uniqueConstraintAnnotations.hasNext());
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		TableGenerator tableGenerator = ((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getGeneratorContainer().getTableGenerator();
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "FOO");
		tableGenerator.addUniqueConstraint(1).addColumnName(0, "BAR");
		tableGenerator.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGeneratorAnnotation = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals(3, tableGeneratorAnnotation.uniqueConstraintsSize());
		
		
		tableGenerator.moveUniqueConstraint(2, 0);
		ListIterator<UniqueConstraint> uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());

		ListIterator<UniqueConstraintAnnotation> uniqueConstraintAnnotations = tableGeneratorAnnotation.uniqueConstraints();
		assertEquals("BAR", uniqueConstraintAnnotations.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNames().next());
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNames().next());


		tableGenerator.moveUniqueConstraint(0, 1);
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());

		uniqueConstraintAnnotations = tableGeneratorAnnotation.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNames().next());
		assertEquals("BAR", uniqueConstraintAnnotations.next().columnNames().next());
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNames().next());
	}
	
	public void testUpdateUniqueConstraints() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		TableGenerator tableGenerator = ((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getGeneratorContainer().getTableGenerator();
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TableGeneratorAnnotation tableGeneratorAnnotation = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
	
		tableGeneratorAnnotation.addUniqueConstraint(0).addColumnName("FOO");
		tableGeneratorAnnotation.addUniqueConstraint(1).addColumnName("BAR");
		tableGeneratorAnnotation.addUniqueConstraint(2).addColumnName("BAZ");
		getJpaProject().synchronizeContextModel();
		
		ListIterator<UniqueConstraint> uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		tableGeneratorAnnotation.moveUniqueConstraint(2, 0);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableGeneratorAnnotation.moveUniqueConstraint(0, 1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableGeneratorAnnotation.removeUniqueConstraint(1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableGeneratorAnnotation.removeUniqueConstraint(1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		tableGeneratorAnnotation.removeUniqueConstraint(0);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
	}
}
