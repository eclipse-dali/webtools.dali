/*******************************************************************************
 *  Copyright (c) 2010, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiIcons;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.swt.graphics.Image;


public class JaxbContextRootItemLabelProvider
		 extends AbstractItemLabelProvider {
	
	public JaxbContextRootItemLabelProvider(
			JaxbContextRoot rootContextNode, DelegatingContentAndLabelProvider labelProvider) {
		
		super(rootContextNode, labelProvider);
	}
	
	
	@Override
	public JaxbContextRoot getModel() {
		return (JaxbContextRoot) super.getModel();
	}
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new StaticPropertyValueModel<Image>(JptJaxbUiPlugin.getImage(JptJaxbUiIcons.JAXB_CONTENT));
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new StaticPropertyValueModel<String>(JptJaxbUiMessages.JaxbContent_label);
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		StringBuilder sb = new StringBuilder();
		sb.append(JptJaxbUiMessages.JaxbContent_label);
		sb.append(" - ");  //$NON-NLS-1$
		sb.append(getModel().getResource().getFullPath().makeRelative());
		return new StaticPropertyValueModel<String>(sb.toString());
	}
}
