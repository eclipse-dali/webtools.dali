/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import java.util.Comparator;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.internal.comparator.ComparatorAdapter;

/**
 * Named context model. Sorta. :-)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaNamedContextModel
	extends JpaContextModel
{
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$
	void setName(String name);

	TransformerAdapter<JpaNamedContextModel, String> NAME_TRANSFORMER = new NameTransformer();
	class NameTransformer
		extends TransformerAdapter<JpaNamedContextModel, String>
	{
		@Override
		public String transform(JpaNamedContextModel node) {
			return node.getName();
		}
	}

	Comparator<JpaNamedContextModel> NAME_COMPARATOR = new NameComparator();
	class NameComparator
		extends ComparatorAdapter<JpaNamedContextModel>
	{
		@Override
		public int compare(JpaNamedContextModel model1, JpaNamedContextModel model2) {
			return model1.getName().compareTo(model2.getName());
		}
	}
}
