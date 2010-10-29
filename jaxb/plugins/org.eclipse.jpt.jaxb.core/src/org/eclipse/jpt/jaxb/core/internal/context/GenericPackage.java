/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context;

import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbRootContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;

//TODO for now we will assume a 1-1 relationship between a package and a package-info.
//Later there could be annotated types in a package that has no package-info.java
//Or we could have a mapping file instead and this would have the PackageInfo in it.
public class GenericPackage
	extends AbstractJaxbContextNode
	implements JaxbPackage
{

	protected final JaxbPackageInfo packageInfo;

	public GenericPackage(JaxbRootContextNode parent, JavaResourcePackage resourcePackage) {
		super(parent);
		this.packageInfo = buildPackageInfo(resourcePackage);
	}
	
	public void synchronizeWithResourceModel() {
		this.packageInfo.synchronizeWithResourceModel();
	}

	public void update() {
		this.packageInfo.update();
	}

	public JavaResourcePackage getResourcePackage() {
		return this.packageInfo.getResourcePackage();
	}


	// ********** package info **********

	public JaxbPackageInfo getPackageInfo() {
		return this.packageInfo;
	}
	
	protected JaxbPackageInfo buildPackageInfo(JavaResourcePackage resourcePackage) {
		return getFactory().buildJavaPackageInfo(this, resourcePackage);
	}
}
