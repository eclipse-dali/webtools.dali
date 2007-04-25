/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

import java.util.ArrayList;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class AttributeAnnotationTools {

	/**
	 * Return the fields in the specified type that may be "persisted".
	 */
	public static IField[] persistableFields(IType type) {
		try {
			return persistableFields_(type);
		} catch(JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static IField[] persistableFields_(IType type) throws JavaModelException {
		ArrayList<IField> persistableFields = new ArrayList<IField>();
		for (IField field : type.getFields()) {
			if (fieldIsPersistable(field)) {
				persistableFields.add(field);
			}
		}
		return persistableFields.toArray(new IField[persistableFields.size()]);
	}

	/**
	 * Return whether the specified field may be "persisted".
	 */
	public static boolean fieldIsPersistable(IField field) {
		try {
			return fieldIsPersistable_(field);
		} catch(JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static boolean fieldIsPersistable_(IField field) throws JavaModelException {
		int flags = field.getFlags();
		if (Flags.isStatic(flags)) {
			return false;
		}
		if (Flags.isPublic(flags)) {
			return false;
		}
		if (Flags.isTransient(flags)) {
			return false;
		}
		if (Flags.isFinal(flags)) {
			return false;
		}
		return true;
	}

	/**
	 * Return the "getter" methods in the specified type that
	 * represent properties that may be "persisted".
	 */
	public static IMethod[] persistablePropertyGetters(IType type) {
		try {
			return persistablePropertyGetters_(type);
		} catch(JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static IMethod[] persistablePropertyGetters_(IType type) throws JavaModelException {
		ArrayList<IMethod> persistableMethods = new ArrayList<IMethod>();
		for (IMethod method : type.getMethods()) {
			if (methodIsPersistablePropertyGetter(method)) {
				persistableMethods.add(method);
			}
		}
		return persistableMethods.toArray(new IMethod[persistableMethods.size()]);
	}

	/**
	 * Return whether the specified method is a "getter" method that
	 * represents a property that may be "persisted".
	 */
	public static boolean methodIsPersistablePropertyGetter(IMethod method) {
		try {
			return methodIsPersistablePropertyGetter_(method);
		} catch(JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static boolean methodIsPersistablePropertyGetter_(IMethod method) throws JavaModelException {
		if (methodHasBadModifiers(method)) {
			return false;
		}

		// TODO need to "resolve" return type
		String returnType = method.getReturnType();
		if (returnType.equals("V")) {		// 'void'
			return false;
		}
		if (method.getNumberOfParameters() != 0) {
			return false;
		}

		String methodName = method.getElementName();
		int beginIndex = 0;
		boolean booleanGetter = false;
		if (methodName.startsWith("is")) {
			if (returnType.equals("Z")) {	// 'boolean'
				beginIndex = 2;
			} else {
				return false;
			}
		} else {
			if (methodName.startsWith("get")) {
				beginIndex = 3;
				if (returnType.equals("Z")) {	// 'boolean'
					booleanGetter = true;
				}
			} else {
				return false;
			}
		}

		String capitalizedAttributeName = method.getElementName().substring(beginIndex);
		// if the type has both methods:
		//     boolean isProperty()
		//     boolean getProperty()
		// then #isProperty() takes precedence and we ignore #getProperty()
		// (see the JavaBeans spec 1.01)
		if (booleanGetter) {
			IMethod isMethod = method.getDeclaringType().getMethod("is" + capitalizedAttributeName, new String[0]);
			if (isMethod.exists() && isMethod.getReturnType().equals("Z")) {		// 'boolean'
				return false;
			}
		}
		IMethod setMethod = method.getDeclaringType().getMethod("set" + capitalizedAttributeName, new String[] {returnType});
		if ( ! setMethod.exists()) {
			return false;
		}
		if (methodHasBadModifiers(setMethod)) {
			return false;
		}
		if ( ! setMethod.getReturnType().equals("V")) {		// 'void'
			return false;
		}
		return true;
	}
	
	/**
	 * Return whether the specified method's modifiers prevent it
	 * from being a getter or setter for a "persistent" property.
	 */
	private static boolean methodHasBadModifiers(IMethod method) throws JavaModelException {
		if (method.isConstructor()) {
			return true;
		}
		int flags = method.getFlags();
		if (Flags.isStatic(flags)) {
			return true;
		}
		if (Flags.isFinal(flags)) {
			return true;
		}
		if ( ! (Flags.isPublic(flags) || Flags.isProtected(flags))) {
			return true;
		}
		return false;
	}
	
	/**
	 * Return whether the type may be "persisted", ie marked as Entity, MappedSuperclass, Embeddable
	 */
	public static boolean typeIsPersistable(IType type) {
		try {
			return typeIsPersistable_(type);
		} catch(JavaModelException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private static boolean typeIsPersistable_(IType type) throws JavaModelException {
		if (type.isInterface()) {
			return false;
		}
		if (type.isAnnotation()) {
			return false;
		}
		if (type.isEnum()) {
			return false;
		}
		if (type.isLocal()) {
			return false;
		}
		if (type.isAnonymous()) {
			return false;
		}
		int flags = type.getFlags();
		if (Flags.isFinal(flags)) {
			return false;
		}
		return true;
	}

}
