/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jdtutility;

import java.util.Arrays;

import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.jdtutility.ASTNodeTextRange;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationStringArrayExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.BooleanStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.CharacterStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumArrayDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NumberStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.PrimitiveTypeStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.StringExpressionConverter;

public class MemberAnnotationElementAdapterTests extends AnnotationTestCase {

	public MemberAnnotationElementAdapterTests(String name) {
		super(name);
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("annot", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}

	private void createEnum(String enumName, String enumBody) throws Exception {
		this.javaProject.createType("enums", enumName + ".java", "public enum " + enumName + " { " + enumBody + " }");
	}

	public void testGetValue1() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		this.createTestType("@annot.Foo(bar=\"xxx\")");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertEquals("xxx", aea.getValue());
	}

	public void testGetValue2() throws Exception {
		this.createAnnotationAndMembers("Foo", "int bar();");
		this.createTestType("@annot.Foo(bar=48)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, NumberLiteral>(daa, "bar", NumberStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertEquals("48", aea.getValue());
	}

	public void testGetValue3() throws Exception {
		this.createAnnotationAndMembers("Foo", "char bar();");
		this.createTestType("@annot.Foo(bar='c')");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, CharacterLiteral>(daa, "bar", CharacterStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertEquals("c", aea.getValue());
	}

	public void testGetValue4() throws Exception {
		this.createAnnotationAndMembers("Foo", "boolean bar();");
		this.createTestType("@annot.Foo(bar=false)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, BooleanLiteral>(daa, "bar", BooleanStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertEquals("false", aea.getValue());
	}

	public void testGetValue5() throws Exception {
		this.createAnnotationAndMembers("Baz", "boolean fred();");
		this.createAnnotationAndMembers("Bar", "annot.Baz jimmy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		this.createTestType("@annot.Foo(@annot.Bar(jimmy=@annot.Baz(fred=false)))");
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "value", "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedDeclarationAnnotationAdapter(daa2, "jimmy", "annot.Baz");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, BooleanLiteral>(daa3, "fred", BooleanStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertEquals("false", aea.getValue());
	}

	public void testGetValue6() throws Exception {
		this.createAnnotationAndMembers("Foo", "boolean value();");
		this.createTestType("@annot.Foo(false)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, BooleanLiteral>(daa, BooleanStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertEquals("false", aea.getValue());
	}

	public void testGetValueNull1() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		this.createTestType("@annot.Foo");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertNull(aea.getValue());
	}

	public void testGetValueNull2() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		this.createTestType();
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertNull(aea.getValue());
	}

	public void testGetValueNull3() throws Exception {
		this.createAnnotationAndMembers("Baz", "String fred();");
		this.createAnnotationAndMembers("Bar", "annot.Baz jimmy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		this.createTestType("@annot.Foo(@annot.Bar(jimmy=@annot.Baz))");
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "value", "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedDeclarationAnnotationAdapter(daa2, "jimmy", "annot.Baz");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa3, "fred");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertNull(aea.getValue());
	}

	public void testASTNode1() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		String value = "\"xxx\"";
		String element = "bar=" + value;
		String annotation = "@annot.Foo(" + element + ")";
		this.createTestType(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);

		ITextRange textRange = new ASTNodeTextRange(aea.astNode());
		assertEquals(this.source().indexOf(value), textRange.getOffset());
		assertEquals(value.length(), textRange.getLength());
		assertEquals(7, textRange.getLineNumber());
	}

	public void testASTNode2() throws Exception {
		this.createAnnotationAndMembers("Baz", "boolean fred();");
		this.createAnnotationAndMembers("Bar", "annot.Baz jimmy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		String value = "false";
		String element = "fred=" + value;
		String annotation = "@annot.Foo(@annot.Bar(jimmy=@annot.Baz(" + element + ")))";
		this.createTestType(annotation);
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "value", "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedDeclarationAnnotationAdapter(daa2, "jimmy", "annot.Baz");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, BooleanLiteral>(daa3, "fred", BooleanStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertEquals("false", aea.getValue());
		ITextRange textRange = new ASTNodeTextRange(aea.astNode());
		assertEquals(value.length(), textRange.getLength());
	}

	public void testASTNode3() throws Exception {
		this.createAnnotationAndMembers("Foo", "String value();");
		String element = "\"xxx\"";
		String annotation = "@annot.Foo(" + element + ")";
		this.createTestType(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa);
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);

		ITextRange textRange = new ASTNodeTextRange(aea.astNode());
		assertEquals(this.source().indexOf(element), textRange.getOffset());
		assertEquals(element.length(), textRange.getLength());
	}

	public void testASTNode4() throws Exception {
		this.createAnnotationAndMembers("Foo", "String value();");
		String annotation = "@annot.Foo";
		this.createTestType(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa);
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);

		ITextRange textRange = new ASTNodeTextRange(aea.astNode());
		assertEquals(this.source().indexOf(annotation), textRange.getOffset());
		assertEquals(annotation.length(), textRange.getLength());
	}

	public void testSetValue1() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		String annotation = "@annot.Foo(bar=\"xxx\")";
		this.createTestType(annotation);
		this.assertSourceContains(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);

		aea.setValue(null);
		this.assertSourceDoesNotContain("Foo");
	}

	public void testSetValue2() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		String annotation = "@annot.Foo(bar=\"xxx\")";
		this.createTestType(annotation);
		this.assertSourceContains(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar", false);
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);

		aea.setValue(null);
		this.assertSourceDoesNotContain(annotation);
		this.assertSourceContains("@Foo");
	}

	public void testSetValue3() throws Exception {
		this.createAnnotationAndMembers("Baz", "boolean fred();");
		this.createAnnotationAndMembers("Bar", "annot.Baz jimmy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		String annotation = "@annot.Foo(@annot.Bar(jimmy=@annot.Baz(fred=false)))";
		this.createTestType(annotation);
		this.assertSourceContains(annotation);
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "value", "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedDeclarationAnnotationAdapter(daa2, "jimmy", "annot.Baz");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, BooleanLiteral>(daa3, "fred", BooleanStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);

		aea.setValue(null);
		this.assertSourceDoesNotContain(annotation);
		this.assertSourceDoesNotContain("Foo");
		this.assertSourceDoesNotContain("Bar");
	}

	public void testSetValue3a() throws Exception {
		this.createAnnotationAndMembers("Baz", "boolean fred();");
		this.createAnnotationAndMembers("Bar", "annot.Baz jimmy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		String annotation = "@annot.Foo(@annot.Bar(jimmy=@annot.Baz(fred=false)))";
		this.createTestType(annotation);
		this.assertSourceContains(annotation);
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "value", "annot.Bar", false);
		DeclarationAnnotationAdapter daa3 = new NestedDeclarationAnnotationAdapter(daa2, "jimmy", "annot.Baz", false);
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, BooleanLiteral>(daa3, "fred", BooleanStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);

		aea.setValue(null);
		this.assertSourceDoesNotContain(annotation);
		this.assertSourceContains("@annot.Foo(@Bar)");
	}

	public void testSetValue4() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		this.createTestType();
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);

		aea.setValue("xxx");
		this.assertSourceContains("@Foo(bar=\"xxx\")");
	}

	public void testSetValue5() throws Exception {
		this.createAnnotationAndMembers("Baz", "boolean fred();");
		this.createAnnotationAndMembers("Bar", "annot.Baz jimmy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		String annotation = "@annot.Foo(@annot.Bar(jimmy=@annot.Baz(fred=false)))";
		this.createTestType(annotation);
		this.assertSourceContains(annotation);
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "value", "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedDeclarationAnnotationAdapter(daa2, "jimmy", "annot.Baz");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, BooleanLiteral>(daa3, "fred", BooleanStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);

		aea.setValue("true");
		this.assertSourceDoesNotContain(annotation);
		this.assertSourceContains("@annot.Foo(@annot.Bar(jimmy=@annot.Baz(fred=true)))");
	}

	public void testSetValue6() throws Exception {
		this.createAnnotationAndMembers("Baz", "boolean fred();");
		this.createAnnotationAndMembers("Bar", "annot.Baz jimmy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		this.createTestType();
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "value", "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedDeclarationAnnotationAdapter(daa2, "jimmy", "annot.Baz");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, BooleanLiteral>(daa3, "fred", BooleanStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);

		aea.setValue("true");
		this.assertSourceContains("@Foo(@Bar(jimmy=@Baz(fred=true)))");
	}

	public void testSetValue7() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		String annotation = "@annot.Foo(bar=\"xxx\")";
		this.createTestType(annotation);
		this.assertSourceContains(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);

		aea.setValue("yyy");
		this.assertSourceDoesNotContain(annotation);
		this.assertSourceContains("@annot.Foo(bar=\"yyy\")");
	}

	public void testSetValue8() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar();");
		String annotation = "@annot.Foo";
		this.createTestType(annotation);
		this.assertSourceContains(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);

		aea.setValue("xxx");
		this.assertSourceContains("@Foo(bar=\"xxx\")");
	}

	public void testSetValue9() throws Exception {
		this.createAnnotationAndMembers("Foo", "String value(); String bar();");
		String annotation = "@annot.Foo(\"zzz\")";
		this.createTestType(annotation);
		this.assertSourceContains(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);

		aea.setValue("xxx");
		this.assertSourceDoesNotContain(annotation);
		this.assertSourceContains("@Foo(value=\"zzz\", bar=\"xxx\")");
	}

	public void testSetValue10() throws Exception {
		this.createAnnotationAndMembers("Foo", "String bar(); String baz();");
		String annotation = "@annot.Foo(bar=\"xxx\")";
		this.createTestType(annotation);
		this.assertSourceContains(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "baz");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);

		aea.setValue("yyy");
		this.assertSourceDoesNotContain(annotation);
		this.assertSourceContains("@annot.Foo(bar=\"xxx\", baz = \"yyy\")");
	}

	public void testSetValue11() throws Exception {
		this.createAnnotationAndMembers("Baz", "int fred();");
		this.createAnnotationAndMembers("Bar", "annot.Baz[] jimmy();");
		this.createAnnotationAndMembers("Foo", "annot.Bar value();");
		String annotation = "@annot.Foo(@annot.Bar(jimmy={@annot.Baz(fred=0), @annot.Baz(fred=1), @annot.Baz(fred=2), @annot.Baz(fred=3)}))";
		this.createTestType(annotation);
		this.assertSourceContains(annotation);
		DeclarationAnnotationAdapter daa1 = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationAdapter daa2 = new NestedDeclarationAnnotationAdapter(daa1, "value", "annot.Bar");
		DeclarationAnnotationAdapter daa3 = new NestedIndexedDeclarationAnnotationAdapter(daa2, "jimmy", 2, "annot.Baz");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, NumberLiteral>(daa3, "fred", NumberStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);

		assertEquals("2", aea.getValue());
		aea.setValue("48");
		this.assertSourceContains("@annot.Foo(@annot.Bar(jimmy={@annot.Baz(fred=0), @annot.Baz(fred=1), @annot.Baz(fred=48), @annot.Baz(fred=3)}))");
	}

	public void testSetValue12() throws Exception {
		this.createAnnotationAndMembers("Foo", "String value();");
		String annotation = "@annot.Foo";
		this.createTestType(annotation);
		this.assertSourceContains(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "value");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);

		aea.setValue("xxx");
		this.assertSourceContains("@Foo(\"xxx\")");
	}

	public void testSetValue13() throws Exception {
		this.createAnnotationAndMembers("Foo", "String value();");
		String annotation = "@annot.Foo(\"zzz\")";
		this.createTestType(annotation);
		this.assertSourceContains(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "value");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);

		aea.setValue("xxx");
		this.assertSourceDoesNotContain(annotation);
		this.assertSourceContains("@annot.Foo(\"xxx\")");
	}

	public void testSimpleTypeLiteral1() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		this.createTestType("@annot.Foo(bar=java.lang.Object.class)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, TypeLiteral>(daa, "bar", SimpleTypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertEquals("java.lang.Object", aea.getValue());
	}

	public void testSimpleTypeLiteral2() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		this.createTestType();
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, TypeLiteral>(daa, "bar", SimpleTypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		aea.setValue("java.lang.Object");
		this.assertSourceContains("@Foo(bar=java.lang.Object.class)");
	}

	public void testSimpleTypeLiteral3() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		this.createTestType("@annot.Foo(bar=int.class)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, TypeLiteral>(daa, "bar", SimpleTypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertNull(aea.getValue());
	}

	public void testSimpleTypeLiteral4() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		this.createTestType("@annot.Foo(bar=java.util.Map.Entry.class)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, TypeLiteral>(daa, "bar", SimpleTypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertEquals("java.util.Map.Entry", aea.getValue());
	}

	public void testPrimitiveTypeLiteral1() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		this.createTestType("@annot.Foo(bar=int.class)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, TypeLiteral>(daa, "bar", PrimitiveTypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertEquals("int", aea.getValue());
	}

	public void testPrimitiveTypeLiteral2() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		this.createTestType();
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, TypeLiteral>(daa, "bar", PrimitiveTypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		aea.setValue("int");
		this.assertSourceContains("@Foo(bar=int.class)");
	}

	public void testPrimitiveTypeLiteral3() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		this.createTestType("@annot.Foo(bar=java.lang.Object.class)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, TypeLiteral>(daa, "bar", PrimitiveTypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertNull(aea.getValue());
	}

	public void testPrimitiveTypeLiteral4() throws Exception {
		this.createAnnotationAndMembers("Foo", "Class bar();");
		this.createTestType("@annot.Foo(bar=void.class)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new ConversionDeclarationAnnotationElementAdapter<String, TypeLiteral>(daa, "bar", PrimitiveTypeStringExpressionConverter.instance());
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertEquals("void", aea.getValue());
	}

	public void testGetValueEnum1() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum bar();");
		this.createTestType("@annot.Foo(bar=enums.TestEnum.XXX)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new EnumDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertEquals("enums.TestEnum.XXX", aea.getValue());
	}

	public void testGetValueEnum2() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum bar();");
		this.createTestType("static enums.TestEnum.XXX", "@annot.Foo(bar=XXX)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new EnumDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertEquals("enums.TestEnum.XXX", aea.getValue());
	}

	public void testGetValueEnum3() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum bar();");
		this.createTestType("@annot.Foo");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new EnumDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertNull(aea.getValue());
	}

	public void testGetValueEnum4() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum bar();");
		this.createTestType("enums.TestEnum", "@annot.Foo(bar=TestEnum.XXX)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new EnumDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		assertEquals("enums.TestEnum.XXX", aea.getValue());
	}

	public void testSetValueEnum1() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum bar();");
		String annotation = "@annot.Foo(bar=XXX)";
		this.createTestType("static enums.TestEnum.XXX", annotation);
		this.assertSourceContains(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new EnumDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		aea.setValue(null);
		this.assertSourceDoesNotContain("Foo");
	}

	public void testSetValueEnum2() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum bar();");
		String annotation = "@Foo(bar=XXX)";
		this.createTestType();
		this.assertSourceDoesNotContain(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String> daea = new EnumDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String> aea = new MemberAnnotationElementAdapter<String>(this.idField(), daea);
		aea.setValue("enums.TestEnum.XXX");
		this.assertSourceContains("import static enums.TestEnum.XXX;");
		this.assertSourceContains(annotation);
	}

	public void testGetValueStringArray() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		this.createTestType("@annot.Foo(bar={\"string0\", \"string1\"})");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[], Expression>(daa, "bar", AnnotationStringArrayExpressionConverter.forStringLiterals());
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(), daea);
		assertTrue(Arrays.equals(new String[] {"string0", "string1"}, aea.getValue()));
	}

	public void testGetValueStringArrayEmpty() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		this.createTestType("@annot.Foo(bar={})");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[], Expression>(daa, "bar", AnnotationStringArrayExpressionConverter.forStringLiterals());
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(), daea);
		assertTrue(Arrays.equals(new String[0], aea.getValue()));
	}

	public void testGetValueStringArraySingleElement() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		this.createTestType("@annot.Foo(bar=\"string0\")");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[], Expression>(daa, "bar", AnnotationStringArrayExpressionConverter.forStringLiterals());
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(), daea);
		assertTrue(Arrays.equals(new String[] {"string0"}, aea.getValue()));
	}

	public void testGetValueNullStringArray() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		this.createTestType("@annot.Foo()");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[], Expression>(daa, "bar", AnnotationStringArrayExpressionConverter.forStringLiterals());
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(), daea);
		assertTrue(Arrays.equals(new String[0], aea.getValue()));
	}

	public void testSetValueStringArray() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		String annotation = "@Foo(bar={\"string0\",\"string1\"})";
		this.createTestType();
		this.assertSourceDoesNotContain(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[], Expression>(daa, "bar", AnnotationStringArrayExpressionConverter.forStringLiterals());
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(), daea);
		aea.setValue(new String[] {"string0", "string1"});
		this.assertSourceContains(annotation);
	}

	public void testSetValueStringArrayEmptyRemove() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		String annotation = "@Foo";
		this.createTestType();
		this.assertSourceDoesNotContain(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[], Expression>(daa, "bar", AnnotationStringArrayExpressionConverter.forStringLiterals());
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(), daea);
		aea.setValue(new String[0]);
		this.assertSourceDoesNotContain(annotation);
	}

	public void testSetValueStringArrayEmpty() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		String annotation = "@Foo(bar={})";
		this.createTestType();
		this.assertSourceDoesNotContain(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		ExpressionConverter<String[], Expression> expressionConverter = new AnnotationStringArrayExpressionConverter<StringLiteral>(StringExpressionConverter.instance(), false);
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[], Expression>(daa, "bar", expressionConverter);
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(), daea);
		aea.setValue(new String[0]);
		this.assertSourceContains(annotation);
	}

	public void testSetValueStringArraySingleElement() throws Exception {
		this.createAnnotationAndMembers("Foo", "String[] bar();");
		String annotation = "@Foo(bar=\"string0\")";
		this.createTestType();
		this.assertSourceDoesNotContain(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new ConversionDeclarationAnnotationElementAdapter<String[], Expression>(daa, "bar", AnnotationStringArrayExpressionConverter.forStringLiterals());
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(), daea);
		aea.setValue(new String[] {"string0"});
		this.assertSourceContains(annotation);
	}

	public void testGetValueEnumArray() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		this.createTestType("@annot.Foo(bar={enums.TestEnum.XXX, enums.TestEnum.YYY})");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(), daea);
		assertTrue(Arrays.equals(new String[] {"enums.TestEnum.XXX", "enums.TestEnum.YYY"}, aea.getValue()));
	}

	public void testGetValueEnumArrayEmpty() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		this.createTestType("@annot.Foo(bar={})");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(), daea);
		assertTrue(Arrays.equals(new String[0], aea.getValue()));
	}

	public void testGetValueEnumArraySingleElement() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		this.createTestType("@annot.Foo(bar=enums.TestEnum.XXX)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(), daea);
		assertTrue(Arrays.equals(new String[] {"enums.TestEnum.XXX"}, aea.getValue()));
	}

	public void testGetValueNullEnumArray() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		this.createTestType("@annot.Foo()");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(), daea);
		assertTrue(Arrays.equals(new String[0], aea.getValue()));
	}

	public void testSetValueEnumArray() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		String annotation = "@Foo(bar={XXX,YYY})";
		this.createTestType();
		this.assertSourceDoesNotContain(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(), daea);
		aea.setValue(new String[] {"enums.TestEnum.XXX", "enums.TestEnum.YYY"});
		this.assertSourceContains("import static enums.TestEnum.XXX;");
		this.assertSourceContains("import static enums.TestEnum.YYY;");
		this.assertSourceContains(annotation);
	}

	public void testSetValueEnumArrayEmptyRemove() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		String annotation = "@Foo";
		this.createTestType();
		this.assertSourceDoesNotContain(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(), daea);
		aea.setValue(new String[0]);
		this.assertSourceDoesNotContain(annotation);
	}

	public void testSetValueEnumArrayEmpty() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		String annotation = "@Foo(bar={})";
		this.createTestType();
		this.assertSourceDoesNotContain(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar", true, false);
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(), daea);
		aea.setValue(new String[0]);
		this.assertSourceContains(annotation);
	}

	public void testSetValueEnumArraySingleElement() throws Exception {
		this.createEnum("TestEnum", "XXX, YYY, ZZZ");
		this.createAnnotationAndMembers("Foo", "enums.TestEnum[] bar();");
		String annotation = "@Foo(bar=XXX)";
		this.createTestType();
		this.assertSourceDoesNotContain(annotation);
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.Foo");
		DeclarationAnnotationElementAdapter<String[]> daea = new EnumArrayDeclarationAnnotationElementAdapter(daa, "bar");
		AnnotationElementAdapter<String[]> aea = new MemberAnnotationElementAdapter<String[]>(this.idField(), daea);
		aea.setValue(new String[] {"enums.TestEnum.XXX"});
		this.assertSourceContains("import static enums.TestEnum.XXX;");
		this.assertSourceContains(annotation);
	}

}
