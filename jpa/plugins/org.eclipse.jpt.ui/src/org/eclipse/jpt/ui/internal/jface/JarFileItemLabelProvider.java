/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.jface;

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemLabelProvider;
import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.java.JarFile;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.swt.graphics.Image;

public class JarFileItemLabelProvider extends AbstractItemLabelProvider
{
	public JarFileItemLabelProvider(
			JarFile jarFile, DelegatingContentAndLabelProvider labelProvider) {
		super(jarFile, labelProvider);
	}
	
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new StaticPropertyValueModel<Image>(JptUiPlugin.getImage(JptUiIcons.JAR_FILE));
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new StaticPropertyValueModel<String>(((JarFile) model()).getResource().getName());
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		JarFile jarFile = (JarFile) model();
		return new StaticPropertyValueModel<String>(
			jarFile.getResource().getName()
			+ " - " + jarFile.getResource().getParent().getFullPath().makeRelative().toString());
	}
}
