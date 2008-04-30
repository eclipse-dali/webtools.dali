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
import java.util.ListIterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaJoinTableTests extends ContextModelTestCase
{
	public JavaJoinTableTests(String name) {
		super(name);
	}
	
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createManyToManyAnnotation() throws Exception{
		this.createAnnotationAndMembers("ManyToMany", "");		
	}
	
	private void createJoinTableAnnotation() throws Exception{
		//TODO
		this.createAnnotationAndMembers("JoinTable", 
			"String name() default \"\"; " +
			"String catalog() default \"\"; " +
			"String schema() default \"\";");		
	}
	
	private void createUniqueConstraintAnnotation() throws Exception{
		this.createAnnotationAndMembers("UniqueConstraint", 
			"String[] columnNames(); ");		
	}

	private IType createTestEntityWithManyToMany() throws Exception {
		createEntityAnnotation();
		createManyToManyAnnotation();
		createJoinTableAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToMany").append(CR);
			}
		});
	}
	
	private IType createTestEntityWithValidManyToMany() throws Exception {
		createEntityAnnotation();
		createManyToManyAnnotation();
		createJoinTableAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_MANY, "java.util.Collection");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToMany").append(CR);
				sb.append("    private Collection<Project> projects;").append(CR);
			}
		});
	}

	private IType createTargetEntity() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ID);
					sb.append(";");
					sb.append(CR);
				sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class Project {").append(CR);
				sb.append(CR);
				sb.append("    @Id").append(CR);
				sb.append("    private int proj_id;").append(CR);
				sb.append(CR);
			}
		};
		return this.javaProject.createType(PACKAGE_NAME, "Project.java", sourceWriter);
	}
	public void testUpdateSpecifiedName() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
	
		JoinTableAnnotation javaJoinTable = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedName());
		assertNull(javaJoinTable);
		
		
		//set name in the resource model, verify context model updated
		attributeResource.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		javaJoinTable = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		javaJoinTable.setName("FOO");
		assertEquals("FOO", joinTable.getSpecifiedName());
		assertEquals("FOO", javaJoinTable.getName());
	
		//set name to null in the resource model
		javaJoinTable.setName(null);
		assertNull(joinTable.getSpecifiedName());
		assertNull(javaJoinTable.getName());
		
		javaJoinTable.setName("FOO");
		assertEquals("FOO", joinTable.getSpecifiedName());
		assertEquals("FOO", javaJoinTable.getName());

		attributeResource.removeAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertNull(joinTable.getSpecifiedName());
		assertNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
	}
	
	public void testModifySpecifiedName() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
	
		JoinTableAnnotation javaJoinTable = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedName());
		assertNull(javaJoinTable);
	
		//set name in the context model, verify resource model modified
		joinTable.setSpecifiedName("foo");
		javaJoinTable = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals("foo", joinTable.getSpecifiedName());
		assertEquals("foo", javaJoinTable.getName());
		
		//set name to null in the context model
		joinTable.setSpecifiedName(null);
		assertNull(joinTable.getSpecifiedName());
		assertNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
	}
	
	public void testDefaultName() throws Exception {
		createTestEntityWithValidManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();
		
		//joinTable default name is null because targetEntity is not in the persistence unit
		assertNull(joinTable.getDefaultName());

		//add target entity to the persistence unit, now join table name is [table name]_[target table name]
		createTargetEntity();
		addXmlClassRef(PACKAGE_NAME + ".Project");
		assertEquals(TYPE_NAME + "_Project", joinTable.getDefaultName());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
	
		//target entity does not resolve, default name is null
		manyToManyMapping.setSpecifiedTargetEntity("Foo");
		assertNull(joinTable.getDefaultName());

		//default target entity does resolve, so default name is again [table name]_[target table name]
		manyToManyMapping.setSpecifiedTargetEntity(null);
		assertEquals(TYPE_NAME + "_Project", joinTable.getDefaultName());

		//add the join table annotation, verify default join table name is the same
		attributeResource.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals(TYPE_NAME + "_Project", joinTable.getDefaultName());
		assertNotNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
		
		//set a table on the target entity, very default join table name updates
		manyToManyMapping.getResolvedTargetEntity().getTable().setSpecifiedName("FOO");
		assertEquals(TYPE_NAME + "_FOO", joinTable.getDefaultName());
		
		//set a table on the owning entity, very default join table name updates
		javaEntity().getTable().setSpecifiedName("BAR");
		assertEquals("BAR_FOO", joinTable.getDefaultName());
	}

	public void testUpdateSpecifiedSchema() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
	
		JoinTableAnnotation javaJoinTable = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedSchema());
		assertNull(javaJoinTable);
		
		
		//set schema in the resource model, verify context model updated
		attributeResource.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		javaJoinTable = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		javaJoinTable.setSchema("FOO");
		assertEquals("FOO", joinTable.getSpecifiedSchema());
		assertEquals("FOO", javaJoinTable.getSchema());
	
		//set schema to null in the resource model
		javaJoinTable.setSchema(null);
		assertNull(joinTable.getSpecifiedSchema());
		assertNull(javaJoinTable.getSchema());
		
		javaJoinTable.setSchema("FOO");
		assertEquals("FOO", joinTable.getSpecifiedSchema());
		assertEquals("FOO", javaJoinTable.getSchema());

		attributeResource.removeAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertNull(joinTable.getSpecifiedSchema());
		assertNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
	}
	
	public void testModifySpecifiedSchema() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
	
		JoinTableAnnotation javaJoinTable = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedSchema());
		assertNull(javaJoinTable);
	
		//set schema in the context model, verify resource model modified
		joinTable.setSpecifiedSchema("foo");
		javaJoinTable = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals("foo", joinTable.getSpecifiedSchema());
		assertEquals("foo", javaJoinTable.getSchema());
		
		//set schema to null in the context model
		joinTable.setSpecifiedSchema(null);
		assertNull(joinTable.getSpecifiedSchema());
		assertNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
	}

	public void testUpdateSpecifiedCatalog() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
	
		JoinTableAnnotation javaJoinTable = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedCatalog());
		assertNull(javaJoinTable);
		
		
		//set catalog in the resource model, verify context model updated
		attributeResource.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		javaJoinTable = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		javaJoinTable.setCatalog("FOO");
		assertEquals("FOO", joinTable.getSpecifiedCatalog());
		assertEquals("FOO", javaJoinTable.getCatalog());
	
		//set catalog to null in the resource model
		javaJoinTable.setCatalog(null);
		assertNull(joinTable.getSpecifiedCatalog());
		assertNull(javaJoinTable.getCatalog());
		
		javaJoinTable.setCatalog("FOO");
		assertEquals("FOO", joinTable.getSpecifiedCatalog());
		assertEquals("FOO", javaJoinTable.getCatalog());

		attributeResource.removeAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertNull(joinTable.getSpecifiedCatalog());
		assertNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
	}
	
	public void testModifySpecifiedCatalog() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
	
		JoinTableAnnotation javaJoinTable = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		
		assertNull(joinTable.getSpecifiedCatalog());
		assertNull(javaJoinTable);
	
		//set catalog in the context model, verify resource model modified
		joinTable.setSpecifiedCatalog("foo");
		javaJoinTable = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals("foo", joinTable.getSpecifiedCatalog());
		assertEquals("foo", javaJoinTable.getCatalog());
		
		//set catalog to null in the context model
		joinTable.setSpecifiedCatalog(null);
		assertNull(joinTable.getSpecifiedCatalog());
		assertNull(attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME));
	}

	public void testAddSpecifiedJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
	
		
		JoinColumn joinColumn = joinTable.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		JoinTableAnnotation joinTableResource = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);

		assertEquals("FOO", joinTableResource.joinColumnAt(0).getName());
		
		JoinColumn joinColumn2 = joinTable.addSpecifiedJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", joinTableResource.joinColumnAt(0).getName());
		assertEquals("FOO", joinTableResource.joinColumnAt(1).getName());
		
		JoinColumn joinColumn3 = joinTable.addSpecifiedJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", joinTableResource.joinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.joinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.joinColumnAt(2).getName());
		
		ListIterator<JoinColumn> joinColumns = joinTable.specifiedJoinColumns();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = joinTable.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();

		joinTable.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joinTable.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joinTable.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JoinTableAnnotation joinTableResource = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals(3, joinTableResource.joinColumnsSize());
		
		joinTable.removeSpecifiedJoinColumn(0);
		assertEquals(2, joinTableResource.joinColumnsSize());
		assertEquals("BAR", joinTableResource.joinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.joinColumnAt(1).getName());

		joinTable.removeSpecifiedJoinColumn(0);
		assertEquals(1, joinTableResource.joinColumnsSize());
		assertEquals("BAZ", joinTableResource.joinColumnAt(0).getName());
		
		joinTable.removeSpecifiedJoinColumn(0);
		assertEquals(0, joinTableResource.joinColumnsSize());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();

		joinTable.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joinTable.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joinTable.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		JoinTableAnnotation joinTableResource = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals(3, joinTableResource.joinColumnsSize());
		
		
		joinTable.moveSpecifiedJoinColumn(2, 0);
		ListIterator<JoinColumn> joinColumns = joinTable.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", joinTableResource.joinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.joinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.joinColumnAt(2).getName());


		joinTable.moveSpecifiedJoinColumn(0, 1);
		joinColumns = joinTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", joinTableResource.joinColumnAt(0).getName());
		assertEquals("BAR", joinTableResource.joinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.joinColumnAt(2).getName());
	}
	
	public void testUpdateJoinColumns() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
	
		JoinTableAnnotation joinTableResource = (JoinTableAnnotation) attributeResource.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	
		joinTableResource.addJoinColumn(0);
		joinTableResource.addJoinColumn(1);
		joinTableResource.addJoinColumn(2);
		
		joinTableResource.joinColumnAt(0).setName("FOO");
		joinTableResource.joinColumnAt(1).setName("BAR");
		joinTableResource.joinColumnAt(2).setName("BAZ");
	
		ListIterator<JoinColumn> joinColumns = joinTable.specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.moveJoinColumn(2, 0);
		joinColumns = joinTable.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		joinTableResource.moveJoinColumn(0, 1);
		joinColumns = joinTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		joinTableResource.removeJoinColumn(1);
		joinColumns = joinTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
	
		joinTableResource.removeJoinColumn(1);
		joinColumns = joinTable.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		joinTableResource.removeJoinColumn(0);
		assertFalse(joinTable.specifiedJoinColumns().hasNext());
	}
	
	public void testGetDefaultJoinColumn() {
		//TODO
	}
	
	public void testSpecifiedJoinColumnsSize() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();

		assertEquals(0, joinTable.specifiedJoinColumnsSize());
		
		joinTable.addSpecifiedJoinColumn(0);
		assertEquals(1, joinTable.specifiedJoinColumnsSize());
		
		joinTable.removeSpecifiedJoinColumn(0);
		assertEquals(0, joinTable.specifiedJoinColumnsSize());
	}

	public void testJoinColumnsSize() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();

		assertEquals(1, joinTable.joinColumnsSize());
		
		joinTable.addSpecifiedJoinColumn(0);
		assertEquals(1, joinTable.joinColumnsSize());
		
		joinTable.addSpecifiedJoinColumn(0);
		assertEquals(2, joinTable.joinColumnsSize());

		joinTable.removeSpecifiedJoinColumn(0);
		joinTable.removeSpecifiedJoinColumn(0);
		assertEquals(1, joinTable.joinColumnsSize());
		
		//if non-owning side of the relationship then no default join column
		manyToManyMapping.setMappedBy("foo");
		assertEquals(0, joinTable.joinColumnsSize());
	}
	
	public void testAddSpecifiedInverseJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
	
		
		JoinColumn inverseJoinColumn = joinTable.addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("FOO");
				
		JoinTableAnnotation joinTableResource = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);

		assertEquals("FOO", joinTableResource.inverseJoinColumnAt(0).getName());
		
		JoinColumn inverseJoinColumn2 = joinTable.addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", joinTableResource.inverseJoinColumnAt(0).getName());
		assertEquals("FOO", joinTableResource.inverseJoinColumnAt(1).getName());
		
		JoinColumn inverseJoinColumn3 = joinTable.addSpecifiedInverseJoinColumn(1);
		inverseJoinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", joinTableResource.inverseJoinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.inverseJoinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.inverseJoinColumnAt(2).getName());
		
		ListIterator<JoinColumn> inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals(inverseJoinColumn2, inverseJoinColumns.next());
		assertEquals(inverseJoinColumn3, inverseJoinColumns.next());
		assertEquals(inverseJoinColumn, inverseJoinColumns.next());
		
		inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedInverseJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();

		joinTable.addSpecifiedInverseJoinColumn(0).setSpecifiedName("FOO");
		joinTable.addSpecifiedInverseJoinColumn(1).setSpecifiedName("BAR");
		joinTable.addSpecifiedInverseJoinColumn(2).setSpecifiedName("BAZ");
		
		JoinTableAnnotation joinTableResource = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals(3, joinTableResource.inverseJoinColumnsSize());
		
		joinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(2, joinTableResource.inverseJoinColumnsSize());
		assertEquals("BAR", joinTableResource.inverseJoinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.inverseJoinColumnAt(1).getName());

		joinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(1, joinTableResource.inverseJoinColumnsSize());
		assertEquals("BAZ", joinTableResource.inverseJoinColumnAt(0).getName());
		
		joinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(0, joinTableResource.inverseJoinColumnsSize());
	}
	
	public void testMoveSpecifiedInverseJoinColumn() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();

		joinTable.addSpecifiedInverseJoinColumn(0).setSpecifiedName("FOO");
		joinTable.addSpecifiedInverseJoinColumn(1).setSpecifiedName("BAR");
		joinTable.addSpecifiedInverseJoinColumn(2).setSpecifiedName("BAZ");
		
		JoinTableAnnotation joinTableResource = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		assertEquals(3, joinTableResource.inverseJoinColumnsSize());
		
		
		joinTable.moveSpecifiedInverseJoinColumn(2, 0);
		ListIterator<JoinColumn> inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());

		assertEquals("BAR", joinTableResource.inverseJoinColumnAt(0).getName());
		assertEquals("BAZ", joinTableResource.inverseJoinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.inverseJoinColumnAt(2).getName());


		joinTable.moveSpecifiedInverseJoinColumn(0, 1);
		inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());

		assertEquals("BAZ", joinTableResource.inverseJoinColumnAt(0).getName());
		assertEquals("BAR", joinTableResource.inverseJoinColumnAt(1).getName());
		assertEquals("FOO", joinTableResource.inverseJoinColumnAt(2).getName());
	}
	
	public void testUpdateInverseJoinColumns() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
	
		JoinTableAnnotation joinTableResource = (JoinTableAnnotation) attributeResource.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	
		joinTableResource.addInverseJoinColumn(0);
		joinTableResource.addInverseJoinColumn(1);
		joinTableResource.addInverseJoinColumn(2);
		
		joinTableResource.inverseJoinColumnAt(0).setName("FOO");
		joinTableResource.inverseJoinColumnAt(1).setName("BAR");
		joinTableResource.inverseJoinColumnAt(2).setName("BAZ");
	
		ListIterator<JoinColumn> inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
		
		joinTableResource.moveInverseJoinColumn(2, 0);
		inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
	
		joinTableResource.moveInverseJoinColumn(0, 1);
		inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("BAR", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
	
		joinTableResource.removeInverseJoinColumn(1);
		inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertEquals("FOO", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
	
		joinTableResource.removeInverseJoinColumn(1);
		inverseJoinColumns = joinTable.specifiedInverseJoinColumns();
		assertEquals("BAZ", inverseJoinColumns.next().getName());
		assertFalse(inverseJoinColumns.hasNext());
		
		joinTableResource.removeInverseJoinColumn(0);
		assertFalse(joinTable.specifiedInverseJoinColumns().hasNext());
	}

	public void testGetDefaultInverseJoinColumn() {
		//TODO
	}
	
	public void testSpecifiedInverseJoinColumnsSize() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();

		assertEquals(0, joinTable.specifiedInverseJoinColumnsSize());
		
		joinTable.addSpecifiedInverseJoinColumn(0);
		assertEquals(1, joinTable.specifiedInverseJoinColumnsSize());
		
		joinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(0, joinTable.specifiedInverseJoinColumnsSize());
	}

	public void testInverseJoinColumnsSize() throws Exception {
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaManyToManyMapping manyToManyMapping = (JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping();
		JoinTable joinTable = manyToManyMapping.getJoinTable();

		assertEquals(1, joinTable.inverseJoinColumnsSize());
		
		joinTable.addSpecifiedInverseJoinColumn(0);
		assertEquals(1, joinTable.inverseJoinColumnsSize());
		
		joinTable.addSpecifiedInverseJoinColumn(0);
		assertEquals(2, joinTable.inverseJoinColumnsSize());

		joinTable.removeSpecifiedInverseJoinColumn(0);
		joinTable.removeSpecifiedInverseJoinColumn(0);
		assertEquals(1, joinTable.inverseJoinColumnsSize());
		
		//if non-owning side of the relationship then no default join column
		manyToManyMapping.setMappedBy("foo");
		assertEquals(0, joinTable.inverseJoinColumnsSize());
	}

	public void testUniqueConstraints() throws Exception {
		createUniqueConstraintAnnotation();
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JoinTable joinTable = ((JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping()).getJoinTable();

		ListIterator<JavaUniqueConstraint> uniqueConstraints = joinTable.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());

		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) attributeResource.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		joinTableAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		joinTableAnnotation.addUniqueConstraint(0).addColumnName(0, "bar");
		
		uniqueConstraints = joinTable.uniqueConstraints();
		assertTrue(uniqueConstraints.hasNext());
		assertEquals("bar", uniqueConstraints.next().columnNames().next());
		assertEquals("foo", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testUniqueConstraintsSize() throws Exception {
		createUniqueConstraintAnnotation();
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JoinTable joinTable = ((JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping()).getJoinTable();

		assertEquals(0,  joinTable.uniqueConstraintsSize());

		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) attributeResource.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		joinTableAnnotation.addUniqueConstraint(0).addColumnName(0, "foo");
		joinTableAnnotation.addUniqueConstraint(1).addColumnName(0, "bar");
		
		assertEquals(2,  joinTable.uniqueConstraintsSize());
	}

	public void testAddUniqueConstraint() throws Exception {
		createUniqueConstraintAnnotation();
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JoinTable joinTable = ((JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping()).getJoinTable();
		joinTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		joinTable.addUniqueConstraint(0).addColumnName(0, "BAR");
		joinTable.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		ListIterator<UniqueConstraintAnnotation> uniqueConstraints = joinTableAnnotation.uniqueConstraints();
		
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testAddUniqueConstraint2() throws Exception {
		createUniqueConstraintAnnotation();
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JoinTable joinTable = ((JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping()).getJoinTable();
		joinTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		joinTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		joinTable.addUniqueConstraint(0).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		ListIterator<UniqueConstraintAnnotation> uniqueConstraints = joinTableAnnotation.uniqueConstraints();
		
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testRemoveUniqueConstraint() throws Exception {
		createUniqueConstraintAnnotation();
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JoinTable joinTable = ((JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping()).getJoinTable();
		joinTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		joinTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		joinTable.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		
		assertEquals(3, joinTableAnnotation.uniqueConstraintsSize());

		joinTable.removeUniqueConstraint(1);
		
		ListIterator<UniqueConstraintAnnotation> uniqueConstraintAnnotations = joinTableAnnotation.uniqueConstraints();
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNames().next());		
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNames().next());
		assertFalse(uniqueConstraintAnnotations.hasNext());
		
		Iterator<UniqueConstraint> uniqueConstraints = joinTable.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());		
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		
		joinTable.removeUniqueConstraint(1);
		uniqueConstraintAnnotations = joinTableAnnotation.uniqueConstraints();
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNames().next());		
		assertFalse(uniqueConstraintAnnotations.hasNext());

		uniqueConstraints = joinTable.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());		
		assertFalse(uniqueConstraints.hasNext());

		
		joinTable.removeUniqueConstraint(0);
		uniqueConstraintAnnotations = joinTableAnnotation.uniqueConstraints();
		assertFalse(uniqueConstraintAnnotations.hasNext());
		uniqueConstraints = joinTable.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
	}
	
	public void testMoveUniqueConstraint() throws Exception {
		createUniqueConstraintAnnotation();
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JoinTable joinTable = ((JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping()).getJoinTable();
		joinTable.addUniqueConstraint(0).addColumnName(0, "FOO");
		joinTable.addUniqueConstraint(1).addColumnName(0, "BAR");
		joinTable.addUniqueConstraint(2).addColumnName(0, "BAZ");
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) attributeResource.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
		
		assertEquals(3, joinTableAnnotation.uniqueConstraintsSize());
		
		
		joinTable.moveUniqueConstraint(2, 0);
		ListIterator<UniqueConstraint> uniqueConstraints = joinTable.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());

		ListIterator<UniqueConstraintAnnotation> uniqueConstraintAnnotations = joinTableAnnotation.uniqueConstraints();
		assertEquals("BAR", uniqueConstraintAnnotations.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNames().next());
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNames().next());


		joinTable.moveUniqueConstraint(0, 1);
		uniqueConstraints = joinTable.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());

		uniqueConstraintAnnotations = joinTableAnnotation.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraintAnnotations.next().columnNames().next());
		assertEquals("BAR", uniqueConstraintAnnotations.next().columnNames().next());
		assertEquals("FOO", uniqueConstraintAnnotations.next().columnNames().next());
	}
	
	public void testUpdateUniqueConstraints() throws Exception {
		createUniqueConstraintAnnotation();
		createTestEntityWithManyToMany();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JoinTable joinTable = ((JavaManyToManyMapping) javaPersistentType().attributes().next().getMapping()).getJoinTable();
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		JoinTableAnnotation joinTableAnnotation = (JoinTableAnnotation) attributeResource.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	
		joinTableAnnotation.addUniqueConstraint(0).addColumnName("FOO");
		joinTableAnnotation.addUniqueConstraint(1).addColumnName("BAR");
		joinTableAnnotation.addUniqueConstraint(2).addColumnName("BAZ");

		
		ListIterator<UniqueConstraint> uniqueConstraints = joinTable.uniqueConstraints();
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		joinTableAnnotation.moveUniqueConstraint(2, 0);
		uniqueConstraints = joinTable.uniqueConstraints();
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		joinTableAnnotation.moveUniqueConstraint(0, 1);
		uniqueConstraints = joinTable.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("BAR", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		joinTableAnnotation.removeUniqueConstraint(1);
		uniqueConstraints = joinTable.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertEquals("FOO", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
	
		joinTableAnnotation.removeUniqueConstraint(1);
		uniqueConstraints = joinTable.uniqueConstraints();
		assertEquals("BAZ", uniqueConstraints.next().columnNames().next());
		assertFalse(uniqueConstraints.hasNext());
		
		joinTableAnnotation.removeUniqueConstraint(0);
		uniqueConstraints = joinTable.uniqueConstraints();
		assertFalse(uniqueConstraints.hasNext());
	}
}