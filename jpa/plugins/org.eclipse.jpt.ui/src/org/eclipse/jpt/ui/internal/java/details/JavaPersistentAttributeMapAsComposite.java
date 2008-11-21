/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.details;

import java.util.Iterator;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.mappings.details.PersistentAttributeMapAsComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.swt.widgets.Composite;

/**
 * This "Map As" composite is responsible for showing the mapping name and
 * mapping type for an attribute declared in a Java type.
 *
 * @see JavaPersistentAttribute
 * @see JavaPersistentAttributeDetailsPage - The parent container
 *
 * @version 2.0
 * @since 2.0
 */
public class JavaPersistentAttributeMapAsComposite extends PersistentAttributeMapAsComposite<JavaPersistentAttribute> {

	/**
	 * Creates a new <code>JavaPersistentAttributeMapAsComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public JavaPersistentAttributeMapAsComposite(Pane<? extends JavaPersistentAttribute> parentPane,
	                                             Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> attributeMappingUiProviders() {
		return getJpaPlatformUi().javaAttributeMappingUiProviders();
	}

	@Override
	protected Iterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> defaultAttributeMappingUiProviders() {
		return getJpaPlatformUi().defaultJavaAttributeMappingUiProviders();
	}
}
