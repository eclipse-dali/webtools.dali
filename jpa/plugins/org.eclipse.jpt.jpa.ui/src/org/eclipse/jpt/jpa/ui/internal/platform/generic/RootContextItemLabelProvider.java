/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.JpaRootContextNode;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.swt.graphics.Image;

public class RootContextItemLabelProvider extends AbstractItemLabelProvider
{
	public RootContextItemLabelProvider(
			JpaRootContextNode rootContextNode, DelegatingContentAndLabelProvider labelProvider) {
		super(rootContextNode, labelProvider);
	}
	
	@Override
	public JpaRootContextNode getModel() {
		return (JpaRootContextNode) super.getModel();
	}
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new StaticPropertyValueModel<Image>(JptJpaUiPlugin.getImage(JptUiIcons.JPA_CONTENT));
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new StaticPropertyValueModel<String>(JptUiMessages.JpaContent_label);
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		StringBuilder sb = new StringBuilder();
		sb.append(JptUiMessages.JpaContent_label);
		sb.append(" - ");  //$NON-NLS-1$
		sb.append(getModel().getResource().getFullPath().makeRelative());
		return new StaticPropertyValueModel<String>(sb.toString());
	}
}
