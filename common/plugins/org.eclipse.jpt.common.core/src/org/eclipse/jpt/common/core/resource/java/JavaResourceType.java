/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;


/**
 * Java source code or binary class or interface.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.0
 */
public interface JavaResourceType
		extends JavaResourceAbstractType {

	/**
	 * Synchronize the [source] type with the specified AST TypeDeclaration.
	 */
	void synchronizeWith(TypeDeclaration typeDeclaration);

	/**
	 * Resolve type information that could be dependent on changes elsewhere
	 * in the workspace.
	 */
	void resolveTypes(TypeDeclaration typeDeclaration);

	/**
	 * Return the fully qualified name of the type's superclass.
	 */
	String getSuperclassQualifiedName();
		String SUPERCLASS_QUALIFIED_NAME_PROPERTY = "superclassQualifiedName"; //$NON-NLS-1$

	/**
	 * Return whether the type is abstract.
	 */
	boolean isAbstract();
		String ABSTRACT_PROPERTY = "abstract"; //$NON-NLS-1$
	
	/**
	 * Return whether the type has a no-arg constructor (private, protected, or public)
	 */
	boolean hasNoArgConstructor();
		String NO_ARG_CONSTRUCTOR_PROPERTY = "noArgConstructor"; //$NON-NLS-1$

	/**
	 * Return whether the type has a private no-arg constructor
	 */
	boolean hasPrivateNoArgConstructor();
		String PRIVATE_NO_ARG_CONSTRUCTOR_PROPERTY = "privateNoArgConstructor"; //$NON-NLS-1$
	
	/**
	 * Return whether the type has a public or protected no-arg constructor
	 * <em>or</em> only the default constructor.
	 */
	boolean hasPublicOrProtectedNoArgConstructor();

	/**
	 * Return whether the type has a public no-arg constructor
	 * <em>or</em> only the default constructor.
	 */
	boolean hasPublicNoArgConstructor();
		
	/**
	 * Return whether the type has any field that have relevant annotations
	 * on them (which can be used to infer the type's access type).
	 */
	boolean hasAnyAnnotatedFields();

	/**
	 * Return whether the type has any field that have relevant annotations
	 * on them (which can be used to infer the type's access type).
	 */
	boolean hasAnyAnnotatedMethods();

	/**
	 * Return whether the type overrides the
	 * {@link Object#equals(Object)} method.
	 */
	boolean hasEqualsMethod();
	
	/**
	 * Return whether the type overrides the
	 * {@link Object#hashCode()} method.
	 */
	boolean hasHashCodeMethod();

	// ********** fields **********
	
	/**
	 * Return the type's fields.
	 */
	Iterable<JavaResourceField> getFields();
		String FIELDS_COLLECTION = "fields"; //$NON-NLS-1$
	
	JavaResourceField getField(String name);
	
	// ********** methods **********
	
	/**
	 * Return the type's methods. This returns *all* methods from the JDT Type
	 */
	Iterable<JavaResourceMethod> getMethods();
		String METHODS_COLLECTION = "methods"; //$NON-NLS-1$
	
	JavaResourceMethod getMethod(String name);
	
	
	// ***** misc *****
	
	/**
	 * Return a {@link TypeBinding} for the given attribute, whether that attribute
	 * is owned directly by this type or this type has specific inherited type information
	 * for it (i.e. this type has constrained the generic type of the attribute from a superclass.)
	 * NB: a return value of null does not mean that the attribute does not exist within the scope
	 * of this type.  It simply means that this type has no more information than the superclass
	 * has, and the superclass should be checked for this information.
	 */
	TypeBinding getAttributeTypeBinding(JavaResourceAttribute attribute);
}
