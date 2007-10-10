/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.Iterator;

import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.utility.internal.node.NodeModel;

public interface IJpaNodeModel extends NodeModel {

	/**
	 * Return the resource that most directly contains the object.
	 * This is used by JpaHelper.
	 */
	IResource resource();

	/**
	 * Return the JPA project the object belongs to.
	 */
	IJpaProject jpaProject();


	// ********** covariant overrides **********

	IJpaNodeModel parent();

	Iterator<? extends IJpaNodeModel> children();

	IJpaProject root();

}
