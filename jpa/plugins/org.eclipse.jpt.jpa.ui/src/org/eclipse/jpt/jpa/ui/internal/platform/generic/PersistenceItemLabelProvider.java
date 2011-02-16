/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemLabelProvider;
import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.swt.graphics.Image;

public class PersistenceItemLabelProvider extends AbstractItemLabelProvider
{
	public PersistenceItemLabelProvider(
			Persistence persistence, DelegatingContentAndLabelProvider labelProvider) {
		super(persistence, labelProvider);
	}
	
	@Override
	public Persistence getModel() {
		return (Persistence) super.getModel();
	}

	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new StaticPropertyValueModel<Image>(JptJpaUiPlugin.getImage(JptUiIcons.PERSISTENCE));
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new StaticPropertyValueModel<String>(
			JptUiMessages.PersistenceItemLabelProviderFactory_persistenceLabel);
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		StringBuilder sb = new StringBuilder();
		sb.append(JptUiMessages.PersistenceItemLabelProviderFactory_persistenceLabel);
		sb.append(" - ");  //$NON-NLS-1$
		sb.append(getModel().getResource().getFullPath().makeRelative());
		return new StaticPropertyValueModel<String>(sb.toString());
	}
}
