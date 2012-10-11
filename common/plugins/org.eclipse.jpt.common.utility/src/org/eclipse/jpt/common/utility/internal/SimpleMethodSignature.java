/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.eclipse.jpt.common.utility.JavaType;
import org.eclipse.jpt.common.utility.MethodSignature;

/**
 * Straightforward implementation of the MethodSignature interface.
 */
public final class SimpleMethodSignature
	implements MethodSignature, Cloneable, Serializable
{
	private final String name;

	/**
	 * store the parameter types as names, so we can reference classes
	 * that are not loaded
	 */
	private final JavaType[] parameterTypes;

	public static final JavaType[] EMPTY_PARAMETER_TYPES = new JavaType[0];

	private static final String PARAMETER_SEPARATOR = ", "; //$NON-NLS-1$
	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a method signature with the specified name and
	 * no parameter types.
	 */
	public SimpleMethodSignature(String name) {
		this(name, EMPTY_PARAMETER_TYPES);
	}

	/**
	 * Construct a method signature with the specified name and parameter
	 * types.
	 */
	public SimpleMethodSignature(String name, JavaType... parameterTypes) {
		super();
		if ((name == null) || (name.length() == 0)) {
			throw new IllegalArgumentException("The name is required."); //$NON-NLS-1$
		}
		if (parameterTypes == null) {
			throw new IllegalArgumentException("The parameter types are required."); //$NON-NLS-1$
		}
		checkParameterTypes(parameterTypes);
		this.name = name;
		this.parameterTypes = parameterTypes;
	}

	private static void checkParameterTypes(JavaType[] parameterTypes) {
		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i] == null) {
				throw new IllegalArgumentException("Missing parameter type: " + i); //$NON-NLS-1$
			}
			if (parameterTypes[i].getElementTypeName().equals(void.class.getName())) {
				throw new IllegalArgumentException("A parameter type of 'void' is not allowed: " + i); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Construct a method signature with the specified name and parameter
	 * types.
	 */
	public SimpleMethodSignature(String name, String... parameterTypeNames) {
		this(name, buildParameterTypes(parameterTypeNames));
	}

	private static JavaType[] buildParameterTypes(String[] parameterTypeNames) {
		if (parameterTypeNames == null) {
			throw new IllegalArgumentException("The parameter type names are required."); //$NON-NLS-1$
		}
		JavaType[] parameterTypes = new JavaType[parameterTypeNames.length];
		for (int i = 0; i < parameterTypeNames.length; i++) {
			if (parameterTypeNames[i] == null) {
				throw new IllegalArgumentException("Missing parameter type name: " + i); //$NON-NLS-1$
			}
			parameterTypes[i] = new SimpleJavaType(parameterTypeNames[i]);
		}
		return parameterTypes;
	}

	/**
	 * Construct a method signature with the specified name and parameter
	 * types.
	 */
	public SimpleMethodSignature(String name, Class<?>... parameterJavaClasses) {
		this(name, buildParameterTypeNames(parameterJavaClasses));
	}

	private static String[] buildParameterTypeNames(Class<?>[] parameterJavaClasses) {
		if (parameterJavaClasses == null) {
			throw new IllegalArgumentException("The parameter Java classes are required."); //$NON-NLS-1$
		}
		String[] parameterTypeNames = new String[parameterJavaClasses.length];
		for (int i = 0; i < parameterJavaClasses.length; i++) {
			if (parameterJavaClasses[i] == null) {
				throw new IllegalArgumentException("Missing parameter Java class: " + i); //$NON-NLS-1$
			}
			parameterTypeNames[i] = parameterJavaClasses[i].getName();
		}
		return parameterTypeNames;
	}

	/**
	 * Construct a method signature for the specified Java method.
	 */
	public SimpleMethodSignature(Method method) {
		this(method.getName(), method.getParameterTypes());
	}


	// ********** accessors **********

	public String getName() {
		return this.name;
	}

	public JavaType[] getParameterTypes() {
		return this.parameterTypes;
	}


	// ********** comparison **********

	public boolean describes(Method method) {
		return this.name.equals(method.getName())
				&& this.parameterTypesDescribe(method.getParameterTypes());
	}

	private boolean parameterTypesDescribe(Class<?>[] otherParameterTypes) {
		JavaType[] localParameterTypes = this.parameterTypes;
		int len = localParameterTypes.length;
		if (otherParameterTypes.length != len) {
			return false;
		}
		for (int i = len; i-- > 0; ) {
			if ( ! localParameterTypes[i].describes(otherParameterTypes[i])) {
				return false;
			}
		}
        return true;
	}

	public boolean equals(String otherName, JavaType[] otherParameterTypes) {
		return this.name.equals(otherName)
				&& Arrays.equals(this.parameterTypes, otherParameterTypes);
	}

	public boolean equals(MethodSignature other) {
		return this.equals(other.getName(), other.getParameterTypes());
	}

	@Override
	public boolean equals(Object o) {
		return (this == o) ? true : (o instanceof MethodSignature) ? this.equals((MethodSignature) o) : false;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode() ^ Arrays.hashCode(this.parameterTypes);
	}


	// ********** printing and displaying **********

	public String getSignature() {
		StringBuilder sb = new StringBuilder(200);
		this.appendSignatureTo(sb);
		return sb.toString();
	}

	public void appendSignatureTo(StringBuilder sb) {
		sb.append(this.name);
		sb.append('(');
		JavaType[] localParameterTypes = this.parameterTypes;
		int len = localParameterTypes.length;
		for (int i = 0; i < len; i++) {
			if (i != 0) {
				sb.append(PARAMETER_SEPARATOR);
			}
			localParameterTypes[i].appendDeclarationTo(sb);
		}
		sb.append(')');
	}

	public void printSignatureOn(PrintWriter pw) {
		pw.print(this.name);
		pw.print('(');
		JavaType[] localParameterTypes = this.parameterTypes;
		int len = localParameterTypes.length;
		for (int i = 0; i < len; i++) {
			if (i != 0) {
				pw.print(PARAMETER_SEPARATOR);
			}
			localParameterTypes[i].printDeclarationOn(pw);
		}
		pw.print(')');
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(200);
		sb.append(this.getClass().getSimpleName());
		sb.append('(');
		this.appendSignatureTo(sb);
		sb.append(')');
		return sb.toString();
	}


	// ********** cloning **********

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

}
