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
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.ui.internal.menus.PersistentAttributeMapAsContribution;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;
import org.eclipse.ui.services.IEvaluationService;

/**
 * This handler is responsible to change the mapping type of the selected
 * <code>PersistentAttribute</code>.
 * <p>
 * This handler is defined in the JPT plugin.xml. It will be invoked by the
 * mapping action dynamically created by the <code>PersistentAttributeMapAsContribution</code>.
 *
 * @see PersistentAttribute
 * @see PersistentAttributeMapAsContribution
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class PersistentAttributeMapAsHandler extends AbstractHandler
	implements IElementUpdater
{
	/**
	 * The unique identifier of the Map As command used for <code>PersistentAttribute</code>
	 * defined in the <code>JptUiPlugin</code> plugin.xml.
	 */
	public static final String COMMAND_ID = "org.eclipse.jpt.ui.persistentAttributeMapAs";
	
	/**
	 * The unique identifier of the Map As command parameter used for <code>PersistentAttribute</code>
	 * defined in the <code>JptUiPlugin</code> plugin.xml.
	 */
	public static final String COMMAND_PARAMETER_ID = "persistentAttributeMappingKey";
	
	
	/**
	 * Creates a new <code>PersistentAttributeMapAsHandler</code>.
	 */
	public PersistentAttributeMapAsHandler() {
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
			PersistentAttribute attribute = (PersistentAttribute) item;
			attribute.setSpecifiedMappingKey(mappingKey);
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
			
			if (! (obj instanceof PersistentAttribute)) {
				return null;
			}
			
			PersistentAttribute persistentAttribute = (PersistentAttribute) obj;
			
			if (commonKey == null) {
				commonKey = persistentAttribute.getMappingKey();
			}
			else if (! commonKey.equals(persistentAttribute.getMappingKey())) {
				return null;
			}
		}
		return commonKey;
	}
}
