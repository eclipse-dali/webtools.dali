/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.jaxb21;

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemLabelProvider;
import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentEnum;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiIcons;
import org.eclipse.swt.graphics.Image;


public class JaxbPersistentEnumItemLabelProvider
		extends AbstractItemLabelProvider {
	
	public JaxbPersistentEnumItemLabelProvider(
			JaxbPersistentEnum jaxbEnum, DelegatingContentAndLabelProvider labelProvider) {
		
		super(jaxbEnum, labelProvider);
	}
	
	@Override
	public JaxbPersistentEnum model() {
		return (JaxbPersistentEnum) super.model();
	}
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new StaticPropertyValueModel<Image>(JptJaxbUiPlugin.getImage(JptJaxbUiIcons.PERSISTENT_ENUM));
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new StaticPropertyValueModel(model().getTypeQualifiedName());
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		return new StaticPropertyValueModel(
				model().getFullyQualifiedName() + " - " + model().getResource().getFullPath().makeRelative());
	}
}
