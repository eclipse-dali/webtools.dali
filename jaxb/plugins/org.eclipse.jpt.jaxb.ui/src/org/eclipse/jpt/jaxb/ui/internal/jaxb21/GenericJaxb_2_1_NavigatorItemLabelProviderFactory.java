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

import org.eclipse.jpt.common.ui.internal.jface.StaticItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProviderFactory;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaEnum;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiImages;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;


public class GenericJaxb_2_1_NavigatorItemLabelProviderFactory
	implements ItemExtendedLabelProviderFactory {
	
	private static GenericJaxb_2_1_NavigatorItemLabelProviderFactory INSTANCE;
	
	
	public static GenericJaxb_2_1_NavigatorItemLabelProviderFactory instance() {
		if (INSTANCE == null) {
			INSTANCE = new GenericJaxb_2_1_NavigatorItemLabelProviderFactory();
		}
		return INSTANCE;
	}
	
	
	protected GenericJaxb_2_1_NavigatorItemLabelProviderFactory() {
		super();
	}
	
	
	public ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager) {
		
		if (item instanceof JaxbContextRoot) {
			return this.buildJaxbContextRootProvider((JaxbContextRoot) item, manager);
		}
		else if (item instanceof JaxbPackage) {
			return this.buildJaxbPackageProvider((JaxbPackage) item, manager);
		}
		else if (item instanceof JavaClass) {
			return new JaxbClassItemLabelProvider((JavaClass) item, manager);
		}
		else if (item instanceof JavaEnum) {
			return new JaxbEnumItemLabelProvider((JavaEnum) item, manager);
		}
		else if (item instanceof JaxbPersistentAttribute) {
			return new JaxbPersistentAttributeItemLabelProvider((JaxbPersistentAttribute) item, manager);
		}
		else if (item instanceof JaxbEnumConstant) {
			return this.buildJaxbEnumConstantProvider((JaxbEnumConstant) item, manager);
		}
		return null;
	}

	protected ItemExtendedLabelProvider buildJaxbContextRootProvider(JaxbContextRoot root, ItemExtendedLabelProvider.Manager manager) {
		return new StaticItemExtendedLabelProvider(
					JptJaxbUiImages.JAXB_CONTENT,
					JptJaxbUiMessages.JaxbContent_label,
					this.buildJaxbContextRootDescription(root),
					manager
				);
	}

	protected String buildJaxbContextRootDescription(JaxbContextRoot root) {
		StringBuilder sb = new StringBuilder();
		sb.append(JptJaxbUiMessages.JaxbContent_label);
		sb.append(" - ");  //$NON-NLS-1$
		sb.append(root.getResource().getFullPath().makeRelative());
		return sb.toString();
	}

	protected ItemExtendedLabelProvider buildJaxbEnumConstantProvider(JaxbEnumConstant enumConstant, ItemExtendedLabelProvider.Manager manager) {
		return new StaticItemExtendedLabelProvider(
					JptJaxbUiImages.ENUM_CONSTANT,
					enumConstant.getName(),
					manager
				);
	}

	protected ItemExtendedLabelProvider buildJaxbPackageProvider(JaxbPackage pkg, ItemExtendedLabelProvider.Manager manager) {
		return new StaticItemExtendedLabelProvider(
					JptJaxbUiImages.JAXB_PACKAGE,
					pkg.getName(),
					this.buildJaxbPackageDescription(pkg),
					manager
				);
	}

	protected String buildJaxbPackageDescription(JaxbPackage pkg) {
		StringBuilder sb = new StringBuilder();
		sb.append(pkg.getName());
		sb.append(" - ");  //$NON-NLS-1$
		sb.append(pkg.getResource().getFullPath().makeRelative());
		return sb.toString();
	}
}
