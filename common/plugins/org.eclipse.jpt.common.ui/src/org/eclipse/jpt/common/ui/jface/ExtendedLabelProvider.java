/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.jface;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.navigator.IDescriptionProvider;

/**
 * Combine provider interfaces to be implemented by a single provider that can
 * be used as both a label and description provider for a tree.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ExtendedLabelProvider
	extends ILabelProvider, IDescriptionProvider
{
	// combine interfaces
}
