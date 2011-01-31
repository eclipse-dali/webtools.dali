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
import org.eclipse.jpt.jaxb.core.context.JaxbType;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiIcons;
import org.eclipse.jpt.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;


public class JaxbTypeItemLabelProvider
		extends AbstractItemLabelProvider {
	
	public JaxbTypeItemLabelProvider(
			JaxbType jaxbType, DelegatingContentAndLabelProvider labelProvider) {
		
		super(jaxbType, labelProvider);
	}
	
	
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new StaticPropertyValueModel<Image>(JptJaxbUiPlugin.getImage(JptJaxbUiIcons.PERSISTENT_CLASS));
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new StaticPropertyValueModel(((JaxbType) model()).getTypeQualifiedName());
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		JaxbType type = (JaxbType) model();
		return new StaticPropertyValueModel(
				type.getFullyQualifiedName() + " - " + type.getResource().getFullPath().makeRelative());
	}
}
