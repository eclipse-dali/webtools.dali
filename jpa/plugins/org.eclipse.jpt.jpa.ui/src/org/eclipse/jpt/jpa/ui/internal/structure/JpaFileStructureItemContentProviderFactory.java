/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.structure;

import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider.Manager;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProviderFactory;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;

/**
 * This factory builds item content providers for a <code>persistence.xml</code>
 * file JPA Structure View.
 */
public class JpaFileStructureItemContentProviderFactory
	implements ItemTreeContentProviderFactory
{
	// singleton
	private static final ItemTreeContentProviderFactory INSTANCE = new JpaFileStructureItemContentProviderFactory();

	/**
	 * Return the singleton
	 */
	public static ItemTreeContentProviderFactory instance() {
		return INSTANCE;
	}


	protected JpaFileStructureItemContentProviderFactory() {
		super();
	}

	public ItemTreeContentProvider buildProvider(Object item, ItemTreeContentProvider.Manager manager) {
		if (item instanceof JpaFile) {
			return this.buildJpaFileProvider((JpaFile) item, manager);			
		}
		return this.buildJpaStructureNodeProvider((JpaStructureNode) item, manager);
	}

	protected ItemTreeContentProvider buildJpaFileProvider(JpaFile item, Manager manager) {
		return new JpaFileItemContentProvider(item, manager);
	}

	protected ItemTreeContentProvider buildJpaStructureNodeProvider(JpaStructureNode item, Manager manager) {
		return new JpaStructureNodeItemContentProvider(item, manager);
	}
}
