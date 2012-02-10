/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.structure;

import org.eclipse.jpt.common.ui.internal.jface.StaticItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider.Manager;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProviderFactory;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.JavaPersistentTypeItemContentProvider;

/**
 * This factory builds item content providers for a Java source file
 * JPA Structure View.
 */
public class JavaStructureItemContentProviderFactory
	implements ItemTreeContentProviderFactory
{
	// singleton
	private static final ItemTreeContentProviderFactory INSTANCE = new JavaStructureItemContentProviderFactory();

	/**
	 * Return the singleton
	 */
	public static ItemTreeContentProviderFactory instance() {
		return INSTANCE;
	}


	protected JavaStructureItemContentProviderFactory() {
		super();
	}

	public ItemTreeContentProvider buildProvider(Object item, ItemTreeContentProvider.Manager manager) {
		if (item instanceof JpaFile) {
			return this.buildJpaFileProvider((JpaFile) item, manager);
		}
		if (item instanceof JavaPersistentType) {
			return this.buildJavaPersistentTypeProvider((JavaPersistentType) item, manager);
		}
		if (item instanceof JavaPersistentAttribute) {
			return this.buildJavaPersistentAttributeProvider((JavaPersistentAttribute) item, manager);
		}
		return null;
	}

	protected ItemTreeContentProvider buildJpaFileProvider(JpaFile item, Manager manager) {
		return new JpaFileItemContentProvider(item, manager);
	}

	protected ItemTreeContentProvider buildJavaPersistentTypeProvider(JavaPersistentType item, Manager manager) {
		return new JavaPersistentTypeItemContentProvider(item, manager);
	}

	protected ItemTreeContentProvider buildJavaPersistentAttributeProvider(JavaPersistentAttribute item, @SuppressWarnings("unused") Manager manager) {
		return new StaticItemTreeContentProvider(item.getParent());
	}
}
