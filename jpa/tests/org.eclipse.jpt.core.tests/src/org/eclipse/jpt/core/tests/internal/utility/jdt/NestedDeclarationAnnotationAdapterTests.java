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
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;

public class NestedDeclarationAnnotationAdapterTests extends AnnotationTestCase {

	public NestedDeclarationAnnotationAdapterTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("annot", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}

	public void testAnnotation1() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation();");
		this.createTestType("@annot.Foo(nestedAnnotation=@annot.Bar)");

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNotNull(annotation);
		assertEquals("annot.Bar", annotation.getTypeName().getFullyQualifiedName());
		assertTrue(annotation.isMarkerAnnotation());
	}

	public void testAnnotation2() throws Exception {
		this.createAnnotationAndMembers("Baz", "String value();");
		this.createAnnotationAndMembers("Bar", "annot.Baz yana();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation();");
		this.createTestType("@annot.Foo(nestedAnnotation=@annot.Bar(yana=@annot.Baz))");

		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "nestedAnnotation", "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedDeclarationAnnotationAdapter(daa2, "yana", "annot.Baz");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa3);
		Annotation annotation = aa.getAnnotation();
		assertNotNull(annotation);
		assertEquals("annot.Baz", annotation.getTypeName().getFullyQualifiedName());
		assertTrue(annotation.isMarkerAnnotation());
	}

	public void testAnnotationNull1() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation();");
		this.createTestType("@annot.Foo()");

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNull(annotation);
	}

	public void testAnnotationNull2() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation();");
		this.createTestType();

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNull(annotation);
	}

	public void testAnnotationNull3() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "String nestedAnnotation();");
		this.createTestType("@annot.Foo(nestedAnnotation=\"annot.Bar\")");

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNull(annotation);
	}

	public void testAnnotationNull4() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Bar2", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar2 nestedAnnotation();");
		this.createTestType("@annot.Foo(nestedAnnotation=@annot.Bar2)");

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNull(annotation);
	}

	public void testRemoveAnnotation1() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation();");
		String na = "@annot.Foo(nestedAnnotation=@annot.Bar)";
		this.createTestType(na);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceDoesNotContain(na);
		this.assertSourceDoesNotContain("Foo");
	}

	public void testRemoveAnnotation1a() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation();");
		String na = "@annot.Foo(nestedAnnotation=@annot.Bar)";
		this.createTestType(na);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar", false);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceDoesNotContain(na);
		this.assertSourceContains("Foo");
	}

	public void testRemoveAnnotation2() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation();");
		this.createTestType();
		this.assertSourceDoesNotContain("Foo");

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNull(annotation);

		aa.removeAnnotation();
		this.assertSourceDoesNotContain("Foo");
	}

	public void testRemoveAnnotation3() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "String nestedAnnotation();");
		String na = "@annot.Foo(nestedAnnotation=\"annot.Bar\")";
		this.createTestType(na);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNull(annotation);

		aa.removeAnnotation();
		this.assertSourceContains(na);
	}

	public void testRemoveAnnotation4() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Bar2", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar2 nestedAnnotation();");
		String na = "@annot.Foo(nestedAnnotation=@annot.Bar2)";
		this.createTestType(na);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNull(annotation);

		aa.removeAnnotation();
		this.assertSourceContains(na);
	}

	public void testRemoveAnnotation5() throws Exception {
		this.createAnnotationAndMembers("Baz", "String value();");
		this.createAnnotationAndMembers("Bar", "annot.Baz nestedAnnotation2();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation1();");
		String na = "@annot.Foo(nestedAnnotation1=@annot.Bar(nestedAnnotation2=@annot.Baz))";
		this.createTestType(na);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daaFoo = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daaBar = new NestedDeclarationAnnotationAdapter(daaFoo, "nestedAnnotation1", "annot.Bar");
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(daaBar, "nestedAnnotation2", "annot.Baz");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceDoesNotContain(na);
		this.assertSourceDoesNotContain("Foo");
		this.assertSourceDoesNotContain("Bar");
		this.assertSourceDoesNotContain("Baz");
	}

	public void testRemoveAnnotation5a() throws Exception {
		this.createAnnotationAndMembers("Baz", "String value();");
		this.createAnnotationAndMembers("Bar", "annot.Baz nestedAnnotation2();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation1();");
		String na = "@annot.Foo(nestedAnnotation1=@annot.Bar(nestedAnnotation2=@annot.Baz))";
		this.createTestType(na);
		this.assertSourceContains(na);

		DeclarationAnnotationAdapter daaFoo = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daaBar = new NestedDeclarationAnnotationAdapter(daaFoo, "nestedAnnotation1", "annot.Bar", false);
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(daaBar, "nestedAnnotation2", "annot.Baz", false);
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceDoesNotContain(na);
		this.assertSourceContains("@annot.Foo(nestedAnnotation1=@Bar)");
	}

	public void testNewMarkerAnnotation1() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation();");
		this.createTestType();
		this.assertSourceDoesNotContain("Foo");

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@Foo(nestedAnnotation=@Bar)");
	}

	public void testNewMarkerAnnotation2() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		this.createTestType();
		this.assertSourceDoesNotContain("Foo");

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("@Foo(@Bar)");
	}

	public void testNewMarkerAnnotation3() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation();");
		this.createTestType("@annot.Foo");
		String expected = "@Foo(nestedAnnotation=@Bar)";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation4() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		this.createTestType("@annot.Foo");
		String expected = "@Foo(@Bar)";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation5() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation(); String value();");
		this.createTestType("@annot.Foo(\"something\")");
		String expected = "@Foo(value=\"something\", nestedAnnotation=@Bar)";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation6() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "Object value();");
		this.createTestType("@annot.Foo(\"something\")");
		String expected = "@annot.Foo(@Bar)";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation7() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "String xxx(); annot.Bar nestedAnnotation();");
		this.createTestType("@annot.Foo(xxx=\"something\")");
		String expected = "@annot.Foo(xxx=\"something\", nestedAnnotation = @Bar)";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewMarkerAnnotation8() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "String xxx(); annot.Bar value();");
		this.createTestType("@annot.Foo(xxx=\"something\")");
		String expected = "@annot.Foo(xxx=\"something\", value = @Bar)";
		this.assertSourceDoesNotContain(expected);

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		Annotation annotation = aa.getAnnotation();
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains(expected);
	}

	public void testNewSingleMemberAnnotation1() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation();");
		this.createTestType();
		this.assertSourceDoesNotContain("Foo");
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation1(declaration);
			}
		});
		this.assertSourceContains("@Foo(nestedAnnotation=@Bar(\"test string literal\"))");
	}

	void editNewSingleMemberAnnotation1(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		StringLiteral stringLiteral = annotation.getAST().newStringLiteral();
		stringLiteral.setLiteralValue("test string literal");
		annotation.setValue(stringLiteral);
	}

	public void testNewSingleMemberAnnotation2() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation();");
		this.createTestType();
		this.assertSourceDoesNotContain("Foo");

		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", "annot.Bar");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(), daa);
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) aa.getAnnotation();
		assertNull(annotation);

		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation2(declaration);
			}
		});
		this.assertSourceContains("@Foo(@Bar(\"test string literal\"))");
	}

	void editNewSingleMemberAnnotation2(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		StringLiteral stringLiteral = annotation.getAST().newStringLiteral();
		stringLiteral.setLiteralValue("test string literal");
		annotation.setValue(stringLiteral);
	}

	public void testNewSingleMemberAnnotation3() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation();");
		this.createTestType("@annot.Foo");
		String expected = "@Foo(nestedAnnotation=@Bar(\"test string literal\"))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation3(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation3(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		StringLiteral stringLiteral = annotation.getAST().newStringLiteral();
		stringLiteral.setLiteralValue("test string literal");
		annotation.setValue(stringLiteral);
	}

	public void testNewSingleMemberAnnotation4() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		this.createTestType("@annot.Foo");
		String expected = "@Foo(@Bar(\"test string literal\"))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation4(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation4(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		StringLiteral stringLiteral = annotation.getAST().newStringLiteral();
		stringLiteral.setLiteralValue("test string literal");
		annotation.setValue(stringLiteral);
	}

	public void testNewSingleMemberAnnotation5() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation(); String value();");
		this.createTestType("@annot.Foo(\"something\")");
		String expected = "@Foo(value=\"something\", nestedAnnotation=@Bar(\"test string literal\"))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation5(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation5(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		StringLiteral stringLiteral = annotation.getAST().newStringLiteral();
		stringLiteral.setLiteralValue("test string literal");
		annotation.setValue(stringLiteral);
	}

	public void testNewSingleMemberAnnotation6() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "Object value();");
		this.createTestType("@annot.Foo(\"something\")");
		String expected = "@annot.Foo(@Bar(\"test string literal\"))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation6(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation6(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		StringLiteral stringLiteral = annotation.getAST().newStringLiteral();
		stringLiteral.setLiteralValue("test string literal");
		annotation.setValue(stringLiteral);
	}

	public void testNewSingleMemberAnnotation7() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation(); String xxx();");
		this.createTestType("@annot.Foo(xxx=\"something\")");
		String expected = "@annot.Foo(xxx=\"something\", nestedAnnotation = @Bar(\"test string literal\"))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation7(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation7(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		StringLiteral stringLiteral = annotation.getAST().newStringLiteral();
		stringLiteral.setLiteralValue("test string literal");
		annotation.setValue(stringLiteral);
	}

	public void testNewSingleMemberAnnotation8() throws Exception {
		this.createAnnotationAndMembers("Bar", "String value();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value(); String xxx();");
		this.createTestType("@annot.Foo(xxx=\"something\")");
		String expected = "@annot.Foo(xxx=\"something\", value = @Bar(\"test string literal\"))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation8(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewSingleMemberAnnotation8(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", "annot.Bar");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		StringLiteral stringLiteral = annotation.getAST().newStringLiteral();
		stringLiteral.setLiteralValue("test string literal");
		annotation.setValue(stringLiteral);
	}

	public void testNewNormalAnnotation1() throws Exception {
		this.createAnnotationAndMembers("Bar", "String yyy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation();");
		this.createTestType();
		this.assertSourceDoesNotContain("Foo");
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation1(declaration);
			}
		});
		this.assertSourceContains("@Foo(nestedAnnotation=@Bar(yyy=\"test string literal\"))");
	}

	void editNewNormalAnnotation1(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "yyy", "test string literal");
	}

	public void testNewNormalAnnotation2() throws Exception {
		this.createAnnotationAndMembers("Bar", "String yyy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		this.createTestType();
		this.assertSourceDoesNotContain("Foo");
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation2(declaration);
			}
		});
		this.assertSourceContains("@Foo(@Bar(yyy=\"test string literal\"))");
	}

	void editNewNormalAnnotation2(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "yyy", "test string literal");
	}

	public void testNewNormalAnnotation3() throws Exception {
		this.createAnnotationAndMembers("Bar", "String yyy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation();");
		this.createTestType("@annot.Foo");
		String expected = "@Foo(nestedAnnotation=@Bar(yyy=\"test string literal\"))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation3(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation3(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "yyy", "test string literal");
	}

	public void testNewNormalAnnotation4() throws Exception {
		this.createAnnotationAndMembers("Bar", "String yyy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		this.createTestType("@annot.Foo");
		String expected = "@Foo(@Bar(yyy=\"test string literal\"))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation4(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation4(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "yyy", "test string literal");
	}

	public void testNewNormalAnnotation5() throws Exception {
		this.createAnnotationAndMembers("Bar", "String yyy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar nestedAnnotation(); String value();");
		this.createTestType("@annot.Foo(\"something\")");
		String expected = "@Foo(value=\"something\", nestedAnnotation=@Bar(yyy=\"test string literal\"))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation5(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation5(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "yyy", "test string literal");
	}

	public void testNewNormalAnnotation6() throws Exception {
		this.createAnnotationAndMembers("Bar", "String yyy();");
		this.createAnnotationAndMembers("Foo", "Object value();");
		this.createTestType("@annot.Foo(\"something\")");
		String expected = "@annot.Foo(@Bar(yyy=\"test string literal\"))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation6(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation6(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "yyy", "test string literal");
	}

	public void testNewNormalAnnotation7() throws Exception {
		this.createAnnotationAndMembers("Bar", "String yyy();");
		this.createAnnotationAndMembers("Foo", "String xxx(); annot.Bar nestedAnnotation();");
		this.createTestType("@annot.Foo(xxx=\"something\")");
		String expected = "@annot.Foo(xxx=\"something\", nestedAnnotation = @Bar(yyy = \"test string literal\"))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation7(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation7(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "nestedAnnotation", "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "yyy", "test string literal");
	}

	public void testNewNormalAnnotation8() throws Exception {
		this.createAnnotationAndMembers("Bar", "String yyy();");
		this.createAnnotationAndMembers("Foo", "String xxx(); annot.Bar value();");
		this.createTestType("@annot.Foo(xxx=\"something\")");
		String expected = "@annot.Foo(xxx=\"something\", value = @Bar(yyy = \"test string literal\"))";
		this.assertSourceDoesNotContain(expected);
		this.idField().edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NestedDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation8(declaration);
			}
		});
		this.assertSourceContains(expected);
	}

	void editNewNormalAnnotation8(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new NestedDeclarationAnnotationAdapter(
				new SimpleDeclarationAnnotationAdapter("annot.Foo"), "value", "annot.Bar");
		NormalAnnotation annotation = (NormalAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		this.addMemberValuePair(annotation, "yyy", "test string literal");
	}

}
