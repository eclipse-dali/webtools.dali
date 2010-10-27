/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;


/**
 * Java package fragment
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.2
 */
public interface JavaResourcePackageFragment
	extends JavaResourceNode
{
	/**
	 * Return the package fragment's class files that contain "persistable" types.
	 */
	Iterable<JavaResourceClassFile> getClassFiles();
		String CLASS_FILES_COLLECTION = "classFiles"; //$NON-NLS-1$

	/**
	 * Return the size of the package fragment's class files.
	 */
	int getClassFilesSize();

	/**
	 * Return the package fragment's Java persistent types.
	 * Return only the files that are annotated with JAXB annotations.
	 */
	Iterable<JavaResourceType> getPersistedTypes();

}
