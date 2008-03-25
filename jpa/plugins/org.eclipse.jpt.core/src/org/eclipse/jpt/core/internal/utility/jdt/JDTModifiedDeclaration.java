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
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
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
 * 
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


	// ********** public methods **********

	public ASTNode declaration() {
		return this.adapter.declaration();
	}

	/**
	 * Return the declaration's list of modifiers.
	 * Element type: org.eclipse.jdt.core.dom.IExtendedModifier
	 */
	public List<IExtendedModifier> modifiers() {
		return this.adapter.modifiers();
	}

	public AST ast() {
		return this.declaration().getAST();
	}

	public CompilationUnit compilationUnit() {
		return (CompilationUnit) this.declaration().getRoot();
	}

	public ICompilationUnit iCompilationUnit() {
		return (ICompilationUnit) this.compilationUnit().getJavaElement();
	}

	public IType type() {
		return this.compilationUnit().getTypeRoot().findPrimaryType();
	}

	/**
	 * Return the declaration's annotations.
	 */
	public Iterator<Annotation> annotations() {
		return new FilteringIterator<IExtendedModifier, Annotation>(this.modifiers().iterator()) {
			@Override
			protected boolean accept(IExtendedModifier next) {
				return next.isAnnotation();
			}
		};
	}

	public Annotation annotationNamed(String annotationName) {
		for (Iterator<Annotation> stream = this.annotations(); stream.hasNext(); ) {
			Annotation annotation = stream.next();
			if (this.annotationIsNamed(annotation, annotationName)) {
				return annotation;
			}
		}
		return null;
	}

	/**
	 * Return whether the declaration has an annotation with the specified name.
	 */
	public boolean containsAnnotationNamed(String annotationName) {
		return this.annotationNamed(annotationName) != null;
	}

	/**
	 * Add the specified annotation to the declaration.
	 * By convention annotations precede the "standard" (JLS2) modifiers;
	 * though, technically, they can be interspersed.
	 */
	public void addAnnotation(Annotation annotation) {
		List<IExtendedModifier> modifiers = this.modifiers();
		for (ListIterator<IExtendedModifier> stream = modifiers.listIterator(); stream.hasNext(); ) {
			if (stream.next().isModifier()) {
				stream.previous();  // put the annotation *before* the first "standard" (JLS2) modifier
				stream.add(annotation);
				return;
			}
		}
		modifiers.add(annotation);  // just tack it on to the end
	}

	public void removeAnnotationNamed(String annotationName) {
		for (Iterator<IExtendedModifier> stream = this.modifiers().iterator(); stream.hasNext(); ) {
			IExtendedModifier modifier = stream.next();
			if (modifier.isAnnotation()) {
				if (this.annotationIsNamed((Annotation) modifier, annotationName)) {
					stream.remove();
					break;
				}
			}
		}
	}

	/**
	 * Remove the specified annotation from the declaration.
	 */
	public void removeAnnotation(Annotation annotation) {
		if ( ! this.modifiers().remove(annotation)) {
			throw new IllegalArgumentException("invalid annotation: " + annotation);
		}
	}

	/**
	 * Replace the specified old annotation with the specified new annotation.
	 */
	public void replaceAnnotation(Annotation oldAnnotation, Annotation newAnnotation) {
		for (ListIterator<IExtendedModifier> stream = this.modifiers().listIterator(); stream.hasNext(); ) {
			if (stream.next().equals(oldAnnotation)) {
				stream.set(newAnnotation);
				return;
			}
		}
		throw new IllegalArgumentException("invalid old annotation: " + oldAnnotation);
	}

	public void replaceAnnotationNamed(String oldAnnotationName, Annotation newAnnotation) {
		List<IExtendedModifier> modifiers = this.modifiers();
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

	public void addImport(String importName) {
		this.addImport(importName, false);
	}

	public void addStaticImport(String importName) {
		this.addImport(importName, true);
	}

	public void addImport(String importName, boolean static_) {
		if (importName.indexOf('.') != -1) {
			this.addImportTo(this.compilationUnit(), importName, static_);
		}
	}

	public String importFor(String shortName) {
		return this.importFor(shortName, false);
	}

	public String staticImportFor(String shortName) {
		return this.importFor(shortName, true);
	}

	// TODO handle wildcards
	public String importFor(String shortName, boolean static_) {
		if (shortName.indexOf('.') != -1) {
			return shortName;
		}
		List<ImportDeclaration> imports = this.imports(this.compilationUnit());
		for (ImportDeclaration importDeclaration : imports) {
			if (this.importIsFor(importDeclaration, shortName, static_)) {
				return importDeclaration.getName().getFullyQualifiedName();
			}
		}
		return null;
	}

	protected boolean importIsFor(ImportDeclaration importDeclaration, String shortName, boolean static_) {
		if (importDeclaration.isStatic() != static_) {
			return false;
		}
		String importDeclarationName = importDeclaration.getName().getFullyQualifiedName();
		return importDeclarationName.endsWith(shortName);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.adapter.toString());
	}


	// ********** internal methods **********

	public boolean annotationIsNamed(Annotation annotation, String name) {
		String qualifiedName = this.qualifiedName(annotation);
		return (qualifiedName != null) && qualifiedName.equals(name);
	}

	// TODO
	private String qualifiedName(Annotation annotation) {
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
		// subsequent lookups... :-(
		return this.importFor(annotation.getTypeName().getFullyQualifiedName());  // look for a matching import

		// OLD METHOD SOURCE:
//		String name = annotation.getTypeName().getFullyQualifiedName();
//		if (name.indexOf('.') != -1) {
//			return name;  // name is already qualified
//		}
//		String resolvedName = JDTTools.resolve(name, this.type());
//		// hack(?): check for a matching import because when moving a stand-alone
//		// annotation to its container in CombinationIndexedDeclarationAnnotationAdapter
//		// the container's import is added but then it won't "resolve" upon
//		// subsequent lookups... :-(
//		return this.importFor(name);  // look for a matching import
	}

	/**
	 * Return whether the specified import was added without a collision.
	 */
	// TODO handle collisions (e.g. java.util.Date vs. java.sql.Date)
	protected void addImportTo(CompilationUnit astRoot, String importName, boolean static_) {
		List<ImportDeclaration> imports = this.imports(astRoot);
		if (this.importsInclude(imports, importName, static_)) {
			return;
		}
		AST ast = astRoot.getAST();
		ImportDeclaration import_ = ast.newImportDeclaration();
		import_.setName(ast.newName(importName));
		import_.setStatic(static_);
		imports.add(import_);
	}

	@SuppressWarnings("unchecked")
	protected List<ImportDeclaration> imports(CompilationUnit astRoot) {
		return astRoot.imports();
	}

	protected boolean importsInclude(List<ImportDeclaration> imports, String importName, boolean static_) {
		for (ImportDeclaration importDeclaration : imports) {
			if (this.importIncludes(importDeclaration, importName, static_)) {
				return true;
			}
		}
		return false;
	}

	protected boolean importIncludes(ImportDeclaration importDeclaration, String importName, boolean static_) {
		if (importDeclaration.isStatic() != static_) {
			return false;
		}
		String importDeclarationName = importDeclaration.getName().getFullyQualifiedName();
		if (importName.equals(importDeclarationName)) {
			return true;
		}
		if (importDeclaration.isOnDemand()
				&& this.onDemandNameFor(importName).equals(importDeclarationName)) {
			return true;
		}
		return false;
	}

	protected String onDemandNameFor(String importName) {
		int lastPeriod = importName.lastIndexOf('.');
		return (lastPeriod == -1) ? "" : importName.substring(0, lastPeriod);
	}
	

	// ********** declaration adapter interface and implementations **********

	/**
	 * Define common protocol among the various "declarations".
	 */
	public interface Adapter {

		/**
		 * Return the adapted "declaration".
		 */
		ASTNode declaration();

		/**
		 * Return the "declaration"'s list of modifiers.
		 * Element type: org.eclipse.jdt.core.dom.IExtendedModifier
		 */
		List<IExtendedModifier> modifiers();

	}

	public static class BodyDeclarationAdapter implements Adapter {
		private final BodyDeclaration declaration;
		public BodyDeclarationAdapter(BodyDeclaration declaration) {
			super();
			this.declaration = declaration;
		}
		public ASTNode declaration() {
			return this.declaration;
		}
		@SuppressWarnings("unchecked")
		public List<IExtendedModifier> modifiers() {
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
		public ASTNode declaration() {
			return this.declaration;
		}
		@SuppressWarnings("unchecked")
		public List<IExtendedModifier> modifiers() {
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
		public ASTNode declaration() {
			return this.declaration;
		}
		@SuppressWarnings("unchecked")
		public List<IExtendedModifier> modifiers() {
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
		public ASTNode declaration() {
			return this.declaration;
		}
		@SuppressWarnings("unchecked")
		public List<IExtendedModifier> modifiers() {
			return this.declaration.modifiers();
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.declaration.toString());
		}
	}

}
