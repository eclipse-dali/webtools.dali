/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.java;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.ui.internal.details.AbstractInheritanceComposite;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for java inheritance.
 *
 * @see JavaEntity
 * @see JavaPrimaryKeyJoinColumnsComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class JavaInheritanceComposite extends AbstractInheritanceComposite<JavaEntity> {

	/**
	 * Creates a new <code>JavaInheritanceComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public JavaInheritanceComposite(Pane<? extends JavaEntity> parentPane,
	                            Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected void addPrimaryKeyJoinColumnsComposite(Composite container) {
		new JavaPrimaryKeyJoinColumnsComposite(this, container);
	}
}