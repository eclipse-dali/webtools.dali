/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core;

import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * An object with a reference to a resource type.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see JptResourceType
 * @version 3.3
 * @since 3.3
 */
public interface JptResourceTypeReference {
	/**
	 * Return the object's resource type.
	 */
	JptResourceType getResourceType();


	Transformer<JptResourceTypeReference, JptResourceType> TRANSFORMER = new ResourceTypeTransformer();
	class ResourceTypeTransformer
		extends TransformerAdapter<JptResourceTypeReference, JptResourceType>
	{
		@Override
		public JptResourceType transform(JptResourceTypeReference ref) {
			return ref.getResourceType();
		}
	}

	/**
	 * A predicate that returns whether a reference's resource type is a
	 * "kind of" the configured resource type.
	 * @see JptResourceType#isKindOf(JptResourceType)
	 */
	class ResourceTypeIsKindOf
		extends CriterionPredicate<JptResourceTypeReference, JptResourceType>
	{
		public ResourceTypeIsKindOf(JptResourceType resourceType) {
			super(resourceType);
			if (resourceType == null) {
				throw new NullPointerException();
			}
		}
		public boolean evaluate(JptResourceTypeReference ref) {
			return ref.getResourceType().isKindOf(this.criterion);
		}
	}
}
