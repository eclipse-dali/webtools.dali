/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemLabelProvider;
import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.swt.graphics.Image;

public class ClassRefItemLabelProvider extends AbstractItemLabelProvider
{
	public ClassRefItemLabelProvider(
			ClassRef classRef, DelegatingContentAndLabelProvider labelProvider) {
		super(classRef, labelProvider);
	}
	
	@Override
	public ClassRef getModel() {
		return (ClassRef) super.getModel();
	}

	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		Image image;
		if (getModel().isVirtual()) {
			image = JptUiIcons.ghost(JptUiIcons.CLASS_REF);
		}
		else {
			 image = JptJpaUiPlugin.getImage(JptUiIcons.CLASS_REF);
		}
		return new StaticPropertyValueModel<Image>(image);
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new PropertyAspectAdapter<ClassRef, String>(ClassRef.CLASS_NAME_PROPERTY, getModel()) {
			 @Override
			protected String buildValue_() {
				return this.subject.getClassName();
			}
		};
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		//TODO also need to listen to the PersistenceUnit name property since this value depends on it
		return new PropertyAspectAdapter<ClassRef, String>(ClassRef.CLASS_NAME_PROPERTY, getModel()) {
			@Override
			protected String buildValue_() {
				StringBuilder sb = new StringBuilder();
				sb.append(this.subject.getPersistenceUnit().getName());
				sb.append("/\"");  //$NON-NLS-1$
				sb.append(this.subject.getClassName());
				sb.append("\" - "); //$NON-NLS-1$
				sb.append(this.subject.getResource().getFullPath().makeRelative());
				return sb.toString();
			}
		};
	}
}
