/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.java;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.jpa2.context.CollectionMapping2_0;

/**
 * Java collection mapping (e.g. 1:m, m:m, element collection)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.3
 */
public interface JavaCollectionMapping2_0
	extends CollectionMapping2_0, JavaConvertibleKeyMapping2_0
{
	JavaAttributeOverrideContainer getMapKeyAttributeOverrideContainer();
	
	ListIterable<? extends JavaSpecifiedJoinColumn> getSpecifiedMapKeyJoinColumns();
	JavaSpecifiedJoinColumn getSpecifiedMapKeyJoinColumn(int index);
	JavaSpecifiedJoinColumn addSpecifiedMapKeyJoinColumn();
	JavaSpecifiedJoinColumn addSpecifiedMapKeyJoinColumn(int index);

	JavaSpecifiedJoinColumn getDefaultMapKeyJoinColumn();
}
