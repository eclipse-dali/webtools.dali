/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;

/**
 * EclipseLink mutable mapping.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.1
 */
public interface EclipseLinkMutableMapping
	extends AttributeMapping
{
	EclipseLinkMutable getMutable();
	Transformer<EclipseLinkMutableMapping, EclipseLinkMutable> MUTABLE_TRANSFORMER = new MutableTransformer();
	class MutableTransformer
		extends TransformerAdapter<EclipseLinkMutableMapping, EclipseLinkMutable>
	{
		@Override
		public EclipseLinkMutable transform(EclipseLinkMutableMapping model) {
			return model.getMutable();
		}
	}
}
