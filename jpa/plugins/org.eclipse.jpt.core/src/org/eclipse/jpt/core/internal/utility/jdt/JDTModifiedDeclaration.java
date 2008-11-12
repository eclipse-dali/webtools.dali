/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.utility.jdt;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

/**
 * Wrap any of the AST nodes that have modifiers (specifically, annotations);
 * i.e. BodyDeclaration, SingleVariableDeclaration, VariableDeclarationExpression,
 * and VariableDeclarationStatement.
 */
public class JDTModifiedDeclaration
	implements ModifiedDeclaration
{
	private final Adapter adapter;


	// ********** constructors **********

	public JDTModifiedDeclaration(Adapter adapter) {
		super();
		this.adapter = adapter;
	}

	public JDTModifiedDeclaration(BodyDeclaration declaration) {
		this(new BodyDeclarationAdapter(declaration));
	}

	public JDTModifiedDeclaration(SingleVariableDeclaration declaration) {
		this(new SingleVariableDeclarationAdapter(declaration));
	}

	public JDTModifiedDeclaration(VariableDeclarationExpression declaration) {
		this(new VariableDeclarationExpressionAdapter(declaration));
	}

	public JDTModifiedDeclaration(VariableDeclarationStatement declaration) {
		this(new VariableDeclarationStatementAdapter(declaration));
	}


	// ********** annotations **********

	public Annotation getAnnotationNamed(String annotationName) {
		for (Iterator<Annotation> stream = this.annotations(); stream.hasNext(); ) {
			Annotation annotation = stream.next();
			if (this.annotationIsNamed(annotation, annotationName)) {
				return annotation;
			}
		}
		return null;
	}

	public void removeAnnotationNamed(String annotationName) {
		for (Iterator<IExtendedModifier> stream = this.getModifiers().iterator(); stream.hasNext(); ) {
			IExtendedModifier modifier = stream.next();
			if (modifier.isAnnotation()) {
				if (this.annotationIsNamed((Annotation) modifier, annotationName)) {
					stream.remove();
					break;
				}
			}
		}
	}

	public void replaceAnnotationNamed(String oldAnnotationName, Annotation newAnnotation) {
		List<IExtendedModifier> modifiers = this.getModifiers();
		for (ListIterator<IExtendedModifier> stream = modifiers.listIterator(); stream.hasNext(); ) {
			IExtendedModifier modifier = stream.next();
			if (modifier.isAnnotation()) {
				if (this.annotationIsNamed((Annotation) modifier, oldAnnotationName)) {
					stream.set(newAnnotation);
					return;
				}
			}
		}
		this.addAnnotation(newAnnotation);
	}

	/**
	 * Add the specified annotation to the declaration.
	 * By convention annotations precede the "standard" (JLS2) modifiers;
	 * though, technically, they can be interspersed.
	 */
	protected void addAnnotation(Annotation annotation) {
		List<IExtendedModifier> modifiers = this.getModifiers();
		for (ListIterator<IExtendedModifier> stream = modifiers.listIterator(); stream.hasNext(); ) {
			if (stream.next().isModifier()) {
				stream.previous();  // put the annotation *before* the first "standard" (JLS2) modifier
				stream.add(annotation);
				return;
			}
		}
		modifiers.add(annotation);  // just tack it on to the end
	}

	/**
	 * Return the declaration's annotations.
	 */
	protected Iterator<Annotation> annotations() {
		return new FilteringIterator<IExtendedModifier, Annotation>(this.getModifiers().iterator()) {
			@Override
			protected boolean accept(IExtendedModifier next) {
				return next.isAnnotation();
			}
		};
	}


	// ********** add import **********

	public boolean addImport(String className) {
		if (className.indexOf('.') == -1) {
			return true;  // the class is in the default package - no need for import
		}
		return this.addImport(className, false);
	}

	public boolean addStaticImport(String enumConstantName) {
		int index1 = enumConstantName.indexOf('.');
		if (index1 == -1) {
			throw new IllegalArgumentException(enumConstantName);  // shouldn't happen?
		}
		int index2 = enumConstantName.indexOf('.', index1 + 1);
		if (index2 == -1) {
			return true;  // the enum is in the default package - no need for import
		}
		return this.addImport(enumConstantName, true);
	}

	public boolean addImport(String importName, boolean staticImport) {
		Boolean include = this.importsInclude(importName, staticImport);
		if (include != null) {
			return include.booleanValue();
		}

		ImportDeclaration importDeclaration = this.getAst().newImportDeclaration();
		importDeclaration.setName(this.getAst().newName(importName));
		importDeclaration.setStatic(staticImport);
		this.getImports().add(importDeclaration);
		return true;
	}

	/**
	 * Just a bit hacky:
	 *     Return Boolean.TRUE if the import is already present.
	 *     Return Boolean.FALSE if a colliding import is already present.
	 *     Return null if a new import may be added.
	 * This hackery allows us to loop through the imports only once
	 * (and compose our methods).
	 * Pre-condition: 'importName' is not in the "default" package (i.e. it *is* qualified)
	 */
	protected Boolean importsInclude(String importName, boolean staticImport) {
		int period = importName.lastIndexOf('.');  // should not be -1
		String importNameQualifier = importName.substring(0, period);
		String shortImportName = importName.substring(period + 1);
		return this.importsInclude(importName, importNameQualifier, shortImportName, staticImport);
	}

	/**
	 * pre-calculate the qualifier and short name
	 */
	protected Boolean importsInclude(String importName, String importNameQualifier, String shortImportName, boolean staticImport) {
		for (ImportDeclaration importDeclaration : this.getImports()) {
			if (importDeclaration.isStatic() == staticImport) {
				Boolean match = this.importMatches(importDeclaration, importName, importNameQualifier, shortImportName);
				if (match != null) {
					return match;
				}
			}
		}
		return null;
	}

	/**
	 * we should be able to rely on the JDT model here, since we are looking
	 * at objects that should not be changing underneath us...
	 */
	protected Boolean importMatches(ImportDeclaration importDeclaration, String importName, String importNameQualifier, String shortImportName) {
		// examples:
		// 'importName' is "java.util.Date"
		//     or
		// 'importName' is "java.lang.annotation.ElementType.TYPE"
		String idn = importDeclaration.getName().getFullyQualifiedName();
		if (importName.equals(idn)) {
			// import java.util.Date; => "Date" will resolve to "java.util.Date"
			// import static java.lang.annotation.ElementType.TYPE; => "TYPE" will resolve to "java.lang.annotation.ElementType.TYPE"
			return Boolean.TRUE;
		}

		String shortIDN = idn.substring(idn.lastIndexOf('.') + 1);
		if (shortImportName.equals(shortIDN)) {
			// import java.sql.Date; => ambiguous resolution of "Date"
			// import static org.foo.Bar.TYPE; => ambiguous resolution of "TYPE"
			return Boolean.FALSE;
		}

		if (importDeclaration.isOnDemand()) {
			if (importNameQualifier.equals(idn)) {
				// import java.util.*; => "Date" will resolve to "java.util.Date"
				// import static java.lang.annotation.ElementType.*; => "TYPE" will resolve to "java.lang.annotation.ElementType.TYPE"
				return Boolean.TRUE;
			}
			if (importDeclaration.isStatic()) {
				if (this.enumResolves(idn, shortImportName)) {
					// import static org.foo.Bar.*; => ambiguous resolution of "TYPE"
					return Boolean.FALSE;
				}
			} else {
				if (this.typeResolves(idn + '.' + shortImportName)) {
					// import java.sql.*; => ambiguous resolution of "Date"
					return Boolean.FALSE;
				}
			}
		}
		// no matches - OK to add explicit import
		return null;
	}

	protected boolean enumResolves(String enumTypeName, String enumConstantName) {
		try {
			return this.enumResolves_(enumTypeName, enumConstantName);
		} catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	protected boolean enumResolves_(String enumTypeName, String enumConstantName) throws JavaModelException {
		IType jdtType = this.findType_(enumTypeName);
		if (jdtType == null) {
			return false;
		}
		if ( ! jdtType.isEnum()) {
			return false;
		}
		for (IField jdtField : jdtType.getFields()) {
			if (jdtField.isEnumConstant() && jdtField.getElementName().equals(enumConstantName)) {
				return true;
			}
		}
		return false;
	}

	protected boolean typeResolves(String name) {
		return this.findType(name) != null;
	}

	protected IType findType(String name) {
		try {
			return this.findType_(name);
		} catch (JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	protected IType findType_(String name) throws JavaModelException {
		return this.getCompilationUnit().getJavaElement().getJavaProject().findType(name);
	}

	protected List<ImportDeclaration> getImports() {
		return this.imports(this.getCompilationUnit());
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected List<ImportDeclaration> imports(CompilationUnit astRoot) {
		return astRoot.imports();
	}


	// ********** annotation name resolution **********

	public boolean annotationIsNamed(Annotation annotation, String name) {
		return this.getQualifiedName(annotation).equals(name);
	}

	/**
	 * Simply return the annotation's unqualified name if we can't "resolve" it.
	 */
	protected String getQualifiedName(Annotation annotation) {
		ITypeBinding typeBinding = annotation.resolveTypeBinding();
		if (typeBinding != null) {
			String resolvedName = typeBinding.getQualifiedName();
			if (resolvedName != null) {
				return resolvedName;
			}
		}
		// hack(?): check for a matching import because when moving a stand-alone
		// annotation to its container in CombinationIndexedDeclarationAnnotationAdapter
		// the container's import is added but then it won't "resolve" upon
		// subsequent lookups (because the parser hasn't had time to run?)... :-(
		return this.convertToFullClassName(annotation.getTypeName().getFullyQualifiedName());
	}

	/**
	 * If necessary, use the declaration's imports to calculate a guess as to
	 * the specified name's fully-qualified form.
	 * Simply return the unqualified name if we can't "resolve" it.
	 */
	protected String convertToFullClassName(String name) {
		// check for fully-qualified name
		return (name.lastIndexOf('.') != -1) ? name : this.resolveAgainstImports(name, false);
	}

	/**
	 * If necessary, use the declaration's imports to calculate a guess as to
	 * the specified name's fully-qualified form.
	 * Simply return the unqualified name if we can't "resolve" it.
	 */
	protected String convertToFullEnumConstantName(String name) {
		int index1 = name.indexOf('.');
		if (index1 == -1) {
			// short name, e.g. "TYPE"
			// true = look for static import of enum constant
			return this.resolveAgainstImports(name, true);
		}

		int index2 = name.indexOf('.', index1 + 1);
		if (index2 == -1) {
			// partially-qualified name, e.g. "ElementType.TYPE"
			// false = look regular import of enum class, not static import of enum constant
			return this.resolveAgainstImports(name, false);
		}

		// fully-qualified name, e.g. "java.lang.annotation.ElementType.TYPE"
		return name;
	}

	protected String resolveAgainstImports(String shortName, boolean static_) {
		for (ImportDeclaration importDeclaration : this.getImports()) {
			if (importDeclaration.isStatic() == static_) {
				String resolvedName = this.resolveAgainstImport(importDeclaration, shortName);
				if (resolvedName != null) {
					return resolvedName;
				}
			}
		}
		return shortName;  // "default" package or unknown
	}

	/**
	 * TODO handle wildcards?
	 * ignoring wildcards will work most of them time, since *we* added the
	 * explicit import for the container annotation earlier
	 */
	protected String resolveAgainstImport(ImportDeclaration importDeclaration, String shortName) {
		String idn = importDeclaration.getName().getFullyQualifiedName();
		if (importDeclaration.isOnDemand()) {
			String candidate = idn + '.' + shortName;
			if (importDeclaration.isStatic()) {
				if (this.enumResolves(idn, shortName)) {
					return candidate;
				}
			} else {
				if (this.typeResolves(candidate)) {
					return candidate;
				}
			}
			// no match
			return null;
		}

		// explicit import - see whether its end matches 'shortName'
		int period = idn.length() - shortName.length() - 1;
		if (period < 1) {
			// something must precede period
			return null;
		}
		if ((idn.charAt(period) == '.') && idn.endsWith(shortName)) {
			return idn;  // probable exact match
		}
		return null;
	}


	// ********** miscellaneous methods **********

	public ASTNode getDeclaration() {
		return this.adapter.getDeclaration();
	}

	/**
	 * Return the declaration's list of modifiers.
	 * Element type: org.eclipse.jdt.core.dom.IExtendedModifier
	 */
	protected List<IExtendedModifier> getModifiers() {
		return this.adapter.getModifiers();
	}

	public AST getAst() {
		return this.getDeclaration().getAST();
	}

	protected CompilationUnit getCompilationUnit() {
		return (CompilationUnit) this.getDeclaration().getRoot();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.adapter.toString());
	}


	// ********** declaration adapter interface and implementations **********

	/**
	 * Define common protocol among the various "declarations".
	 */
	public interface Adapter {

		/**
		 * Return the adapted "declaration".
		 */
		ASTNode getDeclaration();

		/**
		 * Return the "declaration"'s list of modifiers.
		 * Element type: org.eclipse.jdt.core.dom.IExtendedModifier
		 */
		List<IExtendedModifier> getModifiers();

	}

	public static class BodyDeclarationAdapter implements Adapter {
		private final BodyDeclaration declaration;
		public BodyDeclarationAdapter(BodyDeclaration declaration) {
			super();
			this.declaration = declaration;
		}
		public ASTNode getDeclaration() {
			return this.declaration;
		}
		@SuppressWarnings("unchecked")
		public List<IExtendedModifier> getModifiers() {
			return this.declaration.modifiers();
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.declaration.toString());
		}
	}

	public static class SingleVariableDeclarationAdapter implements Adapter {
		private final SingleVariableDeclaration declaration;
		public SingleVariableDeclarationAdapter(SingleVariableDeclaration declaration) {
			super();
			this.declaration = declaration;
		}
		public ASTNode getDeclaration() {
			return this.declaration;
		}
		@SuppressWarnings("unchecked")
		public List<IExtendedModifier> getModifiers() {
			return this.declaration.modifiers();
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.declaration.toString());
		}
	}

	public static class VariableDeclarationExpressionAdapter implements Adapter {
		private final VariableDeclarationExpression declaration;
		public VariableDeclarationExpressionAdapter(VariableDeclarationExpression declaration) {
			super();
			this.declaration = declaration;
		}
		public ASTNode getDeclaration() {
			return this.declaration;
		}
		@SuppressWarnings("unchecked")
		public List<IExtendedModifier> getModifiers() {
			return this.declaration.modifiers();
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.declaration.toString());
		}
	}

	public static class VariableDeclarationStatementAdapter implements Adapter {
		private final VariableDeclarationStatement declaration;
		public VariableDeclarationStatementAdapter(VariableDeclarationStatement declaration) {
			super();
			this.declaration = declaration;
		}
		public ASTNode getDeclaration() {
			return this.declaration;
		}
		@SuppressWarnings("unchecked")
		public List<IExtendedModifier> getModifiers() {
			return this.declaration.modifiers();
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.declaration.toString());
		}
	}

}
