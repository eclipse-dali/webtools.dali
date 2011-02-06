/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.commands;

import java.util.Iterator;
import java.util.Map;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.ui.internal.menus.PersistentTypeMapAsContribution;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;
import org.eclipse.ui.services.IEvaluationService;

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
	 * defined in the <code>JptJpaUiPlugin</code> plugin.xml.
	 */
	public static final String COMMAND_ID = "org.eclipse.jpt.jpa.ui.persistentTypeMapAs";
	
	/**
	 * The unique identifier of the Map As command parameter used for {@link PersistentType}
	 * defined in the <code>JptJpaUiPlugin</code> plugin.xml.
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
		// Retrieve the selection for the UIElement
		
		// Due to Bug 226746, we have to use API workaround to retrieve current 
		// selection
		IEvaluationService es 
			= (IEvaluationService) element.getServiceLocator().getService(IEvaluationService.class);
		IViewPart part = 
			(IViewPart) es.getCurrentState().getVariable(ISources.ACTIVE_PART_NAME);
		IStructuredSelection selection 
			= (IStructuredSelection) part.getSite().getSelectionProvider().getSelection();
		
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
			Object obj = stream.next();
			
			if (! (obj instanceof PersistentType)) {
				return null;
			}
			
			PersistentType persistentType = (PersistentType) obj;
			
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
