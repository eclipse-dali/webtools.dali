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
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.TableAnnotation;
import org.eclipse.jpt.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaTableTests extends ContextModelTestCase
{
	private static final String TABLE_NAME = "MY_TABLE";

	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}

	private ICompilationUnit createTestEntityWithTable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.TABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Table(name=\"" + TABLE_NAME + "\")");
			}
		});
	}

	private ICompilationUnit createTestSubType() throws Exception {
		return this.createTestType(PACKAGE_NAME, "AnnotationTestTypeChild.java", "AnnotationTestTypeChild", new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendExtendsImplementsTo(StringBuilder sb) {
				sb.append("extends " + TYPE_NAME + " ");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}

		});
	}


		
	public JavaTableTests(String name) {
		super(name);
	}

	public void testGetSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(javaEntity().getTable().getSpecifiedName());
	}

	public void testGetSpecifiedName() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(TABLE_NAME, javaEntity().getTable().getSpecifiedName());
	}
	
	public void testGetDefaultNameSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(TYPE_NAME, javaEntity().getTable().getDefaultName());
	}

	public void testGetDefaultName() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(TYPE_NAME, javaEntity().getTable().getDefaultName());
		
		//test that setting the java entity name will change the table default name
		javaEntity().setSpecifiedName("foo");
		assertEquals("foo", javaEntity().getTable().getDefaultName());
	}
	
	public void testGetDefaultNameSingleTableInheritance() throws Exception {
		createTestEntity();
		createTestSubType();
		
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNotSame(javaEntity(), javaEntity().getRootEntity());
		assertEquals(TYPE_NAME, javaEntity().getTable().getDefaultName());
		assertEquals(TYPE_NAME, javaEntity().getRootEntity().getTable().getDefaultName());
		
		//test that setting the root java entity name will change the table default name of the child
		javaEntity().getRootEntity().setSpecifiedName("foo");
		assertEquals("foo", javaEntity().getTable().getDefaultName());
	}

	public void testUpdateDefaultSchemaFromPersistenceUnitDefaults() throws Exception {
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);

		createTestEntity();
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		JavaEntity javaEntity = ormEntity.getJavaEntity();
		
		assertNull(javaEntity.getTable().getDefaultSchema());
		
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedSchema("FOO");
		assertEquals("FOO", javaEntity.getTable().getDefaultSchema());
		
		entityMappings().setSpecifiedSchema("BAR");
		assertEquals("BAR", javaEntity.getTable().getDefaultSchema());
		
		ormEntity.getTable().setSpecifiedSchema("XML_SCHEMA");
		assertEquals("BAR", javaEntity.getTable().getDefaultSchema());

		entityMappings().removeOrmPersistentType(0);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		//default schema taken from persistence-unit-defaults not entity-mappings since the entity is not in an orm.xml file
		assertEquals("FOO", javaEntity().getTable().getDefaultSchema());

		IFile file = ormResource().getFile();
		//remove the mapping file reference from the persistence.xml.  default schema 
		//should still come from persistence-unit-defaults because of implied mapped-file-ref
		xmlPersistenceUnit().getMappingFiles().remove(mappingFileRef);
		assertEquals("FOO", javaEntity().getTable().getDefaultSchema());
	
		file.delete(true, null);
		assertNull(javaEntity().getTable().getDefaultSchema());
	}
	
	public void testGetNameSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(TYPE_NAME, javaEntity().getTable().getName());
	}
	
	public void testGetName() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(TABLE_NAME, javaEntity().getTable().getName());
	}

	public void testSetSpecifiedName() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		javaEntity().getTable().setSpecifiedName("foo");
		
		assertEquals("foo", javaEntity().getTable().getSpecifiedName());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		
		assertEquals("foo", table.getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		javaEntity().getTable().setSpecifiedName(null);
		
		assertNull(javaEntity().getTable().getSpecifiedName());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
	
		assertNull(table);
	}
	
	public void testUpdateFromSpecifiedNameChangeInResourceModel() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		table.setName("foo");
		
		assertEquals("foo", javaEntity().getTable().getSpecifiedName());
		
		typeResource.removeSupportingAnnotation(JPA.TABLE);
		assertNull(javaEntity().getTable().getSpecifiedName());
	}
	
	public void testGetCatalog() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		
		table.setCatalog("myCatalog");
		
		assertEquals("myCatalog", javaEntity().getTable().getSpecifiedCatalog());
		assertEquals("myCatalog", javaEntity().getTable().getCatalog());
	}
	
	public void testGetDefaultCatalog() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(javaEntity().getTable().getDefaultCatalog());
		
		javaEntity().getTable().setSpecifiedCatalog("myCatalog");
		
		assertNull(javaEntity().getTable().getDefaultCatalog());
	}
	
	public void testUpdateDefaultCatalogFromPersistenceUnitDefaults() throws Exception {
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);

		createTestEntity();
		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		JavaEntity javaEntity = ormEntity.getJavaEntity();
		
		assertNull(javaEntity.getTable().getDefaultCatalog());
		
		entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSpecifiedCatalog("FOO");
		assertEquals("FOO", javaEntity.getTable().getDefaultCatalog());
		
		entityMappings().setSpecifiedCatalog("BAR");
		assertEquals("BAR", javaEntity.getTable().getDefaultCatalog());
		
		ormEntity.getTable().setSpecifiedCatalog("XML_CATALOG");
		assertEquals("BAR", javaEntity.getTable().getDefaultCatalog());

		entityMappings().removeOrmPersistentType(0);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		//default catalog taken from persistence-unite-defaults not entity-mappings since the entity is not in an orm.xml file
		assertEquals("FOO", javaEntity().getTable().getDefaultCatalog());

		IFile file = ormResource().getFile();
		//remove the mapping file reference from the persistence.xml.  default schema 
		//should still come from persistence-unit-defaults because of implied mapped-file-ref
		xmlPersistenceUnit().getMappingFiles().remove(mappingFileRef);
		assertEquals("FOO", javaEntity().getTable().getDefaultCatalog());
	
		file.delete(true, null);
		assertNull(javaEntity().getTable().getDefaultCatalog());
	}

	public void testSetSpecifiedCatalog() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Table table = javaEntity().getTable();
		table.setSpecifiedCatalog("myCatalog");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		TableAnnotation tableResource = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		
		assertEquals("myCatalog", tableResource.getCatalog());
		
		table.setSpecifiedCatalog(null);
		assertNull(typeResource.getSupportingAnnotation(JPA.TABLE));
	}
	
	public void testGetSchema() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		TableAnnotation table = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		
		table.setSchema("mySchema");
		
		assertEquals("mySchema", javaEntity().getTable().getSpecifiedSchema());
		assertEquals("mySchema", javaEntity().getTable().getSchema());
	}
	
	public void testGetDefaultSchema() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(javaEntity().getTable().getDefaultSchema());
		
		javaEntity().getTable().setSpecifiedSchema("mySchema");
		
		assertNull(javaEntity().getTable().getDefaultSchema());
	}
	
	public void testSetSpecifiedSchema() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		Table table = javaEntity().getTable();
		table.setSpecifiedSchema("mySchema");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		TableAnnotation tableResource = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		
		assertEquals("mySchema", tableResource.getSchema());
		
		table.setSpecifiedSchema(null);
		assertNull(typeResource.getSupportingAnnotation(JPA.TABLE));
	}

	public void testUniqueConstraints() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		ListIterator<JavaUniqueConstraint> uniqueConstraints = javaEntity().getTable().uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		TableAnnotation tableAnnotation = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		tableAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		tableAnnotation.addUniqueConstraint(0).addColumnName(0, "bar");
		
		uniqueConstraints = javaEntity().getTable().uniqueConstraints();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("bar", uniqueConstraints.next().columnNames().next());
		assertEquals("foo", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsSize() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(0,  javaEntity().getTable().uniqueConstraintsSize());

		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		TableAnnotation tableAnnotation = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		tableAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		tableAnnotation.addUniqueConstraint(1).addColumnName(0, "bar");
		
		assertEquals(2,  javaEntity().getTable().uniqueConstraintsSize());
	}

	public void testAddUniqueConstraint() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Table table = javaEntity().getTable();
		table.addUniqueConstraint(0).addColumnName(0, "FOO");
		table.addUniqueConstraint(0).addColumnName(0, "BAR");
		table.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		TableAnnotation tableAnnotation = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		ListIterator<UniqueConstraintAnnotation> uniqueConstraints = tableAnnotation.uniqueConstraints();
		
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testAddUniqueConstraint2() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Table table = javaEntity().getTable();
		table.addUniqueConstraint(0).addColumnName(0, "FOO");
		table.addUniqueConstraint(1).addColumnName(0, "BAR");
		table.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		TableAnnotation tableAnnotation = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		ListIterator<UniqueConstraintAnnotation> uniqueConstraints = tableAnnotation.uniqueConstraints();
		
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Table table = javaEntity().getTable();
		table.addUniqueConstraint(0).addColumnName(0, "FOO");
		table.addUniqueConstraint(1).addColumnName(0, "BAR");
		table.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		TableAnnotation tableAnnotation = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		
		assertEquals(3, tableAnnotation.uniqueConstraintsSize());

		table.removeUniqueConstraint(1);
		
		ListIterator<UniqueConstraintAnnotation> uniqueConstraintAnnotations = tableAnnotation.uniqueConstraints();
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNames().next());		
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNames().next());
		assertFalse(uniqueConstraintAnnotations.hasNext());
		
		Iterator<UniqueConstraint> uniqueConstraints = table.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());		
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		
		table.removeUniqueConstraint(1);
		uniqueConstraintAnnotations = tableAnnotation.uniqueConstraints();
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNames().next());		
		assertFalse(uniqueConstraintAnnotations.hasNext());

		uniqueConstraints = table.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());		
		assertFalse(uniqueConstraints.hasNext());

		
		table.removeUniqueConstraint(0);
		uniqueConstraintAnnotations = tableAnnotation.uniqueConstraints();
		assertFalse(uniqueConstraintAnnotations.hasNext());
		uniqueConstraints = table.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Table table = javaEntity().getTable();
		table.addUniqueConstraint(0).addColumnName(0, "FOO");
		table.addUniqueConstraint(1).addColumnName(0, "BAR");
		table.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		TableAnnotation tableAnnotation = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
		
		assertEquals(3, tableAnnotation.uniqueConstraintsSize());
		
		
		table.moveUniqueConstraint(2, 0);
		ListIterator<UniqueConstraint> uniqueConstraints = table.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());

		ListIterator<UniqueConstraintAnnotation> uniqueConstraintAnnotations = tableAnnotation.uniqueConstraints();
		assertEquals("BAR", uniqueConstraintAnnotations.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNames().next());
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNames().next());


		table.moveUniqueConstraint(0, 1);
		uniqueConstraints = table.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());

		uniqueConstraintAnnotations = tableAnnotation.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNames().next());
		assertEquals("BAR", uniqueConstraintAnnotations.next().columnNames().next());
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNames().next());
	}
	
	public void testUpdateUniqueConstraints() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		Table table = javaEntity().getTable();
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		TableAnnotation tableAnnotation = (TableAnnotation) typeResource.getSupportingAnnotation(JPA.TABLE);
	
		tableAnnotation.addUniqueConstraint(0).addColumnName("FOO");
		tableAnnotation.addUniqueConstraint(1).addColumnName("BAR");
		tableAnnotation.addUniqueConstraint(2).addColumnName("BAZ");

		
		ListIterator<UniqueConstraint> uniqueConstraints = table.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		tableAnnotation.moveUniqueConstraint(2, 0);
		uniqueConstraints = table.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableAnnotation.moveUniqueConstraint(0, 1);
		uniqueConstraints = table.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableAnnotation.removeUniqueConstraint(1);
		uniqueConstraints = table.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		tableAnnotation.removeUniqueConstraint(1);
		uniqueConstraints = table.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		tableAnnotation.removeUniqueConstraint(0);
		uniqueConstraints = table.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
	}
}
