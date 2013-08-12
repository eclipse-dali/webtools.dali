/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.tests.internal.projects.JavaProjectTestHarness.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMultitenantType2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaMultitenancy2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.MultitenantAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.TenantDiscriminatorColumnAnnotation2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.MultitenantType2_3;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLink2_3ContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLink2_3JavaMultitenancyTests extends EclipseLink2_3ContextModelTestCase
{
	protected static final String SUB_TYPE_NAME = "AnnotationTestTypeChild";
	protected static final String FULLY_QUALIFIED_SUB_TYPE_NAME = PACKAGE_NAME + "." + SUB_TYPE_NAME;


	public EclipseLink2_3JavaMultitenancyTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}

	private ICompilationUnit createTestEntityWithMultitenant() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, EclipseLink.MULTITENANT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Multitenant");
			}
		});
	}

	private ICompilationUnit createTestEntityWithTenantDiscriminatorColumn() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, EclipseLink.MULTITENANT, EclipseLink.TENANT_DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@TenantDiscriminatorColumn(name=\"foo\")");
			}
		});
	}

	private ICompilationUnit createTestEntityWithTenantDiscriminatorColumns() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, EclipseLink.MULTITENANT, EclipseLink.TENANT_DISCRIMINATOR_COLUMN, EclipseLink.TENANT_DISCRIMINATOR_COLUMNS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@TenantDiscriminatorColumns({@TenantDiscriminatorColumn(name=\"foo\"), @TenantDiscriminatorColumn(name=\"bar\")})");
			}
		});
	}

	private ICompilationUnit createTestMultitenantMappedSuperclass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(
					JPA.MAPPED_SUPERCLASS,
					EclipseLink.MULTITENANT,
					EclipseLink.TENANT_DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass").append(CR);
				sb.append("@Multitenant").append(CR);
				sb.append("@TenantDiscriminatorColumn(name=\"MS_TENANT_ID\")").append(CR);
			}
		});
	}

	private ICompilationUnit createTestMultitenantRootEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(
					JPA.ENTITY,
					JPA.INHERITANCE,
					EclipseLink.MULTITENANT,
					EclipseLink.TENANT_DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@Inheritenace").append(CR);
				sb.append("@Multitenant").append(CR);
				sb.append("@TenantDiscriminatorColumn(name=\"ROOT_ENTITY_TENANT_ID\")").append(CR);
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
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
	}

	public EclipseLinkJavaMultitenancy2_3 getJavaMultitenancy() {
		return getJavaEntity().getMultitenancy();
	}

	public void testGetTypeNull() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(getJavaMultitenancy().getType());		
	}

	public void testGetType() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(EclipseLinkMultitenantType2_3.SINGLE_TABLE, getJavaMultitenancy().getType());		
	}

	public void testGetSpecifiedType() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(null, getJavaMultitenancy().getSpecifiedType());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		MultitenantAnnotation2_3 multitenant = (MultitenantAnnotation2_3) resourceType.getAnnotation(MultitenantAnnotation2_3.ANNOTATION_NAME);

		multitenant.setValue(MultitenantType2_3.SINGLE_TABLE);
		getJpaProject().synchronizeContextModel();

		assertEquals(EclipseLinkMultitenantType2_3.SINGLE_TABLE, getJavaMultitenancy().getSpecifiedType());

		multitenant.setValue(MultitenantType2_3.TABLE_PER_TENANT);
		getJpaProject().synchronizeContextModel();

		assertEquals(EclipseLinkMultitenantType2_3.TABLE_PER_TENANT, getJavaMultitenancy().getSpecifiedType());

		multitenant.setValue(MultitenantType2_3.VPD);
		getJpaProject().synchronizeContextModel();

		assertEquals(EclipseLinkMultitenantType2_3.VPD, getJavaMultitenancy().getSpecifiedType());

		multitenant.setValue(null);
		getJpaProject().synchronizeContextModel();

		assertNull(getJavaMultitenancy().getSpecifiedType());
	}

	public void testSetSpecifiedType() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(null, getJavaMultitenancy().getSpecifiedType());

		getJavaMultitenancy().setSpecifiedType(EclipseLinkMultitenantType2_3.SINGLE_TABLE);

		assertEquals(EclipseLinkMultitenantType2_3.SINGLE_TABLE, getJavaMultitenancy().getSpecifiedType());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		MultitenantAnnotation2_3 multitenant = (MultitenantAnnotation2_3) resourceType.getAnnotation(MultitenantAnnotation2_3.ANNOTATION_NAME);
		assertEquals(MultitenantType2_3.SINGLE_TABLE, multitenant.getValue());


		getJavaMultitenancy().setSpecifiedType(EclipseLinkMultitenantType2_3.TABLE_PER_TENANT);

		assertEquals(EclipseLinkMultitenantType2_3.TABLE_PER_TENANT, getJavaMultitenancy().getSpecifiedType());		
		assertEquals(MultitenantType2_3.TABLE_PER_TENANT, multitenant.getValue());

		getJavaMultitenancy().setSpecifiedType(EclipseLinkMultitenantType2_3.VPD);

		assertEquals(EclipseLinkMultitenantType2_3.VPD, getJavaMultitenancy().getSpecifiedType());		
		assertEquals(MultitenantType2_3.VPD, multitenant.getValue());
	}
	
	public void testIsIncludeCriteria() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(true, getJavaMultitenancy().isIncludeCriteria());

		getJavaMultitenancy().setSpecifiedIncludeCriteria(Boolean.FALSE);
		assertEquals(false, getJavaMultitenancy().isIncludeCriteria());
	}

	public void testGetSpecifiedIncludeCriteria() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(getJavaMultitenancy().getSpecifiedIncludeCriteria());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		MultitenantAnnotation2_3 multitenant = (MultitenantAnnotation2_3) resourceType.getAnnotation(MultitenantAnnotation2_3.ANNOTATION_NAME);
		multitenant.setIncludeCriteria(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();

		assertEquals(Boolean.FALSE, getJavaMultitenancy().getSpecifiedIncludeCriteria());

		multitenant.setIncludeCriteria(Boolean.TRUE);
		getJpaProject().synchronizeContextModel();

		assertEquals(Boolean.TRUE, getJavaMultitenancy().getSpecifiedIncludeCriteria());

		multitenant.setIncludeCriteria(null);
		getJpaProject().synchronizeContextModel();

		assertNull(getJavaMultitenancy().getSpecifiedIncludeCriteria());
		multitenant = (MultitenantAnnotation2_3) resourceType.getAnnotation(MultitenantAnnotation2_3.ANNOTATION_NAME);
		assertNotNull(multitenant);
	}

	public void testSetSpecifiedIncludeCriteria() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(getJavaMultitenancy().getSpecifiedIncludeCriteria());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		MultitenantAnnotation2_3 multitenant = (MultitenantAnnotation2_3) resourceType.getAnnotation(MultitenantAnnotation2_3.ANNOTATION_NAME);

		assertNull(multitenant.getIncludeCriteria());

		getJavaMultitenancy().setSpecifiedIncludeCriteria(Boolean.TRUE);
		assertEquals(Boolean.TRUE, multitenant.getIncludeCriteria());

		getJavaMultitenancy().setSpecifiedIncludeCriteria(null);
		multitenant = (MultitenantAnnotation2_3) resourceType.getAnnotation(MultitenantAnnotation2_3.ANNOTATION_NAME);
		assertNull(multitenant.getIncludeCriteria());
		assertNull(getJavaMultitenancy().getSpecifiedIncludeCriteria());
	}

	public void testSpecifiedTenantDiscriminatorColumn() throws Exception {
		createTestEntityWithTenantDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3> tenandDiscriminatorColumns = getJavaMultitenancy().getSpecifiedTenantDiscriminatorColumns().iterator();

		assertTrue(tenandDiscriminatorColumns.hasNext());
		assertEquals("foo", tenandDiscriminatorColumns.next().getName());
		assertFalse(tenandDiscriminatorColumns.hasNext());
	}

	public void testSpecifiedTenantDiscriminatorColumnsSize() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		EclipseLinkJavaMultitenancy2_3 javaMultitenancy = getJavaMultitenancy();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);

		assertEquals(0, javaMultitenancy.getSpecifiedTenantDiscriminatorColumnsSize());

		((TenantDiscriminatorColumnAnnotation2_3) resourceType.addAnnotation(0, TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME)).setName("FOO");
		((TenantDiscriminatorColumnAnnotation2_3) resourceType.addAnnotation(1, TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME)).setName("BAR");
		((TenantDiscriminatorColumnAnnotation2_3) resourceType.addAnnotation(2, TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME)).setName("BAZ");

		getJpaProject().synchronizeContextModel();
		assertEquals(3, javaMultitenancy.getSpecifiedTenantDiscriminatorColumnsSize());
	}

	public void testSpecifiedTenantDiscriminatorColumns() throws Exception {
		createTestEntityWithTenantDiscriminatorColumns();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3> tenandDiscriminatorColumns = getJavaMultitenancy().getSpecifiedTenantDiscriminatorColumns().iterator();

		assertTrue(tenandDiscriminatorColumns.hasNext());
		assertEquals("foo", tenandDiscriminatorColumns.next().getName());
		assertEquals("bar", tenandDiscriminatorColumns.next().getName());
		assertFalse(tenandDiscriminatorColumns.hasNext());
	}

	public void testAddSpecifiedTenantDiscriminatorColumn() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		getJavaMultitenancy().addSpecifiedTenantDiscriminatorColumn(0).setSpecifiedName("FOO");
		getJavaMultitenancy().addSpecifiedTenantDiscriminatorColumn(0).setSpecifiedName("BAR");
		getJavaMultitenancy().addSpecifiedTenantDiscriminatorColumn(0).setSpecifiedName("BAZ");

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		Iterator<NestableAnnotation> tenantDiscriminatorColumns = resourceType.getAnnotations(TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME).iterator();

		assertEquals("BAZ", ((TenantDiscriminatorColumnAnnotation2_3) tenantDiscriminatorColumns.next()).getName());
		assertEquals("BAR", ((TenantDiscriminatorColumnAnnotation2_3) tenantDiscriminatorColumns.next()).getName());
		assertEquals("FOO", ((TenantDiscriminatorColumnAnnotation2_3) tenantDiscriminatorColumns.next()).getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());
	}

	public void testAddSpecifiedTenantDiscriminatorColumn2() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		getJavaMultitenancy().addSpecifiedTenantDiscriminatorColumn(0).setSpecifiedName("FOO");
		getJavaMultitenancy().addSpecifiedTenantDiscriminatorColumn(1).setSpecifiedName("BAR");
		getJavaMultitenancy().addSpecifiedTenantDiscriminatorColumn(0).setSpecifiedName("BAZ");

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		Iterator<NestableAnnotation> tenantDiscriminatorColumns = resourceType.getAnnotations(TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME).iterator();

		assertEquals("BAZ", ((TenantDiscriminatorColumnAnnotation2_3) tenantDiscriminatorColumns.next()).getName());
		assertEquals("FOO", ((TenantDiscriminatorColumnAnnotation2_3) tenantDiscriminatorColumns.next()).getName());
		assertEquals("BAR", ((TenantDiscriminatorColumnAnnotation2_3) tenantDiscriminatorColumns.next()).getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());
	}

	public void testRemoveSpecifiedTenantDiscriminatorColumn() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		getJavaMultitenancy().addSpecifiedTenantDiscriminatorColumn(0).setSpecifiedName("FOO");
		getJavaMultitenancy().addSpecifiedTenantDiscriminatorColumn(1).setSpecifiedName("BAR");
		getJavaMultitenancy().addSpecifiedTenantDiscriminatorColumn(2).setSpecifiedName("BAZ");

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);

		assertEquals(3, resourceType.getAnnotationsSize(TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME));

		getJavaMultitenancy().removeSpecifiedTenantDiscriminatorColumn(1);

		Iterator<NestableAnnotation> tenantDiscriminatorColumnAnnotations = resourceType.getAnnotations(TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME).iterator();
		assertEquals("FOO", ((TenantDiscriminatorColumnAnnotation2_3) tenantDiscriminatorColumnAnnotations.next()).getName());		
		assertEquals("BAZ", ((TenantDiscriminatorColumnAnnotation2_3) tenantDiscriminatorColumnAnnotations.next()).getName());
		assertFalse(tenantDiscriminatorColumnAnnotations.hasNext());

		Iterator<EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3> tenantDiscriminatorColumns = getJavaMultitenancy().getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());		
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());


		getJavaMultitenancy().removeSpecifiedTenantDiscriminatorColumn(1);
		tenantDiscriminatorColumnAnnotations = resourceType.getAnnotations(TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME).iterator();
		assertEquals("FOO", ((TenantDiscriminatorColumnAnnotation2_3) tenantDiscriminatorColumnAnnotations.next()).getName());		
		assertFalse(tenantDiscriminatorColumnAnnotations.hasNext());

		tenantDiscriminatorColumns = getJavaMultitenancy().getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());


		getJavaMultitenancy().removeSpecifiedTenantDiscriminatorColumn(0);
		tenantDiscriminatorColumnAnnotations = resourceType.getAnnotations(TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME).iterator();
		assertFalse(tenantDiscriminatorColumnAnnotations.hasNext());
		tenantDiscriminatorColumns = getJavaMultitenancy().getSpecifiedTenantDiscriminatorColumns().iterator();
		assertFalse(tenantDiscriminatorColumns.hasNext());

		assertNull(resourceType.getAnnotation(TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME));
	}

	public void testMoveSpecifiedTenantDiscriminatorColumn() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		EclipseLinkJavaMultitenancy2_3 javaMultitenancy = getJavaMultitenancy();	
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);

		javaMultitenancy.addSpecifiedTenantDiscriminatorColumn(0).setSpecifiedName("FOO");
		javaMultitenancy.addSpecifiedTenantDiscriminatorColumn(1).setSpecifiedName("BAR");
		javaMultitenancy.addSpecifiedTenantDiscriminatorColumn(2).setSpecifiedName("BAZ");

		Iterator<NestableAnnotation> javaTenantDiscriminatorColumns = resourceType.getAnnotations(TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME).iterator();
		assertEquals(3, IteratorTools.size(javaTenantDiscriminatorColumns));


		javaMultitenancy.moveSpecifiedTenantDiscriminatorColumn(2, 0);
		ListIterator<EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3> tenantDiscriminatorColumns = javaMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAR", tenantDiscriminatorColumns.next().getSpecifiedName());
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getSpecifiedName());
		assertEquals("FOO", tenantDiscriminatorColumns.next().getSpecifiedName());

		javaTenantDiscriminatorColumns = resourceType.getAnnotations(TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME).iterator();
		assertEquals("BAR", ((TenantDiscriminatorColumnAnnotation2_3) javaTenantDiscriminatorColumns.next()).getName());
		assertEquals("BAZ", ((TenantDiscriminatorColumnAnnotation2_3) javaTenantDiscriminatorColumns.next()).getName());
		assertEquals("FOO", ((TenantDiscriminatorColumnAnnotation2_3) javaTenantDiscriminatorColumns.next()).getName());


		javaMultitenancy.moveSpecifiedTenantDiscriminatorColumn(0, 1);
		tenantDiscriminatorColumns = javaMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getSpecifiedName());
		assertEquals("BAR", tenantDiscriminatorColumns.next().getSpecifiedName());
		assertEquals("FOO", tenantDiscriminatorColumns.next().getSpecifiedName());

		javaTenantDiscriminatorColumns = resourceType.getAnnotations(TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME).iterator();
		assertEquals("BAZ", ((TenantDiscriminatorColumnAnnotation2_3) javaTenantDiscriminatorColumns.next()).getName());
		assertEquals("BAR", ((TenantDiscriminatorColumnAnnotation2_3) javaTenantDiscriminatorColumns.next()).getName());
		assertEquals("FOO", ((TenantDiscriminatorColumnAnnotation2_3) javaTenantDiscriminatorColumns.next()).getName());
	}

	public void testUpdateSpecifiedTenantDiscriminatorColumns() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		EclipseLinkJavaMultitenancy2_3 javaMultitenancy = getJavaMultitenancy();	
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);

		((TenantDiscriminatorColumnAnnotation2_3) resourceType.addAnnotation(0, TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME)).setName("FOO");
		((TenantDiscriminatorColumnAnnotation2_3) resourceType.addAnnotation(1, TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME)).setName("BAR");
		((TenantDiscriminatorColumnAnnotation2_3) resourceType.addAnnotation(2, TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();

		ListIterator<EclipseLinkJavaSpecifiedTenantDiscriminatorColumn2_3> tenantDiscriminatorColumns = javaMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());
		assertEquals("BAR", tenantDiscriminatorColumns.next().getName());
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());

		resourceType.moveAnnotation(2, 0, TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		tenantDiscriminatorColumns = javaMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAR", tenantDiscriminatorColumns.next().getName());
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());

		resourceType.moveAnnotation(0, 1, TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		tenantDiscriminatorColumns = javaMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertEquals("BAR", tenantDiscriminatorColumns.next().getName());
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());

		resourceType.removeAnnotation(1,  TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		tenantDiscriminatorColumns = javaMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());

		resourceType.removeAnnotation(1,  TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		tenantDiscriminatorColumns = javaMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());

		resourceType.removeAnnotation(0,  TenantDiscriminatorColumnAnnotation2_3.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		tenantDiscriminatorColumns = javaMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertFalse(tenantDiscriminatorColumns.hasNext());
	}

	//MappedSuperclass specifies multitenancy, all subclass entities are multitenant by default.
	public void testTenantDiscriminatorColumnsWithMappedSuperclass() throws Exception {
		createTestMultitenantMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		EclipseLinkJavaMultitenancy2_3 multitenancy = getJavaMultitenancy();
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("MS_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());

		//if the java entity specifies multitenant, then it does not get the tenant discriminator columns from the mapped superclass
		multitenancy.setSpecifiedType(EclipseLinkMultitenantType2_3.SINGLE_TABLE);
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());
	}

	//Root entity specifies multitenancy and SINGLE_TABLE inheritance, all subclass entities are multitenant by default.
	public void testTenantDiscriminatorColumnsWithInheritance() throws Exception {
		createTestMultitenantRootEntity();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		EclipseLinkJavaMultitenancy2_3 multitenancy = getJavaMultitenancy();
		assertTrue(multitenancy.isMultitenant()); //multitenant by default from root entity
		assertFalse(multitenancy.isSpecifiedMultitenant());
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("ROOT_ENTITY_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());

		getJavaEntity().getRootEntity().setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		assertTrue(multitenancy.isMultitenant()); //multitenant by default from root entity
		assertFalse(multitenancy.isSpecifiedMultitenant());
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("ROOT_ENTITY_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());

		getJavaEntity().getRootEntity().setSpecifiedInheritanceStrategy(InheritanceType.TABLE_PER_CLASS);
		assertFalse(multitenancy.isMultitenant()); //not multitenant since inheritance strategy is table per class
		assertFalse(multitenancy.isSpecifiedMultitenant());
		assertEquals(0, multitenancy.getTenantDiscriminatorColumnsSize());

		EclipseLinkPersistenceUnitDefaults persistenceUnitDefaults = (EclipseLinkPersistenceUnitDefaults) getMappingFile().getRoot().getPersistenceUnitMetadata().getPersistenceUnitDefaults();
		persistenceUnitDefaults.addTenantDiscriminatorColumn().setSpecifiedName("PU_TENANT_ID");
		assertFalse(multitenancy.isMultitenant()); //not multitenant since inheritance strategy is table per class
		assertFalse(multitenancy.isSpecifiedMultitenant());
		assertEquals(0, multitenancy.getTenantDiscriminatorColumnsSize());

		//get the default tenant discriminator column from the persistence unit defaults instead of the root entity since inheritance strategy is table per class
		multitenancy.setSpecifiedMultitenant(true);
		assertTrue(multitenancy.isSpecifiedMultitenant());
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("PU_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());		

		multitenancy.addSpecifiedTenantDiscriminatorColumn().setSpecifiedName("CHILD_TENANT_ID");
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("CHILD_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());		

		getJavaEntity().getRootEntity().setSpecifiedInheritanceStrategy(InheritanceType.JOINED);
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("CHILD_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());		
	}

	public void testTenantDiscriminatorColumnsWithPersistenceUnitDefaults() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		EclipseLinkPersistenceUnitDefaults persistenceUnitDefaults = (EclipseLinkPersistenceUnitDefaults) getMappingFile().getRoot().getPersistenceUnitMetadata().getPersistenceUnitDefaults();
		persistenceUnitDefaults.addTenantDiscriminatorColumn().setSpecifiedName("PU_TENANT_ID");

		EclipseLinkJavaMultitenancy2_3 multitenancy = getJavaMultitenancy();
		assertFalse(multitenancy.isMultitenant());
		assertEquals(0, multitenancy.getTenantDiscriminatorColumnsSize());

		multitenancy.setSpecifiedType(EclipseLinkMultitenantType2_3.SINGLE_TABLE);
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("PU_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());

		//if there are specified tenant discriminator columns then there should not be any default ones
		multitenancy.addSpecifiedTenantDiscriminatorColumn().setSpecifiedName("ENTITY_TENANT_ID");
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals(0, multitenancy.getDefaultTenantDiscriminatorColumnsSize());
		assertEquals("ENTITY_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());

		//if java entity is not multitenant than there are no tenant discriminator columns
		getJavaMultitenancy().setSpecifiedMultitenant(false);
		assertEquals(0, multitenancy.getTenantDiscriminatorColumnsSize());
	}

	public void testTenantDiscriminatorColumnsWithEntityMappingsDefaults() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		EclipseLinkEntityMappings entityMappings = (EclipseLinkEntityMappings) getMappingFile().getRoot();
		entityMappings.addSpecifiedTenantDiscriminatorColumn().setSpecifiedName("EM_TENANT_ID");

		EclipseLinkJavaMultitenancy2_3 multitenancy = getJavaMultitenancy();
		assertFalse(multitenancy.isMultitenant());
		assertEquals(0, multitenancy.getTenantDiscriminatorColumnsSize());

		//entity is not listed in the orm.xml file, so does not get default from entity mappings
		multitenancy.setSpecifiedType(EclipseLinkMultitenantType2_3.SINGLE_TABLE);
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());

		OrmPersistentType persistentType = entityMappings.addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);

		multitenancy = ((EclipseLinkJavaEntity) persistentType.getJavaPersistentType().getMapping()).getMultitenancy();

		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("EM_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());

		//if java entity is not SINGLE_TABLE multitenant than there are no default tenant discriminator columns
		multitenancy.setSpecifiedType(EclipseLinkMultitenantType2_3.TABLE_PER_TENANT);
		assertEquals(0, multitenancy.getTenantDiscriminatorColumnsSize());

		//if java entity is not SINGLE_TABLE multitenant than there are no default tenant discriminator columns
		multitenancy.setSpecifiedMultitenant(false);
		assertEquals(0, multitenancy.getTenantDiscriminatorColumnsSize());


		EclipseLinkPersistenceUnitDefaults persistenceUnitDefaults = (EclipseLinkPersistenceUnitDefaults) getMappingFile().getRoot().getPersistenceUnitMetadata().getPersistenceUnitDefaults();
		persistenceUnitDefaults.addTenantDiscriminatorColumn().setSpecifiedName("PU_TENANT_ID");

		multitenancy.setSpecifiedType(EclipseLinkMultitenantType2_3.SINGLE_TABLE);
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("EM_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());

		entityMappings.removeManagedType(0);
		multitenancy = getJavaMultitenancy();
		assertEquals(1, multitenancy.getTenantDiscriminatorColumnsSize());
		assertEquals("PU_TENANT_ID", multitenancy.getTenantDiscriminatorColumns().iterator().next().getName());
	}

}
