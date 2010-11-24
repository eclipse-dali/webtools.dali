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
	 * The set of persistent classes.  These may be explicitly or implicitly included.
	 */
	Iterable<JaxbPersistentClass> getPersistentClasses();
	public final static String PERSISTENT_CLASSES_COLLECTION = "persistentClasses"; //$NON-NLS-1$
	
	int getPersistentClassesSize();
	
	/**
	 * Return the set of persistent classes that are in the given package
	 */
	Iterable<JaxbPersistentClass> getPersistentClasses(JaxbPackage jaxbPackage);
	
	
//	// ********** validation **********
//
//	/**
//	 * Add validation messages to the specified list.
//	 */
//	public void validate(List<IMessage> messages, IReporter reporter);

}
