/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.utility.jdt;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jpt.core.internal.utility.jdt.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;

public class CombinationIndexedDeclarationAnnotationAdapterTests extends AnnotationTestCase {

	public CombinationIndexedDeclarationAnnotationAdapterTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("annot", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}

	public void testAnnotation1() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumn(name=\"ADDRESS_ID\")");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 0);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);
		assertEquals("annot.JoinColumn", annotation.getTypeName().getFullyQualifiedName());
		assertTrue(annotation.isNormalAnnotation());
	}

	public void testAnnotation2() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumn(name=\"ADDRESS_ID\")");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 1);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);
	}

	public void testAnnotation3() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumns(@annot.JoinColumn(name=\"ADDRESS_ID\"))");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 0);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);
		assertEquals("annot.JoinColumn", annotation.getTypeName().getFullyQualifiedName());
		assertTrue(annotation.isNormalAnnotation());
	}

	public void testAnnotation4() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumns(@annot.JoinColumn(name=\"ADDRESS_ID\"))");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 1);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);
	}

	public void testAnnotation5() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 1);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);
		assertEquals("annot.JoinColumn", annotation.getTypeName().getFullyQualifiedName());
		assertTrue(annotation.isNormalAnnotation());
		String value = ((StringLiteral) ((MemberValuePair) ((NormalAnnotation) annotation).values().get(0)).getValue()).getLiteralValue();
		assertEquals("ADDRESS_ID2", value);
	}

	public void testAnnotation6() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);
	}

	public void testAnnotation7() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);
		assertEquals("annot.JoinColumn", annotation.getTypeName().getFullyQualifiedName());
		assertTrue(annotation.isNormalAnnotation());
		String value = ((StringLiteral) ((MemberValuePair) ((NormalAnnotation) annotation).values().get(0)).getValue()).getLiteralValue();
		assertEquals("ADDRESS_ID2", value);
	}

	public void testAnnotation8() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 1);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);
	}

	public void testRemoveAnnotation1() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumn(name=\"ADDRESS_ID\")");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 0);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		aa.removeAnnotation();
		this.assertSourceDoesNotContain("JoinColumn");
	}

	public void testRemoveAnnotation2() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumns(@annot.JoinColumn(name=\"ADDRESS_ID\"))");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 0);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		aa.removeAnnotation();
		this.assertSourceDoesNotContain("JoinColumns");
		this.assertSourceDoesNotContain("JoinColumn");
	}

	public void testRemoveAnnotation3() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 1);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		aa.removeAnnotation();
		this.assertSourceDoesNotContain("JoinColumns");
		this.assertSourceDoesNotContain("ADDRESS_ID2");
		this.assertSourceContains("@JoinColumn(name=\"ADDRESS_ID1\")");
	}

	public void testRemoveAnnotation4() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		aa.removeAnnotation();
		this.assertSourceDoesNotContain("JoinColumns");
		this.assertSourceDoesNotContain("ADDRESS_ID2");
		this.assertSourceContains("@JoinColumn(name=\"ADDRESS_ID1\")");
	}

	public void testRemoveAnnotation5() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "String comment(); JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(comment=\"test\",columns={@annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		aa.removeAnnotation();
		this.assertSourceContains("@annot.JoinColumns(comment=\"test\",columns=@annot.JoinColumn(name=\"ADDRESS_ID1\"))");
	}

	public void testRemoveAnnotation6() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumns(null)");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", 0);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		aa.removeAnnotation();
		this.assertSourceContains("@annot.JoinColumns(null)");
	}

	public void testRemoveAnnotation12() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), null, @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		String expected = "@JoinColumn(name=\"ADDRESS_ID0\")";
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 2);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceContains(expected);
		this.assertSourceDoesNotContain("JoinColumns");
	}

	public void testRemoveAnnotation13() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumns({null, @annot.JoinColumn(name=\"ADDRESS_ID1\")})");
		this.assertSourceContains("@annot.JoinColumn");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 1);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceDoesNotContain("JoinColumn");
	}

	public void testRemoveAnnotation14() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), null, @annot.JoinColumn(name=\"ADDRESS_ID2\"), null})");
		String expected = "@JoinColumn(name=\"ADDRESS_ID0\")";
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 2);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceContains(expected);
	}

	public void testRemoveAnnotation15() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), null, @annot.JoinColumn(name=\"ADDRESS_ID2\"), @annot.JoinColumn(name=\"ADDRESS_ID3\")})");
		String expected = "@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), null, null, @annot.JoinColumn(name=\"ADDRESS_ID3\")})";
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 2);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceContains(expected);
	}

	public void testRemoveAnnotation16() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), null, @annot.JoinColumn(name=\"ADDRESS_ID2\"), @annot.JoinColumn(name=\"ADDRESS_ID3\")})");
		String expected = "@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), null, @annot.JoinColumn(name=\"ADDRESS_ID2\")})";
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 3);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceContains(expected);
	}

	public void testRemoveAnnotation17() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumns({null, null, @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		this.assertSourceContains("@annot.JoinColumn");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 2);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceDoesNotContain("JoinColumn");
	}

	public void testNewMarkerAnnotation1() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType();
		this.assertSourceDoesNotContain("JoinColumn");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 0);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("JoinColumn");
		this.assertSourceDoesNotContain("JoinColumns");
	}

	public void testNewMarkerAnnotation2() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumn");
		this.assertSourceDoesNotContain("JoinColumns");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@JoinColumns(columns={@JoinColumn,@JoinColumn})");
	}

	public void testNewMarkerAnnotation3() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn, @annot.JoinColumn})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 2);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@annot.JoinColumns(columns={@annot.JoinColumn, @annot.JoinColumn," + CR + "    @JoinColumn})");
	}

	public void testNewMarkerAnnotation4() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumn(77)");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 0);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("JoinColumn");
		this.assertSourceDoesNotContain("JoinColumns");
		this.assertSourceDoesNotContain("77");
	}

	public void testNewMarkerAnnotation5() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns=@annot.JoinColumn(77))");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 0);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@annot.JoinColumns(columns=@JoinColumn)");
		this.assertSourceDoesNotContain("77");
	}

	public void testNewMarkerAnnotation6() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns=@annot.JoinColumn(77))");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@annot.JoinColumns(columns={@annot.JoinColumn(77),@JoinColumn})");
	}

	public void testNewMarkerAnnotation7() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumn(77)");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@JoinColumns(columns={@JoinColumn(77),@JoinColumn})");
	}

	public void testNewMarkerAnnotation8() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(77),@annot.JoinColumn(88)})");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@annot.JoinColumns(columns={@annot.JoinColumn(77),@JoinColumn})");
	}

	public void testNewMarkerAnnotation9() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name(); String text(); int num();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumn(text=\"blah\",num=42)");
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@JoinColumns(columns={@JoinColumn(text=\"blah\", num=42),@JoinColumn})");
	}

	public void testNewMarkerAnnotation23() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name(); String text(); int num();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumn(text=\"blah\",num=42)");
		String expected = "@JoinColumns(columns={@JoinColumn(text=\"blah\", num=42),null,@JoinColumn})";
		this.assertSourceDoesNotContain(expected);
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 2);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation24() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name(); String text(); int num();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumn(text=\"blah\",num=42)");
		String expected1 = "@JoinColumns({";
		String expected2 = "@JoinColumn(text=\"blah\", num=42),null,";
		String expected3 = "@JoinColumn" + CR + "    })";
		this.assertSourceDoesNotContain(expected1);
		this.assertSourceDoesNotContain(expected2);
		this.assertSourceDoesNotContain(expected3);
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 2);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		aa.newMarkerAnnotation();
		this.assertSourceContains(expected1);
		this.assertSourceContains(expected2);
		this.assertSourceContains(expected3);
	}

	public void testNewMarkerAnnotation25() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\")})");
		String expected1 = "@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), null, null,";  // the line gets split
		String expected2 = "@JoinColumn})";
		this.assertSourceDoesNotContain(expected1);
		this.assertSourceDoesNotContain(expected2);
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 4);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		aa.newMarkerAnnotation();
		this.assertSourceContains(expected1);
		this.assertSourceContains(expected2);
	}

	public void testNewMarkerAnnotation26() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\")})");
		String expected1 = "@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), null, null,";  // the line gets split
		String expected2 = "@JoinColumn})";
		this.assertSourceDoesNotContain(expected1);
		this.assertSourceDoesNotContain(expected2);
		DeclarationAnnotationAdapter daa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 4);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		aa.newMarkerAnnotation();
		this.assertSourceContains(expected1);
		this.assertSourceContains(expected2);
	}

	public void testMoveAnnotation1() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumn(name=\"ADDRESS_ID0\")");
		String expected = "@JoinColumns(columns={null,@JoinColumn(name=\"ADDRESS_ID0\")})";
		this.assertSourceDoesNotContain(expected);
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 0);
		IndexedAnnotationAdapter iaa  = new MemberIndexedAnnotationAdapter(this.idField(), cidaa);
		Annotation annotation = iaa.annotation();
		assertNotNull(annotation);

		iaa.moveAnnotation(1);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation2() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns={null,@annot.JoinColumn(name=\"ADDRESS_ID1\")})");
		String expected = "@JoinColumn(name=\"ADDRESS_ID1\")";
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		IndexedAnnotationAdapter iaa  = new MemberIndexedAnnotationAdapter(this.idField(), cidaa);
		Annotation annotation = iaa.annotation();
		assertNotNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected);
		this.assertSourceDoesNotContain("JoinColumns");
	}

	public void testMoveAnnotation2a() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumns({null,@annot.JoinColumn(name=\"ADDRESS_ID1\")})");
		String expected = "@JoinColumn(name=\"ADDRESS_ID1\")";
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 1);
		IndexedAnnotationAdapter iaa  = new MemberIndexedAnnotationAdapter(this.idField(), cidaa);
		Annotation annotation = iaa.annotation();
		assertNotNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected);
		this.assertSourceDoesNotContain("JoinColumns");
	}

	public void testMoveAnnotation3() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\"), @annot.JoinColumn(name=\"ADDRESS_ID3\")})");
		String expected = "@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID3\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})";
		this.assertSourceDoesNotContain(expected);
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 3);
		IndexedAnnotationAdapter iaa  = new MemberIndexedAnnotationAdapter(this.idField(), cidaa);
		Annotation annotation = iaa.annotation();
		assertNotNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation4() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\"), @annot.JoinColumn(name=\"ADDRESS_ID3\"), @annot.JoinColumn(name=\"ADDRESS_ID4\")})");
		String expected = "@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID3\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\"), null, @annot.JoinColumn(name=\"ADDRESS_ID4\")})";
		this.assertSourceDoesNotContain(expected);
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 3);
		IndexedAnnotationAdapter iaa  = new MemberIndexedAnnotationAdapter(this.idField(), cidaa);
		Annotation annotation = iaa.annotation();
		assertNotNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation5() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		String expected = "@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), null, @annot.JoinColumn(name=\"ADDRESS_ID2\")})";
		this.assertSourceDoesNotContain(expected);
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 2);
		IndexedAnnotationAdapter iaa  = new MemberIndexedAnnotationAdapter(this.idField(), cidaa);
		Annotation annotation = iaa.annotation();
		assertNotNull(annotation);

		iaa.moveAnnotation(3);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation6() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		String expected = "@annot.JoinColumns(columns={null, @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\"), @annot.JoinColumn(name=\"ADDRESS_ID0\")})";
		this.assertSourceDoesNotContain(expected);
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 0);
		IndexedAnnotationAdapter iaa  = new MemberIndexedAnnotationAdapter(this.idField(), cidaa);
		Annotation annotation = iaa.annotation();
		assertNotNull(annotation);

		iaa.moveAnnotation(3);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation7() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		String expected = "@annot.JoinColumns(columns={null, @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})";
		this.assertSourceDoesNotContain(expected);
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 3);
		IndexedAnnotationAdapter iaa  = new MemberIndexedAnnotationAdapter(this.idField(), cidaa);
		Annotation annotation = iaa.annotation();
		assertNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation8() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\"), null, @annot.JoinColumn(name=\"ADDRESS_ID4\")})");
		String expected = "@annot.JoinColumns(columns={null, @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\"), null, @annot.JoinColumn(name=\"ADDRESS_ID4\")})";
		this.assertSourceDoesNotContain(expected);
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 3);
		IndexedAnnotationAdapter iaa  = new MemberIndexedAnnotationAdapter(this.idField(), cidaa);
		Annotation annotation = iaa.annotation();
		assertNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation9() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		String expected = "@annot.JoinColumns(columns={null, @annot.JoinColumn(name=\"ADDRESS_ID1\"), @annot.JoinColumn(name=\"ADDRESS_ID2\")})";
		this.createTestType(expected);  // the source should be unchanged
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 0);
		IndexedAnnotationAdapter iaa  = new MemberIndexedAnnotationAdapter(this.idField(), cidaa);
		Annotation annotation = iaa.annotation();
		assertNull(annotation);

		iaa.moveAnnotation(3);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation10() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\")})");
		String expected = "@JoinColumn(name=\"ADDRESS_ID0\")";
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 2);
		IndexedAnnotationAdapter iaa  = new MemberIndexedAnnotationAdapter(this.idField(), cidaa);
		Annotation annotation = iaa.annotation();
		assertNull(annotation);

		iaa.moveAnnotation(1);
		this.assertSourceContains(expected);
		this.assertSourceDoesNotContain("@annot.JoinColumns");
	}

	public void testMoveAnnotation10a() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] value();");
		this.createTestType("@annot.JoinColumns({@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\")})");
		String expected = "@JoinColumn(name=\"ADDRESS_ID0\")";
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "value", 2);
		IndexedAnnotationAdapter iaa  = new MemberIndexedAnnotationAdapter(this.idField(), cidaa);
		Annotation annotation = iaa.annotation();
		assertNull(annotation);

		iaa.moveAnnotation(1);
		this.assertSourceContains(expected);
		this.assertSourceDoesNotContain("@annot.JoinColumns");
	}

	public void testMoveAnnotation11() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumn(name=\"ADDRESS_ID0\")");
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 1);
		IndexedAnnotationAdapter iaa  = new MemberIndexedAnnotationAdapter(this.idField(), cidaa);
		Annotation annotation = iaa.annotation();
		assertNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceDoesNotContain("JoinColumn");
	}

	public void testMoveAnnotation12() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), null, @annot.JoinColumn(name=\"ADDRESS_ID2\")})");
		String expected = "@JoinColumn(name=\"ADDRESS_ID2\")";
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 2);
		IndexedAnnotationAdapter iaa  = new MemberIndexedAnnotationAdapter(this.idField(), cidaa);
		Annotation annotation = iaa.annotation();
		assertNotNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected);
		this.assertSourceDoesNotContain("@annot.JoinColumns");
	}

	public void testMoveAnnotation13() throws Exception {
		this.createAnnotationAndMembers("JoinColumn", "String name();");
		this.createAnnotationAndMembers("JoinColumns", "JoinColumn[] columns();");
		this.createTestType("@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID0\"), @annot.JoinColumn(name=\"ADDRESS_ID1\"), null, @annot.JoinColumn(name=\"ADDRESS_ID3\")})");
		String expected = "@annot.JoinColumns(columns={@annot.JoinColumn(name=\"ADDRESS_ID3\"), @annot.JoinColumn(name=\"ADDRESS_ID1\")})";
		IndexedDeclarationAnnotationAdapter cidaa = new CombinationIndexedDeclarationAnnotationAdapter(
				"annot.JoinColumn", "annot.JoinColumns", "columns", 3);
		IndexedAnnotationAdapter iaa  = new MemberIndexedAnnotationAdapter(this.idField(), cidaa);
		Annotation annotation = iaa.annotation();
		assertNotNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected);
	}

}
