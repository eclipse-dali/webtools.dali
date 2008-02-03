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
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.context.java.IJavaEntity;
import org.eclipse.jpt.core.internal.context.orm.XmlEntity;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.Table;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaTableTests extends ContextModelTestCase
{
	private static final String TABLE_NAME = "MY_TABLE";
	
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	private void createTableAnnotation() throws Exception{
		this.createAnnotationAndMembers("Table", 
			"String name() default \"\"; " +
			"String catalog() default \"\"; " +
			"String schema() default \"\";");		
	}
		

	private IType createTestEntity() throws Exception {
		createEntityAnnotation();

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

	private IType createTestEntityWithTable() throws Exception {
		createEntityAnnotation();
		createTableAnnotation();
	
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

	private IType createTestSubType() throws Exception {
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
		
		assertNotSame(javaEntity(), javaEntity().rootEntity());
		assertEquals(TYPE_NAME, javaEntity().getTable().getDefaultName());
		assertEquals(TYPE_NAME, javaEntity().rootEntity().getTable().getDefaultName());
		
		//test that setting the root java entity name will change the table default name of the child
		javaEntity().rootEntity().setSpecifiedName("foo");
		assertEquals("foo", javaEntity().getTable().getDefaultName());
	}

	public void testUpdateDefaultSchemaFromPersistenceUnitDefaults() throws Exception {
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);

		createTestEntity();
		
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		IJavaEntity javaEntity = xmlEntity.javaEntity();
		
		assertNull(javaEntity.getTable().getDefaultSchema());
		
		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema("FOO");
		assertEquals("FOO", javaEntity.getTable().getDefaultSchema());
		
		xmlEntity.entityMappings().setSpecifiedSchema("BAR");
		assertEquals("BAR", javaEntity.getTable().getDefaultSchema());
		
		xmlEntity.getTable().setSpecifiedSchema("XML_SCHEMA");
		assertEquals("BAR", javaEntity.getTable().getDefaultSchema());

		entityMappings().removeXmlPersistentType(0);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		//default schema taken from persistence-unit-defaults not entity-mappings since the entity is not in an orm.xml file
		assertEquals("FOO", javaEntity().getTable().getDefaultSchema());
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
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		Table table = (Table) typeResource.annotation(JPA.TABLE);
		
		assertEquals("foo", table.getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		javaEntity().getTable().setSpecifiedName(null);
		
		assertNull(javaEntity().getTable().getSpecifiedName());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		Table table = (Table) typeResource.annotation(JPA.TABLE);
	
		assertNull(table);
	}
	
	public void testUpdateFromSpecifiedNameChangeInResourceModel() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		Table table = (Table) typeResource.annotation(JPA.TABLE);
		table.setName("foo");
		
		assertEquals("foo", javaEntity().getTable().getSpecifiedName());
		
		typeResource.removeAnnotation(JPA.TABLE);
		assertNull(javaEntity().getTable().getSpecifiedName());
	}
	
	public void testGetCatalog() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		Table table = (Table) typeResource.annotation(JPA.TABLE);
		
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
		
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		IJavaEntity javaEntity = xmlEntity.javaEntity();
		
		assertNull(javaEntity.getTable().getDefaultCatalog());
		
		xmlEntity.entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog("FOO");
		assertEquals("FOO", javaEntity.getTable().getDefaultCatalog());
		
		xmlEntity.entityMappings().setSpecifiedCatalog("BAR");
		assertEquals("BAR", javaEntity.getTable().getDefaultCatalog());
		
		xmlEntity.getTable().setSpecifiedCatalog("XML_CATALOG");
		assertEquals("BAR", javaEntity.getTable().getDefaultCatalog());

		entityMappings().removeXmlPersistentType(0);
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		//default catalog taken from persistence-unite-defaults not entity-mappings since the entity is not in an orm.xml file
		assertEquals("FOO", javaEntity().getTable().getDefaultCatalog());
	}

	public void testSetSpecifiedCatalog() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		ITable table = javaEntity().getTable();
		table.setSpecifiedCatalog("myCatalog");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		Table tableResource = (Table) typeResource.annotation(JPA.TABLE);
		
		assertEquals("myCatalog", tableResource.getCatalog());
		
		table.setSpecifiedCatalog(null);
		assertNull(typeResource.annotation(JPA.TABLE));
	}
	
	public void testGetSchema() throws Exception {
		createTestEntityWithTable();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		Table table = (Table) typeResource.annotation(JPA.TABLE);
		
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
		ITable table = javaEntity().getTable();
		table.setSpecifiedSchema("mySchema");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		Table tableResource = (Table) typeResource.annotation(JPA.TABLE);
		
		assertEquals("mySchema", tableResource.getSchema());
		
		table.setSpecifiedSchema(null);
		assertNull(typeResource.annotation(JPA.TABLE));
	}

}
