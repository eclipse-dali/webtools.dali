/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.TableGenerator;
import org.eclipse.jpt.jpa.core.context.SpecifiedUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedUniqueConstraint;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

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
		mappingFileRef.setFileName(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID);
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
	
	public void testUpdateSpecifiedTableName() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set name in the resource model, verify context model updated
		tableGeneratorResource.setTable("FOO");
		assertEquals("FOO", tableGenerator.getSpecifiedTableName());
		assertEquals("FOO", tableGeneratorResource.getTable());
	
		//set name to null in the resource model
		tableGeneratorResource.setTable(null);
		assertNull(tableGenerator.getSpecifiedTableName());
		assertNull(tableGeneratorResource.getTable());
	}
	
	public void testModifySpecifiedTableName() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		//set name in the context model, verify resource model modified
		tableGenerator.setSpecifiedTableName("FOO");
		assertEquals("FOO", tableGeneratorResource.getTable());
		assertEquals("FOO", tableGenerator.getSpecifiedTableName());
		
		//set name to null in the context model
		tableGenerator.setSpecifiedTableName(null);
		assertNull(tableGeneratorResource.getTable());
		assertNull(tableGenerator.getSpecifiedTableName());
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
		
		getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedSchema("FOO");
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
		OrmTableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		Iterator<OrmSpecifiedUniqueConstraint> uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());
		
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		tableGeneratorResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "foo");
		
		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		tableGeneratorResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "bar");
		
		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("bar", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("foo", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsSize() throws Exception {
		TableGenerator tableGenerator = getEntityMappings().addTableGenerator(0);		
		XmlTableGenerator tableGeneratorResource = getXmlEntityMappings().getTableGenerators().get(0);
		
		assertEquals(0,  tableGenerator.getUniqueConstraintsSize());
		
		XmlUniqueConstraint uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		tableGeneratorResource.getUniqueConstraints().add(0, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "foo");
		
		uniqueConstraintResource = OrmFactory.eINSTANCE.createXmlUniqueConstraint();
		tableGeneratorResource.getUniqueConstraints().add(1, uniqueConstraintResource);
		uniqueConstraintResource.getColumnNames().add(0, "bar");
		
		assertEquals(2,  tableGenerator.getUniqueConstraintsSize());
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
		
		Iterator<SpecifiedUniqueConstraint> uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());		
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		
		tableGenerator.removeUniqueConstraint(1);
		uniqueConstraintResources = tableGeneratorResource.getUniqueConstraints().listIterator();
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));		
		assertFalse(uniqueConstraintResources.hasNext());

		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());		
		assertFalse(uniqueConstraints.hasNext());

		
		tableGenerator.removeUniqueConstraint(0);
		uniqueConstraintResources = tableGeneratorResource.getUniqueConstraints().listIterator();
		assertFalse(uniqueConstraintResources.hasNext());
		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
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
		Iterator<SpecifiedUniqueConstraint> uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());

		ListIterator<XmlUniqueConstraint> uniqueConstraintResources = tableGeneratorResource.getUniqueConstraints().listIterator();
		assertEquals("BAR", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("BAZ", uniqueConstraintResources.next().getColumnNames().get(0));
		assertEquals("FOO", uniqueConstraintResources.next().getColumnNames().get(0));


		tableGenerator.moveUniqueConstraint(0, 1);
		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());

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

		
		Iterator<SpecifiedUniqueConstraint> uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		tableGeneratorResource.getUniqueConstraints().move(2, 0);
		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableGeneratorResource.getUniqueConstraints().move(0, 1);
		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableGeneratorResource.getUniqueConstraints().remove(1);
		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableGeneratorResource.getUniqueConstraints().remove(1);
		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
		assertFalse(uniqueConstraints.hasNext());
		
		tableGeneratorResource.getUniqueConstraints().remove(0);
		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsFromJava() throws Exception {
		createTestEntity();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);

		OrmPersistentAttribute virtualAttribute = ormPersistentType.getAttributes().iterator().next();
		IdMapping virtualIdMapping = (IdMapping) virtualAttribute.getMapping();

		JavaIdMapping javaIdMapping = (JavaIdMapping) ormPersistentType.getJavaPersistentType().getAttributes().iterator().next().getMapping();
		JavaTableGenerator javaTableGenerator = javaIdMapping.getGeneratorContainer().addTableGenerator();
		javaTableGenerator.setName("TABLE_GENERATOR");
		
// >>> I'm going to argue there is no such thing as a "virtual" generator,
// since a Java generator is *not* overridden when its ID mapping (or entity)
// is overridden in the orm.xml file - both the orm.xml ID mapping and the
// Java mapping can define an "active" generator as long as they have different
// names  ~bjv
		TableGenerator tableGenerator = virtualIdMapping.getGeneratorContainer().getTableGenerator();
		assertNull(tableGenerator);
//		Iterator<UniqueConstraint> uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
//		assertFalse(uniqueConstraints.hasNext());
		
		javaTableGenerator.addUniqueConstraint().addColumnName("FOO");
		javaTableGenerator.addUniqueConstraint().addColumnName("BAR");
		javaTableGenerator.addUniqueConstraint().addColumnName("BAZ");

		assertNull(tableGenerator);
//		uniqueConstraints = tableGenerator.getUniqueConstraints().iterator();
//		assertTrue(uniqueConstraints.hasNext());
//		assertEquals("FOO", uniqueConstraints.next().getColumnNames().iterator().next());
//		assertEquals("BAR", uniqueConstraints.next().getColumnNames().iterator().next());
//		assertEquals("BAZ", uniqueConstraints.next().getColumnNames().iterator().next());
//		assertFalse(uniqueConstraints.hasNext());
		
		OrmIdMapping ormIdMapping = (OrmIdMapping) virtualAttribute.addToXml().getMapping();
	
		OrmTableGenerator ormTableGenerator2 = ormIdMapping.getGeneratorContainer().addTableGenerator();
		ormTableGenerator2.setName("TABLE_GENERATOR");
		
		assertEquals(0, ormTableGenerator2.getUniqueConstraintsSize());
	}
}
