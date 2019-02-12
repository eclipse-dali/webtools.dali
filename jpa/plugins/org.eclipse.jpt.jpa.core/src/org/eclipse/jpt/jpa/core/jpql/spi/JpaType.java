/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpql.spi;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.persistence.jpa.jpql.ExpressionTools;
import org.eclipse.persistence.jpa.jpql.tools.spi.IConstructor;
import org.eclipse.persistence.jpa.jpql.tools.spi.ITypeDeclaration;

/**
 * The concrete implementation of {@link org.eclipse.persistence.jpa.query.spi.IType IType} that is
 * wrapping the design-time representation of a Java type.
 *
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.3
 * @since 3.0
 * @author Pascal Filion
 */
public class JpaType implements IJpaType {

	/**
	 * The cached collection of {@link IConstructor constructors}.
	 */
	private Collection<IConstructor> constructors;

	/**
	 * The list of names for the {@link Enum}'s constants otherwise an empty array.
	 */
	private String[] enumConstants;

	/**
	 * The design-time representation of a Java type.
	 */
	private IType type;

	/**
	 * The {@link ITypeBinding} for the type, which is the information that was resolved and which
	 * can be used to query information about that type.
	 */
	private ITypeBinding typeBinding;

	/**
	 * This flag makes sure to not try more than once resolving the {@link ITypeBinding} if it fails
	 * the first time.
	 */
	private boolean typeBindingResolved;

	/**
	 * Caches the type hierarchy of the {@link IType} in order to prevent rebuilding it each time.
	 */
	private ITypeDeclaration typeDeclaration;

	/**
	 * Caches the type hierarchy of the {@link IType} in order to prevent rebuilding it each time
	 * {@link #isAssignableTo(org.eclipse.persistence.jpa.query.spi.IType)} is called.
	 */
	private ITypeHierarchy typeHierarchy;

	/**
	 * The fully qualified name of the Java type.
	 */
	private final String typeName;

	/**
	 * The external form of a type repository.
	 */
	private final JpaTypeRepository typeRepository;

	/**
	 * Creates a new <code>JpaType</code>.
	 *
	 * @param typeRepository The external form of a type repository
	 * @param type The design-time representation of a Java type
	 */
	public JpaType(JpaTypeRepository typeRepository, IType type) {
		super();
		this.type           = type;
		this.typeName       = type.getFullyQualifiedName();
		this.typeRepository = typeRepository;
	}

	protected CompilationUnit buildCompilationUnit() {

		ASTParser parser = ASTTools.newParser();
		parser.setSource(this.type.getTypeRoot());

		return (CompilationUnit) parser.createAST(null);
	}

	protected Collection<IConstructor> buildConstructors() {

		ITypeBinding typeBinding = getTypeBinding();

		// No Java source is attached to the Java class file, parse the class with a reader
		if ((typeBinding == null) && this.type.isBinary()) {
			Collection<IConstructor> constructors = new ArrayList<IConstructor>();

			try {
				// Root types
				for (IJavaElement rootType : this.type.getTypeRoot().getChildren()) {
					// Root type
					if (rootType.getElementType() == IJavaElement.TYPE) {
						for (IJavaElement javaElement : ((IType) rootType).getChildren()) {
							// Method
							if (javaElement.getElementType() == IJavaElement.METHOD) {
								IMethod method = (IMethod) javaElement;
								// Constructor
								if (method.isConstructor()) {
									constructors.add(new ClassConstructor(this, method));
								}
							}
						}
					}
				}
			}
			catch (Exception e) {
				JptJpaCorePlugin.instance().logError(e);
			}

			return constructors;
		}
		// Use the ITypeBinding to retrieve the constructors
		else if (typeBinding != null) {
			Collection<IConstructor> constructors = new ArrayList<IConstructor>();

			for (IMethodBinding method : typeBinding.getDeclaredMethods()) {
				if (method.isConstructor()) {
					constructors.add(new JpaConstructor(this, method));
				}
			}

			return constructors;
		}

		return Collections.emptyList();
	}

	protected String[] buildEnumConstants() {

		try {
			// Retrieve the enum constants from IType
			if (this.type.isEnum()) {
				List<String> names = new ArrayList<String>();

				for (IField field : this.type.getFields()) {
					if (field.isEnumConstant()) {
						names.add(field.getElementName());
					}
				}

				return names.toArray(new String[names.size()]);
			}
		}
		catch (Exception e) {
			// Just ignore and return an empty array
		}

		return ExpressionTools.EMPTY_STRING_ARRAY;
	}

	protected ITypeBinding buildTypeBinding() {

		// This code was copied from ASTNodes.getTypeBinding(CompilationUnit, IType)
		try {
			CompilationUnit compilationUnit = buildCompilationUnit();

			if (this.type.isAnonymous()) {
				IJavaElement parent = this.type.getParent();

				if ((parent instanceof IField) && Flags.isEnum(((IMember) parent).getFlags())) {
					ASTNode node = NodeFinder.perform(compilationUnit, this.type.getNameRange());
					EnumConstantDeclaration constant = (EnumConstantDeclaration) node;

					if (constant != null) {
						AnonymousClassDeclaration declaration = constant.getAnonymousClassDeclaration();

						if (declaration != null) {
							return declaration.resolveBinding();
						}
					}
				}
				else {
					ASTNode node = NodeFinder.perform(compilationUnit, this.type.getNameRange());
					ClassInstanceCreation creation = (ClassInstanceCreation) getParent(node, ClassInstanceCreation.class);

					if (creation != null) {
						return creation.resolveTypeBinding();
					}
				}
			}
			else {
				ASTNode node = NodeFinder.perform(compilationUnit, this.type.getNameRange());
				AbstractTypeDeclaration declaration = (AbstractTypeDeclaration) getParent(node, AbstractTypeDeclaration.class);

				if (declaration != null) {
					return declaration.resolveBinding();
				}
			}
		}
		catch (Exception e) {
			// Simply ignore
		}

		return null;
	}

	protected ITypeDeclaration buildTypeDeclaration() {
		return new JpaTypeDeclaration(this, new ITypeDeclaration[0]);
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterable<IConstructor> constructors() {
		if (this.constructors == null) {
			this.constructors = buildConstructors();
		}
		return IterableTools.cloneSnapshot(this.constructors);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object object) {
		return equals((org.eclipse.persistence.jpa.jpql.tools.spi.IType) object);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(org.eclipse.persistence.jpa.jpql.tools.spi.IType type) {
		return (this == type) || this.typeName.equals(type.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getEnumConstants() {
		if (this.enumConstants == null) {
			this.enumConstants = buildEnumConstants();
		}
		return this.enumConstants;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return this.typeName;
	}

	protected ASTNode getParent(ASTNode node, Class<? extends ASTNode> parentClass) {

		do {
			node = node.getParent();
		}
		while ((node != null) && !parentClass.isInstance(node));

		return node;
	}

	/**
	 * Returns the encapsulated {@link Type}, which is the actual type.
	 *
	 * @return The design-time representation of a Java type
	 */
	protected IType getType() {
		return this.type;
	}

	protected ITypeBinding getTypeBinding() {
		if ((this.typeBinding == null) && !this.typeBindingResolved) {
			this.typeBinding = buildTypeBinding();
			this.typeBindingResolved = true;
		}
		return this.typeBinding;
	}

	/**
	 * {@inheritDoc}
	 */
	public ITypeDeclaration getTypeDeclaration() {
		if (this.typeDeclaration == null) {
			this.typeDeclaration = buildTypeDeclaration();
		}
		return this.typeDeclaration;
	}

	/**
	 * Returns the repository that gives access to the application's classes.
	 *
	 * @return The external form of the type repository
	 */
	protected JpaTypeRepository getTypeRepository() {
		return this.typeRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
		return this.type.getAnnotation(annotationType.getName()) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return this.typeName.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAssignableTo(org.eclipse.persistence.jpa.jpql.tools.spi.IType otherType) {

		if (equals(otherType)) {
			return true;
		}

		String otherTypeName = otherType.getName();

		// Type hierarchy for Java compiled classes
		ITypeBinding typeBinding = getTypeBinding();

		// Type hierarchy for Class files
		if ((typeBinding == null) && this.type.isBinary()) {

			// First create the type hierarchy
			if (this.typeHierarchy == null) {
				try {
					this.typeHierarchy = this.type.newSupertypeHierarchy(new NullProgressMonitor());
				}
				catch (Exception e) {
					return false;
				}
			}

			// Now check if the other type name is in the type hierarchy
			for (IType superType : this.typeHierarchy.getAllTypes()) {
				if (superType.getFullyQualifiedName().equals(otherTypeName)) {
					return true;
				}
			}
		}
		// Type hierarchy for Java source files
		else if (typeBinding != null) {
			return ASTTools.typeIsSubTypeOf(typeBinding, otherTypeName);
		}

		// Anything else is always false
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isEnum() {
		try {
			return this.type.isEnum();
		}
		catch (Exception e) {
			// Simply ignore and return no
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isResolvable() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTypeDeclaration(JpaTypeDeclaration typeDeclaration) {
		this.typeDeclaration = typeDeclaration;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return ObjectTools.toString(this, this.typeName);
	}
}
