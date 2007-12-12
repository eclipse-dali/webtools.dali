/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.Iterator;

import org.eclipse.jpt.utility.internal.model.Model;

/**
 * Interface used to abstract tree accessing and
 * change notification and make it more pluggable.
 */
public interface TreeValueModel
	extends Model
{
	/**
	 * Return the tree's nodes.
	 */
	Iterator nodes();
		String NODES = "nodes";

}
