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
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.swt.graphics.Image;

public class PersistenceXmlItemLabelProvider extends AbstractItemLabelProvider
{
	public PersistenceXmlItemLabelProvider(
			PersistenceXml persistenceXml, DelegatingContentAndLabelProvider labelProvider) {
		super(persistenceXml, labelProvider);
	}
	
	@Override
	public PersistenceXml getModel() {
		return (PersistenceXml) super.getModel();
	}

	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new StaticPropertyValueModel<Image>(JptJpaUiPlugin.getImage(JptUiIcons.JPA_FILE));
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new StaticPropertyValueModel<String>(getModel().getResource().getName());
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		StringBuilder sb = new StringBuilder();
		sb.append(getModel().getResource().getName());
		sb.append(" - ");  //$NON-NLS-1$
		sb.append(getModel().getResource().getParent().getFullPath().makeRelative());
		return new StaticPropertyValueModel<String>(sb.toString());
	}
}
