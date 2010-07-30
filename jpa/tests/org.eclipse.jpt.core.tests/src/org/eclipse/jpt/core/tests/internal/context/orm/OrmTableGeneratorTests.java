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
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.TableGenerator;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class OrmTableGeneratorTests extends ContextModelTestCase
{
	public OrmTableGeneratorTests(String name) {
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
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set name in the resource model, verify context model updated
		tableGeneratorResource.setName("FOO");
		assertEquals("FOO", tableGenerator.getName());
		assertEquals("FOO", tableGeneratorResource.getName());
	
		//set name to null in the resource model
		tableGeneratorResource.setName(null);
		assertNull(tableGenerator.getName());
		assertNull(tableGeneratorResource.getName());
	}
	
	public void testModifySpecifiedName() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set name in the context model, verify resource model modified
		tableGenerator.setName("FOO");
		assertEquals("FOO", tableGeneratorResource.getName());
		assertEquals("FOO", tableGenerator.getName());
		
		//set name to null in the context model
		tableGenerator.setName(null);
		assertNull(tableGeneratorResource.getName());
		assertNull(tableGenerator.getName());
	}

	public void testUpdateSpecifiedInitialValue() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set initial value in the resource model, verify context model updated
		tableGeneratorResource.setInitialValue(Integer.valueOf(10));
		assertEquals(Integer.valueOf(10), tableGenerator.getSpecifiedInitialValue());
		assertEquals(Integer.valueOf(10), tableGeneratorResource.getInitialValue());
	
		//set initial value to 1, which happens to be the default, in the resource model
		tableGeneratorResource.setInitialValue(Integer.valueOf(1));
		assertEquals(Integer.valueOf(1), tableGenerator.getSpecifiedInitialValue());
		assertEquals(Integer.valueOf(1), tableGeneratorResource.getInitialValue());
	
		//set initial value to null in the resource model
		tableGeneratorResource.setInitialValue(null);
		assertNull(tableGenerator.getSpecifiedInitialValue());
		assertNull(tableGeneratorResource.getInitialValue());
	}
	
	public void testModifySpecifiedInitialValue() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set initial value in the context model, verify resource model modified
		tableGenerator.setSpecifiedInitialValue(Integer.valueOf(10));
		assertEquals(Integer.valueOf(10), tableGeneratorResource.getInitialValue());
		assertEquals(Integer.valueOf(10), tableGenerator.getSpecifiedInitialValue());
		
		tableGenerator.setSpecifiedInitialValue(Integer.valueOf(1));
		assertEquals(Integer.valueOf(1), tableGeneratorResource.getInitialValue());
		assertEquals(Integer.valueOf(1), tableGenerator.getSpecifiedInitialValue());

		//set initial value to null in the context model
		tableGenerator.setSpecifiedInitialValue(null);
		assertNull(tableGeneratorResource.getInitialValue());
		assertNull(tableGenerator.getSpecifiedInitialValue());
	}
	
	public void testUpdateSpecifiedAllocationSize() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set allocation size in the resource model, verify context model updated
		tableGeneratorResource.setAllocationSize(Integer.valueOf(10));
		assertEquals(Integer.valueOf(10), tableGenerator.getSpecifiedAllocationSize());
		assertEquals(Integer.valueOf(10), tableGeneratorResource.getAllocationSize());
	
		//set allocation size to 50, which happens to be the default, in the resource model
		tableGeneratorResource.setAllocationSize(Integer.valueOf(1));
		assertEquals(Integer.valueOf(1), tableGenerator.getSpecifiedAllocationSize());
		assertEquals(Integer.valueOf(1), tableGeneratorResource.getAllocationSize());
	
		//set allocation size to null in the resource model
		tableGeneratorResource.setAllocationSize(null);
		assertNull(tableGenerator.getSpecifiedAllocationSize());
		assertNull(tableGeneratorResource.getAllocationSize());
	}
	
	public void testModifySpecifiedAllocationSize() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set allocation size in the context model, verify resource model modified
		tableGenerator.setSpecifiedAllocationSize(Integer.valueOf(10));
		assertEquals(Integer.valueOf(10), tableGeneratorResource.getAllocationSize());
		assertEquals(Integer.valueOf(10), tableGenerator.getSpecifiedAllocationSize());
		
		tableGenerator.setSpecifiedAllocationSize(Integer.valueOf(50));
		assertEquals(Integer.valueOf(50), tableGeneratorResource.getAllocationSize());
		assertEquals(Integer.valueOf(50), tableGenerator.getSpecifiedAllocationSize());

		//set allocation size to null in the context model
		tableGenerator.setSpecifiedAllocationSize(null);
		assertNull(tableGeneratorResource.getAllocationSize());
		assertNull(tableGenerator.getSpecifiedAllocationSize());
	}
	
	public void testUpdateSpecifiedTable() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set name in the resource model, verify context model updated
		tableGeneratorResource.setTable("FOO");
		assertEquals("FOO", tableGenerator.getSpecifiedTable());
		assertEquals("FOO", tableGeneratorResource.getTable());
	
		//set name to null in the resource model
		tableGeneratorResource.setTable(null);
		assertNull(tableGenerator.getSpecifiedTable());
		assertNull(tableGeneratorResource.getTable());
	}
	
	public void testModifySpecifiedTable() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set name in the context model, verify resource model modified
		tableGenerator.setSpecifiedTable("FOO");
		assertEquals("FOO", tableGeneratorResource.getTable());
		assertEquals("FOO", tableGenerator.getSpecifiedTable());
		
		//set name to null in the context model
		tableGenerator.setSpecifiedTable(null);
		assertNull(tableGeneratorResource.getTable());
		assertNull(tableGenerator.getSpecifiedTable());
	}

	public void testUpdateSpecifiedSchema() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set name in the resource model, verify context model updated
		tableGeneratorResource.setSchema("FOO");
		assertEquals("FOO", tableGenerator.getSpecifiedSchema());
		assertEquals("FOO", tableGeneratorResource.getSchema());
	
		//set name to null in the resource model
		tableGeneratorResource.setSchema(null);
		assertNull(tableGenerator.getSpecifiedSchema());
		assertNull(tableGeneratorResource.getSchema());
	}
	
	public void testModifySpecifiedSchema() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set name in the context model, verify resource model modified
		tableGenerator.setSpecifiedSchema("FOO");
		assertEquals("FOO", tableGeneratorResource.getSchema());
		assertEquals("FOO", tableGenerator.getSpecifiedSchema());
		
		//set name to null in the context model
		tableGenerator.setSpecifiedSchema(null);
		assertNull(tableGeneratorResource.getSchema());
		assertNull(tableGenerator.getSpecifiedSchema());
	}
	
	public void testUpdateDefaultSchemaFromPersistenceUnitDefaults() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		
		assertNull(tableGenerator.getDefaultSchema());
		
		getEntityMappings().getPersistenceUnitDefaults().setSpecifiedSchema("FOO");
		assertEquals("FOO", tableGenerator.getDefaultSchema());
		
		getEntityMappings().setSpecifiedSchema("BAR");
		assertEquals("BAR", tableGenerator.getDefaultSchema());
	}

	public void testUpdateSpecifiedCatalog() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set name in the resource model, verify context model updated
		tableGeneratorResource.setCatalog("FOO");
		assertEquals("FOO", tableGenerator.getSpecifiedCatalog());
		assertEquals("FOO", tableGeneratorResource.getCatalog());
	
		//set name to null in the resource model
		tableGeneratorResource.setCatalog(null);
		assertNull(tableGenerator.getSpecifiedCatalog());
		assertNull(tableGeneratorResource.getCatalog());
	}
	
	public void testModifySpecifiedCatalog() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set name in the context model, verify resource model modified
		tableGenerator.setSpecifiedCatalog("FOO");
		assertEquals("FOO", tableGeneratorResource.getCatalog());
		assertEquals("FOO", tableGenerator.getSpecifiedCatalog());
		
		//set name to null in the context model
		tableGenerator.setSpecifiedCatalog(null);
		assertNull(tableGeneratorResource.getCatalog());
		assertNull(tableGenerator.getSpecifiedCatalog());
	}
	
	public void testUpdateSpecifiedPkColumnName() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set name in the resource model, verify context model updated
		tableGeneratorResource.setPkColumnName("FOO");
		assertEquals("FOO", tableGenerator.getSpecifiedPkColumnName());
		assertEquals("FOO", tableGeneratorResource.getPkColumnName());
	
		//set name to null in the resource model
		tableGeneratorResource.setPkColumnName(null);
		assertNull(tableGenerator.getSpecifiedPkColumnName());
		assertNull(tableGeneratorResource.getPkColumnName());
	}
	
	public void testModifySpecifiedPkColumnName() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set name in the context model, verify resource model modified
		tableGenerator.setSpecifiedPkColumnName("FOO");
		assertEquals("FOO", tableGeneratorResource.getPkColumnName());
		assertEquals("FOO", tableGenerator.getSpecifiedPkColumnName());
		
		//set name to null in the context model
		tableGenerator.setSpecifiedPkColumnName(null);
		assertNull(tableGeneratorResource.getPkColumnName());
		assertNull(tableGenerator.getSpecifiedPkColumnName());
	}
	
	public void testUpdateSpecifiedValueColumnName() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set name in the resource model, verify context model updated
		tableGeneratorResource.setValueColumnName("FOO");
		assertEquals("FOO", tableGenerator.getSpecifiedValueColumnName());
		assertEquals("FOO", tableGeneratorResource.getValueColumnName());
	
		//set name to null in the resource model
		tableGeneratorResource.setValueColumnName(null);
		assertNull(tableGenerator.getSpecifiedValueColumnName());
		assertNull(tableGeneratorResource.getValueColumnName());
	}
	
	public void testModifySpecifiedValueColumnName() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set name in the context model, verify resource model modified
		tableGenerator.setSpecifiedValueColumnName("FOO");
		assertEquals("FOO", tableGeneratorResource.getValueColumnName());
		assertEquals("FOO", tableGenerator.getSpecifiedValueColumnName());
		
		//set name to null in the context model
		tableGenerator.setSpecifiedValueColumnName(null);
		assertNull(tableGeneratorResource.getValueColumnName());
		assertNull(tableGenerator.getSpecifiedValueColumnName());
	}
	
	public void testUpdateSpecifiedPkColumnValue() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set name in the resource model, verify context model updated
		tableGeneratorResource.setPkColumnValue("FOO");
		assertEquals("FOO", tableGenerator.getSpecifiedPkColumnValue());
		assertEquals("FOO", tableGeneratorResource.getPkColumnValue());
	
		//set name to null in the resource model
		tableGeneratorResource.setPkColumnValue(null);
		assertNull(tableGenerator.getSpecifiedPkColumnValue());
		assertNull(tableGeneratorResource.getPkColumnValue());
	}
	
	public void testModifySpecifiedPkColumnValue() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set name in the context model, verify resource model modified
		tableGenerator.setSpecifiedPkColumnValue("FOO");
		assertEquals("FOO", tableGeneratorResource.getPkColumnValue());
		assertEquals("FOO", tableGenerator.getSpecifiedPkColumnValue());
		
		//set name to null in the context model
		tableGenerator.setSpecifiedPkColumnValue(null);
		assertNull(tableGeneratorResource.getPkColumnValue());
		assertNull(tableGenerator.getSpecifiedPkColumnValue());
	}
	

	public void testUniqueConstraints() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		ListIterator<OrmUniqueConstraint> uniqueConstraints = tableGenerator.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
		
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		tableGeneratorResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "foo");
		
		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		tableGeneratorResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "bar");
		
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("bar", uniqueConstraints.next().columnNames().next());
		assertEquals("foo", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsSize() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		assertEquals(0,  tableGenerator.uniqueConstraintsSize());
		
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		tableGeneratorResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "foo");
		
		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		tableGeneratorResource.getUniqueConstraints().add(1, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "bar");
		
		assertEquals(2,  tableGenerator.uniqueConstraintsSize());
	}

	public void testAddUniqueConstraint() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "FOO");
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "BAR");
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "BAZ");
				
		ListIterator<XmlUniqueConstraint> uniqueConstraints = tableGeneratorResource.getUniqueConstraints().listIterator();
		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().get(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testAddUniqueConstraint2() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "FOO");
		tableGenerator.addUniqueConstraint(1).addColumnName(0, "BAR");
		tableGenerator.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		ListIterator<XmlUniqueConstraint> uniqueConstraints = tableGeneratorResource.getUniqueConstraints().listIterator();
		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().get(0));
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().get(0));
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);

		tableGenerator.addUniqueConstraint(0).addColumnName(0, "FOO");
		tableGenerator.addUniqueConstraint(1).addColumnName(0, "BAR");
		tableGenerator.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		assertEquals(3, tableGeneratorResource.getUniqueConstraints().size());

		tableGenerator.removeUniqueConstraint(1);
		
		ListIterator<XmlUniqueConstraint> uniqueConstraintResources = tableGeneratorResource.getUniqueConstraints().listIterator();
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));		
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertFalse(uniqueConstraintResources.hasNext());
		
		Iterator<UniqueConstraint> uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());		
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		
		tableGenerator.removeUniqueConstraint(1);
		uniqueConstraintResources = tableGeneratorResource.getUniqueConstraints().listIterator();
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));		
		assertFalse(uniqueConstraintResources.hasNext());

		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());		
		assertFalse(uniqueConstraints.hasNext());

		
		tableGenerator.removeUniqueConstraint(0);
		uniqueConstraintResources = tableGeneratorResource.getUniqueConstraints().listIterator();
		assertFalse(uniqueConstraintResources.hasNext());
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);

		tableGenerator.addUniqueConstraint(0).addColumnName(0, "FOO");
		tableGenerator.addUniqueConstraint(1).addColumnName(0, "BAR");
		tableGenerator.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		assertEquals(3, tableGeneratorResource.getUniqueConstraints().size());
		
		
		tableGenerator.moveUniqueConstraint(2, 0);
		ListIterator<UniqueConstraint> uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());

		ListIterator<XmlUniqueConstraint> uniqueConstraintResources = tableGeneratorResource.getUniqueConstraints().listIterator();
		assertEquals("BAR", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));


		tableGenerator.moveUniqueConstraint(0, 1);
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());

		uniqueConstraintResources = tableGeneratorResource.getUniqueConstraints().listIterator();
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("BAR", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));
	}
	
	public void testUpdateUniqueConstraints() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
	
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		tableGeneratorResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "FOO");

		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		tableGeneratorResource.getUniqueConstraints().add(1, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "BAR");

		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		tableGeneratorResource.getUniqueConstraints().add(2, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "BAZ");

		
		ListIterator<UniqueConstraint> uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		tableGeneratorResource.getUniqueConstraints().move(2, 0);
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableGeneratorResource.getUniqueConstraints().move(0, 1);
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableGeneratorResource.getUniqueConstraints().remove(1);
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableGeneratorResource.getUniqueConstraints().remove(1);
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		tableGeneratorResource.getUniqueConstraints().remove(0);
		uniqueConstraints = tableGenerator.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsFromJava() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);

		OrmIdMapping ormIdMapping = (OrmIdMapping) ormPersistentType.attributes().next().getMapping();

		JavaIdMapping javaIdMapping = (JavaIdMapping) ormPersistentType.getJavaPersistentType().attributes().next().getMapping();
		JavaTableGenerator javaTableGenerator = javaIdMapping.getGeneratorContainer().addTableGenerator();
		javaTableGenerator.setName("TABLE_GENERATOR");
		
		OrmTableGenerator ormTableGenerator = ormIdMapping.getGeneratorContainer().getTableGenerator();
		assertTrue(ormTableGenerator.isVirtual());
		ListIterator<OrmUniqueConstraint> uniqueConstraints = ormTableGenerator.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());

		
		javaTableGenerator.addUniqueConstraint(0).addColumnName(0, "FOO");
		javaTableGenerator.addUniqueConstraint(1).addColumnName(0, "BAR");
		javaTableGenerator.addUniqueConstraint(2).addColumnName(0, "BAZ");

		uniqueConstraints = ormTableGenerator.uniqueConstraints();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		ormIdMapping.getPersistentAttribute().makeSpecified();
		ormIdMapping = (OrmIdMapping) ormPersistentType.attributes().next().getMapping();
	
		OrmTableGenerator ormTableGenerator2 = ormIdMapping.getGeneratorContainer().addTableGenerator();
		ormTableGenerator2.setName("TABLE_GENERATOR");
		
		assertFalse(ormTableGenerator2.isVirtual());
		assertEquals(0, ormTableGenerator2.uniqueConstraintsSize());
	}

}