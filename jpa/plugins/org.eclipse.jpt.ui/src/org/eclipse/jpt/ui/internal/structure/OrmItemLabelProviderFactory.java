/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.ui.internal.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.jface.ItemLabelProvider;
import org.eclipse.jpt.ui.internal.platform.generic.EntityMappingsItemLabelProvider;

public class OrmItemLabelProviderFactory extends GeneralJpaMappingItemLabelProviderFactory
{
	public ItemLabelProvider buildItemLabelProvider(
			Object item, DelegatingContentAndLabelProvider labelProvider) {
		if (item instanceof EntityMappings) {
			return new EntityMappingsItemLabelProvider((EntityMappings) item, labelProvider);
		}
		else return super.buildItemLabelProvider(item, labelProvider);
	}
}
