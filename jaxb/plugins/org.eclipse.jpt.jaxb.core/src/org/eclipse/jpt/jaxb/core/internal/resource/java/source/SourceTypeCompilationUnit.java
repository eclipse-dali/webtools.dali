/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import java.util.List;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.jaxb.core.AnnotationProvider;
import org.eclipse.jpt.jaxb.core.resource.java.AbstractJavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.utility.CommandExecutor;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;

/**
 * Java compilation unit (source file)
 * non package-info.java file
 */
public final class SourceTypeCompilationUnit
	extends SourceCompilationUnit
{

	/**
	 * The primary type of the AST compilation unit. We are not going to handle
	 * multiple types defined in a single compilation unit. Entities must have
	 * a public/protected no-arg constructor, and there is no way to access
	 * the constructor in a package class (which is what all top-level,
	 * non-primary classes must be).
	 */
	private AbstractJavaResourceType type;	


	// ********** construction **********

	public SourceTypeCompilationUnit(
			ICompilationUnit compilationUnit,
			AnnotationProvider annotationProvider, 
			AnnotationEditFormatter annotationEditFormatter,
			CommandExecutor modifySharedDocumentCommandExecutor) {
		super(compilationUnit, annotationProvider, annotationEditFormatter, modifySharedDocumentCommandExecutor);  // the compilation unit is the root of its sub-tree
		this.type = this.buildType();
	}

	private AbstractJavaResourceType buildType() {
		this.openCompilationUnit();
		CompilationUnit astRoot = this.buildASTRoot();
		this.closeCompilationUnit();
		return this.buildPersistentType(astRoot);
	}


	// ********** JavaResourceNode implementation **********

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncType(astRoot);
	}


	// ********** JavaResourceNode.Root implementation **********

	/**
	 * NB: return *all* the types since we build them all
	 */
	public Iterable<JavaResourceType> getTypes() {
		return (this.type == null) ?
				EmptyIterable.<JavaResourceType>instance() :
				this.type.getAllTypes();
	}

	/**
	 * NB: return *all* the enums since we build them all
	 */
	public Iterable<JavaResourceEnum> getEnums() {
		return (this.type == null) ?
				EmptyIterable.<JavaResourceEnum>instance() :
				this.type.getAllEnums();
	}


	// ********** JpaResourceModel implementation **********
	
	public JpaResourceType getResourceType() {
		return JptCorePlugin.JAVA_SOURCE_RESOURCE_TYPE;
	}


	// ********** JavaResourceCompilationUnit implementation **********

	public void resolveTypes() {
		if (this.type != null) {
			this.type.resolveTypes(this.buildASTRoot());
		}
	}


	// ********** persistent type **********

	private AbstractJavaResourceType buildPersistentType(CompilationUnit astRoot) {
		AbstractTypeDeclaration td = this.getPrimaryTypeDeclaration(astRoot);
		return (td == null) ? null : this.buildType(astRoot, td);
	}


	private void syncType(CompilationUnit astRoot) {
		AbstractTypeDeclaration td = this.getPrimaryTypeDeclaration(astRoot);
		if (td == null) {
			this.syncType_(null);
		} else {
			if (this.type == null) {
				this.syncType_(this.buildType(astRoot, td));
			} else {
				this.type.synchronizeWith(astRoot);
			}
		}
	}

	private void syncType_(AbstractJavaResourceType astType) {
		AbstractJavaResourceType old = this.type;
		this.type = astType;
		this.firePropertyChanged(TYPES_COLLECTION, old, astType);
	}


	// ********** internal **********

	private AbstractJavaResourceType buildType(CompilationUnit astRoot, AbstractTypeDeclaration typeDeclaration) {
		if (typeDeclaration.getNodeType() == ASTNode.TYPE_DECLARATION) {
			return SourceType.newInstance(this, (TypeDeclaration) typeDeclaration, astRoot);
		}
		else if (typeDeclaration.getNodeType() == ASTNode.ENUM_DECLARATION) {
			return SourceEnum.newInstance(this, (EnumDeclaration) typeDeclaration, astRoot);
		}
		throw new IllegalArgumentException();
	}

	/**
	 * i.e. the type with the same name as the compilation unit;
	 * return the first class or interface (ignore annotations and enums) with
	 * the same name as the compilation unit (file);
	 * NB: this type could be in error if there is an annotation or enum
	 * with the same name preceding it in the compilation unit
	 * 
	 * Return null if the parser did not resolve the type declaration's binding.
	 * This can occur if the project JRE is removed (bug 225332).
	 */
	private AbstractTypeDeclaration getPrimaryTypeDeclaration(CompilationUnit astRoot) {
		String primaryTypeName = this.getPrimaryTypeName();
		for (AbstractTypeDeclaration atd : this.types(astRoot)) {
			if (this.nodeIsPrimaryTypeDeclaration(atd, primaryTypeName)) {
				return (atd.resolveBinding() == null) ? null : atd;
			}
		}
		return null;
	}

	private boolean nodeIsPrimaryTypeDeclaration(AbstractTypeDeclaration atd, String primaryTypeName) {
		return (atd.getNodeType() == ASTNode.TYPE_DECLARATION || atd.getNodeType() == ASTNode.ENUM_DECLARATION) && 
				(atd.getName().getFullyQualifiedName().equals(primaryTypeName));
	}

	private String getPrimaryTypeName() {
		return this.getCompilationUnitName();
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<AbstractTypeDeclaration> types(CompilationUnit astRoot) {
		return astRoot.types();
	}
}
