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

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;

public class SimpleDeclarationAnnotationAdapterTests extends AnnotationTestCase {

	public SimpleDeclarationAnnotationAdapterTests(String name) {
		super(name);
	}

	private void createAnnotation(String annotationName) throws Exception {
		this.javaProject.createCompilationUnit("annot", annotationName + ".java", "public @interface " + annotationName + " {}");
	}

	public void testAnnotation1() throws Exception {
		this.createAnnotation("Foo");
		ICompilationUnit cu = this.createTestType("@annot.Foo");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);
		assertEquals("annot.Foo", annotation.getTypeName().getFullyQualifiedName());
		assertTrue(annotation.isMarkerAnnotation());
	}

	public void testAnnotation2() throws Exception {
		this.createAnnotation("Foo");
		ICompilationUnit cu = this.createTestType("@annot.Foo(1) @annot.Foo(2)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);
		assertEquals("annot.Foo", annotation.getTypeName().getFullyQualifiedName());
		assertTrue(annotation.isSingleMemberAnnotation());
		Expression value = ((SingleMemberAnnotation) annotation).getValue();
		assertEquals(ASTNode.NUMBER_LITERAL, value.getNodeType());
		assertEquals("1", ((NumberLiteral) value).getToken());
	}

	public void testAnnotation3() throws Exception {
		this.createAnnotation("Foo");
		ICompilationUnit cu = this.createTestType("annot.Foo", "@Foo");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);
		assertEquals("Foo", annotation.getTypeName().getFullyQualifiedName());
		assertTrue(annotation.isMarkerAnnotation());
	}

	public void testAnnotationNull1() throws Exception {
		this.createAnnotation("Foo");
		ICompilationUnit cu = this.createTestType();
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);
	}

	public void testAnnotationNull2() throws Exception {
		this.createAnnotation("Foo");
		this.createAnnotation("Fop");
		ICompilationUnit cu = this.createTestType("@annot.Fop");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);
		this.assertSourceContains("@annot.Fop", cu);
	}

	public void testAnnotationNull3() throws Exception {
		this.createAnnotation("Foo");
		ICompilationUnit cu = this.createTestType("@annot.Foo");
		// un-qualified name
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("Foo");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);
		this.assertSourceContains("@annot.Foo", cu);
	}

	public void testRemoveAnnotation1() throws Exception {
		this.createAnnotation("Foo");
		ICompilationUnit cu = this.createTestType("@annot.Foo");
		this.assertSourceContains("@annot.Foo", cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceDoesNotContain("@annot.Foo", cu);
	}

	public void testRemoveAnnotation2() throws Exception {
		this.createAnnotation("Foo");
		ICompilationUnit cu = this.createTestType("@annot.Foo(1) @annot.Foo(2)");
		this.assertSourceContains("@annot.Foo(1) @annot.Foo(2)", cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		aa.removeAnnotation();
		this.assertSourceDoesNotContain("@annot.Foo(1)", cu);
		this.assertSourceContains("@annot.Foo(2)", cu);
	}

	public void testNewMarkerAnnotation1() throws Exception {
		this.createAnnotation("Foo");
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain("@annot.Foo", cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("import annot.Foo;", cu);
		this.assertSourceContains("@Foo", cu);
	}

	public void testNewMarkerAnnotation2() throws Exception {
		this.createAnnotation("Foo");
		ICompilationUnit cu = this.createTestType("@annot.Foo(88)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		AnnotationAdapter aa = new MemberAnnotationAdapter(this.idField(cu), daa);
		Annotation annotation = aa.getAnnotation(this.buildASTRoot(cu));
		assertNotNull(annotation);

		aa.newMarkerAnnotation();
		this.assertSourceContains("import annot.Foo;", cu);
		this.assertSourceContains("@Foo", cu);
		this.assertSourceDoesNotContain("@annot.Foo(88)", cu);
	}

	public void testNewSingleMemberAnnotation() throws Exception {
		this.createAnnotation("Foo");
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain("@Foo", cu);
		this.idField(cu).edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				SimpleDeclarationAnnotationAdapterTests.this.editNewSingleMemberAnnotation(declaration);
			}
		});
		this.assertSourceContains("import annot.Foo;", cu);
		this.assertSourceContains("@Foo(\"test string literal\")", cu);
	}

	void editNewSingleMemberAnnotation(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		SingleMemberAnnotation annotation = (SingleMemberAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newSingleMemberAnnotation(declaration);
		StringLiteral stringLiteral = annotation.getAST().newStringLiteral();
		stringLiteral.setLiteralValue("test string literal");
		annotation.setValue(stringLiteral);
	}

	public void testNewNormalAnnotation() throws Exception {
		this.createAnnotation("Foo");
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain("@Foo", cu);
		this.idField(cu).edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				SimpleDeclarationAnnotationAdapterTests.this.editNewNormalAnnotation(declaration);
			}
		});
		this.assertSourceContains("import annot.Foo;", cu);
		this.assertSourceContains("@Foo(bar=\"test string literal\")", cu);
	}

	void editNewNormalAnnotation(ModifiedDeclaration declaration) {
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		NormalAnnotation annotation = (NormalAnnotation) daa.getAnnotation(declaration);
		assertNull(annotation);
		annotation = daa.newNormalAnnotation(declaration);
		MemberValuePair mvp = this.newMemberValuePair(annotation.getAST(), "bar", "test string literal");
		this.values(annotation).add(mvp);
	}

}
