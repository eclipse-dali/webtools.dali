/*******************************************************************************
 *  Copyright (c) 2008 Oracle. 
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
import org.eclipse.jpt.core.internal.context.base.IAssociationOverride;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.AssociationOverride;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaAssociationOverrideTests extends ContextModelTestCase
{
	private static final String ASSOCIATION_OVERRIDE_NAME = "MY_ASSOCIATION_OVERRIDE_NAME";

	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createMappedSuperclassAnnotation() throws Exception{
		this.createAnnotationAndMembers("MappedSuperclass", "");		
	}
	
	private void createJoinColumnAnnotation() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", 
			"String name() default \"\";" +
			"String referencedColumnName() default \"\";" +
			"boolean unique() default false;" +
			"boolean nullable() default true;" +
			"boolean insertable() default true;" +
			"boolean updatable() default true;" +
			"String columnDefinition() default \"\";" +
			"String table() default \"\";");		
	}
	
	private void createAssociationOverrideAnnotation() throws Exception {
		createJoinColumnAnnotation();
		this.createAnnotationAndMembers("AssociationOverride", 
			"String name();" +
			"JoinColumn[] joinColumns();");		
	}
		
	private IType createTestMappedSuperclass() throws Exception {
		createMappedSuperclassAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS, JPA.ONE_TO_ONE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("    @OneToOne");
				sb.append(CR);
				sb.append("    private int address;").append(CR);
				sb.append(CR);
				sb.append("    ");
			}
		});
	}

	private IType createTestEntity() throws Exception {
		createEntityAnnotation();
		createAssociationOverrideAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ASSOCIATION_OVERRIDE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
		});
	}

	private IType createTestEntityWithAssociationOverride() throws Exception {
		createEntityAnnotation();
		createAssociationOverrideAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ASSOCIATION_OVERRIDE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@AssociationOverride(name=\"" + ASSOCIATION_OVERRIDE_NAME + "\")");
			}
		});
	}

	private IType createTestSubType() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("AnnotationTestTypeChild").append(" ");
				sb.append("extends " + TYPE_NAME + " ");
				sb.append("{}").append(CR);
			}
		};
		return this.javaProject.createType(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
	}


		
	public JavaAssociationOverrideTests(String name) {
		super(name);
	}
	
	public void testUpdateName() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IAssociationOverride javaAssociationOverride = javaEntity().addSpecifiedAssociationOverride(0);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverride associationOverrideResource = (AssociationOverride) typeResource.annotation(JPA.ASSOCIATION_OVERRIDE);
		
		assertNull(javaAssociationOverride.getName());
		assertNull(associationOverrideResource.getName());
		assertTrue(javaEntity().associationOverrides().hasNext());
		
		//set name in the resource model, verify context model updated
		associationOverrideResource.setName("FOO");
		assertEquals("FOO", javaAssociationOverride.getName());
		assertEquals("FOO", associationOverrideResource.getName());
	
		//set name to null in the resource model
		associationOverrideResource.setName(null);
		ormResource().save(null);
		assertNull(javaAssociationOverride.getName());
		assertNull(associationOverrideResource.getName());
		
		associationOverrideResource.setName("FOO");
		assertEquals("FOO", javaAssociationOverride.getName());
		assertEquals("FOO", associationOverrideResource.getName());

		typeResource.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		assertFalse(javaEntity().associationOverrides().hasNext());
		assertFalse(typeResource.annotations(JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES).hasNext());
	}
	
	public void testModifyName() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IAssociationOverride javaAssociationOverride = javaEntity().addSpecifiedAssociationOverride(0);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverride associationOverrideResource = (AssociationOverride) typeResource.annotation(JPA.ASSOCIATION_OVERRIDE);

		assertNull(javaAssociationOverride.getName());
		assertNull(associationOverrideResource.getName());
		assertTrue(javaEntity().associationOverrides().hasNext());
		
		//set name in the context model, verify resource model modified
		javaAssociationOverride.setName("foo");
		assertEquals("foo", javaAssociationOverride.getName());
		assertEquals("foo", associationOverrideResource.getName());
		
		//set name to null in the context model
		javaAssociationOverride.setName(null);
		assertNull(javaAssociationOverride.getName());
		associationOverrideResource = (AssociationOverride) typeResource.annotation(JPA.ASSOCIATION_OVERRIDE);
		assertNull(associationOverrideResource.getName());
	}

	public void testAddSpecifiedJoinColumn() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IAssociationOverride javaAssociationOverride = javaEntity().addSpecifiedAssociationOverride(0);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverride associationOverrideResource = (AssociationOverride) typeResource.annotation(JPA.ASSOCIATION_OVERRIDE);

		
		IJoinColumn joinColumn = javaAssociationOverride.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", associationOverrideResource.joinColumnAt(0).getName());
		
		IJoinColumn joinColumn2 = javaAssociationOverride.addSpecifiedJoinColumn(0);
		ormResource().save(null);
		joinColumn2.setSpecifiedName("BAR");
		ormResource().save(null);
		
		assertEquals("BAR", associationOverrideResource.joinColumnAt(0).getName());
		assertEquals("FOO", associationOverrideResource.joinColumnAt(1).getName());
		
		IJoinColumn joinColumn3 = javaAssociationOverride.addSpecifiedJoinColumn(1);
		ormResource().save(null);
		joinColumn3.setSpecifiedName("BAZ");
		ormResource().save(null);
		
		assertEquals("BAR", associationOverrideResource.joinColumnAt(0).getName());
		assertEquals("BAZ", associationOverrideResource.joinColumnAt(1).getName());
		assertEquals("FOO", associationOverrideResource.joinColumnAt(2).getName());
		
		ListIterator<IJoinColumn> joinColumns = javaAssociationOverride.specifiedJoinColumns();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = javaAssociationOverride.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IAssociationOverride javaAssociationOverride = javaEntity().addSpecifiedAssociationOverride(0);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverride associationOverrideResource = (AssociationOverride) typeResource.annotation(JPA.ASSOCIATION_OVERRIDE);
		
		javaAssociationOverride.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		javaAssociationOverride.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		javaAssociationOverride.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(3, associationOverrideResource.joinColumnsSize());
		
		javaAssociationOverride.removeSpecifiedJoinColumn(0);
		assertEquals(2, associationOverrideResource.joinColumnsSize());
		assertEquals("BAR", associationOverrideResource.joinColumnAt(0).getName());
		assertEquals("BAZ", associationOverrideResource.joinColumnAt(1).getName());

		javaAssociationOverride.removeSpecifiedJoinColumn(0);
		assertEquals(1, associationOverrideResource.joinColumnsSize());
		assertEquals("BAZ", associationOverrideResource.joinColumnAt(0).getName());
		
		javaAssociationOverride.removeSpecifiedJoinColumn(0);
		assertEquals(0, associationOverrideResource.joinColumnsSize());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IAssociationOverride javaAssociationOverride = javaEntity().addSpecifiedAssociationOverride(0);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverride associationOverrideResource = (AssociationOverride) typeResource.annotation(JPA.ASSOCIATION_OVERRIDE);
		
		javaAssociationOverride.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		javaAssociationOverride.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		javaAssociationOverride.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
				
		assertEquals(3, associationOverrideResource.joinColumnsSize());
		
		
		javaAssociationOverride.moveSpecifiedJoinColumn(2, 0);
		ListIterator<IJoinColumn> joinColumns = javaAssociationOverride.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", associationOverrideResource.joinColumnAt(0).getName());
		assertEquals("BAZ", associationOverrideResource.joinColumnAt(1).getName());
		assertEquals("FOO", associationOverrideResource.joinColumnAt(2).getName());


		javaAssociationOverride.moveSpecifiedJoinColumn(0, 1);
		joinColumns = javaAssociationOverride.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", associationOverrideResource.joinColumnAt(0).getName());
		assertEquals("BAR", associationOverrideResource.joinColumnAt(1).getName());
		assertEquals("FOO", associationOverrideResource.joinColumnAt(2).getName());
	}
	
	public void testUpdateJoinColumns() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IAssociationOverride javaAssociationOverride = javaEntity().addSpecifiedAssociationOverride(0);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverride associationOverrideResource = (AssociationOverride) typeResource.annotation(JPA.ASSOCIATION_OVERRIDE);
	
		associationOverrideResource.addJoinColumn(0);
		associationOverrideResource.addJoinColumn(1);
		associationOverrideResource.addJoinColumn(2);
		
		associationOverrideResource.joinColumnAt(0).setName("FOO");
		associationOverrideResource.joinColumnAt(1).setName("BAR");
		associationOverrideResource.joinColumnAt(2).setName("BAZ");

		ListIterator<IJoinColumn> joinColumns = javaAssociationOverride.specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		associationOverrideResource.moveJoinColumn(2, 0);
		joinColumns = javaAssociationOverride.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		associationOverrideResource.moveJoinColumn(0, 1);
		joinColumns = javaAssociationOverride.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		associationOverrideResource.removeJoinColumn(1);
		joinColumns = javaAssociationOverride.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		associationOverrideResource.removeJoinColumn(1);
		joinColumns = javaAssociationOverride.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		associationOverrideResource.removeJoinColumn(0);
		assertFalse(javaAssociationOverride.specifiedJoinColumns().hasNext());
	}	
	
	
	
	
	public void testGetName() throws Exception {
		createTestEntityWithAssociationOverride();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IAssociationOverride specifiedAssociationOverride = javaEntity().specifiedAssociationOverrides().next();
		assertEquals(ASSOCIATION_OVERRIDE_NAME, specifiedAssociationOverride.getName());
		
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverride associationOverrideResource = (AssociationOverride) typeResource.annotation(JPA.ASSOCIATION_OVERRIDE);
		
		associationOverrideResource.setName("FOO");
		specifiedAssociationOverride = javaEntity().specifiedAssociationOverrides().next();
		assertEquals("FOO", specifiedAssociationOverride.getName());
	}
	
	public void testSetName() throws Exception {
		createTestEntityWithAssociationOverride();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IAssociationOverride specifiedAssociationOverride = javaEntity().specifiedAssociationOverrides().next();
		assertEquals(ASSOCIATION_OVERRIDE_NAME, specifiedAssociationOverride.getName());
		
		specifiedAssociationOverride.setName("FOO");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverride associationOverrideResource = (AssociationOverride) typeResource.annotation(JPA.ASSOCIATION_OVERRIDE);
		
		assertEquals("FOO", associationOverrideResource.getName());
	}

	public void testDefaultName() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IEntity entity = javaEntity();	
		assertEquals("AnnotationTestTypeChild", entity.getName());
		assertEquals(1, entity.defaultAssociationOverridesSize());
		
		IAssociationOverride associationOverride = entity.defaultAssociationOverrides().next();
		assertEquals("address", associationOverride.getName());
	}
	
	public void testIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IEntity entity = javaEntity();	
		assertEquals("AnnotationTestTypeChild", entity.getName());
		assertEquals(1, entity.defaultAssociationOverridesSize());
		
		IAssociationOverride associationOverride = entity.defaultAssociationOverrides().next();
		assertTrue(associationOverride.isVirtual());
	}
}
