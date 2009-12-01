/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.context;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.jpa2.MetamodelSynchronizer;

/**
 * JPA 2.0 context persistent type.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface PersistentType2_0
	extends PersistentType, MetamodelSynchronizer
{
	/**
	 * Return the file generated as a result of the metamodel synchronization.
	 */
	IFile getMetamodelFile();

	/**
	 * This interface is used by the persistent type to synchonize the metamodel
	 * as required by changes to the context model.
	 */
	interface MetamodelSynchronizer {
		/**
		 * Return the file generated as a result of the metamodel synchronization.
		 */
		IFile getFile();

		/**
		 * Synchronize the metamodel with the current state of the persistent
		 * type.
		 */
		void synchronize();
	}

}
