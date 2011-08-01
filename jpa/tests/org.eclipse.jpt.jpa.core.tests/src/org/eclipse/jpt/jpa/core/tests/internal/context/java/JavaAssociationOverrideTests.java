/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.jpa.core.context.VirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class JavaAssociationOverrideTests extends ContextModelTestCase
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
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
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


		
	public JavaAssociationOverrideTests(String name) {
		super(name);
	}
	
	public void testUpdateName() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME_ + "AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = getJavaEntity();
		JavaAssociationOverrideContainer overrideContainer = entity.getAssociationOverrideContainer();
		VirtualAssociationOverride virtualOverride = overrideContainer.getVirtualOverrides().iterator().next();
		AssociationOverride javaAssociationOverride = virtualOverride.convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(PACKAGE_NAME + ".AnnotationTestTypeChild", Kind.TYPE);
		AssociationOverrideAnnotation associationOverrideResource = (AssociationOverrideAnnotation) resourceType.getAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		
		assertEquals("address", javaAssociationOverride.getName());
		assertEquals("address", associationOverrideResource.getName());
		assertTrue(overrideContainer.getOverrides().iterator().hasNext());
		
		//set name in the resource model, verify context model updated
		associationOverrideResource.setName("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals("FOO", javaAssociationOverride.getName());
		assertEquals("FOO", associationOverrideResource.getName());
	
		//set name to null in the resource model
		associationOverrideResource.setName(null);
		getJpaProject().synchronizeContextModel();
		assertEquals(0, overrideContainer.getSpecifiedOverridesSize());
		assertNull(associationOverrideResource.getName());
		
		associationOverrideResource.setName("FOO");
		getJpaProject().synchronizeContextModel();
		assertEquals(1, overrideContainer.getSpecifiedOverridesSize());
		javaAssociationOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		assertEquals("FOO", javaAssociationOverride.getName());
		assertEquals("FOO", associationOverrideResource.getName());

		resourceType.removeAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		getJpaProject().synchronizeContextModel();
		assertFalse(overrideContainer.getSpecifiedOverrides().iterator().hasNext());
		assertFalse(resourceType.getAnnotations(JPA.ASSOCIATION_OVERRIDE).iterator().hasNext());
	}
	
	public void testModifyName() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = getJavaEntity();	
		JavaAssociationOverrideContainer overrideContainer = entity.getAssociationOverrideContainer();
		VirtualAssociationOverride virtualAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		AssociationOverride javaAssociationOverride = virtualAssociationOverride.convertToSpecified();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(PACKAGE_NAME + ".AnnotationTestTypeChild", Kind.TYPE);
		AssociationOverrideAnnotation associationOverrideResource = (AssociationOverrideAnnotation) resourceType.getAnnotation(0, JPA.ASSOCIATION_OVERRIDE);

		assertEquals("address", javaAssociationOverride.getName());
		assertEquals("address", associationOverrideResource.getName());
		assertTrue(overrideContainer.getOverrides().iterator().hasNext());
		
		//set name in the context model, verify resource model modified
		javaAssociationOverride.setName("foo");
		assertEquals("foo", javaAssociationOverride.getName());
		assertEquals("foo", associationOverrideResource.getName());
		
		//set name to null in the context model
		javaAssociationOverride.setName(null);
		assertNull(javaAssociationOverride.getName());
		associationOverrideResource = (AssociationOverrideAnnotation) resourceType.getAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		assertNull(associationOverrideResource.getName());
	}

	public void testAddSpecifiedJoinColumn() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = getJavaEntity();	
		JavaAssociationOverrideContainer overrideContainer = entity.getAssociationOverrideContainer();
		JavaVirtualAssociationOverride virtualOverride = overrideContainer.getVirtualOverrides().iterator().next();
		JavaAssociationOverride specifiedOverride = virtualOverride.convertToSpecified();
		JavaJoinColumnRelationshipStrategy joiningStrategy = specifiedOverride.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(PACKAGE_NAME + ".AnnotationTestTypeChild", Kind.TYPE);
		AssociationOverrideAnnotation associationOverrideAnnotation = (AssociationOverrideAnnotation) resourceType.getAnnotation(0, JPA.ASSOCIATION_OVERRIDE);

		
		JavaJoinColumn joinColumn1 = joiningStrategy.addSpecifiedJoinColumn(0);
		joinColumn1.setSpecifiedName("FOO");
				
		assertEquals("FOO", associationOverrideAnnotation.joinColumnAt(0).getName());
		
		JavaJoinColumn joinColumn2 = joiningStrategy.addSpecifiedJoinColumn(0);
		joinColumn2.setSpecifiedName("BAR");
		
		assertEquals("BAR", associationOverrideAnnotation.joinColumnAt(0).getName());
		assertEquals("FOO", associationOverrideAnnotation.joinColumnAt(1).getName());
		
		JavaJoinColumn joinColumn3 = joiningStrategy.addSpecifiedJoinColumn(1);
		joinColumn3.setSpecifiedName("BAZ");
		
		assertEquals(4, associationOverrideAnnotation.getJoinColumnsSize());
		assertEquals("BAR", associationOverrideAnnotation.joinColumnAt(0).getName());
		assertEquals("BAZ", associationOverrideAnnotation.joinColumnAt(1).getName());
		assertEquals("FOO", associationOverrideAnnotation.joinColumnAt(2).getName());
		assertEquals("address_id", associationOverrideAnnotation.joinColumnAt(3).getName());  // the old default join column
		
		assertEquals(4, joiningStrategy.getSpecifiedJoinColumnsSize());
		ListIterator<JavaJoinColumn> joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals(joinColumn2, joinColumns.next());
		assertEquals(joinColumn3, joinColumns.next());
		assertEquals(joinColumn1, joinColumns.next());
		
		joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("address_id", joinColumns.next().getName());  // the old default join column
	}
	
	public void testRemoveSpecifiedJoinColumn() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = getJavaEntity();	
		JavaAssociationOverrideContainer overrideContainer = entity.getAssociationOverrideContainer();
		JavaVirtualAssociationOverride javaAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		JavaAssociationOverride specifiedOverride = javaAssociationOverride.convertToSpecified();
		JavaJoinColumnRelationshipStrategy joiningStrategy = specifiedOverride.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(PACKAGE_NAME + ".AnnotationTestTypeChild", Kind.TYPE);
	AssociationOverrideAnnotation associationOverrideResource = (AssociationOverrideAnnotation) resourceType.getAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		
		assertEquals(1, associationOverrideResource.getJoinColumnsSize());

		joiningStrategy.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joiningStrategy.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joiningStrategy.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
		
		assertEquals(4, associationOverrideResource.getJoinColumnsSize());
		
		joiningStrategy.removeSpecifiedJoinColumn(0);
		assertEquals(3, associationOverrideResource.getJoinColumnsSize());
		assertEquals("BAR", associationOverrideResource.joinColumnAt(0).getName());
		assertEquals("BAZ", associationOverrideResource.joinColumnAt(1).getName());

		joiningStrategy.removeSpecifiedJoinColumn(0);
		assertEquals(2, associationOverrideResource.getJoinColumnsSize());
		assertEquals("BAZ", associationOverrideResource.joinColumnAt(0).getName());
		
		joiningStrategy.removeSpecifiedJoinColumn(0);
		assertEquals(1, associationOverrideResource.getJoinColumnsSize());
		
		joiningStrategy.removeSpecifiedJoinColumn(0);
		assertEquals(0, associationOverrideResource.getJoinColumnsSize());
	}
	
	public void testMoveSpecifiedJoinColumn() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = getJavaEntity();	
		JavaAssociationOverrideContainer overrideContainer = entity.getAssociationOverrideContainer();
		JavaVirtualAssociationOverride javaAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		JavaAssociationOverride specifiedOverride = javaAssociationOverride.convertToSpecified();
		JavaJoinColumnRelationshipStrategy joiningStrategy = specifiedOverride.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(PACKAGE_NAME + ".AnnotationTestTypeChild", Kind.TYPE);
		AssociationOverrideAnnotation associationOverrideResource = (AssociationOverrideAnnotation) resourceType.getAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		
		joiningStrategy.addSpecifiedJoinColumn(0).setSpecifiedName("FOO");
		joiningStrategy.addSpecifiedJoinColumn(1).setSpecifiedName("BAR");
		joiningStrategy.addSpecifiedJoinColumn(2).setSpecifiedName("BAZ");
				
		assertEquals(4, associationOverrideResource.getJoinColumnsSize());
		
		
		joiningStrategy.moveSpecifiedJoinColumn(2, 0);
		ListIterator<JavaJoinColumn> joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());

		assertEquals("BAR", associationOverrideResource.joinColumnAt(0).getName());
		assertEquals("BAZ", associationOverrideResource.joinColumnAt(1).getName());
		assertEquals("FOO", associationOverrideResource.joinColumnAt(2).getName());


		joiningStrategy.moveSpecifiedJoinColumn(0, 1);
		joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
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
		
		JavaEntity entity = getJavaEntity();	
		JavaAssociationOverrideContainer overrideContainer = entity.getAssociationOverrideContainer();
		JavaVirtualAssociationOverride javaAssociationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		JavaAssociationOverride specifiedOverride = javaAssociationOverride.convertToSpecified();
		JavaJoinColumnRelationshipStrategy joiningStrategy = specifiedOverride.getRelationship().getJoinColumnStrategy();
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(PACKAGE_NAME + ".AnnotationTestTypeChild", Kind.TYPE);
		AssociationOverrideAnnotation associationOverrideResource = (AssociationOverrideAnnotation) resourceType.getAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
	
		ListIterator<JavaJoinColumn> joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
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

		joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("address_id", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		associationOverrideResource.moveJoinColumn(2, 0);
		getJpaProject().synchronizeContextModel();
		joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("address_id", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		associationOverrideResource.moveJoinColumn(0, 1);
		getJpaProject().synchronizeContextModel();
		joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("BAR", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("address_id", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		associationOverrideResource.removeJoinColumn(1);
		getJpaProject().synchronizeContextModel();
		joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("FOO", joinColumns.next().getName());
		assertEquals("address_id", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());

		associationOverrideResource.removeJoinColumn(1);
		getJpaProject().synchronizeContextModel();
		joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals("BAZ", joinColumns.next().getName());
		assertEquals("address_id", joinColumns.next().getName());
		assertFalse(joinColumns.hasNext());
		
		associationOverrideResource.removeJoinColumn(0);
		getJpaProject().synchronizeContextModel();
		joinColumns = joiningStrategy.getSpecifiedJoinColumns().iterator();
		assertEquals("address_id", joinColumns.next().getName());

		associationOverrideResource.removeJoinColumn(0);
		getJpaProject().synchronizeContextModel();
		assertFalse(joiningStrategy.getSpecifiedJoinColumns().iterator().hasNext());
	}
	
	public void testGetName() throws Exception {
		createTestEntityWithAssociationOverride();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		AssociationOverride specifiedAssociationOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		assertEquals(ASSOCIATION_OVERRIDE_NAME, specifiedAssociationOverride.getName());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		AssociationOverrideAnnotation associationOverrideResource = (AssociationOverrideAnnotation) resourceType.getAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		
		associationOverrideResource.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedAssociationOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		assertEquals("FOO", specifiedAssociationOverride.getName());
	}
	
	public void testSetName() throws Exception {
		createTestEntityWithAssociationOverride();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaAssociationOverrideContainer overrideContainer = getJavaEntity().getAssociationOverrideContainer();
		AssociationOverride specifiedAssociationOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		assertEquals(ASSOCIATION_OVERRIDE_NAME, specifiedAssociationOverride.getName());
		
		specifiedAssociationOverride.setName("FOO");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		AssociationOverrideAnnotation associationOverrideResource = (AssociationOverrideAnnotation) resourceType.getAnnotation(0, JPA.ASSOCIATION_OVERRIDE);
		
		assertEquals("FOO", associationOverrideResource.getName());
	}

	public void testDefaultName() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = getJavaEntity();	
		JavaAssociationOverrideContainer overrideContainer = entity.getAssociationOverrideContainer();
		assertEquals("AnnotationTestTypeChild", entity.getName());
		assertEquals(1, overrideContainer.getVirtualOverridesSize());
		
		ReadOnlyAssociationOverride associationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("address", associationOverride.getName());
	}
	
	public void testIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaEntity entity = getJavaEntity();	
		JavaAssociationOverrideContainer overrideContainer = entity.getAssociationOverrideContainer();
		assertEquals("AnnotationTestTypeChild", entity.getName());
		assertEquals(1, overrideContainer.getVirtualOverridesSize());
		
		ReadOnlyAssociationOverride associationOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertTrue(associationOverride.isVirtual());
	}
}
