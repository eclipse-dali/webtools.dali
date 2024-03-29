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

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Reference an {@link IContentType Eclipse content type}.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface ContentTypeReference {
	/**
	 * Return an Eclipse content type.
	 */
	IContentType getContentType();

	Transformer<ContentTypeReference, IContentType> TRANSFORMER = new ContentTypeTransformer();
	class ContentTypeTransformer
		extends TransformerAdapter<ContentTypeReference, IContentType>
	{
		@Override
		public IContentType transform(ContentTypeReference ref) {
			return ref.getContentType();
		}
	}

	class ContentTypeIsKindOf
		extends CriterionPredicate<ContentTypeReference, IContentType>
	{
		public ContentTypeIsKindOf(IContentType contentType) {
			super(contentType);
		}
		public boolean evaluate(ContentTypeReference ref) {
			return ref.getContentType().isKindOf(this.criterion);
		}
	}

	class ContentTypeIs
		extends CriterionPredicate<ContentTypeReference, IContentType>
	{
		public ContentTypeIs(IContentType contentType) {
			super(contentType);
		}
		public boolean evaluate(ContentTypeReference ref) {
			return ref.getContentType().equals(this.criterion);
		}
	}
}
