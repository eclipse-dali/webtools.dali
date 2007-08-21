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

import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.FieldAttribute;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;

public class JDTToolsTests extends AnnotationTestCase {

	public JDTToolsTests(String name) {
		super(name);
	}

	public void testResolveSignature1() throws Exception {
		this.verifyResolveSignature("String", "java.lang.String");
	}

	public void testResolveSignature2() throws Exception {
		this.verifyResolveSignature("List", null);
	}

	public void testResolveSignature3() throws Exception {
		this.verifyResolveSignature("int", "int");
	}

	public void testResolveSignature4() throws Exception {
		this.verifyResolveSignature("void", "void");
	}

	public void testResolveSignature5() throws Exception {
		this.verifyResolveSignature("int[]", "int[]");
	}

	public void testResolveSignature6() throws Exception {
		this.verifyResolveSignature("String[]", "java.lang.String[]");
	}

	public void testResolveSignature7() throws Exception {
		this.verifyResolveSignature("java.util.List[][][]", "java.util.List[][][]");
	}

	public void testResolveSignature8a() throws Exception {
		// inner class
		this.verifyResolveSignature("java.util.Map.Entry", "java.util.Map.Entry");
	}

	public void testResolveSignature8b() throws Exception {
		// inner class
		this.createTestType("java.util.Map", "");
		this.verifyResolveSignature2("Map.Entry", "java.util.Map.Entry");
	}

	public void testResolveSignature8c() throws Exception {
		// inner class
		this.createTestType("java.util.Map.Entry", "");
		this.verifyResolveSignature2("Entry", "java.util.Map.Entry");
	}

	public void testResolveSignature9() throws Exception {
		// inner class
		this.verifyResolveSignature("Character.Subset", "java.lang.Character.Subset");
	}

	public void testResolveSignature10() throws Exception {
		// generic type
		this.verifyResolveSignature("java.util.List<java.lang.String>", "java.util.List");  // ????
	}

	public void testResolveSignature11() throws Exception {
		// annotation
		this.createTestType("java.lang.annotation.Target", "");
		this.verifyResolveSignature2("Target", "java.lang.annotation.Target");
	}

	public void testResolveSignature12() throws Exception {
		this.createTestType("java.lang.annotation.ElementType", "");
		this.verifyResolveSignature2("ElementType", "java.lang.annotation.ElementType");
	}

	private void verifyResolveSignature(String unresolvedTypeName, String expected) throws Exception {
		this.createTestType();
		this.verifyResolveSignature2(unresolvedTypeName, expected);
	}

	private void verifyResolveSignature2(String unresolvedTypeName, String expected) throws Exception {
		String signature = Signature.createTypeSignature(unresolvedTypeName, false);
		String actual = JDTTools.resolveSignature(signature, this.jdtType());
		assertEquals(expected, actual);
	}


	private void createEnumAndMembers(String enumName, String enumBody) throws Exception {
		this.javaProject.createType("enums", enumName + ".java", "public enum " + enumName + " { " + enumBody + " }");
	}

	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createType("annot", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}

	public void testResolveEnum1() throws Exception {
		this.createEnumAndMembers("TestEnum", "FOO, BAR, BAZ");
		this.createAnnotationAndMembers("TestAnnotation", "TestEnum foo();");

		this.createTestType("@annot.TestAnnotation(foo=enums.TestEnum.BAZ)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.TestAnnotation");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "foo");
		FieldAttribute field = this.idField();

		String actual = JDTTools.resolveEnum((Name) field.annotationElementExpression(daea));
		assertEquals("enums.TestEnum.BAZ", actual);
	}

	public void testResolveEnum2() throws Exception {
		this.createEnumAndMembers("TestEnum", "FOO, BAR, BAZ");
		this.createAnnotationAndMembers("TestAnnotation", "TestEnum foo();");

		this.createTestType("static enums.TestEnum.BAZ", "@annot.TestAnnotation(foo=BAZ)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.TestAnnotation");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "foo");
		FieldAttribute field = this.idField();

		String actual = JDTTools.resolveEnum((Name) field.annotationElementExpression(daea));
		assertEquals("enums.TestEnum.BAZ", actual);
	}

	public void testResolveEnum3() throws Exception {
		this.createEnumAndMembers("TestEnum", "FOO, BAR, BAZ");
		this.createAnnotationAndMembers("TestAnnotation", "TestEnum foo();");

		this.createTestType("static enums.TestEnum.*", "@annot.TestAnnotation(foo=BAZ)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.TestAnnotation");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "foo");
		FieldAttribute field = this.idField();

		String actual = JDTTools.resolveEnum((Name) field.annotationElementExpression(daea));
		assertEquals("enums.TestEnum.BAZ", actual);
	}

	public void testResolveEnum4() throws Exception {
		this.createEnumAndMembers("TestEnum", "FOO, BAR, BAZ");
		this.createAnnotationAndMembers("TestAnnotation", "TestEnum foo();");

		this.createTestType("enums.TestEnum", "@annot.TestAnnotation(foo=TestEnum.BAZ)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.TestAnnotation");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "foo");
		FieldAttribute field = this.idField();

		String actual = JDTTools.resolveEnum((Name) field.annotationElementExpression(daea));
		assertEquals("enums.TestEnum.BAZ", actual);
	}

}
