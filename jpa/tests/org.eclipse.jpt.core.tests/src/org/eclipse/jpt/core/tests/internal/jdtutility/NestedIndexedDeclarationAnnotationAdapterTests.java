/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jdtutility;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;

public class NestedIndexedDeclarationAnnotationAdapterTests extends AnnotationTestCase {

	public NestedIndexedDeclarationAnnotationAdapterTests(String name) {
		super(name);
	}

	private void createAnnotation(String annotationName) throws Exception {
		this.createAnnotationAndMembers(annotationName, "");
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("annot", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}

	public void testAnnotation1() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations={@annot.Bar, @annot.Bar(\"two\")})");
		// 0
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);
		assertEquals("annot.Bar", annotation.getTypeName().getFullyQualifiedName());
		assertTrue(annotation.isMarkerAnnotation());

		// 1
		daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 1, "annot.Bar");
		aa = new MemberAnnotationAdapter(this.idField(), daa);
		annotation = aa.annotation();
		assertNotNull(annotation);
		assertEquals("annot.Bar", annotation.getTypeName().getFullyQualifiedName());
		assertTrue(annotation.isSingleMemberAnnotation());
	}

	public void testAnnotation2() throws Exception {
		this.createAnnotation("Baz");
		this.createAnnotationAndMembers("Bar", "annot.Baz[] yana();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=@annot.Bar(yana=@annot.Baz))");
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "nestedAnnotations", 0, "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedIndexedDeclarationAnnotationAdapter(daa2, "yana", 0, "annot.Baz");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa3);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);
		assertEquals("annot.Baz", annotation.getTypeName().getFullyQualifiedName());
		assertTrue(annotation.isMarkerAnnotation());
	}

	public void testAnnotation3() throws Exception {
		this.createAnnotation("Baz");
		this.createAnnotationAndMembers("Bar", "annot.Baz[] yana();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=@annot.Bar(yana={@annot.Baz}))");
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "nestedAnnotations", 0, "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedIndexedDeclarationAnnotationAdapter(daa2, "yana", 0, "annot.Baz");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa3);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);
		assertEquals("annot.Baz", annotation.getTypeName().getFullyQualifiedName());
		assertTrue(annotation.isMarkerAnnotation());

		// name mismatch
		daa3 = new NestedIndexedDeclarationAnnotationAdapter(daa2, "yana", 0, "annot.Xyz");
		aa = new MemberAnnotationAdapter(this.idField(), daa3);
		annotation = aa.annotation();
		assertNull(annotation);
	}

	public void testAnnotationNull1() throws Exception {
		this.createAnnotation("Bar");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo()");
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);
	}

	public void testAnnotationNull2() throws Exception {
		this.createAnnotation("Bar");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType();
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);
	}

	public void testAnnotationNull3() throws Exception {
		this.createAnnotation("Bar");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=\"annot.Bar\")");
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);
	}

	public void testAnnotationNull4() throws Exception {
		this.createAnnotation("NotBar");
		this.createAnnotation("Bar");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=@annot.NotBar)");
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);
	}

	public void testAnnotationNull5() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations={@annot.Bar, @annot.Bar(\"two\")})");
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 2, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);
	}

	public void testAnnotationNull6() throws Exception {
		this.createAnnotation("Xyz");
		this.createAnnotation("Baz");
		this.createAnnotationAndMembers("Bar", "annot.Baz[] yana();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=@annot.Bar(yana={@annot.Baz}))");
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("Foo");
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "nestedAnnotations", 0, "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedIndexedDeclarationAnnotationAdapter(daa2, "yana", 0, "annot.Xyz");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa3);
		Annotation annotation = aa.annotation();
		assertNull(annotation);
	}

	public void testRemoveAnnotation1() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		String na = "@annot.Foo(nestedAnnotations={@annot.Bar(0), @annot.Bar(1)})";
		String expected = "@annot.Foo(nestedAnnotations={null, @annot.Bar(1)})";
		this.createTestType(na);
		this.assertSourceDoesNotContain(expected);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa2);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceDoesNotContain(na);
		this.assertSourceContains(expected);
	}

	public void testRemoveAnnotation2() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType();
		this.assertSourceDoesNotContain("Foo");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.removeAnnotation();
		this.assertSourceDoesNotContain("Foo");
	}

	public void testRemoveAnnotation3() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "String[] nestedAnnotations();");
		String na = "@annot.Foo(nestedAnnotations={\"annot.Bar1\", \"annot.Bar2\", \"annot.Bar3\"})";
		this.createTestType(na);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.removeAnnotation();
		this.assertSourceContains(na);
	}

	public void testRemoveAnnotation4() throws Exception {
		this.createAnnotationAndMembers("NotBar", "int value();");
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.NotBar[] nestedAnnotations();");
		String na = "@annot.Foo(nestedAnnotations={@annot.NotBar(0), @annot.NotBar(1)})";
		this.createTestType(na);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.removeAnnotation();
		this.assertSourceContains(na);
	}

	public void testRemoveAnnotation5() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		String na = "@annot.Foo(nestedAnnotations=@annot.Bar)";
		this.createTestType(na);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa2);
		Annotation annotation = aa.annotation();
		assertNotNull(annotation);

		aa.removeAnnotation();
		assertNull(aa.annotation());
		this.assertSourceDoesNotContain(na);
		this.assertSourceDoesNotContain("Foo");
	}

	public void testRemoveAnnotation5a() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		String na = "@annot.Foo(nestedAnnotations=@annot.Bar)";
		this.createTestType(na);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa1 = new MemberAnnotationAdapter(this.idField(), daa1);
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "nestedAnnotations", 0, "annot.Bar", false);
		AnnotationAdapter aa2 = new MemberAnnotationAdapter(this.idField(), daa2);
		Annotation annotation = aa2.annotation();
		assertNotNull(annotation);

		aa2.removeAnnotation();
		assertTrue(aa1.annotation().isMarkerAnnotation());
		this.assertSourceDoesNotContain(na);
		this.assertSourceContains("import annot.Foo;");
		this.assertSourceContains("@Foo");
	}

	public void testRemoveAnnotation6() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "String[] nestedAnnotations();");
		String na = "@annot.Foo(nestedAnnotations=\"annot.Bar\")";
		this.createTestType(na);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.removeAnnotation();
		this.assertSourceContains(na);
	}

	public void testRemoveAnnotation7() throws Exception {
		this.createAnnotationAndMembers("NotBar", "int value();");
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.NotBar[] nestedAnnotations();");
		String na = "@annot.Foo(nestedAnnotations=@annot.NotBar)";
		this.createTestType(na);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.removeAnnotation();
		this.assertSourceContains(na);
	}

	public void testRemoveAnnotation8() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		String na = "@annot.Foo(nestedAnnotations={@annot.Bar(0), @annot.Bar(1), @annot.Bar(2), @annot.Bar(3)})";
		this.createTestType(na);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa1 = new MemberAnnotationAdapter(this.idField(), daa1);
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "nestedAnnotations", 2, "annot.Bar");
		AnnotationAdapter aa2 = new MemberAnnotationAdapter(this.idField(), daa2);
		Annotation annotation = aa2.annotation();
		assertNotNull(annotation);

		aa2.removeAnnotation();
		assertTrue(aa1.annotation().isNormalAnnotation());
		assertEquals(ASTNode.ARRAY_INITIALIZER, this.annotationElementValue(aa1.annotation(), "nestedAnnotations").getNodeType());
		this.assertSourceDoesNotContain(na);
		this.assertSourceContains("@annot.Foo(nestedAnnotations={@annot.Bar(0), @annot.Bar(1), null, @annot.Bar(3)})");
	}

	public void testRemoveAnnotation9() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		String na = "@annot.Foo({@annot.Bar(0), @annot.Bar(1), @annot.Bar(2), @annot.Bar(3)})";
		this.createTestType(na);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa1 = new MemberAnnotationAdapter(this.idField(), daa1);
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "value", 2, "annot.Bar");
		AnnotationAdapter aa2 = new MemberAnnotationAdapter(this.idField(), daa2);
		Annotation annotation = aa2.annotation();
		assertNotNull(annotation);

		aa2.removeAnnotation();
		assertTrue(aa1.annotation().isSingleMemberAnnotation());
		assertEquals(ASTNode.ARRAY_INITIALIZER, this.annotationElementValue(aa1.annotation(), "value").getNodeType());
		this.assertSourceDoesNotContain(na);
		this.assertSourceContains("@annot.Foo({@annot.Bar(0), @annot.Bar(1), null, @annot.Bar(3)})");
	}

	public void testRemoveAnnotation10() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		String na = "@annot.Foo({@annot.Bar(0), @annot.Bar(1)})";
		String expected = "@annot.Foo({null, @annot.Bar(1)})";
		this.createTestType(na);
		this.assertSourceDoesNotContain(expected);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "value", 0, "annot.Bar");
		AnnotationAdapter aa2 = new MemberAnnotationAdapter(this.idField(), daa2);
		Annotation annotation = aa2.annotation();
		assertNotNull(annotation);

		aa2.removeAnnotation();
		this.assertSourceDoesNotContain(na);
		this.assertSourceContains(expected);
	}

	public void testRemoveAnnotation11() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		String na = "@annot.Foo({@annot.Bar(0), @annot.Bar(1)})";
		String expected = "@annot.Foo(@annot.Bar(0))";
		this.createTestType(na);
		this.assertSourceDoesNotContain(expected);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa1 = new MemberAnnotationAdapter(this.idField(), daa1);
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "value", 1, "annot.Bar");
		AnnotationAdapter aa2 = new MemberAnnotationAdapter(this.idField(), daa2);
		Annotation annotation = aa2.annotation();
		assertNotNull(annotation);

		aa2.removeAnnotation();
		assertEquals(ASTNode.SINGLE_MEMBER_ANNOTATION, this.annotationElementValue(aa1.annotation(), "value").getNodeType());
		this.assertSourceDoesNotContain(na);
		this.assertSourceContains(expected);
	}

	public void testRemoveAnnotation12() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		String na = "@annot.Foo({@annot.Bar(0), null, @annot.Bar(2)})";
		String expected = "@annot.Foo(@annot.Bar(0))";
		this.createTestType(na);
		this.assertSourceDoesNotContain(expected);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa1 = new MemberAnnotationAdapter(this.idField(), daa1);
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "value", 2, "annot.Bar");
		AnnotationAdapter aa2 = new MemberAnnotationAdapter(this.idField(), daa2);
		Annotation annotation = aa2.annotation();
		assertNotNull(annotation);

		aa2.removeAnnotation();
		assertEquals(ASTNode.SINGLE_MEMBER_ANNOTATION, this.annotationElementValue(aa1.annotation(), "value").getNodeType());
		this.assertSourceDoesNotContain(na);
		this.assertSourceContains(expected);
	}

	public void testRemoveAnnotation13() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		String na = "@annot.Foo({null, @annot.Bar(1)})";
		this.createTestType(na);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "value", 1, "annot.Bar");
		AnnotationAdapter aa2 = new MemberAnnotationAdapter(this.idField(), daa2);
		Annotation annotation = aa2.annotation();
		assertNotNull(annotation);

		aa2.removeAnnotation();
		this.assertSourceDoesNotContain("Foo");
	}

	public void testRemoveAnnotation14() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		String na = "@annot.Foo({@annot.Bar(0), null, @annot.Bar(2), null})";
		String expected = "@annot.Foo(@annot.Bar(0))";
		this.createTestType(na);
		this.assertSourceDoesNotContain(expected);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa1 = new MemberAnnotationAdapter(this.idField(), daa1);
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "value", 2, "annot.Bar");
		AnnotationAdapter aa2 = new MemberAnnotationAdapter(this.idField(), daa2);
		Annotation annotation = aa2.annotation();
		assertNotNull(annotation);

		aa2.removeAnnotation();
		assertEquals(ASTNode.SINGLE_MEMBER_ANNOTATION, this.annotationElementValue(aa1.annotation(), "value").getNodeType());
		this.assertSourceDoesNotContain(na);
		this.assertSourceContains(expected);
	}

	public void testRemoveAnnotation15() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		String na = "@annot.Foo({@annot.Bar(0), null, @annot.Bar(2), @annot.Bar(3)})";
		String expected = "@annot.Foo({@annot.Bar(0), null, null, @annot.Bar(3)})";
		this.createTestType(na);
		this.assertSourceDoesNotContain(expected);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa1 = new MemberAnnotationAdapter(this.idField(), daa1);
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "value", 2, "annot.Bar");
		AnnotationAdapter aa2 = new MemberAnnotationAdapter(this.idField(), daa2);
		Annotation annotation = aa2.annotation();
		assertNotNull(annotation);

		aa2.removeAnnotation();
		assertEquals(ASTNode.ARRAY_INITIALIZER, this.annotationElementValue(aa1.annotation(), "value").getNodeType());
		this.assertSourceDoesNotContain(na);
		this.assertSourceContains(expected);
	}

	public void testRemoveAnnotation16() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		String na = "@annot.Foo({@annot.Bar(0), null, @annot.Bar(2), @annot.Bar(3)})";
		String expected = "@annot.Foo({@annot.Bar(0), null, @annot.Bar(2)})";
		this.createTestType(na);
		this.assertSourceDoesNotContain(expected);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa1 = new MemberAnnotationAdapter(this.idField(), daa1);
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "value", 3, "annot.Bar");
		AnnotationAdapter aa2 = new MemberAnnotationAdapter(this.idField(), daa2);
		Annotation annotation = aa2.annotation();
		assertNotNull(annotation);

		aa2.removeAnnotation();
		assertEquals(ASTNode.ARRAY_INITIALIZER, this.annotationElementValue(aa1.annotation(), "value").getNodeType());
		this.assertSourceDoesNotContain(na);
		this.assertSourceContains(expected);
	}

	public void testRemoveAnnotation17() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		String na = "@annot.Foo(nestedAnnotations={@annot.Bar(0), null, @annot.Bar(2)})";
		String expected = "@annot.Foo(nestedAnnotations=@annot.Bar(0))";
		this.createTestType(na);
		this.assertSourceDoesNotContain(expected);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa1 = new MemberAnnotationAdapter(this.idField(), daa1);
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "nestedAnnotations", 2, "annot.Bar");
		AnnotationAdapter aa2 = new MemberAnnotationAdapter(this.idField(), daa2);
		Annotation annotation = aa2.annotation();
		assertNotNull(annotation);

		aa2.removeAnnotation();
		assertEquals(ASTNode.SINGLE_MEMBER_ANNOTATION, this.annotationElementValue(aa1.annotation(), "nestedAnnotations").getNodeType());
		this.assertSourceDoesNotContain(na);
		this.assertSourceContains(expected);
	}

	public void testRemoveAnnotation18() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		String na = "@annot.Foo(nestedAnnotations={null, @annot.Bar(1)})";
		this.createTestType(na);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "nestedAnnotations", 1, "annot.Bar");
		AnnotationAdapter aa2 = new MemberAnnotationAdapter(this.idField(), daa2);
		Annotation annotation = aa2.annotation();
		assertNotNull(annotation);

		aa2.removeAnnotation();
		this.assertSourceDoesNotContain("Foo");
	}

	public void testRemoveAnnotation19() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		String na = "@annot.Foo(nestedAnnotations={@annot.Bar(0), null, @annot.Bar(2), null})";
		String expected = "@annot.Foo(nestedAnnotations=@annot.Bar(0))";
		this.createTestType(na);
		this.assertSourceDoesNotContain(expected);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa1 = new MemberAnnotationAdapter(this.idField(), daa1);
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "nestedAnnotations", 2, "annot.Bar");
		AnnotationAdapter aa2 = new MemberAnnotationAdapter(this.idField(), daa2);
		Annotation annotation = aa2.annotation();
		assertNotNull(annotation);

		aa2.removeAnnotation();
		assertEquals(ASTNode.SINGLE_MEMBER_ANNOTATION, this.annotationElementValue(aa1.annotation(), "nestedAnnotations").getNodeType());
		this.assertSourceDoesNotContain(na);
		this.assertSourceContains(expected);
	}

	public void testRemoveAnnotation20() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		String na = "@annot.Foo(nestedAnnotations={@annot.Bar(0), null, @annot.Bar(2), @annot.Bar(3)})";
		String expected = "@annot.Foo(nestedAnnotations={@annot.Bar(0), null, null, @annot.Bar(3)})";
		this.createTestType(na);
		this.assertSourceDoesNotContain(expected);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa1 = new MemberAnnotationAdapter(this.idField(), daa1);
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "nestedAnnotations", 2, "annot.Bar");
		AnnotationAdapter aa2 = new MemberAnnotationAdapter(this.idField(), daa2);
		Annotation annotation = aa2.annotation();
		assertNotNull(annotation);

		aa2.removeAnnotation();
		assertEquals(ASTNode.ARRAY_INITIALIZER, this.annotationElementValue(aa1.annotation(), "nestedAnnotations").getNodeType());
		this.assertSourceDoesNotContain(na);
		this.assertSourceContains(expected);
	}

	public void testRemoveAnnotation21() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		String na = "@annot.Foo(nestedAnnotations={@annot.Bar(0), null, @annot.Bar(2), @annot.Bar(3)})";
		String expected = "@annot.Foo(nestedAnnotations={@annot.Bar(0), null, @annot.Bar(2)})";
		this.createTestType(na);
		this.assertSourceDoesNotContain(expected);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa1 = new MemberAnnotationAdapter(this.idField(), daa1);
		DeclarationAnnotationAdapter daa2 = new NestedIndexedDeclarationAnnotationAdapter(daa1, "nestedAnnotations", 3, "annot.Bar");
		AnnotationAdapter aa2 = new MemberAnnotationAdapter(this.idField(), daa2);
		Annotation annotation = aa2.annotation();
		assertNotNull(annotation);

		aa2.removeAnnotation();
		assertEquals(ASTNode.ARRAY_INITIALIZER, this.annotationElementValue(aa1.annotation(), "nestedAnnotations").getNodeType());
		this.assertSourceDoesNotContain(na);
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation1() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType();
		this.assertSourceDoesNotContain("Foo");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@Foo(nestedAnnotations=@Bar)");
	}

	public void testNewMarkerAnnotation2() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType();
		this.assertSourceDoesNotContain("Foo");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@Foo(@Bar)");
	}

	public void testNewMarkerAnnotation3() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo");
		String expected = "@Foo(nestedAnnotations=@Bar)";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation4() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo");
		String expected = "@Foo(@Bar)";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation5() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "String value(); annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(\"something\")");
		String expected = "@Foo(value=\"something\", nestedAnnotations=@Bar)";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation6() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo(\"something\")");
		String expected = "@annot.Foo(@Bar)";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation7() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "String xxx(); annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(xxx=\"something\")");
		String expected = "@annot.Foo(xxx=\"something\", nestedAnnotations = @Bar)";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation8() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "String xxx(); annot.Bar[] value();");
		this.createTestType("@annot.Foo(xxx=\"something\")");
		String expected = "@annot.Foo(xxx=\"something\", value = @Bar)";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation9() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType();

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		assertNull(aa.annotation());

		aa.newMarkerAnnotation();
		this.assertSourceContains("@Foo(nestedAnnotations={null,null,null,null,null,@Bar})");
	}

	public void testNewMarkerAnnotation10() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "String value(); annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(\"something\")");
		this.assertSourceDoesNotContain("Bar");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@Foo(value=\"something\", nestedAnnotations={null,null,null,null,null,@Bar})");
	}

	public void testNewMarkerAnnotation11() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] value();");
		this.createTestType("@annot.Foo({\"one\", \"two\"})");
		String expected = "@annot.Foo({@Bar, \"two\"})";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation12() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] value();");
		this.createTestType("@annot.Foo({\"one\", \"two\"})");
		this.assertSourceDoesNotContain("Bar");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("Bar})");  // split line
	}

	public void testNewMarkerAnnotation13() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo(7)");
		String expected = "@annot.Foo(@Bar)";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation14() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo(7)");
		this.assertSourceDoesNotContain("Bar");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@annot.Foo({7,null,null,null,null,@Bar})");
	}

	public void testNewMarkerAnnotation15() throws Exception {
		this.createAnnotationAndMembers("NotBar", "int value();");
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo(@annot.NotBar)");
		String expected = "@annot.Foo(@Bar)";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation16() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo(@annot.Bar(55))");
		String expected = "@annot.Foo({@annot.Bar(55),@Bar})";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 1, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation17() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations={\"something\"})");
		String expected = "@annot.Foo(nestedAnnotations={@Bar})";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation18() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations={\"something\"})");
		this.assertSourceDoesNotContain("Bar");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("Bar})");  // split line
	}

	public void testNewMarkerAnnotation19() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=\"something\")");
		String expected = "@annot.Foo(nestedAnnotations=@Bar)";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation20() throws Exception {
		this.createAnnotationAndMembers("NotBar", "int value();");
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=@annot.NotBar)");
		String expected = "@annot.Foo(nestedAnnotations=@Bar)";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation21() throws Exception {
		this.createAnnotationAndMembers("NotBar", "int value();");
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=@annot.NotBar)");
		this.assertSourceDoesNotContain("@Bar");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@annot.Foo(nestedAnnotations={@annot.NotBar,null,null,null,null,@Bar})");
	}

	public void testNewMarkerAnnotation22() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=@annot.Bar(88))");
		String expected = "@annot.Foo(nestedAnnotations=@Bar)";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation23() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=@annot.Bar(88))");
		String expected = "@annot.Foo(nestedAnnotations={@annot.Bar(88),null,@Bar})";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 2, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation24() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] value();");
		this.createTestType("@annot.Foo(@annot.Bar(88))");
		String expected = "@annot.Foo({@annot.Bar(88),null,@Bar})";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 2, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation25() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations={@annot.Bar(88), @annot.Bar(77)})");
		String expected1 = "@annot.Foo(nestedAnnotations={@annot.Bar(88), @annot.Bar(77), null, null,";  // the line gets split
		String expected2 = "@Bar})";
		this.assertSourceDoesNotContain(expected1);
		this.assertSourceDoesNotContain(expected2);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 4, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		aa.newMarkerAnnotation();
		this.assertSourceContains(expected1);
		this.assertSourceContains(expected2);
	}

	public void testNewMarkerAnnotation26() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] value();");
		this.createTestType("@annot.Foo({@annot.Bar(88), @annot.Bar(77)})");
		String expected1 = "@annot.Foo({@annot.Bar(88), @annot.Bar(77), null, null,";  // the line gets split
		String expected2 = "@Bar})";
		this.assertSourceDoesNotContain(expected1);
		this.assertSourceDoesNotContain(expected2);

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 4, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		aa.newMarkerAnnotation();
		this.assertSourceContains(expected1);
		this.assertSourceContains(expected2);
	}

	public void testNewSingleMemberAnnotation1() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType();
		this.assertSourceDoesNotContain("@Foo");
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation1(declaration);
			}
		});
		this.assertSourceContains("@Foo(nestedAnnotations=@Bar(88))");
	}

	void editNewSingleMemberAnnotation1(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		NumberLiteral numberLiteral = annotation.getAST().newNumberLiteral();
		numberLiteral.setToken("88");
		annotation.setValue(numberLiteral);
	}

	public void testNewSingleMemberAnnotation2() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType();
		this.assertSourceDoesNotContain("@Foo");
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation2(declaration);
			}
		});
		this.assertSourceContains("@Foo(@Bar(88))");
	}

	void editNewSingleMemberAnnotation2(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		NumberLiteral numberLiteral = annotation.getAST().newNumberLiteral();
		numberLiteral.setToken("88");
		annotation.setValue(numberLiteral);
	}

	public void testNewSingleMemberAnnotation3() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo");
		String expected = "@Foo(nestedAnnotations=@Bar(88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation3(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation3(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		NumberLiteral numberLiteral = annotation.getAST().newNumberLiteral();
		numberLiteral.setToken("88");
		annotation.setValue(numberLiteral);
	}

	public void testNewSingleMemberAnnotation4() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo");
		String expected = "@Foo(@Bar(88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation4(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation4(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		NumberLiteral numberLiteral = annotation.getAST().newNumberLiteral();
		numberLiteral.setToken("88");
		annotation.setValue(numberLiteral);
	}

	public void testNewSingleMemberAnnotation5() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "String value(); annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(\"something\")");
		String expected = "@Foo(value=\"something\", nestedAnnotations=@Bar(88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation5(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation5(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		NumberLiteral numberLiteral = annotation.getAST().newNumberLiteral();
		numberLiteral.setToken("88");
		annotation.setValue(numberLiteral);
	}

	public void testNewSingleMemberAnnotation6() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] value();");
		this.createTestType("@annot.Foo(\"something\")");
		String expected = "@annot.Foo(@Bar(88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation6(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation6(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		NumberLiteral numberLiteral = annotation.getAST().newNumberLiteral();
		numberLiteral.setToken("88");
		annotation.setValue(numberLiteral);
	}

	public void testNewSingleMemberAnnotation7() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "String xxx(); annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(xxx=\"something\")");
		String expected = "@annot.Foo(xxx=\"something\", nestedAnnotations = @Bar(88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation7(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation7(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		NumberLiteral numberLiteral = annotation.getAST().newNumberLiteral();
		numberLiteral.setToken("88");
		annotation.setValue(numberLiteral);
	}

	public void testNewSingleMemberAnnotation8() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "String xxx(); annot.Bar[] value();");
		this.createTestType("@annot.Foo(xxx=\"something\")");
		String expected = "@annot.Foo(xxx=\"something\", value = @Bar(88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation8(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation8(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		NumberLiteral numberLiteral = annotation.getAST().newNumberLiteral();
		numberLiteral.setToken("88");
		annotation.setValue(numberLiteral);
	}

	public void testNewSingleMemberAnnotation9() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType();
		this.assertSourceDoesNotContain("@Bar");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newSingleMemberAnnotation();
		this.assertSourceContains("@Foo(nestedAnnotations={null,null,null,null,null,@Bar(MISSING)})");  // ???
	}

	public void testNewSingleMemberAnnotation10() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "String value(); annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(\"something\")");
		this.assertSourceDoesNotContain("@Bar");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newSingleMemberAnnotation();
		this.assertSourceContains("@Foo(value=\"something\", nestedAnnotations={null,null,null,null,null,@Bar(MISSING)})");  // ???
	}

	public void testNewSingleMemberAnnotation11() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] value();");
		this.createTestType("@annot.Foo({\"one\", \"two\"})");
		String expected = "@annot.Foo({@Bar(88), \"two\"})";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation11(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation11(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		NumberLiteral numberLiteral = annotation.getAST().newNumberLiteral();
		numberLiteral.setToken("88");
		annotation.setValue(numberLiteral);
	}

	public void testNewSingleMemberAnnotation12() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] value();");
		this.createTestType("@annot.Foo({\"one\", \"two\"})");
		this.assertSourceDoesNotContain("@Bar");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newSingleMemberAnnotation();
		this.assertSourceContains("@Bar(MISSING)})");  // split line
	}

	public void testNewSingleMemberAnnotation13() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] value();");
		this.createTestType("@annot.Foo(7)");
		String expected = "@annot.Foo(@Bar(88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation13(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation13(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		NumberLiteral numberLiteral = annotation.getAST().newNumberLiteral();
		numberLiteral.setToken("88");
		annotation.setValue(numberLiteral);
	}

	public void testNewSingleMemberAnnotation14() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] value();");
		this.createTestType("@annot.Foo(7)");
		this.assertSourceDoesNotContain("@Bar");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newSingleMemberAnnotation();
		this.assertSourceContains("@annot.Foo({7,null,null,null,null,@Bar(MISSING)})");
	}

	public void testNewSingleMemberAnnotation15() throws Exception {
		this.createAnnotationAndMembers("NotBar", "int value();");
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] value();");
		this.createTestType("@annot.Foo(@annot.NotBar)");
		String expected = "@annot.Foo(@Bar(88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation15(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation15(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		NumberLiteral numberLiteral = annotation.getAST().newNumberLiteral();
		numberLiteral.setToken("88");
		annotation.setValue(numberLiteral);
	}

	public void testNewSingleMemberAnnotation16() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo(@annot.Bar(55))");
		String expected = "@annot.Foo({@annot.Bar(55),@Bar(88)})";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation16(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation16(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 1, "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		NumberLiteral numberLiteral = annotation.getAST().newNumberLiteral();
		numberLiteral.setToken("88");
		annotation.setValue(numberLiteral);
	}

	public void testNewSingleMemberAnnotation17() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations={\"something\"})");
		String expected = "@annot.Foo(nestedAnnotations={@Bar(88)})";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation17(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation17(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		NumberLiteral numberLiteral = annotation.getAST().newNumberLiteral();
		numberLiteral.setToken("88");
		annotation.setValue(numberLiteral);
	}

	public void testNewSingleMemberAnnotation18() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations={\"something\"})");
		this.assertSourceDoesNotContain("@Bar");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newSingleMemberAnnotation();
		this.assertSourceContains("@Bar(MISSING)})");  // ???
	}

	public void testNewSingleMemberAnnotation19() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=\"something\")");
		String expected = "@annot.Foo(nestedAnnotations=@Bar(88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation19(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation19(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		NumberLiteral numberLiteral = annotation.getAST().newNumberLiteral();
		numberLiteral.setToken("88");
		annotation.setValue(numberLiteral);
	}

	public void testNewSingleMemberAnnotation20() throws Exception {
		this.createAnnotationAndMembers("NotBar", "int value();");
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=@annot.NotBar)");
		String expected = "@annot.Foo(nestedAnnotations=@Bar(88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation20(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation20(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		NumberLiteral numberLiteral = annotation.getAST().newNumberLiteral();
		numberLiteral.setToken("88");
		annotation.setValue(numberLiteral);
	}

	public void testNewSingleMemberAnnotation21() throws Exception {
		this.createAnnotationAndMembers("NotBar", "int value();");
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "Object[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=@annot.NotBar)");
		this.assertSourceDoesNotContain("@Bar");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newSingleMemberAnnotation();
		this.assertSourceContains("@annot.Foo(nestedAnnotations={@annot.NotBar,null,null,null,null,@Bar(MISSING)})");
	}

	public void testNewSingleMemberAnnotation22() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=@annot.Bar(77))");
		String expected = "@annot.Foo(nestedAnnotations=@Bar(88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation22(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation22(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		SingleMemberAnnotation annotation = daa.newSingleMemberAnnotation(declaration);
		NumberLiteral numberLiteral = annotation.getAST().newNumberLiteral();
		numberLiteral.setToken("88");
		annotation.setValue(numberLiteral);
	}

	public void testNewNormalAnnotation1() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType();
		this.assertSourceDoesNotContain("@Foo");
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation1(declaration);
			}
		});
		this.assertSourceContains("@Foo(nestedAnnotations=@Bar(xxx=88))");
	}

	void editNewNormalAnnotation1(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "xxx", 88);
	}

	public void testNewNormalAnnotation2() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType();
		this.assertSourceDoesNotContain("@Foo");
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation2(declaration);
			}
		});
		this.assertSourceContains("@Foo(@Bar(xxx=88))");
	}

	void editNewNormalAnnotation2(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "xxx", 88);
	}

	public void testNewNormalAnnotation3() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo");
		String expected = "@Foo(nestedAnnotations=@Bar(xxx=88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation3(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation3(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "xxx", 88);
	}

	public void testNewNormalAnnotation4() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations();");
		this.createTestType("@annot.Foo");
		String expected = "@Foo(@Bar(xxx=88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation4(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation4(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "xxx", 88);
	}

	public void testNewNormalAnnotation5() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations(); String value();");
		this.createTestType("@annot.Foo(\"something\")");
		String expected = "@Foo(value=\"something\", nestedAnnotations=@Bar(xxx=88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation5(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation5(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "xxx", 88);
	}

	public void testNewNormalAnnotation6() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo(\"something\")");
		String expected = "@annot.Foo(@Bar(xxx=88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation6(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation6(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "xxx", 88);
	}

	public void testNewNormalAnnotation7() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations(); String xxx();");
		this.createTestType("@annot.Foo(xxx=\"something\")");
		String expected = "@annot.Foo(xxx=\"something\", nestedAnnotations = @Bar(xxx = 88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation7(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation7(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "xxx", 88);
	}

	public void testNewNormalAnnotation8() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value(); String xxx();");
		this.createTestType("@annot.Foo(xxx=\"something\")");
		String expected = "@annot.Foo(xxx=\"something\", value = @Bar(xxx = 88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation8(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation8(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "xxx", 88);
	}

	public void testNewNormalAnnotation9() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations(); String xxx();");
		this.createTestType();
		this.assertSourceDoesNotContain("@Bar");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newNormalAnnotation();
		this.assertSourceContains("@Foo(nestedAnnotations={null,null,null,null,null,@Bar()})");
	}

	public void testNewNormalAnnotation10() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] nestedAnnotations(); String value();");
		this.createTestType("@annot.Foo(\"something\")");
		this.assertSourceDoesNotContain("@Bar");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newNormalAnnotation();
		this.assertSourceContains("@Foo(value=\"something\", nestedAnnotations={null,null,null,null,null,@Bar()})");
	}

	public void testNewNormalAnnotation11() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "Object[] value();");
		this.createTestType("@annot.Foo({\"one\", \"two\"})");
		String expected = "@annot.Foo({@Bar(xxx=88), \"two\"})";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation11(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation11(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "xxx", 88);
	}

	public void testNewNormalAnnotation12() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "Object[] value();");
		this.createTestType("@annot.Foo({\"one\", \"two\"})");
		this.assertSourceDoesNotContain("@Bar");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newNormalAnnotation();
		this.assertSourceContains("@Bar()})");  // split line
	}

	public void testNewNormalAnnotation13() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "Object[] value();");
		this.createTestType("@annot.Foo(7)");
		String expected = "@annot.Foo(@Bar(xxx=88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation13(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation13(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "xxx", 88);
	}

	public void testNewNormalAnnotation14() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "Object[] value();");
		this.createTestType("@annot.Foo(7)");
		this.assertSourceDoesNotContain("@Bar");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newNormalAnnotation();
		this.assertSourceContains("@annot.Foo({7,null,null,null,null,@Bar()})");
	}

	public void testNewNormalAnnotation15() throws Exception {
		this.createAnnotationAndMembers("NotBar", "int xxx();");
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "Object[] value();");
		this.createTestType("@annot.Foo(@annot.NotBar)");
		String expected = "@annot.Foo(@Bar(xxx=88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation15(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation15(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "xxx", 88);
	}

	public void testNewNormalAnnotation16() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo(@annot.Bar(55))");
		String expected = "@annot.Foo({@annot.Bar(55),@Bar(xxx=88)})";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation16(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation16(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 1, "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "xxx", 88);
	}

	public void testNewNormalAnnotation17() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "Object[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations={\"something\"})");
		String expected = "@annot.Foo(nestedAnnotations={@Bar(xxx=88)})";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation17(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation17(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "xxx", 88);
	}

	public void testNewNormalAnnotation18() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "Object[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations={\"something\"})");
		this.assertSourceDoesNotContain("@Bar");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newNormalAnnotation();
		this.assertSourceContains("@Bar()})");  // split line
	}

	public void testNewNormalAnnotation19() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "Object[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=\"something\")");
		String expected = "@annot.Foo(nestedAnnotations=@Bar(xxx=88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation19(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation19(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "xxx", 88);
	}

	public void testNewNormalAnnotation20() throws Exception {
		this.createAnnotationAndMembers("NotBar", "int xxx();");
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "Object[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=@annot.NotBar)");
		String expected = "@annot.Foo(nestedAnnotations=@Bar(xxx=88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation20(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation20(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.annotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "xxx", 88);
	}

	public void testNewNormalAnnotation21() throws Exception {
		this.createAnnotationAndMembers("NotBar", "int xxx();");
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "Object[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=@annot.NotBar)");
		this.assertSourceDoesNotContain("@Bar");

		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 5, "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.annotation();
		assertNull(annotation);

		aa.newNormalAnnotation();
		this.assertSourceContains("@annot.Foo(nestedAnnotations={@annot.NotBar,null,null,null,null,@Bar()})");
	}

	public void testNewNormalAnnotation22() throws Exception {
		this.createAnnotationAndMembers("Bar", "int xxx();");
		this.createAnnotationAndMembers("Foo", "Object[] nestedAnnotations();");
		this.createTestType("@annot.Foo(nestedAnnotations=@annot.Bar(77))");
		String expected = "@annot.Foo(nestedAnnotations=@Bar(xxx=88))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedIndexedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation22(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation22(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotations", 0, "annot.Bar");
		NormalAnnotation annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "xxx", 88);
	}

	public void testMoveAnnotation1() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo(@annot.Bar(00))");
		String expected = "@annot.Foo({null,@annot.Bar(00)})";
		this.assertSourceDoesNotContain(expected);

		IndexedDeclarationAnnotationAdapter idaa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		IndexedAnnotationAdapter iaa = new MemberIndexedAnnotationAdapter(this.idField(), idaa);
		Annotation annotation = iaa.annotation();
		assertNotNull(annotation);

		iaa.moveAnnotation(1);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation2() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo({null, @annot.Bar(11)})");
		String expected = "@annot.Foo(@annot.Bar(11))";
		this.assertSourceDoesNotContain(expected);

		IndexedDeclarationAnnotationAdapter idaa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 1, "annot.Bar");
		IndexedAnnotationAdapter iaa = new MemberIndexedAnnotationAdapter(this.idField(), idaa);
		Annotation annotation = iaa.annotation();
		assertNotNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation3() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo({@annot.Bar(00), @annot.Bar(11), @annot.Bar(22), @annot.Bar(33)})");
		String expected = "@annot.Foo({@annot.Bar(33), @annot.Bar(11), @annot.Bar(22)})";
		this.assertSourceDoesNotContain(expected);

		IndexedDeclarationAnnotationAdapter idaa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 3, "annot.Bar");
		IndexedAnnotationAdapter iaa = new MemberIndexedAnnotationAdapter(this.idField(), idaa);
		Annotation annotation = iaa.annotation();
		assertNotNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation4() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo({@annot.Bar(00), @annot.Bar(11), @annot.Bar(22), @annot.Bar(33), @annot.Bar(44)})");
		String expected = "@annot.Foo({@annot.Bar(33), @annot.Bar(11), @annot.Bar(22), null, @annot.Bar(44)})";
		this.assertSourceDoesNotContain(expected);

		IndexedDeclarationAnnotationAdapter idaa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 3, "annot.Bar");
		IndexedAnnotationAdapter iaa = new MemberIndexedAnnotationAdapter(this.idField(), idaa);
		Annotation annotation = iaa.annotation();
		assertNotNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation5() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo({@annot.Bar(00), @annot.Bar(11), @annot.Bar(22)})");
		String expected = "@annot.Foo({@annot.Bar(00), @annot.Bar(11), null, @annot.Bar(22)})";
		this.assertSourceDoesNotContain(expected);

		IndexedDeclarationAnnotationAdapter idaa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 2, "annot.Bar");
		IndexedAnnotationAdapter iaa = new MemberIndexedAnnotationAdapter(this.idField(), idaa);
		Annotation annotation = iaa.annotation();
		assertNotNull(annotation);

		iaa.moveAnnotation(3);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation6() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo({@annot.Bar(00), @annot.Bar(11), @annot.Bar(22)})");
		String expected = "@annot.Foo({null, @annot.Bar(11), @annot.Bar(22), @annot.Bar(00)})";
		this.assertSourceDoesNotContain(expected);

		IndexedDeclarationAnnotationAdapter idaa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		IndexedAnnotationAdapter iaa = new MemberIndexedAnnotationAdapter(this.idField(), idaa);
		Annotation annotation = iaa.annotation();
		assertNotNull(annotation);

		iaa.moveAnnotation(3);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation7() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo({@annot.Bar(00), @annot.Bar(11), @annot.Bar(22)})");
		String expected = "@annot.Foo({null, @annot.Bar(11), @annot.Bar(22)})";
		this.assertSourceDoesNotContain(expected);

		IndexedDeclarationAnnotationAdapter idaa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 3, "annot.Bar");
		IndexedAnnotationAdapter iaa = new MemberIndexedAnnotationAdapter(this.idField(), idaa);
		Annotation annotation = iaa.annotation();
		assertNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation8() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo({@annot.Bar(00), @annot.Bar(11), @annot.Bar(22), null, @annot.Bar(44)})");
		String expected = "@annot.Foo({null, @annot.Bar(11), @annot.Bar(22), null, @annot.Bar(44)})";
		this.assertSourceDoesNotContain(expected);

		IndexedDeclarationAnnotationAdapter idaa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 3, "annot.Bar");
		IndexedAnnotationAdapter iaa = new MemberIndexedAnnotationAdapter(this.idField(), idaa);
		Annotation annotation = iaa.annotation();
		assertNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation9() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		String expected = "@annot.Foo({null, @annot.Bar(11), @annot.Bar(22)})";
		this.createTestType(expected);  // the source should be unchanged

		IndexedDeclarationAnnotationAdapter idaa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 0, "annot.Bar");
		IndexedAnnotationAdapter iaa = new MemberIndexedAnnotationAdapter(this.idField(), idaa);
		Annotation annotation = iaa.annotation();
		assertNull(annotation);

		iaa.moveAnnotation(3);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation10() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo({@annot.Bar(00), @annot.Bar(11)})");
		String expected = "@annot.Foo(@annot.Bar(00))";

		IndexedDeclarationAnnotationAdapter idaa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 2, "annot.Bar");
		IndexedAnnotationAdapter iaa = new MemberIndexedAnnotationAdapter(this.idField(), idaa);
		Annotation annotation = iaa.annotation();
		assertNull(annotation);
		iaa.moveAnnotation(1);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation11() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo(@annot.Bar(00))");

		IndexedDeclarationAnnotationAdapter idaa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 1, "annot.Bar");
		IndexedAnnotationAdapter iaa = new MemberIndexedAnnotationAdapter(this.idField(), idaa);
		Annotation annotation = iaa.annotation();
		assertNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceDoesNotContain("Foo");
	}

	public void testMoveAnnotation12() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo({@annot.Bar(00), null, @annot.Bar(22)})");
		String expected = "@annot.Foo(@annot.Bar(22))";

		IndexedDeclarationAnnotationAdapter idaa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 2, "annot.Bar");
		IndexedAnnotationAdapter iaa = new MemberIndexedAnnotationAdapter(this.idField(), idaa);
		Annotation annotation = iaa.annotation();
		assertNotNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected);
	}

	public void testMoveAnnotation13() throws Exception {
		this.createAnnotationAndMembers("Bar", "int value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar[] value();");
		this.createTestType("@annot.Foo({@annot.Bar(00), @annot.Bar(11), null, @annot.Bar(33)})");
		String expected = "@annot.Foo({@annot.Bar(33), @annot.Bar(11)})";

		IndexedDeclarationAnnotationAdapter idaa = new NestedIndexedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", 3, "annot.Bar");
		IndexedAnnotationAdapter iaa = new MemberIndexedAnnotationAdapter(this.idField(), idaa);
		Annotation annotation = iaa.annotation();
		assertNotNull(annotation);

		iaa.moveAnnotation(0);
		this.assertSourceContains(expected);
	}

}
