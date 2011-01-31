/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.tests.internal.utility.jdt;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jpt.common.core.internal.utility.jdt.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.osgi.framework.Version;

@SuppressWarnings("nls")
public class CombinationIndexedDeclarationAnnotationAdapterTests extends AnnotationTestCase {

	public CombinationIndexedDeclarationAnnotationAdapterTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createCompilationUnit("annot", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}

	public void testAnnotation1() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumn(name=\"ADDRESS_ID\")");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 0);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);
		assertEquals("annot.JoinColumn", annotation.getTypeName().getFullyQualifiedName());
		assertTrue(annotation.isNormalAnnotation());
	}

	public void testAnnotation2() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumn(name=\"ADDRESS_ID\")");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 1);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);
	}

	public void testAnnotation3() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(@annot.JoinColumn(name=\"ADDRESS_ID\"))");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 0);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);
		assertEquals("annot.JoinColumn", annotation.getTypeName().getFullyQualifiedName());
		assertTrue(annotation.isNormalAnnotation());
	}

	public void testAnnotation4() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(@annot.JoinColumn(name=\"ADDRESS_ID\"))");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 1);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);
	}

	public void testAnnotation5() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 1);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);
		assertEquals("annot.JoinColumn", annotation.getTypeName().getFullyQualifiedName());
		assertTrue(annotation.isNormalAnnotation());
		String value = ((StringLiteral) this.values((NormalAnnotation) annotation).get(0).getValue()).getLiteralValue();
		assertEquals("ADDRESS_ID2", value);
	}

	public void testAnnotation6() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);
	}

	public void testAnnotation7() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);
		assertEquals("annot.JoinColumn", annotation.getTypeName().getFullyQualifiedName());
		assertTrue(annotation.isNormalAnnotation());
		String value = ((StringLiteral) this.values(((NormalAnnotation) annotation)).get(0).getValue()).getLiteralValue();
		assertEquals("ADDRESS_ID2", value);
	}

	public void testAnnotation8() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 1);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);
	}

	public void testRemoveAnnotation1() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumn(name=\"ADDRESS_ID\")");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 0);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		aa.removeAnnotation();
		this.assertSourceDoesNotContain("JoinColumn", cu);
	}

	public void testRemoveAnnotation2() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(@annot.JoinColumn(name=\"ADDRESS_ID\"))");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 0);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		aa.removeAnnotation();
		this.assertSourceDoesNotContain("ADDRESS_ID", cu);
	}

	public void testRemoveAnnotation3() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 1);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		aa.removeAnnotation();
		this.assertSourceDoesNotContain("JoinColumns", cu);
		this.assertSourceDoesNotContain("ADDRESS_ID2", cu);
		this.assertSourceContains("@JoinColumn(name=\"ADDRESS_ID1\")", cu);
	}

	public void testRemoveAnnotation4() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		aa.removeAnnotation();
		this.assertSourceDoesNotContain("JoinColumns", cu);
		this.assertSourceDoesNotContain("ADDRESS_ID2", cu);
		this.assertSourceContains("@JoinColumn(name=\"ADDRESS_ID1\")", cu);
	}

	public void testRemoveAnnotation5() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "String comment(); JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(comment=\"test\",columns={@annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		aa.removeAnnotation();
		this.assertSourceContains("@annot.JoinColumns(comment=\"test\",columns=@annot.JoinColumn(name=\"ADDRESS_ID1\"))", cu);
	}

	public void testRemoveAnnotation6() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(null)");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 0);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		aa.removeAnnotation();
		this.assertSourceContains("@annot.JoinColumns(null)", cu);
	}

	public void testRemoveAnnotation12() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), null, @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		String expected = "@JoinColumn(name=\"ADDRESS_ID0\")";
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 2);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceContains(expected, cu);
		this.assertSourceDoesNotContain("JoinColumns", cu);
	}

	public void testRemoveAnnotation13() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns({null, @annot.JoinColumn(name=\"ADDRESS_ID1\")})");
		this.assertSourceContains("@annot.JoinColumn", cu);
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 1);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceDoesNotContain("ADDRESS_ID", cu);
	}

	public void testRemoveAnnotation14() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), null, @annot.JoinColumn(name=\"ADDRESS_ID2\"), null})");
		String expected = "@JoinColumn(name=\"ADDRESS_ID0\")";
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 2);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceContains(expected, cu);
	}

	public void testRemoveAnnotation15() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), null, @annot.JoinColumn(name=\"ADDRESS_ID2\"), @annot.JoinColumn(name=\"ADDRESS_ID3\")})");
		String expected = "@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), null, null, @annot.JoinColumn(name=\"ADDRESS_ID3\")})";
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 2);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceContains(expected, cu);
	}

	public void testRemoveAnnotation16() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), null, @annot.JoinColumn(name=\"ADDRESS_ID2\"), @annot.JoinColumn(name=\"ADDRESS_ID3\")})");
		String expected = "@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), null, @annot.JoinColumn(name=\"ADDRESS_ID2\")})";
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 3);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceContains(expected, cu);
	}

	public void testRemoveAnnotation17() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns({null, null, @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		this.assertSourceContains("@annot.JoinColumn", cu);
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 2);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceDoesNotContain("ADDRESS_ID2", cu);
	}

	public void testNewMarkerAnnotation1() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain("JoinColumn", cu);
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 0);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("JoinColumn", cu);
		this.assertSourceDoesNotContain("JoinColumns", cu);
	}

	public void testNewMarkerAnnotation2() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumn");
		this.assertSourceDoesNotContain("JoinColumns", cu);
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@JoinColumns(columns = { @JoinColumn, @JoinColumn })", cu);
	}

	public void testNewMarkerAnnotation3() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn, @annot.JoinColumn})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 2);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@annot.JoinColumns(columns={@annot.JoinColumn, @annot.JoinColumn," + CR + "    @JoinColumn})", cu);
	}

	public void testNewMarkerAnnotation4() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumn(77)");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 0);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("JoinColumn", cu);
		this.assertSourceDoesNotContain("JoinColumns", cu);
		this.assertSourceDoesNotContain("77", cu);
	}

	public void testNewMarkerAnnotation5() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns=@annot.JoinColumn(77))");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 0);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@annot.JoinColumns(columns=@JoinColumn)", cu);
		this.assertSourceDoesNotContain("77", cu);
	}

	public void testNewMarkerAnnotation6() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns=@annot.JoinColumn(77))");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@annot.JoinColumns(columns={@annot.JoinColumn(77),@JoinColumn})", cu);
	}

	public void testNewMarkerAnnotation7() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumn(77)");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@JoinColumns(columns = { @JoinColumn(77), @JoinColumn })", cu);
	}

	public void testNewMarkerAnnotation8() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(77),@annot.JoinColumn(88)})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@annot.JoinColumns(columns={@annot.JoinColumn(77),@JoinColumn})", cu);
	}

	public void testNewMarkerAnnotation9() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name(); String text(); int num();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumn(text=\"blah\",num=42)");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@JoinColumns(columns = { @JoinColumn(text = \"blah\", num = 42), @JoinColumn })", cu);
	}

	public void testNewMarkerAnnotation23() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name(); String text(); int num();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumn(text=\"b\",num=4)");
		String expected1 = "@JoinColumns(columns = { @JoinColumn(text = \"b\", num = 4), null,";
		String expected2 = "@JoinColumn })";
		this.assertSourceDoesNotContain(expected1, cu);
		this.assertSourceDoesNotContain(expected2, cu);
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 2);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		aa.newMarkerAnnotation();
		this.assertSourceContains(expected1, cu);
		this.assertSourceContains(expected2, cu);
	}

	public void testNewMarkerAnnotation24() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name(); String text(); int num();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumn(text=\"blah\",num=42)");	
		
		//Use old formatting if jdt core version is 3.5 or below - see bug 285604
		Version version = Platform.getBundle("org.eclipse.jdt.core").getVersion();
		int majorVersion = version.getMajor();
		int minorVersion = version.getMinor();
		//This condition should be removed and test updated after Dali 3.0 branches 
		String expected1;
		if (majorVersion == 3 && minorVersion <= 5) {
			expected1 = "@JoinColumns( {";
		}
		else expected1 = "@JoinColumns({";
		
		String expected2 = "@JoinColumn(text = \"blah\", num = 42), null,";
		String expected3 = "@JoinColumn " + CR + "    })";
		this.assertSourceDoesNotContain(expected1, cu);
		this.assertSourceDoesNotContain(expected2, cu);
		this.assertSourceDoesNotContain(expected3, cu);
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 2);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		aa.newMarkerAnnotation();
		this.assertSourceContains(expected1, cu);
		this.assertSourceContains(expected2, cu);
		this.assertSourceContains(expected3, cu);
	}

	public void testNewMarkerAnnotation25() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\")})");
		String expected1 = "@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), null, null,";  // the line gets split
		String expected2 = "@JoinColumn})";
		this.assertSourceDoesNotContain(expected1, cu);
		this.assertSourceDoesNotContain(expected2, cu);
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 4);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		aa.newMarkerAnnotation();
		this.assertSourceContains(expected1, cu);
		this.assertSourceContains(expected2, cu);
	}

	public void testNewMarkerAnnotation26() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\")})");
		String expected1 = "@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), null, null,";  // the line gets split
		String expected2 = "@JoinColumn})";
		this.assertSourceDoesNotContain(expected1, cu);
		this.assertSourceDoesNotContain(expected2, cu);
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 4);
		AnnotationAdapter aa = new ElementAnnotationAdapter(this.idField(cu), daa);
		aa.newMarkerAnnotation();
		this.assertSourceContains(expected1, cu);
		this.assertSourceContains(expected2, cu);
	}

	public void testMoveAnnotation1() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumn(name=\"ADDRESS_ID0\")");
		String expected = "@JoinColumns(columns = { null, @JoinColumn(name = \"ADDRESS_ID0\") })";
		this.assertSourceDoesNotContain(expected, cu);
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 0);
		IndexedAnnotationAdapter iaa  = new ElementIndexedAnnotationAdapter(this.idField(cu), cidaa);
		Annotation annotation = iaa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		iaa.moveAnnotation(1);
		this.assertSourceContains(expected, cu);
	}

	public void testMoveAnnotation2() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns={null,@annot.JoinColumn(name=\"ADDRESS_ID1\")})");
		String expected = "@JoinColumn(name=\"ADDRESS_ID1\")";
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		IndexedAnnotationAdapter iaa  = new ElementIndexedAnnotationAdapter(this.idField(cu), cidaa);
		Annotation annotation = iaa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected, cu);
		this.assertSourceDoesNotContain("JoinColumns", cu);
	}

	public void testMoveAnnotation2a() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns({null,@annot.JoinColumn(name=\"ADDRESS_ID1\")})");
		String expected = "@JoinColumn(name=\"ADDRESS_ID1\")";
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 1);
		IndexedAnnotationAdapter iaa  = new ElementIndexedAnnotationAdapter(this.idField(cu), cidaa);
		Annotation annotation = iaa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected, cu);
		this.assertSourceDoesNotContain("JoinColumns", cu);
	}

	public void testMoveAnnotation3() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\"), @annot.JoinColumn(name=\"ADDRESS_ID3\")})");
		String expected = "@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID3\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})";
		this.assertSourceDoesNotContain(expected, cu);
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 3);
		IndexedAnnotationAdapter iaa  = new ElementIndexedAnnotationAdapter(this.idField(cu), cidaa);
		Annotation annotation = iaa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected, cu);
	}

	public void testMoveAnnotation4() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\"), @annot.JoinColumn(name=\"ADDRESS_ID3\"), @annot.JoinColumn(name=\"ADDRESS_ID4\")})");
		String expected = "@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID3\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\"), null, @annot.JoinColumn(name=\"ADDRESS_ID4\")})";
		this.assertSourceDoesNotContain(expected, cu);
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 3);
		IndexedAnnotationAdapter iaa  = new ElementIndexedAnnotationAdapter(this.idField(cu), cidaa);
		Annotation annotation = iaa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected, cu);
	}

	public void testMoveAnnotation5() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		String expected = "@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), null, @annot.JoinColumn(name=\"ADDRESS_ID2\")})";
		this.assertSourceDoesNotContain(expected, cu);
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 2);
		IndexedAnnotationAdapter iaa  = new ElementIndexedAnnotationAdapter(this.idField(cu), cidaa);
		Annotation annotation = iaa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		iaa.moveAnnotation(3);
		this.assertSourceContains(expected, cu);
	}

	public void testMoveAnnotation6() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		String expected = "@annot.JoinColumns(columns={null, @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\"), @annot.JoinColumn(name=\"ADDRESS_ID0\")})";
		this.assertSourceDoesNotContain(expected, cu);
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 0);
		IndexedAnnotationAdapter iaa  = new ElementIndexedAnnotationAdapter(this.idField(cu), cidaa);
		Annotation annotation = iaa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		iaa.moveAnnotation(3);
		this.assertSourceContains(expected, cu);
	}

	public void testMoveAnnotation7() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		String expected = "@annot.JoinColumns(columns={null, @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})";
		this.assertSourceDoesNotContain(expected, cu);
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 3);
		IndexedAnnotationAdapter iaa  = new ElementIndexedAnnotationAdapter(this.idField(cu), cidaa);
		Annotation annotation = iaa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected, cu);
	}

	public void testMoveAnnotation8() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\"), null, @annot.JoinColumn(name=\"ADDRESS_ID4\")})");
		String expected = "@annot.JoinColumns(columns={null, @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\"), null, @annot.JoinColumn(name=\"ADDRESS_ID4\")})";
		this.assertSourceDoesNotContain(expected, cu);
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 3);
		IndexedAnnotationAdapter iaa  = new ElementIndexedAnnotationAdapter(this.idField(cu), cidaa);
		Annotation annotation = iaa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected, cu);
	}

	public void testMoveAnnotation9() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		String expected = "@annot.JoinColumns(columns={null, @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})";
		ICompilationUnit cu = this.createTestType(expected);  // the source should be unchanged
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 0);
		IndexedAnnotationAdapter iaa  = new ElementIndexedAnnotationAdapter(this.idField(cu), cidaa);
		Annotation annotation = iaa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);

		iaa.moveAnnotation(3);
		this.assertSourceContains(expected, cu);
	}

	public void testMoveAnnotation10() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\")})");
		String expected = "@JoinColumn(name=\"ADDRESS_ID0\")";
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 2);
		IndexedAnnotationAdapter iaa  = new ElementIndexedAnnotationAdapter(this.idField(cu), cidaa);
		Annotation annotation = iaa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);

		iaa.moveAnnotation(1);
		this.assertSourceContains(expected, cu);
		this.assertSourceDoesNotContain("@annot.JoinColumns", cu);
	}

	public void testMoveAnnotation10a() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\")})");
		String expected = "@JoinColumn(name=\"ADDRESS_ID0\")";
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 2);
		IndexedAnnotationAdapter iaa  = new ElementIndexedAnnotationAdapter(this.idField(cu), cidaa);
		Annotation annotation = iaa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);

		iaa.moveAnnotation(1);
		this.assertSourceContains(expected, cu);
		this.assertSourceDoesNotContain("@annot.JoinColumns", cu);
	}

	public void testMoveAnnotation11() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumn(name=\"ADDRESS_ID0\")");
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		IndexedAnnotationAdapter iaa  = new ElementIndexedAnnotationAdapter(this.idField(cu), cidaa);
		Annotation annotation = iaa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceDoesNotContain("JoinColumn", cu);
	}

	public void testMoveAnnotation12() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), null, @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		String expected = "@JoinColumn(name=\"ADDRESS_ID2\")";
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 2);
		IndexedAnnotationAdapter iaa  = new ElementIndexedAnnotationAdapter(this.idField(cu), cidaa);
		Annotation annotation = iaa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected, cu);
		this.assertSourceDoesNotContain("@annot.JoinColumns", cu);
	}

	public void testMoveAnnotation13() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		ICompilationUnit cu = this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), null, @annot.JoinColumn(name=\"ADDRESS_ID3\")})");
		String expected = "@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID3\"), @annot.JoinColumn(name=\"ADDRESS_ID1\")})";
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 3);
		IndexedAnnotationAdapter iaa  = new ElementIndexedAnnotationAdapter(this.idField(cu), cidaa);
		Annotation annotation = iaa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected, cu);
	}

}
