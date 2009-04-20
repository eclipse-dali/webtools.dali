/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.platform.generic;

import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.jface.AbstractItemLabelProvider;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;

public class OrmXmlItemLabelProvider extends AbstractItemLabelProvider
{
	public OrmXmlItemLabelProvider(
			OrmXml ormXml, DelegatingContentAndLabelProvider labelProvider) {
		super(ormXml, labelProvider);
	}
	
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new StaticPropertyValueModel<Image>(JptUiPlugin.getImage(JptUiIcons.JPA_FILE));
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new StaticPropertyValueModel<String>(((OrmXml) model()).getResource().getName());
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		OrmXml ormXml = (OrmXml) model();
		return new StaticPropertyValueModel<String>(
			ormXml.getResource().getName()
			+ " - " + ormXml.getResource().getParent().getFullPath().makeRelative().toString());
	}
}
