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

import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.jface.AbstractItemLabelProvider;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;

public class ClassRefItemLabelProvider extends AbstractItemLabelProvider
{
	public ClassRefItemLabelProvider(
			ClassRef classRef, DelegatingContentAndLabelProvider labelProvider) {
		super(classRef, labelProvider);
	}
	
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		Image image = JptUiPlugin.getImage(JptUiIcons.CLASS_REF);
		if (((ClassRef) model()).isVirtual()) {
			image = JptUiIcons.ghost(image);
		}
		return new StaticPropertyValueModel<Image>(image);
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new PropertyAspectAdapter<ClassRef, String>(ClassRef.CLASS_NAME_PROPERTY, (ClassRef) model()) {
			 @Override
			protected String buildValue_() {
				return subject.getClassName();
			}
		};
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		return new PropertyAspectAdapter<ClassRef, String>(ClassRef.CLASS_NAME_PROPERTY, (ClassRef) model()) {
			@Override
			protected String buildValue_() {
				return subject.getPersistenceUnit().getName() 
				+ "/\"" + subject.getClassName()
				+ "\" - " + subject.getResource().getFullPath().makeRelative();
			}
		};
	}
}
