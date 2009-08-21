/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.utility.jdt;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

/**
 * Convenience methods for JPA-related queries concerning JDT objects.
 */
public class JPTTools {

	// ********** type **********

	/**
	 * Return whether the specified type can be "persisted", i.e. marked as
	 * Entity, MappedSuperclass, Embeddable
	 */
	// TODO check for no-arg constructor (or should that just be validation?)
	// TODO move other checks to validation (e.g. 'final', 'static')?
	public static boolean typeIsPersistable(TypeAdapter typeAdapter) {
		if (typeAdapter.isInterface()) {
			return false;
		}
		if (typeAdapter.isAnnotation()) {
			return false;
		}
		if (typeAdapter.isEnum()) {
			return false;
		}
		if (typeAdapter.isLocal()) {
			return false;
		}
		if (typeAdapter.isAnonymous()) {
			return false;
		}
		if (typeAdapter.isPrimitive()) {
			return false;  // should never get here(?)
		}
		if (typeAdapter.isArray()) {
			return false;  // should never get here(?)
		}
		int modifiers = typeAdapter.getModifiers();
		if (Modifier.isFinal(modifiers)) {
			return false;
		}
		if (typeAdapter.isMember()) {
			if ( ! Modifier.isStatic(modifiers)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Queries needed to calculate whether a type is "persistable".
	 * Adapted to ITypeBinding and IType.
	 */
	public interface TypeAdapter {
		int getModifiers();
		boolean isAnnotation();
		boolean isAnonymous();
		boolean isArray();
		boolean isEnum();
		boolean isInterface();
		boolean isLocal();
		boolean isMember();
		boolean isPrimitive();
	}


	// ********** field **********

	/**
	 * Return whether the specified field may be "persisted".
	 * According to the spec, "All non-transient instance variables that are not 
	 * annotated with the Transient annotation are persistent."
	 */
	public static boolean fieldIsPersistable(FieldAdapter fieldAdapter) {
		int modifiers = fieldAdapter.getModifiers();
		if (Modifier.isStatic(modifiers)) {
			return false;
		}
		if (Modifier.isTransient(modifiers)) {
			return false;
		}
		return true;
	}

	/**
	 * Queries needed to calculate whether a field is "persistable".
	 * Adapted to IVariableBinding and IField.
	 */
	public interface FieldAdapter {
		/**
		 * Return the field's modifiers. We use these to check whether the
		 * field is static or transient.
		 */
		int getModifiers();
	}


	// ********** method **********

	/**
	 * Return whether the specified method is a "getter" method that
	 * represents a property that may be "persisted".
	 */
	public static boolean methodIsPersistablePropertyGetter(MethodAdapter methodAdapter) {
		if (methodHasInvalidModifiers(methodAdapter)) {
			return false;
		}
		if (methodAdapter.isConstructor()) {
			return false;
		}

		String returnTypeName = methodAdapter.getReturnTypeErasureName();
		if (returnTypeName == null) {
			return false;  // DOM method bindings can have a null name
		}
		if (returnTypeName.equals("void")) { //$NON-NLS-1$
			return false;
		}
		if (methodHasParameters(methodAdapter)) {
			return false;
		}

		String name = methodAdapter.getName();
		int beginIndex = 0;
		boolean booleanGetter = false;
		if (name.startsWith("is")) { //$NON-NLS-1$
			if (returnTypeName.equals("boolean")) { //$NON-NLS-1$
				beginIndex = 2;
			} else {
				return false;
			}
		} else if (name.startsWith("get")) { //$NON-NLS-1$
			beginIndex = 3;
			if (returnTypeName.equals("boolean")) { //$NON-NLS-1$
				booleanGetter = true;
			}
		} else {
			return false;
		}

		String capitalizedAttributeName = name.substring(beginIndex);
		// if the type has both methods:
		//     boolean isProperty()
		//     boolean getProperty()
		// then #isProperty() takes precedence and we ignore #getProperty();
		// but only having #getProperty() is OK too
		// (see the JavaBeans spec 1.01)
		if (booleanGetter && methodHasValidSiblingIsMethod(methodAdapter, capitalizedAttributeName)) {
			return false;  // since the type also defines #isProperty(), ignore #getProperty()
		}
		return methodHasValidSiblingSetMethod(methodAdapter, capitalizedAttributeName, returnTypeName);
	}

	/**
	 * Return whether the method's modifiers prevent it
	 * from being a getter or setter for a "persistent" property.
	 */
	private static boolean methodHasInvalidModifiers(SimpleMethodAdapter methodAdapter) {
		int modifiers = methodAdapter.getModifiers();
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

	private static boolean methodHasParameters(MethodAdapter methodAdapter) {
		return methodAdapter.getParametersLength() != 0;
	}

	/**
	 * Return whether the method has a sibling "is" method for the specified
	 * property and that method is valid for a "persistable" property.
	 * Pre-condition: the method is a "boolean getter" (e.g. 'public boolean getProperty()');
	 * this prevents us from returning true when the method itself is an
	 * "is" method.
	 */
	private static boolean methodHasValidSiblingIsMethod(MethodAdapter methodAdapter, String capitalizedAttributeName) {
		SimpleMethodAdapter isMethodAdapter = methodAdapter.getSibling("is" + capitalizedAttributeName); //$NON-NLS-1$
		return methodIsValidSibling(isMethodAdapter, "boolean"); //$NON-NLS-1$
	}

	/**
	 * Return whether the method has a sibling "set" method
	 * and that method is valid for a "persistable" property.
	 */
	private static boolean methodHasValidSiblingSetMethod(MethodAdapter methodAdapter, String capitalizedAttributeName, String parameterTypeErasureName) {
		SimpleMethodAdapter setMethodAdapter = methodAdapter.getSibling("set" + capitalizedAttributeName, parameterTypeErasureName); //$NON-NLS-1$
		return methodIsValidSibling(setMethodAdapter, "void"); //$NON-NLS-1$
	}

	/**
	 * Return whether the specified method is a valid sibling with the
	 * specified return type.
	 */
	private static boolean methodIsValidSibling(SimpleMethodAdapter methodAdapter, String returnTypeName) {
		if (methodAdapter == null) {
			return false;
		}
		if (methodHasInvalidModifiers(methodAdapter)) {
			return false;
		}
		if (methodAdapter.isConstructor()) {
			return false;
		}
		String rtName = methodAdapter.getReturnTypeErasureName();
		if (rtName == null) {
			return false;  // DOM method bindings can have a null name
		}
		return rtName.equals(returnTypeName);
	}

	/**
	 * Queries needed to calculate whether a method is "persistable".
	 * Adapted to IMethodBinding and IMethod.
	 */
	public interface SimpleMethodAdapter {
		/**
		 * Return the method's modifiers.
		 * We use these to check whether the method is static, final, etc.
		 */
		int getModifiers();

		/**
		 * Return the name of the method's return type erasure.
		 * We use this to check for
		 *   - boolean getters
		 *   - void return types
		 *   - matching getters and setters
		 */
		String getReturnTypeErasureName();

		/**
		 * Return whether the method is a constructor.
		 */
		boolean isConstructor();
	}

	/**
	 * Queries needed to calculate whether a method is "persistable".
	 * Adapted to IMethodBinding and IMethod.
	 */
	public interface MethodAdapter extends SimpleMethodAdapter {
		/**
		 * Return the method's name.
		 * We use this to determine
		 *   - whether the method is a "getter"
		 *   - the property name implied by the getter's name
		 */
		String getName();

		/**
		 * Return the number of paramters declared by the method.
		 * We use this to determine whether the method is a "getter".
		 */
		int getParametersLength();

		/**
		 * Return the method's "sibling" with the specified name and no parameters.
		 * We use this to find an "is" boolean getter that would take precedence
		 * over a "get" boolean getter.
		 */
		SimpleMethodAdapter getSibling(String name);

		/**
		 * Return the method's "sibling" with the specified name and single parameter.
		 * We use this to find a matching "setter" for a possible "getter".
		 */
		SimpleMethodAdapter getSibling(String name, String parameterTypeErasureName);
	}


	// ********** Access type **********

	/**
	 * Return the AccessType currently implied by the Java source code
	 * or class file:
	 *     - if only Fields are annotated => FIELD
	 *     - if only Properties are annotated => PROPERTY
	 *     - if both Fields and Properties are annotated => FIELD
	 *     - if nothing is annotated
	 *     		- and fields exist => FIELD
	 *     		- and properties exist, but no fields exist => PROPERTY
	 *     		- and neither fields nor properties exist => null at this level (FIELD in the context model)
	 */
	public static AccessType buildAccess(JavaResourcePersistentType jrpType) {
		boolean hasPersistableFields = false;
		for (Iterator<JavaResourcePersistentAttribute> stream = jrpType.persistableFields(); stream.hasNext(); ) {
			hasPersistableFields = true;
			if (stream.next().isAnnotated()) {
				// any field is annotated => FIELD
				return AccessType.FIELD;
			}
		}

		boolean hasPersistableProperties = false;
		for (Iterator<JavaResourcePersistentAttribute> stream = jrpType.persistableProperties(); stream.hasNext(); ) {
			hasPersistableProperties = true;
			if (stream.next().isAnnotated()) {
				// none of the fields are annotated and a getter is annotated => PROPERTY
				return AccessType.PROPERTY;
			}
		}

		if (hasPersistableProperties && ! hasPersistableFields) {
			return AccessType.PROPERTY;
		}

		// if no annotations exist, access is null at the resource model level
		return null;
	}


	// ********** suppressed constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private JPTTools() {
		super();
		throw new UnsupportedOperationException();
	}

}
