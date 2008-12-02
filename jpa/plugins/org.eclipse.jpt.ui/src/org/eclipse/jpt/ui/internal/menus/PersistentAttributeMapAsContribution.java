/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.menus;

import java.util.Iterator;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.details.MappingUiProvider;
import org.eclipse.jpt.ui.internal.commands.PersistentAttributeMapAsHandler;

/**
 * This menu contribution is responsible to populate the Map As menu with the
 * registered attribute mapping types defined in the <code>JptPlatformUi</code>
 * for <code>PersistentAttribute</code> objects.
 *
 * @see PersistentAttribute
 *
 * @version 2.0
 * @since 2.0
 */
public class PersistentAttributeMapAsContribution extends MapAsContribution
{
	/**
	 * Creates a new <code>PersistentAttributeMapAsContribution</code>.
	 */
	public PersistentAttributeMapAsContribution() {
		super();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String commandId() {
		return PersistentAttributeMapAsHandler.COMMAND_ID;
	}
	
	@Override
	protected String commandParameterId() {
		return PersistentAttributeMapAsHandler.COMMAND_PARAMETER_ID;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Iterator<? extends MappingUiProvider<?>> 
			mappingUiProviders(JpaPlatformUi jpaPlatformUi, JpaStructureNode node) {
		return jpaPlatformUi.attributeMappingUiProviders((PersistentAttribute) node);
	}
}
