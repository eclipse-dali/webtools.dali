/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.Modifier;

public class JPTTools {

	/**
	 * Return whether the specified field may be "persisted".
	 * According to the spec, "All non-transient instance variables that are not 
	 * annotated with the Transient annotation are persistent."
	 */
	public static boolean fieldIsPersistable(IVariableBinding field) {
		int modifiers = field.getModifiers();
		if (Modifier.isStatic(modifiers)) {
			return false;
		}
		if (Modifier.isTransient(modifiers)) {
			return false;
		}
		return true;
	}

	/**
	 * Return whether the specified method is a "getter" method that
	 * represents a property that may be "persisted".
	 */
	public static boolean methodIsPersistablePropertyGetter(IMethodBinding methodBinding) {
		if (methodHasBadModifiers(methodBinding)) {
			return false;
		}

		ITypeBinding returnType = methodBinding.getReturnType();
		if (returnType == null) {
			return false;
		}
		String returnTypeName = returnType.getQualifiedName();
		if (returnTypeName.equals("void")) {
			return false;
		}
		if (methodBinding.getParameterTypes().length != 0) {
			return false;
		}

		String methodName = methodBinding.getName();
		int beginIndex = 0;
		boolean booleanGetter = false;
		if (methodName.startsWith("is")) {
			if (returnTypeName.equals("boolean")) {
				beginIndex = 2;
			} else {
				return false;
			}
		} else {
			if (methodName.startsWith("get")) {
				beginIndex = 3;
				if (returnTypeName.equals("boolean")) {
					booleanGetter = true;
				}
			} else {
				return false;
			}
		}

		String capitalizedAttributeName = methodName.substring(beginIndex);
		// if the type has both methods:
		//     boolean isProperty()
		//     boolean getProperty()
		// then #isProperty() takes precedence and we ignore #getProperty()
		// (see the JavaBeans spec 1.01)
		if (booleanGetter) {
			IMethodBinding isMethod = methodBindingNoParameters(methodBinding.getDeclaringClass(), "is" + capitalizedAttributeName);
			if (isMethod == null) {
				return false;
			}
			if (isMethod.getReturnType().getName().equals("boolean")) {
				return false;
			}
		}
		IMethodBinding setMethod = methodBindingOneParameter(methodBinding.getDeclaringClass(), "set" + capitalizedAttributeName, returnTypeName);
		if (setMethod == null) {
			return false;
		}
		if (methodHasBadModifiers(setMethod)) {
			return false;
		}
		if ( ! setMethod.getReturnType().getName().equals("void")) {
			return false;
		}
		return true;
	}
	
	private static IMethodBinding methodBindingNoParameters(ITypeBinding typeBinding, String methodName) {
		for (IMethodBinding method : typeBinding.getDeclaredMethods()) {
			if (method.getName().equals(methodName)) {
				if (method.getParameterTypes().length == 0) {
					return method;
				}
			}
		}
		return null;
	}
	
	private static IMethodBinding methodBindingOneParameter(ITypeBinding typeBinding, String methodName, String parameterTypeName) {
		for (IMethodBinding method : typeBinding.getDeclaredMethods()) {
			if (method.getName().equals(methodName)) {
				if (method.getParameterTypes().length == 1) {
					if (method.getParameterTypes()[0].getQualifiedName().equals(parameterTypeName)) {
						return method;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Return whether the specified method's modifiers prevent it
	 * from being a getter or setter for a "persistent" property.
	 */
	private static boolean methodHasBadModifiers(IMethodBinding methodBinding) {
		if (methodBinding.isConstructor()) {
			return true;
		}
		int modifiers = methodBinding.getModifiers();
		if (Modifier.isStatic(modifiers)) {
			return true;
		}
		if (Modifier.isFinal(modifiers)) {
			return true;
		}
		if ( ! (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers))) {
			return true;
		}
		return false;
	}
	
	/**
	 * Return whether the type may be "persisted", ie marked as Entity, MappedSuperclass, Embeddable
	 */
	//TODO should persistability be dependent on having a no-arg constructor or should that just be validation?
	//seems like final, or a member type being static could be dealt with through validation instead of filtering them out.
	public static boolean typeIsPersistable(ITypeBinding typeBinding) {
		if (typeBinding.isInterface()) {
			return false;
		}
		if (typeBinding.isAnnotation()) {
			return false;
		}
		if (typeBinding.isEnum()) {
			return false;
		}
		if (typeBinding.isLocal()) {
			return false;
		}
		if (typeBinding.isAnonymous()) {
			return false;
		}
		int modifiers = typeBinding.getModifiers();
		if (Modifier.isFinal(modifiers)) {
			return false;
		}
		if (typeBinding.isMember()) {
			if (!Modifier.isStatic(modifiers)) {
				return false;
			}
		}
		return true;
	}

	public static boolean typeIsAbstract(ITypeBinding typeBinding) {
		return Modifier.isAbstract(typeBinding.getModifiers());	
	}

}
