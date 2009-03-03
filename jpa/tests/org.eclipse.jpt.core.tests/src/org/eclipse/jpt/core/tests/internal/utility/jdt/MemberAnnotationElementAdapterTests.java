/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.utility.jdt;

import java.util.Arrays;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.ASTNodeTextRange;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotationStringArrayExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.BooleanExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.CharacterStringExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.EnumArrayDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NumberIntegerExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.PrimitiveTypeStringExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.StringExpressionConverter;
import org.eclipse.jpt.core.internal.utility.jdt.TypeStringExpressionConverter;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.ExpressionConverter;

@SuppressWarnings("nls")
public class MemberAnnotationElementAdapterTests extends AnnotationTestCase {

	public MemberAnnotationElementAdapterTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createCompilationUnit("annot", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}

	private void createEnum(String enumName, String enumBody) throws Exception {
		this.createEnum("enums", enumName, enumBody);
	}

	private void createEnum(String packageName, String enumName, String enumBody) throws Exception {
		this.javaProject.createCompilationUnit(packageName, enumName + ".java", "public enum " + enumName + " { " + enumBody + " }");
	}

	public void testValue1() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=\"xxx\")");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertEquals("xxx", aea.getValue());
	}

	public void testValue2() throws Exception {
		this.createAnnotationAndMembers("Foo", "int bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=48)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<Integer> daea = new ConversionDeclarationAnnotationElementAdapter<Integer>(daa, "bar", NumberIntegerExpressionConverter.instance());
		AnnotationElementAdapter<Integer> aea = new MemberAnnotationElementAdapter<Integer>(this.idField(cu), daea);
		assertEquals(Integer.valueOf(48), aea.getValue());
	}

	public void testValue3() throws Exception {
		this.createAnnotationAndMembers("Foo", "char bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar='c')");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String>(daa, "bar", CharacterStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertEquals("c", aea.getValue());
	}

	public void testValue4() throws Exception {
		this.createAnnotationAndMembers("Foo", "boolean bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=false)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<Boolean> daea = new ConversionDeclarationAnnotationElementAdapter<Boolean>(daa, "bar", BooleanExpressionConverter.instance());
		AnnotationElementAdapter<Boolean> aea = new MemberAnnotationElementAdapter<Boolean>(this.idField(cu), daea);
		assertEquals(Boolean.FALSE, aea.getValue());
	}

	public void testValue5() throws Exception {
		this.createAnnotationAndMembers("Baz", "boolean fred();");
		this.createAnnotationAndMembers("Bar", "annot.Baz jimmy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(@annot.Bar(jimmy=@annot.Baz(fred=false)))");
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "value", "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedDeclarationAnnotationAdapter(daa2, "jimmy", "annot.Baz");
		DeclarationAnnotationElementAdapter<Boolean> daea = new ConversionDeclarationAnnotationElementAdapter<Boolean>(daa3, "fred", BooleanExpressionConverter.instance());
		AnnotationElementAdapter<Boolean> aea = new MemberAnnotationElementAdapter<Boolean>(this.idField(cu), daea);
		assertEquals(Boolean.FALSE, aea.getValue());
	}

	public void testValue6() throws Exception {
		this.createAnnotationAndMembers("Foo", "boolean value();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(false)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<Boolean> daea = new ConversionDeclarationAnnotationElementAdapter<Boolean>(daa, BooleanExpressionConverter.instance());
		AnnotationElementAdapter<Boolean> aea = new MemberAnnotationElementAdapter<Boolean>(this.idField(cu), daea);
		assertEquals(Boolean.FALSE, aea.getValue());
	}

	public void testValueNull1() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertNull(aea.getValue());
	}

	public void testValueNull2() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		ICompilationUnit cu = this.createTestType();
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertNull(aea.getValue());
	}

	public void testValueNull3() throws Exception {
		this.createAnnotationAndMembers("Baz", "String fred();");
		this.createAnnotationAndMembers("Bar", "annot.Baz jimmy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(@annot.Bar(jimmy=@annot.Baz))");
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "value", "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedDeclarationAnnotationAdapter(daa2, "jimmy", "annot.Baz");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa3, "fred");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertNull(aea.getValue());
	}

	public void testValueStringConcatenation() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=\"xxx\" + \"yyy\" + \"zzz\")");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertEquals("xxxyyyzzz", aea.getValue());
	}

	public void testValueStringConstant() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		// just a bit hacky:
		ICompilationUnit cu = this.createTestType("private static final String FOO_BAR = \"xxx\"; @annot.Foo(bar=FOO_BAR + \"yyy\" + \"zzz\")");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertEquals("xxxyyyzzz", aea.getValue());
	}

	public void testValueNumberArithmetic() throws Exception {
		this.createAnnotationAndMembers("Foo", "int bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=47 - 7 + 2 * 1 / 1)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<Integer> daea = ConversionDeclarationAnnotationElementAdapter.forNumbers(daa, "bar");
		AnnotationElementAdapter<Integer> aea = new MemberAnnotationElementAdapter<Integer>(this.idField(cu), daea);
		assertEquals(Integer.valueOf(42), aea.getValue());
	}

	public void testValueNumberShift() throws Exception {
		this.createAnnotationAndMembers("Foo", "int bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=2 << 2)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<Integer> daea = ConversionDeclarationAnnotationElementAdapter.forNumbers(daa, "bar");
		AnnotationElementAdapter<Integer> aea = new MemberAnnotationElementAdapter<Integer>(this.idField(cu), daea);
		assertEquals(Integer.valueOf(8), aea.getValue());
	}

	public void testValueNumberConstant() throws Exception {
		this.createAnnotationAndMembers("Foo", "int bar();");
		// just a bit hacky:
		ICompilationUnit cu = this.createTestType("private static final int FOO_BAR = 77; @annot.Foo(bar=FOO_BAR)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<Integer> daea = ConversionDeclarationAnnotationElementAdapter.forNumbers(daa, "bar");
		AnnotationElementAdapter<Integer> aea = new MemberAnnotationElementAdapter<Integer>(this.idField(cu), daea);
		assertEquals(Integer.valueOf(77), aea.getValue());
	}

	public void testValueCharacterConstant() throws Exception {
		this.createAnnotationAndMembers("Foo", "char bar();");
		// just a bit hacky:
		ICompilationUnit cu = this.createTestType("private static final char FOO_BAR = 'Q'; @annot.Foo(bar=FOO_BAR)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forCharacters(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertEquals("Q", aea.getValue());
	}

	public void testValueCharacterCast() throws Exception {
		this.createAnnotationAndMembers("Foo", "char bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=(char) 0x41)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forCharacters(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertEquals("A", aea.getValue());
	}

	public void testValueBooleanOperator1() throws Exception {
		this.createAnnotationAndMembers("Foo", "boolean bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=7 > 2)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<Boolean> daea = ConversionDeclarationAnnotationElementAdapter.forBooleans(daa, "bar");
		AnnotationElementAdapter<Boolean> aea = new MemberAnnotationElementAdapter<Boolean>(this.idField(cu), daea);
		assertEquals(Boolean.TRUE, aea.getValue());
	}

	public void testValueBooleanOperator2() throws Exception {
		this.createAnnotationAndMembers("Foo", "boolean bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=7 == 2)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<Boolean> daea = ConversionDeclarationAnnotationElementAdapter.forBooleans(daa, "bar");
		AnnotationElementAdapter<Boolean> aea = new MemberAnnotationElementAdapter<Boolean>(this.idField(cu), daea);
		assertEquals(Boolean.FALSE, aea.getValue());
	}

	public void testValueBooleanOperator3() throws Exception {
		this.createAnnotationAndMembers("Foo", "boolean bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=(7 != 2) && false)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<Boolean> daea = ConversionDeclarationAnnotationElementAdapter.forBooleans(daa, "bar");
		AnnotationElementAdapter<Boolean> aea = new MemberAnnotationElementAdapter<Boolean>(this.idField(cu), daea);
		assertEquals(Boolean.FALSE, aea.getValue());
	}

	public void testValueBooleanOperator4() throws Exception {
		this.createAnnotationAndMembers("Foo", "boolean bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=(7 != 2) ? false : true)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<Boolean> daea = ConversionDeclarationAnnotationElementAdapter.forBooleans(daa, "bar");
		AnnotationElementAdapter<Boolean> aea = new MemberAnnotationElementAdapter<Boolean>(this.idField(cu), daea);
		assertEquals(Boolean.FALSE, aea.getValue());
	}

	public void testValueInvalidValue1() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=77)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertNull(aea.getValue());
	}

	public void testValueInvalidValue2() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=bazzzz)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertNull(aea.getValue());
	}

	public void testValueInvalidValue3() throws Exception {
		this.createAnnotationAndMembers("Foo", "boolean bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=bazzzz)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<Boolean> daea = ConversionDeclarationAnnotationElementAdapter.forBooleans(daa, "bar");
		AnnotationElementAdapter<Boolean> aea = new MemberAnnotationElementAdapter<Boolean>(this.idField(cu), daea);
		assertNull(aea.getValue());
	}

	public void testValueInvalidValue4() throws Exception {
		this.createAnnotationAndMembers("Foo", "char bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=\"bazzzz\")");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forCharacters(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertNull(aea.getValue());
	}

	public void testValueInvalidValue5() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=\"java.lang.Object\")");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String>(daa, "bar", SimpleTypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertNull(aea.getValue());
	}

	public void testValueInvalidValue6() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=enums.TestEnum.class)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new EnumDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertNull(aea.getValue());
	}

	public void testValueInvalidValue7() throws Exception {
		this.createAnnotationAndMembers("Baz", "boolean fred();");
		this.createAnnotationAndMembers("Bar", "annot.Baz jimmy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		String value = "\"false\"";
		String element = "fred=" + value;
		String annotation = "@annot.Foo(@annot.Bar(jimmy=@annot.Baz(" + element + ")))";
		ICompilationUnit cu = this.createTestType(annotation);
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "value", "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedDeclarationAnnotationAdapter(daa2, "jimmy", "annot.Baz");
		DeclarationAnnotationElementAdapter<Boolean> daea = new ConversionDeclarationAnnotationElementAdapter<Boolean>(daa3, "fred", BooleanExpressionConverter.instance());
		AnnotationElementAdapter<Boolean> aea = new MemberAnnotationElementAdapter<Boolean>(this.idField(cu), daea);
		assertNull(aea.getValue());
	}

	public void testValueInvalidValue8() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar={true, false})");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[]>(daa, "bar", AnnotationStringArrayExpressionConverter.forStrings());
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		assertTrue(Arrays.equals(new String[] {null, null}, aea.getValue()));
	}

	public void testValueInvalidValue9() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=77)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[]>(daa, "bar", AnnotationStringArrayExpressionConverter.forStrings());
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		assertTrue(Arrays.equals(new String[] {null}, aea.getValue()));
	}

	public void testASTNode1() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		String value = "\"xxx\"";
		String element = "bar=" + value;
		String annotation = "@annot.Foo(" + element + ")";
		ICompilationUnit cu = this.createTestType(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);

		TextRange textRange = new ASTNodeTextRange(aea.getAstNode(this.buildASTRoot(cu)));
		assertEquals(this.getSource(cu).indexOf(value), textRange.getOffset());
		assertEquals(value.length(), textRange.getLength());
		assertEquals(8, textRange.getLineNumber());
	}

	public void testASTNode2() throws Exception {
		this.createAnnotationAndMembers("Baz", "boolean fred();");
		this.createAnnotationAndMembers("Bar", "annot.Baz jimmy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		String value = "false";
		String element = "fred=" + value;
		String annotation = "@annot.Foo(@annot.Bar(jimmy=@annot.Baz(" + element + ")))";
		ICompilationUnit cu = this.createTestType(annotation);
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "value", "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedDeclarationAnnotationAdapter(daa2, "jimmy", "annot.Baz");
		DeclarationAnnotationElementAdapter<Boolean> daea = new ConversionDeclarationAnnotationElementAdapter<Boolean>(daa3, "fred", BooleanExpressionConverter.instance());
		AnnotationElementAdapter<Boolean> aea = new MemberAnnotationElementAdapter<Boolean>(this.idField(cu), daea);
		assertEquals(Boolean.FALSE, aea.getValue());
		TextRange textRange = new ASTNodeTextRange(aea.getAstNode(this.buildASTRoot(cu)));
		assertEquals(value.length(), textRange.getLength());
	}

	public void testASTNode3() throws Exception {
		this.createAnnotationAndMembers("Foo", "String value();");
		String element = "\"xxx\"";
		String annotation = "@annot.Foo(" + element + ")";
		ICompilationUnit cu = this.createTestType(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa);
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);

		TextRange textRange = new ASTNodeTextRange(aea.getAstNode(this.buildASTRoot(cu)));
		assertEquals(this.getSource(cu).indexOf(element), textRange.getOffset());
		assertEquals(element.length(), textRange.getLength());
	}

	public void testASTNode4() throws Exception {
		this.createAnnotationAndMembers("Foo", "String value();");
		String annotation = "@annot.Foo";
		ICompilationUnit cu = this.createTestType(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa);
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);

		TextRange textRange = new ASTNodeTextRange(aea.getAstNode(this.buildASTRoot(cu)));
		assertEquals(this.getSource(cu).indexOf(annotation), textRange.getOffset());
		assertEquals(annotation.length(), textRange.getLength());
	}

	public void testSetValue1() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		String annotation = "@annot.Foo(bar=\"xxx\")";
		ICompilationUnit cu = this.createTestType(annotation);
		this.assertSourceContains(annotation, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);

		aea.setValue(null);
		this.assertSourceDoesNotContain("Foo", cu);
	}

	public void testSetValue2() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		String annotation = "@annot.Foo(bar=\"xxx\")";
		ICompilationUnit cu = this.createTestType(annotation);
		this.assertSourceContains(annotation, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar", false);
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);

		aea.setValue(null);
		this.assertSourceDoesNotContain(annotation, cu);
		this.assertSourceContains("@Foo", cu);
	}

	public void testSetValue3() throws Exception {
		this.createAnnotationAndMembers("Baz", "boolean fred();");
		this.createAnnotationAndMembers("Bar", "annot.Baz jimmy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		String annotation = "@annot.Foo(@annot.Bar(jimmy=@annot.Baz(fred=false)))";
		ICompilationUnit cu = this.createTestType(annotation);
		this.assertSourceContains(annotation, cu);
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "value", "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedDeclarationAnnotationAdapter(daa2, "jimmy", "annot.Baz");
		DeclarationAnnotationElementAdapter<Boolean> daea = new ConversionDeclarationAnnotationElementAdapter<Boolean>(daa3, "fred", BooleanExpressionConverter.instance());
		AnnotationElementAdapter<Boolean> aea = new MemberAnnotationElementAdapter<Boolean>(this.idField(cu), daea);

		aea.setValue(null);
		this.assertSourceDoesNotContain(annotation, cu);
		this.assertSourceDoesNotContain("Foo", cu);
		this.assertSourceDoesNotContain("Bar", cu);
	}

	public void testSetValue3a() throws Exception {
		this.createAnnotationAndMembers("Baz", "boolean fred();");
		this.createAnnotationAndMembers("Bar", "annot.Baz jimmy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		String annotation = "@annot.Foo(@annot.Bar(jimmy=@annot.Baz(fred=false)))";
		ICompilationUnit cu = this.createTestType(annotation);
		this.assertSourceContains(annotation, cu);
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "value", "annot.Bar", false);
		DeclarationAnnotationAdapter daa3 = new NestedDeclarationAnnotationAdapter(daa2, "jimmy", "annot.Baz", false);
		DeclarationAnnotationElementAdapter<Boolean> daea = new ConversionDeclarationAnnotationElementAdapter<Boolean>(daa3, "fred", BooleanExpressionConverter.instance());
		AnnotationElementAdapter<Boolean> aea = new MemberAnnotationElementAdapter<Boolean>(this.idField(cu), daea);

		aea.setValue(null);
		this.assertSourceDoesNotContain(annotation, cu);
		this.assertSourceContains("@annot.Foo(@Bar)", cu);
	}

	public void testSetValue4() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		ICompilationUnit cu = this.createTestType();
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);

		aea.setValue("xxx");
		this.assertSourceContains("@Foo(bar = \"xxx\")", cu);
	}

	public void testSetValue5() throws Exception {
		this.createAnnotationAndMembers("Baz", "boolean fred();");
		this.createAnnotationAndMembers("Bar", "annot.Baz jimmy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		String annotation = "@annot.Foo(@annot.Bar(jimmy=@annot.Baz(fred=false)))";
		ICompilationUnit cu = this.createTestType(annotation);
		this.assertSourceContains(annotation, cu);
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "value", "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedDeclarationAnnotationAdapter(daa2, "jimmy", "annot.Baz");
		DeclarationAnnotationElementAdapter<Boolean> daea = new ConversionDeclarationAnnotationElementAdapter<Boolean>(daa3, "fred", BooleanExpressionConverter.instance());
		AnnotationElementAdapter<Boolean> aea = new MemberAnnotationElementAdapter<Boolean>(this.idField(cu), daea);

		aea.setValue(Boolean.TRUE);
		this.assertSourceDoesNotContain(annotation, cu);
		this.assertSourceContains("@annot.Foo(@annot.Bar(jimmy=@annot.Baz(fred=true)))", cu);
	}

	public void testSetValue6() throws Exception {
		this.createAnnotationAndMembers("Baz", "boolean fred();");
		this.createAnnotationAndMembers("Bar", "annot.Baz jimmy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		ICompilationUnit cu = this.createTestType();
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "value", "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedDeclarationAnnotationAdapter(daa2, "jimmy", "annot.Baz");
		DeclarationAnnotationElementAdapter<Boolean> daea = new ConversionDeclarationAnnotationElementAdapter<Boolean>(daa3, "fred", BooleanExpressionConverter.instance());
		AnnotationElementAdapter<Boolean> aea = new MemberAnnotationElementAdapter<Boolean>(this.idField(cu), daea);

		aea.setValue(Boolean.TRUE);
		this.assertSourceContains("@Foo(@Bar(jimmy = @Baz(fred = true)))", cu);
	}

	public void testSetValue7() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		String annotation = "@annot.Foo(bar=\"xxx\")";
		ICompilationUnit cu = this.createTestType(annotation);
		this.assertSourceContains(annotation, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);

		aea.setValue("yyy");
		this.assertSourceDoesNotContain(annotation, cu);
		this.assertSourceContains("@annot.Foo(bar=\"yyy\")", cu);
	}

	public void testSetValue8() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		String annotation = "@annot.Foo";
		ICompilationUnit cu = this.createTestType(annotation);
		this.assertSourceContains(annotation, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);

		aea.setValue("xxx");
		this.assertSourceContains("@Foo(bar = \"xxx\")", cu);
	}

	public void testSetValue9() throws Exception {
		this.createAnnotationAndMembers("Foo", "String value(); String bar();");
		String annotation = "@annot.Foo(\"zzz\")";
		ICompilationUnit cu = this.createTestType(annotation);
		this.assertSourceContains(annotation, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);

		aea.setValue("xxx");
		this.assertSourceDoesNotContain(annotation, cu);
		this.assertSourceContains("@Foo(value = \"zzz\", bar = \"xxx\")", cu);
	}

	public void testSetValue10() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar(); String baz();");
		String annotation = "@annot.Foo(bar=\"xxx\")";
		ICompilationUnit cu = this.createTestType(annotation);
		this.assertSourceContains(annotation, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "baz");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);

		aea.setValue("yyy");
		this.assertSourceDoesNotContain(annotation, cu);
		this.assertSourceContains("@annot.Foo(bar=\"xxx\", baz = \"yyy\")", cu);
	}

	public void testSetValue11() throws Exception {
		this.createAnnotationAndMembers("Baz", "int fred();");
		this.createAnnotationAndMembers("Bar", "annot.Baz[] jimmy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		String annotation = "@annot.Foo(@annot.Bar(jimmy={@annot.Baz(fred=0), @annot.Baz(fred=1), @annot.Baz(fred=2), @annot.Baz(fred=3)}))";
		ICompilationUnit cu = this.createTestType(annotation);
		this.assertSourceContains(annotation, cu);
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "value", "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedIndexedDeclarationAnnotationAdapter(daa2, "jimmy", 2, "annot.Baz");
		DeclarationAnnotationElementAdapter<Integer> daea = new ConversionDeclarationAnnotationElementAdapter<Integer>(daa3, "fred", NumberIntegerExpressionConverter.instance());
		AnnotationElementAdapter<Integer> aea = new MemberAnnotationElementAdapter<Integer>(this.idField(cu), daea);

		assertEquals(Integer.valueOf(2), aea.getValue());
		aea.setValue(Integer.valueOf(48));
		this.assertSourceContains("@annot.Foo(@annot.Bar(jimmy={@annot.Baz(fred=0), @annot.Baz(fred=1), @annot.Baz(fred=48), @annot.Baz(fred=3)}))", cu);
	}

	public void testSetValue12() throws Exception {
		this.createAnnotationAndMembers("Foo", "String value();");
		String annotation = "@annot.Foo";
		ICompilationUnit cu = this.createTestType(annotation);
		this.assertSourceContains(annotation, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "value");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);

		aea.setValue("xxx");
		this.assertSourceContains("@Foo(\"xxx\")", cu);
	}

	public void testSetValue13() throws Exception {
		this.createAnnotationAndMembers("Foo", "String value();");
		String annotation = "@annot.Foo(\"zzz\")";
		ICompilationUnit cu = this.createTestType(annotation);
		this.assertSourceContains(annotation, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "value");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);

		aea.setValue("xxx");
		this.assertSourceDoesNotContain(annotation, cu);
		this.assertSourceContains("@annot.Foo(\"xxx\")", cu);
	}

	public void testSimpleTypeLiteral1() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=java.lang.Object.class)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String>(daa, "bar", SimpleTypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertEquals("java.lang.Object", aea.getValue());
	}

	public void testSimpleTypeLiteral2() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		ICompilationUnit cu = this.createTestType();
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String>(daa, "bar", SimpleTypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		aea.setValue("java.lang.Object");
		this.assertSourceContains("@Foo(bar = java.lang.Object.class)", cu);
	}

	public void testSimpleTypeLiteral3() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=int.class)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String>(daa, "bar", SimpleTypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertNull(aea.getValue());
	}

	public void testSimpleTypeLiteral4() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=java.util.Map.Entry.class)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String>(daa, "bar", SimpleTypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertEquals("java.util.Map.Entry", aea.getValue());
	}

	public void testPrimitiveTypeLiteral1() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=int.class)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String>(daa, "bar", PrimitiveTypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertEquals("int", aea.getValue());
	}

	public void testPrimitiveTypeLiteral2() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		ICompilationUnit cu = this.createTestType();
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String>(daa, "bar", PrimitiveTypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		aea.setValue("int");
		this.assertSourceContains("@Foo(bar = int.class)", cu);
	}

	public void testPrimitiveTypeLiteral3() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=java.lang.Object.class)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String>(daa, "bar", PrimitiveTypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertNull(aea.getValue());
	}

	public void testPrimitiveTypeLiteral4() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=void.class)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String>(daa, "bar", PrimitiveTypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertEquals("void", aea.getValue());
	}

	public void testTypeLiteral1() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=java.lang.Object.class)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String>(daa, "bar", TypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertEquals("java.lang.Object", aea.getValue());
	}

	public void testTypeLiteral2() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		ICompilationUnit cu = this.createTestType();
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String>(daa, "bar", TypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		aea.setValue("java.lang.Object");
		this.assertSourceContains("@Foo(bar = java.lang.Object.class)", cu);
	}

	public void testTypeLiteral3() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=java.util.Map.Entry.class)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String>(daa, "bar", TypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertEquals("java.util.Map.Entry", aea.getValue());
	}

	public void testTypeLiteral14() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=int.class)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String>(daa, "bar", TypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertEquals("int", aea.getValue());
	}

	public void testTypeLiteral5() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		ICompilationUnit cu = this.createTestType();
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String>(daa, "bar", TypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		aea.setValue("int");
		this.assertSourceContains("@Foo(bar = int.class)", cu);
	}

	public void testTypeLiteral6() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=void.class)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String>(daa, "bar", TypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertEquals("void", aea.getValue());
	}

	public void testValueEnum1() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=enums.TestEnum.XXX)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new EnumDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertEquals("enums.TestEnum.XXX", aea.getValue());
	}

	public void testValueEnum2() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum bar();");
		ICompilationUnit cu = this.createTestType("static enums.TestEnum.XXX", "@annot.Foo(bar=XXX)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new EnumDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertEquals("enums.TestEnum.XXX", aea.getValue());
	}

	public void testValueEnum3() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new EnumDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertNull(aea.getValue());
	}

	public void testValueEnum4() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum bar();");
		ICompilationUnit cu = this.createTestType("enums.TestEnum", "@annot.Foo(bar=TestEnum.XXX)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new EnumDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		assertEquals("enums.TestEnum.XXX", aea.getValue());
	}

	public void testSetValueEnum1() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum bar();");
		String expected = "@annot.Foo(bar=XXX)";
		ICompilationUnit cu = this.createTestType("static enums.TestEnum.XXX", expected);
		this.assertSourceContains(expected, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new EnumDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		aea.setValue(null);
		this.assertSourceDoesNotContain("Foo", cu);
	}

	public void testSetValueEnum2() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum bar();");
		String expected = "@Foo(bar = XXX)";
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain(expected, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new EnumDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea);
		aea.setValue("enums.TestEnum.XXX");
		this.assertSourceContains("import static enums.TestEnum.XXX;", cu);
		this.assertSourceContains(expected, cu);
	}

	public void testSetValueEnumImportCollision1() throws Exception {
		this.createEnum("enums1", "TestEnum", "XXX, YYY, ZZZ");
		this.createEnum("enums2", "TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums1.TestEnum bar1();  enums2.TestEnum bar2();");
		String expected = "@Foo(bar1 = XXX, bar2 = TestEnum.XXX)";
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain(expected, cu);

		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");

		DeclarationAnnotationElementAdapter<String> daea1 = new EnumDeclarationAnnotationElementAdapter(daa, "bar1");
		AnnotationElementAdapter<String> aea1 = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea1);
		aea1.setValue("enums1.TestEnum.XXX");

		DeclarationAnnotationElementAdapter<String> daea2 = new EnumDeclarationAnnotationElementAdapter(daa, "bar2");
		AnnotationElementAdapter<String> aea2 = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea2);
		aea2.setValue("enums2.TestEnum.XXX");

		this.assertSourceContains("import static enums1.TestEnum.XXX;", cu);
		this.assertSourceDoesNotContain("import static enums2.TestEnum.XXX;", cu);
		this.assertSourceContains("import enums2.TestEnum;", cu);
		this.assertSourceContains(expected, cu);
	}

	public void testSetValueEnumImportCollision2() throws Exception {
		this.createEnum("enums1", "TestEnum", "XXX, YYY, ZZZ");
		this.createEnum("enums2", "TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums1.TestEnum bar1();  enums2.TestEnum bar2();");
		String expected = "@annot.Foo(bar1=XXX, bar2 = TestEnum.XXX)";
		ICompilationUnit cu = this.createTestType("static enums1.TestEnum.*", "@annot.Foo(bar1=XXX)");
		this.assertSourceDoesNotContain(expected, cu);

		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");

		DeclarationAnnotationElementAdapter<String> daea1 = new EnumDeclarationAnnotationElementAdapter(daa, "bar1");
		AnnotationElementAdapter<String> aea1 = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea1);
		assertNotNull(aea1.getValue());

		DeclarationAnnotationElementAdapter<String> daea2 = new EnumDeclarationAnnotationElementAdapter(daa, "bar2");
		AnnotationElementAdapter<String> aea2 = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea2);
		aea2.setValue("enums2.TestEnum.XXX");

		this.assertSourceContains("import static enums1.TestEnum.*;", cu);
		this.assertSourceDoesNotContain("import static enums2.TestEnum.XXX;", cu);
		this.assertSourceContains("import enums2.TestEnum;", cu);
		this.assertSourceContains(expected, cu);
	}

	public void testSetValueEnumImportCollision3() throws Exception {
		this.createEnum("TestEnum1", "XXX, YYY, ZZZ");
		this.createEnum("TestEnum2", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum1 bar1();  enums.TestEnum2 bar2();");
		String expected = "@Foo(bar1 = XXX, bar2 = TestEnum2.XXX)";
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain(expected, cu);

		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");

		DeclarationAnnotationElementAdapter<String> daea1 = new EnumDeclarationAnnotationElementAdapter(daa, "bar1");
		AnnotationElementAdapter<String> aea1 = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea1);
		aea1.setValue("enums.TestEnum1.XXX");

		DeclarationAnnotationElementAdapter<String> daea2 = new EnumDeclarationAnnotationElementAdapter(daa, "bar2");
		AnnotationElementAdapter<String> aea2 = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea2);
		aea2.setValue("enums.TestEnum2.XXX");

		this.assertSourceContains("import static enums.TestEnum1.XXX;", cu);
		this.assertSourceContains("import enums.TestEnum2;", cu);
		this.assertSourceContains(expected, cu);
	}

	public void testSetValueEnumImportCollision4() throws Exception {
		this.createEnum("TestEnum1", "XXX, YYY, ZZZ");
		this.createEnum("TestEnum2", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum1 bar1();  enums.TestEnum2 bar2();");
		String expected = "@annot.Foo(bar1=XXX, bar2 = TestEnum2.XXX)";
		ICompilationUnit cu = this.createTestType("static enums.TestEnum1.*", "@annot.Foo(bar1=XXX)");
		this.assertSourceDoesNotContain(expected, cu);

		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");

		DeclarationAnnotationElementAdapter<String> daea1 = new EnumDeclarationAnnotationElementAdapter(daa, "bar1");
		AnnotationElementAdapter<String> aea1 = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea1);
		assertNotNull(aea1.getValue());

		DeclarationAnnotationElementAdapter<String> daea2 = new EnumDeclarationAnnotationElementAdapter(daa, "bar2");
		AnnotationElementAdapter<String> aea2 = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea2);
		aea2.setValue("enums.TestEnum2.XXX");

		this.assertSourceContains("import static enums.TestEnum1.*;", cu);
		this.assertSourceContains("import enums.TestEnum2;", cu);
		this.assertSourceContains(expected, cu);
	}

	/**
	 * not sure this is exactly what we want...
	 * it would be nice if we just skip the static import; but it's a matter of taste...
	 */
	public void testSetValueEnumImportCollision5() throws Exception {
		this.createEnum("TestEnum1", "XXX, YYY, ZZZ");
		this.createEnum("TestEnum2", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum1 bar1();  enums.TestEnum2 bar2();");
		String expected = "@Foo(bar1 = XXX, bar2 = TestEnum2.XXX)";
		ICompilationUnit cu = this.createTestType("enums.*", "");
		this.assertSourceDoesNotContain(expected, cu);

		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");

		DeclarationAnnotationElementAdapter<String> daea1 = new EnumDeclarationAnnotationElementAdapter(daa, "bar1");
		AnnotationElementAdapter<String> aea1 = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea1);
		aea1.setValue("enums.TestEnum1.XXX");

		DeclarationAnnotationElementAdapter<String> daea2 = new EnumDeclarationAnnotationElementAdapter(daa, "bar2");
		AnnotationElementAdapter<String> aea2 = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea2);
		aea2.setValue("enums.TestEnum2.XXX");

		this.assertSourceContains("import enums.*;", cu);
		this.assertSourceContains("import static enums.TestEnum1.XXX;", cu);
		this.assertSourceDoesNotContain("import enums.TestEnum2;", cu);
		this.assertSourceContains(expected, cu);
	}

	public void testSetValueEnumImportCollision6() throws Exception {
		this.createEnum("enums1", "TestEnum", "XXX, YYY, ZZZ");
		this.createEnum("enums2", "TestEnum", "XXX, YYY, ZZZ");
		this.createEnum("enums3", "TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums1.TestEnum bar1();  enums2.TestEnum bar2();  enums3.TestEnum bar3();");
		String expected = "@Foo(bar1 = XXX, bar2 = TestEnum.XXX, bar3 = enums3.TestEnum.XXX)";
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain(expected, cu);

		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");

		DeclarationAnnotationElementAdapter<String> daea1 = new EnumDeclarationAnnotationElementAdapter(daa, "bar1");
		AnnotationElementAdapter<String> aea1 = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea1);
		aea1.setValue("enums1.TestEnum.XXX");

		DeclarationAnnotationElementAdapter<String> daea2 = new EnumDeclarationAnnotationElementAdapter(daa, "bar2");
		AnnotationElementAdapter<String> aea2 = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea2);
		aea2.setValue("enums2.TestEnum.XXX");

		DeclarationAnnotationElementAdapter<String> daea3 = new EnumDeclarationAnnotationElementAdapter(daa, "bar3");
		AnnotationElementAdapter<String> aea3 = new MemberAnnotationElementAdapter<String>(this.idField(cu), daea3);
		aea3.setValue("enums3.TestEnum.XXX");

		this.assertSourceContains("import static enums1.TestEnum.XXX;", cu);
		this.assertSourceDoesNotContain("import static enums2.TestEnum.XXX;", cu);
		this.assertSourceContains("import enums2.TestEnum;", cu);
		this.assertSourceDoesNotContain("import static enums3", cu);
		this.assertSourceDoesNotContain("import enums3", cu);
		this.assertSourceContains(expected, cu);
	}

	public void testValueStringArray() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar={\"string0\", \"string1\"})");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[]>(daa, "bar", AnnotationStringArrayExpressionConverter.forStrings());
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		assertTrue(Arrays.equals(new String[] {"string0", "string1"}, aea.getValue()));
	}

	public void testValueStringArrayConcatenation() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar={\"stri\" + \"ng0\", \"s\" + \"tring1\"})");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[]>(daa, "bar", AnnotationStringArrayExpressionConverter.forStrings());
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		assertTrue(Arrays.equals(new String[] {"string0", "string1"}, aea.getValue()));
	}

	public void testValueStringArrayEmpty() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar={})");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[]>(daa, "bar", AnnotationStringArrayExpressionConverter.forStrings());
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		assertTrue(Arrays.equals(new String[0], aea.getValue()));
	}

	public void testValueStringArraySingleElement() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=\"string0\")");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[]>(daa, "bar", AnnotationStringArrayExpressionConverter.forStrings());
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		assertTrue(Arrays.equals(new String[] {"string0"}, aea.getValue()));
	}

	public void testValueNullStringArray() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo()");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[]>(daa, "bar", AnnotationStringArrayExpressionConverter.forStrings());
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		assertTrue(Arrays.equals(new String[0], aea.getValue()));
	}

	public void testSetValueStringArray() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		String expected = "@Foo(bar = { \"string0\", \"string1\" })";
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain(expected, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[]>(daa, "bar", AnnotationStringArrayExpressionConverter.forStrings());
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		aea.setValue(new String[] {"string0", "string1"});
		this.assertSourceContains(expected, cu);
	}

	public void testSetValueStringArrayEmptyRemove() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		String expected = "@Foo";
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain(expected, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[]>(daa, "bar", AnnotationStringArrayExpressionConverter.forStrings());
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		aea.setValue(new String[0]);
		this.assertSourceDoesNotContain(expected, cu);
	}

	public void testSetValueStringArrayEmpty() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		String expected = "@Foo(bar = {})";
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain(expected, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		ExpressionConverter<String[]> expressionConverter = new AnnotationStringArrayExpressionConverter(StringExpressionConverter.instance(), false);
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[]>(daa, "bar", expressionConverter);
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		aea.setValue(new String[0]);
		this.assertSourceContains(expected, cu);
	}

	public void testSetValueStringArraySingleElement() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		String annotation = "@Foo(bar = \"string0\")";
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain(annotation, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[]>(daa, "bar", AnnotationStringArrayExpressionConverter.forStrings());
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		aea.setValue(new String[] {"string0"});
		this.assertSourceContains(annotation, cu);
	}

	public void testValueEnumArray() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar={enums.TestEnum.XXX, enums.TestEnum.YYY})");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		assertTrue(Arrays.equals(new String[] {"enums.TestEnum.XXX", "enums.TestEnum.YYY"}, aea.getValue()));
	}

	public void testValueEnumArrayInvalidEntry() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar={enums.TestEnum.XXX, 88})");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		assertTrue(Arrays.equals(new String[] {"enums.TestEnum.XXX", null}, aea.getValue()));
	}

	public void testValueEnumArrayEmpty() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar={})");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		assertTrue(Arrays.equals(new String[0], aea.getValue()));
	}

	public void testValueEnumArraySingleElement() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=enums.TestEnum.XXX)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		assertTrue(Arrays.equals(new String[] {"enums.TestEnum.XXX"}, aea.getValue()));
	}

	public void testValueEnumArraySingleElementInvalid() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo(bar=\"\")");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		assertTrue(Arrays.equals(new String[] {null}, aea.getValue()));
	}

	public void testValueNullEnumArray() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		ICompilationUnit cu = this.createTestType("@annot.Foo()");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		assertTrue(Arrays.equals(new String[0], aea.getValue()));
	}

	public void testSetValueEnumArray() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		String expected = "@Foo(bar = { XXX, YYY })";
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain(expected, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		aea.setValue(new String[] {"enums.TestEnum.XXX", "enums.TestEnum.YYY"});
		this.assertSourceContains("import static enums.TestEnum.XXX;", cu);
		this.assertSourceContains("import static enums.TestEnum.YYY;", cu);
		this.assertSourceContains(expected, cu);
	}

	public void testSetValueEnumArrayEmptyRemove() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		String expected = "@Foo";
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain(expected, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		aea.setValue(new String[0]);
		this.assertSourceDoesNotContain(expected, cu);
	}

	public void testSetValueEnumArrayEmpty() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		String expected = "@Foo(bar = {})";
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain(expected, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar", true, false);
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		aea.setValue(new String[0]);
		this.assertSourceContains(expected, cu);
	}

	public void testSetValueEnumArraySingleElement() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		String expected = "@Foo(bar = XXX)";
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain(expected, cu);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea);
		aea.setValue(new String[] {"enums.TestEnum.XXX"});
		this.assertSourceContains("import static enums.TestEnum.XXX;", cu);
		this.assertSourceContains(expected, cu);
	}

	public void testSetValueEnumArrayImportCollision1() throws Exception {
		this.createEnum("enums1", "TestEnum", "XXX, YYY, ZZZ");
		this.createEnum("enums2", "TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums1.TestEnum[] bar1();  enums2.TestEnum[] bar2();");
		String expected = "@Foo(bar1 = { XXX, YYY }, bar2 = { TestEnum.XXX, TestEnum.YYY })";
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain(expected, cu);

		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");

		DeclarationAnnotationElementAdapter<String[]> daea1 = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar1");
		AnnotationElementAdapter<String[]> aea1 = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea1);
		aea1.setValue(new String[] {"enums1.TestEnum.XXX", "enums1.TestEnum.YYY"});

		DeclarationAnnotationElementAdapter<String[]> daea2 = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar2");
		AnnotationElementAdapter<String[]> aea2 = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea2);
		aea2.setValue(new String[] {"enums2.TestEnum.XXX", "enums2.TestEnum.YYY"});

		this.assertSourceContains("import static enums1.TestEnum.XXX;", cu);
		this.assertSourceContains("import static enums1.TestEnum.YYY;", cu);
		this.assertSourceDoesNotContain("import static enums2.TestEnum.XXX;", cu);
		this.assertSourceDoesNotContain("import static enums2.TestEnum.YYY;", cu);
		this.assertSourceContains("import enums2.TestEnum;", cu);
		this.assertSourceContains(expected, cu);
	}

	public void testSetValueEnumArrayImportCollision2() throws Exception {
		this.createEnum("enums1", "TestEnum", "XXX, YYY, ZZZ");
		this.createEnum("enums2", "TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums1.TestEnum[] bar1();  enums2.TestEnum[] bar2();");
		String expected = "@annot.Foo(bar1={XXX,YYY}, bar2 = { TestEnum.XXX, TestEnum.YYY })";
		ICompilationUnit cu = this.createTestType("static enums1.TestEnum.*", "@annot.Foo(bar1={XXX,YYY})");
		this.assertSourceDoesNotContain(expected, cu);

		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");

		DeclarationAnnotationElementAdapter<String[]> daea1 = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar1");
		AnnotationElementAdapter<String[]> aea1 = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea1);
		assertNotNull(aea1.getValue());

		DeclarationAnnotationElementAdapter<String[]> daea2 = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar2");
		AnnotationElementAdapter<String[]> aea2 = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea2);
		aea2.setValue(new String[] {"enums2.TestEnum.XXX", "enums2.TestEnum.YYY"});

		this.assertSourceContains("import static enums1.TestEnum.*;", cu);
		this.assertSourceDoesNotContain("import static enums2.TestEnum.XXX;", cu);
		this.assertSourceDoesNotContain("import static enums2.TestEnum.YYY;", cu);
		this.assertSourceContains("import enums2.TestEnum;", cu);
		this.assertSourceContains(expected, cu);
	}

	public void testSetValueEnumArrayImportCollision3() throws Exception {
		this.createEnum("TestEnum1", "XXX, YYY, ZZZ");
		this.createEnum("TestEnum2", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum1[] bar1();  enums.TestEnum2[] bar2();");
		String expected = "@Foo(bar1 = { XXX, YYY }, bar2 = { TestEnum2.XXX, TestEnum2.YYY })";
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain(expected, cu);

		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");

		DeclarationAnnotationElementAdapter<String[]> daea1 = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar1");
		AnnotationElementAdapter<String[]> aea1 = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea1);
		aea1.setValue(new String[] {"enums.TestEnum1.XXX", "enums.TestEnum1.YYY"});

		DeclarationAnnotationElementAdapter<String[]> daea2 = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar2");
		AnnotationElementAdapter<String[]> aea2 = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea2);
		aea2.setValue(new String[] {"enums.TestEnum2.XXX", "enums.TestEnum2.YYY"});

		this.assertSourceContains("import static enums.TestEnum1.XXX;", cu);
		this.assertSourceContains("import static enums.TestEnum1.YYY;", cu);
		this.assertSourceDoesNotContain("import static enums.TestEnum2.XXX;", cu);
		this.assertSourceDoesNotContain("import static enums.TestEnum2.YYY;", cu);
		this.assertSourceContains("import enums.TestEnum2;", cu);
		this.assertSourceContains(expected, cu);
	}

	public void testSetValueEnumArrayImportCollision4() throws Exception {
		this.createEnum("TestEnum1", "XXX, YYY, ZZZ");
		this.createEnum("TestEnum2", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum1[] bar1();  enums.TestEnum2[] bar2();");
		String expected = "@annot.Foo(bar1={XXX,YYY}, bar2 = { TestEnum2.XXX, TestEnum2.YYY })";
		ICompilationUnit cu = this.createTestType("static enums.TestEnum1.*", "@annot.Foo(bar1={XXX,YYY})");
		this.assertSourceDoesNotContain(expected, cu);

		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");

		DeclarationAnnotationElementAdapter<String[]> daea1 = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar1");
		AnnotationElementAdapter<String[]> aea1 = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea1);
		assertNotNull(aea1.getValue());

		DeclarationAnnotationElementAdapter<String[]> daea2 = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar2");
		AnnotationElementAdapter<String[]> aea2 = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea2);
		aea2.setValue(new String[] {"enums.TestEnum2.XXX", "enums.TestEnum2.YYY"});

		this.assertSourceContains("import static enums.TestEnum1.*;", cu);
		this.assertSourceDoesNotContain("import static enums.TestEnum2.XXX;", cu);
		this.assertSourceDoesNotContain("import static enums.TestEnum2.YYY;", cu);
		this.assertSourceContains("import enums.TestEnum2;", cu);
		this.assertSourceContains(expected, cu);
	}

	public void testSetValueEnumArrayImportCollision6() throws Exception {
		this.createEnum("enums1", "TestEnum", "XXX, YYY, ZZZ");
		this.createEnum("enums2", "TestEnum", "XXX, YYY, ZZZ");
		this.createEnum("enums3", "TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums1.TestEnum[] bar1();  enums2.TestEnum[] bar2();  enums3.TestEnum[] bar3();");
		String expected = "@Foo(bar1 = { XXX, YYY }, bar2 = { TestEnum.XXX, TestEnum.YYY }, bar3 = { enums3.TestEnum.XXX, enums3.TestEnum.YYY })";
		ICompilationUnit cu = this.createTestType();
		this.assertSourceDoesNotContain(expected, cu);

		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");

		DeclarationAnnotationElementAdapter<String[]> daea1 = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar1");
		AnnotationElementAdapter<String[]> aea1 = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea1);
		aea1.setValue(new String[] {"enums1.TestEnum.XXX", "enums1.TestEnum.YYY"});

		DeclarationAnnotationElementAdapter<String[]> daea2 = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar2");
		AnnotationElementAdapter<String[]> aea2 = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea2);
		aea2.setValue(new String[] {"enums2.TestEnum.XXX", "enums2.TestEnum.YYY"});

		DeclarationAnnotationElementAdapter<String[]> daea3 = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar3");
		AnnotationElementAdapter<String[]> aea3 = new MemberAnnotationElementAdapter<String[]>(this.idField(cu), daea3);
		aea3.setValue(new String[] {"enums3.TestEnum.XXX", "enums3.TestEnum.YYY"});

		this.assertSourceContains("import static enums1.TestEnum.XXX;", cu);
		this.assertSourceContains("import static enums1.TestEnum.YYY;", cu);
		this.assertSourceDoesNotContain("import static enums2", cu);
		this.assertSourceContains("import enums2.TestEnum;", cu);
		this.assertSourceDoesNotContain("import static enums3", cu);
		this.assertSourceDoesNotContain("import enums3", cu);
		this.assertSourceContains(expected, cu);
	}

}
