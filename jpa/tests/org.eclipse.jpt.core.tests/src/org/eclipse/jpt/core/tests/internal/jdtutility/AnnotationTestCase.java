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

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jpt.core.internal.jdtutility.FieldAttribute;
import org.eclipse.jpt.core.internal.jdtutility.MethodAttribute;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.tests.internal.ProjectUtility;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CommandExecutor;
import org.eclipse.jpt.utility.internal.CommandExecutorProvider;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;

import junit.framework.TestCase;

/**
 * Provide an easy(?) way to build an annotated source file.
 * The type must be created by calling one of the #createType() methods
 * before calling any of the various helper methods (i.e. the type is *not*
 * created during #setUp()).
 */
public abstract class AnnotationTestCase extends TestCase {
	protected TestJavaProject javaProject;

	protected static final String CR = System.getProperty("line.separator");
	protected static final String SEP = File.separator;
	protected static final String PROJECT_NAME = "AnnotationTestProject";
	protected static final String PACKAGE_NAME = "test";
	protected static final String TYPE_NAME = "AnnotationTestType";
	protected static final String FULLY_QUALIFIED_TYPE_NAME = PACKAGE_NAME + "." + TYPE_NAME;
	protected static final String FILE_NAME = TYPE_NAME + ".java";
	protected static final IPath FILE_PATH = new Path("src" + SEP + PACKAGE_NAME + SEP + FILE_NAME);

	protected static final String[] EMPTY_STRING_ARRAY = new String[0];


	// ********** TestCase behavior **********

	protected AnnotationTestCase(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ProjectUtility.deleteAllProjects();
		this.javaProject = this.buildJavaProject(PROJECT_NAME, false);  // false = no auto-build
	}

	protected TestJavaProject buildJavaProject(String projectName, boolean autoBuild) throws Exception {
		return new TestJavaProject(projectName, autoBuild);  // false = no auto-build
	}

	@Override
	protected void tearDown() throws Exception {
//		this.dumpSource();
//		this.javaProject.dispose();
		this.javaProject = null;
		super.tearDown();
	}

	protected void dumpSource() throws Exception {
		System.out.println("*** " + this.getName() + " ****");
		System.out.println(this.source());
		System.out.println();
	}


	// ********** type creation **********

	/**
	 * create an un-annotated type
	 */
	protected IType createTestType() throws CoreException {
		return this.createTestType(new DefaultAnnotationWriter());
	}

	/**
	 * shortcut for simply adding an annotation to the 'id' field
	 */
	protected IType createTestType(final String annotationImport, final String idFieldAnnotation) throws CoreException {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return (annotationImport == null) ?
					EmptyIterator.<String>instance()
				:
					new SingleElementIterator<String>(annotationImport);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuffer sb) {
				sb.append(idFieldAnnotation);
			}
		});
	}

	/**
	 * shortcut for simply adding a fully-qualified annotation to the 'id' field
	 */
	protected IType createTestType(final String idFieldAnnotation) throws CoreException {
		return this.createTestType(null, idFieldAnnotation);
	}

	protected IType createTestType(AnnotationWriter annotationWriter) throws CoreException {
		return this.javaProject.createType(PACKAGE_NAME, FILE_NAME, this.createSourceWriter(annotationWriter));
	}

	protected SourceWriter createSourceWriter(AnnotationWriter annotationWriter) {
		return new AnnotatedSourceWriter(annotationWriter);
	}

	protected void appendSourceTo(StringBuffer sb, AnnotationWriter annotationWriter) {
		sb.append(CR);
		for (Iterator<String> stream = annotationWriter.imports(); stream.hasNext(); ) {
			sb.append("import ");
			sb.append(stream.next());
			sb.append(";");
			sb.append(CR);
		}
		annotationWriter.appendTypeAnnotationTo(sb);
		sb.append(CR);
		sb.append("public class ").append(TYPE_NAME).append(" {").append(CR);
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


	// ********** queries **********

	protected IType jdtType() throws JavaModelException {
		return this.javaProject.findType(FULLY_QUALIFIED_TYPE_NAME);
	}

	protected Type testType() throws JavaModelException {
		return this.buildType(this.jdtType());
	}

	protected Type buildType(IType jdtType) {
		return new Type(jdtType, this.modifySharedDocumentCommandExecutorProvider());
	}

	protected FieldAttribute idField() throws JavaModelException {
		return this.fieldNamed("id");
	}

	protected FieldAttribute nameField() throws JavaModelException {
		return this.fieldNamed("name");
	}

	protected FieldAttribute fieldNamed(String fieldName) throws JavaModelException {
		return new FieldAttribute(this.jdtType().getField(fieldName), this.modifySharedDocumentCommandExecutorProvider());
	}

	protected MethodAttribute idGetMethod() throws JavaModelException {
		return this.methodNamed("getId");
	}
	
	protected MethodAttribute idSetMethod() throws JavaModelException {
		return this.method("setId", new String[] {"I"});
	}

	protected MethodAttribute nameGetMethod() throws JavaModelException {
		return this.methodNamed("getName");
	}

	protected MethodAttribute methodNamed(String methodName) throws JavaModelException {
		return this.method(methodName, EMPTY_STRING_ARRAY);
	}

	protected MethodAttribute method(String methodName, String[] parameterTypeSignatures) throws JavaModelException {
		return new MethodAttribute(this.jdtType().getMethod(methodName, parameterTypeSignatures), this.modifySharedDocumentCommandExecutorProvider());
	}

	protected String source() throws JavaModelException {
		return this.jdtType().getOpenable().getBuffer().getContents();
	}

	/**
	 * the tests will be run "headless" so use the default "shared document modifier"
	 */
	protected CommandExecutorProvider modifySharedDocumentCommandExecutorProvider() {
		return MODIFY_SHARED_DOCUMENT_COMMAND_EXECUTOR_PROVIDER;
	}

	protected static final CommandExecutorProvider MODIFY_SHARED_DOCUMENT_COMMAND_EXECUTOR_PROVIDER =
		new CommandExecutorProvider() {
			public CommandExecutor commandExecutor() {
				return CommandExecutor.Default.instance();
			}
		};
		

	// ********** test validation **********

	protected void assertSourceContains(String s) throws JavaModelException {
		String source = this.source();
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

	protected void assertSourceDoesNotContain(String s) throws JavaModelException {
		String source = this.source();
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

	@SuppressWarnings("unchecked")
	protected List<MemberValuePair> values(NormalAnnotation na) {
		return na.values();
	}

	/**
	 * check for null member value pair
	 */
	protected Expression valueInternal(MemberValuePair pair) {
		return (pair == null) ? null : pair.getValue();
	}

	/**
	 * Return the value of the *first* annotation element
	 * with the specified name.
	 * Return null if the annotation has no such element.
	 */
	protected Expression annotationElementValue(NormalAnnotation annotation, String elementName) {
		return this.valueInternal(this.memberValuePair(annotation, elementName));
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
	 * Build a string literal and set its initial value.
	 */
	protected StringLiteral newStringLiteral(AST ast, String value) {
		StringLiteral stringLiteral = ast.newStringLiteral();
		stringLiteral.setLiteralValue(value);
		return stringLiteral;
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


	// ********** member classes **********

	public interface AnnotationWriter {
		Iterator<String> imports();
		void appendTypeAnnotationTo(StringBuffer sb);
		void appendIdFieldAnnotationTo(StringBuffer sb);
		void appendNameFieldAnnotationTo(StringBuffer sb);
		void appendGetIdMethodAnnotationTo(StringBuffer sb);
		void appendSetIdMethodAnnotationTo(StringBuffer sb);
		void appendGetNameMethodAnnotationTo(StringBuffer sb);
		void appendSetNameMethodAnnotationTo(StringBuffer sb);
		void appendMemberTypeTo(StringBuffer sb);
		void appendTopLevelTypesTo(StringBuffer sb);
	}

	public static class DefaultAnnotationWriter implements AnnotationWriter {
		public Iterator<String> imports() {return EmptyIterator.instance();}
		public void appendTypeAnnotationTo(StringBuffer sb) {/* do nothing */}
		public void appendIdFieldAnnotationTo(StringBuffer sb) {/* do nothing */}
		public void appendNameFieldAnnotationTo(StringBuffer sb) {/* do nothing */}
		public void appendGetIdMethodAnnotationTo(StringBuffer sb) {/* do nothing */}
		public void appendSetIdMethodAnnotationTo(StringBuffer sb) {/* do nothing */}
		public void appendGetNameMethodAnnotationTo(StringBuffer sb) {/* do nothing */}
		public void appendSetNameMethodAnnotationTo(StringBuffer sb) {/* do nothing */}
		public void appendMemberTypeTo(StringBuffer sb) {/* do nothing */}
		public void appendTopLevelTypesTo(StringBuffer sb) {/* do nothing */}
	}

	public static class AnnotationWriterWrapper implements AnnotationWriter {
		private final AnnotationWriter aw;
		public AnnotationWriterWrapper(AnnotationWriter aw) {
			super();
			this.aw = aw;
		}
		public Iterator<String> imports() {return aw.imports();}
		public void appendTypeAnnotationTo(StringBuffer sb) {aw.appendTypeAnnotationTo(sb);}
		public void appendIdFieldAnnotationTo(StringBuffer sb) {aw.appendIdFieldAnnotationTo(sb);}
		public void appendNameFieldAnnotationTo(StringBuffer sb) {aw.appendNameFieldAnnotationTo(sb);}
		public void appendGetIdMethodAnnotationTo(StringBuffer sb) {aw.appendGetIdMethodAnnotationTo(sb);}
		public void appendSetIdMethodAnnotationTo(StringBuffer sb) {aw.appendSetIdMethodAnnotationTo(sb);}
		public void appendGetNameMethodAnnotationTo(StringBuffer sb) {aw.appendGetNameMethodAnnotationTo(sb);}
		public void appendSetNameMethodAnnotationTo(StringBuffer sb) {aw.appendSetNameMethodAnnotationTo(sb);}
		public void appendMemberTypeTo(StringBuffer sb) {aw.appendMemberTypeTo(sb);}
		public void appendTopLevelTypesTo(StringBuffer sb) {aw.appendTopLevelTypesTo(sb);}
	}

	public class AnnotatedSourceWriter implements SourceWriter {
		private AnnotationWriter annotationWriter;
		public AnnotatedSourceWriter(AnnotationWriter annotationWriter) {
			super();
			this.annotationWriter = annotationWriter;
		}
		public void appendSourceTo(StringBuffer sb) {
			AnnotationTestCase.this.appendSourceTo(sb, this.annotationWriter);
		}
	}

}
