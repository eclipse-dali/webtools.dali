/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.JptResourceTypeReference;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;

/**
 * A transformer that transforms a resource type reference into a flag
 * indicating whether the reference's resource type is a "kind of" the
 * configured resource type.
 * @see JptResourceType#isKindOf(JptResourceType)
 */
public class ResourceTypeIsKindOf<R extends JptResourceTypeReference>
	extends AbstractTransformer<R, Boolean>
{
	private final JptResourceType resourceType;

	public ResourceTypeIsKindOf(JptResourceType resourceType) {
		super();
		if (resourceType == null) {
			throw new NullPointerException();
		}
		this.resourceType = resourceType;
	}

	@Override
	protected Boolean transform_(R ref) {
		return Boolean.valueOf(ref.getResourceType().isKindOf(this.resourceType));
	}
}
