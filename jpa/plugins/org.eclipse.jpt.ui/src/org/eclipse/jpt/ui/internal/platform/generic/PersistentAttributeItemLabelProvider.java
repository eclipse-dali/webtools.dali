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

import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.jface.AbstractItemLabelProvider;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;

public class PersistentAttributeItemLabelProvider extends AbstractItemLabelProvider
{
	public PersistentAttributeItemLabelProvider(
			PersistentAttribute persistentAttribute, DelegatingContentAndLabelProvider labelProvider) {
		super(persistentAttribute, labelProvider);
	}
	
	
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new PropertyAspectAdapter<PersistentAttribute, Image>(
				new String[] {PersistentAttribute.DEFAULT_MAPPING_PROPERTY, PersistentAttribute.SPECIFIED_MAPPING_PROPERTY}, 
				(PersistentAttribute) model()) {
			@Override
			protected Image buildValue_() {
				Image image = JpaMappingImageHelper.imageForAttributeMapping(subject.getMappingKey());
				if (((PersistentAttribute) model()).isVirtual()) {
					return JptUiIcons.ghost(image);
				}
				return image;
			}
		};
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new PropertyAspectAdapter<PersistentAttribute, String>(PersistentAttribute.NAME_PROPERTY, (PersistentAttribute) model()) {
			@Override
			protected String buildValue_() {
				return subject.getName();
			}
		};
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		return new PropertyAspectAdapter<PersistentAttribute, String>(PersistentAttribute.NAME_PROPERTY, (PersistentAttribute) model()) {
			@Override
			protected String buildValue_() {
				return subject.getPersistenceUnit().getName() 
				+ "/" + subject.getPersistentType().getName()
				+ "/" + subject.getName()
				+ " - " + subject.getResource().getFullPath().makeRelative();
			}
		};
	}
}
