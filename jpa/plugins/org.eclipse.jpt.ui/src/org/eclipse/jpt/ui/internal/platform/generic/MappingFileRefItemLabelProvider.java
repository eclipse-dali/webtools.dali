/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.platform.generic;

import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.ui.internal.jface.AbstractItemLabelProvider;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;

public class MappingFileRefItemLabelProvider extends AbstractItemLabelProvider
{
	public MappingFileRefItemLabelProvider(
			MappingFileRef mappingFileRef, DelegatingContentAndLabelProvider labelProvider) {
		super(mappingFileRef, labelProvider);
	}
	
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		Image image = JptUiPlugin.getImage(JptUiIcons.MAPPING_FILE_REF);
		if (((MappingFileRef) model()).isImplied()) {
			image = JptUiIcons.ghost(image);
		}
		return new StaticPropertyValueModel<Image>(image);
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new PropertyAspectAdapter<MappingFileRef, String>(MappingFileRef.FILE_NAME_PROPERTY, (MappingFileRef) model()) {
			 @Override
			protected String buildValue_() {
				return subject.getFileName();
			}
		};
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		return new PropertyAspectAdapter<MappingFileRef, String>(MappingFileRef.FILE_NAME_PROPERTY, (MappingFileRef) model()) {
			@Override
			protected String buildValue_() {
				return subject.getPersistenceUnit().getName() 
				+ "/\"" + subject.getFileName() + "\""
				+ " - " + subject.getResource().getFullPath().makeRelative();
			}
		};
	}
}
