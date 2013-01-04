/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.jaxb21;

import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider.Manager;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProviderFactory;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnum;

/**
 * 
 */
public class GenericJaxb_2_1_NavigatorTreeItemContentProviderFactory
	implements ItemTreeContentProviderFactory
{
	// singleton
	private static final ItemTreeContentProviderFactory INSTANCE = new GenericJaxb_2_1_NavigatorTreeItemContentProviderFactory();

	/**
	 * Return the singleton
	 */
	public static ItemTreeContentProviderFactory instance() {
		return INSTANCE;
	}


	protected GenericJaxb_2_1_NavigatorTreeItemContentProviderFactory() {
		super();
	}

	public ItemTreeContentProvider buildProvider(Object item, Manager manager) {
		if (item instanceof JaxbContextRoot) {
			return this.buildJaxbContextRootProvider((JaxbContextRoot) item, manager);
		}
		if (item instanceof JaxbPackage) {
			return this.buildJaxbPackageProvider((JaxbPackage) item, manager);
		}
		if (item instanceof JavaClass) {
			return this.buildJaxbClassProvider((JavaClass) item, manager);
		}
		if (item instanceof JavaEnum) {
			return this.buildJaxbEnumProvider((JavaEnum) item, manager);
		}
		return null;
	}

	protected ItemTreeContentProvider buildJaxbContextRootProvider(JaxbContextRoot item, Manager manager) {
		return new JaxbContextRootItemContentProvider(item, manager);
	}

	protected ItemTreeContentProvider buildJaxbPackageProvider(JaxbPackage item, Manager manager) {
		return new JaxbPackageItemContentProvider(item, manager);
	}

	protected ItemTreeContentProvider buildJaxbClassProvider(JavaClass item, Manager manager) {
		return new JavaClassItemContentProvider(item, manager);
	}

	protected ItemTreeContentProvider buildJaxbEnumProvider(JavaEnum item, Manager manager) {
		return new JavaEnumItemContentProvider(item, manager);
	}
}
