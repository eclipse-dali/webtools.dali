/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinColumnRelationshipStrategy;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.JoinTableRelationshipStrategy;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.core.context.VirtualAssociationOverride;
import org.eclipse.jpt.core.context.VirtualJoinTable;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinColumnRelationshipStrategy;
import org.eclipse.jpt.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.core.jpa2.context.OverrideRelationship2_0;
import org.eclipse.jpt.core.jpa2.context.VirtualOverrideRelationship2_0;
import org.eclipse.jpt.core.jpa2.resource.java.AssociationOverride2_0Annotation;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

@SuppressWarnings("nls")
public class GenericJavaAssociationOverride2_0Tests extends Generic2_0ContextModelTestCase
{
	private static final String ASSOCIATION_OVERRIDE_NAME = "MY_ASSOCIATION_OVERRIDE_NAME";

		
	private ICompilationUnit createTestMappedSuperclass() throws Exception {		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS, JPA.ONE_TO_ONE, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@OneToOne");
				sb.append(CR);
				sb.append("    private AnnotationTestTypeChild address;").append(CR);
				sb.append(CR);
				sb.append("    @Id");
			}
		});
	}
	
	private ICompilationUnit createTestMappedSuperclassManyToMany() throws Exception {		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS, JPA.MANY_TO_MANY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ManyToMany");
				sb.append(CR);
				sb.append("    private java.util.Collection<AnnotationTestTypeChild> address;").append(CR);
				sb.append(CR);
				sb.append("    @Id");
			}
		});
	}

	private ICompilationUnit createTestEntityWithAssociationOverride() throws Exception {
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

	private void createTestSubType() throws Exception {
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
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
	}


		
	public GenericJavaAssociationOverride2_0Tests(String name) {
		super(name);
	}
	
	public void testUpdateName() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		
		ReadOnlyAssociationOverride javaAssociationOverride = overrideContainer.virtualOverrides().next();
		javaAssociationOverride = ((VirtualAssociationOverride) javaAssociationOverride).convertToSpecified();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(PACKAGE_NAME + ".AnnotationTestTypeChild");
		AssociationOverrideAnnotation associationOverrideResource = (AssociationOverrideAnnotation) typeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);
		
		assertEquals("address", javaAssociationOverride.getName());
		assertEquals("address", associationOverrideResource.getName());
		assertTrue(overrideContainer.overrides().hasNext());
		
		//set name in the resource model, verify context model updated
		associationOverrideResource.setName("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals("FOO", javaAssociationOverride.getName());
		assertEquals("FOO", associationOverrideResource.getName());
	
		//set name to null in the resource model
		associationOverrideResource.setName(null);
		getJpaProject().synchronizeContextModel();
		assertEquals(0, overrideContainer.specifiedOverridesSize());
		assertNull(associationOverrideResource.getName());
		
		associationOverrideResource.setName("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals(1, overrideContainer.specifiedOverridesSize());
		javaAssociationOverride = overrideContainer.specifiedOverrides().next();
		assertEquals("FOO", javaAssociationOverride.getName());
		assertEquals("FOO", associationOverrideResource.getName());

		typeResource.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES);
		getJpaProject().synchronizeContextModel();
		assertFalse(overrideContainer.specifiedOverrides().hasNext());
		assertFalse(typeResource.annotations(JPA.ASSOCIATION_OVERRIDE, JPA.ASSOCIATION_OVERRIDES).hasNext());
	}
	
	public void testModifyName() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		VirtualAssociationOverride virtualAssociationOverride = overrideContainer.virtualOverrides().next();
		AssociationOverride specifiedAssociationOverride = virtualAssociationOverride.convertToSpecified();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(PACKAGE_NAME + ".AnnotationTestTypeChild");
		AssociationOverrideAnnotation associationOverrideResource = (AssociationOverrideAnnotation) typeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);

		assertEquals("address", specifiedAssociationOverride.getName());
		assertEquals("address", associationOverrideResource.getName());
		assertTrue(overrideContainer.overrides().hasNext());
		
		//set name in the context model, verify resource model modified
		specifiedAssociationOverride.setName("foo");
		assertEquals("foo", specifiedAssociationOverride.getName());
		assertEquals("foo", associationOverrideResource.getName());
		
		//set name to null in the context model
		specifiedAssociationOverride.setName(null);
		assertNull(specifiedAssociationOverride.getName());
		associationOverrideResource = (AssociationOverrideAnnotation) typeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);
		assertNull(associationOverrideResource.getName());
	}

	public void testAddSpecifiedJoinColumn() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		AssociationOverride specifiedOverride = overrideContainer.virtualOverrides().next().convertToSpecified();
		JoinColumnRelationshipStrategy joiningStrategy = specifiedOverride.getRelationship().getJoinColumnStrategy();
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(PACKAGE_NAME + ".AnnotationTestTypeChild");
		AssociationOverrideAnnotation associationOverrideResource = (AssociationOverrideAnnotation) typeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);

		
		JoinColumn joinColumn = joiningStrategy.addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("FOO");
				
		assertEquals("FOO", associationOverrideResource.joinColumnAt(0).getName());
		
		JoinColumn joinColumn2 = joiningStrategy.addSpecifiedJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", associationOverrideResource.joinColumnAt(0).getName());
		assertEquals("FOO", associationOverrideResource.joinColumnAt(1).getName());
		
		JoinColumn joinColumn3 = joiningStrategy.addSpecifiedJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals("BAR", associationOverrideResource.joinColumnAt(0).getName());
		assertEquals("BAZ", associationOverrideResource.joinColumnAt(1).getName());
		assertEquals("FOO", associationOverrideResource.joinColumnAt(2).getName());
		
		ListIterator<JoinColumn> joinColumns = (ListIterator<JoinColumn>) joiningStrategy.specifiedJoinColumns();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn, joinColumns.next());
		
		joinColumns = (ListIterator<JoinColumn>) joiningStrategy.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		AssociationOverride javaAssociationOverride = overrideContainer.virtualOverrides().next().convertToSpecified();
		JoinColumnRelationshipStrategy joiningStrategy = javaAssociationOverride.getRelationship().getJoinColumnStrategy();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(PACKAGE_NAME + ".AnnotationTestTypeChild");
		AssociationOverrideAnnotation associationOverrideResource = (AssociationOverrideAnnotation) typeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);
		
		assertEquals(1, associationOverrideResource.joinColumnsSize());

		joiningStrategy.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joiningStrategy.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joiningStrategy.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(4, associationOverrideResource.joinColumnsSize());
		
		joiningStrategy.removeSpecifiedJoinColumn(0);
		assertEquals(3, associationOverrideResource.joinColumnsSize());
		assertEquals("BAR", associationOverrideResource.joinColumnAt(0).getName());
		assertEquals("BAZ", associationOverrideResource.joinColumnAt(1).getName());

		joiningStrategy.removeSpecifiedJoinColumn(0);
		assertEquals(2, associationOverrideResource.joinColumnsSize());
		assertEquals("BAZ", associationOverrideResource.joinColumnAt(0).getName());
		
		joiningStrategy.removeSpecifiedJoinColumn(0);
		assertEquals(1, associationOverrideResource.joinColumnsSize());
		
		joiningStrategy.removeSpecifiedJoinColumn(0);
		assertEquals(0, associationOverrideResource.joinColumnsSize());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		AssociationOverride javaAssociationOverride = overrideContainer.virtualOverrides().next().convertToSpecified();
		JoinColumnRelationshipStrategy joiningStrategy = javaAssociationOverride.getRelationship().getJoinColumnStrategy();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(PACKAGE_NAME + ".AnnotationTestTypeChild");
		AssociationOverrideAnnotation associationOverrideResource = (AssociationOverrideAnnotation) typeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);
		
		joiningStrategy.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joiningStrategy.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joiningStrategy.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
				
		assertEquals(4, associationOverrideResource.joinColumnsSize());
		
		
		joiningStrategy.moveSpecifiedJoinColumn(2, 0);
		ListIterator<JoinColumn> joinColumns = (ListIterator<JoinColumn>) joiningStrategy.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", associationOverrideResource.joinColumnAt(0).getName());
		assertEquals("BAZ", associationOverrideResource.joinColumnAt(1).getName());
		assertEquals("FOO", associationOverrideResource.joinColumnAt(2).getName());


		joiningStrategy.moveSpecifiedJoinColumn(0, 1);
		joinColumns = (ListIterator<JoinColumn>) joiningStrategy.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAZ", associationOverrideResource.joinColumnAt(0).getName());
		assertEquals("BAR", associationOverrideResource.joinColumnAt(1).getName());
		assertEquals("FOO", associationOverrideResource.joinColumnAt(2).getName());
	}
	
	public void testUpdateJoinColumns() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		JavaVirtualAssociationOverride virtualAssociationOverride = overrideContainer.virtualOverrides().next();
		JavaAssociationOverride specifiedAssociationOverride = virtualAssociationOverride.convertToSpecified();
		JavaJoinColumnRelationshipStrategy joiningStrategy = specifiedAssociationOverride.getRelationship().getJoinColumnStrategy();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(PACKAGE_NAME + ".AnnotationTestTypeChild");
		AssociationOverrideAnnotation associationOverrideResource = (AssociationOverrideAnnotation) typeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);
		
		ListIterator<JavaJoinColumn> joinColumns = joiningStrategy.specifiedJoinColumns();
		JoinColumn joinColumn = joinColumns.next();
		assertEquals("address_id", joinColumn.getSpecifiedName());
		assertEquals("id", joinColumn.getSpecifiedReferencedColumnName());
	
		associationOverrideResource.addJoinColumn(0);
		associationOverrideResource.addJoinColumn(1);
		associationOverrideResource.addJoinColumn(2);
		
		associationOverrideResource.joinColumnAt(0).setName("FOO");
		associationOverrideResource.joinColumnAt(1).setName("BAR");
		associationOverrideResource.joinColumnAt(2).setName("BAZ");
		getJpaProject().synchronizeContextModel();

		joinColumns = joiningStrategy.specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("address_id", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		associationOverrideResource.moveJoinColumn(2, 0);
		getJpaProject().synchronizeContextModel();
		joinColumns = joiningStrategy.specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("address_id", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		associationOverrideResource.moveJoinColumn(0, 1);
		getJpaProject().synchronizeContextModel();
		joinColumns = joiningStrategy.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("address_id", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		associationOverrideResource.removeJoinColumn(1);
		getJpaProject().synchronizeContextModel();
		joinColumns = joiningStrategy.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("address_id", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		associationOverrideResource.removeJoinColumn(1);
		getJpaProject().synchronizeContextModel();
		joinColumns = joiningStrategy.specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("address_id", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());		
		
		associationOverrideResource.removeJoinColumn(0);
		getJpaProject().synchronizeContextModel();
		joinColumns = joiningStrategy.specifiedJoinColumns();
		assertEquals("address_id", joinColumns.next().getName());

		associationOverrideResource.removeJoinColumn(0);
		getJpaProject().synchronizeContextModel();
		assertFalse(joiningStrategy.specifiedJoinColumns().hasNext());
	}
	
	public void testGetName() throws Exception {
		createTestEntityWithAssociationOverride();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();

		AssociationOverride specifiedAssociationOverride = overrideContainer.specifiedOverrides().next();
		assertEquals(ASSOCIATION_OVERRIDE_NAME, specifiedAssociationOverride.getName());
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverrideAnnotation associationOverrideResource = (AssociationOverrideAnnotation) typeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);
		
		associationOverrideResource.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverride = overrideContainer.specifiedOverrides().next();
		assertEquals("FOO", specifiedAssociationOverride.getName());
	}
	
	public void testSetName() throws Exception {
		createTestEntityWithAssociationOverride();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();

		AssociationOverride specifiedAssociationOverride = overrideContainer.specifiedOverrides().next();
		assertEquals(ASSOCIATION_OVERRIDE_NAME, specifiedAssociationOverride.getName());
		
		specifiedAssociationOverride.setName("FOO");
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		AssociationOverrideAnnotation associationOverrideResource = (AssociationOverrideAnnotation) typeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);
		
		assertEquals("FOO", associationOverrideResource.getName());
	}

	public void testDefaultName() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();	
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		assertEquals("AnnotationTestTypeChild", entity.getName());
		assertEquals(1, overrideContainer.virtualOverridesSize());
		
		ReadOnlyAssociationOverride associationOverride = overrideContainer.virtualOverrides().next();
		assertEquals("address", associationOverride.getName());
	}
	
	public void testIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();	
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		assertEquals("AnnotationTestTypeChild", entity.getName());
		assertEquals(1, overrideContainer.virtualOverridesSize());
		
		ReadOnlyAssociationOverride associationOverride = overrideContainer.virtualOverrides().next();
		assertTrue(associationOverride.isVirtual());
	}
	
	
	public void testSetJoinTableName() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		ReadOnlyAssociationOverride javaAssociationOverride = overrideContainer.virtualOverrides().next();
		javaAssociationOverride = ((VirtualAssociationOverride) javaAssociationOverride).convertToSpecified();
		((OverrideRelationship2_0) javaAssociationOverride.getRelationship()).setStrategyToJoinTable();
		JoinTableRelationshipStrategy joiningStrategy = ((OverrideRelationship2_0) javaAssociationOverride.getRelationship()).getJoinTableStrategy();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(PACKAGE_NAME + ".AnnotationTestTypeChild");
		AssociationOverride2_0Annotation associationOverrideResource = (AssociationOverride2_0Annotation) typeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);

		
		JoinTable joinTable = joiningStrategy.getJoinTable();
		joinTable.setSpecifiedName("FOO");
				
		assertEquals("FOO", associationOverrideResource.getJoinTable().getName());
	}
	
	public void testUpdateJoinTable() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		ReadOnlyAssociationOverride javaAssociationOverride = overrideContainer.virtualOverrides().next();
		javaAssociationOverride = ((VirtualAssociationOverride) javaAssociationOverride).convertToSpecified();
		((OverrideRelationship2_0) javaAssociationOverride.getRelationship()).setStrategyToJoinTable();
		JoinTableRelationshipStrategy joiningStrategy = ((OverrideRelationship2_0) javaAssociationOverride.getRelationship()).getJoinTableStrategy();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(PACKAGE_NAME + ".AnnotationTestTypeChild");
		AssociationOverride2_0Annotation associationOverrideResource = (AssociationOverride2_0Annotation) typeResource.getAnnotation(JPA.ASSOCIATION_OVERRIDE);
	
		associationOverrideResource.getJoinTable().setName("MY_JOIN_TABLE");
		getJpaProject().synchronizeContextModel();
		assertEquals("MY_JOIN_TABLE", joiningStrategy.getJoinTable().getSpecifiedName());

		associationOverrideResource.getJoinTable().addJoinColumn(0);
		associationOverrideResource.getJoinTable().addJoinColumn(1);
		associationOverrideResource.getJoinTable().addJoinColumn(2);
		
		associationOverrideResource.getJoinTable().joinColumnAt(0).setName("FOO");
		associationOverrideResource.getJoinTable().joinColumnAt(1).setName("BAR");
		associationOverrideResource.getJoinTable().joinColumnAt(2).setName("BAZ");
		getJpaProject().synchronizeContextModel();

		ListIterator<JoinColumn> joinColumns = (ListIterator<JoinColumn>) joiningStrategy.getJoinTable().specifiedJoinColumns();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		associationOverrideResource.getJoinTable().moveJoinColumn(2, 0);
		getJpaProject().synchronizeContextModel();
		joinColumns = (ListIterator<JoinColumn>) joiningStrategy.getJoinTable().specifiedJoinColumns();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		associationOverrideResource.getJoinTable().moveJoinColumn(0, 1);
		getJpaProject().synchronizeContextModel();
		joinColumns = (ListIterator<JoinColumn>) joiningStrategy.getJoinTable().specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		associationOverrideResource.getJoinTable().removeJoinColumn(1);
		getJpaProject().synchronizeContextModel();
		joinColumns = (ListIterator<JoinColumn>) joiningStrategy.getJoinTable().specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		associationOverrideResource.getJoinTable().removeJoinColumn(1);
		getJpaProject().synchronizeContextModel();
		joinColumns = (ListIterator<JoinColumn>) joiningStrategy.getJoinTable().specifiedJoinColumns();
		assertEquals("BAZ", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		associationOverrideResource.getJoinTable().removeJoinColumn(0);
		getJpaProject().synchronizeContextModel();
		assertFalse(joiningStrategy.getJoinTable().specifiedJoinColumns().hasNext());
	}	
	
	public void testDefaultJoinTableName() throws Exception {
		createTestMappedSuperclassManyToMany();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		ReadOnlyAssociationOverride javaAssociationOverride = overrideContainer.virtualOverrides().next();
		VirtualOverrideRelationship2_0 rel = (VirtualOverrideRelationship2_0) javaAssociationOverride.getRelationship();
		VirtualJoinTable joinTable = rel.getJoinTableStrategy().getJoinTable();
		
		assertEquals("AnnotationTestTypeChild_AnnotationTestTypeChild", joinTable.getName());
		
		
		getJavaEntity().getTable().setSpecifiedName("FOO");
		assertEquals("FOO_FOO", joinTable.getName());
		
		PersistentType mappedSuperclass = getJavaEntity().getPersistentType().getSuperPersistentType();
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) mappedSuperclass.getAttributeNamed("address").getMapping();
		manyToManyMapping.getRelationship().getJoinTableStrategy().getJoinTable().setSpecifiedName("BAR");

		assertEquals("BAR", joinTable.getName());
	}	
}
