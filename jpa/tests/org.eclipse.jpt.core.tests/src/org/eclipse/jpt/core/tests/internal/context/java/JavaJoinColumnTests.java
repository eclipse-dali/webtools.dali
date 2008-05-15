/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaJoinColumnTests extends ContextModelTestCase
{
	public JavaJoinColumnTests(String name) {
		super(name);
	}
	
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createOneToOneAnnotation() throws Exception{
		this.createAnnotationAndMembers("OneToOne", "");		
	}
	
	private void createJoinColumnAnnotation() throws Exception{
		this.createAnnotationAndMembers("JoinColumn", 
			"String name() default \"\"; " +
			"String referencedColumnName() default \"\"; " +
			"boolean unique() default false; " +
			"boolean nullable() default true; " +
			"boolean insertable() default true; " +
			"boolean updatable() default true; " +
			"String columnDefinition() default \"\"; " +
			"String table() default \"\";");		
	}
		

	private ICompilationUnit createTestEntityWithOneToOne() throws Exception {
		createEntityAnnotation();
		createOneToOneAnnotation();
		createJoinColumnAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithValidOneToOne() throws Exception {
		createEntityAnnotation();
		createOneToOneAnnotation();
		createJoinColumnAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ONE_TO_ONE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne").append(CR);
				sb.append("    private Project project;").append(CR);
			}
		});
	}

	private void createTargetEntity() throws Exception {
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
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "Project.java", sourceWriter);
	}
	
	public void testUpdateSpecifiedName() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) javaPersistentType().attributes().next().getMapping();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
	
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		
		assertEquals(0, oneToOneMapping.specifiedJoinColumnsSize());
		assertNull(javaJoinColumn);
		
		
		//set name in the resource model, verify context model updated
		javaJoinColumn = (JoinColumnAnnotation) attributeResource.addAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		javaJoinColumn.setName("FOO");	
		JavaJoinColumn joinColumn = oneToOneMapping.specifiedJoinColumns().next();
		assertEquals("FOO", joinColumn.getSpecifiedName());
		assertEquals("FOO", javaJoinColumn.getName());
	
		//set name to null in the resource model, annotation removed, specified join column removed
		javaJoinColumn.setName(null);
		assertEquals(0, oneToOneMapping.specifiedJoinColumnsSize());
		assertNull(attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	public void testModifySpecifiedName() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) javaPersistentType().attributes().next().getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();

		
		JavaJoinColumn joinColumn = oneToOneMapping.addSpecifiedJoinColumn(0);
		//set name in the context model, verify resource model modified
		joinColumn.setSpecifiedName("foo");
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		assertEquals("foo", joinColumn.getSpecifiedName());
		assertEquals("foo", javaJoinColumn.getName());
		
		//set name to null in the context model
		joinColumn.setSpecifiedName(null);
		assertNull(joinColumn.getSpecifiedName());
		assertNull(attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME));
	}
	
	// <attribute name>_<referenced column name>
	//     or
	// <target entity name>_<referenced column name>	
	public void testDefaultName() throws Exception {
		createTestEntityWithValidOneToOne();
		createTargetEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		
		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) javaPersistentType().attributes().next().getMapping();
		JavaJoinColumn defaultJavaJoinColumn = oneToOneMapping.getDefaultJoinColumn();
		
//		assertNull(defaultJavaJoinColumn.getDefaultName());
//TODO test default join column name
//		//add target entity to the persistence unit, now join table name is [table name]_[target table name]
//		addXmlClassRef(PACKAGE_NAME + ".Project");
//		assertEquals(TYPE_NAME + "_Project", joinTable.getDefaultName());
//		
//		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
//		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
//		assertNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
//	
//		//target entity does not resolve, default name is null
//		manyToManyMapping.setSpecifiedTargetEntity("Foo");
//		assertNull(joinTable.getDefaultName());
//
//		//default target entity does resolve, so default name is again [table name]_[target table name]
//		manyToManyMapping.setSpecifiedTargetEntity(null);
//		assertEquals(TYPE_NAME + "_Project", joinTable.getDefaultName());
//
//		//add the join table annotation, verify default join table name is the same
//		attributeResource.addAnnotation(JoinTable.ANNOTATION_NAME);
//		assertEquals(TYPE_NAME + "_Project", joinTable.getDefaultName());
//		assertNotNull(attributeResource.annotation(JoinTable.ANNOTATION_NAME));
//		
//		//set a table on the target entity, very default join table name updates
//		manyToManyMapping.getResolvedTargetEntity().getTable().setSpecifiedName("FOO");
//		assertEquals(TYPE_NAME + "_FOO", joinTable.getDefaultName());
//		
//		//set a table on the owning entity, very default join table name updates
//		javaEntity().getTable().setSpecifiedName("BAR");
//		assertEquals("BAR_FOO", joinTable.getDefaultName());
	}
	
	public void testUpdateSpecifiedReferencedColumnName() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) javaPersistentType().attributes().next().getMapping();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
	
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		
		assertEquals(0, oneToOneMapping.specifiedJoinColumnsSize());
		assertNull(javaJoinColumn);
		
		
		//set referenced column name in the resource model, verify context model updated
		javaJoinColumn = (JoinColumnAnnotation) attributeResource.addAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		javaJoinColumn.setName("FOO");	
		javaJoinColumn.setReferencedColumnName("BAR");
		JavaJoinColumn joinColumn = oneToOneMapping.specifiedJoinColumns().next();
		assertEquals("BAR", joinColumn.getSpecifiedReferencedColumnName());
		assertEquals("BAR", javaJoinColumn.getReferencedColumnName());
	
		//set referenced column name to null in the resource model, 
		javaJoinColumn.setReferencedColumnName(null);
		assertNull(joinColumn.getSpecifiedReferencedColumnName());
		assertNull("BAR", javaJoinColumn.getReferencedColumnName());
	}
	
	public void testModifySpecifiedReferencedColumnName() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) javaPersistentType().attributes().next().getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();

		
		JavaJoinColumn joinColumn = oneToOneMapping.addSpecifiedJoinColumn(0);
		//set referenced column name in the context model, verify resource model modified
		joinColumn.setSpecifiedName("foo");
		joinColumn.setSpecifiedReferencedColumnName("BAR");
		
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", joinColumn.getSpecifiedReferencedColumnName());
		assertEquals("BAR", javaJoinColumn.getReferencedColumnName());
		
		//set referenced column name to null in the context model
		joinColumn.setSpecifiedReferencedColumnName(null);
		assertNull(joinColumn.getSpecifiedReferencedColumnName());
		assertNull(javaJoinColumn.getReferencedColumnName());
	}
	
	public void testDefaultReferencedColumnName() throws Exception {
	//TODO test default join column referenced column name
		
	}
	
	public void testUpdateTable() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) javaPersistentType().attributes().next().getMapping();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
	
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		
		assertEquals(0, oneToOneMapping.specifiedJoinColumnsSize());
		assertNull(javaJoinColumn);
		
		
		//set table in the resource model, verify context model updated
		javaJoinColumn = (JoinColumnAnnotation) attributeResource.addAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		javaJoinColumn.setName("FOO");	
		javaJoinColumn.setTable("BAR");
		JavaJoinColumn joinColumn = oneToOneMapping.specifiedJoinColumns().next();
		assertEquals("BAR", joinColumn.getSpecifiedTable());
		assertEquals("BAR", javaJoinColumn.getTable());
	
		//set table to null in the resource model, 
		javaJoinColumn.setTable(null);
		assertNull(joinColumn.getSpecifiedTable());
		assertNull("BAR", javaJoinColumn.getTable());
	}
	
	public void testModifyTable() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) javaPersistentType().attributes().next().getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();

		
		JavaJoinColumn joinColumn = oneToOneMapping.addSpecifiedJoinColumn(0);
		//set table in the context model, verify resource model modified
		joinColumn.setSpecifiedName("foo");
		joinColumn.setSpecifiedTable("BAR");
		
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		assertEquals("BAR", joinColumn.getSpecifiedTable());
		assertEquals("BAR", javaJoinColumn.getTable());
		
		//set table to null in the context model
		joinColumn.setSpecifiedTable(null);
		assertNull(joinColumn.getSpecifiedTable());
		assertNull(javaJoinColumn.getTable());
	}
	
	public void testDefaultTable() throws Exception {
	//TODO test default join column table
		
	}

	public void testUpdateUnique() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) javaPersistentType().attributes().next().getMapping();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
	
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		
		assertEquals(0, oneToOneMapping.specifiedJoinColumnsSize());
		assertNull(javaJoinColumn);
		
		
		//set unique in the resource model, verify context model updated
		javaJoinColumn = (JoinColumnAnnotation) attributeResource.addAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		javaJoinColumn.setName("FOO");	
		javaJoinColumn.setUnique(Boolean.TRUE);
		JavaJoinColumn joinColumn = oneToOneMapping.specifiedJoinColumns().next();
		assertEquals(Boolean.TRUE, joinColumn.getSpecifiedUnique());
		assertEquals(Boolean.TRUE, javaJoinColumn.getUnique());
	
		//set unique to null in the resource model, 
		javaJoinColumn.setUnique(null);
		assertNull(joinColumn.getSpecifiedUnique());
		assertNull(javaJoinColumn.getUnique());
	}
	
	public void testModifyUnique() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) javaPersistentType().attributes().next().getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();

		
		JavaJoinColumn joinColumn = oneToOneMapping.addSpecifiedJoinColumn(0);
		//set unique in the context model, verify resource model modified
		joinColumn.setSpecifiedName("foo");
		joinColumn.setSpecifiedUnique(Boolean.TRUE);
		
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.TRUE, joinColumn.getSpecifiedUnique());
		assertEquals(Boolean.TRUE, javaJoinColumn.getUnique());
		
		//set unique to null in the context model
		joinColumn.setSpecifiedUnique(null);
		assertNull(joinColumn.getSpecifiedUnique());
		assertNull(javaJoinColumn.getUnique());
	}
	
	public void testUpdateNullable() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) javaPersistentType().attributes().next().getMapping();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
	
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		
		assertEquals(0, oneToOneMapping.specifiedJoinColumnsSize());
		assertNull(javaJoinColumn);
		
		
		//set nullable in the resource model, verify context model updated
		javaJoinColumn = (JoinColumnAnnotation) attributeResource.addAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		javaJoinColumn.setName("FOO");	
		javaJoinColumn.setNullable(Boolean.FALSE);
		JavaJoinColumn joinColumn = oneToOneMapping.specifiedJoinColumns().next();
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, javaJoinColumn.getNullable());
	
		//set nullable to null in the resource model, 
		javaJoinColumn.setNullable(null);
		assertNull(joinColumn.getSpecifiedNullable());
		assertNull(javaJoinColumn.getNullable());
	}
	
	public void testModifyNullable() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) javaPersistentType().attributes().next().getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();

		
		JavaJoinColumn joinColumn = oneToOneMapping.addSpecifiedJoinColumn(0);
		//set nullable in the context model, verify resource model modified
		joinColumn.setSpecifiedName("foo");
		joinColumn.setSpecifiedNullable(Boolean.FALSE);
		
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedNullable());
		assertEquals(Boolean.FALSE, javaJoinColumn.getNullable());
		
		//set nullable to null in the context model
		joinColumn.setSpecifiedNullable(null);
		assertNull(joinColumn.getSpecifiedNullable());
		assertNull(javaJoinColumn.getNullable());
	}

	public void testUpdateInsertable() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) javaPersistentType().attributes().next().getMapping();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
	
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		
		assertEquals(0, oneToOneMapping.specifiedJoinColumnsSize());
		assertNull(javaJoinColumn);
		
		
		//set insertable in the resource model, verify context model updated
		javaJoinColumn = (JoinColumnAnnotation) attributeResource.addAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		javaJoinColumn.setName("FOO");	
		javaJoinColumn.setInsertable(Boolean.FALSE);
		JavaJoinColumn joinColumn = oneToOneMapping.specifiedJoinColumns().next();
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, javaJoinColumn.getInsertable());
	
		//set insertable to null in the resource model, 
		javaJoinColumn.setInsertable(null);
		assertNull(joinColumn.getSpecifiedInsertable());
		assertNull(javaJoinColumn.getInsertable());
	}
	
	public void testModifyInsertable() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) javaPersistentType().attributes().next().getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();

		
		JavaJoinColumn joinColumn = oneToOneMapping.addSpecifiedJoinColumn(0);
		//set insertable in the context model, verify resource model modified
		joinColumn.setSpecifiedName("foo");
		joinColumn.setSpecifiedInsertable(Boolean.FALSE);
		
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedInsertable());
		assertEquals(Boolean.FALSE, javaJoinColumn.getInsertable());
		
		//set insertable to null in the context model
		joinColumn.setSpecifiedInsertable(null);
		assertNull(joinColumn.getSpecifiedInsertable());
		assertNull(javaJoinColumn.getInsertable());
	}

	public void testUpdateUpdatable() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) javaPersistentType().attributes().next().getMapping();
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
	
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		
		assertEquals(0, oneToOneMapping.specifiedJoinColumnsSize());
		assertNull(javaJoinColumn);
		
		
		//set updatable in the resource model, verify context model updated
		javaJoinColumn = (JoinColumnAnnotation) attributeResource.addAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		javaJoinColumn.setName("FOO");	
		javaJoinColumn.setUpdatable(Boolean.FALSE);
		JavaJoinColumn joinColumn = oneToOneMapping.specifiedJoinColumns().next();
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.FALSE, javaJoinColumn.getUpdatable());
	
		//set updatable to null in the resource model, 
		javaJoinColumn.setUpdatable(null);
		assertNull(joinColumn.getSpecifiedUpdatable());
		assertNull(javaJoinColumn.getUpdatable());
	}
	
	public void testModifyUpdatable() throws Exception {
		createTestEntityWithOneToOne();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaOneToOneMapping oneToOneMapping = (JavaOneToOneMapping) javaPersistentType().attributes().next().getMapping();
		JavaResourcePersistentType typeResource = jpaProject().getJavaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();

		
		JavaJoinColumn joinColumn = oneToOneMapping.addSpecifiedJoinColumn(0);
		//set updatable in the context model, verify resource model modified
		joinColumn.setSpecifiedName("foo");
		joinColumn.setSpecifiedUpdatable(Boolean.FALSE);
		
		JoinColumnAnnotation javaJoinColumn = (JoinColumnAnnotation) attributeResource.getAnnotation(JoinColumnAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.FALSE, joinColumn.getSpecifiedUpdatable());
		assertEquals(Boolean.FALSE, javaJoinColumn.getUpdatable());
		
		//set updatable to null in the context model
		joinColumn.setSpecifiedUpdatable(null);
		assertNull(joinColumn.getSpecifiedUpdatable());
		assertNull(javaJoinColumn.getUpdatable());
	}
}