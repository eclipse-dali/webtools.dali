/*******************************************************************************
 * Copyright (c) 2010, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import java.lang.reflect.Modifier;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jpt.common.utility.MethodSignature;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Java source code or binary method
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.0
 */
public interface JavaResourceMethod
	extends JavaResourceAttribute
{
	// ***** method name *****

	/**
	 * The Java resource method's name does not change.
	 */
	String getMethodName();
	
	
	// ***** parameter type names *****
	
	String PARAMETER_TYPE_NAMES_LIST = "parameterTypeNames"; //$NON-NLS-1$
	
	ListIterable<String> getParameterTypeNames();
		
	String getParameterTypeName(int index);
	
	int getParametersSize();
	
	
	// ***** constructor *****
	
	String CONSTRUCTOR_PROPERTY = "constructor"; //$NON-NLS-1$
	
	boolean isConstructor();
	Predicate<JavaResourceMethod> CONSTRUCTOR_PREDICATE = new ConstructorPredicate();
	class ConstructorPredicate
		extends PredicateAdapter<JavaResourceMethod>
	{
		@Override
		public boolean evaluate(JavaResourceMethod method) {
			return method.isConstructor();
		}
	}
	
	// ***** misc *****
	
	/**
	 * Return whether the Java resource persistent attribute is for the specified
	 * method.
	 */
	boolean isFor(MethodSignature methodSignature, int occurrence);

	/**
	 * Synchronize the [source] method with the specified AST MethodDeclaration.
	 */
	void synchronizeWith(MethodDeclaration methodDeclaration);

	/**
	 * Return whether the method is a standard property "getter" method.
	 */
	Predicate<JavaResourceMethod> PROPERTY_GETTER_PREDICATE = new PropertyGetterPredicate();
	@SuppressWarnings("nls")
	class PropertyGetterPredicate
		extends PredicateAdapter<JavaResourceMethod>
	{
		@Override
		public boolean evaluate(JavaResourceMethod method) {
			String methodName = method.getMethodName();
			if ((methodName.length() <= 3) && ! methodName.startsWith("is")) {
				return false;
			}
			String returnType = this.getInstanceMethodReturnType(method);
			if (returnType == null) {
				return false;
			}
			if (method.getParametersSize() != 0) {
				return false;
			}

			if (methodName.startsWith("is")) {
				return returnType.equals("boolean");  // first-priority boolean getter
			}
			if ( ! methodName.startsWith("get")) {
				return false;
			}
			if ( ! returnType.equals("boolean")) {
				return true;  // simple non-boolean getter
			}
			// if the type has both methods:
			//     boolean isProperty() {...}
			//     boolean getProperty() {...}
			// then #isProperty() takes precedence and we ignore #getProperty();
			// but only having 'getProperty()' is OK too (see the JavaBeans spec 1.01)
			String isMethodName = "is" + methodName.substring(3);
			for (JavaResourceMethod sibling : method.getResourceType().getMethods()) {
				if (sibling.getMethodName().equals(isMethodName) && (sibling.getParametersSize() == 0)) {
					// 'isProperty()' exists - if its return type is boolean, 'getProperty()' is *not* a getter
					return ObjectTools.notEquals(this.getInstanceMethodReturnType(sibling), "boolean");
				}
			}
			return true;  // 'isProperty()' does not exist
		}

		private String getInstanceMethodReturnType(JavaResourceMethod method) {
			if (Modifier.isStatic(method.getModifiers())) {
				return null;
			}
			if (method.isConstructor()) {
				return null;
			}
			String returnType = method.getTypeBinding().getQualifiedName();
			if (returnType == null) {
				return null;  // DOM method bindings can have a null name...
			}
			if (returnType.equals("void")) {
				return null;
			}
			return returnType;
		}
	}

	/**
	 * Transform a "get"/"is" method into its corresponding "set" method,
	 * if present.
	 */
	Transformer<JavaResourceMethod, JavaResourceMethod> SET_METHOD_TRANSFORMER = new SetMethodTransformer();
	@SuppressWarnings("nls")
	class SetMethodTransformer
		extends TransformerAdapter<JavaResourceMethod, JavaResourceMethod>
	{
		@Override
		public JavaResourceMethod transform(JavaResourceMethod getMethod) {
			String getMethodName = getMethod.getMethodName();
			String setMethodName = "set" + getMethodName.substring(this.calculateNameIndex(getMethodName)); //$NON-NLS-1$
			String propertyTypeName = getMethod.getTypeBinding().getQualifiedName();
			for (JavaResourceMethod sibling : getMethod.getResourceType().getMethods()) {
				if (sibling.getMethodName().equals(setMethodName) && (sibling.getParametersSize() == 1) && sibling.getParameterTypeName(0).equals(propertyTypeName)) {
					return this.methodIsValidGetterSibling(sibling) ? sibling : null;
				}
			}
			return null;
		}

		private int calculateNameIndex(String getMethodName) {
			int len = getMethodName.length();
			// as some methods may actually be named "get" or "is", allow for that possibility
			if (getMethodName.startsWith("get") && (len >= 3)) {
				return 3;
			}
			if (getMethodName.startsWith("is") && (len >= 2)) {
				return 2;
			}
			throw new IllegalArgumentException(getMethodName);
		}

		private boolean methodIsValidGetterSibling(JavaResourceMethod method) {
			return ! method.isConstructor() &&
					! Modifier.isStatic(method.getModifiers()) &&
					ObjectTools.equals(method.getTypeBinding().getQualifiedName(), "void"); //$NON-NLS-1$
		}
	}
}
