/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.ui.internal.platform.generic.PersistentAttributeItemLabelProvider;
import org.eclipse.jpt.ui.internal.platform.generic.PersistentTypeItemLabelProvider;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.ui.jface.ItemLabelProviderFactory;

public abstract class GeneralJpaMappingItemLabelProviderFactory
	implements ItemLabelProviderFactory
{
	public ItemLabelProvider buildItemLabelProvider(
			Object item, DelegatingContentAndLabelProvider labelProvider) {
		if (item instanceof PersistentType) {
			return new PersistentTypeItemLabelProvider((PersistentType) item, labelProvider);
		}
		else if (item instanceof PersistentAttribute) {
			return new PersistentAttributeItemLabelProvider((PersistentAttribute) item, labelProvider);
		}
		return null;
	}
}
