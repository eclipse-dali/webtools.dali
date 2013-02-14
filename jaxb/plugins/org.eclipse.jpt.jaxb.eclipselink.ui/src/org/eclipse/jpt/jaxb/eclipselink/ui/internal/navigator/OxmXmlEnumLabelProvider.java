/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal.navigator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.internal.jface.AbstractItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlEnum;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiImages;

public class OxmXmlEnumLabelProvider
		extends AbstractItemExtendedLabelProvider<OxmXmlEnum> {
	
	public OxmXmlEnumLabelProvider(OxmXmlEnum oxmXmlEnum, ItemExtendedLabelProvider.Manager manager) {
		super(oxmXmlEnum, manager);
	}

	@Override
	protected ImageDescriptor getImageDescriptor() {
		return JptJaxbUiImages.JAXB_ENUM;
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new PropertyAspectAdapter<OxmXmlEnum, String>(OxmXmlEnum.TYPE_NAME_PROPERTY, this.item) {
			@Override
			protected String buildValue_() {
				return this.subject.getTypeName().getTypeQualifiedName();
			}
		};
	}

	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		return new PropertyAspectAdapter<OxmXmlEnum, String>(OxmXmlEnum.TYPE_NAME_PROPERTY, this.item) {
			@Override
			protected String buildValue_() {
				return this.subject.getTypeName().getFullyQualifiedName();
			}
		};
	}
}