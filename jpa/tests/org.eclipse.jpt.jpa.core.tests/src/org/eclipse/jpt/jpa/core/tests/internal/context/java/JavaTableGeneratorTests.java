/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.TableGenerator;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

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
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);
		
		assertEquals("foo", tableGenerator.getName());
	}
	
	public void testGetCatalog() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getCatalog());
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation generatorAnnotation = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("myCatalog", generatorAnnotation.getCatalog());
	}

	public void testGetSchema() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getSchema());
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		
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
		mappingFileRef.setFileName(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);

		createTestEntityWithTableGenerator();
		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		IdMapping idMapping = (IdMapping)  ormPersistentType.getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getDefaultSchema());
		
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedSchema("FOO");
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation generatorAnnotation = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("mySchema", generatorAnnotation.getSchema());
	}
	
	public void testGetPkColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getPkColumnName());
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation generatorAnnotation = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("myPkColumnName", generatorAnnotation.getPkColumnName());
	}	
	
	public void testGetValueColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getValueColumnName());
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation generatorAnnotation = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("myValueColumnName", generatorAnnotation.getValueColumnName());
	}
	
	public void testGetPkColumnValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertNull(idMapping.getGeneratorContainer().getTableGenerator().getPkColumnValue());
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation generatorAnnotation = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("myPkColumnValue", generatorAnnotation.getPkColumnValue());
	}
	
	public void testGetInitialValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertEquals(TableGenerator.DEFAULT_INITIAL_VALUE, idMapping.getGeneratorContainer().getTableGenerator().getInitialValue());
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation generatorAnnotation = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals(Integer.valueOf(20), generatorAnnotation.getInitialValue());
	}
	
	public void testGetAllocationSize() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IdMapping idMapping = (IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping();
		
		assertEquals(Generator.DEFAULT_ALLOCATION_SIZE, idMapping.getGeneratorContainer().getTableGenerator().getAllocationSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation tableGenerator = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation generatorAnnotation = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals(Integer.valueOf(25), generatorAnnotation.getAllocationSize());
	}
	

	public void testUniqueConstraints() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaTableGenerator tableGenerator =(JavaTableGenerator) ((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getGeneratorContainer().getTableGenerator();

		Iterator<JavaUniqueConstraint> uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation tableGeneratorAnnotation = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		tableGeneratorAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		tableGeneratorAnnotation.addUniqueConstraint(0).addColumnName(0, "bar");
		getJpaProject().synchronizeContextModel();
		
		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("bar", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("foo", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsSize() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		TableGenerator tableGenerator = ((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getGeneratorContainer().getTableGenerator();

		assertEquals(0,  tableGenerator.getUniqueConstraintsSize());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation tableGeneratorAnnotation = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		tableGeneratorAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		tableGeneratorAnnotation.addUniqueConstraint(1).addColumnName(0, "bar");
		
		getJpaProject().synchronizeContextModel();
		assertEquals(2,  tableGenerator.getUniqueConstraintsSize());
	}

	public void testAddUniqueConstraint() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		TableGenerator tableGenerator = ((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getGeneratorContainer().getTableGenerator();
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "FOO");
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "BAR");
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation tableGeneratorAnnotation = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		ListIterator<UniqueConstraintAnnotation> uniqueConstraints = tableGeneratorAnnotation.getUniqueConstraints().iterator();
		
		assertEquals("BAZ", uniqueConstraints.next().columnNameAt(0));
		assertEquals("BAR", uniqueConstraints.next().columnNameAt(0));
		assertEquals("FOO", uniqueConstraints.next().columnNameAt(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testAddUniqueConstraint2() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		TableGenerator tableGenerator = ((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getGeneratorContainer().getTableGenerator();
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "FOO");
		tableGenerator.addUniqueConstraint(1).addColumnName(0, "BAR");
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation tableGeneratorAnnotation = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		ListIterator<UniqueConstraintAnnotation> uniqueConstraints = tableGeneratorAnnotation.getUniqueConstraints().iterator();
		
		assertEquals("BAZ", uniqueConstraints.next().columnNameAt(0));
		assertEquals("FOO", uniqueConstraints.next().columnNameAt(0));
		assertEquals("BAR", uniqueConstraints.next().columnNameAt(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		TableGenerator tableGenerator = ((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getGeneratorContainer().getTableGenerator();
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "FOO");
		tableGenerator.addUniqueConstraint(1).addColumnName(0, "BAR");
		tableGenerator.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation tableGeneratorAnnotation = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals(3, tableGeneratorAnnotation.getUniqueConstraintsSize());

		tableGenerator.removeUniqueConstraint(1);
		
		ListIterator<UniqueConstraintAnnotation> uniqueConstraintAnnotations = tableGeneratorAnnotation.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNameAt(0));		
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertFalse(uniqueConstraintAnnotations.hasNext());
		
		Iterator<UniqueConstraint> uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		
		tableGenerator.removeUniqueConstraint(1);
		uniqueConstraintAnnotations = tableGeneratorAnnotation.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNameAt(0));		
		assertFalse(uniqueConstraintAnnotations.hasNext());

		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());		
		assertFalse(uniqueConstraints.hasNext());

		
		tableGenerator.removeUniqueConstraint(0);
		uniqueConstraintAnnotations = tableGeneratorAnnotation.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraintAnnotations.hasNext());
		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		TableGenerator tableGenerator = ((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getGeneratorContainer().getTableGenerator();
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "FOO");
		tableGenerator.addUniqueConstraint(1).addColumnName(0, "BAR");
		tableGenerator.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation tableGeneratorAnnotation = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
		
		assertEquals(3, tableGeneratorAnnotation.getUniqueConstraintsSize());
		
		
		tableGenerator.moveUniqueConstraint(2, 0);
		Iterator<UniqueConstraint> uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());

		ListIterator<UniqueConstraintAnnotation> uniqueConstraintAnnotations = tableGeneratorAnnotation.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNameAt(0));


		tableGenerator.moveUniqueConstraint(0, 1);
		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());

		uniqueConstraintAnnotations = tableGeneratorAnnotation.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertEquals("BAR", uniqueConstraintAnnotations.next().columnNameAt(0));
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNameAt(0));
	}
	
	public void testUpdateUniqueConstraints() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		TableGenerator tableGenerator = ((IdMapping) getJavaPersistentType().getAttributeNamed("id").getMapping()).getGeneratorContainer().getTableGenerator();
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TableGeneratorAnnotation tableGeneratorAnnotation = (TableGeneratorAnnotation) resourceField.getAnnotation(JPA.TABLE_GENERATOR);	
	
		tableGeneratorAnnotation.addUniqueConstraint(0).addColumnName("FOO");
		tableGeneratorAnnotation.addUniqueConstraint(1).addColumnName("BAR");
		tableGeneratorAnnotation.addUniqueConstraint(2).addColumnName("BAZ");
		getJpaProject().synchronizeContextModel();
		
		Iterator<UniqueConstraint> uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		tableGeneratorAnnotation.moveUniqueConstraint(2, 0);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableGeneratorAnnotation.moveUniqueConstraint(0, 1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableGeneratorAnnotation.removeUniqueConstraint(1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableGeneratorAnnotation.removeUniqueConstraint(1);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		tableGeneratorAnnotation.removeUniqueConstraint(0);
		getJpaProject().synchronizeContextModel();
		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());
	}
}
