/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal.navigator;

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.ui.internal.plugin.JptJaxbEclipseLinkUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiIcons;
import org.eclipse.swt.graphics.Image;

public class OxmJavaTypeItemLabelProvider
		extends AbstractItemExtendedLabelProvider<OxmJavaType> {
	
	public OxmJavaTypeItemLabelProvider(OxmJavaType oxmJavaType, ItemLabelProvider.Manager manager) {
		super(oxmJavaType, manager);
	}
	
	@Override
	public Image getImage() {
		return JptJaxbEclipseLinkUiPlugin.instance().getImage(JptJaxbUiIcons.JAXB_CLASS);
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new PropertyAspectAdapter<OxmJavaType, String>(OxmJavaType.QUALIFIED_NAME_PROPERTY, this.item) {
			@Override
			protected String buildValue_() {
				return this.subject.getSimpleName();
			}
		};
	}

	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		return new PropertyAspectAdapter<OxmJavaType, String>(OxmJavaType.QUALIFIED_NAME_PROPERTY, this.item) {
			@Override
			protected String buildValue_() {
				return this.subject.getQualifiedName();
			}
		};
	}
}
