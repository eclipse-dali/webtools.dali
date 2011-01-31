/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.platform.generic;

import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.ui.internal.jface.AbstractItemLabelProvider;
import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;

public class PersistentAttributeItemLabelProvider
	extends AbstractItemLabelProvider
{
	public PersistentAttributeItemLabelProvider(
			ReadOnlyPersistentAttribute persistentAttribute, DelegatingContentAndLabelProvider labelProvider) {
		super(persistentAttribute, labelProvider);
	}
	
	
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new PropertyAspectAdapter<ReadOnlyPersistentAttribute, Image>(
				new String[] {ReadOnlyPersistentAttribute.DEFAULT_MAPPING_KEY_PROPERTY, ReadOnlyPersistentAttribute.MAPPING_PROPERTY}, 
				(ReadOnlyPersistentAttribute) model()) {
			@Override
			protected Image buildValue_() {
				if (((ReadOnlyPersistentAttribute) model()).isVirtual()) {
					return JptUiIcons.ghost(JpaMappingImageHelper.iconKeyForAttributeMapping(this.subject.getMappingKey()));
				}
				return JpaMappingImageHelper.imageForAttributeMapping(this.subject.getMappingKey());
			}
		};
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new PropertyAspectAdapter<ReadOnlyPersistentAttribute, String>(ReadOnlyPersistentAttribute.NAME_PROPERTY, (ReadOnlyPersistentAttribute) model()) {
			@Override
			protected String buildValue_() {
				return subject.getName();
			}
		};
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		return new PropertyAspectAdapter<ReadOnlyPersistentAttribute, String>(ReadOnlyPersistentAttribute.NAME_PROPERTY, (ReadOnlyPersistentAttribute) model()) {
			@Override
			protected String buildValue_() {
				StringBuilder sb = new StringBuilder();
				sb.append(this.subject.getPersistenceUnit().getName());
				sb.append('/');
				sb.append(this.subject.getOwningPersistentType().getName());
				sb.append('/');
				sb.append(this.subject.getName());
				IResource resource = this.subject.getResource();
				if (resource != null) {
					sb.append(" - "); //$NON-NLS-1$
					sb.append(resource.getFullPath().makeRelative());
				}
				return sb.toString();
			}
		};
	}
}
