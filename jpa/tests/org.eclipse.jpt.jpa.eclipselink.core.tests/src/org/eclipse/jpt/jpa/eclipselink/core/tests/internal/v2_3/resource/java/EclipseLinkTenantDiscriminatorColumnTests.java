/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.v2_3.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLink2_3;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java.EclipseLinkTenantDiscriminatorColumnAnnotation;

@SuppressWarnings("nls")
public class EclipseLinkTenantDiscriminatorColumnTests extends EclipseLink2_3JavaResourceModelTestCase {
	
	private static final String COLUMN_NAME = "MY_COLUMN";
	private static final String COLUMN_COLUMN_DEFINITION = "COLUMN_DEFINITION";
	private static final String COLUMN_CONTEXT_PROPERTY = "eclipselink.foo-id";
	private static final String COLUMN_TABLE = "MY_TABLE";

	public EclipseLinkTenantDiscriminatorColumnTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTestTenantDiscriminatorColumn() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@TenantDiscriminatorColumn");
			}
		});
	}
	
	private ICompilationUnit createTestTenantDiscriminatorColumnWithName() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@TenantDiscriminatorColumn(name = \"" + COLUMN_NAME + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestTenantDiscriminatorColumnWithColumnDefinition() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@TenantDiscriminatorColumn(columnDefinition = \"" + COLUMN_COLUMN_DEFINITION + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestTenantDiscriminatorColumnWithContextProperty() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@TenantDiscriminatorColumn(contextProperty = \"" + COLUMN_CONTEXT_PROPERTY + "\")");
			}
		});
	}
	
	private ICompilationUnit createTestTenantDiscriminatorColumnWithTable() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@TenantDiscriminatorColumn(table = \"" + COLUMN_TABLE + "\")");
			}
		});
	}

	private ICompilationUnit createTestTenenatDiscriminatorColumnWithDiscriminatorType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN, JPA.DISCRIMINATOR_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@TenantDiscriminatorColumn(discriminatorType = DiscriminatorType.CHAR)");
			}
		});
	}
	
	private ICompilationUnit createTestColumnWithIntElement(final String intElement) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@TenantDiscriminatorColumn(" + intElement + " = 5)");
			}
		});
	}
	
	private ICompilationUnit createTestColumnWithBooleanElement(final String booleanElement) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@TenantDiscriminatorColumn(" + booleanElement + " = true)");
			}
		});
	}

	public void testGetName() throws Exception {
		ICompilationUnit cu = this.createTestTenantDiscriminatorColumnWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		EclipseLinkTenantDiscriminatorColumnAnnotation column = (EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.getAnnotation(0, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);
		assertNotNull(column);
		assertEquals(COLUMN_NAME, column.getName());
	}

	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestTenantDiscriminatorColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		EclipseLinkTenantDiscriminatorColumnAnnotation column = (EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.getAnnotation(0, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);
		assertNotNull(column);
		assertNull(column.getName());
		assertNull(column.getDiscriminatorType());
		assertNull(column.getColumnDefinition());
	}

	public void testSetName() throws Exception {
		ICompilationUnit cu = this.createTestTenantDiscriminatorColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		EclipseLinkTenantDiscriminatorColumnAnnotation column = (EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.getAnnotation(0, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);

		assertNotNull(column);
		assertNull(column.getName());

		column.setName("Foo");
		assertEquals("Foo", column.getName());
		
		assertSourceContains("@TenantDiscriminatorColumn(name = \"Foo\")", cu);
	}
	
	public void testSetNameNull() throws Exception {
		ICompilationUnit cu = this.createTestTenantDiscriminatorColumnWithName();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		EclipseLinkTenantDiscriminatorColumnAnnotation column = (EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.getAnnotation(0, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);

		assertEquals(COLUMN_NAME, column.getName());
		
		column.setName(null);
		assertNull(column.getName());
		
		assertSourceDoesNotContain("(name", cu);
	}

	public void testGetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestTenantDiscriminatorColumnWithColumnDefinition();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		EclipseLinkTenantDiscriminatorColumnAnnotation column = (EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.getAnnotation(0, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);
		assertEquals(COLUMN_COLUMN_DEFINITION, column.getColumnDefinition());
	}

	public void testSetColumnDefinition() throws Exception {
		ICompilationUnit cu = this.createTestTenantDiscriminatorColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		EclipseLinkTenantDiscriminatorColumnAnnotation column = (EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.getAnnotation(0, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);

		assertNotNull(column);
		assertNull(column.getColumnDefinition());

		column.setColumnDefinition("Foo");
		assertEquals("Foo", column.getColumnDefinition());
		
		assertSourceContains("@TenantDiscriminatorColumn(columnDefinition = \"Foo\")", cu);

		
		column.setColumnDefinition(null);
		assertSourceDoesNotContain("(columnDefinition", cu);
	}

	public void testGetLength() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithIntElement("length");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		EclipseLinkTenantDiscriminatorColumnAnnotation column = (EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.getAnnotation(0, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);

		assertEquals(Integer.valueOf(5), column.getLength());
	}
	
	public void testSetLength() throws Exception {
		ICompilationUnit cu = this.createTestTenantDiscriminatorColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		EclipseLinkTenantDiscriminatorColumnAnnotation column = (EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.getAnnotation(0, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);

		assertNotNull(column);
		assertNull(column.getLength());

		column.setLength(Integer.valueOf(5));
		assertEquals(Integer.valueOf(5), column.getLength());
		
		assertSourceContains("@TenantDiscriminatorColumn(length = 5)", cu);
		
		column.setLength(null);
		assertSourceDoesNotContain("(length", cu);
	}
	
	public void testGetDiscriminatorType() throws Exception {
		ICompilationUnit cu = this.createTestTenenatDiscriminatorColumnWithDiscriminatorType();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		EclipseLinkTenantDiscriminatorColumnAnnotation column = (EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.getAnnotation(0, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);
		assertEquals(DiscriminatorType.CHAR, column.getDiscriminatorType());
	}
	
	public void testSetDiscriminatorType() throws Exception {
		ICompilationUnit cu = this.createTestTenantDiscriminatorColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		EclipseLinkTenantDiscriminatorColumnAnnotation column = (EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.getAnnotation(0, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);

		assertNull(column.getDiscriminatorType());

		column.setDiscriminatorType(DiscriminatorType.INTEGER);
		assertEquals(DiscriminatorType.INTEGER, column.getDiscriminatorType());
		
		assertSourceContains("@TenantDiscriminatorColumn(discriminatorType = INTEGER)", cu);
		
		column.setDiscriminatorType(null);
		assertSourceDoesNotContain("(discriminatorType", cu);
	}

	public void testGetContextProperty() throws Exception {
		ICompilationUnit cu = this.createTestTenantDiscriminatorColumnWithContextProperty();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		EclipseLinkTenantDiscriminatorColumnAnnotation column = (EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.getAnnotation(0, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);
		assertEquals(COLUMN_CONTEXT_PROPERTY, column.getContextProperty());
	}

	public void testSetContextProperty() throws Exception {
		ICompilationUnit cu = this.createTestTenantDiscriminatorColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		EclipseLinkTenantDiscriminatorColumnAnnotation column = (EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.getAnnotation(0, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);

		assertNotNull(column);
		assertNull(column.getContextProperty());

		column.setContextProperty("Foo");
		assertEquals("Foo", column.getContextProperty());
		
		assertSourceContains("@TenantDiscriminatorColumn(contextProperty = \"Foo\")", cu);

		
		column.setContextProperty(null);
		assertSourceDoesNotContain("(contextProperty", cu);
	}

	public void testGetTable() throws Exception {
		ICompilationUnit cu = this.createTestTenantDiscriminatorColumnWithTable();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		EclipseLinkTenantDiscriminatorColumnAnnotation column = (EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.getAnnotation(0, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);
		assertEquals(COLUMN_TABLE, column.getTable());
	}

	public void testSetTable() throws Exception {
		ICompilationUnit cu = this.createTestTenantDiscriminatorColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		EclipseLinkTenantDiscriminatorColumnAnnotation column = (EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.getAnnotation(0, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);

		assertNotNull(column);
		assertNull(column.getTable());

		column.setTable("Foo");
		assertEquals("Foo", column.getTable());
		
		assertSourceContains("@TenantDiscriminatorColumn(table = \"Foo\")", cu);

		
		column.setTable(null);
		assertSourceDoesNotContain("(table", cu);
	}

	public void testGetPrimaryKey() throws Exception {
		ICompilationUnit cu = this.createTestColumnWithBooleanElement("primaryKey");
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		EclipseLinkTenantDiscriminatorColumnAnnotation column = (EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.getAnnotation(0, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);

		assertEquals(Boolean.TRUE, column.getPrimaryKey());
	}
	
	public void testSetPrimaryKey() throws Exception {
		ICompilationUnit cu = this.createTestTenantDiscriminatorColumn();
		JavaResourceType resourceType = buildJavaResourceType(cu); 
		EclipseLinkTenantDiscriminatorColumnAnnotation column = (EclipseLinkTenantDiscriminatorColumnAnnotation) resourceType.getAnnotation(0, EclipseLink2_3.TENANT_DISCRIMINATOR_COLUMN);

		assertNotNull(column);
		assertNull(column.getPrimaryKey());

		column.setPrimaryKey(Boolean.FALSE);
		assertEquals(Boolean.FALSE, column.getPrimaryKey());
		
		assertSourceContains("@TenantDiscriminatorColumn(primaryKey = false)", cu);
		
		column.setPrimaryKey(null);
		assertSourceDoesNotContain("primaryKey", cu);
	}

}
