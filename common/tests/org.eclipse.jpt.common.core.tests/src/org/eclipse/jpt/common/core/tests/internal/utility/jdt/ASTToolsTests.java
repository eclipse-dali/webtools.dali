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

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotationStringArrayExpressionConverter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.JDTFieldAttribute;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class ASTToolsTests
		extends AnnotationTestCase {
	
	public ASTToolsTests(String name) {
		super(name);
	}
	
	
	private void createClassAndMembers(String className, String classBody) throws Exception {
		this.javaProject.createCompilationUnit("clazz", className + ".java", "public class " + className + " { " + classBody + " }");
	}
	
	private void createEnumAndMembers(String enumName, String enumBody) throws Exception {
		this.javaProject.createCompilationUnit("enums", enumName + ".java", "public enum " + enumName + " { " + enumBody + " }");
	}
	
	private void createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		this.javaProject.createCompilationUnit("annot", annotationName + ".java", "public @interface " + annotationName + " { " + annotationBody + " }");
	}
	
	private ICompilationUnit createTestType(final Iterator<String> imports, final String idFieldAnnotation)
			throws Exception {
		return createTestType(
				new DefaultAnnotationWriter() {
					@Override
					public Iterator<String> imports() {
						return imports;
					}
					@Override
					public void appendIdFieldAnnotationTo(StringBuilder sb) {
						sb.append(idFieldAnnotation);
					}
				});
	}

	public void testResolveEnum1() throws Exception {
		this.createEnumAndMembers("TestEnum", "FOO, BAR, BAZ");
		this.createAnnotationAndMembers("TestAnnotation", "TestEnum foo();");

		ICompilationUnit cu = this.createTestType("@annot.TestAnnotation(foo=enums.TestEnum.BAZ)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.TestAnnotation");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "foo");
		JDTFieldAttribute field = this.idField(cu);

		String actual = ASTTools.resolveEnum((Name) daea.getExpression(field.getModifiedDeclaration(this.buildASTRoot(cu))));
		assertEquals("enums.TestEnum.BAZ", actual);
	}

	public void testResolveEnum2() throws Exception {
		this.createEnumAndMembers("TestEnum", "FOO, BAR, BAZ");
		this.createAnnotationAndMembers("TestAnnotation", "TestEnum foo();");

		ICompilationUnit cu = this.createTestType("static enums.TestEnum.BAZ", "@annot.TestAnnotation(foo=BAZ)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.TestAnnotation");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "foo");
		JDTFieldAttribute field = this.idField(cu);

		String actual = ASTTools.resolveEnum((Name) daea.getExpression(field.getModifiedDeclaration(this.buildASTRoot(cu))));
		assertEquals("enums.TestEnum.BAZ", actual);
	}

	public void testResolveEnum3() throws Exception {
		this.createEnumAndMembers("TestEnum", "FOO, BAR, BAZ");
		this.createAnnotationAndMembers("TestAnnotation", "TestEnum foo();");

		ICompilationUnit cu = this.createTestType("static enums.TestEnum.*", "@annot.TestAnnotation(foo=BAZ)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.TestAnnotation");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "foo");
		JDTFieldAttribute field = this.idField(cu);

		String actual = ASTTools.resolveEnum((Name)daea.getExpression(field.getModifiedDeclaration(this.buildASTRoot(cu))));
		assertEquals("enums.TestEnum.BAZ", actual);
	}

	public void testResolveEnum4() throws Exception {
		this.createEnumAndMembers("TestEnum", "FOO, BAR, BAZ");
		this.createAnnotationAndMembers("TestAnnotation", "TestEnum foo();");

		ICompilationUnit cu = this.createTestType("enums.TestEnum", "@annot.TestAnnotation(foo=TestEnum.BAZ)");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter("annot.TestAnnotation");
		DeclarationAnnotationElementAdapter<String> daea = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, "foo");
		JDTFieldAttribute field = this.idField(cu);

		String actual = ASTTools.resolveEnum((Name) daea.getExpression(field.getModifiedDeclaration(this.buildASTRoot(cu))));
		assertEquals("enums.TestEnum.BAZ", actual);
	}
	
	public void testResolveFullyQualifiedNames() throws Exception {
		final String otherClassName = "OtherClass";
		final String otherClassName2 = "OtherClass2";
		final String fqOtherClassName = "clazz.OtherClass";
		final String fqOtherClassName2 = "clazz.OtherClass2";
		final String annotationName = "TestAnnotation";
		final String fqAnnotationName = "annot.TestAnnotation";
		
		createClassAndMembers(otherClassName, "");
		createClassAndMembers(otherClassName2, "");
		createAnnotationAndMembers(annotationName, "Class[] foo();");
		
		ICompilationUnit cu = createTestType(
				new ArrayIterator<String>(new String[] {fqOtherClassName, fqOtherClassName2, fqAnnotationName}),
				"@TestAnnotation(foo={" + otherClassName + ".class, " + otherClassName2 + ".class})");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter(fqAnnotationName);
		DeclarationAnnotationElementAdapter<String[]> daea = 
				new ConversionDeclarationAnnotationElementAdapter<String[]>(
						daa, "foo", AnnotationStringArrayExpressionConverter.forTypes());
		JDTFieldAttribute field = idField(cu);
		
		Iterable<String> actual = ASTTools.resolveFullyQualifiedNames(daea.getExpression(field.getModifiedDeclaration(this.buildASTRoot(cu))));
		
		assertEquals(
				CollectionTools.list(new String[] {fqOtherClassName, fqOtherClassName2}),
				CollectionTools.list(actual));
	}
	
	public void testResolveFullyQualifiedNames2() throws Exception {
		final String otherClassName = "OtherClass";
		final String otherClassName2 = "OtherClass2";
		final String fqOtherClassName = "clazz.OtherClass";
		final String fqOtherClassName2 = "clazz.OtherClass2";
		final String annotationName = "TestAnnotation";
		final String fqAnnotationName = "annot.TestAnnotation";
		
		createClassAndMembers(otherClassName, "");
		createClassAndMembers(otherClassName2, "");
		createAnnotationAndMembers(annotationName, "Class[] foo();");
		
		ICompilationUnit cu = createTestType(
				new ArrayIterator<String>(new String[] {fqOtherClassName, fqOtherClassName2, fqAnnotationName}),
				"@TestAnnotation(foo={1, " + otherClassName + ".class})");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter(fqAnnotationName);
		DeclarationAnnotationElementAdapter<String[]> daea = 
				new ConversionDeclarationAnnotationElementAdapter<String[]>(
						daa, "foo", AnnotationStringArrayExpressionConverter.forTypes());
		JDTFieldAttribute field = idField(cu);
		
		Iterable<String> actual = ASTTools.resolveFullyQualifiedNames(daea.getExpression(field.getModifiedDeclaration(this.buildASTRoot(cu))));
		
		assertEquals(
				CollectionTools.list(new String[] {null, fqOtherClassName}),
				CollectionTools.list(actual));
	}
	
	public void testResolveFullyQualifiedNames3() throws Exception {
		final String otherClassName = "OtherClass";
		final String otherClassName2 = "OtherClass2";
		final String fqOtherClassName = "clazz.OtherClass";
		final String fqOtherClassName2 = "clazz.OtherClass2";
		final String annotationName = "TestAnnotation";
		final String fqAnnotationName = "annot.TestAnnotation";
		
		createClassAndMembers(otherClassName, "");
		createClassAndMembers(otherClassName2, "");
		createAnnotationAndMembers(annotationName, "Class[] foo();");
		
		ICompilationUnit cu = createTestType(
				new ArrayIterator<String>(new String[] {fqOtherClassName, fqOtherClassName2, fqAnnotationName}),
				"@TestAnnotation(foo={@TestAnnotation(), " + otherClassName + ".class}");
		DeclarationAnnotationAdapter daa = new SimpleDeclarationAnnotationAdapter(fqAnnotationName);
		DeclarationAnnotationElementAdapter<String[]> daea = 
				new ConversionDeclarationAnnotationElementAdapter<String[]>(
						daa, "foo", AnnotationStringArrayExpressionConverter.forTypes());
		JDTFieldAttribute field = idField(cu);
		
		Iterable<String> actual = ASTTools.resolveFullyQualifiedNames(daea.getExpression(field.getModifiedDeclaration(this.buildASTRoot(cu))));
		
		assertEquals(
				CollectionTools.list(new String[] {null, fqOtherClassName}),
				CollectionTools.list(actual));
	}
}
