/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal;

import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.ui.internal.jaxb21.JaxbPersistentAttributeItemLabelProvider;
import org.eclipse.swt.graphics.Image;


public class ELJaxbPersistentAttributeItemLabelProvider 
		extends JaxbPersistentAttributeItemLabelProvider {
	
	public ELJaxbPersistentAttributeItemLabelProvider(
			JaxbPersistentAttribute attribute, DelegatingContentAndLabelProvider labelProvider) {
		super(attribute, labelProvider);
	}
	
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		
		return new PropertyAspectAdapter<JaxbPersistentAttribute, Image>(
				new String[] {JaxbPersistentAttribute.DEFAULT_MAPPING_KEY_PROPERTY, JaxbPersistentAttribute.MAPPING_PROPERTY}, 
				getModel()) {
			
			@Override
			protected Image buildValue_() {
				return ELJaxbMappingImageHelper.imageForAttributeMapping(this.subject.getMappingKey());
			}
		};
	}
}
