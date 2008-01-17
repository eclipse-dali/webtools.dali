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
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.context.base.IAssociationOverride;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.resource.java.AssociationOverride;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
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
		assertEquals(1, CollectionTools.size(entity.defaultAssociationOverrides()));
		
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
		assertEquals(1, CollectionTools.size(entity.defaultAssociationOverrides()));
		
		IAssociationOverride associationOverride = entity.defaultAssociationOverrides().next();
		assertTrue(associationOverride.isVirtual());
	}
}
