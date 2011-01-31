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

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import junit.framework.TestCase;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.JDTFieldAttribute;
import org.eclipse.jpt.common.core.internal.utility.jdt.JDTMethodAttribute;
import org.eclipse.jpt.common.core.internal.utility.jdt.JDTType;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;
import org.eclipse.jpt.utility.tests.internal.TestTools;

/**
 * Provide an easy(?) way to build an annotated source file.
 * The type must be created by calling one of the {@link #createTestType()}
 * methods before calling any of the various helper methods (i.e. the type is
 * <em>not</em> created during {@link #setUp()}).
 */
@SuppressWarnings("nls")
public abstract class AnnotationTestCase
	extends TestCase
{
	protected TestJavaProject javaProject;

	public static final String CR = System.getProperty("line.separator");
	public static final String SEP = File.separator;
	public static final String PROJECT_NAME = "AnnotationTestProject";
	public static final String PACKAGE_NAME = "test";
	public static final String PACKAGE_NAME_ = PACKAGE_NAME + '.';
	public static final String PACKAGE_INFO_FILE_NAME = "package-info.java";
	public static final IPath PACKAGE_INFO_FILE_PATH = new Path("src" + SEP + PACKAGE_NAME + SEP + PACKAGE_INFO_FILE_NAME);
	public static final String TYPE_NAME = "AnnotationTestType";
	public static final String FULLY_QUALIFIED_TYPE_NAME = PACKAGE_NAME_ + TYPE_NAME;
	public static final String FILE_NAME = TYPE_NAME + ".java";
	public static final IPath FILE_PATH = new Path("src" + SEP + PACKAGE_NAME + SEP + FILE_NAME);

	public static final String[] EMPTY_STRING_ARRAY = new String[0];


	// ********** TestCase behavior **********

	protected AnnotationTestCase(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.javaProject = this.buildJavaProject(false);  // false = no auto-build
	}
	
	protected TestJavaProject buildJavaProject(boolean autoBuild) throws Exception {
		return this.buildJavaProject(PROJECT_NAME, autoBuild);
	}
	
	protected TestJavaProject buildJavaProject(String projectName, boolean autoBuild) throws Exception {
		return new TestJavaProject(projectName, autoBuild);
	}

	@Override
	protected void tearDown() throws Exception {
//		this.dumpSource();
		this.deleteProject();
		TestTools.clear(this);
		super.tearDown();
	}
	
	protected void deleteProject() throws Exception {
		int i = 1;
		boolean deleted = false;
		while ( ! deleted) {
			try {
				this.javaProject.getProject().delete(true, true, null);
				deleted = true;
			} catch (CoreException ex) {
				if (i == 4) {
					throw new RuntimeException(this.getName() + " - unable to delete project", ex);
				}
				Thread.sleep(1000);
				i++;
			}
		}
	}

	protected void dumpSource(ICompilationUnit cu) throws Exception {
		System.out.println("*** " + this.getName() + " ****");
		System.out.println(this.getSource(cu));
		System.out.println();
	}
	
	
	// ********** package creation *********
	
	/** 
	 * create an un-annotated package-info
	 */
	protected ICompilationUnit createTestPackageInfo() throws CoreException {
		return this.createTestPackageInfo(new DefaultAnnotationWriter());
	}
	
	/** 
	 * create an un-annotated package-info in a package the given name
	 */
	protected ICompilationUnit createTestPackageInfo(String packageName) throws CoreException {
		return this.createTestPackageInfo(packageName, new DefaultAnnotationWriter());
	}
	
	/**
	 * shortcut for simply adding an annotation to the package declaration
	 */
	protected ICompilationUnit createTestPackageInfo(
			final String packageAnnotation, final String ... imports) 
			throws CoreException {
		
		return createTestPackageInfo(
				new DefaultAnnotationWriter() {
					@Override
					public Iterator<String> imports() {
						return new ArrayIterator<String>(imports);
					}
					
					@Override
					public void appendPackageAnnotationTo(StringBuilder sb) {
						sb.append(packageAnnotation);
					}
				});
	}
	
	protected ICompilationUnit createTestPackageInfo(AnnotationWriter annotationWriter) throws CoreException {
		return this.createTestPackageInfo(PACKAGE_NAME, annotationWriter);
	}
	
	protected ICompilationUnit createTestPackageInfo(String packageName, AnnotationWriter annotationWriter) throws CoreException {
		return this.javaProject.createCompilationUnit(
			packageName, PACKAGE_INFO_FILE_NAME, this.createSourceWriter(annotationWriter, packageName, null));
	}
	
	
	// ********** type creation **********
	
	/**
	 * create an un-annotated type
	 */
	protected ICompilationUnit createTestType() throws CoreException {
		return this.createTestType(new DefaultAnnotationWriter());
	}
	
	/**
	 * shortcut for simply adding an annotation to the 'id' field
	 */
	protected ICompilationUnit createTestType(final String annotationImport, final String idFieldAnnotation) throws CoreException {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return (annotationImport == null) ?
					EmptyIterator.<String>instance() :
					new SingleElementIterator<String>(annotationImport);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(idFieldAnnotation);
			}
		});
	}
	
	/**
	 * shortcut for simply adding a fully-qualified annotation to the 'id' field
	 */
	protected ICompilationUnit createTestType(final String idFieldAnnotation) throws CoreException {
		return this.createTestType(null, idFieldAnnotation);
	}
	
	protected ICompilationUnit createTestType(AnnotationWriter annotationWriter) throws CoreException {
		return this.javaProject.createCompilationUnit(PACKAGE_NAME, FILE_NAME, this.createSourceWriter(annotationWriter));
	}
	
	protected ICompilationUnit createTestType(String packageName, String fileName, String typeName, AnnotationWriter annotationWriter) throws CoreException {
		return this.javaProject.createCompilationUnit(packageName, fileName, this.createSourceWriter(annotationWriter, typeName));
	}
	
	protected SourceWriter createSourceWriter(AnnotationWriter annotationWriter) {
		return new AnnotatedSourceWriter(annotationWriter);
	}
	
	protected SourceWriter createSourceWriter(AnnotationWriter annotationWriter, String typeName) {
		return new AnnotatedSourceWriter(annotationWriter, typeName);
	}
	
	protected SourceWriter createSourceWriter(AnnotationWriter annotationWriter, String packageName, String typeName) {
		return new AnnotatedSourceWriter(annotationWriter, packageName, typeName);
	}
	
	protected ICompilationUnit createTestEnum(EnumAnnotationWriter annotationWriter) throws CoreException {
		return this.javaProject.createCompilationUnit(PACKAGE_NAME, FILE_NAME, this.createEnumSourceWriter(annotationWriter));
	}
	
	protected ICompilationUnit createTestEnum(String packageName, String fileName, String enumName, EnumAnnotationWriter annotationWriter) throws CoreException {
		return this.javaProject.createCompilationUnit(packageName, fileName, this.createEnumSourceWriter(annotationWriter, enumName));
	}
	
	protected SourceWriter createEnumSourceWriter(EnumAnnotationWriter annotationWriter) {
		return new EnumAnnotatedSourceWriter(annotationWriter);
	}
	
	protected SourceWriter createEnumSourceWriter(EnumAnnotationWriter annotationWriter, String enumName) {
		return new EnumAnnotatedSourceWriter(annotationWriter, enumName);
	}
	
	protected SourceWriter createEnumSourceWriter(EnumAnnotationWriter annotationWriter, String packageName, String enumName) {
		return new EnumAnnotatedSourceWriter(annotationWriter, packageName, enumName);
	}
	
	protected ICompilationUnit createAnnotatedEnumAndMembers(String enumName, String enumBody) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("package ").append(PACKAGE_NAME).append(";").append(CR);
		sb.append(CR);
		sb.append("import javax.xml.bind.annotation.XmlEnum;");
		sb.append(CR);
		sb.append(CR);
		sb.append("@XmlEnum");
		sb.append(CR);
		sb.append("public enum ").append(enumName).append(" { ").append(enumBody).append(" }");
		
		return this.javaProject.createCompilationUnit(PACKAGE_NAME, enumName + ".java", sb.toString());
	}
	
	/**
	 * writes source for package-info java files
	 */
	protected void appendSourceTo(
			StringBuilder sb, AnnotationWriter annotationWriter, String packageName) {
		
		annotationWriter.appendPackageAnnotationTo(sb);
		sb.append(CR);
		sb.append("package ").append(packageName).append(";").append(CR);
		sb.append(CR);
		for (Iterator<String> stream = annotationWriter.imports(); stream.hasNext(); ) {
			sb.append("import ").append(stream.next()).append(";").append(CR);
		}
	}
	
	/**
	 * writes source for typical java files
	 */
	protected void appendSourceTo(
			StringBuilder sb, AnnotationWriter annotationWriter, 
			String packageName, String typeName) {
		
		sb.append("package ").append(packageName).append(";").append(CR);
		sb.append(CR);
		for (Iterator<String> stream = annotationWriter.imports(); stream.hasNext(); ) {
			sb.append("import ");
			sb.append(stream.next());
			sb.append(";");
			sb.append(CR);
		}
		sb.append(CR);
		annotationWriter.appendTypeAnnotationTo(sb);
		sb.append(CR);
		sb.append("public class ").append(typeName).append(" ");
		annotationWriter.appendExtendsImplementsTo(sb);
		sb.append("{").append(CR);
		sb.append(CR);
		sb.append("    ");
		annotationWriter.appendIdFieldAnnotationTo(sb);
		sb.append(CR);
		sb.append("    private int id;").append(CR);
		sb.append(CR);
		sb.append("    ");
		annotationWriter.appendNameFieldAnnotationTo(sb);
		sb.append(CR);
		sb.append("    private String name;").append(CR);
		sb.append(CR);
		sb.append("    ");
		annotationWriter.appendGetIdMethodAnnotationTo(sb);
		sb.append(CR);
		sb.append("    public int getId() {").append(CR);
		sb.append("        return this.id;").append(CR);
		sb.append("    }").append(CR);
		sb.append(CR);
		sb.append("    ");
		annotationWriter.appendSetIdMethodAnnotationTo(sb);
		sb.append(CR);
		sb.append("    public void setId(int id) {").append(CR);
		sb.append("        this.id = id;").append(CR);
		sb.append("    }").append(CR);
		sb.append(CR);
		sb.append("    ");
		annotationWriter.appendGetNameMethodAnnotationTo(sb);
		sb.append(CR);
		sb.append("    public String getName() {").append(CR);
		sb.append("        return this.name;").append(CR);
		sb.append("    }").append(CR);
		sb.append(CR);
		sb.append("    ");
		annotationWriter.appendSetNameMethodAnnotationTo(sb);
		sb.append(CR);
		sb.append("    public void setTestField(String testField) {").append(CR);
		sb.append("        this.testField = testField;").append(CR);
		sb.append("    }").append(CR);
		sb.append(CR);
		annotationWriter.appendMemberTypeTo(sb);
		sb.append(CR);		
		sb.append("}").append(CR);
		annotationWriter.appendTopLevelTypesTo(sb);
		sb.append(CR);
	}

	
	/**
	 * writes source for typical java enum files
	 */
	protected void appendEnumSourceTo(
			StringBuilder sb, EnumAnnotationWriter annotationWriter, 
			String packageName, String enumName) {
		
		sb.append("package ").append(packageName).append(";").append(CR);
		sb.append(CR);
		for (Iterator<String> stream = annotationWriter.imports(); stream.hasNext(); ) {
			sb.append("import ");
			sb.append(stream.next());
			sb.append(";");
			sb.append(CR);
		}
		sb.append(CR);
		annotationWriter.appendEnumAnnotationTo(sb);
		sb.append(CR);
		sb.append("public enum ").append(enumName).append(" {").append(CR);
		sb.append(CR);
		sb.append("    ");
		annotationWriter.appendSundayEnumConstantAnnotationTo(sb);
		sb.append(CR);
		sb.append("    SUNDAY, ").append(CR);
		sb.append(CR);
		sb.append("    ");
		annotationWriter.appendMondayEnumConstantAnnotationTo(sb);
		sb.append(CR);
		sb.append("    MONDAY").append(CR);
		sb.append(CR);
		sb.append("}").append(CR);
		sb.append(CR);
	}


	// ********** queries **********

	protected TestJavaProject getJavaProject() {
		return this.javaProject;
	}

	protected JDTType testType(ICompilationUnit cu) {
		return this.buildType(TYPE_NAME, cu);
	}

	protected JDTType buildType(String name, ICompilationUnit cu) {
		return this.buildType(name, 1, cu);
	}

	protected JDTType buildType(String name, int occurrence, ICompilationUnit cu) {
		return this.buildType(null, name, occurrence, cu);
	}

	protected JDTType buildType(Type declaringType, String name, int occurrence, ICompilationUnit cu) {
		return new JDTType(declaringType, name, occurrence, cu);
	}

	protected JDTFieldAttribute idField(ICompilationUnit cu) {
		return this.buildField("id", cu);
	}

	protected JDTFieldAttribute nameField(ICompilationUnit cu) {
		return this.buildField("name", cu);
	}

	protected JDTFieldAttribute buildField(String name, ICompilationUnit cu) {
		return this.buildField(name, 1, cu);
	}

	protected JDTFieldAttribute buildField(String name, int occurrence, ICompilationUnit cu) {
		return this.buildField(this.testType(cu), name, occurrence, cu);
	}

	protected JDTFieldAttribute buildField(Type declaringType, String name, int occurrence, ICompilationUnit cu) {
		return new JDTFieldAttribute(declaringType, name, occurrence, cu);
	}

	protected JDTMethodAttribute idGetMethod(ICompilationUnit cu) {
		return this.buildMethod("getId", cu);
	}
	
	protected JDTMethodAttribute idSetMethod(ICompilationUnit cu) {
		return this.buildMethod("setId", new String[] {"int"}, cu);
	}

	protected JDTMethodAttribute nameGetMethod(ICompilationUnit cu) {
		return this.buildMethod("getName", cu);
	}

	protected JDTMethodAttribute buildMethod(String name, ICompilationUnit cu) {
		return this.buildMethod(name, EMPTY_STRING_ARRAY, cu);
	}

	protected JDTMethodAttribute buildMethod(String name, String[] parameterTypeNames, ICompilationUnit cu) {
		return this.buildMethod(name, parameterTypeNames, 1, cu);
	}

	protected JDTMethodAttribute buildMethod(String name, String[] parameterTypeNames, int occurrence, ICompilationUnit cu) {
		return new JDTMethodAttribute(this.testType(cu), name, parameterTypeNames, occurrence, cu);
	}

	protected JDTMethodAttribute buildMethod(Type declaringType, String name, String[] parameterTypeNames, int occurrence, ICompilationUnit cu) {
		return new JDTMethodAttribute(declaringType, name, parameterTypeNames, occurrence, cu);
	}

	protected String getSource(ICompilationUnit cu) throws JavaModelException {
		return cu.getBuffer().getContents();
	}

	protected CompilationUnit buildASTRoot(ICompilationUnit cu) {
		return ASTTools.buildASTRoot(cu);
	}


	// ********** test validation **********

	protected void assertSourceContains(String s, ICompilationUnit cu) throws JavaModelException {
		String source = this.getSource(cu);
		boolean found = source.indexOf(s) > -1;
		if ( ! found) {
			String msg = "source does not contain the expected string: " + s + " (see System console)";
			System.out.println("*** " + this.getName() + " ****");
			System.out.println(msg);
			System.out.println(source);
			System.out.println();
			fail(msg);
		}
	}

	protected void assertSourceDoesNotContain(String s, ICompilationUnit cu) throws JavaModelException {
		String source = this.getSource(cu);
		int pos = source.indexOf(s);
		if (pos != -1) {
			String msg = "unexpected string in source (position: " + pos + "): " + s + " (see System console)";
			System.out.println("*** " + this.getName() + " ****");
			System.out.println(msg);
			System.out.println(source);
			System.out.println();
			fail(msg);
		}
	}


	// ********** manipulate annotations **********

	/**
	 * Return the *first* member value pair for the specified annotation element
	 * with the specified name.
	 * Return null if the annotation has no such element.
	 */
	protected MemberValuePair memberValuePair(NormalAnnotation annotation, String elementName) {
		for (MemberValuePair pair : this.values(annotation)) {
			if (pair.getName().getFullyQualifiedName().equals(elementName)) {
				return pair;
			}
		}
		return null;
	}

	/**
	 * minimize the scope of the suppressed warnings
	 */
	@SuppressWarnings("unchecked")
	protected List<MemberValuePair> values(NormalAnnotation na) {
		return na.values();
	}

	/**
	 * minimize the scope of the suppressed warnings
	 */
	@SuppressWarnings("unchecked")
	protected List<EnumConstantDeclaration> enumConstants(EnumDeclaration ed) {
		return ed.enumConstants();
	}

	/**
	 * check for null member value pair
	 */
	protected Expression value_(MemberValuePair pair) {
		return (pair == null) ? null : pair.getValue();
	}

	/**
	 * Return the value of the *first* annotation element
	 * with the specified name.
	 * Return null if the annotation has no such element.
	 */
	protected Expression annotationElementValue(NormalAnnotation annotation, String elementName) {
		return this.value_(this.memberValuePair(annotation, elementName));
	}

	/**
	 * Return the value of the *first* annotation element
	 * with the specified name.
	 * Return null if the annotation has no such element.
	 */
	protected Expression annotationElementValue(SingleMemberAnnotation annotation, String elementName) {
		return elementName.equals("value") ? annotation.getValue() : null;
	}

	/**
	 * Return the value of the *first* annotation element
	 * with the specified name.
	 * Return null if the annotation has no such element.
	 * (An element name of "value" will return the value of a single
	 * member annotation.)
	 */
	protected Expression annotationElementValue(Annotation annotation, String elementName) {
		if (annotation.isNormalAnnotation()) {
			return this.annotationElementValue((NormalAnnotation) annotation, elementName);
		}
		if (annotation.isSingleMemberAnnotation()) {
			return this.annotationElementValue((SingleMemberAnnotation) annotation, elementName);
		}
		return null;
	}
	/**
	 * Build a number literal and set its initial value to the specified literal.
	 */
	protected NumberLiteral newNumberLiteral(AST ast, int value) {
		return ast.newNumberLiteral(Integer.toString(value));
	}

	/**
	 * Build a number literal and set its initial value to the specified literal.
	 */
	protected BooleanLiteral newBooleanLiteral(AST ast, boolean value) {
		return ast.newBooleanLiteral(value);
	}

	/**
	 * Build a string literal and set its initial value.
	 */
	protected StringLiteral newStringLiteral(AST ast, String value) {
		StringLiteral stringLiteral = ast.newStringLiteral();
		stringLiteral.setLiteralValue(value);
		return stringLiteral;
	}

	protected TypeLiteral newTypeLiteral(AST ast, String typeName) {
		TypeLiteral typeLiteral = ast.newTypeLiteral();
		typeLiteral.setType(this.newSimpleType(ast, typeName));
		return typeLiteral;
	}

	protected SimpleType newSimpleType(AST ast, String typeName) {
		return this.newSimpleType(ast, ast.newName(typeName));
	}

	protected SimpleType newSimpleType(AST ast, Name typeName) {
		return ast.newSimpleType(typeName);
	}

	protected MemberValuePair newMemberValuePair(AST ast, SimpleName name, Expression value) {
		MemberValuePair pair = ast.newMemberValuePair();
		pair.setName(name);
		pair.setValue(value);
		return pair;
	}

	protected MemberValuePair newMemberValuePair(AST ast, String name, Expression value) {
		return this.newMemberValuePair(ast, ast.newSimpleName(name), value);
	}

	protected MemberValuePair newMemberValuePair(AST ast, String name, String value) {
		return this.newMemberValuePair(ast, name, this.newStringLiteral(ast, value));
	}

	protected MemberValuePair newMemberValuePair(AST ast, String name, int value) {
		return this.newMemberValuePair(ast, name, this.newNumberLiteral(ast, value));
	}

	protected MemberValuePair newMemberValuePair(AST ast, String name, boolean value) {
		return this.newMemberValuePair(ast, name, this.newBooleanLiteral(ast, value));
	}

	protected EnumConstantDeclaration newEnumConstantDeclaration(AST ast, String enumConstantName) {
		EnumConstantDeclaration enumConstantDeclaration = ast.newEnumConstantDeclaration();
		enumConstantDeclaration.setName(ast.newSimpleName(enumConstantName));
		return enumConstantDeclaration;
	}
	/**
	 * Add the specified member value pair to the specified annotation.
	 * Return the resulting annotation.
	 */
	protected NormalAnnotation addMemberValuePair(NormalAnnotation annotation, MemberValuePair pair) {
		this.values(annotation).add(pair);
		return annotation;
	}

	/**
	 * Add the specified member value pair to the specified annotation.
	 * Return the resulting annotation.
	 */
	protected NormalAnnotation addMemberValuePair(NormalAnnotation annotation, String name, int value) {
		return this.addMemberValuePair(annotation, this.newMemberValuePair(annotation.getAST(), name, value));
	}

	/**
	 * Add the specified member value pair to the specified annotation.
	 * Return the resulting annotation.
	 */
	protected NormalAnnotation addMemberValuePair(NormalAnnotation annotation, String name, String value) {
		return this.addMemberValuePair(annotation, this.newMemberValuePair(annotation.getAST(), name, value));
	}

	/**
	 * Add the specified member value pair to the marker annotation
	 * by first replacing it with a normal annotation.
	 * Return the resulting normal annotation.
	 */
	protected NormalAnnotation addMemberValuePair(MarkerAnnotation annotation, String name, String value) {
		NormalAnnotation normalAnnotation = this.replaceMarkerAnnotation(annotation);
		this.addMemberValuePair(normalAnnotation, this.newMemberValuePair(annotation.getAST(), name, value));
		return normalAnnotation;
	}

	protected NormalAnnotation addMemberValuePair(MarkerAnnotation annotation, String name, boolean value) {
		NormalAnnotation normalAnnotation = this.replaceMarkerAnnotation(annotation);
		this.addMemberValuePair(normalAnnotation, this.newMemberValuePair(annotation.getAST(), name, value));
		return normalAnnotation;
	}

	/**
	 * Add the specified member value pair to the marker annotation
	 * by first replacing it with a normal annotation.
	 * Return the resulting normal annotation.
	 */
	protected NormalAnnotation addMemberValuePair(MarkerAnnotation annotation, MemberValuePair pair) {
		NormalAnnotation normalAnnotation = this.replaceMarkerAnnotation(annotation);
		return this.addMemberValuePair(normalAnnotation, pair);
	}

	protected void setEnumMemberValuePair(ModifiedDeclaration declaration, String annotationName, String enumValue) {
		NormalAnnotation annotation = (NormalAnnotation) declaration.getAnnotationNamed(annotationName);
		if (annotation == null) {
			annotation = addNormalAnnotation(declaration.getDeclaration(), annotationName);
		}
		this.setEnumMemberValuePair(annotation, "value", enumValue);
	}

	protected void setEnumMemberValuePair(NormalAnnotation annotation, String elementName, String enumValue) {
		MemberValuePair memberValuePair = this.memberValuePair(annotation, elementName);
		if (memberValuePair == null) {
			this.addEnumMemberValuePair(annotation, elementName, enumValue);
		}
		else {
			memberValuePair.setValue(annotation.getAST().newName(enumValue));
		}
	}

	protected void addEnumMemberValuePair(MarkerAnnotation markerAnnotation, String elementName, String value) {
		this.addEnumMemberValuePair(this.replaceMarkerAnnotation(markerAnnotation), elementName, value);
	}

	protected void addEnumMemberValuePair(NormalAnnotation annotation, String elementName, String value) {
		this.addMemberValuePair(annotation, elementName, annotation.getAST().newName(value));
	}

	protected void addMemberValuePair(NormalAnnotation annotation, String elementName, Expression value) {
		MemberValuePair memberValuePair = this.newMemberValuePair(annotation.getAST(), elementName, value);
		this.addMemberValuePair(annotation, memberValuePair);
	}

	protected void addMemberValuePair(MarkerAnnotation annotation, String elementName, Expression value) {
		MemberValuePair memberValuePair = this.newMemberValuePair(annotation.getAST(), elementName, value);
		this.addMemberValuePair(annotation, memberValuePair);
	}
	
	protected void addEnumConstant(EnumDeclaration enumDeclaration, String enumConstantName) {
		EnumConstantDeclaration enumConstantDeclaration = this.newEnumConstantDeclaration(enumDeclaration.getAST(), enumConstantName);
		this.enumConstants(enumDeclaration).add(enumConstantDeclaration);
	}
	
	protected void removeEnumConstant(EnumDeclaration enumDeclaration, String enumConstantName) {
		List<EnumConstantDeclaration> enumConstantsList = this.enumConstants(enumDeclaration);
		for (EnumConstantDeclaration constant : enumConstantsList) {
			if (constant.getName().getFullyQualifiedName().equals(enumConstantName)) {
				enumConstantsList.remove(constant);
				break;
			}
		}
	}
	
	protected void changeEnumConstantName(EnumDeclaration enumDeclaration, String oldEnumConstantName, String newEnumConstantName) {
		List<EnumConstantDeclaration> enumConstantsList = this.enumConstants(enumDeclaration);
		for (EnumConstantDeclaration constant : enumConstantsList) {
			if (constant.getName().getFullyQualifiedName().equals(oldEnumConstantName)) {
				this.changeEnumConstantName(constant, newEnumConstantName);
				break;
			}
		}
	}
	
	protected void changeEnumConstantName(EnumConstantDeclaration enumConstantDeclaration, String newEnumConstantName) {
		enumConstantDeclaration.setName(enumConstantDeclaration.getAST().newSimpleName(newEnumConstantName));
	}

	/**
	 * Add the array element to an annotation that is either a normal annotation or a marker annotation.
	 * If it is a marker annotation first make it a normal annotation.
	 */
	protected void addArrayElement(ModifiedDeclaration declaration, String annotationName, int index, String elementName, Expression arrayElement) {
		Annotation annotation = declaration.getAnnotationNamed(annotationName);
		NormalAnnotation normalAnnotation;
		if (annotation == null) {
			normalAnnotation = this.addNormalAnnotation(declaration.getDeclaration(), annotationName);
		}
		else if (annotation.getNodeType() == ASTNode.MARKER_ANNOTATION) {
			normalAnnotation = this.replaceMarkerAnnotation((MarkerAnnotation) annotation);
		}
		else {
			normalAnnotation = (NormalAnnotation) annotation;
		}
		this.addArrayElement(normalAnnotation, index, elementName, arrayElement);
	}

	/**
	 * Add the array element to the given normal annotation's element named elementName.
	 * Add a new member value pair if one does not exist.
	 * If the member value pair exists but the value is not yet an array, make it an array.
	 */
	protected void addArrayElement(NormalAnnotation annotation, int index, String elementName, Expression arrayElement) {
		MemberValuePair pair = this.memberValuePair(annotation, elementName);
		if (pair == null) {
			pair = this.newMemberValuePair(annotation.getAST(), elementName, arrayElement);
			this.addMemberValuePair(annotation, pair);
		}
		else {
			Expression value = pair.getValue();
			if (value.getNodeType() == ASTNode.ARRAY_INITIALIZER) {
				this.expressions((ArrayInitializer) value).add(index, arrayElement);
			}
			else {
				ArrayInitializer arrayInitializer = annotation.getAST().newArrayInitializer();
				pair.setValue(arrayInitializer);
				this.expressions(arrayInitializer).add(value);
				this.expressions(arrayInitializer).add(index, arrayElement);
			}
		}
	}

	/**
	 * This assumes an element with name elementName exists with an array as its value.
	 * Move the array element at sourceIndex to the targetIndex
	 */
	protected void moveArrayElement(NormalAnnotation annotation, String elementName, int targetIndex, int sourceIndex) {
		MemberValuePair pair = this.memberValuePair(annotation, elementName);
		ArrayInitializer array = (ArrayInitializer) pair.getValue();
		CollectionTools.move(this.expressions(array), targetIndex, sourceIndex);
	}

	/**
	 * This assumes an element with name elementName exists with potentially an array as its value.
	 * If the value is not an array, then the member value pair is removed.
	 * If the array element is removed and there is only 1 array element left, the array itself is removed
	 * and the remaining element is set as the value of the member value pair
	 */
	protected void removeArrayElement(NormalAnnotation annotation, String elementName, int index) {
		MemberValuePair pair = this.memberValuePair(annotation, elementName);
		if (pair.getValue().getNodeType() == ASTNode.ARRAY_INITIALIZER) {
			ArrayInitializer array = (ArrayInitializer) pair.getValue();
			this.expressions(array).remove(index);
			if (this.expressions(array).size() == 1) {
				pair.setValue(this.expressions(array).remove(0));
			}
		}
		else {
			this.values(annotation).remove(pair);
		}
	}

	/**
	 * Replace the given marker annotation with a normal annotation.
	 * Return the resulting normal annotation.
	 */
	protected NormalAnnotation replaceMarkerAnnotation(MarkerAnnotation annotation) {
		List<IExtendedModifier> annotations = this.annotations(annotation.getParent());
		int index = annotations.indexOf(annotation);
		NormalAnnotation normalAnnotation = newNormalAnnotation(annotation.getAST(), annotation.getTypeName().getFullyQualifiedName());
		annotations.set(index, normalAnnotation);
		return normalAnnotation;
	}

	/**
	 * Build a normal annotation and set its name.
	 */
	protected NormalAnnotation newNormalAnnotation(AST ast, String name) {
		NormalAnnotation annotation = ast.newNormalAnnotation();
		annotation.setTypeName(ast.newName(name));
		return annotation;
	}

	/**
	 * Add the normal annotation to the given AST node. 
	 * This should be a PackageDeclaration or a BodyDeclaration.
	 * Return the resulting normal annotation.
	 */
	protected NormalAnnotation addNormalAnnotation(ASTNode astNode, String name) {
		NormalAnnotation annotation = this.newNormalAnnotation(astNode.getAST(), name);
		this.addAnnotation(astNode, annotation);
		return annotation;
	}

	/**
	 * Add the annotation to the given AST node. 
	 * This should be a PackageDeclaration or a BodyDeclaration.
	 */
	protected void addAnnotation(ASTNode astNode, Annotation annotation) {
		this.annotations(astNode).add(annotation);
	}

	/**
	 * Build a marker annotation and set its name.
	 */
	protected MarkerAnnotation newMarkerAnnotation(AST ast, String name) {
		MarkerAnnotation annotation = ast.newMarkerAnnotation();
		annotation.setTypeName(ast.newName(name));
		return annotation;
	}

	/**
	 * Add the normal annotation to the given AST node. 
	 * This should be a PackageDeclaration or a BodyDeclaration.
	 * Return the resulting normal annotation.
	 */
	protected MarkerAnnotation addMarkerAnnotation(ASTNode astNode, String name) {
		MarkerAnnotation annotation = this.newMarkerAnnotation(astNode.getAST(), name);
		this.addAnnotation(astNode, annotation);
		return annotation;
	}

	/**
	 * Remove the annotation with the specified name
	 */
	protected void removeAnnotation(ModifiedDeclaration declaration, String name) {
		this.removeAnnotation(declaration.getDeclaration(), declaration.getAnnotationNamed(name));
	}

	/**
	 * Remove the specified annotation from the AST node.
	 * This should be a PackageDeclaration or a BodyDeclaration.
	 */
	protected void removeAnnotation(ASTNode astNode, Annotation annotation) {
		this.annotations(astNode).remove(annotation);
	}

	/**
	 * minimize the scope of the suppressed warnings
	 */
	@SuppressWarnings("unchecked")
	protected List<IExtendedModifier> annotations(ASTNode astNode) {
		if (astNode instanceof BodyDeclaration) {
			return ((BodyDeclaration) astNode).modifiers();
		}
		else if (astNode instanceof PackageDeclaration) {
			return ((PackageDeclaration) astNode).annotations();
		}
		return Collections.emptyList();
	}

	/**
	 * minimize the scope of the suppressed warnings
	 */
	@SuppressWarnings("unchecked")
	protected List<Expression> expressions(ArrayInitializer arrayInitializer) {
		return arrayInitializer.expressions();
	}


	// ********** member classes **********

	public interface AnnotationWriter {
		Iterator<String> imports();
		void appendPackageAnnotationTo(StringBuilder sb);
		void appendTypeAnnotationTo(StringBuilder sb);
		void appendExtendsImplementsTo(StringBuilder sb);
		void appendIdFieldAnnotationTo(StringBuilder sb);
		void appendNameFieldAnnotationTo(StringBuilder sb);
		void appendGetIdMethodAnnotationTo(StringBuilder sb);
		void appendSetIdMethodAnnotationTo(StringBuilder sb);
		void appendGetNameMethodAnnotationTo(StringBuilder sb);
		void appendSetNameMethodAnnotationTo(StringBuilder sb);
		void appendMemberTypeTo(StringBuilder sb);
		void appendTopLevelTypesTo(StringBuilder sb);
	}

	public static class DefaultAnnotationWriter implements AnnotationWriter {
		public Iterator<String> imports() {return EmptyIterator.instance();}
		public void appendPackageAnnotationTo(StringBuilder sb) {/* do nothing */}
		public void appendTypeAnnotationTo(StringBuilder sb) {/* do nothing */}
		public void appendExtendsImplementsTo(StringBuilder sb) {/* do nothing */}
		public void appendIdFieldAnnotationTo(StringBuilder sb) {/* do nothing */}
		public void appendNameFieldAnnotationTo(StringBuilder sb) {/* do nothing */}
		public void appendGetIdMethodAnnotationTo(StringBuilder sb) {/* do nothing */}
		public void appendSetIdMethodAnnotationTo(StringBuilder sb) {/* do nothing */}
		public void appendGetNameMethodAnnotationTo(StringBuilder sb) {/* do nothing */}
		public void appendSetNameMethodAnnotationTo(StringBuilder sb) {/* do nothing */}
		public void appendMemberTypeTo(StringBuilder sb) {/* do nothing */}
		public void appendTopLevelTypesTo(StringBuilder sb) {/* do nothing */}
	}

	public static class AnnotationWriterWrapper implements AnnotationWriter {
		private final AnnotationWriter aw;
		public AnnotationWriterWrapper(AnnotationWriter aw) {
			super();
			this.aw = aw;
		}
		public Iterator<String> imports() {return this.aw.imports();}
		public void appendPackageAnnotationTo(StringBuilder sb) {this.aw.appendPackageAnnotationTo(sb);}
		public void appendTypeAnnotationTo(StringBuilder sb) {this.aw.appendTypeAnnotationTo(sb);}
		public void appendExtendsImplementsTo(StringBuilder sb) {this.aw.appendExtendsImplementsTo(sb);}
		public void appendIdFieldAnnotationTo(StringBuilder sb) {this.aw.appendIdFieldAnnotationTo(sb);}
		public void appendNameFieldAnnotationTo(StringBuilder sb) {this.aw.appendNameFieldAnnotationTo(sb);}
		public void appendGetIdMethodAnnotationTo(StringBuilder sb) {this.aw.appendGetIdMethodAnnotationTo(sb);}
		public void appendSetIdMethodAnnotationTo(StringBuilder sb) {this.aw.appendSetIdMethodAnnotationTo(sb);}
		public void appendGetNameMethodAnnotationTo(StringBuilder sb) {this.aw.appendGetNameMethodAnnotationTo(sb);}
		public void appendSetNameMethodAnnotationTo(StringBuilder sb) {this.aw.appendSetNameMethodAnnotationTo(sb);}
		public void appendMemberTypeTo(StringBuilder sb) {this.aw.appendMemberTypeTo(sb);}
		public void appendTopLevelTypesTo(StringBuilder sb) {this.aw.appendTopLevelTypesTo(sb);}
	}

	public class AnnotatedSourceWriter
			implements SourceWriter {
		
		private AnnotationWriter annotationWriter;
		private String packageName;
		private String typeName;
		
		public AnnotatedSourceWriter(AnnotationWriter annotationWriter) {
			this(annotationWriter, TYPE_NAME);
		}
		
		public AnnotatedSourceWriter(AnnotationWriter annotationWriter, String typeName) {
			this(annotationWriter, PACKAGE_NAME, typeName);
		}
		
		public AnnotatedSourceWriter(AnnotationWriter annotationWriter, String packageName, String typeName) {
			super();
			this.annotationWriter = annotationWriter;
			this.packageName = packageName;
			this.typeName = typeName;
		}
		
		public void appendSourceTo(StringBuilder sb) {
			if (this.typeName != null) {
				AnnotationTestCase.this.appendSourceTo(sb, this.annotationWriter, this.packageName, this.typeName);
			}
			else {
				AnnotationTestCase.this.appendSourceTo(sb, this.annotationWriter, this.packageName);
			}
		}
	}

	public interface EnumAnnotationWriter {
		Iterator<String> imports();
		void appendPackageAnnotationTo(StringBuilder sb);
		void appendEnumAnnotationTo(StringBuilder sb);
		void appendSundayEnumConstantAnnotationTo(StringBuilder sb);
		void appendMondayEnumConstantAnnotationTo(StringBuilder sb);
	}

	public static class DefaultEnumAnnotationWriter implements EnumAnnotationWriter {
		public Iterator<String> imports() {return EmptyIterator.instance();}
		public void appendPackageAnnotationTo(StringBuilder sb) {/* do nothing */}
		public void appendEnumAnnotationTo(StringBuilder sb) {/* do nothing */}
		public void appendSundayEnumConstantAnnotationTo(StringBuilder sb) {/* do nothing */}
		public void appendMondayEnumConstantAnnotationTo(StringBuilder sb) {/* do nothing */}
	}

	public class EnumAnnotatedSourceWriter
			implements SourceWriter {
		
		private EnumAnnotationWriter annotationWriter;
		private String packageName;
		private String enumName;
		
		public EnumAnnotatedSourceWriter(EnumAnnotationWriter annotationWriter) {
			this(annotationWriter, TYPE_NAME);
		}
		
		public EnumAnnotatedSourceWriter(EnumAnnotationWriter annotationWriter, String enumName) {
			this(annotationWriter, PACKAGE_NAME, enumName);
		}
		
		public EnumAnnotatedSourceWriter(EnumAnnotationWriter annotationWriter, String packageName, String enumName) {
			super();
			this.annotationWriter = annotationWriter;
			this.packageName = packageName;
			this.enumName = enumName;
		}
		
		public void appendSourceTo(StringBuilder sb) {
			AnnotationTestCase.this.appendEnumSourceTo(sb, this.annotationWriter, this.packageName, this.enumName);
		}
	}

}
