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

import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.JDTFieldAttribute;
import org.eclipse.jpt.core.internal.jdtutility.JDTTools;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;

public class JDTToolsTests extends AnnotationTestCase {

	public JDTToolsTests(String name) {
		super(name);
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
		JDTFieldAttribute field = this.idField();

		String actual = JDTTools.resolveEnum((Name) field.annotationElementExpression(daea));
		assertEquals("enums.TestEnum.BAZ", actual);
	}

	public void testResolveEnum2() throws Exception {
		this.createEnumAndMembers("TestEnum", "FOO, BAR, BAZ");
		this.createAnnotationAndMembers("TestAnnotation", "TestEnum foo();");

		this.createTestType("static enums.TestEnum.BAZ", "@annot.TestAnnotation(foo=BAZ)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.TestAnnotation");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "foo");
		JDTFieldAttribute field = this.idField();

		String actual = JDTTools.resolveEnum((Name) field.annotationElementExpression(daea));
		assertEquals("enums.TestEnum.BAZ", actual);
	}

	public void testResolveEnum3() throws Exception {
		this.createEnumAndMembers("TestEnum", "FOO, BAR, BAZ");
		this.createAnnotationAndMembers("TestAnnotation", "TestEnum foo();");

		this.createTestType("static enums.TestEnum.*", "@annot.TestAnnotation(foo=BAZ)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.TestAnnotation");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "foo");
		JDTFieldAttribute field = this.idField();

		String actual = JDTTools.resolveEnum((Name) field.annotationElementExpression(daea));
		assertEquals("enums.TestEnum.BAZ", actual);
	}

	public void testResolveEnum4() throws Exception {
		this.createEnumAndMembers("TestEnum", "FOO, BAR, BAZ");
		this.createAnnotationAndMembers("TestAnnotation", "TestEnum foo();");

		this.createTestType("enums.TestEnum", "@annot.TestAnnotation(foo=TestEnum.BAZ)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.TestAnnotation");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "foo");
		JDTFieldAttribute field = this.idField();

		String actual = JDTTools.resolveEnum((Name) field.annotationElementExpression(daea));
		assertEquals("enums.TestEnum.BAZ", actual);
	}

}
