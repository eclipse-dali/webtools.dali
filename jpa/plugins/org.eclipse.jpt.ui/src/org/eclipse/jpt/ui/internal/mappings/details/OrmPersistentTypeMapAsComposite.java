/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Iterator;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.ui.details.MappingUiProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.swt.widgets.Composite;

/**
 * This "Map As" composite is responsible for showing the mapping name and
 * mapping type for a type declared within a JPA mapping descriptor file.
 *
 * @see OrmPersistentType
 * @see OrmPersistentTypeDetailsPage - The parent container
 *
 * @version 2.0
 * @since 2.0
 */
public class OrmPersistentTypeMapAsComposite extends PersistentTypeMapAsComposite<OrmPersistentType>
{
	/**
	 * Creates a new <code>OrmPersistentTypeMapAsComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public OrmPersistentTypeMapAsComposite(Pane<? extends OrmPersistentType> parentPane,
	                                       Composite parent) {

		super(parentPane, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected MappingUiProvider<?> buildDefaultProvider() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Iterator<TypeMappingUiProvider<? extends TypeMapping>> typeMappingUiProviders() {
		return getJpaPlatformUi().ormTypeMappingUiProviders();
	}
}
