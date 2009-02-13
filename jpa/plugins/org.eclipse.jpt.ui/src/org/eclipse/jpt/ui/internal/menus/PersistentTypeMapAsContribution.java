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
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.details.MappingUiProvider;
import org.eclipse.jpt.ui.internal.commands.PersistentTypeMapAsHandler;

/**
 * This menu contribution is responsible to populate the Map As menu with the
 * registered mapping types defined in the <code>JptPlatformUi</code> for
 * <code>PersistentType</code> objects.
 *
 * @see JpaPlatform
 * @see JpaPlatformUi
 * @see PersistentType
 *
 * @version 2.0
 * @since 2.0
 */
public class PersistentTypeMapAsContribution extends MapAsContribution
{
	/**
	 * Creates a new <code>PersistentTypeMapAsContribution</code>.
	 */
	public PersistentTypeMapAsContribution() {
		super();
	}
	
	@Override
	protected String commandId() {
		return PersistentTypeMapAsHandler.COMMAND_ID;
	}
	
	@Override
	protected String commandParameterId() {
		return PersistentTypeMapAsHandler.COMMAND_PARAMETER_ID;
	}

	@Override
	protected Iterator<? extends MappingUiProvider<?>> 
			mappingUiProviders(JpaPlatformUi jpaPlatformUi, JpaStructureNode node) {
		return jpaPlatformUi.typeMappingUiProviders((PersistentType) node);
	}
}
