/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.commands;

import java.util.Iterator;
import java.util.Map;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.ui.internal.menus.PersistentTypeMapAsContribution;
import org.eclipse.ui.ISources;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.menus.UIElement;

/**
 * This handler is responsible to change the mapping type of the selected
 * <code>PersistentType</code>.
 * <p>
 * This handler is defined in the JPT plugin.xml. It will be invoked by the
 * mapping action dynamically created by the <code>PersistentTypeMapAsContribution</code>.
 *
 * @see PersistentType
 * @see PersistentTypeMapAsContribution
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class PersistentTypeMapAsHandler extends AbstractHandler
	implements IElementUpdater
{
	/**
	 * The unique identifier of the Map As command used for {@link PersistentType}
	 * defined in the <code>JptUiPlugin</code> plugin.xml.
	 */
	public static final String COMMAND_ID = "org.eclipse.jpt.ui.persistentTypeMapAs";
	
	/**
	 * The unique identifier of the Map As command parameter used for {@link PersistentType}
	 * defined in the <code>JptUiPlugin</code> plugin.xml.
	 */
	public static final String COMMAND_PARAMETER_ID = "persistentTypeMappingKey";
	
	
	/**
	 * Creates a new <code>PersistentTypeMapAsHandler</code>.
	 */
	public PersistentTypeMapAsHandler() {
		super();
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {

		// Retrieve the selection from the ExecutionEvent
		IStructuredSelection selection = (IStructuredSelection)
			HandlerUtil.getCurrentSelectionChecked(event);

		// Retrieve the value of the unique parameter passed to the command
		String mappingKey = event.getParameter(COMMAND_PARAMETER_ID);

		// Change the mapping key for all the selected items
		for (Object item : selection.toArray()) {
			PersistentType type = (PersistentType) item;
			type.setMappingKey(mappingKey);
		}

		return null;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void updateElement(UIElement element, Map parameters) {
		// Retrieve the selection from the UIElement
		IHandlerService handlerService =
			(IHandlerService) element.getServiceLocator().getService(IHandlerService.class);
		IStructuredSelection selection = 
			(IStructuredSelection) handlerService.getCurrentState().getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);
		
		String commonMappingKey = commonMappingKey(selection);
		String handlerMappingKey = (String) parameters.get(COMMAND_PARAMETER_ID);
		if (handlerMappingKey != null) {
			element.setChecked(handlerMappingKey.equals(commonMappingKey));
		}

	}
	
	@SuppressWarnings("unchecked")
	protected String commonMappingKey(IStructuredSelection selection) {
		String commonKey = null;
		for (Iterator stream = selection.iterator(); stream.hasNext(); ) {
			PersistentType persistentType = (PersistentType) stream.next();
			if (commonKey == null) {
				commonKey = persistentType.getMappingKey();
			}
			else if (! commonKey.equals(persistentType.getMappingKey())) {
				return null;
			}
		}
		return commonKey;
	}
}
