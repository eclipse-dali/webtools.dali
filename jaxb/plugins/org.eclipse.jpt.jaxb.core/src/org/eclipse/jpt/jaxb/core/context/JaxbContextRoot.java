/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import java.util.List;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Root of the JAXB context model.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface JaxbContextRoot
		extends JaxbContextNode {
	
	/**
	 * The set of packages.  Includes any package with any interesting JAXB content.
	 */
	Iterable<JaxbPackage> getPackages();
	public final static String PACKAGES_COLLECTION = "packages"; //$NON-NLS-1$
	
	int getPackagesSize();

	/**
	 * Return the package with the given name
	 */
	JaxbPackage getPackage(String packageName);
	
	/**
	 * Return the set of all JAXB types within this context root.
	 */
	Iterable<JaxbType> getTypes();
	public final static String TYPES_COLLECTION = "types"; //$NON-NLS-1$
	
	int getTypesSize();
	
	/**
	 * Return the set of types that are in the given package
	 */
	Iterable<JaxbType> getTypes(JaxbPackage jaxbPackage);
	
	/**
	 * The set of persistent classes.  These may be explicitly or implicitly included.
	 */
	Iterable<JaxbPersistentClass> getPersistentClasses();
	
	/**
	 * Return the set of persistent classes that are in the given package
	 */
	Iterable<JaxbPersistentClass> getPersistentClasses(JaxbPackage jaxbPackage);

	/**
	 * Return the persistent class with the given fully qualified name
	 */
	JaxbPersistentClass getPersistentClass(String fullyQualifiedTypeName);

	/**
	 * The set of persistent enums.  These may be explicitly or implicitly included.
	 */
	Iterable<JaxbPersistentEnum> getPersistentEnums();

	/**
	 * Return the set of persistent enums that are in the given package
	 */
	Iterable<JaxbPersistentEnum> getPersistentEnums(JaxbPackage jaxbPackage);

	/**
	 * Return the persistent enum with the given fully qualified name
	 */
	JaxbPersistentEnum getPersistentEnum(String fullyQualifiedTypeName);

	/**
	 * The set of registries.
	 */
	Iterable<JaxbRegistry> getRegistries();
	
	/**
	 * Return the set of registries that are in the given package
	 * (There should typically be a max of 1, but there are invalid states ...)
	 */
	Iterable<JaxbRegistry> getRegistries(JaxbPackage jaxbPackage);
	
	
	// **************** validation ********************************************
	
	/**
	 * Add validation messages to the specified list.
	 */
	public void validate(List<IMessage> messages, IReporter reporter);
}
