/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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

		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(TABLE_GENERATOR_NAME, idMapping.getTableGenerator().getName());

		//change resource model tableGenerator name, verify the context model is updated
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.setName("foo");
		
		assertEquals("foo", idMapping.getTableGenerator().getName());
	}

	public void testSetName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(TABLE_GENERATOR_NAME, idMapping.getTableGenerator().getName());

		idMapping.getTableGenerator().setName("foo");
		
		assertEquals("foo", idMapping.getTableGenerator().getName());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);
		
		assertEquals("foo", tableGenerator.getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		assertEquals(TABLE_GENERATOR_NAME, idMapping.getTableGenerator().getName());

		idMapping.getTableGenerator().setName(null);
		
		assertNull(idMapping.getTableGenerator());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);
		
		assertNull(tableGenerator);
	}
	
	public void testGetCatalog() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getTableGenerator().getCatalog());
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setCatalog("myCatalog");
		
		assertEquals("myCatalog", idMapping.getTableGenerator().getCatalog());
		assertEquals("myCatalog", idMapping.getTableGenerator().getSpecifiedCatalog());
	}
	
	public void testGetDefaultCatalog() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();

		assertNull(idMapping.getTableGenerator().getDefaultCatalog());
		
		idMapping.getTableGenerator().setSpecifiedCatalog("myCatalog");
		
		assertNull(idMapping.getTableGenerator().getDefaultCatalog());
		assertEquals("myCatalog", idMapping.getTableGenerator().getSpecifiedCatalog());
	}
	
	public void testSetSpecifiedCatalog() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getTableGenerator().setSpecifiedCatalog("myCatalog");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("myCatalog", tableGenerator.getCatalog());
		
		idMapping.getTableGenerator().setName(null);
		idMapping.getTableGenerator().setSpecifiedCatalog(null);
		assertNull(attributeResource.getAnnotation(JPA.TABLE_GENERATOR));
	}

	public void testGetSchema() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getTableGenerator().getSchema());
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setSchema("mySchema");
		
		assertEquals("mySchema", idMapping.getTableGenerator().getSchema());
		assertEquals("mySchema", idMapping.getTableGenerator().getSpecifiedSchema());
	}
	
	public void testGetDefaultSchema() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();

		assertNull(idMapping.getTableGenerator().getDefaultSchema());
		
		idMapping.getTableGenerator().setSpecifiedSchema("mySchema");
		
		assertNull(idMapping.getTableGenerator().getDefaultSchema());
		assertEquals("mySchema", idMapping.getTableGenerator().getSpecifiedSchema());
	}
	
	public void testUpdateDefaultSchemaFromPersistenceUnitDefaults() throws Exception {
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);

		createTestEntityWithTableGenerator();
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		IdMapping idMapping = (IdMapping)  ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getTableGenerator().getDefaultSchema());
		
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedSchema("FOO");
		assertEquals("FOO", idMapping.getTableGenerator().getDefaultSchema());
		
		entityMappings().setSpecifiedSchema("BAR");
		assertEquals("BAR", idMapping.getTableGenerator().getDefaultSchema());
		
		ormEntity.getTable().setSpecifiedSchema("XML_SCHEMA");
		assertEquals("BAR", idMapping.getTableGenerator().getDefaultSchema());

		entityMappings().removeOrmPersistentType(0);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		//default schema taken from persistence-unit-defaults not entity-mappings since the entity is not in an orm.xml file
		assertEquals("FOO", ((IdMapping) javaPersistentType().getAttributeNamed("id").getMapping()).getTableGenerator().getDefaultSchema());
	}

	public void testSetSpecifiedSchema() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getTableGenerator().setSpecifiedSchema("mySchema");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("mySchema", tableGenerator.getSchema());
		
		idMapping.getTableGenerator().setName(null);
		idMapping.getTableGenerator().setSpecifiedSchema(null);
		assertNull(attributeResource.getAnnotation(JPA.TABLE_GENERATOR));
	}
	
	public void testGetPkColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getTableGenerator().getPkColumnName());
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setPkColumnName("myPkColumnName");
		
		assertEquals("myPkColumnName", idMapping.getTableGenerator().getPkColumnName());
		assertEquals("myPkColumnName", idMapping.getTableGenerator().getSpecifiedPkColumnName());
	}
	
	public void testGetDefaultPkColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();

		assertNull(idMapping.getTableGenerator().getDefaultPkColumnName());
		
		idMapping.getTableGenerator().setSpecifiedPkColumnName("myPkColumnName");
		
		assertNull(idMapping.getTableGenerator().getDefaultPkColumnName());
		assertEquals("myPkColumnName", idMapping.getTableGenerator().getSpecifiedPkColumnName());
	}
	
	public void testSetSpecifiedPkColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getTableGenerator().setSpecifiedPkColumnName("myPkColumnName");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("myPkColumnName", tableGenerator.getPkColumnName());
		
		idMapping.getTableGenerator().setName(null);
		idMapping.getTableGenerator().setSpecifiedPkColumnName(null);
		assertNull(attributeResource.getAnnotation(JPA.TABLE_GENERATOR));
	}	
	
	public void testGetValueColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getTableGenerator().getValueColumnName());
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setValueColumnName("myValueColumnName");
		
		assertEquals("myValueColumnName", idMapping.getTableGenerator().getValueColumnName());
		assertEquals("myValueColumnName", idMapping.getTableGenerator().getSpecifiedValueColumnName());
	}
	
	public void testGetDefaultValueColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();

		assertNull(idMapping.getTableGenerator().getDefaultValueColumnName());
		
		idMapping.getTableGenerator().setSpecifiedValueColumnName("myValueColumnName");
		
		assertNull(idMapping.getTableGenerator().getDefaultValueColumnName());
		assertEquals("myValueColumnName", idMapping.getTableGenerator().getSpecifiedValueColumnName());
	}
	
	public void testSetSpecifiedValueColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getTableGenerator().setSpecifiedValueColumnName("myValueColumnName");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("myValueColumnName", tableGenerator.getValueColumnName());
		
		idMapping.getTableGenerator().setName(null);
		idMapping.getTableGenerator().setSpecifiedValueColumnName(null);
		assertNull(attributeResource.getAnnotation(JPA.TABLE_GENERATOR));
	}
	
	public void testGetPkColumnValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getTableGenerator().getPkColumnValue());
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setPkColumnValue("myPkColumnValue");
		
		assertEquals("myPkColumnValue", idMapping.getTableGenerator().getPkColumnValue());
		assertEquals("myPkColumnValue", idMapping.getTableGenerator().getSpecifiedPkColumnValue());
	}
	
	public void testGetDefaultPkColumnValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();

		assertNull(idMapping.getTableGenerator().getDefaultPkColumnValue());
		
		idMapping.getTableGenerator().setSpecifiedPkColumnValue("myPkColumnValue");
		
		assertNull(idMapping.getTableGenerator().getDefaultPkColumnValue());
		assertEquals("myPkColumnValue", idMapping.getTableGenerator().getSpecifiedPkColumnValue());
	}
	
	public void testSetSpecifiedPkColumnValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getTableGenerator().setSpecifiedPkColumnValue("myPkColumnValue");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("myPkColumnValue", tableGenerator.getPkColumnValue());
		
		idMapping.getTableGenerator().setName(null);
		idMapping.getTableGenerator().setSpecifiedPkColumnValue(null);
		assertNull(attributeResource.getAnnotation(JPA.TABLE_GENERATOR));
	}
	
	public void testGetInitialValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		
		assertEquals(TableGenerator.DEFAULT_INITIAL_VALUE, idMapping.getTableGenerator().getInitialValue());
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setInitialValue(Integer.valueOf(82));
		
		assertEquals(Integer.valueOf(82), idMapping.getTableGenerator().getInitialValue());
		assertEquals(Integer.valueOf(82), idMapping.getTableGenerator().getSpecifiedInitialValue());
	}
	
	public void testGetDefaultInitialValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();

		assertEquals(TableGenerator.DEFAULT_INITIAL_VALUE, idMapping.getTableGenerator().getDefaultInitialValue());
		
		idMapping.getTableGenerator().setSpecifiedInitialValue(Integer.valueOf(82));
		
		assertEquals(TableGenerator.DEFAULT_INITIAL_VALUE, idMapping.getTableGenerator().getDefaultInitialValue());
		assertEquals(Integer.valueOf(82), idMapping.getTableGenerator().getSpecifiedInitialValue());
	}
	
	public void testSetSpecifiedInitialValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getTableGenerator().setSpecifiedInitialValue(Integer.valueOf(20));
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals(Integer.valueOf(20), tableGenerator.getInitialValue());
		
		idMapping.getTableGenerator().setName(null);
		idMapping.getTableGenerator().setSpecifiedInitialValue(null);
		assertNull(attributeResource.getAnnotation(JPA.TABLE_GENERATOR));
	}
	
	public void testGetAllocationSize() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		
		assertEquals(Generator.DEFAULT_ALLOCATION_SIZE, idMapping.getTableGenerator().getAllocationSize());

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setAllocationSize(Integer.valueOf(20));
		
		assertEquals(Integer.valueOf(20), idMapping.getTableGenerator().getAllocationSize());
		assertEquals(Integer.valueOf(20), idMapping.getTableGenerator().getSpecifiedAllocationSize());
	}
	
	public void testGetDefaultAllocationSize() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();

		assertEquals(Generator.DEFAULT_ALLOCATION_SIZE, idMapping.getTableGenerator().getDefaultAllocationSize());
		
		idMapping.getTableGenerator().setSpecifiedAllocationSize(Integer.valueOf(20));
		
		assertEquals(Generator.DEFAULT_ALLOCATION_SIZE, idMapping.getTableGenerator().getDefaultAllocationSize());
		assertEquals(Integer.valueOf(20), idMapping.getTableGenerator().getSpecifiedAllocationSize());
	}
	
	public void testSetSpecifiedAllocationSize() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) javaPersistentType().getAttributeNamed("id").getMapping();
		idMapping.getTableGenerator().setSpecifiedAllocationSize(Integer.valueOf(25));
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals(Integer.valueOf(25), tableGenerator.getAllocationSize());
		
		idMapping.getTableGenerator().setName(null);
		idMapping.getTableGenerator().setSpecifiedAllocationSize(null);
		assertNull(attributeResource.getAnnotation(JPA.TABLE_GENERATOR));
	}
	

	public void testUniqueConstraints() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		TableGenerator tableGenerator = ((IdMapping) javaPersistentType().getAttributeNamed("id").getMapping()).getTableGenerator();

		ListIterator<JavaUniqueConstraint> uniqueConstraints = tableGenerator.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGeneratorAnnotation = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		tableGeneratorAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		tableGeneratorAnnotation.addUniqueConstraint(0).addColumnName(0, "bar");
		
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("bar", uniqueConstraints.next().columnNames().next());
		assertEquals("foo", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsSize() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		TableGenerator tableGenerator = ((IdMapping) javaPersistentType().getAttributeNamed("id").getMapping()).getTableGenerator();

		assertEquals(0,  tableGenerator.uniqueConstraintsSize());

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGeneratorAnnotation = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
		tableGeneratorAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		tableGeneratorAnnotation.addUniqueConstraint(1).addColumnName(0, "bar");
		
		assertEquals(2,  tableGenerator.uniqueConstraintsSize());
	}

	public void testAddUniqueConstraint() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		TableGenerator tableGenerator = ((IdMapping) javaPersistentType().getAttributeNamed("id").getMapping()).getTableGenerator();
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "FOO");
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "BAR");
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
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
		
		TableGenerator tableGenerator = ((IdMapping) javaPersistentType().getAttributeNamed("id").getMapping()).getTableGenerator();
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "FOO");
		tableGenerator.addUniqueConstraint(1).addColumnName(0, "BAR");
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
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
		
		TableGenerator tableGenerator = ((IdMapping) javaPersistentType().getAttributeNamed("id").getMapping()).getTableGenerator();
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "FOO");
		tableGenerator.addUniqueConstraint(1).addColumnName(0, "BAR");
		tableGenerator.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
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
		
		TableGenerator tableGenerator = ((IdMapping) javaPersistentType().getAttributeNamed("id").getMapping()).getTableGenerator();
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "FOO");
		tableGenerator.addUniqueConstraint(1).addColumnName(0, "BAR");
		tableGenerator.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
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

		TableGenerator tableGenerator = ((IdMapping) javaPersistentType().getAttributeNamed("id").getMapping()).getTableGenerator();
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TableGeneratorAnnotation tableGeneratorAnnotation = (TableGeneratorAnnotation) attributeResource.getAnnotation(JPA.TABLE_GENERATOR);	
	
		tableGeneratorAnnotation.addUniqueConstraint(0).addColumnName("FOO");
		tableGeneratorAnnotation.addUniqueConstraint(1).addColumnName("BAR");
		tableGeneratorAnnotation.addUniqueConstraint(2).addColumnName("BAZ");

		
		ListIterator<UniqueConstraint> uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		tableGeneratorAnnotation.moveUniqueConstraint(2, 0);
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableGeneratorAnnotation.moveUniqueConstraint(0, 1);
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableGeneratorAnnotation.removeUniqueConstraint(1);
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableGeneratorAnnotation.removeUniqueConstraint(1);
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		tableGeneratorAnnotation.removeUniqueConstraint(0);
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
	}
}
