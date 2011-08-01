/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;


/**
 * Java source code or binary class or interface.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.0
 */
public interface JavaResourceType
	extends JavaResourceAbstractType
{

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
	 * Return whether the type has any field that have relevant annotations
	 * on them (which can be used to infer the type's access type).
	 */
	boolean hasAnyAnnotatedFields();

	/**
	 * Return whether the type has any field that have relevant annotations
	 * on them (which can be used to infer the type's access type).
	 */
	boolean hasAnyAnnotatedMethods();

	// ********** fields **********

	/**
	 * Return the type's fields.
	 */
	Iterable<JavaResourceField> getFields();
		String FIELDS_COLLECTION = "fields"; //$NON-NLS-1$


	// ********** methods **********

	/**
	 * Return the type's methods. This returns *all* methods from the JDT Type
	 */
	Iterable<JavaResourceMethod> getMethods();
		String METHODS_COLLECTION = "methods"; //$NON-NLS-1$

}
