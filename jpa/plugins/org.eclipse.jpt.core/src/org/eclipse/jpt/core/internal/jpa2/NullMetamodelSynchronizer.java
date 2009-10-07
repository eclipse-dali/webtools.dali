/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2;

import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.jpa2.MetamodelSynchronizer;

/**
 * 
 */
public class NullMetamodelSynchronizer
	implements MetamodelSynchronizer
{
	// singleton
	private static final NullMetamodelSynchronizer INSTANCE = new NullMetamodelSynchronizer();

	/**
	 * Return the singleton.
	 */
	public static MetamodelSynchronizer instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private NullMetamodelSynchronizer() {
		super();
	}

	public void synchronize(PersistentType persistentType) {
		// NOP
	}

}
