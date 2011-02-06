/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.ui.internal.jface.AbstractItemLabelProvider;
import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.ui.internal.JpaMappingImageHelper;
import org.eclipse.swt.graphics.Image;

public class PersistentTypeItemLabelProvider extends AbstractItemLabelProvider
{
	public PersistentTypeItemLabelProvider(
			PersistentType persistentType, DelegatingContentAndLabelProvider labelProvider) {
		super(persistentType, labelProvider);
	}
	
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new PropertyAspectAdapter<PersistentType, Image>(PersistentType.MAPPING_PROPERTY, (PersistentType) model()) {
			@Override
			protected Image buildValue_() {
				return JpaMappingImageHelper.imageForTypeMapping(subject.getMappingKey());
			}
		};
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new PropertyAspectAdapter<PersistentType, String>(PersistentType.NAME_PROPERTY, (PersistentType) model()) {
			@Override
			protected String buildValue_() {
				return subject.getSimpleName();
			}
		};
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		return new PropertyAspectAdapter<PersistentType, String>(PersistentType.NAME_PROPERTY, (PersistentType) model()) {
			@Override
			protected String buildValue_() {
				StringBuilder sb = new StringBuilder();
				sb.append(this.subject.getPersistenceUnit().getName());
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
