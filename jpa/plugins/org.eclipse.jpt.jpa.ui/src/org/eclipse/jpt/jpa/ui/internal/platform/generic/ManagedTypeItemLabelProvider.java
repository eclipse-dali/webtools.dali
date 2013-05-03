/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.internal.jface.AbstractItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ManagedType;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;

public class ManagedTypeItemLabelProvider
	extends AbstractItemExtendedLabelProvider<ManagedType>
{
	public ManagedTypeItemLabelProvider(ManagedType managedType, ItemExtendedLabelProvider.Manager manager) {
		super(managedType, manager);
	}


	// ********** image **********

	@Override
	protected ImageDescriptor getImageDescriptor() {
		return JptJpaUiImages.CONVERTER;
	}


	// ********** text **********

	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new TextModel(this.item);
	}

	protected static class TextModel
		extends PropertyAspectAdapter<ManagedType, String>
	{
		public TextModel(ManagedType subject) {
			super(ManagedType.NAME_PROPERTY, subject);
		}
		@Override
		protected String buildValue_() {
			return this.subject.getSimpleName();
		}
	}


	// ********** description **********

	@Override
	@SuppressWarnings("unchecked")
	protected PropertyValueModel<String> buildDescriptionModel() {
		return PersistenceUnitItemLabelProvider.buildNonQuotedComponentDescriptionModel(
					this.item,
					this.textModel
				);
	}
}
