/*******************************************************************************
 *  Copyright (c) 2009, 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemLabelProvider;
import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.swt.graphics.Image;

public class JarFileRefItemLabelProvider extends AbstractItemLabelProvider
{
	public JarFileRefItemLabelProvider(
			JarFileRef jarFileRef, DelegatingContentAndLabelProvider labelProvider) {
		super(jarFileRef, labelProvider);
	}
	
	@Override
	public JarFileRef getModel() {
		return (JarFileRef) super.getModel();
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new PropertyAspectAdapter<JarFileRef, String>(JarFileRef.FILE_NAME_PROPERTY, getModel()) {
			 @Override
			protected String buildValue_() {
				return this.subject.getFileName();
			}
		};
	}
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new StaticPropertyValueModel<Image>(JptJpaUiPlugin.getImage(JptUiIcons.JAR_FILE_REF));
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		return new PropertyAspectAdapter<JarFileRef, String>(JarFileRef.FILE_NAME_PROPERTY, getModel()) {
			@Override
			protected String buildValue_() {
				StringBuilder sb = new StringBuilder();
				sb.append(this.subject.getPersistenceUnit().getName());
				sb.append("/\"");  //$NON-NLS-1$
				sb.append(this.subject.getFileName());
				sb.append("\" - "); //$NON-NLS-1$
				sb.append(this.subject.getResource().getFullPath().makeRelative());
				return sb.toString();
			}
		};
	}
}
