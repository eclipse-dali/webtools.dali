/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpql;

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
import org.eclipse.jdt.core.dom.AST;
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
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.persistence.jpa.jpql.ExpressionTools;
import org.eclipse.persistence.jpa.jpql.spi.IConstructor;
import org.eclipse.persistence.jpa.jpql.spi.ITypeDeclaration;

/**
 * The concrete implementation of {@link org.eclipse.persistence.jpa.query.spi.IType IType} that is
 * wrapping the design-time representation of a Java type.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
final class JpaType implements IJpaType {

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
	JpaType(JpaTypeRepository typeRepository, IType type) {
		super();
		this.type           = type;
		this.typeName       = type.getFullyQualifiedName();
		this.typeRepository = typeRepository;
	}

	private CompilationUnit buildCompilationUnit() {

		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource(type.getTypeRoot());
		parser.setIgnoreMethodBodies(true);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);

		return (CompilationUnit) parser.createAST(new NullProgressMonitor());
	}

	private Collection<IConstructor> buildConstructors() {

		ITypeBinding typeBinding = getTypeBinding();

		// No Java source is attached to the Java class file, parse the class with a reader
		if ((typeBinding == null) && type.isBinary()) {
			Collection<IConstructor> constructors = new ArrayList<IConstructor>();

			try {
				// Root types
				for (IJavaElement rootType : type.getTypeRoot().getChildren()) {
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
				JptJpaCorePlugin.log(e);
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

	private String[] buildEnumConstants() {

		try {
			// Retrieve the enum constants from IType
			if (type.isEnum()) {
				List<String> names = new ArrayList<String>();

				for (IField field : type.getFields()) {
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

	private ITypeBinding buildTypeBinding() {

		// This code was copied from ASTNodes.getTypeBinding(CompilationUnit, IType)
		try {
			CompilationUnit compilationUnit = buildCompilationUnit();

			if (type.isAnonymous()) {
				IJavaElement parent = type.getParent();

				if ((parent instanceof IField) && Flags.isEnum(((IMember) parent).getFlags())) {
					ASTNode node = NodeFinder.perform(compilationUnit, type.getNameRange());
					EnumConstantDeclaration constant = (EnumConstantDeclaration) node;

					if (constant != null) {
						AnonymousClassDeclaration declaration = constant.getAnonymousClassDeclaration();

						if (declaration != null) {
							return declaration.resolveBinding();
						}
					}
				}
				else {
					ASTNode node = NodeFinder.perform(compilationUnit, type.getNameRange());
					ClassInstanceCreation creation = (ClassInstanceCreation) getParent(node, ClassInstanceCreation.class);

					if (creation != null) {
						return creation.resolveTypeBinding();
					}
				}
			}
			else {
				ASTNode node = NodeFinder.perform(compilationUnit, type.getNameRange());
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

	private ITypeDeclaration buildTypeDeclaration() {
		return new JpaTypeDeclaration(this, new ITypeDeclaration[0]);
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterable<IConstructor> constructors() {
		if (constructors == null) {
			constructors = buildConstructors();
		}
		return constructors;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object object) {
		return (this == object) || equals((org.eclipse.persistence.jpa.jpql.spi.IType) object);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(org.eclipse.persistence.jpa.jpql.spi.IType type) {
		return (this == type) || typeName.equals(type.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getEnumConstants() {
		if (enumConstants == null) {
			enumConstants = buildEnumConstants();
		}
		return enumConstants;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return typeName;
	}

	private ASTNode getParent(ASTNode node, Class<? extends ASTNode> parentClass) {

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
	IType getType() {
		return type;
	}

	private ITypeBinding getTypeBinding() {
		if ((typeBinding == null) && !typeBindingResolved) {
			typeBinding = buildTypeBinding();
			typeBindingResolved = true;
		}
		return typeBinding;
	}

	/**
	 * {@inheritDoc}
	 */
	public ITypeDeclaration getTypeDeclaration() {
		if (typeDeclaration == null) {
			typeDeclaration = buildTypeDeclaration();
		}
		return typeDeclaration;
	}

	/**
	 * Returns the repository that gives access to the application's classes.
	 *
	 * @return The external form of the type repository
	 */
	JpaTypeRepository getTypeRepository() {
		return typeRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
		return type.getAnnotation(annotationType.getName()) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return typeName.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAssignableTo(org.eclipse.persistence.jpa.jpql.spi.IType otherType) {

		if (this == otherType) {
			return true;
		}

		String otherTypeName = otherType.getName();

		// Type hierarchy for Java compiled classes
		ITypeBinding typeBinding = getTypeBinding();

		// Type hierarchy for Class files
		if ((typeBinding == null) && type.isBinary()) {

			// First create the type hierarchy
			if (typeHierarchy == null) {
				try {
					typeHierarchy = type.newSupertypeHierarchy(new NullProgressMonitor());
				}
				catch (Exception e) {
					return false;
				}
			}

			// Now check if the other type name is in the type hierarchy
			for (IType superType : typeHierarchy.getAllTypes()) {
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
			return type.isEnum();
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
		return StringTools.buildToStringFor(this, typeName);
	}
}