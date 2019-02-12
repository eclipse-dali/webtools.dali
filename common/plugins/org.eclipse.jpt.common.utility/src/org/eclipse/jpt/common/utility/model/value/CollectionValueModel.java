/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.model.value;

import java.util.Iterator;
import org.eclipse.jpt.common.utility.model.Model;

/**
 * Interface used to abstract collection accessing and
 * change notification and make it more pluggable.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <E> the type of values held by the model
 */
public interface CollectionValueModel<E>
	extends Model, Iterable<E>
{

	/**
	 * Return the collection's values.
	 */
	Iterator<E> iterator();
		String VALUES = "values"; //$NON-NLS-1$

	/**
	 * Return the size of the collection.
	 */
	int size();

}
