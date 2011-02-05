/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AbstractType;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.common.core.utility.jdt.Type;
import org.eclipse.jpt.common.utility.CommandExecutor;

/**
 * Adapt and extend a JDT abstract type.
 */
public abstract class AbstractJDTType
	extends JDTMember
	implements AbstractType
{

	/**
	 * constructor for the compilation unit's primary type
	 */
	protected AbstractJDTType(
			AbstractTypeDeclaration typeDeclaration,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor) {
		this(typeDeclaration, compilationUnit, modifySharedDocumentCommandExecutor, DefaultAnnotationEditFormatter.instance());
	}

	/**
	 * constructor for the compilation unit's primary type
	 */
	protected AbstractJDTType(
			AbstractTypeDeclaration typeDeclaration,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor,
			AnnotationEditFormatter annotationEditFormatter) {
		this(null, typeDeclaration, 1, compilationUnit, modifySharedDocumentCommandExecutor, annotationEditFormatter);
	}

	/**
	 * constructor for nested types
	 */
	protected AbstractJDTType(
			Type declaringType,
			AbstractTypeDeclaration typeDeclaration,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor) {
		this(declaringType, typeDeclaration, occurrence, compilationUnit, modifySharedDocumentCommandExecutor, DefaultAnnotationEditFormatter.instance());
	}

	/**
	 * constructor for nested types
	 */
	protected AbstractJDTType(
			Type declaringType,
			AbstractTypeDeclaration typeDeclaration,
			int occurrence,
			ICompilationUnit compilationUnit,
			CommandExecutor modifySharedDocumentCommandExecutor,
			AnnotationEditFormatter annotationEditFormatter) {
		super(declaringType, typeDeclaration.getName().getFullyQualifiedName(), occurrence, compilationUnit, modifySharedDocumentCommandExecutor, annotationEditFormatter);
	}

	/**
	 * constructor for testing
	 */
	protected AbstractJDTType(Type declaringType, String name, int occurrence, ICompilationUnit compilationUnit) {
		super(declaringType, name, occurrence, compilationUnit, CommandExecutor.Default.instance(), DefaultAnnotationEditFormatter.instance());
	}

	@Override
	protected Type getDeclaringType() {
		return (Type) super.getDeclaringType();
	}

	// ********** Member/Type implementation **********
	
	public ITypeBinding getBinding(CompilationUnit astRoot) {
		AbstractTypeDeclaration td = this.getBodyDeclaration(astRoot);
		return (td == null) ? null : td.resolveBinding();
	}

	/**
	 * find the type's body declaration in the specified AST
	 */
	@Override
	public AbstractTypeDeclaration getBodyDeclaration(CompilationUnit astRoot) {
		Type declaringType = this.getDeclaringType();
		if (declaringType == null) {
			return this.getTopLevelTypeDeclaration(astRoot);
		}
		TypeDeclaration typeDeclaration = declaringType.getBodyDeclaration(astRoot);
		// the type declaration can be null when the source is completely hosed
		return (typeDeclaration == null) ? null : this.getNestedTypeDeclaration(typeDeclaration);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return new ASTNodeTextRange(this.getBodyDeclaration(astRoot).getName());
	}


	// ********** internal **********

	/**
	 * return the first top-level type in the specified AST with a matching name
	 */
	protected AbstractTypeDeclaration getTopLevelTypeDeclaration(CompilationUnit astRoot) {
		return this.getTypeDeclaration(types(astRoot));
	}

	protected AbstractTypeDeclaration getTypeDeclaration(List<AbstractTypeDeclaration> typeDeclarations) {
		return this.getTypeDeclaration(typeDeclarations.toArray(new AbstractTypeDeclaration[typeDeclarations.size()]));
	}

	/**
	 * Return the nested type declaration with matching name and occurrence within the declaring type
	 */
	protected abstract AbstractTypeDeclaration getNestedTypeDeclaration(TypeDeclaration declaringTypeDeclaration);

	/**
	 * return the type declaration corresponding to the type from the specified
	 * set of type declarations (match name and occurrence).
	 * Only return type or enum declarations
	 */
	protected AbstractTypeDeclaration getTypeDeclaration(AbstractTypeDeclaration[] typeDeclarations) {
		String name = this.getName();
		int occurrence = this.getOccurrence();
		int count = 0;
		for (AbstractTypeDeclaration typeDeclaration : typeDeclarations) {
			if (typeDeclaration.getName().getFullyQualifiedName().equals(name)) {
				count++;
				if (count == occurrence) {
					return (typeDeclaration.getNodeType() == this.getASTNodeType()) ? typeDeclaration : null;
				}
			}
		}
		// return null if the type is no longer in the source code;
		// this can happen when the context model has not yet
		// been synchronized with the resource model but is still
		// asking for an ASTNode (e.g. during a selection event)
		return null;
	}

	protected abstract int getASTNodeType();

	/**
	 * we only instantiate a single top-level, non-annotation
	 * type per compilation unit (i.e. a class, enum, or interface)
	 */
	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	protected static List<AbstractTypeDeclaration> types(CompilationUnit astRoot) {
		return astRoot.types();
	}

	protected static EnumDeclaration[] getEnums(TypeDeclaration declaringTypeDeclaration) {
		List<BodyDeclaration> bd = bodyDeclarations(declaringTypeDeclaration);
		int typeCount = 0;
		for (Iterator<BodyDeclaration> it = bd.listIterator(); it.hasNext(); ) {
			if (it.next().getNodeType() == ASTNode.ENUM_DECLARATION) {
				typeCount++;
			}
		}
		EnumDeclaration[] memberEnums = new EnumDeclaration[typeCount];
		int next = 0;
		for (Iterator<BodyDeclaration> it = bd.listIterator(); it.hasNext(); ) {
			BodyDeclaration decl = it.next();
			if (decl.getNodeType() == ASTNode.ENUM_DECLARATION) {
				memberEnums[next++] = (EnumDeclaration) decl;
			}
		}
		return memberEnums;
	}

	@SuppressWarnings("unchecked")
	protected static List<BodyDeclaration> bodyDeclarations(TypeDeclaration typeDeclaration) {
		return typeDeclaration.bodyDeclarations();
	}

}
