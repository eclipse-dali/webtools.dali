/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.PersistentType;

/**
 * JPA 2.0 context persistent type.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface PersistentType2_0
	extends PersistentType, MetamodelSourceType2_0
{
	/**
	 * Return the name of the persistent type's "declaring type".
	 * Return <code>null</code> if the persistent type is a top-level type.
	 * The declaring type may or may not be a persistent type.
	 */
	String getDeclaringTypeName();
		String DECLARING_TYPE_NAME_PROPERTY = "declaringTypeName"; //$NON-NLS-1$

	/**
	 * Return the persistent type to be used for static metamodel generation,
	 * typically the persistent type itself. Return <code>null</code> if the
	 * persistent type is not to be used for static metamodel generation
	 * (e.g. the type does not have a corresponding Java source declaration
	 * and the resulting metamodel classes would not compile).
	 */
	PersistentType2_0 getMetamodelType();
	Transformer<PersistentType2_0, PersistentType2_0> METAMODEL_TYPE_TRANSFORMER = new MetamodelTypeTransformer();
	class MetamodelTypeTransformer
		extends TransformerAdapter<PersistentType2_0, PersistentType2_0>
	{
		@Override
		public PersistentType2_0 transform(PersistentType2_0 pt) {
			return pt.getMetamodelType();
		}
	}
}
