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
import org.eclipse.jpt.core.internal.context.base.IClassRef;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IPersistenceUnit;
import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.Table;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModel;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaTableTests extends ContextModelTestCase
{
	private static final String ENTITY_NAME = "entityName";
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
			public void appendTypeAnnotationTo(StringBuffer sb) {
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
			public void appendTypeAnnotationTo(StringBuffer sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Table(name=\"" + TABLE_NAME + "\")");
			}
		});
	}

		
	public JavaTableTests(String name) {
		super(name);
	}
	
	protected XmlPersistenceUnit xmlPersistenceUnit() {
		PersistenceResourceModel prm = persistenceResourceModel();
		return prm.getPersistence().getPersistenceUnits().get(0);
	}
	
	protected IPersistenceUnit persistenceUnit() {
		return jpaContent().getPersistenceXml().getPersistence().persistenceUnits().next();
	}
	
	protected IClassRef classRef() {
		return persistenceUnit().classRefs().next();
	}
	
	protected IJavaPersistentType javaPersistentType() {
		return classRef().getJavaPersistentType();
	}
	
	protected IEntity javaEntity() {
		return (IEntity) javaPersistentType().getMapping();
	}
	
	protected void addXmlClassRef(String className) {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		
		XmlJavaClassRef xmlClassRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		xmlClassRef.setJavaClass(className);
		xmlPersistenceUnit.getClasses().add(xmlClassRef);
	}
	
	public void testGetSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(javaEntity().getSpecifiedName());
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
