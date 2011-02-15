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
import org.eclipse.jpt.jaxb.core.context.JaxbType;


public abstract class JaxbTypeItemLabelProvider
		extends AbstractItemLabelProvider {
	
	protected JaxbTypeItemLabelProvider(
			JaxbType jaxbType, DelegatingContentAndLabelProvider labelProvider) {
		
		super(jaxbType, labelProvider);
	}
	
	@Override
	public JaxbType model() {
		return (JaxbType) super.model();
	}

	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new StaticPropertyValueModel<String>(model().getTypeQualifiedName());
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		StringBuilder sb = new StringBuilder();
		sb.append(model().getFullyQualifiedName());
		sb.append(" - ");  //$NON-NLS-1$
		sb.append(model().getResource().getFullPath().makeRelative());
		return new StaticPropertyValueModel<String>(sb.toString());
	}
}
