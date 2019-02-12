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

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.persistence.jpa.jpql.tools.spi.IConstructor;
import org.eclipse.persistence.jpa.jpql.tools.spi.IType;
import org.eclipse.persistence.jpa.jpql.tools.spi.ITypeDeclaration;
import org.eclipse.persistence.jpa.jpql.tools.spi.ITypeRepository;

/**
 * The concrete implementation of {@link IConstructor} that is wrapping the design-time
 * representation of a Java constructor.
 *
 * Provisional API: This interface is part of an interim API that is still under development and
 * expected to change significantly before reaching stability. It is available at this early stage
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this
 * API will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.1
 * @since 3.0
 * @author Pascal Filion
 */
public class JpaConstructor implements IConstructor {

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
	public JpaConstructor(JpaType type, IMethodBinding constructor) {
		super();
		this.type        = type;
		this.constructor = constructor;
	}

	protected ITypeDeclaration buildTypeDeclaration(ITypeBinding parameterType) {

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

	protected ITypeDeclaration[] buildTypeDeclarations() {

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