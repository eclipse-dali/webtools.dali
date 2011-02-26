/*******************************************************************************
* Copyright (c) 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.ui.internal.wizards.gen;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.ui.internal.wizards.JavaProjectWizardPage;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.dbws.eclipselink.ui.internal.DbwsGeneratorUi;

/**
 *  WebDynamicProjectWizardPage
 */
public class WebDynamicProjectWizardPage extends JavaProjectWizardPage {

	public WebDynamicProjectWizardPage(IJavaProject javaProject) {
		super(javaProject);
	}

	@Override
	protected Iterable<IProject> getJavaProjects() {
	   return new FilteringIterable<IProject>(CollectionTools.collection(this.getProjects())) {
	      @Override
	      protected boolean accept(IProject next) {
				return DbwsGeneratorUi.projectIsWebDynamic(next);
	      }
	   };
	}

}
