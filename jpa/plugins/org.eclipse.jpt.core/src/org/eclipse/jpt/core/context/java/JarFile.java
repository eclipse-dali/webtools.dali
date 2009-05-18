/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * A JAR file identified by a persistence.xml 'jar-file' element.
 * Holds persistent types corresponding to all the "persistable" types
 * discovered in the JAR.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JarFile
	extends JpaContextNode, JpaStructureNode
{
	/**
	 * Return the JAR file's Java persistent types.
	 * Return only the types that are annotated with JPA annotations.
	 */
	Iterator<JavaPersistentType> javaPersistentTypes();
		String JAVA_PERSISTENT_TYPES_COLLECTION = "javaPersistentTypes"; //$NON-NLS-1$

	/**
	 * Return the size of the JAR file's Java persistent types.
	 */
	int javaPersistentTypesSize();

	/**
	 * Return the persistent type with the specified name.
	 * Return null if the persistent type is not found.
	 */
	PersistentType getPersistentType(String typeName);

	/**
	 * Synchronize the context JAR file to the specified Java package fragment
	 * root.
	 * @see org.eclipse.jpt.core.JpaProject#update()
	 */
	void update(JavaResourcePackageFragmentRoot jrpfr);

	/**
	 * Add to the list of current validation messages
	 */
	void validate(List<IMessage> messages, IReporter reporter);

}
