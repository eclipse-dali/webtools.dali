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

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.persistence.jpa.jpql.spi.IConstructor;
import org.eclipse.persistence.jpa.jpql.spi.IType;
import org.eclipse.persistence.jpa.jpql.spi.ITypeDeclaration;
import org.eclipse.persistence.jpa.jpql.spi.ITypeRepository;

/**
 * The concrete implementation of {@link IConstructor} that is wrapping the design-time
 * representation of a Java constructor.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
final class JpaConstructor implements IConstructor {

	/**
	 * The design-time representation of a Java constructor.
	 */
	private IMethodBinding constructor;

	/**
	 * The cached {@link ITypeDeclaration ITypeDeclarations} representing each of the constructor's
	 * parameter types.
	 */
	private ITypeDeclaration[] typeDeclarations;

	/**
	 * The type owning this constructor.
	 */
	private JpaType type;

	/**
	 * Creates a new <code>JpaConstructor</code>.
	 *
	 * @param type The type owning the constructor
	 * @param constructor The design-time representation of a Java constructor
	 */
	JpaConstructor(JpaType type, IMethodBinding constructor) {
		super();
		this.type        = type;
		this.constructor = constructor;
	}

	private ITypeDeclaration buildTypeDeclaration(ITypeBinding parameterType) {

		boolean array = parameterType.isArray();
		String typeParameterName;

		if (array) {
			ITypeBinding componentType = parameterType.getComponentType();

			// <T>[] or <? extends <class_name>>[]
			if (componentType.isTypeVariable() ||
			    componentType.isParameterizedType()) {

				typeParameterName = componentType.getErasure().getQualifiedName();
			}
			else {
				typeParameterName = componentType.getQualifiedName();
			}

			// Now for the type arguments, we have to use the component type
			parameterType = componentType;
		}
		// <T> or <? extends <class_name>>
		else if (parameterType.isTypeVariable() ||
		         parameterType.isParameterizedType()) {

			typeParameterName = parameterType.getErasure().getQualifiedName();
		}
		else {
			typeParameterName = parameterType.getQualifiedName();
		}

		// Retrieve the fully qualified name of the type
		ITypeRepository typeRepository = type.getTypeRepository();
		ITypeBinding[] typeArguments = parameterType.getTypeArguments();
		ITypeDeclaration[] genericTypes = new ITypeDeclaration[typeArguments.length];
		int index = 0;

		for (ITypeBinding typeArgument : typeArguments) {
			String genericTypeName = typeArgument.getErasure().getQualifiedName();
			IType genericType = typeRepository.getType(genericTypeName);
			genericTypes[index++] = genericType.getTypeDeclaration();
		}

		return new JpaTypeDeclaration(
			typeRepository.getType(typeParameterName),
			genericTypes,
			parameterType.getDimensions()
		);
	}

	private ITypeDeclaration[] buildTypeDeclarations() {

		ITypeBinding[] parameterTypes = constructor.getParameterTypes();
		ITypeDeclaration[] declarations = new ITypeDeclaration[parameterTypes.length];

		for (int index = declarations.length; --index >= 0; ) {
			declarations[index] = buildTypeDeclaration(parameterTypes[index]);
		}

		return declarations;
	}

	/**
	 * {@inheritDoc}
	 */
	public ITypeDeclaration[] getParameterTypes() {
		if (typeDeclarations == null) {
			typeDeclarations = buildTypeDeclarations();
		}
		return typeDeclarations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return constructor.toString();
	}
}