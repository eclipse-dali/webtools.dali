/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
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
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.ui.internal.JaxbMappingImageHelper;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;


public abstract class JaxbPersistentAttributeItemLabelProvider
		 extends AbstractItemLabelProvider {
	
	public JaxbPersistentAttributeItemLabelProvider(
		JaxbPersistentAttribute attribute, DelegatingContentAndLabelProvider labelProvider) {
		
		super(attribute, labelProvider);
	}
	
	
	@Override
	public JaxbPersistentAttribute model() {
		return (JaxbPersistentAttribute) super.model();
	}
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new PropertyAspectAdapter<JaxbPersistentAttribute, Image>(
				new String[] {JaxbPersistentAttribute.DEFAULT_MAPPING_KEY_PROPERTY, JaxbPersistentAttribute.MAPPING_PROPERTY}, 
				model()) {
			@Override
			protected Image buildValue_() {
				return JaxbMappingImageHelper.imageForAttributeMapping(this.subject.getMappingKey());
			}
		};
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new StaticPropertyValueModel<String>(model().getName());
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		return new StaticPropertyValueModel<String>(model().getName());
	}
}
