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

import org.eclipse.jdt.core.BindingKey;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.Signature;
import org.eclipse.persistence.jpa.jpql.spi.IConstructor;
import org.eclipse.persistence.jpa.jpql.spi.IType;
import org.eclipse.persistence.jpa.jpql.spi.ITypeDeclaration;
import org.eclipse.persistence.jpa.jpql.spi.ITypeRepository;

/**
 * The concrete implementation of {@link IConstructor} that is wrapping the {@link IMethod}
 * representation of a Java constructor (either coming from a Java compiled file or a Java source
 * file).
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
final class ClassConstructor implements IConstructor {

	/**
	 * The information of the constructor.
	 */
	private IMethod method;

	/**
	 * The declaring type of this constructor.
	 */
	private JpaType type;

	/**
	 * The cached {@link ITypeDeclaration ITypeDeclarations} representing each of the constructor's
	 * parameter types.
	 */
	private ITypeDeclaration[] typeDeclarations;

	/**
	 * Creates a new <code>ClassConstructor</code>.
	 *
	 * @param type The declaring type of this constructor
	 * @param methodInfo The information of the constructor
	 */
	ClassConstructor(JpaType type, IMethod method) {
		super();
		this.type   = type;
		this.method = method;
	}

	private ITypeDeclaration[] buildParameterTypes() {

		BindingKey bindingKey = new BindingKey(method.getKey());
		String signature = bindingKey.toSignature();

		int count = Signature.getParameterCount(signature);
		ITypeDeclaration[] typeDeclarations = new ITypeDeclaration[count];
		int index = 0;

		for (String parameterType : Signature.getParameterTypes(signature)) {

			// 1. Retrieve the parameter type (without the type parameters)
			String parameterTypeName = Signature.getTypeErasure(parameterType);

			// 3. Convert the type signature to a dot-based name
			parameterTypeName = Signature.toString(parameterTypeName);

			// 4. Create the ITypeDeclaration
			typeDeclarations[index++] = new JpaTypeDeclaration(
				getTypeRepository().getType(parameterTypeName),
				buildTypeParameters(parameterType),
				Signature.getArrayCount(parameterType)
			);
		}

		return typeDeclarations;
	}

	private ITypeDeclaration[] buildTypeParameters(String signature) {

		String[] typeParameters = Signature.getTypeArguments(signature);
		ITypeDeclaration[] generics = new ITypeDeclaration[typeParameters.length];

		for (int index = 0; index < typeParameters.length; index++) {
			String typeParameter = typeParameters[index];

			// 1. Retrieve the parameter type (without the wild cards)
			switch (Signature.getTypeSignatureKind(typeParameter)) {
				case Signature.WILDCARD_TYPE_SIGNATURE: {
					typeParameter = typeParameter.substring(1);
				}
			}

			if (typeParameter.length() == 0) {
				generics[index] = getTypeRepository().getTypeHelper().objectTypeDeclaration();
			}
			else {
				String typeParameterName = Signature.getTypeErasure(typeParameter);

				// 3. Convert the type signature to a dot-based name
				typeParameterName = Signature.toString(typeParameterName);

				// 3. Retrieve the IType for the type parameter
				IType genericType = getTypeRepository().getType(typeParameterName);

				if (genericType.isResolvable()) {
					generics[index] = genericType.getTypeDeclaration();
				}
				else {
					generics[index] = getTypeRepository().getTypeHelper().objectTypeDeclaration();
				}
			}
		}

		return generics;
	}

	/**
	 * {@inheritDoc}
	 */
	public ITypeDeclaration[] getParameterTypes() {
		if (typeDeclarations == null) {
			typeDeclarations = buildParameterTypes();
		}
		return typeDeclarations;
	}

	private ITypeRepository getTypeRepository() {
		return type.getTypeRepository();
	}
}