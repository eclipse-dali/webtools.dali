/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.PersistentTypeContainer;

/**
 * A JAR file identified by a <code>persistence.xml</code> <code>jar-file</code> element.
 * This holds persistent types corresponding to all the "persistable" types
 * discovered in the JAR.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.2
 */
public interface JarFile
	extends JpaContextModel, PersistentTypeContainer
{
	JavaResourcePackageFragmentRoot getJarResourcePackageFragmentRoot();


	// ********** Java managed types **********

	/**
	 * Return the JAR file's Java managed types.
	 * Return only the types that are annotated with JPA annotations.
	 */
	Iterable<JavaManagedType> getJavaManagedTypes();
		String JAVA_MANAGED_TYPES_COLLECTION = "javaManagedTypes"; //$NON-NLS-1$

	/**
	 * Return the size of the JAR file's Java managed types.
	 */
	int getJavaManagedTypesSize();


	// ********** misc **********

	/**
	 * Return true if this jar file exists in the given folder
	 */
	boolean isIn(IFolder folder);
}
