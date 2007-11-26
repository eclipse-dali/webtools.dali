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
import org.eclipse.jpt.core.internal.context.base.DiscriminatorType;
import org.eclipse.jpt.core.internal.context.base.IClassRef;
import org.eclipse.jpt.core.internal.context.base.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IPersistenceUnit;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorColumn;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaDiscriminatorColumnTests extends ContextModelTestCase
{
	private static final String DISCRIMINATOR_COLUMN_NAME = "MY_DISCRIMINATOR_COLUMN";
	private static final String COLUMN_DEFINITION = "MY_COLUMN_DEFINITION";
	
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	private void createDiscriminatorColumnAnnotation() throws Exception{
		this.createAnnotationAndMembers("DiscriminatorColumn", 
			"String name() default \"DTYPE\";" +
			"DiscriminatorType discriminatorType() default STRING;" +
			"String columnDefinition() default \"\";" +
			"int length() default 31;");		
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

	private IType createTestEntityWithDiscriminatorColumn() throws Exception {
		createEntityAnnotation();
		createDiscriminatorColumnAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@DiscriminatorColumn(name=\"" + DISCRIMINATOR_COLUMN_NAME + "\")");
			}
		});
	}

		
	public JavaDiscriminatorColumnTests(String name) {
		super(name);
	}
	
	protected XmlPersistenceUnit xmlPersistenceUnit() {
		PersistenceResource prm = persistenceResource();
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

		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedName());
	}

	public void testGetSpecifiedName() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(DISCRIMINATOR_COLUMN_NAME, javaEntity().getDiscriminatorColumn().getSpecifiedName());
	}
	
	public void testGetDefaultNameSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals("DTYPE", javaEntity().getDiscriminatorColumn().getDefaultName());
	}

	public void testGetDefaultName() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals("DTYPE", javaEntity().getDiscriminatorColumn().getDefaultName());
		
		javaEntity().getDiscriminatorColumn().setSpecifiedName("foo");
		assertEquals("DTYPE", javaEntity().getDiscriminatorColumn().getDefaultName());
	}
	
	public void testGetNameSpecifiedNameNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals("DTYPE", javaEntity().getDiscriminatorColumn().getName());
	}
	
	public void testGetName() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(DISCRIMINATOR_COLUMN_NAME, javaEntity().getDiscriminatorColumn().getName());
	}

	public void testSetSpecifiedName() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		javaEntity().getDiscriminatorColumn().setSpecifiedName("foo");
		
		assertEquals("foo", javaEntity().getDiscriminatorColumn().getSpecifiedName());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumn discriminatorColumn = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);
		
		assertEquals("foo", discriminatorColumn.getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		javaEntity().getDiscriminatorColumn().setSpecifiedName(null);
		
		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedName());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumn discriminatorColumn = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);
	
		assertNull(discriminatorColumn);
	}
	
	public void testGetDefaultDiscriminatorType() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(DiscriminatorType.STRING, javaEntity().getDiscriminatorColumn().getDefaultDiscriminatorType());
	}
	
	public void testGetDiscriminatorType() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertEquals(DiscriminatorType.STRING, javaEntity().getDiscriminatorColumn().getDiscriminatorType());
		
		javaEntity().getDiscriminatorColumn().setSpecifiedDiscriminatorType(DiscriminatorType.CHAR);
		assertEquals(DiscriminatorType.CHAR, javaEntity().getDiscriminatorColumn().getDiscriminatorType());
	}
	
	public void testGetSpecifiedDiscriminatorType() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
		
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumn discriminatorColumn = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);
		discriminatorColumn.setDiscriminatorType(org.eclipse.jpt.core.internal.resource.java.DiscriminatorType.CHAR);
		
		assertEquals(DiscriminatorType.CHAR, javaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
	}
	
	public void testSetSpecifiedDiscriminatorType() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
		
		javaEntity().getDiscriminatorColumn().setSpecifiedDiscriminatorType(DiscriminatorType.CHAR);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumn discriminatorColumn = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);
		
		assertEquals(org.eclipse.jpt.core.internal.resource.java.DiscriminatorType.CHAR, discriminatorColumn.getDiscriminatorType());
		
		javaEntity().getDiscriminatorColumn().setSpecifiedName(null);
		javaEntity().getDiscriminatorColumn().setSpecifiedDiscriminatorType(null);
		assertNull(typeResource.annotation(JPA.DISCRIMINATOR_COLUMN));
	}
	
	public void testGetDiscriminatorTypeUpdatesFromResourceChange() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumn column = (DiscriminatorColumn) typeResource.addAnnotation(JPA.DISCRIMINATOR_COLUMN);

		column.setDiscriminatorType(org.eclipse.jpt.core.internal.resource.java.DiscriminatorType.INTEGER);
		assertEquals(DiscriminatorType.INTEGER, javaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
		assertEquals(DiscriminatorType.INTEGER, javaEntity().getDiscriminatorColumn().getDiscriminatorType());
		
		column.setDiscriminatorType(null);
		assertNull(javaEntity().getDiscriminatorColumn().getSpecifiedDiscriminatorType());
		assertEquals(IDiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE, javaEntity().getDiscriminatorColumn().getDiscriminatorType());
	}

	public void testGetLength() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(IDiscriminatorColumn.DEFAULT_LENGTH, javaEntity().getDiscriminatorColumn().getLength());
		javaEntity().getDiscriminatorColumn().setSpecifiedLength(55);
		assertEquals(55, javaEntity().getDiscriminatorColumn().getLength());
	}
	
	public void testGetDefaultLength() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(IDiscriminatorColumn.DEFAULT_LENGTH, javaEntity().getDiscriminatorColumn().getDefaultLength());
		javaEntity().getDiscriminatorColumn().setSpecifiedLength(55);
		
		assertEquals(IDiscriminatorColumn.DEFAULT_LENGTH, javaEntity().getDiscriminatorColumn().getDefaultLength());
	}	
	
	public void testGetSpecifiedLength() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(DiscriminatorColumn.DEFAULT_LENGTH, javaEntity().getDiscriminatorColumn().getSpecifiedLength());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumn discriminatorColumn = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);
		discriminatorColumn.setLength(66);
		
		assertEquals(66, javaEntity().getDiscriminatorColumn().getSpecifiedLength());
		assertEquals(66, javaEntity().getDiscriminatorColumn().getLength());		
		discriminatorColumn.setName(null);
		discriminatorColumn.setLength(-1);
		
		assertNull(typeResource.annotation(JPA.DISCRIMINATOR_COLUMN));
		assertEquals(DiscriminatorColumn.DEFAULT_LENGTH, javaEntity().getDiscriminatorColumn().getSpecifiedLength());	
	}	
	
	public void testSetSpecifiedLength() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(DiscriminatorColumn.DEFAULT_LENGTH, javaEntity().getDiscriminatorColumn().getSpecifiedLength());
		
		javaEntity().getDiscriminatorColumn().setSpecifiedLength(100);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumn discriminatorColumn = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);
		
		assertEquals(100, discriminatorColumn.getLength());
		
		javaEntity().getDiscriminatorColumn().setSpecifiedName(null);
		javaEntity().getDiscriminatorColumn().setSpecifiedLength(-1);
		assertNull(typeResource.annotation(JPA.DISCRIMINATOR_COLUMN));
	}
	
	public void testGetLengthUpdatesFromResourceChange() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(-1, javaEntity().getDiscriminatorColumn().getSpecifiedLength());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumn column = (DiscriminatorColumn) typeResource.addAnnotation(JPA.DISCRIMINATOR_COLUMN);

		column.setLength(78);
		assertEquals(78, javaEntity().getDiscriminatorColumn().getSpecifiedLength());
		assertEquals(78, javaEntity().getDiscriminatorColumn().getLength());
		
		column.setLength(-1);
		assertEquals(-1, javaEntity().getDiscriminatorColumn().getSpecifiedLength());
		assertEquals(IDiscriminatorColumn.DEFAULT_LENGTH, javaEntity().getDiscriminatorColumn().getLength());
	}

	
	public void testGetColumnDefinition() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		assertNull(javaEntity().getDiscriminatorColumn().getColumnDefinition());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumn column = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);
		column.setColumnDefinition(COLUMN_DEFINITION);
		
		assertEquals(COLUMN_DEFINITION, javaEntity().getDiscriminatorColumn().getColumnDefinition());
		
		column.setColumnDefinition(null);
		
		assertNull(javaEntity().getDiscriminatorColumn().getColumnDefinition());

		typeResource.removeAnnotation(JPA.DISCRIMINATOR_COLUMN);
	}
	
	public void testSetColumnDefinition() throws Exception {
		createTestEntityWithDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		javaEntity().getDiscriminatorColumn().setColumnDefinition("foo");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		DiscriminatorColumn column = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);
		
		assertEquals("foo", column.getColumnDefinition());
		
		javaEntity().getDiscriminatorColumn().setColumnDefinition(null);
		column = (DiscriminatorColumn) typeResource.annotation(JPA.DISCRIMINATOR_COLUMN);
		assertNull(column.getColumnDefinition());
	}

}
