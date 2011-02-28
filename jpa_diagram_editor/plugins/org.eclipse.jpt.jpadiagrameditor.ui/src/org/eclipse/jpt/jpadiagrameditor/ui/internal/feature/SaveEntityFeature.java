/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class SaveEntityFeature extends AbstractCustomFeature {

	public SaveEntityFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	public boolean canExecute(ICustomContext context) {
		return true;
	}	

	public void execute(ICustomContext context) {
		JavaPersistentType jpt = (JavaPersistentType)getFeatureProvider().getBusinessObjectForPictogramElement(context.getPictogramElements()[0]);
		JpaArtifactFactory.instance().forceSaveEntityClass(jpt, getFeatureProvider());
	}
	
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}		

}
