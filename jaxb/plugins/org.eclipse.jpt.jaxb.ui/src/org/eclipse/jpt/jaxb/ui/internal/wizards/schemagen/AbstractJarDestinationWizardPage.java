/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.schemagen;

import org.eclipse.jdt.ui.jarpackager.JarPackageData;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 *  Façade class to change accessibility of AbstractJarDestinationWizardPage.
 */
@SuppressWarnings("restriction")
public abstract class AbstractJarDestinationWizardPage extends org.eclipse.jdt.internal.ui.jarpackager.AbstractJarDestinationWizardPage
{

	public AbstractJarDestinationWizardPage(String pageName, IStructuredSelection selection, JarPackageData jarPackage) {
		super(pageName, selection, jarPackage);
	}

}
