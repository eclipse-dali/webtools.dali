/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.java;

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaCollectionMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumn;
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
 * @version 3.1
 * @since 2.3
 */
public interface JavaCollectionMapping2_0
	extends CollectionMapping2_0, JavaCollectionMapping, JavaConvertibleKeyMapping2_0
{
	JavaAttributeOverrideContainer getMapKeyAttributeOverrideContainer();

	/**
	 * If the map key class is specified, this will return it fully qualified.
	 * If not specified, it returns the default map key class, which is always
	 * fully qualified.
	 */
	String getFullyQualifiedMapKeyClass();
		String FULLY_QUALIFIED_MAP_KEY_CLASS_PROPERTY = "fullyQualifiedMapKeyClass"; //$NON-NLS-1$
	
	ListIterable<? extends JavaJoinColumn> getSpecifiedMapKeyJoinColumns();
	JavaJoinColumn getSpecifiedMapKeyJoinColumn(int index);
	JavaJoinColumn addSpecifiedMapKeyJoinColumn();
	JavaJoinColumn addSpecifiedMapKeyJoinColumn(int index);

	JavaJoinColumn getDefaultMapKeyJoinColumn();
}