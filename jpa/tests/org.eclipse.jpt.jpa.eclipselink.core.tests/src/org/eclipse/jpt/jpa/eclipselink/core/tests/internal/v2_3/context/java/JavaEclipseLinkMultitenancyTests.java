/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_3.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_3.context.EclipseLink2_3ContextModelTestCase;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.EclipseLinkMultitenantType;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.java.JavaEclipseLinkMultitenancy;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.java.JavaTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLink2_3;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLinkMultitenantAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLinkTenantDiscriminatorColumnAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.MultitenantType;

@SuppressWarnings("nls")
public class JavaEclipseLinkMultitenancyTests extends EclipseLink2_3ContextModelTestCase
{
	protected static final String SUB_TYPE_NAME = "AnnotationTestTypeChild";
	protected static final String FULLY_QUALIFIED_SUB_TYPE_NAME = PACKAGE_NAME + "." + SUB_TYPE_NAME;


	public JavaEclipseLinkMultitenancyTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
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
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLink2_3.MULTITENANT);
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
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLink2_3.MULTITENANT, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);
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
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLink2_3.MULTITENANT, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMNS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@TenantDiscriminatorColumns({@TenantDiscriminatorColumn(name=\"foo\"), @TenantDiscriminatorColumn(name=\"bar\")})");
			}
		});
	}

	public JavaEclipseLinkMultitenancy getJavaMultitenancy() {
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

		assertEquals(EclipseLinkMultitenantType.SINGLE_TABLE, getJavaMultitenancy().getType());		
	}

	public void testGetSpecifiedType() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertEquals(null, getJavaMultitenancy().getSpecifiedType());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		EclipseLinkMultitenantAnnotation multitenant = (EclipseLinkMultitenantAnnotation) resourceType.getAnnotation(EclipseLinkMultitenantAnnotation.ANNOTATION_NAME);

		multitenant.setValue(MultitenantType.SINGLE_TABLE);
		getJpaProject().synchronizeContextModel();

		assertEquals(EclipseLinkMultitenantType.SINGLE_TABLE, getJavaMultitenancy().getSpecifiedType());

		multitenant.setValue(MultitenantType.TABLE_PER_TENANT);
		getJpaProject().synchronizeContextModel();

		assertEquals(EclipseLinkMultitenantType.TABLE_PER_TENANT, getJavaMultitenancy().getSpecifiedType());

		multitenant.setValue(null);
		getJpaProject().synchronizeContextModel();

		assertNull(getJavaMultitenancy().getSpecifiedType());
	}

	public void testSetSpecifiedType() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(null, getJavaMultitenancy().getSpecifiedType());

		getJavaMultitenancy().setSpecifiedType(EclipseLinkMultitenantType.SINGLE_TABLE);

		assertEquals(EclipseLinkMultitenantType.SINGLE_TABLE, getJavaMultitenancy().getSpecifiedType());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		EclipseLinkMultitenantAnnotation multitenant = (EclipseLinkMultitenantAnnotation) resourceType.getAnnotation(EclipseLinkMultitenantAnnotation.ANNOTATION_NAME);
		assertEquals(MultitenantType.SINGLE_TABLE, multitenant.getValue());


		getJavaMultitenancy().setSpecifiedType(EclipseLinkMultitenantType.TABLE_PER_TENANT);

		assertEquals(EclipseLinkMultitenantType.TABLE_PER_TENANT, getJavaMultitenancy().getSpecifiedType());		
		assertEquals(MultitenantType.TABLE_PER_TENANT, multitenant.getValue());
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

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		EclipseLinkMultitenantAnnotation multitenant = (EclipseLinkMultitenantAnnotation) resourceType.getAnnotation(EclipseLinkMultitenantAnnotation.ANNOTATION_NAME);
		multitenant.setIncludeCriteria(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();

		assertEquals(Boolean.FALSE, getJavaMultitenancy().getSpecifiedIncludeCriteria());

		multitenant.setIncludeCriteria(Boolean.TRUE);
		getJpaProject().synchronizeContextModel();

		assertEquals(Boolean.TRUE, getJavaMultitenancy().getSpecifiedIncludeCriteria());

		multitenant.setIncludeCriteria(null);
		getJpaProject().synchronizeContextModel();

		assertNull(getJavaMultitenancy().getSpecifiedIncludeCriteria());
		multitenant = (EclipseLinkMultitenantAnnotation) resourceType.getAnnotation(EclipseLinkMultitenantAnnotation.ANNOTATION_NAME);
		assertNotNull(multitenant);
	}

	public void testSetSpecifiedIncludeCriteria() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		assertNull(getJavaMultitenancy().getSpecifiedIncludeCriteria());

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		EclipseLinkMultitenantAnnotation multitenant = (EclipseLinkMultitenantAnnotation) resourceType.getAnnotation(EclipseLinkMultitenantAnnotation.ANNOTATION_NAME);

		assertNull(multitenant.getIncludeCriteria());

		getJavaMultitenancy().setSpecifiedIncludeCriteria(Boolean.TRUE);
		assertEquals(Boolean.TRUE, multitenant.getIncludeCriteria());

		getJavaMultitenancy().setSpecifiedIncludeCriteria(null);
		multitenant = (EclipseLinkMultitenantAnnotation) resourceType.getAnnotation(EclipseLinkMultitenantAnnotation.ANNOTATION_NAME);
		assertNull(multitenant.getIncludeCriteria());
		assertNull(getJavaMultitenancy().getSpecifiedIncludeCriteria());
	}

	public void testTenantDiscriminatorColumns() throws Exception {
		createTestEntityWithTenantDiscriminatorColumn();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaTenantDiscriminatorColumn> tenandDiscriminatorColumns = getJavaMultitenancy().getSpecifiedTenantDiscriminatorColumns().iterator();

		assertTrue(tenandDiscriminatorColumns.hasNext());
		assertEquals("foo", tenandDiscriminatorColumns.next().getName());
		assertFalse(tenandDiscriminatorColumns.hasNext());
	}

	public void testSpecifiedTenantDiscriminatorColumnsSize() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEclipseLinkMultitenancy javaMultitenancy = getJavaMultitenancy();		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		assertEquals(0, javaMultitenancy.getSpecifiedTenantDiscriminatorColumnsSize());

		((EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.addAnnotation(0, EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME)).setName("FOO");
		((EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.addAnnotation(1, EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME)).setName("BAR");
		((EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.addAnnotation(2, EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME)).setName("BAZ");

		getJpaProject().synchronizeContextModel();
		assertEquals(3, javaMultitenancy.getSpecifiedTenantDiscriminatorColumnsSize());
	}

	public void testSpecifiedTenantDiscriminatorColumns() throws Exception {
		createTestEntityWithTenantDiscriminatorColumns();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		ListIterator<JavaTenantDiscriminatorColumn> tenandDiscriminatorColumns = getJavaMultitenancy().getSpecifiedTenantDiscriminatorColumns().iterator();

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

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		Iterator<NestableAnnotation> tenantDiscriminatorColumns = resourceType.getAnnotations(EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME).iterator();

		assertEquals("BAZ", ((EclipseLinkTenantDiscriminatorColumnAnnotation) tenantDiscriminatorColumns.next()).getName());
		assertEquals("BAR", ((EclipseLinkTenantDiscriminatorColumnAnnotation) tenantDiscriminatorColumns.next()).getName());
		assertEquals("FOO", ((EclipseLinkTenantDiscriminatorColumnAnnotation) tenantDiscriminatorColumns.next()).getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());
	}

	public void testAddSpecifiedTenantDiscriminatorColumn2() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		getJavaMultitenancy().addSpecifiedTenantDiscriminatorColumn(0).setSpecifiedName("FOO");
		getJavaMultitenancy().addSpecifiedTenantDiscriminatorColumn(1).setSpecifiedName("BAR");
		getJavaMultitenancy().addSpecifiedTenantDiscriminatorColumn(0).setSpecifiedName("BAZ");

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		Iterator<NestableAnnotation> tenantDiscriminatorColumns = resourceType.getAnnotations(EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME).iterator();

		assertEquals("BAZ", ((EclipseLinkTenantDiscriminatorColumnAnnotation) tenantDiscriminatorColumns.next()).getName());
		assertEquals("FOO", ((EclipseLinkTenantDiscriminatorColumnAnnotation) tenantDiscriminatorColumns.next()).getName());
		assertEquals("BAR", ((EclipseLinkTenantDiscriminatorColumnAnnotation) tenantDiscriminatorColumns.next()).getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());
	}

	public void testRemoveSpecifiedTenantDiscriminatorColumn() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		getJavaMultitenancy().addSpecifiedTenantDiscriminatorColumn(0).setSpecifiedName("FOO");
		getJavaMultitenancy().addSpecifiedTenantDiscriminatorColumn(1).setSpecifiedName("BAR");
		getJavaMultitenancy().addSpecifiedTenantDiscriminatorColumn(2).setSpecifiedName("BAZ");

		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		assertEquals(3, resourceType.getAnnotationsSize(EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME));

		getJavaMultitenancy().removeSpecifiedTenantDiscriminatorColumn(1);

		Iterator<NestableAnnotation> tenantDiscriminatorColumnAnnotations = resourceType.getAnnotations(EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("FOO", ((EclipseLinkTenantDiscriminatorColumnAnnotation) tenantDiscriminatorColumnAnnotations.next()).getName());		
		assertEquals("BAZ", ((EclipseLinkTenantDiscriminatorColumnAnnotation) tenantDiscriminatorColumnAnnotations.next()).getName());
		assertFalse(tenantDiscriminatorColumnAnnotations.hasNext());

		Iterator<JavaTenantDiscriminatorColumn> tenantDiscriminatorColumns = getJavaMultitenancy().getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());		
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());


		getJavaMultitenancy().removeSpecifiedTenantDiscriminatorColumn(1);
		tenantDiscriminatorColumnAnnotations = resourceType.getAnnotations(EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("FOO", ((EclipseLinkTenantDiscriminatorColumnAnnotation) tenantDiscriminatorColumnAnnotations.next()).getName());		
		assertFalse(tenantDiscriminatorColumnAnnotations.hasNext());

		tenantDiscriminatorColumns = getJavaMultitenancy().getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());


		getJavaMultitenancy().removeSpecifiedTenantDiscriminatorColumn(0);
		tenantDiscriminatorColumnAnnotations = resourceType.getAnnotations(EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME).iterator();
		assertFalse(tenantDiscriminatorColumnAnnotations.hasNext());
		tenantDiscriminatorColumns = getJavaMultitenancy().getSpecifiedTenantDiscriminatorColumns().iterator();
		assertFalse(tenantDiscriminatorColumns.hasNext());

		assertNull(resourceType.getAnnotation(EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME));
	}

	public void testMoveSpecifiedTenantDiscriminatorColumn() throws Exception {
		createTestEntityWithMultitenant();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEclipseLinkMultitenancy javaMultitenancy = getJavaMultitenancy();	
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		javaMultitenancy.addSpecifiedTenantDiscriminatorColumn(0).setSpecifiedName("FOO");
		javaMultitenancy.addSpecifiedTenantDiscriminatorColumn(1).setSpecifiedName("BAR");
		javaMultitenancy.addSpecifiedTenantDiscriminatorColumn(2).setSpecifiedName("BAZ");

		Iterator<NestableAnnotation> javaTenantDiscriminatorColumns = resourceType.getAnnotations(EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME).iterator();
		assertEquals(3, CollectionTools.size(javaTenantDiscriminatorColumns));


		javaMultitenancy.moveSpecifiedTenantDiscriminatorColumn(2, 0);
		ListIterator<JavaTenantDiscriminatorColumn> tenantDiscriminatorColumns = javaMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAR", tenantDiscriminatorColumns.next().getSpecifiedName());
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getSpecifiedName());
		assertEquals("FOO", tenantDiscriminatorColumns.next().getSpecifiedName());

		javaTenantDiscriminatorColumns = resourceType.getAnnotations(EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAR", ((EclipseLinkTenantDiscriminatorColumnAnnotation) javaTenantDiscriminatorColumns.next()).getName());
		assertEquals("BAZ", ((EclipseLinkTenantDiscriminatorColumnAnnotation) javaTenantDiscriminatorColumns.next()).getName());
		assertEquals("FOO", ((EclipseLinkTenantDiscriminatorColumnAnnotation) javaTenantDiscriminatorColumns.next()).getName());


		javaMultitenancy.moveSpecifiedTenantDiscriminatorColumn(0, 1);
		tenantDiscriminatorColumns = javaMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getSpecifiedName());
		assertEquals("BAR", tenantDiscriminatorColumns.next().getSpecifiedName());
		assertEquals("FOO", tenantDiscriminatorColumns.next().getSpecifiedName());

		javaTenantDiscriminatorColumns = resourceType.getAnnotations(EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME).iterator();
		assertEquals("BAZ", ((EclipseLinkTenantDiscriminatorColumnAnnotation) javaTenantDiscriminatorColumns.next()).getName());
		assertEquals("BAR", ((EclipseLinkTenantDiscriminatorColumnAnnotation) javaTenantDiscriminatorColumns.next()).getName());
		assertEquals("FOO", ((EclipseLinkTenantDiscriminatorColumnAnnotation) javaTenantDiscriminatorColumns.next()).getName());
	}

	public void testUpdateSpecifiedSTenantDiscriminatorColumns() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaEclipseLinkMultitenancy javaMultitenancy = getJavaMultitenancy();	
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);

		((EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.addAnnotation(0, EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME)).setName("FOO");
		((EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.addAnnotation(1, EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME)).setName("BAR");
		((EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.addAnnotation(2, EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME)).setName("BAZ");
		getJpaProject().synchronizeContextModel();

		ListIterator<JavaTenantDiscriminatorColumn> tenantDiscriminatorColumns = javaMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());
		assertEquals("BAR", tenantDiscriminatorColumns.next().getName());
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());

		resourceType.moveAnnotation(2, 0, EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		tenantDiscriminatorColumns = javaMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAR", tenantDiscriminatorColumns.next().getName());
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());

		resourceType.moveAnnotation(0, 1, EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		tenantDiscriminatorColumns = javaMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertEquals("BAR", tenantDiscriminatorColumns.next().getName());
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());

		resourceType.removeAnnotation(1,  EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		tenantDiscriminatorColumns = javaMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertEquals("FOO", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());

		resourceType.removeAnnotation(1,  EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		tenantDiscriminatorColumns = javaMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertEquals("BAZ", tenantDiscriminatorColumns.next().getName());
		assertFalse(tenantDiscriminatorColumns.hasNext());

		resourceType.removeAnnotation(0,  EclipseLinkTenantDiscriminatorColumnAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		tenantDiscriminatorColumns = javaMultitenancy.getSpecifiedTenantDiscriminatorColumns().iterator();
		assertFalse(tenantDiscriminatorColumns.hasNext());
	}
}
