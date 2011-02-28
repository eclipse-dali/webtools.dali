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
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class RenameEntityWithoutUIFeature extends RefactorEntityFeature {
	
	private String newName = null;

	public RenameEntityWithoutUIFeature(IFeatureProvider fp, String newName) {
		super(fp);
		this.newName = newName;
	}

	public void execute(JavaPersistentType jpt) {
		ICompilationUnit cu = getFeatureProvider().getCompilationUnit(jpt);
		ats = JpaArtifactFactory.instance().getRelatedAttributes(jpt);
		execute(new CustomContext(), newName, cu, jpt);
	}

	public void execute(ICustomContext context) {
		// not used
	}
	
}
